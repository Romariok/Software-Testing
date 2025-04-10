from selenium import webdriver
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.firefox.firefox_profile import FirefoxProfile
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager
import os
from typing import Optional
from selenium.webdriver.chrome.service import Service
from selenium_stealth import stealth


class DriverManager:
    def __init__(self):
        self.driver: Optional[webdriver.Chrome] = None

    def create_driver(self, browser_type: str = "chrome") -> webdriver.Chrome:
        if browser_type.lower() != "chrome":
            raise ValueError("Поддерживается только Chrome")

        chrome_options = ChromeOptions()

        # Добавляем опции для обхода Cloudflare
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")
        chrome_options.add_argument("--disable-blink-features=AutomationControlled")
        chrome_options.add_argument("--disable-extensions")
        chrome_options.add_argument("--disable-gpu")
        chrome_options.add_argument("--disable-infobars")
        chrome_options.add_argument("--disable-notifications")
        chrome_options.add_argument("--disable-popup-blocking")
        chrome_options.add_argument("--disable-web-security")
        chrome_options.add_argument("--ignore-certificate-errors")
        chrome_options.add_argument("--ignore-ssl-errors")
        chrome_options.add_argument("--start-maximized")

        # Добавляем user-agent
        chrome_options.add_argument(
            "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36"
        )

        # Создаем драйвер
        self.driver = webdriver.Chrome(options=chrome_options)

        # Применяем stealth режим
        stealth(
            self.driver,
            languages=["ru-RU", "ru"],
            vendor="Google Inc.",
            platform="Win32",
            webgl_vendor="Intel Inc.",
            renderer="Intel Iris OpenGL Engine",
            fix_hairline=True,
        )

        return self.driver

    def get_driver(self) -> webdriver.Chrome:
        if not self.driver:
            self.create_driver()
        return self.driver

    def quit_driver(self):
        if self.driver:
            self.driver.quit()
            self.driver = None

    @staticmethod
    def initialize_driver(browser_type: str, incognito: bool = True) -> webdriver:
        return DriverManager._initialize_driver_impl(browser_type, incognito)

    @staticmethod
    def _initialize_driver_impl(browser_type: str, incognito: bool) -> webdriver:
        browser_type = browser_type.lower()
        driver: Optional[webdriver] = None

        if browser_type == "chrome":
            ChromeDriverManager().install()
            options = ChromeOptions()
            options.add_argument("--start-maximized")
            options.add_argument("--disable-notifications")
            options.add_argument("--disable-extensions")
            options.add_argument("--disable-gpu")
            options.add_argument("--disable-dev-shm-usage")
            options.add_argument("--no-sandbox")
            options.add_argument("--window-size=1920,1080")
            options.add_argument(
                "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
            )
            options.add_experimental_option(
                "excludeSwitches", ["enable-automation", "enable-logging"]
            )

            if incognito:
                options.add_argument("--incognito")
                print("Chrome запускается в режиме инкогнито")

            chrome_paths = [
                "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
            ]

            for path in chrome_paths:
                if os.path.exists(path):
                    options.binary_location = path
                    break

            driver = webdriver.Chrome(options=options)

        elif browser_type == "firefox":
            GeckoDriverManager().install()
            options = FirefoxOptions()
            options.add_argument("--start-maximized")
            options.add_argument("--disable-notifications")

            if incognito:
                options.add_argument("-private")
                print("Firefox запускается в приватном режиме")
                profile = FirefoxProfile()
                profile.set_preference("browser.privatebrowsing.autostart", True)
                options.profile = profile

            firefox_paths = [
                "C:\\Program Files\\Mozilla Firefox\\firefox.exe",
                "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe",
            ]

            for path in firefox_paths:
                if os.path.exists(path):
                    options.binary_location = path
                    break

            driver = webdriver.Firefox(options=options)
        else:
            raise ValueError(f"Неподдерживаемый тип браузера: {browser_type}")

        driver.implicitly_wait(10)
        driver.set_page_load_timeout(30)
        return driver
