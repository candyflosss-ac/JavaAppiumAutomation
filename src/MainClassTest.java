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

    @Test
    public void testGetClassNumber () {
        MainClass mainClass = new MainClass();
        int actualValue = mainClass.getClassNumber();
        System.out.println("testGetClassNumber: actual value is " + actualValue);

        Assert.assertTrue(String.format("Expected getClassNumber to return a value greater than 45, but got %s", actualValue),
                actualValue > 45);

    }

}
