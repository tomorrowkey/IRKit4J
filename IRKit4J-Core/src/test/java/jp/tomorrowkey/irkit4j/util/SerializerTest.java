package jp.tomorrowkey.irkit4j.util;

import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SerializerTest extends TestCase {
    public void testSerialize() {
        Serializer.WifiSecurity wifiSecurity = Serializer.WifiSecurity.WPA_WPA2;
        String ssid = "IRKitTester";
        String password = "password";
        String deviceKey = "0123456789ABCDEF0123456789ABCDEF";
        String serialize = Serializer.serialize(wifiSecurity, ssid, password, deviceKey);

        assertThat(serialize, is("8/49524B6974546573746572/70617373776F7264/0123456789ABCDEF0123456789ABCDEF/2//////46"));
    }
}
