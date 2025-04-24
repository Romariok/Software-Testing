package itmo.bonga;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class FullLoginTest extends BaseTest {

    private final String TEST_USERNAME = "test_user";
    private final String TEST_PASSWORD = "test_password";

    @Test
    @DisplayName("Test full login process to Mail.ru")
    public void testFullLogin() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"mailbox\"]/div[1]/button")));
        loginButton.click();

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='username']")));

        usernameInput.clear();
        usernameInput.sendKeys(TEST_USERNAME);

        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-test-id='next-button']")));
        nextButton.click();

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='password']")));
        passwordInput.clear();
        passwordInput.sendKeys(TEST_PASSWORD);

        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-test-id='submit-button']")));
        signInButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".mail-App-Header")));

        System.out.println("Login successful!");
    }
}