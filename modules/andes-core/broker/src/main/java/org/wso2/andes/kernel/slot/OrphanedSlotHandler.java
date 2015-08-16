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

package org.wso2.andes.kernel.slot;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.andes.kernel.AndesContext;
import org.wso2.andes.kernel.AndesException;
import org.wso2.andes.kernel.AndesKernelBoot;
import org.wso2.andes.kernel.AndesSubscription;
import org.wso2.andes.subscription.LocalSubscription;
import org.wso2.andes.kernel.MessagingEngine;
import org.wso2.andes.kernel.SubscriptionListener;
import org.wso2.andes.subscription.SubscriptionStore;

import java.util.Collection;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * This class will reassign the slots own by this node when its last subscriber leaves
 */
public class OrphanedSlotHandler implements SubscriptionListener {

    private static Log log = LogFactory.getLog(OrphanedSlotHandler.class);

    /**
     * Used for asynchronously execute slot reassign task
     */
    private final ExecutorService executor;

    public OrphanedSlotHandler() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("AndesReassignSlotTaskExecutor").build();
        executor = Executors.newSingleThreadExecutor(namedThreadFactory);
    }

    @Override
    public void handleClusterSubscriptionsChanged(AndesSubscription subscription, SubscriptionChange changeType)
            throws AndesException {
        //Cluster wise changes are not necessary
    }

    @Override
    public void handleLocalSubscriptionsChanged(LocalSubscription subscription, SubscriptionChange changeType)
            throws AndesException {
        switch (changeType) {
            case DELETED:
                reAssignSlotsIfNeeded(subscription);
                break;
            case DISCONNECTED:
                reAssignSlotsIfNeeded(subscription);
                break;
        }
    }

    /**
     * Re-assign slots back to the slot manager if this is the last subscriber of this node.
     *
     * @param subscription
     *         current subscription fo the leaving node
     * @throws AndesException
     */
    private void reAssignSlotsIfNeeded(AndesSubscription subscription) throws AndesException {
        if (subscription.isDurable()) {
            // Problem happens only with Queues
            SubscriptionStore subscriptionStore = AndesContext.getInstance().getSubscriptionStore();
            String destination = subscription.getSubscribedDestination();
            Collection<LocalSubscription> localSubscribersForQueue = subscriptionStore
                    .getActiveLocalSubscribers(destination, false);
            if (localSubscribersForQueue.size() == 0) {
                scheduleSlotToReassign(subscription.getStorageQueueName());
            }
        }
    }

    /**
     * Schedule to re-assign slots of the node related to a particular queue when last subscriber leaves
     *
     * @param storageQueue
     *         Name of the storageQueue
     */
    public void scheduleSlotToReassign(String storageQueue) {
        executor.submit(new SlotReAssignTask(storageQueue));
    }

    /**
     * This class is a scheduler class to schedule re-assignment of slots when last subscriber leaves a particular
     * queue
     */
    private class SlotReAssignTask extends TimerTask {

        /**
         * Storage queue handled by this task
         */
        private String storageQueue;

        /**
         * Manger used to notify of the stale storage queue
         */
        private SlotDeliveryWorkerManager slotDeliveryWorkerManager;

        public SlotReAssignTask(String storageQueue) {
            this.storageQueue = storageQueue;
            slotDeliveryWorkerManager = SlotDeliveryWorkerManager.getInstance();
        }

        public void run() {
            if (log.isDebugEnabled()) {
                log.debug("Trying to reAssign slots for queue " + storageQueue);
            }
            try {
                MessagingEngine.getInstance().getSlotCoordinator().reAssignSlotWhenNoSubscribers(storageQueue);

                if (log.isDebugEnabled()) {
                    log.debug("Re-assigned slots for queue: " + storageQueue);
                }

                //remove tracking when orphan slot situation only when node up and running
                if (!AndesKernelBoot.isKernelShuttingDown()) {
                    slotDeliveryWorkerManager.stopDeliveryForDestination(storageQueue);
                }

            } catch (ConnectionException e) {
                log.error("Error occurred while re-assigning the slot to slot manager", e);
            }
        }
    }
}
