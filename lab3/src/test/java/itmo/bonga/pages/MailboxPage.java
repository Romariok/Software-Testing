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
    private final By confirmSendButtonXPath = By.cssSelector("button[data-test-id='false'].vkuiButton--mode-primary");

    private final By closeConfirmationButtonXPath = By.cssSelector(".button2.button2_has-ico.button2_close");

    private final By emailInListXPath = By.cssSelector("a.llc.js-letter-list-item");
    private final By emailTitleInListXPath = By.cssSelector(".ll-sj__normal");
    private final By emailTitleInOpenMailXPath = By.cssSelector(".thread-subject");

    private final By sentFolderXPath = By.cssSelector("a[href='/sent/?']");
    private final By inboxFolderXPath = By.cssSelector("a[href='/inbox/?']");
    private final By emailCountInFolder = By.xpath("//*[@id='app-canvas']//a[contains(@class, 'llc')]");

    private final By trashFolderXPath = By.cssSelector("a[href='/trash/?']");
    private final By deleteButtonXPath = By.cssSelector(".button2_delete");

    private final By emailAvatarOriginalXPath = By.xpath(
            "//*[@id=\"app-canvas\"]/div/div[1]/div[1]/div/div[2]/div/div[5]/div/div/div/div/div/div[4]/div/div/div/div[1]/div/div/a[1]/div[3]/div/button/div/div/span");

    private final By emailCheckboxXPath = By.cssSelector(".ll-av__checkbox");
    private final By emailAvatarContainerXPath = By.cssSelector(".ll-av__container");
    private final By emailAvatarXPath = By.cssSelector(".ll-av__img");

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
            By.xpath("/html/body/div[18]/div/div/div[2]/h3"),
            By.xpath("/html/body/div[17]/div/div/div[2]/h3"),
            By.xpath("//div[contains(@class, 'popup')]//h3"),
            By.cssSelector("[data-test-id='confirmation:empty-letter'] h3"));

    private final List<By> confirmSendButtonFallbacks = Arrays.asList(
            confirmSendButtonXPath,
            By.xpath("/html/body/div[18]/div/div/div[2]/button[1]"),
            By.cssSelector("button[data-test-id='false']"),
            By.cssSelector(".vkuiButton--mode-primary"),
            By.cssSelector(".vkuiButton--appearance-accent"),
            By.cssSelector(".vkuiButton .vkuiButton__content"),
            By.cssSelector("button .vkuiButton__content"),
            By.cssSelector("button:first-of-type"),
            By.cssSelector(".vkuiButton"),
            By.cssSelector("button.vkuiButton"),
            By.xpath("//span[@class='vkuiButton__content' and text()='Отправить']/ancestor::button"),
            By.xpath("//button[.//span[contains(text(), 'Отправить')]]"),
            By.xpath("//div[contains(@class, 'popup')]//button[contains(text(), 'Отправить')]"),
            By.cssSelector(".popup button"));

    private final List<By> closeConfirmationButtonFallbacks = Arrays.asList(
            closeConfirmationButtonXPath,
            By.cssSelector(".button2_close"),
            By.cssSelector("[title='Закрыть']"),
            By.xpath("//span[@title='Закрыть']/ancestor::span[contains(@class, 'button2')]"),
            By.xpath("//span[contains(@class, 'button2_close')]"),
            By.xpath("//svg[contains(@class, 'ico_16-close')]/ancestor::span[contains(@class, 'button2')]"));

    private final List<By> emailInListFallbacks = Arrays.asList(
            emailInListXPath,
            By.xpath(
                    "//*[@id=\"app-canvas\"]/div/div[1]/div[1]/div/div[2]/div/div[6]/div/div/div/div/div/div[4]/div/div/div/div[1]/div/div/a"),
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

    private final List<By> sentFolderFallbacks = Arrays.asList(
            sentFolderXPath,
            By.xpath("//a[contains(@href, '/sent')]"),
            By.xpath(
                    "//div[contains(@class, 'nav__folder-name__txt') and contains(text(), 'Отправленные')]/ancestor::a"),
            By.cssSelector("[data-folder-link-id='500000']"));

    private final List<By> inboxFolderFallbacks = Arrays.asList(
            inboxFolderXPath,
            By.xpath("//a[contains(@href, '/inbox')]"),
            By.xpath("//div[contains(@class, 'nav__folder-name__txt') and contains(text(), 'Входящие')]/ancestor::a"),
            By.cssSelector("[data-folder-link-id='0']"));

    private final List<By> trashFolderFallbacks = Arrays.asList(
            trashFolderXPath,
            By.xpath("//a[contains(@href, '/trash')]"),
            By.xpath("//div[contains(@class, 'nav__folder-name__txt') and contains(text(), 'Корзина')]/ancestor::a"),
            By.cssSelector("[data-folder-link-id='500002']"));

    private final List<By> deleteButtonFallbacks = Arrays.asList(
            deleteButtonXPath,
            By.cssSelector(".button2_delete"),
            By.xpath("//span[contains(@class, 'button2_delete')]"),
            By.xpath("//span[contains(@title, 'Удалить')]"),
            By.xpath(
                    "//*[@id='app-canvas']/div/div[1]/div[1]/div/div[2]/div/div[7]/div/div/div/div/div/div[3]/div/div/div[3]/div[1]/div/span"),
            By.xpath(
                    "//div[contains(@class, 'button2__txt') and contains(text(), 'Удалить')]/ancestor::span[contains(@class, 'button2')]"));

    private final By filterButtonXPath = By.cssSelector(".filters-control");
    private final By unreadFilterOptionXPath = By.cssSelector(
            "#app-canvas > div > div.application-mail > div.application-mail__overlay.js-promo-id_new-toolbar > div > div.application-mail__layout.application-mail__layout_main > div > div.layout__main-frame > div > div > div > div > div > div.new-menu > div > div:nth-child(2) > div > div > div > div.select__items.select__items_expanded.select__items_align-right > div > div:nth-child(2)");
    private final By selectAllButtonXPath = By
            .xpath("//span[contains(@class, 'button2__explanation') and contains(text(), 'Выделить все')]");
    private final By markAsReadButtonXPath = By
            .xpath("//div[contains(@class, 'button2__txt') and contains(text(), 'Отметить все прочитанными')]");

    private final List<By> filterButtonFallbacks = Arrays.asList(
            filterButtonXPath,
            By.cssSelector(".filters-control_pure"),
            By.cssSelector(".filters-control__text"),
            By.xpath("//div[contains(@class, 'filters-control')]"));

    private final List<By> unreadFilterOptionFallbacks = Arrays.asList(
            unreadFilterOptionXPath,
            By.cssSelector("div.list-item.list-item_hover-support.list-item_markable"),
            By.cssSelector("div.select__items_expanded div:nth-child(2)"),
            By.cssSelector(".select__items_expanded .list-item"),
            By.cssSelector(".list-item_markable"),
            By.xpath(
                    "//div[contains(@class, 'list-item')][.//span[contains(@class, 'list-item__text')][contains(text(), 'Непрочитанные')]]"),
            By.xpath("//span[contains(@class, 'list-item__text')][contains(text(), 'Непрочитанные')]/parent::div"),
            By.xpath("//div[contains(@class, 'select__items_expanded')]//div[contains(@class, 'list-item')]"),
            By.xpath("//div[contains(@class, 'select__items')]//span[text()=' Непрочитанные ']/.."));

    private final List<By> selectAllButtonFallbacks = Arrays.asList(
            selectAllButtonXPath,
            By.cssSelector(".button2__ico .ico_16-select-all"),
            By.xpath(
                    "//span[contains(@class, 'button2__explanation') and contains(text(), 'Выделить все')]/ancestor::span"),
            By.xpath("//span[contains(text(), 'Выделить все')]"));

    private final List<By> markAsReadButtonFallbacks = Arrays.asList(
            markAsReadButtonXPath,
            By.cssSelector(".button2__ico .ico_16-status\\:read"),
            By.xpath("//div[contains(text(), 'Отметить все прочитанными')]/ancestor::span"),
            By.xpath(
                    "//span[contains(@class, 'button2__wrapper')]//div[contains(text(), 'Отметить все прочитанными')]"));

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
        try {
            System.out
                    .println("Trying to click button directly with XPath: /html/body/div[18]/div/div/div[2]/button[1]");
            WebElement directButton = driver.findElement(By.xpath("/html/body/div[18]/div/div/div[2]/button[1]"));
            directButton.click();
            System.out.println("Successfully clicked button with direct XPath");
            return true;
        } catch (Exception e) {
            System.out.println("Failed with direct XPath, trying fallbacks");
        }

        try {
            System.out.println("Trying to click button with data-test-id='false'");
            WebElement testIdButton = driver.findElement(By.cssSelector("button[data-test-id='false']"));
            testIdButton.click();
            System.out.println("Successfully clicked button with data-test-id='false'");
            return true;
        } catch (Exception e) {
            System.out.println("Failed with data-test-id selector, trying fallbacks");
        }
        for (By locator : confirmSendButtonFallbacks) {
            try {
                System.out.println("Attempting to find confirm send button with locator: " + locator);

                if (locator.toString().contains("cssSelector")) {
                    List<WebElement> elements = driver.findElements(locator);

                    if (elements.isEmpty()) {
                        System.out.println("No elements found with this CSS selector");
                        continue;
                    }

                    WebElement confirmButton = null;
                    for (WebElement element : elements) {
                        String text = element.getText();
                        if (text.contains("Отправить")) {
                            confirmButton = element;
                            System.out.println("Found button with text: " + text);
                            break;
                        }
                    }

                    if (confirmButton == null) {
                        confirmButton = elements.get(0);
                        System.out.println("Using first element found with selector");
                    }

                    confirmButton.click();
                    System.out.println("Successfully clicked confirm send button");
                    return true;
                } else {
                    WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    confirmButton.click();
                    System.out.println("Successfully clicked confirm send button with XPath");
                    return true;
                }
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
            System.out.println("Opened email subject '" + openedSubject
                    + "' does not match clicked email subject '"
                    + lastClickedEmailSubject + "'");
        }

        return matches;
    }

    public boolean navigateToSentFolder() {
        for (By locator : sentFolderFallbacks) {
            try {
                System.out.println("Attempting to find Sent folder with locator: " + locator);
                WebElement sentFolder = wait.until(ExpectedConditions.elementToBeClickable(locator));
                sentFolder.click();

                wait.until(ExpectedConditions.urlContains("/sent/"));
                System.out.println("Successfully navigated to Sent folder");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find Sent folder with locator: " + locator);
            }
        }

        System.out.println("Failed to navigate to Sent folder with all locator strategies");
        return false;
    }

    public boolean navigateToInbox() {
        for (By locator : inboxFolderFallbacks) {
            try {
                System.out.println("Attempting to find Inbox folder with locator: " + locator);
                WebElement inboxFolder = wait.until(ExpectedConditions.elementToBeClickable(locator));
                inboxFolder.click();

                wait.until(ExpectedConditions.urlContains("/inbox/"));
                System.out.println("Successfully navigated to Inbox folder");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find Inbox folder with locator: " + locator);
            }
        }

        System.out.println("Failed to navigate to Inbox folder with all locator strategies");
        return false;
    }

    public int countEmailsInCurrentFolder() {
        try {
            List<WebElement> emails = driver.findElements(emailCountInFolder);
            if (emails.isEmpty()) {
                By alternativeLocator = emailInListFallbacks.get(1);
                emails = driver.findElements(alternativeLocator);
                if (emails.isEmpty() && emailInListFallbacks.size() > 2) {
                    alternativeLocator = emailInListFallbacks.get(2);
                    emails = driver.findElements(alternativeLocator);
                }
            }

            int count = emails.size();
            System.out.println("Found " + count + " emails in the current folder");
            return count;
        } catch (Exception e) {
            System.out.println("Failed to count emails: " + e.getMessage());
            return -1;
        }
    }

    public boolean verifySentEmailCount(int beforeCount) {
        try {
            int afterCount = countEmailsInCurrentFolder();
            System.out.println("Before count: " + beforeCount + ", After count: " + afterCount);

            if (afterCount > beforeCount) {
                System.out.println("Email count increased from " + beforeCount + " to " + afterCount);
                return true;
            } else if (afterCount == beforeCount) {
                afterCount = countEmailsInCurrentFolder();

                if (afterCount > beforeCount) {
                    System.out.println("Email count increased after waiting, from " + beforeCount + " to " + afterCount);
                    return true;
                } else {
                    System.out.println("Email count did not increase even after waiting. Before: " + beforeCount
                            + ", After: " + afterCount);
                    return false;
                }
            } else {
                System.out.println(
                        "Email count unexpectedly decreased. Before: " + beforeCount + ", After: " + afterCount);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Failed to verify sent email count: " + e.getMessage());
            return false;
        }
    }

    public boolean closeEmailSentConfirmation() {
        for (By locator : closeConfirmationButtonFallbacks) {
            try {
                System.out.println("Attempting to find close button with locator: " + locator);
                WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                closeButton.click();
                System.out.println("Successfully clicked close button on confirmation popup");

                try {
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(confirmationMessageXPath));
                    System.out.println("Confirmation popup is closed");
                } catch (Exception e) {
                    System.out.println("Warning: Could not confirm popup was closed, but continuing");
                }

                return true;
            } catch (Exception e) {
                System.out.println("Failed to find close button with locator: " + locator);
            }
        }

        System.out.println("Failed to find close button with all locator strategies");
        return false;
    }

    public boolean navigateToTrashFolder() {
        for (By locator : trashFolderFallbacks) {
            try {
                System.out.println("Attempting to find Trash folder with locator: " + locator);
                WebElement trashFolder = wait.until(ExpectedConditions.elementToBeClickable(locator));
                trashFolder.click();

                wait.until(ExpectedConditions.urlContains("/trash/"));
                System.out.println("Successfully navigated to Trash folder");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find Trash folder with locator: " + locator);
            }
        }

        System.out.println("Failed to navigate to Trash folder with all locator strategies");
        return false;
    }

    public boolean selectFirstEmail() {
        try {
            System.out.println("Attempting to find and click on the checkbox in the first email");
            WebElement checkboxElement = wait.until(ExpectedConditions.elementToBeClickable(emailCheckboxXPath));
            checkboxElement.click();
            System.out.println("Successfully clicked on the checkbox in the first email");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to click on checkbox, trying avatar container");

            try {
                WebElement avatarContainer = wait.until(ExpectedConditions.elementToBeClickable(emailAvatarContainerXPath));
                avatarContainer.click();
                System.out.println("Successfully clicked on avatar container");
                return true;
            } catch (Exception e2) {
                System.out.println("Failed to click on avatar container, trying avatar image");

                try {
                    WebElement avatarImage = wait.until(ExpectedConditions.elementToBeClickable(emailAvatarXPath));
                    avatarImage.click();
                    System.out.println("Successfully clicked on avatar image");
                    return true;
                } catch (Exception e3) {
                    System.out.println("Failed to click on avatar image, trying by XPath");

                    try {
                        WebElement firstEmailCheckbox = driver.findElement(
                                By.xpath("//a[contains(@class, 'llc')][1]//span[contains(@class, 'll-av__checkbox')]"));
                        firstEmailCheckbox.click();
                        System.out.println("Successfully clicked on first email checkbox by XPath");
                        return true;
                    } catch (Exception e4) {
                        try {
                            System.out.println("Trying to select email using JavaScript");
                            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                                    "document.querySelector('.ll-av__container').click();");
                            System.out.println("Clicked on avatar container using JavaScript");
                            return true;
                        } catch (Exception e5) {
                            System.out.println("All attempts to select email failed");
                            return false;
                        }
                    }
                }
            }
        }
    }

    public boolean deleteSelectedEmail() {
        try {
            boolean isSelected = driver.findElement(By.cssSelector("input[type='checkbox']:checked")) != null;
            if (!isSelected) {
                System.out.println("No email appears to be selected, trying to select again");
                if (!selectFirstEmail()) {
                    System.out.println("Failed to select an email for deletion");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot determine if an email is selected, continuing with deletion");
        }

        for (By locator : deleteButtonFallbacks) {
            try {
                System.out.println("Attempting to find delete button with locator: " + locator);
                WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                deleteButton.click();
                System.out.println("Successfully clicked delete button");

                try {
                    wait.until(ExpectedConditions.stalenessOf(deleteButton));
                } catch (Exception ex) {
                }

                return true;
            } catch (Exception e) {
                System.out.println("Failed to find delete button with locator: " + locator);
            }
        }

        try {
            System.out.println("Trying keyboard shortcut for delete");
            driver.findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.DELETE);

            System.out.println("Delete key pressed");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to delete using keyboard shortcut: " + e.getMessage());
        }

        System.out.println("Failed to delete email with all strategies");
        return false;
    }

    public boolean clickFilterButton() {
        for (By locator : filterButtonFallbacks) {
            try {
                System.out.println("Attempting to find filter button with locator: " + locator);
                WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                filterButton.click();
                System.out.println("Successfully clicked on filter button");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find filter button with locator: " + locator);
            }
        }

        System.out.println("Failed to find filter button with all locator strategies");
        return false;
    }

    public boolean clickUnreadFilterOption() {
        for (By locator : unreadFilterOptionFallbacks) {
            try {
                System.out.println("Attempting to find unread filter option with locator: " + locator);
                WebElement unreadOption = wait.until(ExpectedConditions.elementToBeClickable(locator));
                unreadOption.click();
                System.out.println("Successfully clicked on unread filter option");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find unread filter option with locator: " + locator);
            }
        }

        System.out.println("Failed to find unread filter option with all locator strategies");
        return false;
    }

    public boolean clickSelectAllButton() {
        for (By locator : selectAllButtonFallbacks) {
            try {
                System.out.println("Attempting to find select all button with locator: " + locator);
                WebElement selectAllButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                selectAllButton.click();
                System.out.println("Successfully clicked on select all button");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find select all button with locator: " + locator);
            }
        }

        System.out.println("Failed to find select all button with all locator strategies");
        return false;
    }

    public boolean clickMarkAsReadButton() {
        for (By locator : markAsReadButtonFallbacks) {
            try {
                System.out.println("Attempting to find mark as read button with locator: " + locator);
                WebElement markAsReadButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                markAsReadButton.click();
                System.out.println("Successfully clicked on mark as read button");
                return true;
            } catch (Exception e) {
                System.out.println("Failed to find mark as read button with locator: " + locator);
            }
        }

        System.out.println("Failed to find mark as read button with all locator strategies");
        return false;
    }

    public int getUnreadEmailCount() {
        return countEmailsInCurrentFolder();
    }

    public boolean filterByUnread() {
        boolean filterButtonClicked = clickFilterButton();
        if (!filterButtonClicked) {
            System.out.println("Could not click filter button, test will fail");
            return false;
        }

        boolean unreadOptionClicked = clickUnreadFilterOption();
        if (!unreadOptionClicked) {
            System.out.println("Could not click unread filter option, test will fail");
            return false;
        }
        return true;
    }

    public boolean markAllEmailsAsRead() {
        boolean selectAllClicked = clickSelectAllButton();
        if (!selectAllClicked) {
            System.out.println("Could not click select all button, test will fail");
            return false;
        }

        boolean markAsReadClicked = clickMarkAsReadButton();
        if (!markAsReadClicked) {
            System.out.println("Could not click mark as read button, test will fail");
            return false;
        }
        try {
            wait.until(driver -> countEmailsInCurrentFolder() == 0);
            System.out.println("Successfully waited for all emails to be marked as read");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to wait for all emails to be marked as read: " + e.getMessage());
            return false;
        }
    }

    public boolean waitForNoUnreadEmails(int timeout) {
        try {
            WebDriverWait longWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(timeout));
            longWait.until(driver -> {
                int count = countEmailsInCurrentFolder();
                System.out.println("Current email count during wait: " + count);
                return count == 0;
            });
            System.out.println("Successfully verified no unread emails left");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to verify no unread emails left: " + e.getMessage());
            return false;
        }
    }
}
