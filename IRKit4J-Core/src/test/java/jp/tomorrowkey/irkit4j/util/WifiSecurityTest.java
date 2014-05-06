package jp.tomorrowkey.irkit4j.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class WifiSecurityTest {

    @Test
    public void valueOf() throws Exception {
        assertThat(Serializer.WifiSecurity.valueOf(0), is(Serializer.WifiSecurity.NONE));
        assertThat(Serializer.WifiSecurity.valueOf(2), is(Serializer.WifiSecurity.WEP));
        assertThat(Serializer.WifiSecurity.valueOf(8), is(Serializer.WifiSecurity.WPA_WPA2));
        try {
            Serializer.WifiSecurity.valueOf(-1);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }

}
