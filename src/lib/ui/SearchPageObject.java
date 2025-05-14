package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_CANCEL_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
            SEARCH_X_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{SUBSTRING}']";

    public SearchPageObject(AndroidDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS -> */
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* <- TEMPLATES METHODS */

    public void initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find 'Search Wikipedia' input", 5);
    }

    public void waitForCancelButtonToAppear() {
        // Button '<-' has xpath but doesn't have id
        this.waitForElementPresent(By.xpath(SEARCH_CANCEL_BUTTON), "Cannot find <- to cancel the search and return to the main window", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.xpath(SEARCH_CANCEL_BUTTON), "Button <- is still present on the page", 5);
    }

    public void clickCancelSearch(){
        this.waitForElementAndClick(By.xpath(SEARCH_CANCEL_BUTTON), "Cannot click <- to cancel the search button", 5);
    }

    public void clickXToCancelSearch() {
        /* Button X appears in the new version of the app Wiki when some text is entered into the search field */
        this.waitForElementAndClick(By.id(SEARCH_X_BUTTON), "Cannot find X to clear the search",5);
    }

    public void waitForXButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_X_BUTTON), "Button X is still present on the page", 5);
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKey(By.xpath(SEARCH_INPUT), searchLine, "Cannot find search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find page_list_item with description: " + substring, 15);
    }



}
