package itmo.bonga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import itmo.bonga.pages.LoginPage;
import itmo.bonga.pages.MailRuHomePage;
import itmo.bonga.pages.MailboxPage;

public class PageObjectLoginTest extends BaseTest {

    private final String TEST_USERNAME = "zphrmynxajsw@mail-craft.ru";
    private final String TEST_PASSWORD = "hCGEAGA8uxXyCZr";

    @Test
    @DisplayName("Test login to Mail.ru using step-by-step approach with Page Object Model")
    public void testLoginStepByStep() {
        MailRuHomePage homePage = new MailRuHomePage(driver, wait);
        Assertions.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");

        LoginPage loginPage = homePage.clickLoginButton();

        loginPage.enterUsername(TEST_USERNAME);

        loginPage.clickNextButton();

        loginPage.enterPassword(TEST_PASSWORD);

        MailboxPage mailboxPage = loginPage.clickSubmitButton();

        Assertions.assertTrue(mailboxPage.isLoggedIn(), "User should be logged in");

        System.out.println("Step-by-step login test using Page Object Model completed successfully");
    }
}