from selenium import webdriver
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.firefox.firefox_profile import FirefoxProfile
from selenium.webdriver.edge.options import Options as EdgeOptions
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager
from webdriver_manager.microsoft import EdgeChromiumDriverManager
import os
from typing import Optional
from selenium.webdriver.chrome.service import Service
from selenium_stealth import stealth
import random
import string


class DriverManager:
    def __init__(self):
        self.driver: Optional[webdriver.Chrome] = None

    def create_driver(self, browser_type: str = "chrome") -> webdriver.Chrome:
        if browser_type.lower() != "chrome":
            raise ValueError("Поддерживается только Chrome")

        chrome_options = ChromeOptions()

        # Добавляем опции для обхода Cloudflare и защиты от обнаружения
        chrome_options.add_argument("--no-sandbox")
        chrome_options.add_argument("--disable-dev-shm-usage")
        chrome_options.add_argument("--disable-blink-features=AutomationControlled")
        chrome_options.add_experimental_option("excludeSwitches", ["enable-automation"])
        chrome_options.add_experimental_option("useAutomationExtension", False)
        
        # Устанавливаем параметры, имитирующие обычный браузер
        chrome_options.add_argument("--disable-extensions")
        chrome_options.add_argument("--disable-gpu")
        chrome_options.add_argument("--disable-infobars")
        chrome_options.add_argument("--disable-notifications")
        chrome_options.add_argument("--disable-popup-blocking")
        chrome_options.add_argument("--disable-web-security")
        chrome_options.add_argument("--ignore-certificate-errors")
        chrome_options.add_argument("--ignore-ssl-errors")
        chrome_options.add_argument("--start-maximized")
        
        width, height = 1920,1080
        chrome_options.add_argument(f"--window-size={width},{height}")
        
        # Добавляем user-agent из списка реальных UA
        user_agents = [
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0"
        ]
        chrome_options.add_argument(f"--user-agent={random.choice(user_agents)}")
        
        # Устанавливаем языковые настройки
        chrome_options.add_argument("--lang=ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
        
        # Используем случайные геолокационные данные
        lats = [55.7558, 59.9343, 54.7065, 56.8389, 53.1955]  # Moscow, SPB, Novosibirsk, etc.
        lngs = [37.6176, 30.3351, 20.5216, 60.6057, 50.1216]
        position = random.randint(0, len(lats) - 1)
        chrome_prefs = {
            "profile.default_content_setting_values.geolocation": 1,
            "profile.default_content_settings.geolocation": 1,
            "profile.managed_default_content_settings.geolocation": 1,
            "profile.content_settings.exceptions.geolocation": {
                f"https://www.bongacams.com,*": {"setting": 1}
            }
        }
        chrome_options.add_experimental_option("prefs", chrome_prefs)
        
        # Создаем драйвер с увеличенным таймаутом для скрытых действий
        service = Service(ChromeDriverManager().install())
        self.driver = webdriver.Chrome(service=service, options=chrome_options)
        
        # Устанавливаем геолокацию через JavaScript
        self.driver.execute_cdp_cmd("Emulation.setGeolocationOverride", {
            "latitude": lats[position],
            "longitude": lngs[position],
            "accuracy": 100
        })

        # Применяем stealth режим с расширенными настройками
        stealth(
            self.driver,
            languages=["ru-RU", "ru", "en-US", "en"],
            vendor="Google Inc.",
            platform="Win32", 
            webgl_vendor="Intel Inc.",
            renderer="Intel Iris OpenGL Engine",
            fix_hairline=True,
            run_on_insecure_origins=True,
        )
        
        # Добавляем случайную задержку перед действиями
        self.driver.implicitly_wait(random.uniform(8, 12))
        
        # Удаляем вебдрайвер из navigator через JavaScript
        self.driver.execute_script("""
        try {
            // Скрываем webdriver
            Object.defineProperty(navigator, 'webdriver', {
                get: () => undefined
            });
            
            // Добавляем случайную задержку для имитации поведения человека 
            // Безопасным способом, не заменяя нативный querySelector
            console.log('Webdriver property hidden');
        } catch (e) {
            console.log('Error hiding webdriver: ' + e.message);
        }
        """)
        
        # Добавляем случайные данные в localStorage для имитации обычного пользователя
        self.driver.execute_script("""
        try {
            // Проверяем доступность localStorage
            if (window.localStorage) {
                localStorage.setItem('visited_before', 'true');
                localStorage.setItem('last_visit', new Date().toString());
                localStorage.setItem('user_id', Math.random().toString(36).substr(2, 9));
                console.log('localStorage configured');
            }
        } catch (e) {
            console.log('localStorage not available: ' + e.message);
        }
        """)
        
        # Устанавливаем отпечаток canvas
        self.driver.execute_script("""
        try {
            const getImageData = CanvasRenderingContext2D.prototype.getImageData;
            CanvasRenderingContext2D.prototype.getImageData = function() {
                const imageData = getImageData.apply(this, arguments);
                // Вносим небольшие случайные изменения в пиксели, не затрагивающие видимый результат
                for (let i = 0; i < 3; i++) {
                    const randomPixel = Math.floor(Math.random() * imageData.data.length / 4) * 4;
                    const offset = Math.floor(Math.random() * 3);
                    imageData.data[randomPixel + offset] = imageData.data[randomPixel + offset] % 255;
                }
                return imageData;
            };
            console.log('Canvas fingerprint modified');
        } catch (e) {
            console.log('Canvas modification error: ' + e.message);
        }
        """)

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
        elif browser_type == "edge":
            EdgeChromiumDriverManager().install()
            options = EdgeOptions()
            options.add_argument("--start-maximized")
            options.add_argument("--disable-blink-features=AutomationControlled")

            if incognito:
                options.add_argument("--incognito")
                print("Edge запускается в режиме инкогнито")

            driver = webdriver.Edge(options=options)
        else:
            raise ValueError(f"Неподдерживаемый тип браузера: {browser_type}")

        driver.implicitly_wait(10)
        driver.set_page_load_timeout(30)
        return driver

    def random_string(self, length=10):
        """Генерирует случайную строку указанной длины"""
        return ''.join(random.choice(string.ascii_letters + string.digits) for _ in range(length))
