import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass{

    @Test
    public void testGetLocalNumber () {
        int expectedValue = 14;
        int actualValue = this.getLocalNumber();
        System.out.println("testGetLocalNumber: actual value is " + actualValue);

        Assert.assertTrue(String.format("Expected value of the getLocalNumber is %s, actual value: %s", expectedValue, actualValue), actualValue == expectedValue);

    }

}
