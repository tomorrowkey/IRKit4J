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

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Message {

    String format;

    @SerializedName(value = "freq")
    int frequency;

    int[] data;

    public Message(String format, int frequency, int[] data) {
        if (Strings.isNullOrEmpty(format))
            throw new IllegalArgumentException("format must not be null or empty");
        if (data == null || data.length == 0)
            throw new IllegalArgumentException("data must not be null or empty");

        this.format = format;
        this.frequency = frequency;
        this.data = data;
    }

    public String getFormat() {
        return format;
    }

    public int getFrequency() {
        return frequency;
    }

    public int[] getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (frequency != message.frequency) return false;
        if (!Arrays.equals(data, message.data)) return false;
        if (format != null ? !format.equals(message.format) : message.format != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = format != null ? format.hashCode() : 0;
        result = 31 * result + frequency;
        result = 31 * result + (data != null ? Arrays.hashCode(data) : 0);
        return result;
    }
}
