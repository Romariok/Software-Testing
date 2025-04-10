from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException, WebDriverException
from tests.test_driver import DriverManager
from typing import List, Optional
import time
import os
from datetime import datetime


class BaseBongaTest:
    def __init__(self, browser_type: str, existing_driver: webdriver = None):
        self.browser_type = browser_type
        self.driver_manager = DriverManager()
        self.driver = (
            existing_driver
            if existing_driver
            else self.driver_manager.create_driver(browser_type)
        )
        self.test_results: List[str] = []
        self.should_close_driver = existing_driver is None

    def setup(self):
        """Настройка окружения перед тестами"""
        if not self.driver:
            self.driver = self.driver_manager.create_driver(self.browser_type)

    def teardown(self):
        """Очистка после тестов"""
        if self.should_close_driver and self.driver:
            self.driver_manager.quit_driver()

    def sleep(self, milliseconds: int):
        """Удобный метод для паузы"""
        time.sleep(milliseconds / 1000)

    def wait_for_element(
        self, by: By, value: str, timeout: int = 10
    ) -> Optional[webdriver.remote.webelement.WebElement]:
        """Ожидание появления элемента"""
        try:
            element = WebDriverWait(self.driver, timeout).until(
                EC.presence_of_element_located((by, value))
            )
            return element
        except TimeoutException:
            return None

    def test_homepage_loads(self) -> bool:
        """Тест загрузки главной страницы"""
        test_name = "UC1: Загрузка главной страницы"
        try:
            print(f"{test_name}: Переход на https://www.bongacams.com/")
            self.driver.get("https://www.bongacams.com/")
            print(f"{test_name}: Ожидание 5 секунд после перехода...")
            self.sleep(5000)  # Даем время на первоначальную загрузку

            # Обработка диалога подтверждения возраста, если он есть
            print(
                f"{test_name}: Проверка и обработка диалога подтверждения возраста..."
            )
            self.handle_age_verification()
            print(f"{test_name}: Диалог подтверждения возраста обработан (если был).")

            # Дополнительное ожидание после возможного закрытия диалога
            print(f"{test_name}: Ожидание 3 секунды...")
            self.sleep(3000)

            print(
                f"{test_name}: Попытка найти элемент header (ожидание до 20 секунд)..."
            )
            # Проверяем наличие основных элементов с увеличенным таймаутом
            header_element = self.wait_for_element(By.CLASS_NAME, "header", timeout=20)

            if not header_element:
                print(f"{test_name}: Элемент header не найден.")

                raise AssertionError(
                    "Не удалось загрузить главную страницу (элемент header не найден)"
                )

            print(f"{test_name}: Элемент header найден.")
            self.record_result(test_name, True, "Страница успешно загружена")
            return True
        except Exception as e:
            print(f"{test_name}: Произошла ошибка - {str(e)}")
            self.record_result(test_name, False, f"Ошибка: {str(e)}")
            return False

    def record_result(self, test_name: str, success: bool, message: str):
        """Запись результата теста"""
        status = "УСПЕХ" if success else "ОШИБКА"
        log_message = f"{test_name}: {status} - {message}"
        self.test_results.append(log_message)
        print(log_message)  # Добавим вывод в консоль для наглядности

    def run_tests(self, close_driver: bool = True):
        """Запуск всех тестов"""
        self.should_close_driver = close_driver
        try:
            self.run_tests_impl()
        finally:
            if self.should_close_driver:
                self.teardown()

    def run_tests_impl(self):
        """Реализация запуска тестов (должна быть переопределена в дочерних классах)"""
        raise NotImplementedError(
            "Метод run_tests_impl должен быть реализован в дочернем классе"
        )

    def handle_age_verification(self):
        """Обработка диалога подтверждения возраста"""
        try:
            # Используем более общий XPath, который может сработать для разных вариантов кнопки
            confirm_button_xpath = "//a[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'i am 18') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'continue') or contains(@class, 'js-close_warning')]"
            print(
                f"handle_age_verification: Ищем кнопку подтверждения возраста по XPath: {confirm_button_xpath}"
            )
            confirm_button = self.wait_for_element(
                By.XPATH, confirm_button_xpath, timeout=5
            )  # Короткий таймаут, т.к. диалог обычно появляется быстро

            if (
                confirm_button
                and confirm_button.is_displayed()
                and confirm_button.is_enabled()
            ):
                print(
                    "handle_age_verification: Кнопка подтверждения найдена, пытаемся кликнуть..."
                )
                confirm_button.click()
                self.record_result(
                    "Проверка возраста", True, "Диалог подтверждения возраста обработан"
                )
                print(
                    "handle_age_verification: Клик по кнопке выполнен. Ожидание 2 секунды..."
                )
                self.sleep(2000)  # Пауза после клика
            else:
                print(
                    "handle_age_verification: Кнопка подтверждения возраста не найдена или неактивна."
                )
                # Не записываем ошибку, т.к. отсутствие диалога - норма
        except Exception as e:
            # Записываем как информацию, а не ошибку, т.к. отсутствие диалога - нормально
            print(
                f"handle_age_verification: Произошла ошибка при поиске/клике кнопки подтверждения: {str(e)}"
            )
            self.record_result(
                "Проверка возраста",
                True,
                f"Информация: Не удалось обработать диалог (возможно, его не было): {str(e)}",
            )

    def get_driver(self) -> webdriver:
        return self.driver

    def get_test_results(self) -> List[str]:
        return self.test_results
