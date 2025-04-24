package itmo.bonga;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

   /**
    * Инициализирует драйвер для указанного браузера
    * 
    * @param browserType тип браузера ("chrome" или "firefox")
    * @return WebDriver
    */
   public static WebDriver initializeDriver(String browserType) {
      return initializeDriver(browserType, true);
   }

   /**
    * Инициализирует драйвер для указанного браузера с опцией приватного режима
    *
    * @param browserType тип браузера ("chrome" или "firefox")
    * @param incognito   включить режим инкогнито/приватный режим
    * @return WebDriver
    */
   public static WebDriver initializeDriver(String browserType, boolean incognito) {
      WebDriver driver;

      if (browserType.equalsIgnoreCase("chrome")) {
         WebDriverManager.chromedriver().setup();

         ChromeOptions options = new ChromeOptions();
         options.addArguments("--start-maximized");
         options.addArguments("--disable-notifications");
         options.addArguments("--disable-extensions");
         options.addArguments("--disable-gpu");
         options.addArguments("--disable-dev-shm-usage");
         options.addArguments("--no-sandbox");
         options.addArguments("--window-size=1920,1080");
         options.addArguments(
               "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");

         options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation", "enable-logging" });
         if (incognito) {
            options.addArguments("--incognito");
            System.out.println("Chrome запускается в режиме инкогнито");
         }

         String[] chromePaths = {
               "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
               "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe"
         };

         for (String path : chromePaths) {
            File file = new File(path);
            if (file.exists()) {
               options.setBinary(path);
               break;
            }
         }

         driver = new ChromeDriver(options);
      } else if (browserType.equalsIgnoreCase("firefox")) {
         WebDriverManager.firefoxdriver().setup();

         FirefoxOptions options = new FirefoxOptions();
         options.addArguments("--start-maximized");
         options.addArguments("--disable-notifications");
         if (incognito) {
            options.addArguments("-private");
            System.out.println("Firefox запускается в приватном режиме");
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("browser.privatebrowsing.autostart", true);
            options.setProfile(profile);
         }

         String[] firefoxPaths = {
               "C:\\Program Files\\Mozilla Firefox\\firefox.exe",
               "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"
         };

         for (String path : firefoxPaths) {
            File file = new File(path);
            if (file.exists()) {
               options.setBinary(path);
               break;
            }
         }

         driver = new FirefoxDriver(options);
      } else {
         throw new IllegalArgumentException("Неподдерживаемый тип браузера: " + browserType);
      }

      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

      return driver;
   }
}