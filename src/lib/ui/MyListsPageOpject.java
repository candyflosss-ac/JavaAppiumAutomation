package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class MyListsPageOpject extends MainPageObject{

    private static final String
            FOLDER_BY_NAME_TML= "//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TML = "//android.view.ViewGroup[@resource-id='org.wikipedia:id/page_list_item_container']/*[@text='{TITLE}']";

    private static String getFolderXpathByName(String folderName) {
        return FOLDER_BY_NAME_TML.replace("{FOLDER_NAME}", folderName);
    }

    private static String getSavedArticleXpathByName(String articleTitle) {
        return ARTICLE_BY_TITLE_TML.replace("{TITLE}", articleTitle);
    }

    public MyListsPageOpject(AndroidDriver driver) {
        super(driver);
    }

    public void openFolderByName(String folderName) {
        // Open the list of the saved articles
        String xpathFolderName = getFolderXpathByName(folderName);
        this.waitForElementAndClick(
                By.xpath(xpathFolderName),
                "Cannot open the folder: '" + folderName + "',\n xpath: " + xpathFolderName,
                5);
        // Message may appear
        this.closeMsgShareIfPresented();
    }

    public void waitForArticleToAppearByTitle(String articleTitle) {
        // Check element presented in the list
        String locatorSavedArticle = getSavedArticleXpathByName(articleTitle);
        this.waitForElementPresent(
                By.xpath(locatorSavedArticle),
                "Cannot find saved article by title: " + articleTitle,
                5);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {
        // Check element not presented in the list
        String locatorSavedArticle = getSavedArticleXpathByName(articleTitle);
        this.waitForElementNotPresent(
                By.xpath(locatorSavedArticle),
                "Cannot delete saved article, element displayed: " + articleTitle,
                5);
    }

    public void swipeByArticleToDelete(String articleTitle) {
        waitForArticleToAppearByTitle(articleTitle);
        // Swipe element left to remove
        String locatorSavedArticle = getSavedArticleXpathByName(articleTitle);
        this.swipeElementLeft(
                By.xpath(locatorSavedArticle),
                "left",
                400);

        waitForArticleToDisappearByTitle(articleTitle);
    }
}
