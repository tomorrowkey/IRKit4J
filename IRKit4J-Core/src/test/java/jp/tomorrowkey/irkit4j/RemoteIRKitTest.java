package jp.tomorrowkey.irkit4j;

import com.google.gson.Gson;
import jp.tomorrowkey.irkit4j.entity.Message;
import jp.tomorrowkey.irkit4j.entity.Messages;
import jp.tomorrowkey.irkit4j.http.HttpClient;
import jp.tomorrowkey.irkit4j.http.HttpRequest;
import jp.tomorrowkey.irkit4j.http.HttpResponse;
import jp.tomorrowkey.irkit4j.http.HttpStatusCode;
import jp.tomorrowkey.irkit4j.util.StringKeyValue;
import jp.tomorrowkey.irkit4j.util.StringKeyValueTest;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
public class RemoteIRKitTest {

    private static final Gson GSON = new Gson();

    public static class GetMessagesTest {
        @Test
        public void getMessgesShouldReturnMessage() throws Exception {
            String format = "raw";
            int frequency = 38;
            int[] data = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            Message requestMessage = new Message(format, frequency, data);

            String hostName = "CCC";
            String deviceId = "AAA";
            String clientKey = "BBB";
            Messages requestMessages = new Messages(requestMessage, hostName, deviceId, clientKey);

            String json = GSON.toJson(requestMessages);

            HttpClient httpClient = mock(HttpClient.class);
            HttpResponse httpResponse = mock(HttpResponse.class);
            when(httpResponse.getStatusCode()).thenReturn(HttpStatusCode.OK);
            when(httpResponse.getContent()).thenReturn(json);
            when(httpClient.request(Mockito.notNull(HttpRequest.class))).thenReturn(httpResponse);

            boolean clear = true;

            Messages messages = RemoteIRKit.getMessages(clientKey, clear, httpClient);
            assertThat(messages, is(not(nullValue())));
            assertThat(messages.getClientKey(), is(clientKey));
            assertThat(messages.getDeviceId(), is(deviceId));
            assertThat(messages.getHostname(), is(hostName));

            Message message = messages.getMessage();
            assertThat(message, is(not(nullValue())));
            assertThat(message.getFormat(), is(not(nullValue())));
            assertThat(message.getFormat(), is(format));
            assertThat(message.getFrequency(), is(not(nullValue())));
            assertThat(message.getFrequency(), is(frequency));
            assertThat(message.getData(), is(not(nullValue())));
            assertThat(message.getData(), is(data));
        }
    }

    public static class AuthenticateTest {
        @Test(expected = IllegalArgumentException.class)
        public void authenticateThrowIllegalArgumentExceptionWhenClientTokenIsNull() throws Exception {
            HttpClient httpClient = mock(HttpClient.class);
            RemoteIRKit.postKeys(null, httpClient);
        }

        @Test
        public void authenticateShouldReturnDeviceIdAndClientKey() throws Exception {
            String clientToken = "AAA";
            String rawDeviceId = "BBB";
            String rawClientKey = "CCC";
            String keysResponse = String.format("{\"deviceid\":\"%s\",\"clientkey\":\"%s\"}", rawDeviceId, rawClientKey);

            HttpClient httpClient = mock(HttpClient.class);
            HttpResponse httpResponse = mock(HttpResponse.class);
            when(httpResponse.getStatusCode()).thenReturn(HttpStatusCode.OK);
            when(httpResponse.getContent()).thenReturn(keysResponse);
            when(httpClient.request(Mockito.notNull(HttpRequest.class))).thenReturn(httpResponse);

            StringKeyValue keyValue = RemoteIRKit.postKeys(clientToken, httpClient);
            assertThat(keyValue, is(not(nullValue())));
            assertThat(keyValue.get("deviceid"), is(not(nullValue())));
            assertThat(keyValue.get("deviceid"), is(rawDeviceId));
            assertThat(keyValue.get("clientkey"), is(not(nullValue())));
            assertThat(keyValue.get("clientkey"), is(rawClientKey));
        }
    }
}
