/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.andes.kernel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.andes.kernel.slot.Slot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents the message metadata and all the delivery aspects of it to the subscribers (outbound path).
 * The lifecycle of the message is maintained here itself.
 */
public class DeliverableAndesMetadata extends AndesMessageMetadata{

    /**
     * Number of scheduled deliveries. concurrently modified whenever the message is scheduled to be delivered.
     */
    private AtomicInteger numberOfScheduledDeliveries;
    /**
     * Map to keep message status and delivery information of this message to vivid channels
     */
    private Map<UUID, ChannelInformation> channelDeliveryInfo;
    /**
     * State transition of the message
     */
    private List<MessageStatus> messageStatus;
    /**
     * Parent slot of message.
     */
    private Slot slot;

    /**
     * Time stamp message is read from the store
     */
    private long timeMessageIsRead;

    private static Log log = LogFactory.getLog(DeliverableAndesMetadata.class);

    public DeliverableAndesMetadata(Slot slot, long messageID, byte[] metadata, boolean parse) {
        super(messageID, metadata, parse);
        this.slot = slot;
        this.timeMessageIsRead = System.currentTimeMillis();
        this.channelDeliveryInfo = new ConcurrentHashMap<>();
        this.messageStatus = Collections.synchronizedList(new ArrayList<MessageStatus>());
        this.messageStatus.add(MessageStatus.READ);
        this.numberOfScheduledDeliveries = new AtomicInteger(0);
    }

    //TODO: need to remove this constructor
    public DeliverableAndesMetadata(AndesMessageMetadata messageMetadata) {
        super((messageMetadata.messageID), messageMetadata.metadata, true);
        this.slot = null;
        this.timeMessageIsRead = System.currentTimeMillis();
        this.channelDeliveryInfo = new ConcurrentHashMap<>();
        this.messageStatus = Collections.synchronizedList(new ArrayList<MessageStatus>());
        this.messageStatus.add(MessageStatus.READ);
        this.numberOfScheduledDeliveries = new AtomicInteger(0);
    }

