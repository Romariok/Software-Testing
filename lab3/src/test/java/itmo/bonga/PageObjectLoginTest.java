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
    private final String EMAIL_SUBJECT = "chpoki chpoki";

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
    
    @Test
    @DisplayName("Test sending email to yourself after login")
    public void testSendEmailToYourself() {
        MailRuHomePage homePage = new MailRuHomePage(driver, wait);
        Assertions.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        
        LoginPage loginPage = homePage.clickLoginButton();
        loginPage.enterUsername(TEST_USERNAME);
        loginPage.clickNextButton();
        loginPage.enterPassword(TEST_PASSWORD);
        
        MailboxPage mailboxPage = loginPage.clickSubmitButton();
        Assertions.assertTrue(mailboxPage.isLoggedIn(), "User should be logged in");
        
        boolean burgerClicked = mailboxPage.clickBurgerMenu();
        Assertions.assertTrue(burgerClicked, "Burger menu button should be clickable");
        
        boolean writeToYourselfClicked = mailboxPage.clickWriteToYourself();
        Assertions.assertTrue(writeToYourselfClicked, "Write to yourself option should be clickable");
        
        boolean editorDisplayed = mailboxPage.isEmailEditorDisplayed();
        Assertions.assertTrue(editorDisplayed, "Email editor should be displayed");
        
        boolean subjectEntered = mailboxPage.enterEmailSubject(EMAIL_SUBJECT);
        Assertions.assertTrue(subjectEntered, "Should be able to enter email subject");
        
        boolean sendButtonClicked = mailboxPage.clickSendButton();
        Assertions.assertTrue(sendButtonClicked, "Send button should be clickable");
        
        if (mailboxPage.isEmptyEmailDialogDisplayed()) {
            boolean confirmSendClicked = mailboxPage.confirmSendEmptyEmail();
            Assertions.assertTrue(confirmSendClicked, "Should be able to confirm sending empty email");
        }
        
        boolean confirmationDisplayed = mailboxPage.isEmailSentConfirmationDisplayed();
        Assertions.assertTrue(confirmationDisplayed, "Email sent confirmation should be displayed");
        
        System.out.println("Send email to yourself test completed successfully");
    }
}