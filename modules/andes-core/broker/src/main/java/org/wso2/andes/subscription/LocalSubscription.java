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
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.andes.subscription;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.andes.configuration.AndesConfigurationManager;
import org.wso2.andes.configuration.enums.AndesConfiguration;
import org.wso2.andes.kernel.*;
import org.wso2.andes.mqtt.MQTTLocalSubscription;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This is the class that represents a subscription in andes kernel. It is responsible
 * for handling both inbound (protocol > kernel) and outbound (kernel > protocol) subscription
 * events. For handling outbound events it keeps a OutboundSubscription object and forward
 * requests
 */
public class LocalSubscription  extends BasicSubscription implements InboundSubscription{

    private static Log log = LogFactory.getLog(LocalSubscription.class);

    /**
     * Outbound subscription reference. We forward outbound events to this object. Get its response
     * and act upon (make kernel side changes)
     */
    private OutboundSubscription subscription;

    /**
     * Map to track messages being sent <message id, MsgData reference>
     */
    private final ConcurrentHashMap<Long, DeliverableAndesMetadata> messageSendingTracker
            = new ConcurrentHashMap<>();

    /**
     * Count sent but not acknowledged message count for channel of the subscriber
     */
    private AtomicInteger unAcknowledgedMsgCount = new AtomicInteger(0);

    private Integer maxNumberOfUnAcknowledgedMessages = 100000;

    /**
     * Create a new local subscription object in andes kernel
     * @param subscription protocol subscription to send messages
     * @param subscriptionID ID of the subscription (unique)
     * @param destination subscription bound destination
     * @param isBoundToTopic true if subscription is a topic subscription (durable/non-durable)
     * @param isExclusive true is the subscription is exclusive
     * @param isDurable true if subscription is durable (should preserve messages in absence)
     * @param subscribedNode identifier of the node actual subscription exists
     * @param subscribeTime timestamp of the subscription made
     * @param targetQueue name of underlying queue subscription is bound to
     * @param targetQueueOwner name of the owner of underlying queue subscription is bound to
     * @param targetQueueBoundExchange name of exchange of underlying queue subscription is bound to
     * @param targetQueueBoundExchangeType type of exchange of underlying queue subscription is bound to
     * @param isTargetQueueBoundExchangeAutoDeletable is queue subscription is bound to is auto deletable (this can
     *                                                be true if subscription is non durable)
     * @param hasExternalSubscriptions true if subscription is active (has a live TCP connection)
     */
    public LocalSubscription(OutboundSubscription subscription, String subscriptionID, String destination,
                             boolean isBoundToTopic, boolean isExclusive, boolean isDurable,
                             String subscribedNode, long subscribeTime, String targetQueue, String targetQueueOwner, String targetQueueBoundExchange,
                             String targetQueueBoundExchangeType, Short isTargetQueueBoundExchangeAutoDeletable,
                             boolean hasExternalSubscriptions) {

        super(subscriptionID, destination, isBoundToTopic, isExclusive, isDurable, subscribedNode, subscribeTime,
                targetQueue, targetQueueOwner, targetQueueBoundExchange, targetQueueBoundExchangeType,
                isTargetQueueBoundExchangeAutoDeletable, hasExternalSubscriptions);

        this.subscription = subscription;

        //We need to keep the subscriptionType in basic subscription for notification
        if(subscription instanceof AMQPLocalSubscription) {
            setSubscriptionType(SubscriptionType.AMQP);
        } else if(subscription instanceof MQTTLocalSubscription) {
            setSubscriptionType(SubscriptionType.MQTT);
        }

        this.maxNumberOfUnAcknowledgedMessages = AndesConfigurationManager.readValue
                (AndesConfiguration.PERFORMANCE_TUNING_ACK_HANDLING_MAX_UNACKED_MESSAGES);

    }