    /**
     * Check if message is expired
     * @return check expire result
     */
    public boolean isExpired() {
        if (expirationTime != 0L) {
            long now = System.currentTimeMillis();
            if(now > expirationTime) {
                addMessageStatus(MessageStatus.EXPIRED);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Slot getSlot() {
        return slot;
    }

    /**
     * Get Message Status this message passed as a string
     * @return encoded status history
     */
    public String getStatusHistoryAsString() {
        String history = "";
        for (MessageStatus status : messageStatus) {
            history = history + status + ">>";
        }
        return history;
    }

    public List<MessageStatus> getStatusHistory() {
        return messageStatus;
    }

    /**
     * Get current status of the message
     * @return message status
     */
    public MessageStatus getLatestState() {
        MessageStatus latest = null;
        if (messageStatus.size() > 0) {
            latest = messageStatus.get(messageStatus.size() - 1);
        }
        return latest;
    }

    /**
     * Check if this message is to be redelivered. This method should be evaluated before calling
     * markAsDeliveredToChannel method.
     * @param channelID ID of the channel to deliver
     * @return if message is a redelivery
     */
    public boolean isRedelivered(UUID channelID) {
        Integer numOfDeliveries = channelDeliveryInfo.get(channelID).getDeliveryCount();
        return numOfDeliveries > 0;
    }

    /**
     * Mark the message as buffered. Buffered messages will be scheduled to the subscribers.
     */
    public void markAsBuffered() {
        addMessageStatus(MessageStatus.BUFFERED);
    }

    /**
     * Mark message as scheduled to deliver to given subscribers
     * @param localSubscriptions local subscriptions to deliver. AMQP/MQTT subscribers have individual
     *                         delivery channels
     */
    public void markAsScheduledToDeliver(Collection<LocalSubscription> localSubscriptions) {
        for (LocalSubscription subscription : localSubscriptions) {
            ChannelInformation channelInformation = new ChannelInformation();
            channelDeliveryInfo.put(subscription.getChannelID(), channelInformation);
        }
        addMessageStatus(MessageStatus.SCHEDULED_TO_SEND);
    }

    /**
     * Mark message as scheduled to deliver to given subscriber
     * @param subscription subscription to deliver message
     */
    public void markAsScheduledToDeliver(LocalSubscription subscription) {
        ChannelInformation channelInformation = new ChannelInformation();
        channelDeliveryInfo.put(subscription.getChannelID(), channelInformation);
        addMessageStatus(MessageStatus.SCHEDULED_TO_SEND);
    }

    /**
     * Record delivery rule evaluation for a message. Before delivering a message to client
     * rules are evaluated and the result is recorded
     * @param channelID ID of the channel
     * @param isOKToDeliver is delivery rule evaluation passed
     */
    public void markDeliveryRuleEvaluation(UUID channelID, boolean isOKToDeliver) {
        ChannelInformation channelInformation = channelDeliveryInfo.get(channelID);
        if(isOKToDeliver) {
            channelInformation.addChannelStatus(ChannelMessageStatus.DELIVERY_OK);
        } else {
            channelInformation.addChannelStatus(ChannelMessageStatus.DELIVERY_REJECT);
        }
    }

    /**
     * Record message delivery to channel
     * @param channelID ID of the channel
     * @return current number of times message is delivered to given channel
     */
    public int markAsDeliveredToChannel(UUID channelID) {
        ChannelInformation channelInformation = channelDeliveryInfo.get(channelID);
        int deliveryCount = channelInformation.incrementDeliveryCount();
        if(deliveryCount == 1) {
            channelInformation.addChannelStatus(ChannelMessageStatus.SENT);
        } else if(deliveryCount > 1) {
            channelInformation.addChannelStatus(ChannelMessageStatus.RESENT);
        }
        channelDeliveryInfo.put(channelID, channelInformation);

        boolean isDeliveredToAllChannels = isMarkAsDelivered();

        if(isDeliveredToAllChannels) {
            addMessageStatus(MessageStatus.SENT_TO_ALL);
        }
        return deliveryCount;
    }

    /**
     * Record acknowledge by channel
     * @param channelID Id of the channel
     * @return if acknowledges by all the channels are received
     */
    public boolean markAsAcknowledgedByChannel(UUID channelID) {
        boolean isAcknowledgedByAll = false;
        ChannelInformation channelInformation = channelDeliveryInfo.get(channelID);
        channelInformation.addChannelStatus(ChannelMessageStatus.ACKED);
        channelDeliveryInfo.put(channelID, channelInformation);

        if(isMarkAsAcked()) {
            addMessageStatus(MessageStatus.ACKED_BY_ALL);
            isAcknowledgedByAll = true;
        }
        return isAcknowledgedByAll;
    }

    /**
     * Record the NAK/REJECT by a channel
     * @param channelID ID of the channel
     */
    public void markAsRejectedByClient(UUID channelID) {
        ChannelInformation channelInformation = channelDeliveryInfo.get(channelID);
        channelInformation.addChannelStatus(ChannelMessageStatus.CLIENT_REJECTED);
    }

    /**
     * Mark as a Dead Letter Channel message
     */
    public void markAsDLCMessage() {
        addMessageStatus(MessageStatus.DLC_MESSAGE);
    }


    /**
     * Check if message is sent to DLC
     * @return
     */
    public boolean isDLCMessage() {
        return getLatestState().equals(MessageStatus.DLC_MESSAGE);
    }

    /**
     * Check if message is deleted or purged
     * @return
     */
    public boolean isPurgedOrDeletedOrExpired() {
        MessageStatus currentStatus = getLatestState();
        return currentStatus.equals(MessageStatus.PURGED)
                || currentStatus.equals(MessageStatus.DELETED)
                || currentStatus.equals(MessageStatus.EXPIRED);
    }

    /**
     * Check if the message is OK to clear from memory
     * @return true if conditions are met
     */
    public boolean isOKToDispose() {
        return MessageStatus.isOKToRemove(messageStatus);
    }

    /**
     * Mark ad purged message
     */
    public void markAsPurgedMessage() {
        addMessageStatus(MessageStatus.PURGED);
    }

    /**
     * Mark as deleted message
     */
    public void markAsDeletedMessage() {
        addMessageStatus(MessageStatus.DELETED);
    }

    /**
     * Mark as slot removed message
     */
    public void markAsSlotRemoved() {
        addMessageStatus(MessageStatus.SLOT_REMOVED);
    }

    /**
     * Due to last subscription close of local node
     * slots can get returned to slot coordinator. There we mark
     * the messages in that slot as slot returned
     */
    public void markAsSlotReturned() {
        addMessageStatus(MessageStatus.SLOT_RETURNED);
    }

    /**
     * Cancel message delivery for channel. This is called when a
     * message delivery is failed from broker side. By the time message MUST be maked
     * as SENT to this channel
     * @param channelID id of the channel
     * @return current number of times this message is delivered to the given channel
     */
    public int markDeliveryFailureOfASentMessage(UUID channelID) {
        return channelDeliveryInfo.get(channelID).decrementDeliveryCount();
    }

    /**
     * Remove a channel that this message is scheduled to deliver. If the subscriber has closed
     * during the message schedule and actual sent this should be called
     * @param channelID Id of the channel to remove
     */
    public void removeScheduledDeliveryChannel(UUID channelID) {
        channelDeliveryInfo.remove(channelID);
        if(isMarkAsDelivered()) {
            addMessageStatus(MessageStatus.SENT_TO_ALL);
        }
        if(isMarkAsAcked()) {
            addMessageStatus(MessageStatus.ACKED_BY_ALL);
        }
    }


    /**
     * Get the channels this message is delivered to
     * @return Set of channel IDs
     */
    public Set<UUID> getAllDeliveredChannels() {
        return channelDeliveryInfo.keySet();
    }

    /**
     * Check if this message delivered to all the scheduled channels
     * @return true if delivered to all the channels
     */
    public boolean isMarkAsDelivered() {
        boolean isDelivered = true;
        for (Map.Entry<UUID, ChannelInformation> channelInfoEntry : channelDeliveryInfo.entrySet()) {
            ChannelMessageStatus channelMessageStatus = channelInfoEntry.getValue().getLatestMessageStatus();
            if(null == channelMessageStatus
                    || !(channelMessageStatus.equals(ChannelMessageStatus.SENT)
                    || channelMessageStatus.equals(ChannelMessageStatus.RESENT))) {
                isDelivered = false;
                break;
            }
        }
        return isDelivered;
    }

    /**
     * Check if this message is acknowledged by all the channels it is delivered to
     * @return true if message is acknowledged by all the channels
     */
    public boolean isMarkAsAcked() {
        boolean isAcked = true;
        for (Map.Entry<UUID, ChannelInformation> channelInfoEntry : channelDeliveryInfo.entrySet()) {
            ChannelMessageStatus messageStatus = channelInfoEntry.getValue().getLatestMessageStatus();
            if(null == messageStatus || !messageStatus.equals(ChannelMessageStatus.ACKED)) {
                isAcked = false;
                break;
            }
        }
        return isAcked;
    }

    /**
     * Get the number of times this message is delivered to the given channel
     * @param channelID Id of the channel
     * @return number of deliveries
     */
    public int getNumOfDeliveries4Channel(UUID channelID) {
         /* Since sometimes Broker tries to send stored messages when it initialised a subscription
            so then it returns null value for that subscription's channel's amount of deliveries,
            Since we need to the evaluate the rules before we send message, therefore we have to ignore the null value,
            then we have to check the number of deliveries for the particular channel */
        if (null != channelDeliveryInfo.get(channelID)) {
            return channelDeliveryInfo.get(channelID).getDeliveryCount();
        } else {
            return 0;
        }
    }

    /**
     * Check if state going to be added is valid considering it as the next
     * transition compared to current latest state.
     * @param state state to be transferred
     */
    public boolean addMessageStatus(MessageStatus state) {

        boolean isValidTransition = false;

        if(messageStatus.isEmpty()) {
            if(MessageStatus.READ.equals(state)) {
                isValidTransition = true;
                messageStatus.add(state);
            } else {
                log.warn("Invalid message state transition suggested: " + state  + " Message ID: " + messageID);
            }
        } else {
            isValidTransition = messageStatus.get(messageStatus.size() - 1).isValidNextTransition(state);
            if(isValidTransition) {
                messageStatus.add(state);
            } else {
                log.warn("Invalid message state transition from " + messageStatus.get
                        (messageStatus.size() - 1) + " suggested: " + state + " Message ID: " + messageID + "Status " +
                        "Message Status History >> " + messageStatus);

            }
        }

        return isValidTransition;
    }

    /**
     * Check if state going to be added is valid considering it as the next transition compared
     * to current latest state. This status is for individual delivery channels
     * @param channelID ID of the channel to record status
     * @param status state to be transferred
     */
/*    public boolean addMessageStatusForChannel(UUID channelID, MessageStatus status) {
         return channelDeliveryInfo.get(channelID).addChannelStatus(status);
    }*/


    /**
     * Dump message info to a csv file
     *
     * @param fileToWrite file to dump info
     * @throws AndesException
     */
    public void dumpMessageStatusToFile(File fileToWrite) throws AndesException {

        try {
            FileWriter writer = new FileWriter(fileToWrite);

            writer.append("Message ID ");
                writer.append(Long.toString(messageID));
                writer.append(',');
            writer.append("Message Header ");
                writer.append("null");
                writer.append(',');
            writer.append("Destination ");
                writer.append(getDestination());
                writer.append(',');
            writer.append("Message status ");
                writer.append(getStatusHistoryAsString());
                writer.append(',');
            writer.append("Slot Info ");
                writer.append(slot.toString());
                writer.append(',');
            writer.append("Timestamp ");
                writer.append(Long.toString(timeMessageIsRead));
                writer.append(',');
            writer.append("Expiration time ");
                writer.append(Long.toString(expirationTime));
                writer.append(',');
            writer.append("NumOfScheduledDeliveries ");
                writer.append(Integer.toString(numberOfScheduledDeliveries.get()));
                writer.append(',');
            writer.append("Channels sent ");
                String deliveries = "";
                for (UUID channelID : getAllDeliveredChannels()) {
                    deliveries = deliveries + channelID + " >> " + channelDeliveryInfo.get(channelID)
                            .getMessageStatusHistoryForChannel() + " : ";
                }
                writer.append(deliveries);
                writer.append('\n');

            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            log.error("File to write is not found", e);
            throw new AndesException("File to write is not found", e);
        } catch (IOException e) {
            log.error("Error while dumping message status to file", e);
            throw new AndesException("Error while dumping message status to file", e);
        }
    }

    /**
     * Inner class to hold Message status channel-wise
     */
    private class ChannelInformation {

        private Integer channelToNumOfDeliveries = 0;
        private List<ChannelMessageStatus> messageStatusesForChannel = new ArrayList<>(5);

        private int incrementDeliveryCount() {
            channelToNumOfDeliveries = channelToNumOfDeliveries + 1;
            return channelToNumOfDeliveries;
        }

        private int decrementDeliveryCount() {
            channelToNumOfDeliveries = channelToNumOfDeliveries - 1;
            return channelToNumOfDeliveries;
        }

        private int getDeliveryCount() {
            return channelToNumOfDeliveries;
        }

        /**
         * Check if state going to be added is valid considering it as the next transition compared
         * to current latest state. This status is for individual delivery channels
         * @param state state to be transferred
         */
        private boolean addChannelStatus(ChannelMessageStatus state) {

            boolean isValidTransition = false;

            if(messageStatusesForChannel.isEmpty()) {
                if(ChannelMessageStatus.DELIVERY_OK.equals(state) || ChannelMessageStatus.DELIVERY_REJECT.equals(state)) {
                    isValidTransition = true;
                    messageStatusesForChannel.add(state);
                } else {
                    log.warn("Invalid channel message state transition suggested: " + state  + " Message ID: " +
                            messageID + " Message Status History >> " + messageStatus);
                }
            } else {
                isValidTransition = messageStatusesForChannel.
                        get(messageStatusesForChannel.size() - 1).isValidNextTransition(state);

                if(isValidTransition) {
                    messageStatusesForChannel.add(state);
                } else {
                    log.warn("Invalid channel message state transition from " + messageStatusesForChannel.get
                            (messageStatusesForChannel.size() - 1) + " suggested: " + state + " Message ID: " +
                            messageID + " Channel Status History >> " + messageStatusesForChannel);
                }
            }

            return isValidTransition;
        }

        private ChannelMessageStatus getLatestMessageStatus() {
            if(!messageStatusesForChannel.isEmpty()) {
                return messageStatusesForChannel.get(messageStatusesForChannel.size() - 1);
            } else {
                return null;
            }
        }

        private List<ChannelMessageStatus> getMessageStatusHistoryForChannel() {
            return messageStatusesForChannel;
        }

    }


}
