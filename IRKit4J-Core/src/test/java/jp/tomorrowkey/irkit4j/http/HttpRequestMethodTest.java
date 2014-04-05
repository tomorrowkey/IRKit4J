package jp.tomorrowkey.irkit4j.http;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpRequestMethodTest {
    @Test
    public void GETEqualsStringOfGET() {
        assertThat(HttpRequestMethod.GET.toString(), is("GET"));
    }

    @Test
    public void POSTEqualsStringOfPOST() {
        assertThat(HttpRequestMethod.POST.toString(), is("POST"));
    }
}
