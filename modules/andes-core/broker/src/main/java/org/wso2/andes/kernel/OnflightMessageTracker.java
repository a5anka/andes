/*
 *
 *   Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 * /
 */

package org.wso2.andes.kernel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.andes.kernel.slot.Slot;
import org.wso2.andes.kernel.slot.SlotDeliveryWorker;
import org.wso2.andes.kernel.slot.SlotDeliveryWorkerManager;
import java.io.File;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class will track message delivery by broker
 * on the fly. Message delivery times, message status,
 * is tracked here
 */
public class OnflightMessageTracker {

    private static Log log = LogFactory.getLog(OnflightMessageTracker.class);

    private static OnflightMessageTracker instance;

    static {
        try {
            instance = new OnflightMessageTracker();
        } catch (AndesException e) {
            log.error("Error occurred when reading configurations : ", e);
        }
    }


    public static OnflightMessageTracker getInstance() {
        return instance;
    }

    /**
     * In memory map keeping sent message statistics by message id
     */
    private final ConcurrentHashMap<Long, DeliverableAndesMetadata> msgId2MsgData;

    /**
     * Map to keep track of message counts pending to read
     */
    private final ConcurrentHashMap<Slot, AtomicInteger> pendingMessagesBySlot = new
            ConcurrentHashMap<>();

    /**
     * Class to keep tracking data of a message
     */

    private OnflightMessageTracker() throws AndesException {

        // We don't know the size of the map at startup. hence using an arbitrary value of 16, Need to test
        // Load factor set to default value 0.75
        // Concurrency level set to 6. Currently SlotDeliveryWorker, AckHandler AckSubscription, DeliveryEventHandler,
        // MessageFlusher access this. To be on the safe side set to 6.
        msgId2MsgData = new ConcurrentHashMap<>(16, 0.75f, 6);

    }

    /**
     * Decrement message count in slot and if it is zero prepare for slot deletion
     *
     * @param slot Slot whose message count is decremented
     * @throws AndesException
     */
    public void decrementMessageCountInSlot(Slot slot)
            throws AndesException {
        AtomicInteger pendingMessageCount = pendingMessagesBySlot.get(slot);
        int messageCount = pendingMessageCount.decrementAndGet();
        if (messageCount == 0) {
            /*
            All the Acks for the slot has bee received. Check the slot again for unsend
            messages and if there are any send them and delete the slot.
             */
            SlotDeliveryWorker slotWorker = SlotDeliveryWorkerManager.getInstance()
                                                                     .getSlotWorker(slot.getStorageQueueName());
            if (log.isDebugEnabled()) {
                log.debug("Slot has no pending messages. Now re-checking slot for messages");
            }
            slot.setSlotInActive();
            slotWorker.deleteSlot(slot);
        }
    }

    /**
     * Increment the message count in a slot
     *
     * @param slot slot whose message counter should increment
     */
    public void incrementMessageCountInSlot(Slot slot, int amount) {
        AtomicInteger pendingMessageCount = pendingMessagesBySlot.get(slot);
        if (null == pendingMessageCount) {
            pendingMessageCount = new AtomicInteger();
            pendingMessagesBySlot.putIfAbsent(slot, pendingMessageCount);
        }
        pendingMessageCount.addAndGet(amount);
    }

    /**
     * Track that this message is buffered. Return true if eligible to buffer
     *
     * @param andesMessageMetadata metadata to buffer
     */
    public void addMessageToTracker(DeliverableAndesMetadata andesMessageMetadata) {
        long messageID = andesMessageMetadata.getMessageID();
        if (log.isDebugEnabled()) {
            log.debug("Adding message to tracker, id = " + messageID + " slot = ");
        }

        msgId2MsgData.put(messageID, andesMessageMetadata);
    }

    /**
     * Remove message from tracker.
     *
     * @param messageID
     *         Message ID of the message
     */
    public void removeMessageFromTracker(Long messageID) {
        msgId2MsgData.remove(messageID);
    }

    /**
     * Get message tracking object for a message. This contains
     * all delivery information and message status of the message
     *
     * @param messageID id of the message
     * @return tracking object for message
     */
    public DeliverableAndesMetadata getTrackingData(long messageID) {
        return msgId2MsgData.get(messageID);
    }

    /**
     * Permanently remove message from tacker. This will clear the tracking
     * that message is buffered and message is sent and also will remove
     * tracking object from memory
     *
     * @param messageID id of the message
     */
    public void stampMessageAsDLCAndRemoveFromTacking(long messageID) throws AndesException {
        //remove actual object from memory
        if (log.isDebugEnabled()) {
            log.debug("Removing all tracking of message id = " + messageID);
        }
        DeliverableAndesMetadata trackingData = msgId2MsgData.remove(messageID);
        Slot slot = trackingData.getSlot();

        //clear subscription tracking information in all delivered subscriptions
        for (UUID channelID : trackingData.getAllDeliveredChannels()) {
            LocalSubscription subscription = AndesContext.getInstance().getSubscriptionStore()
                    .getLocalSubscriptionForChannelId(channelID);
            if(null != subscription) {
                subscription.msgRejectReceived(messageID);
            }
        }

        decrementMessageCountInSlot(slot);
    }

    /**
     * Dump message info to a csv file
     *
     * @param fileToWrite file to dump info
     * @throws AndesException
     */
    public void dumpMessageStatusToFile(File fileToWrite) throws AndesException {

        for (Long messageID : msgId2MsgData.keySet()) {
            DeliverableAndesMetadata trackingData = msgId2MsgData.get(messageID);
            trackingData.dumpMessageStatusToFile(fileToWrite);
        }
    }
}
