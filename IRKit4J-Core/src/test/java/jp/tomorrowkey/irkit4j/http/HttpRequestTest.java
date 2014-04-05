package jp.tomorrowkey.irkit4j.http;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;


public class HttpRequestTest {
    @Test
    public void constructor() throws Exception {
        try {
            HttpRequestMethod httpRequestMethod = null;
            String url = "http://example.com";
            String encoding = "utf-8";
            String content = "Hello";
            new HttpRequest(httpRequestMethod, url, encoding, content);
            fail("IllegalArguemtnException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = null;
            String encoding = "utf-8";
            String content = "Hello";
            new HttpRequest(httpRequestMethod, url, encoding, content);
            fail("IllegalArguemtnException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "";
            String encoding = "utf-8";
            String content = "Hello";
            new HttpRequest(httpRequestMethod, url, encoding, content);
            fail("IllegalArguemtnException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = null;
            String content = "Hello";
            new HttpRequest(httpRequestMethod, url, encoding, content);
            fail("IllegalArguemtnException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "";
            String content = "Hello";
            new HttpRequest(httpRequestMethod, url, encoding, content);
            fail("IllegalArguemtnException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "utf-8";
            String content = null;
            new HttpRequest(httpRequestMethod, url, encoding, content);
            fail("IllegalArguemtnException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "utf-8";
            String content = "";
            new HttpRequest(httpRequestMethod, url, encoding, content);
            fail("IllegalArguemtnException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = null;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            new HttpRequest(httpRequestMethod, url, encoding, parameters);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = null;
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            new HttpRequest(httpRequestMethod, url, encoding, parameters);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            new HttpRequest(httpRequestMethod, url, encoding, parameters);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = null;
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            new HttpRequest(httpRequestMethod, url, encoding, parameters);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            new HttpRequest(httpRequestMethod, url, encoding, parameters);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "Hello";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            new HttpRequest(httpRequestMethod, url, encoding, parameters);
            fail("UnsupportedEncodingException is not thrown");
        } catch (UnsupportedEncodingException e) {
            // OK
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.POST;
            String url = "http://example.com";
            String encoding = "Hello";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            new HttpRequest(httpRequestMethod, url, encoding, parameters);
            // OK
        } catch (UnsupportedEncodingException e) {
            fail("UnsupportedEncodingException is thrown when method id POST and encoding is invalid");
        }

        try {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = null;
            new HttpRequest(httpRequestMethod, url, encoding, parameters);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

    @Test
    public void getUrl() throws Exception {
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding, parameters);

            assertThat(httpRequest.getUrl(), is("http://example.com?key=value"));
        }
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{
                    new HttpParameter("key", "value"),
                    new HttpParameter("key2", "value2")
            };
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding, parameters);

            assertThat(httpRequest.getUrl(), is("http://example.com?key=value&key2=value2"));
        }
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.POST;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{
                    new HttpParameter("key", "value")
            };
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding, parameters);

            assertThat(httpRequest.getUrl(), is("http://example.com"));
        }
    }

    @Test
    public void getParameters() throws Exception {
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.POST;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[0];
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding, parameters);

            assertThat(httpRequest.getParameters(), is(not(nullValue())));
            assertThat(httpRequest.getParameters().length, is(0));
        }
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.POST;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding);

            assertThat(httpRequest.getParameters(), is(not(nullValue())));
            assertThat(httpRequest.getParameters().length, is(0));
        }
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.POST;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{
                    new HttpParameter("key", "value")
            };
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding, parameters);

            assertThat(httpRequest.getParameters(), is(not(nullValue())));
            assertThat(httpRequest.getParameters().length, is(1));
            assertThat(httpRequest.getParameters()[0].getKey(), is("key"));
            assertThat(httpRequest.getParameters()[0].getValue(), is("value"));
        }
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.POST;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{
                    new HttpParameter("key", "value"),
                    new HttpParameter("key2", "value2"),
            };
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding, parameters);

            assertThat(httpRequest.getParameters(), is(not(nullValue())));
            assertThat(httpRequest.getParameters().length, is(2));
            assertThat(httpRequest.getParameters()[0].getKey(), is("key"));
            assertThat(httpRequest.getParameters()[0].getValue(), is("value"));
            assertThat(httpRequest.getParameters()[1].getKey(), is("key2"));
            assertThat(httpRequest.getParameters()[1].getValue(), is("value2"));
        }
    }

    @Test
    public void addParameters() throws Exception {
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding);
            httpRequest.addParameters(new HttpParameter("key", "value"));
            assertThat(httpRequest.getParameters(), is(not(nullValue())));
            assertThat(httpRequest.getParameters().length, is(1));
            assertThat(httpRequest.getParameters()[0].getKey(), is("key"));
            assertThat(httpRequest.getParameters()[0].getValue(), is("value"));
        }
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding);
            httpRequest.addParameters(new HttpParameter("key", "value"), new HttpParameter("key2", "value2"));
            assertThat(httpRequest.getParameters(), is(not(nullValue())));
            assertThat(httpRequest.getParameters().length, is(2));
            assertThat(httpRequest.getParameters()[0].getKey(), is("key"));
            assertThat(httpRequest.getParameters()[0].getValue(), is("value"));
            assertThat(httpRequest.getParameters()[1].getKey(), is("key2"));
            assertThat(httpRequest.getParameters()[1].getValue(), is("value2"));
        }
    }

    @Test
    public void getEncoding() throws Exception {
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            String encoding = "utf-8";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, encoding, parameters);

            assertThat(httpRequest.getEncoding(), is("utf-8"));
        }
        {
            HttpRequestMethod httpRequestMethod = HttpRequestMethod.GET;
            String url = "http://example.com";
            HttpParameter[] parameters = new HttpParameter[]{new HttpParameter("key", "value")};
            HttpRequest httpRequest = new HttpRequest(httpRequestMethod, url, parameters);

            assertThat(httpRequest.getEncoding(), is("utf-8"));
        }
    }


}
