import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetLocalNumber () {
        MainClass mainClass = new MainClass();
        int expectedValue = 14;
        int actualValue = mainClass.getLocalNumber();
        System.out.println("testGetLocalNumber: actual value is " + actualValue);

        Assert.assertEquals("Expected value of the getLocalNumber is incorrect", expectedValue, actualValue);

    }

}
