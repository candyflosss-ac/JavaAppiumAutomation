package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class MainPageObject {

    protected AndroidDriver driver;

    public MainPageObject(AndroidDriver driver) {
        this.driver = driver;
    }


    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKey(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(By by, String expectedText, String errorMessage) {
        WebElement element = waitForElementPresent(by, "Cannot find element to check text: '" + by + "'", 10);
        String actualText = element.getText();
        Assert.assertEquals(errorMessage, expectedText, actualText);
    }

    public int getAmountOfElements(By by) {
        return driver.findElements(by).size();
    }

    public void assertElementNotPresentedByAmount(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        Assert.assertTrue(errorMessage + amountOfElements, amountOfElements == 0);
    }

    public void assertElementNotPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String msgDefault = "An element '" + by.toString() + "' supposed to be not presented";
            throw new AssertionError(msgDefault + " " + errorMessage);
        }
    }

    public void assertElementPresent(By by, String errorMessage) {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements == 0) {
            String msgDefault = "Element not found: " + by.toString();
            throw new AssertionError(msgDefault + " " + errorMessage);
        }
    }

    public void closeMsgShareIfPresented() {
        try {
            WebElement msg = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/textView'][@text='Share this reading list with others']"));
            WebElement btn = driver.findElement(By.xpath("//android.widget.Button[@resource-id=\"org.wikipedia:id/buttonView\"][@text='Got it']"));

            if (msg.isDisplayed()) {
                btn.click();
            }
        } catch (Exception ignored) { }
    }

    public void saveArticleToList(String listName) {
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

        boolean listFound = false;
        // First time to save
        try {
            // Enter tne Name of the list
            waitForElementAndSendKey(
                    By.id("org.wikipedia:id/text_input"),
                    listName,
                    "Cannot find input field to enter the Name of the list",
                    5);

            // Press 'OK'
            waitForElementAndClick(
                    By.xpath("//android.widget.Button[@text='OK']"),
                    "Cannot press the button 'OK' to save the article in the new list",
                    5);
            listFound = true;

        } catch (TimeoutException e) {
            // The list already exists
            String locatorListOfLists = "//androidx.recyclerview.widget.RecyclerView[@resource-id='org.wikipedia:id/list_of_lists']//*[@resource-id='org.wikipedia:id/item_title']";
            List<WebElement> existingLists = driver.findElements(By.xpath(locatorListOfLists));

            for (WebElement list : existingLists) {
                if (list.getText().equalsIgnoreCase(listName)) {
                    list.click();
                    listFound = true;
                    break;
                }
            }
        }

        // Create new if it was not found in the list
        if (!listFound) {
            waitForElementAndClick(
                    By.xpath("//android.widget.LinearLayout[@resource-id='org.wikipedia:id/create_button']"),
                    "Cannot found the element Create new",
                    5);
            // Enter tne Name of the list
            waitForElementAndSendKey(
                    By.id("org.wikipedia:id/text_input"),
                    listName,
                    "Cannot find input field to enter the Name of the list",
                    5);
            // Press 'OK'
            waitForElementAndClick(
                    By.xpath("//android.widget.Button[@text='OK']"),
                    "Cannot press the button 'OK' to save the article in the new list",
                    5);
        }
    }

    public void openExplorePage() {
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

    }

    public void swipeUp(int timeOfSwipe) {
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

    public void swipeUpQuick(){
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
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


    public void swipeElementLeft(By by, String errorMessage, int timeOfSwipe) {
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


    public void swipe(String direction, int timeOfSwipeMillis) {
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
