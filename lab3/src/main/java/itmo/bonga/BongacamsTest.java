package itmo.bonga;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BongacamsTest {
   private final String browserType;
   private WebDriver driver;
   private WebDriverWait wait;
   private final List<String> testResults = new ArrayList<>();

   public BongacamsTest(String browserType) {
      this.browserType = browserType;
   }

   public void setup() {
      System.out.println("Настройка тестовой среды в " + browserType + "...");
      driver = DriverManager.initializeDriver(browserType);
      wait = new WebDriverWait(driver, Duration.ofSeconds(15));
   }

   public void teardown() {
      if (driver != null) {
         driver.quit();
      }
      System.out.println("\nРезультаты тестов для " + browserType + ":");
      for (String result : testResults) {
         System.out.println("- " + result);
      }
      System.out.println("Тесты завершены в " + browserType + "\n");
   }

   private void recordResult(String testName, boolean passed, String message) {
      String result = (passed ? "✓" : "✗") + " " + testName + ": " + message;
      testResults.add(result);
      System.out.println(result);
   }

   private void handleAgeVerification() {
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

         // Запишем часть исходного кода страницы для диагностики
         String pageSource = driver.getPageSource();
         String pageSourcePreview = pageSource.length() > 200
               ? pageSource.substring(0, 200) + "..."
               : pageSource;
         recordResult("UC1: Загрузка домашней страницы", true,
               "Исходный код страницы начинается с: " + pageSourcePreview);

         recordResult("UC1: Загрузка домашней страницы", true, "Домашняя страница успешно загружена");
         return true;
      } catch (Exception e) {
         recordResult("UC1: Загрузка домашней страницы", false, "Ошибка: " + e.getMessage());
         return false;
      }
   }

   public boolean testModelCount() {
      try {
         recordResult("UC2: Количество моделей", true, "Начало поиска моделей");

         // Увеличим время ожидания для поиска моделей
         WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));

         // Логируем текущий URL
         String currentUrl = driver.getCurrentUrl();
         recordResult("UC2: Количество моделей", true, "Текущий URL: " + currentUrl);

         // Различные возможные XPath для элементов моделей
         String[] xpathOptions = {
               "//div[contains(@class, 'ls_thumb')]"
         };

         List<WebElement> modelElements = null;

         // Попробуем найти хотя бы один список моделей
         for (String xpath : xpathOptions) {
            try {
               modelElements = longWait.until(
                     ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(xpath), 0));

               if (modelElements != null && !modelElements.isEmpty()) {
                  break;
               }
            } catch (Exception e) {

            }
         }

         if (modelElements == null || modelElements.isEmpty()) {
            throw new NoSuchElementException("Не удалось найти элементы моделей с использованием ожидаемых XPath");
         }

         int modelCount = modelElements.size();
         if (modelCount <= 70) {
            throw new AssertionError("Ожидалось более 70 моделей, найдено " + modelCount);
         }

         recordResult("UC2: Количество моделей", true, "Найдено " + modelCount + " моделей (70+ подтверждено)");
         return true;
      } catch (Exception e) {
         recordResult("UC2: Количество моделей", false, "Ошибка: " + e.getMessage());
         return false;
      }
   }

   public boolean testNavigateToModelProfile() {
      try {
         // Различные возможные XPath для клика по модели
         String[] xpathOptions = {
               "(//div[contains(@class, '__category_rows') and contains(@class, 'mls_models')]/div[@class='__s_medium ls_thumb js-ls_thumb __percent_width'])[1]",
               "(//div[@class='__s_medium ls_thumb js-ls_thumb __percent_width'])[1]",
               "(//div[contains(@class, '__s_medium') and contains(@class, 'ls_thumb') and contains(@class, 'js-ls_thumb') and contains(@class, '__percent_width')])[1]",
               "(//div[contains(@class, 'model')])[1]",
               "(//div[contains(@class, 'cam-item')])[1]",
               "(//div[contains(@class, 'performer')])[1]",
               "(//a[contains(@class, 'model')])[1]"
         };

         WebElement modelElement = null;
         for (String xpath : xpathOptions) {
            try {
               modelElement = wait.until(
                     ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

               if (modelElement != null) {
                  break;
               }
            } catch (Exception ignored) {
               // Продолжаем перебор
            }
         }

         if (modelElement == null) {
            throw new NoSuchElementException("Не удалось найти кликабельный элемент модели");
         }

         // Получение имени модели для проверки, если возможно
         String modelName = null;
         try {
            modelName = modelElement.getAttribute("data-model");
            if (modelName == null || modelName.isEmpty()) {
               modelName = modelElement.getText().trim();
            }
         } catch (Exception ignored) {
            // Продолжаем без имени модели
         }

         modelElement.click();
         sleep(3000); // Ожидание загрузки страницы профиля

         // Проверка, что мы на странице модели
         String currentUrl = driver.getCurrentUrl().toLowerCase();
         if (!currentUrl.contains("profile") && !currentUrl.contains("model") && !currentUrl.contains("cam")) {
            throw new AssertionError("Не удалось перейти на профиль модели");
         }

         if (modelName != null && !modelName.isEmpty()) {
            recordResult("UC3: Навигация по профилю модели", true,
                  "Успешный переход на профиль модели " + modelName);
         } else {
            recordResult("UC3: Навигация по профилю модели", true,
                  "Успешный переход на профиль модели");
         }
         return true;
      } catch (Exception e) {
         recordResult("UC3: Навигация по профилю модели", false, "Ошибка: " + e.getMessage());
         return false;
      }
   }

   public boolean testVerifyLiveStream() {
      try {
         // Различные возможные XPath для элементов потока
         String[] xpathOptions = {
               "//video",
               "//div[contains(@class, 'stream')]",
               "//div[contains(@class, 'video-player')]",
               "//div[contains(@class, 'broadcast')]",
               "//iframe[contains(@src, 'stream')]"
         };

         WebElement streamElement = null;
         for (String xpath : xpathOptions) {
            try {
               streamElement = wait.until(
                     ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

               if (streamElement != null) {
                  break;
               }
            } catch (Exception ignored) {
               // Продолжаем перебор
            }
         }

         if (streamElement == null) {
            throw new NoSuchElementException("Не удалось найти элемент живого потока");
         }

         if (!streamElement.isDisplayed()) {
            throw new AssertionError("Элемент живого потока не отображается");
         }

         recordResult("UC4: Живой поток", true, "Элемент живого потока присутствует и отображается");
         return true;
      } catch (Exception e) {
         recordResult("UC4: Живой поток", false, "Ошибка: " + e.getMessage());
         return false;
      }
   }

   public boolean testSearchFunctionality() {
      try {
         // Возврат на главную страницу
         driver.get("http://www.bongacams.com/");
         sleep(3000);

         handleAgeVerification();

         // Поиск поля ввода с использованием различных возможных XPath
         String[] xpathOptions = {
               "//input[@type='search']",
               "//input[contains(@class, 'search')]",
               "//div[contains(@class, 'search')]//input",
               "//form[contains(@class, 'search')]//input"
         };

         WebElement searchInput = null;
         for (String xpath : xpathOptions) {
            try {
               searchInput = wait.until(
                     ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

               if (searchInput != null) {
                  break;
               }
            } catch (Exception ignored) {
               // Продолжаем перебор
            }
         }

         if (searchInput == null) {
            throw new NoSuchElementException("Не удалось найти поле поиска");
         }

         // Ввод поискового запроса
         String searchTerm = "blonde";
         searchInput.clear();
         searchInput.sendKeys(searchTerm);
         searchInput.sendKeys(Keys.ENTER);
         sleep(3000); // Ожидание результатов поиска

         // Проверка, что URL или содержимое страницы указывают на результаты поиска
         String currentUrl = driver.getCurrentUrl().toLowerCase();
         String pageSource = driver.getPageSource().toLowerCase();

         if (!currentUrl.contains(searchTerm.toLowerCase()) && !pageSource.contains(searchTerm.toLowerCase())) {
            throw new AssertionError("Страница результатов поиска не загружена");
         }

         recordResult("UC5: Функциональность поиска", true, "Поиск по '" + searchTerm + "' успешен");
         return true;
      } catch (Exception e) {
         recordResult("UC5: Функциональность поиска", false, "Ошибка: " + e.getMessage());
         return false;
      }
   }

   public boolean testFilterFunctionality() {
      try {
         // Возврат на главную страницу
         driver.get("http://www.bongacams.com/");
         sleep(3000);

         handleAgeVerification();

         // Поиск элементов фильтра/категории с использованием различных возможных XPath
         String[] xpathOptions = {
               "//div[contains(@class, 'filter')]//a",
               "//div[contains(@class, 'category')]//a",
               "//div[contains(@class, 'tag')]//a",
               "//a[contains(@class, 'filter')]",
               "//a[contains(@class, 'category')]"
         };

         List<WebElement> filterElements = null;
         for (String xpath : xpathOptions) {
            try {
               filterElements = driver.findElements(By.xpath(xpath));

               if (filterElements != null && !filterElements.isEmpty()) {
                  break;
               }
            } catch (Exception ignored) {
               // Продолжаем перебор
            }
         }

         if (filterElements == null || filterElements.isEmpty()) {
            throw new NoSuchElementException("Не удалось найти элементы фильтра/категории");
         }

         // Клик по фильтру
         WebElement filterElement = filterElements.get(0);
         String filterName = filterElement.getText().trim();
         filterElement.click();
         sleep(3000); // Ожидание отфильтрованных результатов

         // Проверка применения фильтра (проверка URL или состояния страницы)
         String currentUrl = driver.getCurrentUrl().toLowerCase();
         String pageSource = driver.getPageSource().toLowerCase();

         if (!currentUrl.contains(filterName.toLowerCase()) && !pageSource.contains(filterName.toLowerCase())) {
            throw new AssertionError("Фильтр не применен");
         }

         recordResult("UC6: Функциональность фильтра", true, "Фильтрация по '" + filterName + "' успешна");
         return true;
      } catch (Exception e) {
         recordResult("UC6: Функциональность фильтра", false, "Ошибка: " + e.getMessage());
         return false;
      }
   }

   public void runTests() {
      try {
         setup();

         // Выполнение тестовых случаев
         if (testHomepageLoads()) {
            testModelCount();
            // Закомментировано, как и в оригинале
            // if (testNavigateToModelProfile()) {
            // testVerifyLiveStream();
            // }
            // testSearchFunctionality();
            // testFilterFunctionality();
         }
      } catch (Exception e) {
         recordResult("Набор тестов", false, "Непредвиденная ошибка: " + e.getMessage());
      } finally {
         teardown();
      }
   }

   private void sleep(long millis) {
      try {
         Thread.sleep(millis);
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      }
   }
}