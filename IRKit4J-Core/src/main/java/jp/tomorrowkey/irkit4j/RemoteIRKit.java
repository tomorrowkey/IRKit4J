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
import jp.tomorrowkey.irkit4j.entity.Messages;
import jp.tomorrowkey.irkit4j.http.*;
import jp.tomorrowkey.irkit4j.util.StringKeyValue;

import java.io.IOException;

public class RemoteIRKit {

    private static final String BASE_URL = "https://api.getirkit.com/1";

    private static final Gson GSON = new Gson();

    private RemoteIRKit() {
    }

    public static Messages getMessages(String clientKey, boolean clear) throws IOException {
        return getMessages(clientKey, clear, new HttpClient());
    }

    @VisibleForTesting
    static Messages getMessages(String clientKey, boolean clear, HttpClient httpClient) throws IOException {
        if (Strings.isNullOrEmpty(clientKey))
            throw new IllegalArgumentException("clientKey must not be null");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String url = BASE_URL + "/messages";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.GET, url, new HttpParameter("clientkey", clientKey));
            if (clear)
                httpRequest.addParameters(new HttpParameter("clear", "1"));
            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                return GSON.fromJson(content, Messages.class);
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }

    public static void postMessage(String clientKey, String deviceId, String format, int frequency, int[] data) throws IOException {
        postMessage(clientKey, deviceId, new Message(format, frequency, data));
    }

    public static void postMessage(String clientKey, String deviceId, Message message) throws IOException {
        postMessage(clientKey, deviceId, message, new HttpClient());
    }

    static void postMessage(String clientKey, String deviceId, Message message, HttpClient httpClient) throws IOException {
        if (Strings.isNullOrEmpty(clientKey))
            throw new IllegalArgumentException("clientKey must not be null or empty");
        if (Strings.isNullOrEmpty(clientKey))
            throw new IllegalArgumentException("deviceId must not be null or empty");
        if (message == null)
            throw new IllegalArgumentException("message must not be null");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String messageAsJson = GSON.toJson(message);

            String url = BASE_URL + "/messages";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.POST, url,
                    new HttpParameter("clientkey", clientKey),
                    new HttpParameter("deviceid", deviceId),
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

    @VisibleForTesting
    public static String postClients(String apiKey) throws IOException {
        return postClients(apiKey, new HttpClient());
    }

    static String postClients(String apiKey, HttpClient httpClient) throws IOException {
        if (Strings.isNullOrEmpty(apiKey))
            throw new IllegalArgumentException("apiKey must not be null or empty");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String url = BASE_URL + "/clients";
            HttpRequest request = new HttpRequest(HttpRequestMethod.POST, url, new HttpParameter("apikey", apiKey));
            HttpResponse response = httpClient.request(request);
            int statusCode = response.getStatusCode();
            String content = response.getContent();
            if (statusCode == HttpStatusCode.OK) {
                StringKeyValue keyValue = GSON.fromJson(content, StringKeyValue.class);
                return keyValue.get("clientkey");
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }

    public static StringKeyValue postDevices(String clientKey) throws IOException {
        return postDevices(clientKey, new HttpClient());
    }

    static StringKeyValue postDevices(String clientKey, HttpClient httpClient) throws IOException {
        if (Strings.isNullOrEmpty(clientKey))
            throw new IllegalArgumentException("clientKey must not be null or empty");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String url = BASE_URL + "/devices";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.POST, url, new HttpParameter("clientkey", clientKey));
            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                return GSON.fromJson(content, StringKeyValue.class);
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }

    public static String postApps(String email) throws IOException {
        return postApps(email, new HttpClient());
    }

    @VisibleForTesting
    static String postApps(String email, HttpClient httpClient) throws IOException {
        if (Strings.isNullOrEmpty(email))
            throw new IllegalArgumentException("email must not be null or empty");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String url = BASE_URL + "/apps";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.POST, url, new HttpParameter("email", email));
            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                StringKeyValue keyValue = GSON.fromJson(content, StringKeyValue.class);
                return keyValue.get("message");
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }


    public static StringKeyValue postKeys(String clientToken) throws IOException {
        return postKeys(clientToken, new HttpClient());
    }

    @VisibleForTesting
    static StringKeyValue postKeys(String clientToken, HttpClient httpClient) throws IOException {
        if (Strings.isNullOrEmpty(clientToken))
            throw new IllegalArgumentException("clientToken must not be null or empty");
        if (httpClient == null)
            throw new IllegalArgumentException("httpClient must not be null");

        try {
            String url = BASE_URL + "/keys";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.POST, url, new HttpParameter("clienttoken", clientToken));
            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                return GSON.fromJson(content, StringKeyValue.class);
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }

    public static String postDoor(String clientKey, String deviceId) throws IOException {
        return postDoor(clientKey, deviceId, new HttpClient());
    }

    static String postDoor(String clientKey, String deviceId, HttpClient httpClient) throws IOException {
        if (Strings.isNullOrEmpty(clientKey))
            throw new IllegalAccessError("clientKey must not be null or empty");
        if (Strings.isNullOrEmpty(deviceId))
            throw new IllegalAccessError("deviceId must not be null or empty");
        if (httpClient == null)
            throw new IllegalAccessError("httpClient must not be null");

        try {
            String url = BASE_URL + "/door";
            HttpRequest httpRequest = new HttpRequest(HttpRequestMethod.POST, url,
                    new HttpParameter("clientkey", clientKey),
                    new HttpParameter("deviceid", deviceId)
            );
            HttpResponse httpResponse = httpClient.request(httpRequest);
            int statusCode = httpResponse.getStatusCode();
            String content = httpResponse.getContent();
            if (statusCode == HttpStatusCode.OK) {
                return GSON.fromJson(content, StringKeyValue.class).get("hostname");
            } else {
                throw new UnexpectedStatusCodeException(statusCode, content);
            }
        } finally {
            httpClient.disconnect();
        }
    }

}
