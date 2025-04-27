import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class FirstTest {

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
                .setApp(System.getProperty("user.dir") + "/apks/org.wikipedia_50524.apk");

        URL serverURL = URI.create("http://127.0.0.1:4723").toURL();
        driver = new AndroidDriver(serverURL, options);

        skipOnboardingScreenIfPresented();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find page_list_item with description 'Object-oriented programming language'",
                15
        );
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKey(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5);

        /* Button '<-' has xpath but doesn't have id */
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find <- to cancel the search and return to the main window",
                5
        );

        waitForElementNotPresent(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Button <- is still present on the page",
                5
        );
    }

    @Test
    public void testCancelSearchWithBtnX() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKey(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5
        );

        /* Button X appears in the new version of the app Wiki when some text is entered into the search field */
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to clear the search",
                5
        );

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Button X is still present on the page",
                5
        );

        /* Button '<-' has xpath but doesn't have id */
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find <- to cancel the search and return to the main window",
                5
        );

        waitForElementNotPresent(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Button <- is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find page_list_item with description 'Object-oriented programming language'",
                5);

        /* Title does not have id, xpath was used */
        WebElement titleElement = waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot find article title",
                15);
        String actualArticleTitle = titleElement.getAttribute("text");

        Assert.assertEquals("We see unexpected title","Java (programming language)",actualArticleTitle);

    }

    @Test
    public void testSearchInputHasExpectedTextOnMainPage() {
        By locatorSearchOnMainPage = By.xpath("//androidx.cardview.widget.CardView[@resource-id='org.wikipedia:id/search_container']//android.widget.TextView");
        assertElementHasText(
                locatorSearchOnMainPage,
                "Search Wikipedia",
                "Search input on the main page does not have expected text");
    }

    @Test
    public void testSearchInputHasExpectedText() {
        By locatorSearchOnMainPage = By.id("org.wikipedia:id/search_container");
        waitForElementAndClick(
                locatorSearchOnMainPage,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        By locatorSearch = By.id("org.wikipedia:id/search_src_text");
        assertElementHasText(
                locatorSearch,
                "Search Wikipedia",
                "Search input does not have expected text");
    }

    @Test
    public void testSearchAndCancelResults() {
        By locatorSearchOnMainPage = By.id("org.wikipedia:id/search_container");
        waitForElementAndClick(
                locatorSearchOnMainPage,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        waitForElementAndSendKey(
                By.id("org.wikipedia:id/search_src_text"),
                "Appium",
                "Cannot find search input",
                5
        );

        By locatorSearchResult = By.id("org.wikipedia:id/page_list_item_title");
        int countSearchResult = getAmountOfElements(locatorSearchResult);
        Assert.assertTrue(
                "Expected to find more then 1 search result, found " + countSearchResult,
                countSearchResult > 1
        );

        /* The button X clears the search field. */
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to clear the search",
                5
        );

        assertElementNotPresentedByAmount(
                locatorSearchResult,
                "Expected to find empty search result, found "
        );

        /* Check the same element(s) but with existent verification */
        waitForElementNotPresent(
                locatorSearchResult,
                "Expected to find empty search result, found " +  locatorSearchResult,
                5
        );

        /* Button X appears/displays only when some text is entered into the search field, Check that is not presented */
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Button <- is still present on the page",
                5
        );

    }

    private void skipOnboardingScreenIfPresented() {
        try {
            WebElement btnSkip = driver.findElement(By.id("org.wikipedia:id/fragment_onboarding_skip_button"));
            if (btnSkip.isDisplayed()) {
                btnSkip.click();
            }
        } catch (Exception ignored) { }
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKey(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
    
    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement element = waitForElementPresent(by, "Cannot find element to check text: '" + by + "'");
        String actualText = element.getText();
        Assert.assertEquals(errorMessage, expectedText, actualText);
    }

    private int getAmountOfElements(By by) {
        return driver.findElements(by).size();
    }

    private void assertElementNotPresentedByAmount(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        Assert.assertTrue(errorMessage + amountOfElements, amountOfElements == 0);
    }


}
