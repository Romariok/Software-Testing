package itmo.bonga;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EnteringBongacamsTest extends BaseBongaTest {

   public EnteringBongacamsTest(String browserType) {
      super(browserType);
   }
   
   public EnteringBongacamsTest(String browserType, WebDriver existingDriver) {
      super(browserType, existingDriver);
   }

   public boolean testModelCount() {
      try {
         recordResult("UC2: Количество моделей", true, "Начало поиска моделей");

         WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));

         String currentUrl = driver.getCurrentUrl();
         recordResult("UC2: Количество моделей", true, "Текущий URL: " + currentUrl);

         String[] xpathOptions = {
               "//div[contains(@class, 'ls_thumb')]"
         };

         List<WebElement> modelElements = null;

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
      } catch (NoSuchElementException e) {
         recordResult("UC2: Количество моделей", false, "Ошибка: " + e.getMessage());
         return false;
      }
   }

   @Override
   public void runTests() {
      try {
         setup();
         
         if (driver.getCurrentUrl().contains("bongacams")) {
            testModelCount();
         } else {
            if (testHomepageLoads()) {
               testModelCount();
            }
         }
      } catch (Exception e) {
         recordResult("Набор тестов", false, "Непредвиденная ошибка: " + e.getMessage());
      } finally {
         if (shouldCloseDriver) {
            teardown();
         } else {
            System.out.println("\nРезультаты тестов для " + browserType + ":");
            for (String result : testResults) {
               System.out.println("- " + result);
            }
            System.out.println("Тесты завершены в " + browserType + "\n");
         }
      }
   }
} 