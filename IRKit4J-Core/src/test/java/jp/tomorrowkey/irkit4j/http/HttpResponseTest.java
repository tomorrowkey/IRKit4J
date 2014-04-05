package jp.tomorrowkey.irkit4j.http;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpResponseTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwIllegalArgumentExceptionIfHttpURLConnectionIsNull() throws Exception {
        new HttpResponse(null);
    }

    @Test
    public void getStatusCodeReturn200WhenHttpURLConnectionReturn200() throws Exception {
        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getResponseCode()).thenReturn(200);

        HttpResponse httpResponse = new HttpResponse(connection);
        assertThat(httpResponse.getStatusCode(), is(200));
    }

    @Test
    public void getStatusCodeReturn404WhenHttpURLConnectionReturn404() throws Exception {
        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getResponseCode()).thenReturn(404);

        HttpResponse httpResponse = new HttpResponse(connection);
        assertThat(httpResponse.getStatusCode(), is(404));
    }

    @Test
    public void getInputStreamReturnsErrorStreamWhenHttpURLConnectionReturnsErrorStream() throws Exception {
        InputStream expected = new ByteArrayInputStream(new byte[0]);

        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getErrorStream()).thenReturn(expected);

        HttpResponse httpResponse = new HttpResponse(connection);
        InputStream actual = httpResponse.getInputStream();
        assertThat(actual, is(not(nullValue())));
        assertThat(actual, is(sameInstance(expected)));
    }

    @Test
    public void getInputStreamReturnsInputStreamWhenHttpURLConnectionReturnsInputStream() throws Exception {
        InputStream expected = new ByteArrayInputStream(new byte[0]);

        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getInputStream()).thenReturn(expected);

        HttpResponse httpResponse = new HttpResponse(connection);
        InputStream actual = httpResponse.getInputStream();
        assertThat(actual, is(not(nullValue())));
        assertThat(actual, is(sameInstance(expected)));
    }

    @Test
    public void getInputStreamReturnsErrorStreamWhenHttpConnectionReturnsBothErrorStreamAndInputStream() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(new byte[0]);
        InputStream errorStream = new ByteArrayInputStream(new byte[0]);

        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getErrorStream()).thenReturn(errorStream);
        when(connection.getInputStream()).thenReturn(inputStream);

        HttpResponse httpResponse = new HttpResponse(connection);
        InputStream actual = httpResponse.getInputStream();
        assertThat(actual, is(not(nullValue())));
        assertThat(actual, is(sameInstance(errorStream)));
    }

    @Test
    public void getContentReturnsErrorMessageWhenHttpURLConnectionReturnsErrorStream() throws Exception {
        String expected = "Hello, World! This is error message.";
        byte[] actual = expected.getBytes();

        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getContentLength()).thenReturn(actual.length);
        when(connection.getErrorStream()).thenReturn(new ByteArrayInputStream(actual));

        HttpResponse httpResponse = new HttpResponse(connection);
        assertThat(httpResponse.getContent(), is(expected));
    }

    @Test
    public void getContentReturnsContentWhenHttpURLConnectionReturnsInputStream() throws Exception {
        String content = "Hello, World! This is content.";
        byte[] actual = content.getBytes();

        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getContentLength()).thenReturn(actual.length);
        when(connection.getErrorStream()).thenReturn(null);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(actual));

        HttpResponse httpResponse = new HttpResponse(connection);
        assertThat(httpResponse.getContent(), is(content));
    }

    @Test
    public void getContentReturnsErrorMessageWhenHttpURLConnectionReturnsBothErrorStreamAndInputStream() throws Exception {
        String errorMessage = "Hello, World! This is error message.";
        String content = "Hello, World! This is content.";

        HttpURLConnection connection = mock(HttpURLConnection.class);
        when(connection.getContentLength()).thenReturn(1);
        when(connection.getErrorStream()).thenReturn(new ByteArrayInputStream(errorMessage.getBytes()));
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream(content.getBytes()));

        HttpResponse httpResponse = new HttpResponse(connection);
        assertThat(httpResponse.getContent(), is(errorMessage));
    }

}
