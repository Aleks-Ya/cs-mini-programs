import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TimerTest {

    @Test
    public void testToString() throws Exception {
        Timer t = new Timer();
        t.start();
        TimeUnit.SECONDS.sleep(2);
        t.finish();
        assertEquals("0:02", t.toString());
    }
}