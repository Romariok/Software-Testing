package itmo.bonga.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailRuHomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginButtonLocator = By.xpath("//button[text()='Войти']");
    private final By loginButtonAlternative = By.cssSelector("button.resplash-btn");

    public MailRuHomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public LoginPage clickLoginButton() {
        try {
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
            loginButton.click();
        } catch (Exception e) {
            System.out.println("Primary login button not found, trying alternative: " + e.getMessage());
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonAlternative));
            loginButton.click();
        }
        return new LoginPage(driver, wait);
    }

    public boolean isPageLoaded() {
        try {
            return wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(loginButtonLocator),
                ExpectedConditions.visibilityOfElementLocated(loginButtonAlternative)
            )) != null;
        } catch (Exception e) {
            return false;
        }
    }
}