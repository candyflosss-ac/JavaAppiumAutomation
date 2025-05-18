package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "//android.view.View[@resource-id='pcs']/android.view.View/android.widget.TextView[1]",
            FOOTER = "//*[@text='View article in browser']";

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
}
