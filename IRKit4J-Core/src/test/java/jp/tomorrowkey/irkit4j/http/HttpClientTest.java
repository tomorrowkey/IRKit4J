package jp.tomorrowkey.irkit4j.http;

import com.google.common.net.HttpHeaders;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpClientTest {

    @Test
    public void newHttpURLConnection() throws Exception {
        {
            HttpClient httpClient = new HttpClient();
            HttpURLConnection httpURLConnection = httpClient.newHttpURLConnection(new URL("http://example.com"), HttpRequestMethod.GET, "utf-8");
            assertThat(httpURLConnection.getURL(), is(new URL("http://example.com")));
            assertThat(httpURLConnection.getDoInput(), is(true));
            assertThat(httpURLConnection.getDoOutput(), is(false));
            assertThat(httpURLConnection.getUseCaches(), is(false));
            assertThat(httpURLConnection.getRequestMethod(), is(not(nullValue())));
            assertThat(httpURLConnection.getRequestMethod().toUpperCase(), is("GET"));
            assertThat(httpURLConnection.getRequestProperties().size(), is(1));
            assertThat(httpURLConnection.getRequestProperties().containsKey(HttpHeaders.ACCEPT_CHARSET), is(true));
            assertThat(httpURLConnection.getRequestProperties().get(HttpHeaders.ACCEPT_CHARSET).size(), is(1));
            assertThat(httpURLConnection.getRequestProperties().get(HttpHeaders.ACCEPT_CHARSET).get(0), is("utf-8"));
        }
        {
            HttpClient httpClient = new HttpClient();
            HttpURLConnection httpURLConnection = httpClient.newHttpURLConnection(new URL("http://example.com"), HttpRequestMethod.POST, "utf-8");
            assertThat(httpURLConnection.getURL(), is(new URL("http://example.com")));
            assertThat(httpURLConnection.getDoInput(), is(true));
            assertThat(httpURLConnection.getDoOutput(), is(true));
            assertThat(httpURLConnection.getUseCaches(), is(false));
            assertThat(httpURLConnection.getRequestMethod(), is(not(nullValue())));
            assertThat(httpURLConnection.getRequestMethod().toUpperCase(), is("POST"));
            assertThat(httpURLConnection.getRequestProperties().size(), is(2));
            assertThat(httpURLConnection.getRequestProperties().containsKey(HttpHeaders.ACCEPT_CHARSET), is(true));
            assertThat(httpURLConnection.getRequestProperties().get(HttpHeaders.ACCEPT_CHARSET).size(), is(1));
            assertThat(httpURLConnection.getRequestProperties().get(HttpHeaders.ACCEPT_CHARSET).get(0), is("utf-8"));
            assertThat(httpURLConnection.getRequestProperties().containsKey(HttpHeaders.CONTENT_TYPE), is(true));
            assertThat(httpURLConnection.getRequestProperties().get(HttpHeaders.CONTENT_TYPE).size(), is(1));
            assertThat(httpURLConnection.getRequestProperties().get(HttpHeaders.CONTENT_TYPE).get(0), is("application/x-www-form-urlencoded;charset=utf-8"));
        }
    }


    @Test
    public void write() throws Exception {
        String content = "Hello, World.";
        byte[] rawBytes = content.getBytes("utf-8");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpClient httpClient = new HttpClient();
        httpClient.write(outputStream, rawBytes);

        assertThat(outputStream.toByteArray(), is(rawBytes));
    }

    @Test
    public void request() throws IOException {
        {
            String responseContent = "Response";
            String requestContent = "Request";
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(responseContent.getBytes("utf-8"));

            HttpURLConnection httpURLConnection = mock(HttpURLConnection.class);
            when(httpURLConnection.getOutputStream()).thenReturn(outputStream);
            when(httpURLConnection.getResponseCode()).thenReturn(-1);
            when(httpURLConnection.getContentLength()).thenReturn(responseContent.length());
            when(httpURLConnection.getInputStream()).thenReturn(inputStream);

            HttpClient httpClient = new HttpClient();
            httpClient.httpURLConnection = httpURLConnection;
            HttpResponse response = httpClient.request(requestContent, "utf-8");

            assertThat(response, is(not(nullValue())));
            assertThat(response.getStatusCode(), is(-1));
            assertThat(response.getContent(), is("Response"));
        }
        {
            String responseContent = "";
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(responseContent.getBytes("utf-8"));

            HttpURLConnection httpURLConnection = mock(HttpURLConnection.class);
            when(httpURLConnection.getDoOutput()).thenReturn(false);
            when(httpURLConnection.getOutputStream()).thenReturn(outputStream);
            when(httpURLConnection.getResponseCode()).thenReturn(-1);
            when(httpURLConnection.getContentLength()).thenReturn(responseContent.length());
            when(httpURLConnection.getInputStream()).thenReturn(inputStream);

            HttpClient httpClient = new HttpClient();
            httpClient.httpURLConnection = httpURLConnection;
            httpClient.request("something", "utf-8");

            assertThat(outputStream.size(), is(0));
        }
        {
            String responseContent = "";
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(responseContent.getBytes("utf-8"));

            HttpURLConnection httpURLConnection = mock(HttpURLConnection.class);
            when(httpURLConnection.getDoOutput()).thenReturn(true);
            when(httpURLConnection.getOutputStream()).thenReturn(outputStream);
            when(httpURLConnection.getResponseCode()).thenReturn(-1);
            when(httpURLConnection.getContentLength()).thenReturn(responseContent.length());
            when(httpURLConnection.getInputStream()).thenReturn(inputStream);

            HttpClient httpClient = new HttpClient();
            httpClient.httpURLConnection = httpURLConnection;
            httpClient.request("key=value&key2=value2", "utf-8");

            String expectedString = "key=value&key2=value2";
            assertThat(outputStream.size(), is(expectedString.length()));
            assertThat(outputStream.toByteArray(), is(expectedString.getBytes("utf-8")));
        }
    }

    @Test
    public void disconnect() throws Exception {
        {
            HttpClient httpClient = new HttpClient();
            httpClient.disconnect();
            // No exception is OK
        }
    }

}
