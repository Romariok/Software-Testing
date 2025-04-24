package itmo.bonga.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    
    private final By iframeLocator = By.cssSelector(".ag-popup__frame_layout__iframe, iframe.ag-popup__frame_layout_iframe, iframe[src*='account.mail.ru/login']");
    private final By iframeFallbackLocator = By.cssSelector("iframe[class*='popup'][class*='frame']");
    
    private final By usernameInputLocator = By.cssSelector("input[placeholder='Имя аккаунта']");
    
    private final By usernameInputFallback1 = By.cssSelector("input.input-0-2-71");
    private final By usernameInputFallback2 = By.cssSelector("input[type='text'][autocomplete='username']");
    private final By usernameInputFallback3 = By.cssSelector("input[name='username']");

    private final By usernameInputFallback4 = By.id("email");
    private final By usernameInputFallback5 = By.cssSelector("input[placeholder='Имя ящика']");
    private final By usernameInputFallback6 = By.cssSelector(".vkuiUnstyledTextField__host.vkuiInput__el");
    private final By usernameInputFallback7 = By.xpath("/html/body/div/div/div/div/div/div/div/div/div/div/div/div/div[2]/form/fieldset/div[1]/span/div/div/input");
    
    private final By nextButtonLocator = By.cssSelector("button.Button2_view_default");
    private final By passwordInputLocator = By.cssSelector("input[type='password'], input[name='password']");
    private final By submitButtonLocator = By.cssSelector("button.Button2_view_default");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        switchToLoginIframe();
        waitForUsernameInput();
    }
    
    private void switchToLoginIframe() {
        try {
            System.out.println("Trying to switch to login iframe");
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
            System.out.println("Successfully switched to login iframe");
        } catch (Exception e) {
            System.out.println("Primary iframe locator failed, trying fallback: " + e.getMessage());
            try {
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeFallbackLocator));
                System.out.println("Successfully switched to login iframe using fallback locator");
            } catch (Exception e2) {
                System.out.println("Failed to switch to iframe: " + e2.getMessage());
                // Try to find iframe by index as last resort
                try {
                    driver.switchTo().frame(0);
                    System.out.println("Switched to first iframe on page");
                } catch (Exception e3) {
                    System.out.println("Failed to switch to any iframe: " + e3.getMessage());
                }
            }
        }
    }
    
    private void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
    
    private WebElement waitForUsernameInput() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputLocator));
        } catch (Exception e) {
            System.out.println("Primary username input locator failed, trying fallback 1");
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputFallback1));
            } catch (Exception e1) {
                System.out.println("Fallback 1 failed, trying fallback 2");
                try {
                    return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputFallback2));
                } catch (Exception e2) {
                    System.out.println("Fallback 2 failed, trying fallback 3");
                    try {
                        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputFallback3));
                    } catch (Exception e3) {
                        System.out.println("Fallback 3 failed, trying fallback 4");
                        try {
                            return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputFallback4));
                        } catch (Exception e4) {
                            System.out.println("Fallback 4 failed, trying fallback 5");
                            try {
                                return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputFallback5));
                            } catch (Exception e5) {
                                System.out.println("Fallback 5 failed, trying fallback 6");
                                try {
                                    return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputFallback6));
                                } catch (Exception e6) {
                                    System.out.println("Fallback 6 failed, trying fallback 7");
                                    return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInputFallback7));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public LoginPage enterUsername(String username) {
        WebElement usernameInput = waitForUsernameInput();
        usernameInput.clear();
        usernameInput.sendKeys(username);
        return this;
    }

    public LoginPage clickNextButton() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator));
        nextButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        return this;
    }

    public LoginPage enterPassword(String password) {
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public MailboxPage clickSubmitButton() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
        submitButton.click();
        
        // Switch back to default content before transitioning to MailboxPage
        switchToDefaultContent();
        return new MailboxPage(driver, wait);
    }

    public MailboxPage login(String username, String password) {
        enterUsername(username);
        clickNextButton();
        enterPassword(password);
        return clickSubmitButton();
    }
}