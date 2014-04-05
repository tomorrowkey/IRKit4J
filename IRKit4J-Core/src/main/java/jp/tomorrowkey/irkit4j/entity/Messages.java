/**
 * Copyright 2014 tomorrowkey
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.tomorrowkey.irkit4j.entity;

import com.google.gson.annotations.SerializedName;

public class Messages {

    Message message;

    String hostname;

    @SerializedName(value = "deviceid")
    String deviceId;

    @SerializedName(value = "clientkey")
    String clientKey;

    public Messages() {
    }

    public Messages(Message message, String hostname, String deviceId, String clientKey) {
        this.message = message;
        this.hostname = hostname;
        this.deviceId = deviceId;
        this.clientKey = clientKey;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "message=" + message +
                ", hostname='" + hostname + '\'' +
                ", deviceId=" + deviceId +
                ", clientKey=" + clientKey +
                '}';
    }


}
