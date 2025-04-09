from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.firefox.service import Service as FirefoxService
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.common.exceptions import TimeoutException, NoSuchElementException
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.action_chains import ActionChains
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.firefox import GeckoDriverManager
import time
import sys
import os


# Function to initialize WebDriver for a given browser
def initialize_driver(browser_type):
    if browser_type.lower() == "chrome":
        options = ChromeOptions()
        options.add_argument("--start-maximized")
        options.add_argument("--disable-notifications")
        
        # Try to find Chrome in the default installation paths
        chrome_paths = [
            r"C:\Program Files\Google\Chrome\Application\chrome.exe",
            r"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe",
            # Add any other potential paths here
        ]
        
        for path in chrome_paths:
            if os.path.exists(path):
                options.binary_location = path
                break
        
        service = ChromeService(ChromeDriverManager().install())
        driver = webdriver.Chrome(service=service, options=options)
    elif browser_type.lower() == "firefox":
        options = FirefoxOptions()
        options.add_argument("--start-maximized")
        options.add_argument("--disable-notifications")
        
        # Try to find Firefox in the default installation paths
        firefox_paths = [
            r"C:\Program Files\Mozilla Firefox\firefox.exe",
            r"C:\Program Files (x86)\Mozilla Firefox\firefox.exe",
            # Add any other potential paths here
        ]
        
        for path in firefox_paths:
            if os.path.exists(path):
                options.binary_location = path
                break
                
        service = FirefoxService(GeckoDriverManager().install())
        driver = webdriver.Firefox(service=service, options=options)
    else:
        raise ValueError("Unsupported browser type")
    return driver


