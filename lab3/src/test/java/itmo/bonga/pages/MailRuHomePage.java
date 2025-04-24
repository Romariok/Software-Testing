package itmo.bonga.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailRuHomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private final By loginButtonLocator = By.xpath("/html/body/main/div[2]/div[2]/div[1]/div/div/div[1]/button");

    public MailRuHomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public LoginPage clickLoginButton() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        loginButton.click();
        return new LoginPage(driver, wait);
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginButtonLocator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}