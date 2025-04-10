package itmo.bonga;

public class Main {
   public static void main(String[] args) {
      String[] browsers = { "chrome" };

      if (args.length > 0) {
         // Разрешаем выбор определенного браузера из командной строки
         browsers = new String[] { args[0].toLowerCase() };
      }

      for (String browser : browsers) {
         try {
            BongacamsTest test = new BongacamsTest(browser);
            test.runTests();
         } catch (Exception e) {
            System.out.println("Не удалось запустить тесты в " + browser + ": " + e.getMessage());
            e.printStackTrace();
         }
      }

      // Принудительное завершение программы для корректного закрытия всех потоков
      System.exit(0);
   }
}