from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from tests.test_base import BaseBongaTest
import os
from datetime import datetime
from typing import Optional
from selenium.webdriver.common.action_chains import ActionChains


class ChpokiBongaTest(BaseBongaTest):
    def __init__(self, browser_type: str, existing_driver: webdriver = None):
        super().__init__(browser_type, existing_driver)

    def is_cloudflare_challenge(self) -> bool:
        try:
            page_source = self.driver.page_source
            has_captcha_text = "человек" in page_source and (
                "Подтвердите" in page_source or "проверить" in page_source
            )

            has_cloudflare_elements = (
                len(
                    self.driver.find_elements(
                        By.XPATH, "//div[contains(@class, 'cloudflare')]"
                    )
                )
                > 0
                or len(
                    self.driver.find_elements(
                        By.XPATH, "//*[contains(text(), 'cloudflare')]"
                    )
                )
                > 0
            )

            current_url = self.driver.current_url
            is_cloudflare_url = (
                "cloudflare" in current_url or "challenge" in current_url
            )

            if has_captcha_text or has_cloudflare_elements or is_cloudflare_url:
                print(
                    "Обнаружена защита Cloudflare с CAPTCHA. Требуется вмешательство человека."
                )
                return True

            return False
        except Exception:
            return False

    def wait_for_manual_captcha_solving(self) -> bool:
        if not self.is_cloudflare_challenge():
            return True

        self.make_screenshot("cloudflare_challenge_manual")

        print("\n" + "=" * 60)
        print("ВНИМАНИЕ! Обнаружена защита Cloudflare с CAPTCHA!")
        print("Пожалуйста, решите CAPTCHA вручную в открытом браузере.")
        print("После прохождения CAPTCHA нажмите ENTER для продолжения...")
        print("Или введите 'exit' для прекращения тестирования.")
        print("=" * 60 + "\n")

        input_text = input().strip()
        if input_text.lower() == "exit":
            print("Тест прерван пользователем.")
            return False

        self.sleep(3000)

        if self.is_cloudflare_challenge():
            print("CAPTCHA все еще обнаружена. Возможно, она не была решена.")
            return False
        else:
            print("CAPTCHA успешно пройдена! Продолжаем тестирование.")
            return True

    def make_screenshot(self, name: str):
        try:
            screenshot_dir = "screenshots"
            if not os.path.exists(screenshot_dir):
                os.makedirs(screenshot_dir)

            timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
            filename = f"{screenshot_dir}/{name}_{timestamp}.png"

            screenshot = self.driver.get_screenshot_as_file(filename)
            if screenshot:
                print(f"Скриншот сохранен как {filename}")
            else:
                print("Не удалось сохранить скриншот")
        except Exception as e:
            print(f"Не удалось создать скриншот: {str(e)}")

    def test_list_categories(self) -> bool:
        try:
            self.sleep(3000)

            if (
                self.is_cloudflare_challenge()
                and not self.wait_for_manual_captcha_solving()
            ):
                self.record_result(
                    "UC3: Список категорий",
                    False,
                    "Не удалось пройти защиту Cloudflare с CAPTCHA.",
                )
                return False

            category_elements = self.driver.find_elements(
                By.XPATH, "//a[contains(@class, 'js-header_tag_link')]"
            )

            if not category_elements:
                raise AssertionError("Не удалось найти категории")

            print("\nДоступные категории:")
            for i, category in enumerate(category_elements, 1):
                category_name = category.text
                category_href = category.get_attribute("href")
                print(f"{i}. {category_name} - {category_href}")

            self.record_result(
                "UC3: Список категорий",
                True,
                f"Найдено {len(category_elements)} категорий",
            )
            return True
        except Exception as e:
            self.record_result("UC3: Список категорий", False, f"Ошибка: {str(e)}")
            return False

    def run_tests_impl(self):
        try:
            self.setup()

            if "bongacams" in self.driver.current_url:
                self.test_list_categories()
            else:
                if self.test_homepage_loads():
                    self.test_list_categories()
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
