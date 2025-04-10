from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
from tests.test_base import BaseBongaTest
from typing import List


class EnteringBongacamsTest(BaseBongaTest):
    def __init__(self, browser_type: str, existing_driver: webdriver = None):
        super().__init__(browser_type, existing_driver)

    def test_model_count(self) -> bool:
        try:
            self.record_result("UC2: Количество моделей", True, "Начало поиска моделей")

            long_wait = WebDriverWait(self.driver, 30)
            current_url = self.driver.current_url
            self.record_result(
                "UC2: Количество моделей", True, f"Текущий URL: {current_url}"
            )

            xpath_options = ["//div[contains(@class, 'ls_thumb')]"]

            model_elements: List[webdriver.remote.webelement.WebElement] = None

            for xpath in xpath_options:
                try:
                    model_elements = long_wait.until(
                        EC.presence_of_all_elements_located((By.XPATH, xpath))
                    )
                    if model_elements and len(model_elements) > 0:
                        break
                except Exception:
                    continue

            if not model_elements or len(model_elements) == 0:
                raise NoSuchElementException(
                    "Не удалось найти элементы моделей с использованием ожидаемых XPath"
                )

            model_count = len(model_elements)
            if model_count <= 70:
                raise AssertionError(
                    f"Ожидалось более 70 моделей, найдено {model_count}"
                )

            self.record_result(
                "UC2: Количество моделей",
                True,
                f"Найдено {model_count} моделей (70+ подтверждено)",
            )
            return True
        except NoSuchElementException as e:
            self.record_result("UC2: Количество моделей", False, f"Ошибка: {str(e)}")
            return False

    def run_tests_impl(self):
        try:
            self.setup()

            if "bongacams" in self.driver.current_url:
                self.test_model_count()
            else:
                if self.test_homepage_loads():
                    self.test_model_count()
        except Exception as e:
            self.record_result(
                "Набор тестов", False, f"Непредвиденная ошибка: {str(e)}"
            )
        finally:
            if self.should_close_driver:
                self.teardown()
            else:
                print(f"\nРезультаты тестов для {self.browser_type}:")
                for result in self.test_results:
                    print(f"- {result}")
                print(f"Тесты завершены в {self.browser_type}\n")
