package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "//android.view.View[@resource-id='pcs']/android.view.View/android.widget.TextView[1]",
            FOOTER = "//*[@text='View article in browser']",
            SAVE_BUTTON = "org.wikipedia:id/page_save",
            SNACKBAR_ADD_TO_LIST = "//android.widget.Button[@resource-id='org.wikipedia:id/snackbar_action'][@text='Add to list']",
            MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//android.widget.Button[@text='OK']",
            MENU_MORE_OPTIONS = "//android.widget.ImageView[@content-desc='More options']",
            MENU_ITEM_EXPLORE = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_explore']";

    public ArticlePageObject(AndroidDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(By.xpath(TITLE), "Cannot find article title on page", 15);
    }

    public String getArticleTitle() {
        WebElement titleElement = waitForTitleElement();
        return titleElement.getText();
    }

    public void swipeUpToFooter() {
        this.swipeUpToFindElement(
                By.xpath(FOOTER), "Cannot find the end of the article", 20);
    }

    public void addArticleToMyList(String folderName) {

        // Click 'Save' in the footer
        this.waitForElementAndClick(
                By.id(SAVE_BUTTON),
                "Cannot find button to save article",
                5);

        // Click 'Add to list' in the appeared message
        this.waitForElementAndClick(
                By.xpath(SNACKBAR_ADD_TO_LIST),
                "Cannot find the element to add to the reading list",
                5);

        // Enter tne Name of the list
        this.waitForElementAndSendKey(
                By.id(MY_LIST_NAME_INPUT),
                folderName,
                "Cannot find input field to enter the Name of the list",
                5);

        // Press 'OK'
        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press the button 'OK' to save the article in the new list",
                5);
    }

    public void closeArticle() {
        // Return to the main page via the menu 'More options'
        this.waitForElementAndClick(
                By.xpath(MENU_MORE_OPTIONS),
                "Cannot open menu 'More options' in the right top corner",
                5);

        // Click menu item 'Explore' to return to the main page
        this.waitForElementAndClick(
                By.xpath(MENU_ITEM_EXPLORE),
                "Cannot click the menu item Explore",
                5);

    }
}
