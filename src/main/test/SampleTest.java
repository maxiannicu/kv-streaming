import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by lschidu on 5/16/17.
 */
@RunWith(JUnit4.class)
public class SampleTest {


    @Test
    public void dummyTest() {
        Assert.assertEquals("horse".length(), 5);
    }
}
