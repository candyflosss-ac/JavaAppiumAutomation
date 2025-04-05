import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void testGetLocalNumber() {
        MainClass mainClass = new MainClass();
        int expectedValue = 14;
        int actualValue = mainClass.getLocalNumber();
        System.out.println("testGetLocalNumber: actual value is " + actualValue);

        Assert.assertEquals("Expected value of the getLocalNumber is incorrect", expectedValue, actualValue);

    }

    @Test
    public void testGetClassNumber() {
        MainClass mainClass = new MainClass();
        int actualValue = mainClass.getClassNumber();
        System.out.println("testGetClassNumber: actual value is " + actualValue);

        Assert.assertTrue(String.format("Expected getClassNumber to return a value greater than 45, but got %s", actualValue),
                actualValue > 45);

    }

    @Test
    public void testGetClassString() {
        MainClass mainClass = new MainClass();
        String stringValueToCheck = mainClass.getClassString();
        System.out.println("testGetClassString: returned value is \"" + stringValueToCheck + "\"");

        boolean containsHelloLower = stringValueToCheck.contains("hello");
        boolean containsHelloUpper = stringValueToCheck.contains("Hello");

        Assert.assertTrue(
                "The string does not contain expected substrings 'hello' or 'Hello'",
                containsHelloLower || containsHelloUpper);

    }

}
