package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{

    private static final String
        LINK_SAVED = "//android.widget.FrameLayout[@content-desc='Saved']";

    public NavigationUI(AndroidDriver driver) {
        super(driver);
    }

    public void clickSaved() {
        // Open list of the saved articles - Click 'Saved' in the footer
        this.waitForElementAndClick(
                By.xpath(LINK_SAVED),
                "Cannot click on Saved on the bottom toolbar",
                5);

    }
}
