import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception{
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testSearch() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find page_list_item with description 'Object-oriented programming language'",
                15
        );
    }

    @Test
    public void testCancelSearch() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKey(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5);

        /* Button '<-' has xpath but doesn't have id */
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find <- to cancel the search and return to the main window",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Button <- is still present on the page",
                5
        );
    }

    @Test
    public void testCancelSearchWithBtnX() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKey(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Cannot find search input",
                5
        );

        /* Button X appears in the new version of the app Wiki when some text is entered into the search field */
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to clear the search",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Button X is still present on the page",
                5
        );

        /* Button '<-' has xpath but doesn't have id */
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find <- to cancel the search and return to the main window",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Button <- is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Object-oriented programming language']"),
                "Cannot find page_list_item with description 'Object-oriented programming language'",
                5);

        /* Title does not have id, xpath was used */
        WebElement titleElement = MainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot find article title",
                15);
        String actualArticleTitle = titleElement.getAttribute("text");

        Assert.assertEquals("We see unexpected title","Java (programming language)",actualArticleTitle);

    }

    @Test
    public void testSearchInputHasExpectedTextOnMainPage() {
        By locatorSearchOnMainPage = By.xpath("//androidx.cardview.widget.CardView[@resource-id='org.wikipedia:id/search_container']//android.widget.TextView");
        MainPageObject.assertElementHasText(
                locatorSearchOnMainPage,
                "Search Wikipedia",
                "Search input on the main page does not have expected text");
    }

    @Test
    public void testSearchInputHasExpectedText() {
        By locatorSearchOnMainPage = By.id("org.wikipedia:id/search_container");
        MainPageObject.waitForElementAndClick(
                locatorSearchOnMainPage,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        By locatorSearch = By.id("org.wikipedia:id/search_src_text");
        MainPageObject.assertElementHasText(
                locatorSearch,
                "Search Wikipedia",
                "Search input does not have expected text");
    }

    @Test
    public void testSearchAndCancelResults() {
        By locatorSearchOnMainPage = By.id("org.wikipedia:id/search_container");
        MainPageObject.waitForElementAndClick(
                locatorSearchOnMainPage,
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKey(
                By.id("org.wikipedia:id/search_src_text"),
                "Appium",
                "Cannot find search input",
                5
        );

        By locatorSearchResult = By.id("org.wikipedia:id/page_list_item_title");
        int countSearchResult = MainPageObject.getAmountOfElements(locatorSearchResult);
        Assert.assertTrue(
                "Expected to find more then 1 search result, found " + countSearchResult,
                countSearchResult > 1
        );

        /* The button X clears the search field. */
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to clear the search",
                5
        );

        MainPageObject.assertElementNotPresentedByAmount(
                locatorSearchResult,
                "Expected to find empty search result, found "
        );

        /* Check the same element(s) but with existent verification */
        MainPageObject.waitForElementNotPresent(
                locatorSearchResult,
                "Expected to find empty search result, found " +  locatorSearchResult,
                5
        );

        /* Button X appears/displays only when some text is entered into the search field, Check that is not presented */
        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Button <- is still present on the page",
                5
        );

    }

    @Test
    public void testSwipeArticle() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine = "Appium";
        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath(String.format("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title'][@text='%s']", searchLine)),
                String.format("Cannot find page_list_item with title '%s'", searchLine),
                5);

        MainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='" + searchLine + "']"),
                "Cannot find article title " + searchLine,
                15);

        // Click somewhere to close msg 'customize your toolbar'-'got it' that cannot be found in elements
        WebElement titleElement = MainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='" + searchLine + "']"),
                "Cannot find article title",
                15);
        titleElement.click();

        MainPageObject.swipeUpToFindElement(
                By.xpath("//*[@text='View article in browser']"),
                "Cannot find the end of the article",
                20);
    }

    @Test
    public void testSaveFirstArticleToMyList() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine = "Java";
        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5);

        String expectedDescription = "Object-oriented programming language";
        String expectedArticleTitle = "Java (programming language)";

        // Open article from the search list by description
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='"+ expectedDescription +"']"),
                "Cannot find page list item with description '"+ expectedDescription +"'",
                5);

        // Check the title presented. Title does not have id, xpath was used
        MainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='" + expectedArticleTitle + "']"),
                "Cannot find article title: " + expectedArticleTitle,
                15);

        // Click 'Save' in the footer
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find button to save article",
                5);

        // Click 'Add to list' in the appeared message
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.Button[@resource-id='org.wikipedia:id/snackbar_action'][@text='Add to list']"),
                "Cannot find the element to add to the reading list",
                5);

        // Enter tne Name of the list
        String nameOfList = "Learning programing";
        MainPageObject.waitForElementAndSendKey(
                By.id("org.wikipedia:id/text_input"),
                nameOfList,
                "Cannot find input field to enter the Name of the list",
                5);

        // Press 'OK'
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.Button[@text='OK']"),
                "Cannot press the button 'OK' to save the article in the new list",
                5);

        // Return to the main page via the menu 'More options'
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot open menu 'More options' in the right top corner",
                5);

        // Click menu item 'Explore' to return to the main page
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_explore']"),
                "Cannot click the menu item Explore",
                5);

        // Open list of the saved articles - Click 'Saved' in the footer
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Saved']"),
                "Cannot click on Saved on the bottom toolbar",
                5);

        // Open the list of the saved articles
        MainPageObject.waitForElementAndClick(
                By.xpath(String.format("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='%s']", nameOfList)),
                "Cannot open the folder: " + nameOfList,
                5);

        // Message may appear
        MainPageObject.closeMsgShareIfPresented();

        String locatorSavedArticle = "//android.view.ViewGroup[@resource-id='org.wikipedia:id/page_list_item_container']/*[@text='" + expectedArticleTitle + "']";
        // Check the article presented
        MainPageObject.waitForElementPresent(
                By.xpath(locatorSavedArticle),
                "Cannot find title in the list of saved articles: " + expectedArticleTitle,
                15);

        // Swipe element left to remove
        MainPageObject.swipeElementLeft(
                By.xpath(locatorSavedArticle),
                "left",
                400);

        // Check element not presented in the list
        MainPageObject.waitForElementNotPresent(
                By.xpath(locatorSavedArticle),
                "Cannot delete saved article, element displayed",
                5);
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine = "Linkin Park discography";
        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5);

        String locatorSearchResult = "//androidx.recyclerview.widget.RecyclerView[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
        MainPageObject.waitForElementPresent(
                By.xpath(locatorSearchResult),
                "Cannot find anything by the request: " + searchLine,
                15
        );

        int amountOfSearchResults = MainPageObject.getAmountOfElements(By.xpath(locatorSearchResult));

        Assert.assertTrue(
                "We found too few results",
                amountOfSearchResults > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine = "qwertyuiooooooo";
        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5);

        String locatorSearchResult = "//androidx.recyclerview.widget.RecyclerView[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']";
        String locatorEmptyResult = "//*[@text='No results']";

        MainPageObject.waitForElementPresent(
                By.xpath(locatorEmptyResult),
                "Cannot find empty result element by the request: " + searchLine,
                15
        );

        MainPageObject.assertElementNotPresent(
                By.xpath(locatorSearchResult),
                "Some results were found by the request: " + searchLine
        );

    }

    @Test
    public void testSaveTwoArticlesToMyListAndRemoveOne() {
        // The first article, search
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine_FirstArticle = "Java";
        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine_FirstArticle,
                "Cannot find search input",
                5);

        String expectedDescription_FirstArticle = "Object-oriented programming language";
        String expectedTitle_FirstArticle = "Java (programming language)";
        // Open article from the search list by description
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='"+ expectedDescription_FirstArticle +"']"),
                "Cannot find page list item with description '"+ expectedDescription_FirstArticle +"'",
                5);

        // Check the title presented. Title does not have id, xpath was used
        MainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='" + expectedTitle_FirstArticle + "']"),
                "Cannot find article title: " + expectedTitle_FirstArticle,
                15);

        // Save the article
        String nameOfList = "Ex5 Two articles";
        MainPageObject.saveArticleToList(nameOfList);

        // Second article, search
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine_SecondArticle = "Appium";
        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine_SecondArticle,
                "Cannot find search input",
                5);

        String expectedDescription_SecondArticle = "Automation for Apps";
        String expectedTitle_SecondArticle = "Appium";
        // Open article from the search list by description
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='"+ expectedDescription_SecondArticle +"']"),
                "Cannot find page list item with description '"+ expectedDescription_SecondArticle +"'",
                5);

        // Check the title presented
        MainPageObject.waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='" + expectedTitle_SecondArticle + "']"),
                "Cannot find article title: " + expectedTitle_SecondArticle,
                15);

        //Save the second article
        MainPageObject.saveArticleToList(nameOfList);
        // Finish with the second article

        // Return to the main page via the menu 'More options'
        MainPageObject.openExplorePage();

        // Open list of the saved articles - Click 'Saved' in the footer
        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Saved']"),
                "Cannot click on Saved on the bottom toolbar",
                5);

        // Open the list of the saved articles
        MainPageObject.waitForElementAndClick(
                By.xpath(String.format("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='%s']", nameOfList)),
                "Cannot open the folder: " + nameOfList,
                5);

        // Message may appear
        MainPageObject.closeMsgShareIfPresented();

        // Check the 1st article presented
        String locatorFirstSavedArticle = "//android.view.ViewGroup[@resource-id='org.wikipedia:id/page_list_item_container']/*[@text='" + expectedTitle_FirstArticle + "']";
        MainPageObject.waitForElementPresent(
                By.xpath(locatorFirstSavedArticle),
                "Cannot find title in the list of saved articles: " + expectedTitle_FirstArticle,
                15);

        // Check the 2nd article presented
        String locatorSecondSavedArticle = "//android.view.ViewGroup[@resource-id='org.wikipedia:id/page_list_item_container']/*[@text='" + expectedTitle_SecondArticle + "']";
        MainPageObject.waitForElementPresent(
                By.xpath(locatorSecondSavedArticle),
                "Cannot find title in the list of saved articles: " + expectedTitle_SecondArticle,
                15);

        // Swipe 1st saved article left to remove
        MainPageObject.swipeElementLeft(
                By.xpath(locatorFirstSavedArticle),
                "left",
                400);

        // Check element not presented in the list
        MainPageObject.waitForElementNotPresent(
                By.xpath(locatorFirstSavedArticle),
                "Cannot delete saved article, element displayed",
                5);

        // Check the 2nd article presented
        MainPageObject.waitForElementPresent(
                By.xpath(locatorSecondSavedArticle),
                "Cannot find title in the list of saved articles: " + expectedTitle_SecondArticle,
                15);

        // Open the 2nd article
        MainPageObject.waitForElementAndClick(
                By.xpath(locatorSecondSavedArticle),
                "Cannot find the article in the list",
                5
        );

        //Check the title in the opened article
        String locatorArticleTitle = "//android.view.View[@resource-id='pcs']/android.view.View[1]/android.widget.TextView[@text='" + expectedTitle_SecondArticle + "']";
        MainPageObject.assertElementHasText(
                By.xpath(locatorArticleTitle),
                expectedTitle_SecondArticle,
                "Search input does not have expected text: " + expectedTitle_SecondArticle
        );
    }

    @Test
    public void testArticleHasTitle() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5);

        String searchLine = "Muse discography";
        MainPageObject.waitForElementAndSendKey(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                searchLine,
                "Cannot find search input",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Muse discography']"),
                "Cannot find the article in search results list by search  line: '"+ searchLine +"'",
                5);

        // Click somewhere to close msg 'Customize your toolbar'-'got it' that cannot be found in elements,
        // Otherwise, article elements are not available
        MainPageObject.waitForElementAndClick(By.id("org.wikipedia:id/page_web_view"),"",5);

        String locatorArticleTitle = "//android.view.View[@resource-id='pcs']/android.view.View[1]/android.widget.TextView";
        MainPageObject.assertElementPresent(
                By.xpath(locatorArticleTitle),
                "Cannot get an element by locator.");
    }


}
