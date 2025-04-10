package itmo.bonga;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseBongaTest {
    protected final String browserType;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final List<String> testResults = new ArrayList<>();
    protected boolean shouldCloseDriver = true;

    public BaseBongaTest(String browserType) {
        this.browserType = browserType;
    }
    
    public BaseBongaTest(String browserType, WebDriver existingDriver) {
        this.browserType = browserType;
        this.driver = existingDriver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.shouldCloseDriver = false;
    }

    public void setup() {
        if (driver == null) {
            System.out.println("Настройка тестовой среды в " + browserType + "...");
            driver = DriverManager.initializeDriver(browserType);
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        } else {
            System.out.println("Используется существующий WebDriver для " + browserType);
        }
    }

    public void teardown() {
        if (driver != null && shouldCloseDriver) {
            driver.quit();
            driver = null;
        }
        System.out.println("\nРезультаты тестов для " + browserType + ":");
        for (String result : testResults) {
            System.out.println("- " + result);
        }
        System.out.println("Тесты завершены в " + browserType + "\n");
    }

    protected void recordResult(String testName, boolean passed, String message) {
        String result = (passed ? "✓" : "✗") + " " + testName + ": " + message;
        testResults.add(result);
        System.out.println(result);
    }

    protected void handleAgeVerification() {
        try {
            WebElement confirmButton = wait.until(
                  ExpectedConditions.elementToBeClickable(
                        By.xpath(
                              "//a[contains(@class, 'agree') and contains(@class, 'js-close_warning')] | //a[contains(text(), 'Продолжить')]")));
            confirmButton.click();
            recordResult("Проверка возраста", true, "Диалог подтверждения возраста обработан");
            sleep(2000); // Ожидание исчезновения диалога
        } catch (Exception e) {
            recordResult("Проверка возраста", true, "Диалог подтверждения возраста не обнаружен");
        }
    }

    public boolean testHomepageLoads() {
        try {
            driver.get("http://www.bongacams.com/");
            // Увеличим ожидание загрузки страницы
            sleep(5000);

            handleAgeVerification();

            // Дополнительное ожидание после обработки диалога возраста
            sleep(3000);

            if (!driver.getTitle().contains("BongaCams")) {
                throw new AssertionError("Домашняя страница не загрузилась корректно");
            }

            recordResult("Загрузка домашней страницы", true, "Домашняя страница успешно загружена");
            return true;
        } catch (Exception e) {
            recordResult("Загрузка домашней страницы", false, "Ошибка: " + e.getMessage());
            return false;
        }
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public WebDriver getDriver() {
        return driver;
    }
    
    public abstract void runTests();
    
    public void runTests(boolean closeDriver) {
        this.shouldCloseDriver = closeDriver;
        runTests();
    }
}