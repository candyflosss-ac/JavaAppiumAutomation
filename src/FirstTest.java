import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.MutableCapabilities;

import java.net.URL;

public class FirstTest {

//    private AppiumDriver driver;  // for v0
    private AndroidDriver driver;

    @Before
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("AndroidTestDevice")
                .setPlatformVersion("15")
                .setAutomationName("UiAutomator2")
                .setAppPackage("org.wikipedia")
                .setAppActivity(".main.MainActivity")
                .setApp(System.getProperty("user.dir") + "/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);

//        //v1 - works
//        MutableCapabilities options = new MutableCapabilities();
//        options.setCapability("platformName", "Android");
//
//        MutableCapabilities appiumOptions = new MutableCapabilities();
//        appiumOptions.setCapability("deviceName", "AndroidTestDevice");
//        appiumOptions.setCapability("platformVersion", "15");
//        appiumOptions.setCapability("automationName", "UiAutomator2");
//        appiumOptions.setCapability("appPackage", "org.wikipedia");
//        appiumOptions.setCapability("appActivity", ".main.MainActivity");
//        appiumOptions.setCapability("app", System.getProperty("user.dir") + "/apks/org.wikipedia.apk");
//
//        options.setCapability("appium:options", appiumOptions);
//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
//
//        //v0
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability("platformName", "Android");
//        capabilities.setCapability("deviceName", "AndroidTestDevice");  // "Pixel_9_and35"
//        capabilities.setCapability("platformVersion", "15");
//        capabilities.setCapability("automationName", "UiAutomator2");  // Appium  // UiAutomator2
//        capabilities.setCapability("appPackage", "org.wikipedia");
//        capabilities.setCapability("appActivity", ".main.MainActivity");
//        capabilities.setCapability("app", "C:\\Users\\acherniy\\Wrk\\GitHub\\JavaAppiumAutomation\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
//        capabilities.setCapability("app", "apks/org.wikipedia.apk");
//
//        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void firstTest() {
        System.out.println("First Test Run");
    }
}