    /**
     * Send message to the underlying protocol subscriber
     * @param messageMetadata metadata of the message
     * @param content content of the message
     * @return true if the send is a success
     * @throws AndesException
     */
    public boolean sendMessageToSubscriber(DeliverableAndesMetadata messageMetadata, AndesContent content) throws
            AndesException {
        boolean sendSuccess = subscription.sendMessageToSubscriber(messageMetadata, content);
        if(sendSuccess) {
            addMessageToSendingTracker(messageMetadata);
            unAcknowledgedMsgCount.incrementAndGet();
            return true;
        } else {
            // Move message to DLC
            // All the Queues and Durable Topics related messages are adding to DLC
            messageMetadata.markDeliveryFailureOfASentMessage(getChannelID());
            if ((!isBoundToTopic) || isDurable){
                String destinationQueue = messageMetadata.getDestination();
                MessagingEngine.getInstance().moveMessageToDeadLetterChannel(messageMetadata.getMessageID(), destinationQueue);
            } else { //for topic messages we forget that the message is sent to that subscriber
                log.warn("Delivery rule evaluation failed. Forgetting message id= " + messageMetadata.getMessageID()
                        + " for subscriber " + subscriptionID);
                messageMetadata.removeScheduledDeliveryChannel(getChannelID());
            }
            return false;
        }
    }

    public DeliverableAndesMetadata getMessageByMessageID(long messageID) {
        DeliverableAndesMetadata metadata =  messageSendingTracker.get(messageID);
        if(null == metadata) {
            log.error("Message reference has been already cleared for message id "
                    + messageID + ". Acknowledge or Nak is already received");
        }
        return metadata;
    }

    /**
     * Is the underlying protocol subscription active and can accept
     * messages
     * @return true if subscription is active
     */
    public boolean isActive() {
        return subscription.isActive();
    }


    /**
     * ID of the protocol channel this subscription holds
     * @return unique id if the subscription channel
     */
    public UUID getChannelID() {
        return subscription.getChannelID();
    }

    /**
     * Check if this subscription has ability to accept messages
     * If pending ack count is high it does not have ability to accept new messages
     * @return true if able to accept messages
     */
    public boolean hasRoomToAcceptMessages() {
        int notAcknowledgedMsgCount = unAcknowledgedMsgCount.get();
        if (notAcknowledgedMsgCount < maxNumberOfUnAcknowledgedMessages) {
            return true;
        } else {

            if (log.isDebugEnabled()) {
                log.debug(
                        "Not selected. Too much pending acks, subscription = " + this + " pending count =" +
                                (notAcknowledgedMsgCount));
            }

            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void ackReceived(long messageID) throws AndesException{
        messageSendingTracker.remove(messageID);
        unAcknowledgedMsgCount.decrementAndGet();
    }

    /**
     * {@inheritDoc}
     */
    public void msgRejectReceived(long messageID) throws AndesException{
        messageSendingTracker.remove(messageID);
        unAcknowledgedMsgCount.decrementAndGet();
    }

    /**
     * {@inheritDoc}
     */
    public void close() throws AndesException {
        List<DeliverableAndesMetadata> messagesToRemove = new ArrayList<>();

        for (DeliverableAndesMetadata andesMetadata : messageSendingTracker.values()) {
            andesMetadata.removeScheduledDeliveryChannel(getChannelID());

            //TODO: decide if we need to do this only for topics
            //for topic messages see if we can delete the message
            if((!andesMetadata.isOKToDispose()) && (andesMetadata.isTopic())) {
                if(andesMetadata.getLatestState().equals(MessageStatus.ACKED_BY_ALL)) {
                     messagesToRemove.add(andesMetadata);
                }
            }
        }

        messageSendingTracker.clear();
        unAcknowledgedMsgCount.set(0);
        MessagingEngine.getInstance().deleteMessages(messagesToRemove, false);
    }

    /**
     * Add message to sending tracker which keeps messages delivered to this channel
     * @param messageData message to add
     */
    private void addMessageToSendingTracker(DeliverableAndesMetadata messageData) {

        if (log.isDebugEnabled()) {
            log.debug("Adding message to sending tracker channel id = " + getChannelID() + " message id = "
                    + messageData.getMessageID());
        }

        DeliverableAndesMetadata messageDataToAdd = messageSendingTracker.get(messageData.getMessageID());

        if (null == messageDataToAdd) {
            messageSendingTracker.put(messageData.getMessageID(), messageData);
        }
    }

    public boolean equals(Object o) {
        LocalSubscription c = (LocalSubscription) o;
        if (this.subscriptionID.equals(c.subscriptionID) &&
                this.getSubscribedNode().equals(c.getSubscribedNode()) &&
                this.targetQueue.equals(c.targetQueue) &&
                this.targetQueueBoundExchange.equals(c.targetQueueBoundExchange)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(subscriptionID).
                append(getSubscribedNode()).
                append(targetQueue).
                append(targetQueueBoundExchange).
                toHashCode();
    }
}
