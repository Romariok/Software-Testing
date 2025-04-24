package itmo.bonga;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import java.time.Duration;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
   public static void main(String[] args) {
      WebDriver driver = null;

      try {
         System.out.println("Initializing Chrome WebDriver");
         WebDriverManager.chromedriver().setup();
         driver = new ChromeDriver();
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
         driver.manage().window().maximize();
         driver.get("https://mail.ru/");
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

         System.out.println("Page loaded successfully");

      } catch (Exception e) {
         System.out.println("Failed to initialize WebDriver: " + e.getMessage());
      } finally {
         if (driver != null) {
            System.out.println("Closing WebDriver...");
            driver.quit();
         }
      }

      System.exit(0);
   }
}