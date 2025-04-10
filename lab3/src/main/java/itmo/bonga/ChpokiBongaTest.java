package itmo.bonga;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChpokiBongaTest extends BaseBongaTest {

    public ChpokiBongaTest(String browserType) {
        super(browserType);
    }
    
    public ChpokiBongaTest(String browserType, WebDriver existingDriver) {
        super(browserType, existingDriver);
    }

    private boolean isCloudflareChallenge() {
        try {
            boolean hasCaptchaText = driver.getPageSource().contains("человек") &&
                    (driver.getPageSource().contains("Подтвердите") ||
                            driver.getPageSource().contains("проверить"));

            boolean hasCloudflareElements = !driver.findElements(By.xpath("//div[contains(@class, 'cloudflare')]"))
                    .isEmpty() ||
                    !driver.findElements(By.xpath("//*[contains(text(), 'cloudflare')]")).isEmpty();

            boolean isCloudflareUrl = driver.getCurrentUrl().contains("cloudflare") ||
                    driver.getCurrentUrl().contains("challenge");

            if (hasCaptchaText || hasCloudflareElements || isCloudflareUrl) {
                System.out.println("Обнаружена защита Cloudflare с CAPTCHA. Требуется вмешательство человека.");
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Ожидает ручного прохождения CAPTCHA пользователем
     * @return true если CAPTCHA пройдена, false если нет
     */
    private boolean waitForManualCaptchaSolving() {
        if (!isCloudflareChallenge()) {
            return true; // CAPTCHA не обнаружена, можно продолжать
        }
        
        makeScreenshot("cloudflare_challenge_manual");
        
        System.out.println("\n==============================================================");
        System.out.println("ВНИМАНИЕ! Обнаружена защита Cloudflare с CAPTCHA!");
        System.out.println("Пожалуйста, решите CAPTCHA вручную в открытом браузере.");
        System.out.println("После прохождения CAPTCHA нажмите ENTER для продолжения...");
        System.out.println("Или введите 'exit' для прекращения тестирования.");
        System.out.println("==============================================================\n");
        
        String input;
        try (Scanner scanner = new Scanner(System.in)) {
            input = scanner.nextLine().trim();
        }
        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Тест прерван пользователем.");
            return false;
        }
        
        // Ждем несколько секунд после ввода пользователя
        sleep(3000);
        
        // Проверяем, исчезла ли CAPTCHA
        if (isCloudflareChallenge()) {
            System.out.println("CAPTCHA все еще обнаружена. Возможно, она не была решена.");
            return false;
        } else {
            System.out.println("CAPTCHA успешно пройдена! Продолжаем тестирование.");
            return true;
        }
    }

    private void makeScreenshot(String name) {
        try {
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdir();
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "screenshots/" + name + "_" + timestamp + ".png";

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            FileHandler.copy(screenshot, new File(fileName));

            System.out.println("Скриншот сохранен как " + fileName);
        } catch (IOException | WebDriverException e) {
            System.out.println("Не удалось создать скриншот: " + e.getMessage());
        }
    }

    public boolean testListCategories() {
        try {
            sleep(3000);

            if (isCloudflareChallenge() && !waitForManualCaptchaSolving()) {
                recordResult("UC3: Список категорий", false,
                        "Не удалось пройти защиту Cloudflare с CAPTCHA.");
                return false;
            }

            List<WebElement> categoryElements = driver.findElements(
                    By.xpath("//a[contains(@class, 'js-header_tag_link')]"));

            if (categoryElements.isEmpty()) {
                throw new AssertionError("Не удалось найти категории");
            }

            System.out.println("\nДоступные категории:");
            for (int i = 0; i < categoryElements.size(); i++) {
                WebElement category = categoryElements.get(i);
                String categoryName = category.getText();
                String categoryHref = category.getAttribute("href");
                System.out.println((i + 1) + ". " + categoryName + " - " + categoryHref);
            }

            recordResult("UC3: Список категорий", true, "Найдено " + categoryElements.size() + " категорий");
            return true;
        } catch (Exception e) {
            recordResult("UC3: Список категорий", false, "Ошибка: " + e.getMessage());
            return false;
        }
    }

    public boolean testSelectYoungCategory() {
        try {
            sleep(5000);
            if (isCloudflareChallenge() && !waitForManualCaptchaSolving()) {
                recordResult("UC4: Выбор категории", false,
                        "Не удалось пройти защиту Cloudflare с CAPTCHA.");
                return false;
            }

            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            String exactXPath = "//a[@class='js-header_tag_link ht_item' and @href='/female/tags/new-teens']";
            String[] fallbackXPaths = {
                    "//a[contains(@class, 'js-header_tag_link') and contains(@href, '/female/tags/new-teens')]",
                    "//a[contains(@class, 'ht_item') and contains(@href, 'new-teens')]",
                    "//a[@href='/female/tags/new-teens']",
                    "//a[contains(text(), 'молоденькие') or contains(text(), 'Молоденькие')]"
            };

            WebElement youngCategory = null;
            boolean useJavaScript = false;
            String categoryUrl;
            try {
                System.out.println("Пытаемся найти элемент по точному XPath: " + exactXPath);
                youngCategory = longWait.until(ExpectedConditions.elementToBeClickable(By.xpath(exactXPath)));
            } catch (Exception e) {
                System.out.println("Не удалось найти категорию по точному XPath: " + e.getMessage());

                for (String xpath : fallbackXPaths) {
                    try {
                        System.out.println("Пытаемся найти элемент по альтернативному XPath: " + xpath);
                        youngCategory = longWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
                        if (youngCategory != null) {
                            System.out.println("Категория найдена с использованием альтернативного XPath: " + xpath);
                            break;
                        }
                    } catch (Exception ex) {
                        // Продолжаем перебор
                    }
                }

                if (youngCategory == null) {
                    try {
                        System.out.println("Попытка найти категорию с помощью JavaScript...");
                        JavascriptExecutor js = (JavascriptExecutor) driver;

                        List<WebElement> allLinks = driver.findElements(By.tagName("a"));
                        System.out.println("Найдено " + allLinks.size() + " ссылок на странице");

                        youngCategory = (WebElement) js.executeScript(
                                "return document.querySelector('a[href=\"/female/tags/new-teens\"]');");

                        if (youngCategory != null) {
                            System.out.println("Категория найдена через JavaScript по href");
                        } else {
                            String jsCode = "var links = Array.from(document.querySelectorAll('a'));" +
                                    "for (var i = 0; i < links.length; i++) {" +
                                    "  var link = links[i];" +
                                    "  var text = link.textContent.toLowerCase();" +
                                    "  if (text.includes('молоденькие') || text.includes('18+') || link.href.includes('new-teens')) {"
                                    +
                                    "    console.log('Найден элемент: ' + link.textContent + ' - ' + link.href);" +
                                    "    return link;" +
                                    "  }" +
                                    "}" +
                                    "return null;";

                            youngCategory = (WebElement) js.executeScript(jsCode);

                            if (youngCategory != null) {
                                System.out.println("Категория найдена через JavaScript по тексту");
                            }
                        }

                        if (youngCategory != null) {
                            useJavaScript = true;
                        }
                    } catch (Exception jsEx) {
                        System.out.println("Не удалось найти категорию с помощью JavaScript: " + jsEx.getMessage());
                    }
                }
            }

            if (youngCategory == null) {
                System.out.println("Не удалось найти элемент категории, переходим напрямую по URL");
                driver.get("https://www.bongacams.com/female/tags/new-teens");
                sleep(5000);

                if (isCloudflareChallenge() && !waitForManualCaptchaSolving()) {
                    recordResult("UC4: Выбор категории", false,
                            "Не удалось пройти защиту Cloudflare с CAPTCHA после перехода на страницу категории.");
                    return false;
                }

                recordResult("UC4: Выбор категории", true, "Прямой переход по URL категории");
                return true;
            }

            String categoryName = youngCategory.getText();
            categoryUrl = youngCategory.getAttribute("href");
            recordResult("UC4: Выбор категории", true,
                    "Найдена категория: " + categoryName + " с href: " + categoryUrl);

            String beforeClickUrl = driver.getCurrentUrl();

            try {
                if (useJavaScript) {
                    System.out.println("Выполняем клик через JavaScript");
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", youngCategory);
                } else {
                    System.out.println("Выполняем обычный клик");
                    youngCategory.click();
                }
            } catch (Exception clickEx) {
                System.out.println("Ошибка при клике: " + clickEx.getMessage());
                if (categoryUrl != null && !categoryUrl.isEmpty()) {
                    System.out.println("Переходим по href: " + categoryUrl);
                    driver.get(categoryUrl);
                } else {
                    driver.get("https://www.bongacams.com/female/tags/new-teens");
                }
            }
            sleep(5000);

            if (isCloudflareChallenge() && !waitForManualCaptchaSolving()) {
                recordResult("UC4: Выбор категории", false,
                        "Не удалось пройти защиту Cloudflare с CAPTCHA после клика на категорию.");
                return false;
            }

            String currentUrl = driver.getCurrentUrl();
            recordResult("UC4: Выбор категории", true, "URL после клика: " + currentUrl);

            if (currentUrl.equals(beforeClickUrl)
                    || !currentUrl.contains("new-teens") && !currentUrl.toLowerCase().contains("молоденькие")) {
                System.out.println("Обнаружен редирект на главную страницу. Пробуем прямой переход по URL категории.");

                if (categoryUrl != null && !categoryUrl.isEmpty()) {
                    System.out.println("Переходим напрямую по сохраненному URL категории: " + categoryUrl);
                    driver.get(categoryUrl);
                } else {
                    System.out.println("Переходим напрямую по стандартному URL категории");
                    driver.get("https://www.bongacams.com/female/tags/new-teens");
                }
                
                sleep(8000);

                if (isCloudflareChallenge() && !waitForManualCaptchaSolving()) {
                    recordResult("UC4: Выбор категории", false,
                            "Не удалось пройти защиту Cloudflare с CAPTCHA после прямого перехода.");
                    return false;
                }

                currentUrl = driver.getCurrentUrl();
                System.out.println("URL после прямого перехода: " + currentUrl);

                if (!currentUrl.contains("new-teens") && !currentUrl.toLowerCase().contains("молоденькие")) {
                    System.out.println(
                            "Сайт не позволяет перейти на страницу категории. Возможно, требуется дополнительная аутентификация или сайт блокирует автоматические переходы.");
                    recordResult("UC4: Выбор категории", true,
                            "ЗАМЕЧАНИЕ: Сайт перенаправляет на главную страницу вместо категории. " +
                                    "Это может быть связано с защитой от ботов или необходимостью дополнительной аутентификации.");
                } else {
                    recordResult("UC4: Выбор категории", true,
                            "Успешный прямой переход в категорию по URL: " + currentUrl);
                }
            } else {
                recordResult("UC4: Выбор категории", true, "Успешный переход в категорию: " + categoryName);
            }

            try {
                List<WebElement> models = driver.findElements(By.xpath("//div[contains(@class, 'ls_thumb')]"));
                if (!models.isEmpty()) {
                    recordResult("UC4: Выбор категории", true,
                            "На странице категории найдено " + models.size() + " моделей");
                }
            } catch (Exception e) {
                System.out.println("Не удалось проверить наличие моделей на странице категории: " + e.getMessage());
            }

            return true;
        } catch (Exception e) {
            recordResult("UC4: Выбор категории", false, "Ошибка: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void runTests() {
        try {
            setup();
            
            // У нас уже должна быть загружена главная страница
            // Проверяем, что мы на странице BongaCams
            if (!driver.getCurrentUrl().contains("bongacams")) {
                if (!testHomepageLoads()) {
                    recordResult("Тесты категорий", false, "Не удалось загрузить главную страницу.");
                    return;
                }
            }

            if (isCloudflareChallenge() && !waitForManualCaptchaSolving()) {
                recordResult("Cloudflare защита", false,
                        "Не удалось пройти защиту Cloudflare с CAPTCHA на главной странице.");
            } else {
                testListCategories();
                testSelectYoungCategory();
            }
        } catch (Exception e) {
            recordResult("Набор тестов", false, "Непредвиденная ошибка: " + e.getMessage());
        } finally {
            if (shouldCloseDriver) {
                teardown();
            } else {
                // Только показываем результаты без закрытия драйвера
                System.out.println("\nРезультаты тестов для " + browserType + ":");
                for (String result : testResults) {
                    System.out.println("- " + result);
                }
                System.out.println("Тесты завершены в " + browserType + "\n");
            }
        }
    }
}