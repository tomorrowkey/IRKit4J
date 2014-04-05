package jp.tomorrowkey.irkit4j.entity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MessageTest {
    @Test
    public void constructor() {
        {
            String format = "something";
            int frequency = -1;
            int[] data = new int[]{
                    1, 2, 3, 4, 5
            };
            Message message = new Message(format, frequency, data);
            assertThat(message.format, is("something"));
            assertThat(message.frequency, is(-1));
            assertThat(message.data, is(new int[]{1, 2, 3, 4, 5}));
        }

        try {
            String format = null;
            int frequency = 0;
            int[] data = new int[]{1};
            new Message(format, frequency, data);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            String format = "something";
            int frequency = 0;
            int[] data = null;
            new Message(format, frequency, data);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }

        try {
            String format = "something";
            int frequency = 0;
            int[] data = new int[0];
            new Message(format, frequency, data);
            fail("IllegalArgumentException is not thrown");
        } catch (IllegalArgumentException e) {
            // OK
        }
    }
}
