package itmo.bonga.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MailboxPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

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
}