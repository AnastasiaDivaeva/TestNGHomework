package divaeva.testNG.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PriceFilterTest {
    private final By INPUT_MAX_PRICE = By.cssSelector("[formcontrolname='max']");
    private final By PRICE_FILTER_OK_BUTTON = By.xpath("//button[contains(text(),'Ok')]");
    private final By ONE_PAGE_PRODUCTS_PRICES = By.cssSelector("[class='goods-tile__price-value']");

    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(TestUtils.ROZETKA_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        driver.close();
    }

    @DataProvider(name = "searchMaxPrice")
    public Object[][] createPriceData() {
        return new Object[][]{
                {5000, 5000},
                {20000, 20000},
                {9000, 9000},
        };
    }

    @Test(dataProvider = "searchMaxPrice")
    public void productPriceIsLessThanUpperPriceBound(Integer upperPrice, Integer expectedValue) {
        TestUtils.findFirstSubCategoryOfCatalog(driver);
        setUpperPriceWithRetry(upperPrice, 10, INPUT_MAX_PRICE);
        driver.findElement(PRICE_FILTER_OK_BUTTON).click();

        List<String> prices = findElementWithRetry(ONE_PAGE_PRODUCTS_PRICES, 10);

        for (String actualPrice : prices) {
            String cleansedPrice = actualPrice
                    .replaceAll("₴", "")
                    .replaceAll(" ", "");
            Assert.assertTrue(new Integer(cleansedPrice) < expectedValue);
        }
    }

    private void setUpperPriceWithRetry(Integer upperPrice, int retryMax, By locator) {
        for (int i = 0; i < retryMax; i++) {
            try {
                System.out.println("Retry attempt: " + i);
                WebElement maxPriceInput = driver.findElement(INPUT_MAX_PRICE);
                maxPriceInput.clear();
                maxPriceInput.sendKeys(upperPrice.toString());
                return;
            } catch (StaleElementReferenceException ex) {
                System.out.println("Retry failed: " + i);
            }
        }
        throw new RuntimeException("Cannot find element: " + locator);
    }

    private List<String> findElementWithRetry(By locator, int retryMax) {
        for (int i = 0; i < retryMax; i++) {
            try {
                System.out.println("Retry attempt: " + i);
                List<WebElement> foundProducts = TestUtils.waitUntilElementsVisibility(driver, locator);
                List<String> pricesValues = new ArrayList<>();
                for (WebElement element : foundProducts) {
                    pricesValues.add(element.getText());
                }
                return pricesValues;
            } catch (StaleElementReferenceException ex) {
                System.out.println("Retry failed: " + i);
            }
        }
        throw new RuntimeException("Cannot find element: " + locator);
    }
}
