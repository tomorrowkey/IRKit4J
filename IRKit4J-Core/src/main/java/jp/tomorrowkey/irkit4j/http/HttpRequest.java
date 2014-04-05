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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpRequest {

    private static final String DEFAULT_ENCODING = "utf-8";

    private String url;

    private HttpRequestMethod mHttpRequestMethod;

    private List<HttpParameter> parameterList;

    private String content;

    private String encoding;

    public HttpRequest(HttpRequestMethod httpRequestMethod, String url, String content) {
        this(httpRequestMethod, url, DEFAULT_ENCODING, content);
    }

    public HttpRequest(HttpRequestMethod httpRequestMethod, String url, String encoding, String content) {
        if (httpRequestMethod == null)
            throw new IllegalArgumentException("httpRequestMethod must not be null");
        if (Strings.isNullOrEmpty(url))
            throw new IllegalArgumentException("url must not be null or empty");
        if (Strings.isNullOrEmpty(encoding))
            throw new IllegalArgumentException("encoding must not be null or empty");
        if (Strings.isNullOrEmpty(content))
            throw new IllegalArgumentException("content must not be null or empty");

        this.mHttpRequestMethod = httpRequestMethod;
        this.url = url;
        this.encoding = encoding;
        this.content = content;
        this.parameterList = new ArrayList<HttpParameter>();
    }

    public HttpRequest(HttpRequestMethod httpRequestMethod, String url, HttpParameter... parameters) throws UnsupportedEncodingException {
        this(httpRequestMethod, url, DEFAULT_ENCODING, parameters);
    }

    public HttpRequest(HttpRequestMethod httpRequestMethod, String url, String encoding, HttpParameter... parameters) throws UnsupportedEncodingException {
        if (httpRequestMethod == null)
            throw new IllegalArgumentException("httpRequestMethod must not be null");
        if (Strings.isNullOrEmpty(url))
            throw new IllegalArgumentException("url must not be null or empty");
        if (Strings.isNullOrEmpty(encoding))
            throw new IllegalArgumentException("encoding must not be null or empty");
        if (parameters == null)
            throw new IllegalArgumentException("parameters must not be null");

        this.mHttpRequestMethod = httpRequestMethod;
        this.url = url;
        this.encoding = encoding;
        this.parameterList = new ArrayList<HttpParameter>(Arrays.asList(parameters));

        if (this.mHttpRequestMethod == HttpRequestMethod.GET) {
            this.url = this.url + "?" + HttpParameter.encodeParameters(getParameters(), this.encoding);
        }
    }

    public HttpRequestMethod getHttpRequestMethod() {
        return mHttpRequestMethod;
    }

    public String getUrl() {
        return url;
    }

    public HttpParameter[] getParameters() {
        return parameterList.toArray(new HttpParameter[parameterList.size()]);
    }

    public void addParameters(HttpParameter... parameters) {
        if (parameters == null)
            throw new IllegalArgumentException("parameters must not be null");

        for (HttpParameter parameter : parameters) {
            addParameter(parameter);
        }
    }

    public void addParameter(HttpParameter parameter) {
        if (parameter == null)
            throw new IllegalArgumentException("parameter must not be null");

        parameterList.add(parameter);
    }

    public String getEncoding() {
        return encoding;
    }

    public String content() {
        return content;
    }
}
