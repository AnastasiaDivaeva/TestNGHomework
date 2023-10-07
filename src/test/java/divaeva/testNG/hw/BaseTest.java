package divaeva.testNG.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {

    private static final By CATALOG_BUTTON = By.xpath("//ul[@class='menu-categories menu-categories_type_main']");
    private static final By CATEGORY_OF_CATALOG = By.cssSelector(".menu-categories__item.ng-star-inserted");
    private static final By SUB_CATEGORY = By.xpath("//li[@class='portal-grid__cell ng-star-inserted']");

    public static final String ROZETKA_URL = "https://rozetka.com.ua/ua/";
    protected WebDriver driver;

    protected WebDriverWait wait;

    @BeforeMethod(alwaysRun = true)
    public void openBrowser() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.navigate().to(BaseTest.ROZETKA_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        driver.close();
    }

    public void clickFirstSubCategoryInCatalog() {
        driver
                .findElement(CATALOG_BUTTON)
                .findElement(CATEGORY_OF_CATALOG).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(SUB_CATEGORY)).click();
    }
}
