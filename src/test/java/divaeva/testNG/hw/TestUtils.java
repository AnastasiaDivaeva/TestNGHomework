package divaeva.testNG.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TestUtils {

    private static final By CATALOG_BUTTON = By.xpath("//ul[@class='menu-categories menu-categories_type_main']");
    private static final By CATEGORY_OF_CATALOG = By.cssSelector(".menu-categories__item.ng-star-inserted");
    private static final By SUB_CATEGORY = By.xpath("//li[@class='portal-grid__cell ng-star-inserted']");

    public static final String ROZETKA_URL = "https://rozetka.com.ua/ua/";

    public static WebElement waitUntilElementVisibility(WebDriver driver, By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    public static List<WebElement> waitUntilElementsVisibility(WebDriver driver, By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(element));
    }

    public static List<WebElement> waitUntilElementsPresence(WebDriver driver, By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(element));
    }

    public static void findFirstSubCategoryOfCatalog(WebDriver driver) {
        driver
                .findElement(CATALOG_BUTTON)
                .findElement(CATEGORY_OF_CATALOG).click();
        waitUntilElementVisibility(driver, SUB_CATEGORY).click();
    }
}
