package divaeva.testNG.hw;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class RegistrationNewUserTest {
    private final By REGISTRATION_BUTTON_ON_HOME_PAGE = By.xpath("//rz-user[@class='header-actions__component']" +
            "//button[@class='header__button ng-star-inserted']");
    private final By REGISTER_BUTTON = By.xpath("//button[text()=' Зареєструватися ']");
    private final By EMPTY_REGISTRATION_FORM_FIELDS = By.xpath("//div[contains(@class,'type_error')]");
    private final By VALIDATION_ERROR_MESSAGES = By.xpath("//form-error[@class='validation-message']");
    private final By REGISTRATION_CONFIRMATION_PAGE = By.xpath("//div[@class='auth-modal']");
    private final By NAME_FIELD = By.cssSelector("[id='registerUserName']");
    private final By SURNAME_FIELD = By.cssSelector("[id='registerUserSurname']");
    private final By PHONE_NUMBER_FIELD = By.cssSelector("[id='registerUserPhone']");
    private final By EMAIL_FIELD = By.cssSelector("[id='registerUserEmail']");
    private final By PASSWORD_FIELD = By.cssSelector("[id='registerUserPassword']");

    private final String PHONE_NUMBER_FIELD_VALUE = "0953005787";
    private final String EMAIL_FIELD_VALUE = "sekach873@gmail.com";
    private final String PASSWORD_FIELD_VALUE = "Y9gkrqq";

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

    private void fillInRegistrationInfo(String name, String surname, String phoneNumber, String email, String password) {
        driver.findElement(REGISTRATION_BUTTON_ON_HOME_PAGE).click();
        TestUtils.waitUntilElementVisibility(driver, REGISTER_BUTTON).click();

        driver.findElement(NAME_FIELD).sendKeys(name);
        driver.findElement(SURNAME_FIELD).sendKeys(surname);
        driver.findElement(PHONE_NUMBER_FIELD).sendKeys(phoneNumber);
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(REGISTER_BUTTON).click();
    }

    @Test(groups = "positive")
    public void registrationWithCyrillicFilledFields() {
        fillInRegistrationInfo("Лера", "Секач", PHONE_NUMBER_FIELD_VALUE, EMAIL_FIELD_VALUE, PASSWORD_FIELD_VALUE);

        WebElement registrationConfirmationPage = driver.findElement(REGISTRATION_CONFIRMATION_PAGE);
        Assert.assertTrue(registrationConfirmationPage.isDisplayed(), "Registration was successful.");
    }

    @Test(groups = "negative")
    public void registrationWithLatinFilledFields() {
        fillInRegistrationInfo("Lera", "Sekach", PHONE_NUMBER_FIELD_VALUE, EMAIL_FIELD_VALUE, PASSWORD_FIELD_VALUE);
        String expectedNameFieldError = "Введіть своє ім'я кирилицею";
        String expectedLastNameFieldError = "Введіть своє прізвище кирилицею";

        List<WebElement> validationErrorMessages = TestUtils.waitUntilElementsPresence(driver, VALIDATION_ERROR_MESSAGES);

        boolean isNameErrorFound = false;
        boolean isLastNameErrorFound = false;
        for (WebElement validationErrorMessage : validationErrorMessages) {
            if (validationErrorMessage.getText().equals(expectedNameFieldError)) {
                isNameErrorFound = true;
            }
            if (validationErrorMessage.getText().equals(expectedLastNameFieldError)) {
                isLastNameErrorFound = true;
            }
        }

        Assert.assertTrue(isNameErrorFound, "Name field's validation error message is not displayed");
        Assert.assertTrue(isLastNameErrorFound, "Last Name field's validation error message is not displayed");
    }

    @Test(groups = "negative")
    public void registrationWithEmptyFields() {
        driver.findElement(REGISTRATION_BUTTON_ON_HOME_PAGE).click();
        TestUtils.waitUntilElementVisibility(driver, REGISTER_BUTTON).click();
        driver.findElement(REGISTER_BUTTON).click();

        List<WebElement> emptyFormFields = driver.findElements(EMPTY_REGISTRATION_FORM_FIELDS);
        boolean isEmptyFieldsPresent = !emptyFormFields.isEmpty();
        Assert.assertTrue(isEmptyFieldsPresent, "Registration with empty fields should fail.");
    }
}
