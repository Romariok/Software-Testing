package itmo.bonga;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {
    
    @Test
    public void testClickLoginButton() {
        // Wait for the login button to be clickable
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//*[@id=\"mailbox\"]/div[1]/button")));
        
        // Click the login button
        loginButton.click();
        
        // Wait for the login form to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("input[name='username']")));
        
        System.out.println("Login button clicked successfully and login form appeared");
        
        // You can extend this test to enter username/password and click the next buttons
        // For example:
        // WebElement usernameInput = driver.findElement(By.cssSelector("input[name='username']"));
        // usernameInput.sendKeys("your_test_username");
        // 
        // WebElement submitButton = driver.findElement(By.cssSelector("button[data-test-id='next-button']"));
        // submitButton.click();
        //
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='password']")));
        // WebElement passwordInput = driver.findElement(By.cssSelector("input[name='password']"));
        // passwordInput.sendKeys("your_test_password");
        //
        // WebElement signInButton = driver.findElement(By.cssSelector("button[data-test-id='submit-button']"));
        // signInButton.click();
        
        // Assert that we're logged in (you would need to check for an element that appears when logged in)
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mail-App-Header")));
    }
} 