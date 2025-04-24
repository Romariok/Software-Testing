package itmo.bonga.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailboxPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    private final By mailAppHeaderLocator = By.cssSelector(".mail-App-Header");
    private final By composeButtonLocator = By.cssSelector("[data-test-id='compose-button']");

    private final By vkWorkMailHeaderLocator = By.cssSelector(".HeaderDesktop, header");
    private final By inboxFolderLocator = By.cssSelector(".nav-folder-name, a[href*='inbox'], .FolderListItem");
    private final By mailListLocator = By.cssSelector(".MailListItems, .mail-list, .letter-list");
    private final By userProfileLocator = By.cssSelector(".Avatar, .ProfileButton, .UserPic");
    private final By newMessageButtonLocator = By.cssSelector("button[title='Написать'], [title='New message']");
    
    private final By anyHeaderLocator = By.tagName("header");

    public MailboxPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        
        try {
            wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(mailAppHeaderLocator),
                ExpectedConditions.visibilityOfElementLocated(composeButtonLocator),
                
                ExpectedConditions.visibilityOfElementLocated(vkWorkMailHeaderLocator),
                ExpectedConditions.visibilityOfElementLocated(inboxFolderLocator),
                ExpectedConditions.visibilityOfElementLocated(mailListLocator),
                ExpectedConditions.visibilityOfElementLocated(userProfileLocator),
                ExpectedConditions.visibilityOfElementLocated(newMessageButtonLocator),
                
                ExpectedConditions.titleContains("Mail"),
                ExpectedConditions.titleContains("Почта"),
                ExpectedConditions.titleContains("inbox"),
                ExpectedConditions.titleContains("WorkMail"),
                ExpectedConditions.visibilityOfElementLocated(anyHeaderLocator)
            ));
            System.out.println("Successfully detected mailbox page");
            System.out.println("Current page title: " + driver.getTitle());
        } catch (Exception e) {
            System.out.println("WARNING: Could not detect any standard mailbox elements. Current URL: " + driver.getCurrentUrl());
            System.out.println("Current page title: " + driver.getTitle());
        }
    }

    public boolean isLoggedIn() {
        try {
            return wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(mailAppHeaderLocator),
                ExpectedConditions.visibilityOfElementLocated(composeButtonLocator),
                
                ExpectedConditions.visibilityOfElementLocated(vkWorkMailHeaderLocator),
                ExpectedConditions.visibilityOfElementLocated(inboxFolderLocator),
                ExpectedConditions.visibilityOfElementLocated(mailListLocator),
                ExpectedConditions.visibilityOfElementLocated(userProfileLocator),
                ExpectedConditions.visibilityOfElementLocated(newMessageButtonLocator),

                ExpectedConditions.urlContains("inbox"),
                ExpectedConditions.urlContains("mail"),
                ExpectedConditions.urlContains("e.mail.ru"),
                ExpectedConditions.urlContains("workmail"),
                
                ExpectedConditions.titleContains("Mail"),
                ExpectedConditions.titleContains("Почта"),
                ExpectedConditions.titleContains("WorkMail"),
                ExpectedConditions.titleContains("Входящие")
            )) != null;
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
}