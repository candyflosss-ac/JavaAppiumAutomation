import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

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

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine = "Appium";
        waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath(String.format("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title'][@text='%s']", searchLine)),
                String.format("Cannot find page_list_item with title '%s'", searchLine),
                5);

        waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='" + searchLine + "']"),
                "Cannot find article title " + searchLine,
                15);

        // Click somewhere to close msg 'customize your toolbar'-'got it' that cannot be found in elements
        WebElement titleElement = waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='" + searchLine + "']"),
                "Cannot find article title",
                15);
        titleElement.click();

        swipeUpToFindElement(
                By.xpath("//*[@text='View article in browser']"),
                "Cannot find the end of the article",
                20);
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine = "Java";
        waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5);

        String expectedDescription = "Object-oriented programming language";
        String expectedArticleTitle = "Java (programming language)";

        // Open article from the search list by description
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='"+ expectedDescription +"']"),
                "Cannot find page list item with description '"+ expectedDescription +"'",
                5);

        // Check the title presented. Title does not have id, xpath was used
        waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='" + expectedArticleTitle + "']"),
                "Cannot find article title: " + expectedArticleTitle,
                15);

        // Click 'Save' in the footer
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find button to save article",
                5);

        // Click 'Add to list' in the appeared message
        waitForElementAndClick(
                By.xpath("//android.widget.Button[@resource-id='org.wikipedia:id/snackbar_action'][@text='Add to list']"),
                "Cannot find the element to add to the reading list",
                5);

        // Enter tne Name of the list
        String nameOfList = "Learning programing";
        waitForElementAndSendKey(
                By.id("org.wikipedia:id/text_input"),
                nameOfList,
                "Cannot find input field to enter the Name of the list",
                5);

        // Press 'OK'
        waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot press the button 'OK' to save the article in the new list",
                5);

        // Return to the main page via the menu 'More options'
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot open menu 'More options' in the right top corner",
                5);

        // Click menu item 'Explore' to return to the main page
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_explore']"),
                "Cannot click the menu item Explore",
                5);

        // Open list of the saved articles - Click 'Saved' in the footer
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Saved']"),
                "Cannot click on Saved on the bottom toolbar",
                5);

        // Open the list of the saved articles
        waitForElementAndClick(
                By.xpath(String.format("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='%s']", nameOfList)),
                "Cannot open the folder: " + nameOfList,
                5);

        // Message may appear
        closeMsgShareIfPresented();

        String locatorSavedArticle = "//android.view.ViewGroup[@resource-id='org.wikipedia:id/page_list_item_container']/*[@text='" + expectedArticleTitle + "']";
        // Check the article presented
        waitForElementPresent(
                By.xpath(locatorSavedArticle),
                "Cannot find title in the list of saved articles: " + expectedArticleTitle,
                15);

        // Swipe element left to remove
        swipeElementLeft(
                By.xpath(locatorSavedArticle),
                "left",
                400);

        // Check element not presented in the list
        waitForElementNotPresent(
                By.xpath(locatorSavedArticle),
                "Cannot delete saved article, element displayed",
                5);
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

    private void closeMsgShareIfPresented() {
        try {
            WebElement msg = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/textView'][@text='Share this reading list with others']"));
            WebElement btn = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"org.wikipedia:id/buttonView\"][@text='Got it']"));

            if (msg.isDisplayed()) {
                btn.click();
            }
        } catch (Exception ignored) { }
    }

    protected void swipeUp(int timeOfSwipe) {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int yStart = (int) (size.height * 0.8);
        int yEnd = (int) (size.height * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, yStart));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe), PointerInput.Origin.viewport(), x, yEnd));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }


    protected void swipeElementLeft(By by, String errorMessage, int timeOfSwipe) {
        WebElement element = waitForElementPresent(by, errorMessage, 10);

        int xLeft = element.getLocation().getX();
        int xRight = xLeft + element.getSize().getWidth();
        int yUpper = element.getLocation().getY();
        int yLower = yUpper + element.getSize().getHeight();
        int yMiddle = (yUpper + yLower) / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xRight, yMiddle));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipe), PointerInput.Origin.viewport(), xLeft, yMiddle));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));

//        // cannot make it works as expected
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("direction", "left");
//        params.put("top", xLeft);
//        params.put("left", yUpper);
//        params.put("width", xRight);
//        params.put("height", yLower);
//        params.put("percent", percent);
//        params.put("speed", 500);
//        driver.executeScript("mobile: swipeGesture", params);
    }


    protected void swipe(By by, String direction, int timeOfSwipeMillis) {
        Dimension size = driver.manage().window().getSize();

        int xStart = size.width / 2;
        int yStart = size.height / 2;
        int xEnd = xStart;
        int yEnd = yStart;

        switch (direction.toLowerCase()) {
            case "up":
                yEnd = (int) (size.height * 0.2);
                break;
            case "down":
                yEnd = (int) (size.height * 0.8);
                break;
            case "left":
                xEnd = (int) (size.width * 0.2);
            case "right":
                xEnd = (int) (size.width * 0.8);
            default:
                throw new IllegalArgumentException("Invalid swipe direction: " + direction);
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xStart, yStart));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(timeOfSwipeMillis), PointerInput.Origin.viewport(), xEnd, yEnd));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(swipe));
    }

}
