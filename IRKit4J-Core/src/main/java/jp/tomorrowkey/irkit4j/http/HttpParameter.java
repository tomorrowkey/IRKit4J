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
import java.net.URLEncoder;

public class HttpParameter {

    private String key;

    private String value;

    public HttpParameter(String key, String value) {
        if (Strings.isNullOrEmpty(key))
            throw new IllegalArgumentException("key must not be null or empty");
        if (Strings.isNullOrEmpty(value))
            throw new IllegalArgumentException("value must not be null or empty");

        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getEncodedKey(String encoding) throws UnsupportedEncodingException {
        if (Strings.isNullOrEmpty(encoding))
            throw new IllegalArgumentException("encoding must not be null or empty");

        return encode(key, encoding);
    }

    public String getEncodedValue(String encoding) throws UnsupportedEncodingException {
        if (Strings.isNullOrEmpty(encoding))
            throw new IllegalArgumentException("encoding must not be null or empty");

        return encode(value, encoding);
    }

    private static String encode(String value, String encoding) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, encoding);
    }

    public static String encodeParameters(HttpParameter[] parameters, String encoding) throws UnsupportedEncodingException {
        StringBuilder buffer = new StringBuilder();
        boolean isFirst = true;
        for (HttpParameter parameter : parameters) {
            if (isFirst) {
                isFirst = false;
            } else {
                buffer.append("&");
            }

            String key = parameter.getEncodedKey(encoding);
            String value = parameter.getEncodedValue(encoding);
            buffer.append(key).append("=").append(value);
        }
        return buffer.toString();
    }
}