class BongacamsTest:
    def __init__(self, browser_type):
        self.browser_type = browser_type
        self.driver = None
        self.wait = None
        self.test_results = []
        
    def setup(self):
        print(f"Setting up test environment in {self.browser_type}...")
        self.driver = initialize_driver(self.browser_type)
        self.wait = WebDriverWait(self.driver, 15)  # Increased timeout for better reliability
        
    def teardown(self):
        if self.driver:
            self.driver.quit()
        print(f"\nTest Results for {self.browser_type}:")
        for result in self.test_results:
            print(f"- {result}")
        print(f"Tests completed in {self.browser_type}\n")
        
    def record_result(self, test_name, passed, message=""):
        result = f"{'✓' if passed else '✗'} {test_name}: {message}"
        self.test_results.append(result)
        print(result)
        
    def handle_age_verification(self):
        try:
            confirm_button = self.wait.until(
                EC.element_to_be_clickable(
                    (By.XPATH, "//a[contains(@class, 'agree') and contains(@class, 'js-close_warning')] | //a[contains(text(), 'Продолжить')]")
                )
            )
            confirm_button.click()
            self.record_result("Age verification", True, "Age verification popup handled")
            time.sleep(2)  # Wait for popup to disappear
        except TimeoutException:
            self.record_result("Age verification", True, "No age verification popup detected")
            
    def test_homepage_loads(self):
        try:
            self.driver.get("http://www.bongacams.com/")
            time.sleep(3)  # Allow page to load fully
            
            self.handle_age_verification()
            
            assert "BongaCams" in self.driver.title, "Homepage failed to load correctly"
            self.record_result("UC1: Homepage Load", True, "Homepage loaded successfully")
            return True
        except Exception as e:
            self.record_result("UC1: Homepage Load", False, f"Failed: {str(e)}")
            return False
            
    def test_model_count(self):
        try:
            # Different possible XPaths for model elements
            xpath_options = [
                "//div[contains(@class, 'model')]",
                "//div[contains(@class, 'cam-item')]",
                "//div[contains(@class, 'performer')]"
            ]
            
            model_elements = None
            for xpath in xpath_options:
                try:
                    model_elements = self.wait.until(
                        EC.presence_of_all_elements_located((By.XPATH, xpath))
                    )
                    if model_elements and len(model_elements) > 0:
                        break
                except Exception:
                    continue
                    
            if not model_elements:
                raise NoSuchElementException("Could not find model elements with any of the expected XPaths")
                
            model_count = len(model_elements)
            assert model_count > 100, f"Expected 100+ models, found {model_count}"
            self.record_result("UC2: Model Count", True, f"Found {model_count} models (100+ verified)")
            return True
        except Exception as e:
            self.record_result("UC2: Model Count", False, f"Failed: {str(e)}")
            return False
            
    def test_navigate_to_model_profile(self):
        try:
            # Different possible XPaths for clicking on a model
            xpath_options = [
                "(//div[contains(@class, 'model')])[1]",
                "(//div[contains(@class, 'cam-item')])[1]",
                "(//div[contains(@class, 'performer')])[1]",
                "(//a[contains(@class, 'model')])[1]"
            ]
            
            model_element = None
            for xpath in xpath_options:
                try:
                    model_element = self.wait.until(
                        EC.element_to_be_clickable((By.XPATH, xpath))
                    )
                    if model_element:
                        break
                except:
                    continue
                    
            if not model_element:
                raise NoSuchElementException("Could not find clickable model element")
                
            # Get model name for verification if possible
            try:
                model_name = model_element.get_attribute("data-model") or model_element.text
                model_name = model_name.strip()
            except:
                model_name = None
                
            model_element.click()
            time.sleep(3)  # Wait for profile page to load
            
            # Verify we're on a model page
            current_url = self.driver.current_url.lower()
            assert any(term in current_url for term in ["profile", "model", "cam"]), "Failed to navigate to model profile"
            
            if model_name:
                self.record_result("UC3: Model Profile Navigation", True, 
                                  f"Successfully navigated to model profile for {model_name}")
            else:
                self.record_result("UC3: Model Profile Navigation", True, 
                                  "Successfully navigated to model profile")
            return True
        except Exception as e:
            self.record_result("UC3: Model Profile Navigation", False, f"Failed: {str(e)}")
            return False
            
    def test_verify_live_stream(self):
        try:
            # Different possible XPaths for stream elements
            xpath_options = [
                "//video",
                "//div[contains(@class, 'stream')]",
                "//div[contains(@class, 'video-player')]",
                "//div[contains(@class, 'broadcast')]",
                "//iframe[contains(@src, 'stream')]"
            ]
            
            stream_element = None
            for xpath in xpath_options:
                try:
                    stream_element = self.wait.until(
                        EC.visibility_of_element_located((By.XPATH, xpath))
                    )
                    if stream_element:
                        break
                except:
                    continue
                    
            if not stream_element:
                raise NoSuchElementException("Could not find a live stream element")
                
            assert stream_element.is_displayed(), "Live stream element not visible"
            self.record_result("UC4: Live Stream", True, "Live stream element is present and visible")
            return True
        except Exception as e:
            self.record_result("UC4: Live Stream", False, f"Failed: {str(e)}")
            return False
            
    def test_search_functionality(self):
        try:
            # Go back to main page
            self.driver.get("http://www.bongacams.com/")
            time.sleep(3)
            
            self.handle_age_verification()
            
            # Find search input using different possible XPaths
            xpath_options = [
                "//input[@type='search']",
                "//input[contains(@class, 'search')]",
                "//div[contains(@class, 'search')]//input",
                "//form[contains(@class, 'search')]//input"
            ]
            
            search_input = None
            for xpath in xpath_options:
                try:
                    search_input = self.wait.until(
                        EC.element_to_be_clickable((By.XPATH, xpath))
                    )
                    if search_input:
                        break
                except:
                    continue
                    
            if not search_input:
                raise NoSuchElementException("Could not find search input field")
                
            # Type a generic search term
            search_term = "blonde"
            search_input.clear()
            search_input.send_keys(search_term)
            search_input.send_keys(Keys.ENTER)
            time.sleep(3)  # Wait for search results
            
            # Verify the URL or page state indicates search results
            assert search_term.lower() in self.driver.current_url.lower() or \
                   search_term.lower() in self.driver.page_source.lower(), \
                   "Search results page not loaded"
                   
            self.record_result("UC5: Search Functionality", True, f"Search for '{search_term}' successful")
            return True
        except Exception as e:
            self.record_result("UC5: Search Functionality", False, f"Failed: {str(e)}")
            return False
            
    def test_filter_functionality(self):
        try:
            # Go back to main page
            self.driver.get("http://www.bongacams.com/")
            time.sleep(3)
            
            self.handle_age_verification()
            
            # Look for filter/category elements with different possible XPaths
            xpath_options = [
                "//div[contains(@class, 'filter')]//a",
                "//div[contains(@class, 'category')]//a",
                "//div[contains(@class, 'tag')]//a",
                "//a[contains(@class, 'filter')]",
                "//a[contains(@class, 'category')]"
            ]
            
            filter_elements = None
            for xpath in xpath_options:
                try:
                    filter_elements = self.driver.find_elements(By.XPATH, xpath)
                    if filter_elements and len(filter_elements) > 0:
                        break
                except:
                    continue
                    
            if not filter_elements or len(filter_elements) == 0:
                raise NoSuchElementException("Could not find filter/category elements")
                
            # Click on a filter
            filter_element = filter_elements[0]
            filter_name = filter_element.text.strip()
            filter_element.click()
            time.sleep(3)  # Wait for filtered results
            
            # Verify filter applied (check URL or page state)
            assert filter_name.lower() in self.driver.current_url.lower() or \
                   filter_name.lower() in self.driver.page_source.lower(), \
                   "Filter not applied"
                   
            self.record_result("UC6: Filter Functionality", True, f"Filter by '{filter_name}' successful")
            return True
        except Exception as e:
            self.record_result("UC6: Filter Functionality", False, f"Failed: {str(e)}")
            return False
    
    def run_tests(self):
        try:
            self.setup()
            
            # Execute test cases
            if self.test_homepage_loads():
                pass
                self.test_model_count()
                if self.test_navigate_to_model_profile():
                    self.test_verify_live_stream()
                self.test_search_functionality()
                self.test_filter_functionality()
        except Exception as e:
            self.record_result("Test Suite", False, f"Unexpected error: {str(e)}")
        finally:
            self.teardown()


# Run tests in both browsers
if __name__ == "__main__":
    browsers = ["chrome"]
    
    if len(sys.argv) > 1:
        # Allow specific browser selection from command line
        browsers = [sys.argv[1].lower()]
        
    for browser in browsers:
        try:
            test = BongacamsTest(browser)
            test.run_tests()
        except Exception as e:
            print(f"Failed to run tests in {browser}: {str(e)}")
