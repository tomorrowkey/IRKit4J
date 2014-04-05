package jp.tomorrowkey.irkit4j.http;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnexpectedStatusCodeExceptionTest {

    @Test
    public void constructor() {
        new UnexpectedStatusCodeException(1, null);
        new UnexpectedStatusCodeException(1, "");
        new UnexpectedStatusCodeException(-1, "");
        new UnexpectedStatusCodeException(0, "");
    }

    @Test
    public void getStatusCode() {
        UnexpectedStatusCodeException e = new UnexpectedStatusCodeException(100, "");
        assertThat(e.getStatusCode(), is(100));
    }

    @Test
    public void getMessage() {
        UnexpectedStatusCodeException e = new UnexpectedStatusCodeException(100, "Hello, World");
        assertThat(e.getMessage(), is("Hello, World"));
    }

}
