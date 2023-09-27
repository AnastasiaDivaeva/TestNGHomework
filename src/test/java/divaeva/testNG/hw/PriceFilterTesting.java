package divaeva.testNG.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class PriceFilterTesting {
    private WebDriver driver;

    @BeforeMethod()
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://rozetka.com.ua/ua/");
    }

    @AfterMethod
    public void closeBrowser() {
        driver.close();
    }

    private WebElement waitUntilElementVisibility(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    private List<WebElement> waitUntilElementsVisibility(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
    }

    private final By SELECT_CATEGORY_IN_CATALOG = By.xpath("//li[@class='menu-categories__item ng-star-inserted']");
    private final By SELECT_SUB_CATEGORY = By.cssSelector("[class='tile-cats__picture ng-star-inserted']");
    private final By SELECT_SUB_CATEGORY_XPATH = By.xpath("//li[@class='portal-grid__cell ng-star-inserted']");
    private final By INPUT_MAX_PRICE = By.cssSelector("[formcontrolname='max']");
    private final By INPUT_MIN_PRICE = By.cssSelector("[formcontrolname='min']");
    private final By PRICE_FILTER_OK_BUTTON = By.xpath("//button[contains(text(),'Ok')]");
    private final By FILTER_PRICE = By.cssSelector("[class='catalog-selection__link']");
    private final By ONE_PAGE_PRODUCTS_PRICES = By.cssSelector("[class='goods-tile__price-value']");


    public void categorySearch() {
        driver.findElement(By.xpath("//ul[@class='menu-categories menu-categories_type_main']"))
                .findElement(By.cssSelector(".menu-categories__item.ng-star-inserted")).click();
        waitUntilElementVisibility(SELECT_SUB_CATEGORY_XPATH).click();
    }

    @DataProvider(name = "searchMaxPrice")
    public Object[][] createData1() {
        return new Object[][]{
                {5000, 5000}
        };
    }

    @Test(dataProvider = "searchMaxPrice")
    public void maxPrice(Integer searchQuery, Integer expectedValue) {
        categorySearch();
        WebElement maxPriceInput = driver.findElement(INPUT_MAX_PRICE);
        maxPriceInput.clear();
        maxPriceInput.sendKeys(searchQuery.toString());
        driver.findElement(PRICE_FILTER_OK_BUTTON).click();


        List<WebElement> prices = waitUntilElementsVisibility(ONE_PAGE_PRODUCTS_PRICES);
        for (WebElement actualPrice : prices) {
            String cleansedPrice = actualPrice.getText()
                    .replaceAll("₴", "")
                    .replaceAll(" ", "");

            Assert.assertTrue(new Integer(cleansedPrice) < expectedValue);
        }
    }

}
