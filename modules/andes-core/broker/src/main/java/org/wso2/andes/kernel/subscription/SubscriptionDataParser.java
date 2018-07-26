/*
 * Copyright (c) 2018, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.andes.kernel.subscription;

import org.apache.commons.codec.binary.Base64;

/**
 * Parses subscription data string into a subscription data fields.
 */
public class SubscriptionDataParser {

    private String subscriptionId;

    private String storageQueueName;

    private String protocolType;

    private boolean isActive;

    private String decodedConnectionInfo;

    public SubscriptionDataParser(String encodedData) {
        String[] propertyToken = encodedData.split(",");

        for (String pt : propertyToken) {
            String[] tokens = pt.split("=", 2);
            switch (tokens[0]) {
                case "subscriptionId":
                    this.subscriptionId = tokens[1];
                    break;
                case "storageQueue":
                    this.storageQueueName = tokens[1];
                    break;
                case "protocolType":
                    this.protocolType = tokens[1];
                    break;
                case "isActive":
                    this.isActive = Boolean.parseBoolean(tokens[1]);
                    break;
                case "subscriberConnection":
                    byte[] decodedBytes = Base64.decodeBase64(tokens[1].getBytes());
                    this.decodedConnectionInfo = new String(decodedBytes);
                    break;
                default:
                    if (tokens[0].trim().length() > 0) {
                        throw new UnsupportedOperationException("Unexpected token " + tokens[0]);
                    }
                    break;
            }
        }
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public String getStorageQueueName() {
        return storageQueueName;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getDecodedConnectionInfo() {
        return decodedConnectionInfo;
    }
}
