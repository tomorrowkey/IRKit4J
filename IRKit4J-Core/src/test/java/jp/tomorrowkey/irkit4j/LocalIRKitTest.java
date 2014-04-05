package jp.tomorrowkey.irkit4j;

import jp.tomorrowkey.irkit4j.entity.Message;
import jp.tomorrowkey.irkit4j.http.HttpClient;
import jp.tomorrowkey.irkit4j.http.HttpRequest;
import jp.tomorrowkey.irkit4j.http.HttpResponse;
import jp.tomorrowkey.irkit4j.http.UnexpectedStatusCodeException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.InetAddress;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LocalIRKitTest {

    private InetAddress mInetAddress;

    @Before
    public void setUp() throws Exception {
        mInetAddress = InetAddress.getByName("0.0.0.0");
    }

    @Test
    public void getKeys() throws IOException {
        try {
            LocalIRKit.getKeys(null, new HttpClient());
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            LocalIRKit.getKeys(mInetAddress, null);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        {
            HttpClient httpClient = mock(HttpClient.class);
            HttpResponse httpResponse = mock(HttpResponse.class);
            when(httpResponse.getStatusCode()).thenReturn(200);
            when(httpResponse.getContent()).thenReturn("{\"clienttoken\": \"something\"}");
            when(httpClient.request(Mockito.notNull(HttpRequest.class))).thenReturn(httpResponse);

            String clientToken = LocalIRKit.getKeys(mInetAddress, httpClient);
            assertThat(clientToken, is("something"));
        }

        {
            HttpClient httpClient = mock(HttpClient.class);
            HttpResponse httpResponse = mock(HttpResponse.class);
            when(httpResponse.getStatusCode()).thenReturn(500);
            when(httpClient.request(Mockito.notNull(HttpRequest.class))).thenReturn(httpResponse);

            try {
                LocalIRKit.getKeys(mInetAddress, httpClient);
                fail("UnexpectedStatusCodeException is not thrown");
            } catch (UnexpectedStatusCodeException e) {
                // OK
            }
        }
    }

    @Test
    public void getMessage() throws IOException {
        try {
            LocalIRKit.getMessage(null, new HttpClient());
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            LocalIRKit.getMessage(mInetAddress, null);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        {
            HttpClient httpClient = mock(HttpClient.class);
            HttpResponse httpResponse = mock(HttpResponse.class);
            when(httpResponse.getStatusCode()).thenReturn(200);
            when(httpResponse.getContent()).thenReturn("{\"format\":\"raw\",\"freq\":38,\"data\":[18031,8755,1190,1190,1190,3341,1190,3341,1190,3341,1190,1190,1190,3341,1190,3341,1190,3341,1190,3341,1190,3341,1190,3341,1190,1190,1190,1190,1190,1190,1190,1190,1190,3341,1190,3341,1190,1190,1190,3341,1190,1190,1190,1190,1190,1190,1190,1190,1190,1190,1190,1190,1190,1190,1190,1190,1190,3341,1190,3341,1190,3341,1190,3341,1190,3341,1190,65535,0,9379,18031,4400,1190]}");
            when(httpClient.request(Mockito.notNull(HttpRequest.class))).thenReturn(httpResponse);

            Message message = LocalIRKit.getMessage(mInetAddress, httpClient);
            assertThat(message.getFormat(), is("raw"));
            assertThat(message.getFrequency(), is(38));
            assertThat(message.getData(), is(new int[]{18031, 8755, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 65535, 0, 9379, 18031, 4400, 1190}));
        }

        {
            HttpClient httpClient = mock(HttpClient.class);
            HttpResponse httpResponse = mock(HttpResponse.class);
            when(httpResponse.getStatusCode()).thenReturn(500);
            when(httpClient.request(Mockito.notNull(HttpRequest.class))).thenReturn(httpResponse);

            try {
                LocalIRKit.getMessage(mInetAddress, httpClient);
                fail("UnexpectedStatusCodeException is not thrown");
            } catch (UnexpectedStatusCodeException e) {
                // OK
            }
        }
    }

    @Test
    public void postMessage() throws IOException {
        try {
            String format = "raw";
            int frequency = 38;
            int[] data = new int[]{18031, 8755, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 65535, 0, 9379, 18031, 4400, 1190};
            Message message = new Message(format, frequency, data);
            LocalIRKit.postMessage(null, message, new HttpClient());
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            LocalIRKit.postMessage(mInetAddress, null, new HttpClient());
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            String format = "raw";
            int frequency = 38;
            int[] data = new int[]{18031, 8755, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 65535, 0, 9379, 18031, 4400, 1190};
            Message message = new Message(format, frequency, data);
            LocalIRKit.postMessage(mInetAddress, message, null);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        {
            HttpClient httpClient = mock(HttpClient.class);
            HttpResponse httpResponse = mock(HttpResponse.class);
            when(httpResponse.getStatusCode()).thenReturn(200);
            when(httpClient.request(Mockito.notNull(HttpRequest.class))).thenReturn(httpResponse);

            String format = "raw";
            int frequency = 38;
            int[] data = new int[]{18031, 8755, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 65535, 0, 9379, 18031, 4400, 1190};
            Message message = new Message(format, frequency, data);

            LocalIRKit.postMessage(mInetAddress, message, httpClient);
        }

        {
            HttpClient httpClient = mock(HttpClient.class);
            HttpResponse httpResponse = mock(HttpResponse.class);
            when(httpResponse.getStatusCode()).thenReturn(200);
            when(httpClient.request(Mockito.notNull(HttpRequest.class))).thenReturn(httpResponse);

            String format = "raw";
            int frequency = 38;
            int[] data = new int[]{18031, 8755, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 1190, 1190, 3341, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 3341, 1190, 65535, 0, 9379, 18031, 4400, 1190};

            LocalIRKit.postMessage(mInetAddress, format, frequency, data, httpClient);
        }

    }

}
