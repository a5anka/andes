/*
* Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/
package org.wso2.andes.kernel;

import java.util.EnumSet;
import java.util.List;

/**
 * Message status to keep track in which state message is
 */
public enum MessageStatus {

    /**
     * Message has been read from store
     */
    READ(1),

    /**
     * Message has been buffered for delivery
     */
    BUFFERED(2),

    /**
     * Message has been added to the final async delivery queue (deliverAsynchronously method has been called for
     * the message.)
     */
    SCHEDULED_TO_SEND(3),

    /**
     * In a topic scenario, message has been sent to all subscribers
     */
    SENT_TO_ALL(4),

    /**
     * In a topic scenario, all subscribed consumers have acknowledged receipt of message
     */
    ACKED_BY_ALL(5),


    /**
     * All messages of the slot containing this message have been handled successfully, causing it to be removed
     */
    SLOT_REMOVED(6),

    /**
     * Message has expired (JMS Expiration duration sent with the message has passed)
     */
    EXPIRED(7),

    /**
     * Message is moved to the DLC queue
     */
    DLC_MESSAGE(8),

    /**
     * Message has been cleared from delivery due to a queue purge event.
     */
    PURGED(9),

    /**
     * Message is deleted from the store
     */
    DELETED(10),

    /**
     * Slot of the message is returned back to the coordinator, causing message to remove from memory
     */
    SLOT_RETURNED(11);


    private int code;

    //keep next possible states
    private EnumSet<MessageStatus> next;

    //keep previous possible states
    private EnumSet<MessageStatus> previous;

    /**
     * Define a message state
     *
     * @param code integer representing state
     */
    MessageStatus(int code) {
        this.code = code;
    }

    /**
     * Get code of the state
     *
     * @return integer representing state
     */
    public int getCode() {
        return code;
    }

    /**
     * Check if submitted state is an allowed state as per state model
     *
     * @param nextState suggested next state to transit
     * @return if transition is valid
     */
    public boolean isValidNextTransition(MessageStatus nextState) {
        return next.contains(nextState);
    }

    /**
     * Check if submitted state is an allowed state as per state model
     *
     * @param previousState suggested next state to transit
     * @return if transition is valid
     */
    public boolean isValidPreviousState(MessageStatus previousState) {
        return previous.contains(previousState);
    }

    static MessageStatus parseMessageState(int state) {

        for (MessageStatus s : MessageStatus.values()) {
            if (s.code == state) {
                return s;
            }
        }

        throw new IllegalArgumentException("Invalid message state argument specified: " + state);
    }


    static {

        READ.next = EnumSet.of(BUFFERED);
        READ.previous = EnumSet.complementOf(EnumSet.allOf(MessageStatus.class));

        BUFFERED.next = EnumSet.of(SCHEDULED_TO_SEND);
        BUFFERED.previous = EnumSet.of(READ);

        SCHEDULED_TO_SEND.next = EnumSet.of(SENT_TO_ALL);
        SCHEDULED_TO_SEND.previous = EnumSet.of(BUFFERED);

        SENT_TO_ALL.next = EnumSet.of(ACKED_BY_ALL);
        SENT_TO_ALL.previous = EnumSet.of(SCHEDULED_TO_SEND);

        ACKED_BY_ALL.next = EnumSet.of(DELETED);
        ACKED_BY_ALL.previous = EnumSet.of(SENT_TO_ALL);

        EXPIRED.next = EnumSet.of(DELETED);
        EXPIRED.previous = EnumSet.allOf(MessageStatus.class);

        DLC_MESSAGE.next = EnumSet.of(BUFFERED, DELETED);
        DLC_MESSAGE.previous = EnumSet.of(SENT_TO_ALL);

        PURGED.next = EnumSet.of(DELETED);
        PURGED.previous = EnumSet.allOf(MessageStatus.class);

        DELETED.next = EnumSet.of(SLOT_REMOVED);
        DELETED.previous = EnumSet.of(EXPIRED, DLC_MESSAGE, PURGED);

        SLOT_REMOVED.next = EnumSet.complementOf(EnumSet.allOf(MessageStatus.class));
        SLOT_REMOVED.previous = EnumSet.of(DELETED);

        SLOT_RETURNED.next = EnumSet.complementOf(EnumSet.allOf(MessageStatus.class));
        SLOT_RETURNED.previous = EnumSet.allOf(MessageStatus.class);

    }

    /**
     * Is OK to remove tracking message
     *
     * @return eligibility to remove
     */
    public static boolean isOKToRemove(List<MessageStatus> messageStatus) {
        return (messageStatus.contains
                (MessageStatus.ACKED_BY_ALL)
                || messageStatus.contains(MessageStatus.EXPIRED)
                || messageStatus.contains(MessageStatus.DLC_MESSAGE)
                || messageStatus.contains(MessageStatus.PURGED)
                || messageStatus.contains(MessageStatus.DELETED))
                || messageStatus.get(messageStatus.size() - 1).equals(MessageStatus.SLOT_REMOVED);
    }
}
