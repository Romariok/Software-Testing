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
    
    private final By nextButtonLocator = By.cssSelector("button.base-0-2-79.primary-0-2-93[type='submit']");
    private final By nextButtonLocator2 = By.xpath("//*[@id=\"root\"]/div/div/div/div[2]/div/form/div[2]/div[5]/div[3]/div/div/div[1]/button");
    private final By nextButtonLocator3 = By.xpath("//button[.//span[text()='Войти']]");
    private final By nextButtonLocator4 = By.cssSelector("button[type='submit']");
    
    private final By passwordInputLocator = By.cssSelector("input[placeholder='Пароль']");
    private final By passwordInputLocator2 = By.cssSelector("input.input-0-2-71.withIcon-0-2-72[type='password']");
    private final By passwordInputLocator3 = By.cssSelector("input[type='password']");
    private final By passwordInputLocator4 = By.cssSelector("input[name='password']");
    private final By passwordInputLocator5 = By.cssSelector("input[autocomplete='current-password']");

    private final By submitButtonLocator = By.cssSelector("button.Button2_view_default");
    private final By submitButtonLocator2 = By.cssSelector("button.base-0-2-79.primary-0-2-93[type='submit']");
    private final By submitButtonLocator3 = By.xpath("//button[.//span[text()='Войти']]");
    private final By submitButtonLocator4 = By.cssSelector("button[type='submit']");
    private final By submitButtonLocator5 = By.xpath("//button[contains(@class, 'primary')]");

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
        try {
            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator));
            nextButton.click();
            System.out.println("Clicked login button using primary locator");
        } catch (Exception e) {
            try {
                System.out.println("Primary button locator failed, trying second locator: " + e.getMessage());
                WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator2));
                nextButton.click();
                System.out.println("Clicked login button using second locator");
            } catch (Exception e2) {
                try {
                    System.out.println("Second button locator failed, trying third locator: " + e2.getMessage());
                    WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator3));
                    nextButton.click();
                    System.out.println("Clicked login button using third locator");
                } catch (Exception e3) {
                    System.out.println("Third button locator failed, trying fourth locator: " + e3.getMessage());
                    WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextButtonLocator4));
                    nextButton.click();
                    System.out.println("Clicked login button using fourth locator");
                }
            }
        }

        // Wait for password field with multiple attempts
        waitForPasswordField();
        
        return this;
    }
    
    private WebElement waitForPasswordField() {
        try {
            System.out.println("Looking for password field with primary locator");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        } catch (Exception e) {
            System.out.println("Primary password locator failed, trying alternative 1");
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator2));
            } catch (Exception e2) {
                System.out.println("Alternative 1 failed, trying alternative 2");
                try {
                    return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator3));
                } catch (Exception e3) {
                    System.out.println("Alternative 2 failed, trying alternative 3");
                    try {
                        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator4));
                    } catch (Exception e4) {
                        System.out.println("Alternative 3 failed, trying alternative 4");
                        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator5));
                    }
                }
            }
        }
    }

    public LoginPage enterPassword(String password) {
        WebElement passwordInput = waitForPasswordField();
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public MailboxPage clickSubmitButton() {
        try {
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
            submitButton.click();
            System.out.println("Clicked submit button using primary locator");
        } catch (Exception e) {
            try {
                System.out.println("Primary submit button locator failed, trying second locator: " + e.getMessage());
                WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator2));
                submitButton.click();
                System.out.println("Clicked submit button using second locator");
            } catch (Exception e2) {
                try {
                    System.out.println("Second submit button locator failed, trying third locator: " + e2.getMessage());
                    WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator3));
                    submitButton.click();
                    System.out.println("Clicked submit button using third locator");
                } catch (Exception e3) {
                    try {
                        System.out.println("Third submit button locator failed, trying fourth locator: " + e3.getMessage());
                        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator4));
                        submitButton.click();
                        System.out.println("Clicked submit button using fourth locator");
                    } catch (Exception e4) {
                        System.out.println("Fourth submit button locator failed, trying fifth locator: " + e4.getMessage());
                        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator5));
                        submitButton.click();
                        System.out.println("Clicked submit button using fifth locator");
                    }
                }
            }
        }
        
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