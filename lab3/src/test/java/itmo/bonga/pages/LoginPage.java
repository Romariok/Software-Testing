package itmo.bonga.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By usernameInputLocator = By.xpath(
            "/html/body/div[1]/div[2]/div/div/div/div[2]/div/form/div[2]/div[2]/div[1]/div/div/div/div/div/div[1]/div/input");
    private final By nextButtonLocator = By.xpath("//*[@id=\"kjLElvf\"]/div/div[3]/div/div/div[1]/button");
    private final By passwordInputLocator = By.xpath(
            "/html/body/div[1]/div[2]/div/div/div/div[2]/div/form/div[2]/div[4]/div[4]/div[3]/div[1]/div[5]/div[1]/div/div[2]/div/div[2]/div/div/div/input");
    private final By submitButtonLocator = By.xpath(
            "/html/body/div[1]/div[2]/div/div/div/div[2]/div/form/div[2]/div[4]/div[4]/div[3]/div[1]/div[5]/div[1]/div/div[3]/div/div/div[1]/button");

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