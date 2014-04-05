package jp.tomorrowkey.irkit4j.http;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Enclosed.class)
public class HttpParameterTest {

    public static class constructorTest {
        @Test(expected = IllegalArgumentException.class)
        public void throwIllegalArgumentExceptionWhenKeyIsNull() {
            new HttpParameter(null, "value");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwIllegalArgumentExceptionWhenKeyIsEmpty() {
            new HttpParameter(null, "");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwIllegalArgumentExceptionWhenValueIsNull() {
            new HttpParameter("key", null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwIllegalArgumentExceptionWhenValueIsEmpty() {
            new HttpParameter("key", "");
        }
    }

    public static class getKeyTest {
        @Test
        public void getKeyEqualsKeyWhichPassesWhenConstructor() throws Exception {
            HttpParameter httpParameter = new HttpParameter("Hello", "World");
            assertThat(httpParameter.getKey(), is("Hello"));
        }
    }

    public static class getValueTest {
        @Test
        public void getValueEqualsKeyWhichPassesWhenConstructor() throws Exception {
            HttpParameter httpParameter = new HttpParameter("Hello", "World");
            assertThat(httpParameter.getValue(), is("World"));
        }
    }

    public static class getEncodedKeyTest {
        @Test
        public void getEncodedKeyIsEncoded() throws Exception {
            HttpParameter httpParameter = new HttpParameter("ハロー　ワールド!@#$%^&()_+{}:\">?<`=[];',/", "value");
            String encodedKey = httpParameter.getEncodedKey("UTF-8");
            assertThat(encodedKey, is(not(nullValue())));
            assertThat(
                    encodedKey.toUpperCase(),
                    is("%e3%83%8f%e3%83%ad%e3%83%bc%e3%80%80%e3%83%af%e3%83%bc%e3%83%ab%e3%83%89%21%40%23%24%25%5e%26%28%29_%2b%7b%7d%3a%22%3e%3f%3c%60%3d%5b%5d%3b%27%2c%2f".toUpperCase())
            );
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwIllegalArgumentExceptionWhenParameterIsEmpty() throws Exception {
            HttpParameter httpParameter = new HttpParameter("key", "value");
            httpParameter.getEncodedKey("");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwIllegalArgumentExceptionWhenParameterIsNull() throws Exception {
            HttpParameter httpParameter = new HttpParameter("key", "value");
            httpParameter.getEncodedKey(null);
        }
    }

    public static class getEncodedValueTest {
        @Test
        public void getEncodedValueIsEncoded() throws Exception {
            HttpParameter httpParameter = new HttpParameter("key", "ハロー　ワールド!@#$%^&()_+{}:\">?<`=[];',/");
            String encodedValue = httpParameter.getEncodedValue("UTF-8");
            assertThat(encodedValue, is(not(nullValue())));
            assertThat(
                    encodedValue.toUpperCase(),
                    is("%e3%83%8f%e3%83%ad%e3%83%bc%e3%80%80%e3%83%af%e3%83%bc%e3%83%ab%e3%83%89%21%40%23%24%25%5e%26%28%29_%2b%7b%7d%3a%22%3e%3f%3c%60%3d%5b%5d%3b%27%2c%2f".toUpperCase())
            );
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwIllegalArgumentExceptionWhenParameterIsEmpty() throws Exception {
            HttpParameter httpParameter = new HttpParameter("key", "value");
            httpParameter.getEncodedValue("");
        }

        @Test(expected = IllegalArgumentException.class)
        public void throwUnsupportEncodingExceptionWhenParameterIsNull() throws Exception {
            HttpParameter httpParameter = new HttpParameter("key", "value");
            httpParameter.getEncodedValue(null);
        }
    }
}
