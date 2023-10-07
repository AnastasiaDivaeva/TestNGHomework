package divaeva.testNG.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class PriceFilterTest extends BaseTest {
    private final By INPUT_MAX_PRICE = By.cssSelector("[formcontrolname='max']");
    private final By PRICE_FILTER_OK_BUTTON = By.xpath("//button[contains(text(),'Ok')]");
    private final By ONE_PAGE_PRODUCTS_PRICES = By.cssSelector("[class='goods-tile__price-value']");

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
        clickFirstSubCategoryInCatalog();
        setUpperPrice(upperPrice);
        driver.findElement(PRICE_FILTER_OK_BUTTON).click();

        List<String> prices = findElementsText(ONE_PAGE_PRODUCTS_PRICES);

        for (String actualPrice : prices) {
            String cleansedPrice = actualPrice
                    .replaceAll("₴", "")
                    .replaceAll(" ", "");
            Assert.assertTrue(new Integer(cleansedPrice) < expectedValue);
        }
    }

    private void setUpperPrice(Integer upperPrice) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        wait.until(ExpectedConditions.refreshed(webDriver -> {
            WebElement maxPriceInput = driver.findElement(INPUT_MAX_PRICE);
            maxPriceInput.clear();
            maxPriceInput.sendKeys(upperPrice.toString());
            return maxPriceInput;
        }));
    }

    private List<String> findElementsText(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        return wait.until(ExpectedConditions.refreshed(webDriver ->
                webDriver.findElements(locator)
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList())));
    }
}
