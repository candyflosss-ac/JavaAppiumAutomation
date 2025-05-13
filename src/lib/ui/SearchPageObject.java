package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text, 'Search Wikipedia')]",
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

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKey(By.xpath(SEARCH_INPUT), searchLine, "Cannot find search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find page_list_item with description: " + substring, 15);
    }



}
