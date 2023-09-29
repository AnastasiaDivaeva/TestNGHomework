package divaeva.testNG.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest {
    private final By PRODUCT_LINK = By.xpath("//a[@class='goods-tile__picture ng-star-inserted']" +
            "[@href=\"https://rozetka.com.ua/ua/acer-nhqhheu00g/p389616942/\"]");
    private final By PRODUCT_TITLE = By.cssSelector("[class='product__title-left product__title-collapsed ng-star-inserted']");
    private final By BUY_BUTTON = By.xpath("//button[@type='button'][@aria-label='Купити']");
    private final By PRODUCT_TITLE_IN_CART = By.cssSelector("[class='cart-product__title']");
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

    @Test
    public void addingProductToTheCart() {
        TestUtils.findFirstSubCategoryOfCatalog(driver);
        TestUtils.waitUntilElementVisibility(driver, PRODUCT_LINK).click();
        String expectedProductTitle = TestUtils.waitUntilElementVisibility(driver, PRODUCT_TITLE).getText();

        Actions actions = new Actions(driver);
        WebElement buyButton = driver.findElement(BUY_BUTTON);
        actions.moveToElement(buyButton).perform();
        buyButton.click();

        String actualTitleProduct = TestUtils.waitUntilElementVisibility(driver, PRODUCT_TITLE_IN_CART).getText();

        Assert.assertEquals(actualTitleProduct, expectedProductTitle);
    }
}


