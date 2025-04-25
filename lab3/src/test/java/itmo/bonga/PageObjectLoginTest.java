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
        
        boolean navigatedToSent = mailboxPage.navigateToSentFolder();
        Assertions.assertTrue(navigatedToSent, "Should be able to navigate to Sent folder");
        
        int sentEmailsCountBefore = mailboxPage.countEmailsInCurrentFolder();
        Assertions.assertTrue(sentEmailsCountBefore >= 0, "Should be able to count emails in Sent folder");
        System.out.println("Number of sent emails before sending: " + sentEmailsCountBefore);
        
        boolean navigatedToInbox = mailboxPage.navigateToInbox();
        Assertions.assertTrue(navigatedToInbox, "Should be able to navigate back to Inbox");
        
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
        
        boolean confirmationClosed = mailboxPage.closeEmailSentConfirmation();
        Assertions.assertTrue(confirmationClosed, "Should be able to close the confirmation popup");
        
        boolean navigatedToSentAgain = mailboxPage.navigateToSentFolder();
        Assertions.assertTrue(navigatedToSentAgain, "Should be able to navigate to Sent folder after sending");
        
        boolean countIncreased = mailboxPage.verifySentEmailCount(sentEmailsCountBefore);
        Assertions.assertTrue(countIncreased, "Number of sent emails should increase after sending");
        
        System.out.println("Send email to yourself test completed successfully");
    }
    
    @Test
    @DisplayName("Test opening an email and verifying its subject")
    public void testOpenEmailAndVerifySubject() {
        MailRuHomePage homePage = new MailRuHomePage(driver, wait);
        Assertions.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        
        LoginPage loginPage = homePage.clickLoginButton();
        loginPage.enterUsername(TEST_USERNAME);
        loginPage.clickNextButton();
        loginPage.enterPassword(TEST_PASSWORD);
        
        MailboxPage mailboxPage = loginPage.clickSubmitButton();
        Assertions.assertTrue(mailboxPage.isLoggedIn(), "User should be logged in");
        
        String emailSubjectBeforeClick = mailboxPage.getFirstEmailSubject();
        System.out.println("Subject of the first email before clicking: " + emailSubjectBeforeClick);
        boolean emailClicked = mailboxPage.clickFirstEmail();
        Assertions.assertTrue(emailClicked, "Should be able to click on an email");

        String clickedEmailSubject = mailboxPage.getLastClickedEmailSubject();
        Assertions.assertNotNull(clickedEmailSubject, "Clicked email subject should not be null");
        System.out.println("Subject saved when clicking: " + clickedEmailSubject);

        String openedEmailSubject = mailboxPage.getOpenedEmailSubject();
        Assertions.assertNotNull(openedEmailSubject, "Opened email subject should not be null");
        System.out.println("Subject from opened email: " + openedEmailSubject);

        boolean subjectsMatch = mailboxPage.verifyOpenedEmailMatchesClicked();
        Assertions.assertTrue(subjectsMatch, "The subjects should match");
        
        System.out.println("Open email and verify subject test completed successfully");
    }
}