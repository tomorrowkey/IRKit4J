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

package jp.tomorrowkey.irkit4j.http;

import com.google.common.base.Strings;
import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class HttpResponse {

    HttpURLConnection httpURLConnection;

    int statusCode;

    InputStream inputStream;

    String content;

    public HttpResponse(HttpURLConnection httpURLConnection) throws IOException {
        if (httpURLConnection == null)
            throw new IllegalArgumentException("httpURLConnection must not be null");

        this.httpURLConnection = httpURLConnection;
        this.statusCode = httpURLConnection.getResponseCode();

        inputStream = httpURLConnection.getErrorStream();
        if (inputStream == null)
            inputStream = httpURLConnection.getInputStream();
    }

    public int getStatusCode() throws IOException {
        return statusCode;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getContent() throws IOException {
        int contentLength = httpURLConnection.getContentLength();
        if (contentLength == 0) {
            return "";
        }

        if (Strings.isNullOrEmpty(content)) {
            content = CharStreams.toString(new InputStreamReader(getInputStream()));
        }
        return content;
    }

    public void disconnect() {
        httpURLConnection.disconnect();
    }
}
