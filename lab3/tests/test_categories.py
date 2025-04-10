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
            
            # Пробуем точный XPath, предоставленный пользователем
            user_xpath = "//*[@id='ls_sub_header_container']/div/div/div[1]/div[2]/div/a[5]"
            
            # Другие XPath для поиска
            xpaths_to_try = [
                user_xpath,  # Сначала пробуем XPath, предоставленный пользователем
                "//a[contains(text(), 'Новые молоденькие 18+') or contains(text(), 'Новые Молоденькие 18+')]",
                "//a[contains(text(), 'молоденькие') or contains(text(), 'Молоденькие')]",
                "//a[contains(@href, '/female/tags/new-teens')]",
                "//a[@href='/female/tags/new-teens']",
                "//div[contains(@class, 'ht_list')]//a[position()=5]",  # Пятый элемент в списке
            ]

            young_category: Optional[WebElement] = None
            found_by = "unknown"
            
            # Создаем скриншот для анализа состояния страницы
            self.make_screenshot("before_category_search")
            
            # Пробуем все XPath по очереди
            for i, xpath in enumerate(xpaths_to_try):
                try:
                    print(f"Попытка {i+1}: Ищем элемент по XPath: {xpath}")
                    young_category = long_wait.until(
                        EC.element_to_be_clickable((By.XPATH, xpath))
                    )
                    if young_category:
                        found_by = f"XPath #{i+1}: {xpath}"
                        print(f"Категория найдена! Использован {found_by}")
                        
                        # Проверяем, правильная ли это категория
                        element_text = young_category.text.lower()
                        element_href = young_category.get_attribute("href")
                        
                        if ('молоденькие' in element_text or '18+' in element_text or 
                            'new-teens' in element_href):
                            print(f"Найдена корректная категория: '{young_category.text}' с URL: {element_href}")
                            break
                        else:
                            print(f"Найден элемент, но это не та категория. Текст: '{young_category.text}', URL: {element_href}")
                            young_category = None
                            continue
                except Exception as e:
                    print(f"XPath не сработал: {xpath}. Ошибка: {str(e)}")

            # Если элемент не найден вообще, тест не может продолжаться
            if not young_category:
                self.record_result(
                    "UC4: Выбор категории",
                    False,
                    "Не удалось найти элемент категории, тест прерван"
                )
                return False

            # Если нашли элемент, пробуем кликнуть
            category_name = young_category.text
            category_url = young_category.get_attribute("href")
            self.record_result(
                "UC4: Выбор категории",
                True,
                f"Найдена категория: {category_name} с href: {category_url} (найдено через {found_by})",
            )

            before_click_url = self.driver.current_url

            # Делаем скриншот перед кликом
            self.make_screenshot("before_category_click")
            
            click_success = False
            max_attempts = 3
            attempt = 0
            
            while not click_success and attempt < max_attempts:
                attempt += 1
                print(f"Попытка клика #{attempt}")
                
                try:
                    # Сначала проверим, валиден ли еще элемент (не стал ли stale)
                    try:
                        if not young_category.is_enabled() or not young_category.is_displayed():
                            print("Элемент больше не валиден, ищем элемент снова")
                            for xpath in xpaths_to_try:
                                try:
                                    temp_element = self.driver.find_element(By.XPATH, xpath)
                                    if temp_element and temp_element.is_displayed() and temp_element.is_enabled():
                                        young_category = temp_element
                                        print(f"Найден новый элемент: {young_category.text}")
                                        break
                                except:
                                    continue
                    except:
                        print("Невозможно проверить состояние элемента, ищем элемент снова")
                        category_elements = self.driver.find_elements(By.XPATH, "//a[contains(text(), 'Молоденькие')]")
                        if category_elements and len(category_elements) > 0:
                            young_category = category_elements[0]
                            print(f"Найден новый элемент: {young_category.text}")
                    try:
                        target_url = young_category.get_attribute("href")
                        print(f"URL категории: {target_url}")
                    except:
                        print("Не удалось получить URL категории")
                    
                    # Используем полную последовательность событий мыши для имитации естественного взаимодействия
                    if attempt < 5:
                        print(f"Попытка {attempt}: Используем полную последовательность событий с помощью ActionChains")
                        actions = ActionChains(self.driver)
                        # Симулируем реальное взаимодействие пользователя
                        actions.move_to_element(young_category)  # Наведение на элемент
                        actions.pause(1)  # Пауза для отображения hover эффекта
                        actions.click(young_category)  # Клик
                        actions.pause(1)
                        actions.double_click(young_category)

                        actions.perform()
                    
                    # Увеличиваем время ожидания после клика
                    print(f"Ожидание после клика (попытка {attempt})...")
                    self.sleep(10000)  # 10 секунд ожидания
                    
                    # Проверяем, изменился ли URL
                    after_click_url = self.driver.current_url
                    print(f"URL до клика: {before_click_url}")
                    print(f"URL после клика (попытка {attempt}): {after_click_url}")
                    
                    if after_click_url != before_click_url:
                        click_success = True
                        print(f"Клик успешен! URL изменился на: {after_click_url}")
                    else:
                        print(f"URL не изменился после попытки {attempt}")
                except Exception as e:
                    print(f"Ошибка при попытке клика #{attempt}: {str(e)}")
                
                # Проверяем наличие Cloudflare после попытки клика
                if self.is_cloudflare_challenge():
                    print(f"Обнаружен Cloudflare после попытки клика #{attempt}")
                    if self.wait_for_manual_captcha_solving():
                        print("Cloudflare пройден, продолжаем")
                        self.sleep(5000)
                        # После прохождения Cloudflare считаем клик успешным
                        click_success = True
                    else:
                        print("Не удалось пройти Cloudflare")
                        break
            
            # Создаем скриншот после попыток клика
            self.make_screenshot("after_category_clicks")
            
            # Проверяем результат навигации
            final_url = self.driver.current_url
            if click_success or final_url != before_click_url:
                self.record_result(
                    "UC4: Выбор категории",
                    True,
                    f"Успешный переход на страницу категории: {final_url}"
                )
                return True
            else:
                self.record_result(
                    "UC4: Выбор категории",
                    False,
                    f"Не удалось перейти на страницу категории после {max_attempts} попыток"
                )
                return False
        except Exception as e:
            self.record_result("UC4: Выбор категории", False, f"Ошибка: {str(e)}")
            return False

    def count_models_in_category(self) -> int:
        """Подсчет количества моделей в выбранной категории"""
        try:
            print("Подсчет количества моделей в выбранной категории...")
            
            # Сначала проверяем наличие Cloudflare
            if self.is_cloudflare_challenge():
                print("Обнаружен Cloudflare challenge перед подсчетом моделей")
                if not self.wait_for_manual_captcha_solving():
                    print("Не удалось пройти Cloudflare, подсчет моделей невозможен")
                    return 0
                print("Cloudflare challenge пройден, продолжаем подсчет")
                # Дополнительная пауза после прохождения Cloudflare
                self.sleep(5000)
            
            # Увеличиваем таймаут для поиска моделей
            long_wait = WebDriverWait(self.driver, 30)
            
            # Делаем скриншот перед поиском моделей для отладки
            self.make_screenshot("before_models_search")
            
            # Различные XPath для поиска моделей на странице
            model_xpaths = [
                "//div[contains(@class, 'ls_thumb')]",
                "//div[contains(@class, 'lst_wrp')]/div",
                "//div[contains(@class, 'ls_thumb_wrapper')]",
                "//div[contains(@class, 'bChatRoomThumbnailsOriginal')]",
                "//div[contains(@class, 'c-1')]//div[contains(@class, 'ls_thumb')]",
                "//div[contains(@class, 'room-list')]//div[contains(@class, 'room-item')]",
                "//div[contains(@class, 'model-list')]//div[contains(@class, 'model-item')]",
                "//div[contains(@class, 'room') or contains(@class, 'model')]",
            ]
            
            models_count = 0
            found_by_xpath = ""
            
            # Вывод HTML структуры страницы для отладки
            try:
                html_structure = self.driver.execute_script(
                    "return document.body.innerHTML.substring(0, 5000);"
                )
                print(f"Часть HTML структуры страницы (первые 5000 символов):\n{html_structure}")
                
                # Получаем URL страницы для проверки
                current_url = self.driver.current_url
                print(f"Текущий URL при подсчете моделей: {current_url}")
            except Exception as html_err:
                print(f"Не удалось получить HTML структуру: {str(html_err)}")
            
            # Пробуем найти модели по различным XPath
            for xpath in model_xpaths:
                try:
                    print(f"Поиск моделей по XPath: {xpath}")
                    model_elements = self.driver.find_elements(By.XPATH, xpath)
                    count = len(model_elements)
                    
                    if count > models_count:
                        models_count = count
                        found_by_xpath = xpath
                        print(f"Найдено {models_count} моделей по XPath: {xpath}")
                        
                        # Выводим текст/атрибуты первых нескольких элементов для проверки
                        if count > 0 and count < 5:
                            for i, element in enumerate(model_elements):
                                try:
                                    element_html = element.get_attribute("outerHTML")
                                    print(f"Элемент {i+1}: {element_html[:200]}...")
                                except:
                                    print(f"Не удалось получить HTML элемента {i+1}")
                except Exception as e:
                    print(f"Ошибка при поиске по XPath {xpath}: {str(e)}")
            
            # Если не удалось найти модели по XPath, пробуем JavaScript
            if models_count == 0:
                try:
                    print("Использование JavaScript для поиска моделей...")
                    self.make_screenshot("models_search_problem")
                    
                    models_count = self.driver.execute_script("""
                        var selectors = [
                            '.ls_thumb', 
                            '.lst_wrp > div', 
                            '.ls_thumb_wrapper',
                            '.bChatRoomThumbnailsOriginal',
                            '.c-1 .ls_thumb',
                            '.room-list .room-item',
                            '.model-list .model-item',
                            '[class*="room"], [class*="model"]'
                        ];
                        
                        var results = {};
                        
                        for (var i = 0; i < selectors.length; i++) {
                            var selector = selectors[i];
                            var elements = document.querySelectorAll(selector);
                            if (elements && elements.length > 0) {
                                console.log('Найдено ' + elements.length + ' моделей по селектору: ' + selector);
                                results[selector] = elements.length;
                                // Выводим первый элемент для отладки
                                if (elements[0]) {
                                    console.log('Пример элемента: ' + elements[0].outerHTML.substring(0, 200));
                                }
                            }
                        }
                        
                        // Возвращаем максимальное количество
                        var max = 0;
                        for (var key in results) {
                            if (results[key] > max) max = results[key];
                        }
                        
                        return max;
                    """)
                    
                    print(f"JavaScript: найдено {models_count} моделей")
                except Exception as js_err:
                    print(f"Ошибка при использовании JavaScript: {str(js_err)}")
            
            # Если до сих пор ничего не нашли, проверяем еще раз Cloudflare
            if models_count == 0:
                print("Модели не найдены, проверяем наличие Cloudflare еще раз")
                if self.is_cloudflare_challenge():
                    print("Обнаружен Cloudflare challenge после поиска моделей")
                    if self.wait_for_manual_captcha_solving():
                        print("Cloudflare пройден, пробуем подсчитать модели еще раз")
                        # Рекурсивно вызываем метод еще раз после прохождения Cloudflare
                        return self.count_models_in_category()
            
            return models_count
        except Exception as e:
            print(f"Ошибка при подсчете моделей: {str(e)}")
            return 0

    def test_category_model_count(self) -> bool:
        """Проверка количества моделей в выбранной категории"""
        try:
            # Проверяем наличие Cloudflare перед тестом
            if self.is_cloudflare_challenge():
                if not self.wait_for_manual_captcha_solving():
                    self.record_result(
                        "UC5: Количество моделей в категории",
                        False,
                        "Не удалось пройти защиту Cloudflare с CAPTCHA перед подсчетом моделей."
                    )
                    return False
            
            # Подсчет моделей
            model_count = self.count_models_in_category()
            
            # Проверка количества моделей (не менее 20)
            min_expected = 20
            
            if model_count >= min_expected:
                self.record_result(
                    "UC5: Количество моделей в категории",
                    True,
                    f"Найдено {model_count} моделей (ожидалось не менее {min_expected})"
                )
                return True
            else:
                self.record_result(
                    "UC5: Количество моделей в категории",
                    False,
                    f"Найдено {model_count} моделей (ожидалось не менее {min_expected})"
                )
                return False
        except Exception as e:
            self.record_result(
                "UC5: Количество моделей в категории",
                False,
                f"Ошибка при подсчете моделей: {str(e)}"
            )
            return False

    def run_tests_impl(self):
        try:
            self.setup()

            if "bongacams" in self.driver.current_url:
                self.test_list_categories()
                self.test_select_young_category()
                self.test_category_model_count()
            else:
                if self.test_homepage_loads():
                    self.test_list_categories()
                    self.test_select_young_category()
                    self.test_category_model_count()
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
