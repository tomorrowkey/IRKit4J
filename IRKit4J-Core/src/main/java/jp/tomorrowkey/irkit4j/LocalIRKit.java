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

package jp.tomorrowkey.irkit4j;


import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import jp.tomorrowkey.irkit4j.entity.Message;
import jp.tomorrowkey.irkit4j.http.*;
import jp.tomorrowkey.irkit4j.util.Serializer;
import jp.tomorrowkey.irkit4j.util.StringKeyValue;

import java.io.IOException;
import java.net.InetAddress;

public class LocalIRKit {

    private static final Gson GSON = new Gson();

    private LocalIRKit() {
    }

    public static String getKeys(InetAddress inetAddress) throws IOException {
        return getKeys(inetAddress, new HttpClient());
    }

    @VisibleForTesting
    static String getKeys(InetAddress inetAddress, HttpClient httpClient) throws IOException {
        if (inetAddress == null)
            throw new IllegalArgumentException("inetAddress must not be null");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String url = "http://" + inetAddress.getHostName() + "/keys";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.POST, url);
            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                StringKeyValue keyValue = GSON.fromJson(content, StringKeyValue.class);
                return keyValue.get("clienttoken");
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }

    public static Message getMessage(InetAddress inetAddress) throws IOException {
        return getMessage(inetAddress, new HttpClient());
    }

    @VisibleForTesting
    static Message getMessage(InetAddress inetAddress, HttpClient httpClient) throws IOException {
        if (inetAddress == null)
            throw new IllegalArgumentException("inetAddress must not be null");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String url = "http://" + inetAddress.getHostName() + "/messages";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.GET, url);
            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                return GSON.fromJson(content, Message.class);
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }

    public static void postMessage(InetAddress inetAddress, String format, int frequency, int[] data) throws IOException {
        postMessage(inetAddress, format, frequency, data, new HttpClient());
    }

    public static void postMessage(InetAddress inetAddress, String format, int frequency, int[] data, HttpClient httpClient) throws IOException {
        postMessage(inetAddress, new Message(format, frequency, data), httpClient);
    }

    public static void postMessage(InetAddress inetAddress, Message message) throws IOException {
        postMessage(inetAddress, message, new HttpClient());
    }

    @VisibleForTesting
    static void postMessage(InetAddress inetAddress, Message message, HttpClient httpClient) throws IOException {
        if (inetAddress == null)
            throw new IllegalArgumentException("inetAddress must not be null");
        if (message == null)
            throw new IllegalArgumentException("message must not be null");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String messageAsJson = GSON.toJson(message);

            String url = "http://" + inetAddress.getHostName() + "/messages";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.POST, url,
                    new HttpParameter("message", messageAsJson));

            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                return;
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }

    public static void postWifi(InetAddress inetAddress, Serializer.WifiSecurity wifiSecurity, String ssid, String password, String deviceKey) throws IOException {
        postWifi(inetAddress, wifiSecurity, ssid, password, deviceKey, new HttpClient());
    }

    @VisibleForTesting
    static void postWifi(InetAddress inetAddress, Serializer.WifiSecurity wifiSecurity, String ssid, String password, String deviceKey, HttpClient httpClient) throws IOException {
        if (inetAddress == null)
            throw new IllegalArgumentException("inetAddress must not be null");
        if (wifiSecurity == null)
            throw new IllegalArgumentException("wifiSecurity must not be null");
        if (Strings.isNullOrEmpty(ssid))
            throw new IllegalArgumentException("ssid must not be null or empty");
        if (wifiSecurity != Serializer.WifiSecurity.NONE && Strings.isNullOrEmpty(password))
            throw new IllegalArgumentException("password must not be null or empty when wifi security is " + wifiSecurity.toString());
        if (Strings.isNullOrEmpty(deviceKey))
            throw new IllegalArgumentException("deviceKey must not be null or empty");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String token = Serializer.serialize(wifiSecurity, ssid, password, deviceKey);
            String url = "http://" + inetAddress.getHostName() + "/wifi";


            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.POST, url, token);

            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                return;
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }


}
