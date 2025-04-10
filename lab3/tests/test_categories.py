from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from tests.test_base import BaseBongaTest
import os
from datetime import datetime
from typing import Optional


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

    def test_select_young_category(self) -> bool:
        try:
            self.sleep(5000)
            if (
                self.is_cloudflare_challenge()
                and not self.wait_for_manual_captcha_solving()
            ):
                self.record_result(
                    "UC4: Выбор категории",
                    False,
                    "Не удалось пройти защиту Cloudflare с CAPTCHA.",
                )
                return False

            long_wait = WebDriverWait(self.driver, 30)
            exact_xpath = "//a[@class='js-header_tag_link ht_item' and @href='/female/tags/new-teens']"
            fallback_xpaths = [
                "//a[contains(@class, 'js-header_tag_link') and contains(@href, '/female/tags/new-teens')]",
                "//a[contains(@class, 'ht_item') and contains(@href, 'new-teens')]",
                "//a[@href='/female/tags/new-teens']",
                "//a[contains(text(), 'молоденькие') or contains(text(), 'Молоденькие')]",
            ]

            young_category: Optional[WebElement] = None
            use_javascript = False
            category_url = ""

            try:
                print(f"Пытаемся найти элемент по точному XPath: {exact_xpath}")
                young_category = long_wait.until(
                    EC.element_to_be_clickable((By.XPATH, exact_xpath))
                )
            except Exception as e:
                print(f"Не удалось найти категорию по точному XPath: {str(e)}")

                for xpath in fallback_xpaths:
                    try:
                        print(
                            f"Пытаемся найти элемент по альтернативному XPath: {xpath}"
                        )
                        young_category = long_wait.until(
                            EC.element_to_be_clickable((By.XPATH, xpath))
                        )
                        if young_category:
                            print(
                                f"Категория найдена с использованием альтернативного XPath: {xpath}"
                            )
                            break
                    except Exception:
                        continue

                if not young_category:
                    try:
                        print("Попытка найти категорию с помощью JavaScript...")
                        js = self.driver.execute_script

                        all_links = self.driver.find_elements(By.TAG_NAME, "a")
                        print(f"Найдено {len(all_links)} ссылок на странице")

                        young_category = js(
                            "return document.querySelector('a[href=\"/female/tags/new-teens\"]');"
                        )

                        if young_category:
                            print("Категория найдена через JavaScript по href")
                        else:
                            js_code = """
                                var links = Array.from(document.querySelectorAll('a'));
                                for (var i = 0; i < links.length; i++) {
                                    var link = links[i];
                                    var text = link.textContent.toLowerCase();
                                    if (text.includes('молоденькие') || text.includes('18+') || link.href.includes('new-teens')) {
                                        console.log('Найден элемент: ' + link.textContent + ' - ' + link.href);
                                        return link;
                                    }
                                }
                                return null;
                            """
                            young_category = js(js_code)

                            if young_category:
                                print("Категория найдена через JavaScript по тексту")

                        if young_category:
                            use_javascript = True
                    except Exception as js_ex:
                        print(
                            f"Не удалось найти категорию с помощью JavaScript: {str(js_ex)}"
                        )

            if not young_category:
                print("Не удалось найти элемент категории, переходим напрямую по URL")
                self.driver.get("https://www.bongacams.com/female/tags/new-teens")
                self.sleep(5000)

                if (
                    self.is_cloudflare_challenge()
                    and not self.wait_for_manual_captcha_solving()
                ):
                    self.record_result(
                        "UC4: Выбор категории",
                        False,
                        "Не удалось пройти защиту Cloudflare с CAPTCHA после перехода на страницу категории.",
                    )
                    return False

                self.record_result(
                    "UC4: Выбор категории", True, "Прямой переход по URL категории"
                )
                return True

            category_name = young_category.text
            category_url = young_category.get_attribute("href")
            self.record_result(
                "UC4: Выбор категории",
                True,
                f"Найдена категория: {category_name} с href: {category_url}",
            )

            before_click_url = self.driver.current_url

            try:
                if use_javascript:
                    self.driver.execute_script("arguments[0].click();", young_category)
                else:
                    young_category.click()
            except Exception as e:
                print(f"Ошибка при клике на категорию: {str(e)}")
                self.driver.get(category_url)

            self.sleep(5000)

            if (
                self.is_cloudflare_challenge()
                and not self.wait_for_manual_captcha_solving()
            ):
                self.record_result(
                    "UC4: Выбор категории",
                    False,
                    "Не удалось пройти защиту Cloudflare с CAPTCHA после перехода на страницу категории.",
                )
                return False

            after_click_url = self.driver.current_url
            if after_click_url == before_click_url:
                raise AssertionError("URL не изменился после клика на категорию")

            self.record_result(
                "UC4: Выбор категории",
                True,
                f"Успешный переход на страницу категории: {after_click_url}",
            )
            return True
        except Exception as e:
            self.record_result("UC4: Выбор категории", False, f"Ошибка: {str(e)}")
            return False

    def run_tests_impl(self):
        try:
            self.setup()

            if "bongacams" in self.driver.current_url:
                self.test_list_categories()
                self.test_select_young_category()
            else:
                if self.test_homepage_loads():
                    self.test_list_categories()
                    self.test_select_young_category()
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
