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
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class RegistrationNewUserTesting {
    private final By REGISTRATION_BUTTON_ON_HOME_PAGE = By.xpath("//rz-user[@class='header-actions__component']//button[@class='header__button ng-star-inserted']");
    private final By REGISTER_BUTTON = By.xpath("//button[text()=' Зареєструватися ']");
    private final By REGISTER_BUTTON_ON_REGISTRATION_PAGE = By.xpath("//button[text()=' Зареєструватися ']");
    private final By A_LIST_OF_EMPTY_FIELDS = By.xpath("//div[contains(@class,'type_error')]");
    private final By INCORRECT_DATA_ENTRY = By.xpath("//form-error[@class='validation-message']");
    private final By REGISTRATION_CONFIRMATION_PAGE = By.xpath("//div[@class='auth-modal']");
    private final By NAME_FIELD = By.cssSelector("[id='registerUserName']");
    private final By SURNAME_FIELD = By.cssSelector("[id='registerUserSurname']");
    private final By PHONE_NUMBER_FIELD = By.cssSelector("[id='registerUserPhone']");
    private final By EMAIL_FIELD = By.cssSelector("[id='registerUserEmail']");
    private final By PASSWORD_FIELD = By.cssSelector("[id='registerUserPassword']");
    private final String[] groups = new String[]{"positive", "negative"};
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void openBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://rozetka.com.ua/ua/");
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        driver.close();
    }

    private void waitUntilElementVisibility(By element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

    @Test(groups = "positive")
    public void registrationWithCorrectlyFilledFields() {
        String name = "Лера";
        String surname = "Секач";
        String phoneNumber = "0953005787";
        String email = "sekach873@gmail.com";
        String password = "Y9gkrqq";

        driver.findElement(REGISTRATION_BUTTON_ON_HOME_PAGE).click();
        waitUntilElementVisibility(REGISTER_BUTTON);
        driver.findElement(REGISTER_BUTTON).click();
        driver.findElement(NAME_FIELD).sendKeys(name);
        driver.findElement(SURNAME_FIELD).sendKeys(surname);
        driver.findElement(PHONE_NUMBER_FIELD).sendKeys(phoneNumber);
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(REGISTER_BUTTON_ON_REGISTRATION_PAGE).click();
        WebElement registrationConfirmationPage = driver.findElement(REGISTRATION_CONFIRMATION_PAGE);
        Assert.assertTrue(registrationConfirmationPage.isDisplayed(), "Registration was successful. ");
    }

    @Test(groups = "negative")
    public void fillingInRegistrationFieldsInEnglish() {
        String name = "Lera";
        String surname = "Sekach";
        String phoneNumber = "0953005787";
        String email = "sekach873@gmail.com";
        String password = "Y9gkrqq";

        driver.findElement(REGISTRATION_BUTTON_ON_HOME_PAGE).click();
        waitUntilElementVisibility(REGISTER_BUTTON);
        driver.findElement(REGISTER_BUTTON).click();
        driver.findElement(NAME_FIELD).sendKeys(name);
        driver.findElement(SURNAME_FIELD).sendKeys(surname);
        driver.findElement(PHONE_NUMBER_FIELD).sendKeys(phoneNumber);
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        waitUntilElementVisibility(INCORRECT_DATA_ENTRY);
        List<WebElement> incorrectDataEntryMessages = driver.findElements(INCORRECT_DATA_ENTRY);
        boolean areMessagesDisplayed = !incorrectDataEntryMessages.isEmpty();
        Assert.assertTrue(areMessagesDisplayed, "Incorrect data entry messages should be displayed.");
    }

    @Test(groups = "negative")
    public void registrationWithEmptyFields() {
        driver.findElement(REGISTRATION_BUTTON_ON_HOME_PAGE).click();
        waitUntilElementVisibility(REGISTER_BUTTON);
        driver.findElement(REGISTER_BUTTON).click();
        driver.findElement(REGISTER_BUTTON_ON_REGISTRATION_PAGE).click();
        List<WebElement> emptyFields = driver.findElements(A_LIST_OF_EMPTY_FIELDS);
        boolean areEmptyFieldsPresent = !emptyFields.isEmpty();
        Assert.assertTrue(areEmptyFieldsPresent, "Registration with empty fields should fail.");
    }
}
