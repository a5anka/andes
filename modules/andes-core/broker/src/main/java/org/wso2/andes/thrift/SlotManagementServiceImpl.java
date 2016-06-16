/*
 * Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.andes.thrift;

import org.apache.thrift.TException;
import org.wso2.andes.kernel.AndesContext;
import org.wso2.andes.kernel.AndesException;
import org.wso2.andes.kernel.slot.Slot;
import org.wso2.andes.kernel.slot.SlotData;
import org.wso2.andes.kernel.slot.SlotManagerClusterMode;
import org.wso2.andes.kernel.slot.SlotPartData;
import org.wso2.andes.thrift.slot.gen.SlotDataHolder;
import org.wso2.andes.thrift.slot.gen.SlotInfo;
import org.wso2.andes.thrift.slot.gen.SlotManagementService;
import org.wso2.andes.thrift.slot.gen.SlotPart;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is the implementation of SlotManagementService interface. This class contains operations
 * does on slots through slot manager.When thrift client calls the services on
 * SlotManagementService interface, methods in this class will be triggered.
 */

public class SlotManagementServiceImpl implements SlotManagementService.Iface {

    private static SlotManagerClusterMode slotManager = SlotManagerClusterMode.getInstance();

    @Override
    public SlotInfo getSlotInfo(String queueName, String nodeId) throws TException {
        if (AndesContext.getInstance().getClusterAgent().isCoordinator()) {
            SlotInfo slotInfo = new SlotInfo();
            try {
                Slot slot = slotManager.getSlot(queueName, nodeId);
                if (null != slot) {
                    slotInfo = new SlotInfo(slot.getStartMessageId(), slot.getEndMessageId(),
                            slot.getStorageQueueName(), nodeId, slot.isAnOverlappingSlot());
                }
            } catch (AndesException e) {
                throw new TException("Failed to get slot info for queue: " + queueName + " nodeId: " + nodeId, e);
            }
            return slotInfo;
        } else {
            throw new TException("This node is not the slot coordinator right now");
        }
    }

    @Override
    public SlotDataHolder getSlotId(String queueName, String nodeId) throws TException {
        SlotDataHolder slotDataHolder;

        if (AndesContext.getInstance().getClusterAgent().isCoordinator()) {
            try {
                SlotData slotFromManager = slotManager.getSlotId(queueName, nodeId);

                List<SlotPartData> partDataList = slotFromManager.getPartDataList();
                List<SlotPart> convertedSlotPartList = partDataList
                        .stream()
                        .map(a -> new SlotPart(a.getInstanceId(), a.getPartId()))
                        .collect(Collectors.toList());

                slotDataHolder = new SlotDataHolder(slotFromManager.getSlotId(), convertedSlotPartList);
            } catch (AndesException e) {
                throw new TException("Failed to get slot info for queue: " + queueName + " nodeId: " + nodeId, e);
            }
        } else {
            throw new TException("This node is not the slot coordinator right now");
        }
        return slotDataHolder;
    }

    @Override
    public void updateMessageId(String queueName, String nodeId, long startMessageId, long endMessageId, long localSafeZone) throws TException {
        if (AndesContext.getInstance().getClusterAgent().isCoordinator()) {
            try {
                slotManager.updateMessageID(queueName, nodeId, startMessageId, endMessageId, localSafeZone);
            } catch (AndesException e) {
                throw new TException("Failed to update message id for queue: " + queueName + " nodeId: " + nodeId, e);
            }
        } else {
            throw new TException("This node is not the slot coordinator right now");
        }
    }

    @Override
    public boolean deleteSlot(String queueName, long slotInfo, String nodeId) throws TException {
        if (AndesContext.getInstance().getClusterAgent().isCoordinator()) {
            boolean result;
            try {
                result = slotManager.deleteSlot(queueName, slotInfo, nodeId);
            } catch (AndesException e) {
                throw new TException("Failed to delete slot for queue:" + queueName, e);
            }
            return result;
        } else {
            throw new TException("This node is not the slot coordinator right now");
        }
    }

    @Override
    public void reAssignSlotWhenNoSubscribers(String nodeId, String queueName) throws TException {
        if (AndesContext.getInstance().getClusterAgent().isCoordinator()) {
            try {
                slotManager.reAssignSlotWhenNoSubscribers(nodeId, queueName);
            } catch (AndesException e) {
                throw new TException("Failed to reAssign slot for node:" + nodeId + " queue:"
                                     + queueName, e);
            }
        } else {
            throw new TException("This node is not the slot coordinator right now");
        }
    }

    @Override
    public long updateCurrentMessageIdForSafeZone(long messageId, String nodeId) throws TException {
        long slotDeletionSafeZone;
        if (AndesContext.getInstance().getClusterAgent().isCoordinator()) {
            slotDeletionSafeZone = slotManager.updateAndReturnSlotDeleteSafeZone(nodeId,messageId);
        } else {
            throw new TException("This node is not the slot coordinator right now");
        }
        return slotDeletionSafeZone;
    }

    @Override
    public void clearAllActiveSlotRelationsToQueue(String queueName) throws TException {
        if (AndesContext.getInstance().getClusterAgent().isCoordinator()) {
            try {
                slotManager.clearAllActiveSlotRelationsToQueue(queueName);
            } catch (AndesException e) {
                throw new TException("Failed to clear active slots for queue:" + queueName, e);
            }
        } else {
            throw new TException("This node is not the slot coordinator right now");
        }
    }

}
