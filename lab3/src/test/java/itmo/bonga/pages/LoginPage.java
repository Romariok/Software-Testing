package itmo.bonga.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final By usernameInputLocator = By.cssSelector("input[name='username']");
    private final By nextButtonLocator = By.cssSelector("button[data-test-id='next-button']");
    private final By passwordInputLocator = By.cssSelector("input[name='password']");
    private final By submitButtonLocator = By.cssSelector("button[data-test-id='submit-button']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputLocator));
    }

    public LoginPage enterUsername(String username) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputLocator));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        return this;
    }

    public LoginPage clickNextButton() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator));
        nextButton.click();

        // Wait for password field to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public MailboxPage clickSubmitButton() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
        submitButton.click();
        return new MailboxPage(driver, wait);
    }

    public MailboxPage login(String username, String password) {
        enterUsername(username);
        clickNextButton();
        enterPassword(password);
        return clickSubmitButton();
    }
}