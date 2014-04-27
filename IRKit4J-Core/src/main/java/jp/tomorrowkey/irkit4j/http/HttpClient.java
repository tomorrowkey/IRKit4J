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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Closeables;
import com.google.common.net.HttpHeaders;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

    HttpURLConnection httpURLConnection;

    public HttpClient() {
    }

    public HttpResponse request(HttpRequest httpRequest) throws IOException {
        URL url = new URL(httpRequest.getUrl());
        HttpRequestMethod method = httpRequest.getHttpRequestMethod();
        String encoding = httpRequest.getEncoding();
        httpURLConnection = newHttpURLConnection(url, method, encoding);

        String content = httpRequest.getContent();
        if (content == null) {
            content = HttpParameter.encodeParameters(httpRequest.getParameters(), encoding);
        }
        if (method == HttpRequestMethod.POST) {
            httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.length()));
        }
        return request(content, encoding);
    }

    @VisibleForTesting
    HttpResponse request(String content, String encoding) throws IOException {
        httpURLConnection.connect();
        if (httpURLConnection.getDoOutput()) {
            byte[] rawBytes = content.getBytes(encoding);
            write(httpURLConnection.getOutputStream(), rawBytes);
        }
        return new HttpResponse(httpURLConnection);
    }

    @VisibleForTesting
    void write(OutputStream outputStream, byte[] content) throws IOException {
        try {
            outputStream.write(content);
            outputStream.flush();
        } finally {
            Closeables.close(outputStream, true);
        }
    }

    @VisibleForTesting
    HttpURLConnection newHttpURLConnection(URL url, HttpRequestMethod method, String encoding) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(method == HttpRequestMethod.POST);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod(method.toString());
        httpURLConnection.setRequestProperty(HttpHeaders.ACCEPT_CHARSET, encoding);
        if (method == HttpRequestMethod.POST) {
            httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=" + encoding);
        }
        return httpURLConnection;
    }

    public void disconnect() {
        if (httpURLConnection == null)
            return;

        httpURLConnection.disconnect();
    }
}
