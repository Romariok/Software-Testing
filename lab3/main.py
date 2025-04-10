import sys
from tests.test_entering import EnteringBongacamsTest
from tests.test_categories import ChpokiBongaTest
from tests.test_base import BaseBongaTest


def main():
    browsers = ["chrome"]
    test_type = "all"

    if len(sys.argv) > 1:
        browsers = [sys.argv[1].lower()]
        if len(sys.argv) > 2:
            test_type = sys.argv[2].lower()

    for browser in browsers:
        try:
            print(f"Инициализация WebDriver для браузера {browser}")

            # Создаем базовый тест, чтобы инициализировать WebDriver
            class DummyTest(BaseBongaTest):
                def run_tests_impl(self):
                    pass

            base_test = DummyTest(browser)

            # Настройка окружения и получение инициализированного драйвера
            base_test.setup()
            driver = base_test.get_driver()

            try:
                # Загружаем главную страницу один раз
                homepage_loaded = base_test.test_homepage_loads()

                if homepage_loaded:
                    if test_type in ["entering", "all"]:
                        print(f"Запуск тестов входа на сайт в браузере {browser}")
                        enter_test = EnteringBongacamsTest(browser, driver)
                        enter_test.run_tests(
                            False
                        )  # False означает, что драйвер не нужно закрывать

                        # Возвращаемся на главную страницу
                        driver.get("https://www.bongacams.com/")
                        base_test.sleep(3000)

                    if test_type in ["category", "all"]:
                        print(f"Запуск тестов выбора категорий в браузере {browser}")
                        category_test = ChpokiBongaTest(browser, driver)
                        category_test.run_tests(
                            False
                        )  # False означает, что драйвер не нужно закрывать
                else:
                    print("Не удалось загрузить главную страницу. Тесты прерваны.")
            finally:
                # Закрываем драйвер после всех тестов
                print("Завершение работы WebDriver...")
                base_test.teardown()
        except Exception as e:
            print(f"Не удалось запустить тесты в {browser}: {str(e)}")


if __name__ == "__main__":
    main()
