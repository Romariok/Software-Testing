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
    private final By burgerMenuButtonXPath = By.xpath("//*[@id=\"app-canvas\"]/div/div[1]/div[1]/div/div[2]/div/div[4]/div[1]/div/div/span/div/div[1]/div/div/div/div/span");
    private final By writeToYourselfOptionXPath = By.xpath("//*[@id=\"app-canvas\"]/div/div[1]/div[1]/div/div[2]/div/div[4]/div[1]/div/div/span/div/div[1]/div/div/div/div/div/div/div[1]");
    private final By emailEditorSignatureXPath = By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[2]/div[3]/div[4]/div/div/div[2]/div[1]/div[3]/div/div[2]/div");
    
    // Fallback locators
    private final List<By> burgerMenuFallbacks = Arrays.asList(
        burgerMenuButtonXPath,
        By.cssSelector(".button2_navigation_drop_down"),
        By.cssSelector("[data-highlighted-class='button2_highlighted']"),
        By.xpath("//span[contains(@class, 'button2_navigation_drop_down')]"),
        By.xpath("//span[contains(@class, 'button2_rotate-ico')]")
    );
    
    private final List<By> writeToYourselfFallbacks = Arrays.asList(
        writeToYourselfOptionXPath,
        By.xpath("//div[contains(text(), 'Написать себе')]"),
        By.cssSelector(".list-item .compose-dropdown__text"),
        By.xpath("//span[text()='Написать себе']")
    );

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
            WebElement emailEditor = wait.until(ExpectedConditions.visibilityOfElementLocated(emailEditorSignatureXPath));
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
}