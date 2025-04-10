package itmo.bonga;

import org.openqa.selenium.WebDriver;

public class Main {
   public static void main(String[] args) {
      String[] browsers = { "chrome" };
      String testType = "all";

      if (args.length > 0) {
         browsers = new String[] { args[0].toLowerCase() };
         
         if (args.length > 1) {
            testType = args[1].toLowerCase();
         }
      }

      for (String browser : browsers) {
         try {
            System.out.println("Инициализация WebDriver для браузера " + browser);
            
            // Создаем базовый тест, чтобы инициализировать WebDriver
            BaseBongaTest baseTest = new BaseBongaTest(browser) {
               @Override
               public void runTests() {
                  // Этот метод не будет вызываться
               }
            };
            
            // Настройка окружения и получение инициализированного драйвера
            baseTest.setup();
            WebDriver driver = baseTest.getDriver();
            
            try {
               // Загружаем главную страницу один раз
               boolean homepageLoaded = baseTest.testHomepageLoads();
               
               if (homepageLoaded) {
                  if (testType.equals("entering") || testType.equals("all")) {
                     System.out.println("Запуск тестов входа на сайт в браузере " + browser);
                     EnteringBongacamsTest enterTest = new EnteringBongacamsTest(browser, driver);
                     enterTest.runTests(false); // false означает, что драйвер не нужно закрывать
                     
                     // Возвращаемся на главную страницу
                     driver.get("https://www.bongacams.com/");
                     baseTest.sleep(3000);
                  }
                  
                  if (testType.equals("category") || testType.equals("all")) {
                     System.out.println("Запуск тестов выбора категорий в браузере " + browser);
                     ChpokiBongaTest categoryTest = new ChpokiBongaTest(browser, driver);
                     categoryTest.runTests(false); // false означает, что драйвер не нужно закрывать
                  }
               } else {
                  System.out.println("Не удалось загрузить главную страницу. Тесты прерваны.");
               }
            } finally {
               // Закрываем драйвер после всех тестов
               System.out.println("Завершение работы WebDriver...");
               baseTest.teardown();
            }
         } catch (Exception e) {
            System.out.println("Не удалось запустить тесты в " + browser + ": " + e.getMessage());
         }
      }
      System.exit(0);
   }
}