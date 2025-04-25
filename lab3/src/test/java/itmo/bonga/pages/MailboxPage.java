package itmo.bonga.pages;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailboxPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Primary locators
    private final By burgerMenuButtonXPath = By.cssSelector(".button2_navigation_drop_down");
    private final By writeToYourselfOptionXPath = By.xpath(
            "//*[@id=\"app-canvas\"]/div/div[1]/div[1]/div/div[2]/div/div[4]/div[1]/div/div/span/div/div[1]/div/div/div/div/div/div/div[1]");
    private final By emailEditorSignatureXPath = By.xpath(
            "/html/body/div[1]/div/div[2]/div/div/div/div[2]/div[3]/div[4]/div/div/div[2]/div[1]/div[3]/div/div[2]/div");

    private final By subjectInputXPath = By
            .xpath("/html/body/div[1]/div/div[2]/div/div/div/div[2]/div[3]/div[2]/div/div[2]/div/input");
    private final By sendButtonXPath = By
            .xpath("/html/body/div[1]/div/div[2]/div/div/div/div[4]/div[1]/div[1]/div/button");
    private final By confirmationMessageXPath = By.xpath("//a[contains(text(), 'Письмо отправлено')]");

    private final By emptyEmailDialogXPath = By.xpath("/html/body/div[18]/div/div/div[2]/h3");
    private final By confirmSendButtonXPath = By.xpath("/html/body/div[18]/div/div/div[2]/button[1]");

    private final By emailInListXPath = By.cssSelector("a.llc.js-letter-list-item");
    private final By emailTitleInListXPath = By.cssSelector(".ll-sj__normal");
    private final By emailTitleInOpenMailXPath = By.cssSelector(".thread-subject");

    private final List<By> burgerMenuFallbacks = Arrays.asList(
            burgerMenuButtonXPath,
            By.cssSelector("[data-highlighted-class='button2_highlighted']"),
            By.xpath("//span[contains(@class, 'button2_navigation_drop_down')]"),
            By.xpath("//span[contains(@class, 'button2_rotate-ico')]"));

    private final List<By> writeToYourselfFallbacks = Arrays.asList(
            writeToYourselfOptionXPath,
            By.xpath("//div[contains(text(), 'Написать себе')]"),
            By.cssSelector(".list-item .compose-dropdown__text"),
            By.xpath("//span[text()='Написать себе']"));

    private final List<By> subjectInputFallbacks = Arrays.asList(
            subjectInputXPath,
            By.name("Subject"),
            By.cssSelector("input[name='Subject']"),
            By.cssSelector(".container--H9L5q"),
            By.xpath("//input[@type='text' and @tabindex='400']"));

    private final List<By> sendButtonFallbacks = Arrays.asList(
            sendButtonXPath,
            By.xpath("//button[contains(text(), 'Отправить')]"),
            By.cssSelector("[data-test-id='send']"),
            By.cssSelector(".vkuiButton--mode-primary"),
            By.xpath("//button[.//span[contains(text(), 'Отправить')]]"));

    private final List<By> confirmationMessageFallbacks = Arrays.asList(
            confirmationMessageXPath,
            By.xpath("//a[contains(@class, 'layer__link')]"),
            By.xpath("//*[contains(text(), 'Письмо отправлено')]"),
            By.linkText("Письмо отправлено"),
            By.partialLinkText("Письмо отправлено"));

    private final List<By> emptyEmailDialogFallbacks = Arrays.asList(
            emptyEmailDialogXPath,
            By.xpath("//h3[contains(text(), 'отправить пустое письмо')]"),
            By.xpath("//*[contains(text(), 'Вы действительно хотите отправить пустое письмо')]"),
            By.xpath("//div[contains(@class, 'popup')]//h3"),
            By.cssSelector("[data-test-id='confirmation:empty-letter'] h3"));

    private final List<By> confirmSendButtonFallbacks = Arrays.asList(
            confirmSendButtonXPath,
            By.xpath("/html/body/div[18]/div/div/div[2]/button[1]"),
            By.xpath("/html/body/div[18]/div/div/div[2]/button[1]/span"),
            By.xpath("//button[.//span[contains(text(), 'Отправить')]]"),
            By.xpath("//div[contains(@class, 'popup')]//button[contains(text(), 'Отправить')]"),
            By.cssSelector(".popup button"),
            By.xpath("//span[@class='vkuiButton__content' and text()='Отправить']/ancestor::button"),
            By.xpath("//div[18]/div/div/div[2]/button[1]"));

    private final List<By> emailInListFallbacks = Arrays.asList(
            emailInListXPath,
            By.cssSelector("a.llc"),
            By.cssSelector(".js-letter-list-item"),
            By.xpath("//a[contains(@class, 'llc')]"),
            By.xpath("//a[contains(@href, '/inbox/')]"));

    private final List<By> emailTitleInListFallbacks = Arrays.asList(
            emailTitleInListXPath,
            By.cssSelector(".llc__subject .ll-sj__normal"),
            By.xpath("//span[contains(@class, 'll-sj__normal')]"),
            By.xpath("//div[contains(@class, 'll-sj')]/span"));

    private final List<By> emailTitleInOpenMailFallbacks = Arrays.asList(
            By.cssSelector("h2.thread-subject"),
            By.xpath("//h2[@class='thread-subject']"),
            emailTitleInOpenMailXPath,
            By.cssSelector(".thread__subject"),
            By.cssSelector(".thread__subject-line"),
            By.xpath("//*[contains(@class, 'thread-subject')]"));

    public MailboxPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;

        try {
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.urlContains("e.mail.ru/inbox"),
                    ExpectedConditions.titleContains("Входящие")));
            System.out.println("Successfully detected mailbox page");
            System.out.println("Current page title: " + driver.getTitle());
        } catch (Exception e) {
            System.out.println(
                    "WARNING: Could not detect any standard mailbox elements. Current URL: " + driver.getCurrentUrl());
            System.out.println("Current page title: " + driver.getTitle());
        }
    }

    public boolean isLoggedIn() {
        try {
            return wait.until(ExpectedConditions.and(
                    ExpectedConditions.urlContains("e.mail.ru/inbox"),
                    ExpectedConditions.titleContains("Входящие"))) != null;
        } catch (Exception e) {
            System.out.println("Failed to detect if logged in: " + e.getMessage());
            return false;
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean clickBurgerMenu() {
        for (By locator : burgerMenuFallbacks) {
            try {
                System.out.println("Attempting to find burger menu with locator: " + locator);
                WebElement burgerButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                burgerButton.click();
                System.out.println("Successfully clicked on burger menu button");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find burger menu with locator: " + locator);
            }
        }

        System.out.println("Failed to find burger menu with all locator strategies");
        return false;
    }

    public boolean clickWriteToYourself() {
        for (By locator : writeToYourselfFallbacks) {
            try {
                System.out.println("Attempting to find 'Write to yourself' option with locator: " + locator);
                WebElement writeToYourselfOption = wait.until(ExpectedConditions.elementToBeClickable(locator));
                writeToYourselfOption.click();
                System.out.println("Successfully clicked on 'Write to yourself' option");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find 'Write to yourself' option with locator: " + locator);
            }
        }

        System.out.println("Failed to find 'Write to yourself' option with all locator strategies");
        return false;
    }

    public boolean isEmailEditorDisplayed() {
        try {
            WebElement emailEditor = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(emailEditorSignatureXPath));
            return emailEditor.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed to detect email editor: " + e.getMessage());
            return false;
        }
    }

    public boolean writeEmailToYourself() {
        boolean burgerClicked = clickBurgerMenu();
        if (!burgerClicked) {
            System.out.println("Could not click burger menu, test will fail");
            return false;
        }

        boolean writeToYourselfClicked = clickWriteToYourself();
        if (!writeToYourselfClicked) {
            System.out.println("Could not click 'Write to yourself' option, test will fail");
            return false;
        }

        return isEmailEditorDisplayed();
    }

    public boolean enterEmailSubject(String subject) {
        for (By locator : subjectInputFallbacks) {
            try {
                System.out.println("Attempting to find subject input with locator: " + locator);
                WebElement subjectInput = wait.until(ExpectedConditions.elementToBeClickable(locator));
                subjectInput.clear();
                subjectInput.sendKeys(subject);
                System.out.println("Successfully entered subject: " + subject);
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find subject input with locator: " + locator);
            }
        }

        System.out.println("Failed to find subject input with all locator strategies");
        return false;
    }

    public boolean clickSendButton() {
        for (By locator : sendButtonFallbacks) {
            try {
                System.out.println("Attempting to find send button with locator: " + locator);
                WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                sendButton.click();
                System.out.println("Successfully clicked send button");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find send button with locator: " + locator);
            }
        }

        System.out.println("Failed to find send button with all locator strategies");
        return false;
    }

    public boolean isEmptyEmailDialogDisplayed() {
        for (By locator : emptyEmailDialogFallbacks) {
            try {
                System.out.println("Checking for empty email dialog with locator: " + locator);
                WebElement dialog = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                boolean isDisplayed = dialog.isDisplayed();
                if (isDisplayed) {
                    System.out.println("Empty email confirmation dialog is displayed");
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Empty email dialog not found with locator: " + locator);
            }
        }

        return false;
    }

    public boolean confirmSendEmptyEmail() {
        for (By locator : confirmSendButtonFallbacks) {
            try {
                System.out.println("Attempting to find confirm send button with locator: " + locator);
                WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                confirmButton.click();
                System.out.println("Successfully clicked confirm send button");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find confirm send button with locator: " + locator);
            }
        }

        System.out.println("Failed to find confirm send button with all locator strategies");
        return false;
    }

    public boolean isEmailSentConfirmationDisplayed() {
        for (By locator : confirmationMessageFallbacks) {
            try {
                System.out.println("Attempting to find confirmation message with locator: " + locator);
                WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                boolean isDisplayed = confirmationMessage.isDisplayed();
                if (isDisplayed) {
                    System.out.println("Successfully found confirmation message: " + confirmationMessage.getText());
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Failed to find confirmation message with locator: " + locator);
            }
        }

        System.out.println("Failed to find confirmation message with all locator strategies");
        return false;
    }

    public boolean sendEmailToYourself(String subject) {
        if (!writeEmailToYourself()) {
            return false;
        }

        if (!enterEmailSubject(subject)) {
            return false;
        }

        if (!clickSendButton()) {
            return false;
        }

        if (isEmptyEmailDialogDisplayed()) {
            if (!confirmSendEmptyEmail()) {
                return false;
            }
        }

        return isEmailSentConfirmationDisplayed();
    }

    public String getFirstEmailSubject() {
        for (By locator : emailInListFallbacks) {
            try {
                System.out.println("Attempting to find emails in list with locator: " + locator);
                WebElement firstEmail = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

                for (By titleLocator : emailTitleInListFallbacks) {
                    try {
                        WebElement titleElement = firstEmail.findElement(titleLocator);
                        String subjectText = titleElement.getText();
                        System.out.println("Successfully found email subject: " + subjectText);
                        return subjectText;
                    } catch (Exception e) {
                        System.out.println("Failed to find title with locator: " + titleLocator + " in email");
                    }
                }

                break;
            } catch (Exception e) {
                System.out.println("Failed to find emails with locator: " + locator);
            }
        }

        System.out.println("Failed to get any email subject");
        return null;
    }

    public boolean clickFirstEmail() {
        WebElement clickedEmail = null;
        String emailSubject = null;

        for (By locator : emailInListFallbacks) {
            try {
                System.out.println("Attempting to find emails in list with locator: " + locator);
                clickedEmail = wait.until(ExpectedConditions.elementToBeClickable(locator));
                for (By titleLocator : emailTitleInListFallbacks) {
                    try {
                        WebElement titleElement = clickedEmail.findElement(titleLocator);
                        emailSubject = titleElement.getText();
                        System.out.println("Email with subject: '" + emailSubject + "' will be clicked");
                        break;
                    } catch (Exception e) {
                        System.out.println("Failed to find title with locator: " + titleLocator + " in email");
                    }
                }

                clickedEmail.click();
                System.out.println("Successfully clicked on first email");

                this.lastClickedEmailSubject = emailSubject;
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find or click email with locator: " + locator);
            }
        }

        System.out.println("Failed to click on any email");
        return false;
    }

    private String lastClickedEmailSubject;

    public String getLastClickedEmailSubject() {
        return lastClickedEmailSubject;
    }

    public String getOpenedEmailSubject() {
        for (By locator : emailTitleInOpenMailFallbacks) {
            try {
                System.out.println("Attempting to find email subject in opened mail with locator: " + locator);
                WebElement subjectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                String subjectText = subjectElement.getText();
                System.out.println("Successfully found opened email subject: " + subjectText);
                return subjectText;
            } catch (Exception e) {
                System.out.println("Failed to find opened email subject with locator: " + locator);
            }
        }

        System.out.println("Failed to get opened email subject");
        return null;
    }

    public boolean verifyOpenedEmailMatchesClicked() {
        if (lastClickedEmailSubject == null) {
            System.out.println("No email was clicked previously");
            return false;
        }

        String openedSubject = getOpenedEmailSubject();
        if (openedSubject == null) {
            System.out.println("Couldn't get the opened email subject");
            return false;
        }

        System.out.println(
                "Comparing subjects - Clicked: '" + lastClickedEmailSubject + "', Opened: '" + openedSubject + "'");

        boolean exactMatch = openedSubject.equals(lastClickedEmailSubject);
        boolean openedContainsClicked = openedSubject.contains(lastClickedEmailSubject);
        boolean clickedContainsOpened = lastClickedEmailSubject.contains(openedSubject);

        boolean matches = exactMatch || openedContainsClicked || clickedContainsOpened;

        if (matches) {
            System.out.println("Opened email subject matches clicked email subject (using flexible comparison)");
            if (exactMatch) {
                System.out.println("Exact match!");
            } else if (openedContainsClicked) {
                System.out.println("Opened subject contains clicked subject");
            } else if (clickedContainsOpened) {
                System.out.println("Clicked subject contains opened subject");
            }
        } else {
            System.out.println("Opened email subject '" + openedSubject +
                    "' does not match clicked email subject '" +
                    lastClickedEmailSubject + "'");
        }

        return matches;
    }
}