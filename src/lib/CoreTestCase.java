package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import junit.framework.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.URI;
import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AndroidDriver driver;
    private static final String AppiumURL = "http://127.0.0.1:4723";

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("AndroidTestDevice")
                .setPlatformVersion("15")
                .setAutomationName("UiAutomator2")
                .setAppPackage("org.wikipedia")
                .setAppActivity(".main.MainActivity")
                .setApp(System.getProperty("user.dir") + "/apks/org.wikipedia_50524.apk");

        URL serverURL = URI.create(AppiumURL).toURL();
        driver = new AndroidDriver(serverURL, options);

        skipOnboardingScreenIfPresented();
    }

    @Override
    protected void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();

            super.tearDown();
        }
    }

    private void skipOnboardingScreenIfPresented() {
        try {
            WebElement btnSkip = driver.findElement(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
            if (btnSkip.isDisplayed()) {
                btnSkip.click();
            }
        } catch (Exception ignored) { }
    }

}
