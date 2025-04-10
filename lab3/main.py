from tests.test_entering import EnteringBongacamsTest
from tests.test_categories import ChpokiBongaTest


def main():
    browser = "chrome"
    
    # Test 1: Run entering test in its own session
    print(f"Запуск тестов входа на сайт в браузере {browser}")
    enter_test = EnteringBongacamsTest(browser)
    enter_test.run_tests(True)  # True ensures the browser closes after test
    
    # Test 2: Run category test in its own session
    print(f"Запуск тестов выбора категорий в браузере {browser}")
    category_test = ChpokiBongaTest(browser)
    category_test.run_tests(True)  # True ensures the browser closes after test


if __name__ == "__main__":
    main()
