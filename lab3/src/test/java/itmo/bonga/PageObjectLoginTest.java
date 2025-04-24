package itmo.bonga;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;

import itmo.bonga.pages.MailRuHomePage;
import itmo.bonga.pages.LoginPage;
import itmo.bonga.pages.MailboxPage;

/**
 * Test class that demonstrates the Page Object Model pattern for testing Mail.ru login
 */
public class PageObjectLoginTest extends BaseTest {
    
    // Replace these with test credentials
    private final String TEST_USERNAME = "test_user";
    private final String TEST_PASSWORD = "test_password";
    
    @Test
    @DisplayName("Test login to Mail.ru using Page Object Model")
    public void testLoginUsingPOM() {
        // Step 1: Load and verify the home page
        MailRuHomePage homePage = new MailRuHomePage(driver, wait);
        Assertions.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        
        // Step 2: Click login button and proceed to login page
        LoginPage loginPage = homePage.clickLoginButton();
        
        // Step 3: Perform login with test credentials
        MailboxPage mailboxPage = loginPage.login(TEST_USERNAME, TEST_PASSWORD);
        
        // Step 4: Verify successful login
        Assertions.assertTrue(mailboxPage.isLoggedIn(), "User should be logged in");
        
        System.out.println("Login test using Page Object Model completed successfully");
    }
    
    @Test
    @DisplayName("Test login to Mail.ru using step-by-step approach with Page Object Model")
    public void testLoginStepByStep() {
        // Step 1: Load and verify the home page
        MailRuHomePage homePage = new MailRuHomePage(driver, wait);
        Assertions.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        
        // Step 2: Click login button and proceed to login page
        LoginPage loginPage = homePage.clickLoginButton();
        
        // Step 3: Enter username
        loginPage.enterUsername(TEST_USERNAME);
        
        // Step 4: Click next button
        loginPage.clickNextButton();
        
        // Step 5: Enter password
        loginPage.enterPassword(TEST_PASSWORD);
        
        // Step 6: Click submit button and proceed to mailbox page
        MailboxPage mailboxPage = loginPage.clickSubmitButton();
        
        // Step 7: Verify successful login
        Assertions.assertTrue(mailboxPage.isLoggedIn(), "User should be logged in");
        
        System.out.println("Step-by-step login test using Page Object Model completed successfully");
    }
} 