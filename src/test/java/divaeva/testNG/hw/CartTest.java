package divaeva.testNG.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    private final By PRODUCT_LINK = By.xpath("//a[@class='goods-tile__picture ng-star-inserted']" +
            "[@href=\"https://rozetka.com.ua/ua/acer-nhqhheu00g/p389616942/\"]");
    private final By PRODUCT_TITLE = By.cssSelector("[class='product__title-left product__title-collapsed ng-star-inserted']");
    private final By BUY_BUTTON = By.xpath("//button[@type='button'][@aria-label='Купити']");
    private final By PRODUCT_TITLE_IN_CART = By.cssSelector("[class='cart-product__title']");

    @Test
    public void addingProductToTheCart() {
        clickFirstSubCategoryInCatalog();
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_LINK)).click();
        String expectedProductTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE)).getText();

        Actions actions = new Actions(driver);
        WebElement buyButton = driver.findElement(BUY_BUTTON);
        actions.moveToElement(buyButton).perform();
        buyButton.click();

        String actualTitleProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE_IN_CART)).getText();

        Assert.assertEquals(actualTitleProduct, expectedProductTitle);
    }
}


