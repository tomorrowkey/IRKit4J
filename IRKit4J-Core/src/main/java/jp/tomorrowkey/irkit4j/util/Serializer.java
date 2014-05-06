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

package jp.tomorrowkey.irkit4j.util;

public class Serializer {

    public static enum WifiSecurity {
        WPA_WPA2(8), WEP(2), NONE(0);

        private int value;

        private WifiSecurity(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static WifiSecurity valueOf(int value) {
            WifiSecurity[] wifiSecurities = WifiSecurity.values();
            for (WifiSecurity wifiSecurity : wifiSecurities) {
                if (wifiSecurity.getValue() == value)
                    return wifiSecurity;
            }

            throw new IllegalArgumentException("Unknown value, value=" + value);
        }
    }

    private static final int CRC8POLY = 0x31; // = X^8+X^5+X^4+X^0

    static int crc8(String data, int size) {
        return crc8(data, size, 0x00);
    }

    static int crc8(String data, int size, int crc) {
        int length = data.length();
        for (int i = 0; i < size; i++) {
            if (i < length) {
                crc = crc8(data.charAt(i), crc);
            } else {
                crc = crc8(0x00, crc);
            }
        }
        return crc;
    }

    static int crc8(int data) {
        return crc8(data, 0x00);
    }

    static int crc8(int data, int crc) {
        if (data > 0xff)
            throw new IllegalArgumentException("data must be less than 0xff");

        crc ^= data;
        for (int i = 0; i < 8; i++) {
            if ((crc & 0x80) > 0) {
                crc = (crc << 1) ^ CRC8POLY;
            } else {
                crc <<= 1;
            }
        }
        crc &= 0xFF;

        return crc;
    }

    static String serializeWifiSecurity(WifiSecurity wifiSecurity) {
        return String.valueOf(wifiSecurity.getValue());
    }

    static String serializeSSID(String ssid) {
        StringBuilder sb = new StringBuilder();
        int length = ssid.length();
        for (int i = 0; i < length; i++) {
            char c = ssid.charAt(i);
            String str = Integer.toHexString(c).toUpperCase();
            sb.append(str);
        }
        return sb.toString();
    }

    static String serializePassword(String password) {
        StringBuilder sb = new StringBuilder();
        int length = password.length();
        for (int i = 0; i < length; i++) {
            char c = password.charAt(i);
            String str = Integer.toHexString(c).toUpperCase();
            sb.append(str);
        }
        return sb.toString();
    }

    static String serializeWEPPassword(String password) {
        StringBuilder sb = new StringBuilder();
        int length = password.length();
        for (int i = 0; i < length; i++) {
            char c = password.charAt(i);
            String str = Integer.toHexString(c).toUpperCase();
            sb.append(str);
        }
        return sb.toString();
    }

    static String serializeDevicekey(String devicekey) {
        return devicekey;
    }

    static String serializeCRC(WifiSecurity wifiSecurity, String ssid, String password, String devicekey) {
        int crc;
        crc = crc8(wifiSecurity.getValue());
        crc = crc8(ssid, 33, crc);
        crc = crc8(password, 64, crc);
        crc = crc8(1, crc); // wifi_is_set
        crc = crc8(0, crc); // wifi_was_valid
        crc = crc8(devicekey, 33, crc);
        return Integer.toHexString(crc).toUpperCase();
    }

    public static String serialize(WifiSecurity wifiSecurity, String ssid, String password, String devicekey) {
        StringBuilder sb = new StringBuilder();
        sb.append(serializeWifiSecurity(wifiSecurity)).append("/");
        sb.append(serializeSSID(ssid)).append("/");
        if (wifiSecurity == WifiSecurity.WEP) {
            sb.append(serializeWEPPassword(password)).append("/");
        } else {
            sb.append(serializePassword(password)).append("/");
        }
        sb.append(serializeDevicekey(devicekey)).append("/");
        sb.append("2").append("/");
        sb.append("").append("/");
        sb.append("").append("/");
        sb.append("").append("/");
        sb.append("").append("/");
        sb.append("").append("/");
        sb.append(serializeCRC(wifiSecurity, ssid, password, devicekey));
        return sb.toString();
    }


}
