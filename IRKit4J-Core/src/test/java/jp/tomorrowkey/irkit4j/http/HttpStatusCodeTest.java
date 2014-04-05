package jp.tomorrowkey.irkit4j.http;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpStatusCodeTest {
    @Test
    public void ok() {
        assertThat(HttpStatusCode.OK, is(200));
    }
}
