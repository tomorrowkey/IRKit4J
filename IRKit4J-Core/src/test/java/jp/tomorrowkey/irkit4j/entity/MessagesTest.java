package jp.tomorrowkey.irkit4j.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessagesTest {
    @Test
    public void constructor() {
        {
            Message message = new Message("format", 1, new int[]{1, 2, 3, 4, 5});
            String hostname = "something";
            String deviceId = "deviceId";
            String clientKey = "clientKey";
            Messages messages = new Messages(message, hostname, deviceId, clientKey);

            assertThat(messages.message, is(message));
            assertThat(messages.hostname, is(hostname));
            assertThat(messages.deviceId, is(deviceId));
            assertThat(messages.clientKey, is(clientKey));
        }
    }
}
