# Laboratory Work #3 - Functional Testing with Selenium

Example of running tests:

```shell
PS F:\git\Software-Testing\lab3> mvn test
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------< itmo.bonga:bongacams-selenium-tests >-----------------
[INFO] Building bongacams-selenium-tests 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ bongacams-selenium-tests ---
[INFO] skip non existing resourceDirectory F:\git\Software-Testing\lab3\src\main\resources
[INFO]
[INFO] --- compiler:3.11.0:compile (default-compile) @ bongacams-selenium-tests ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- resources:3.3.1:testResources (default-testResources) @ bongacams-selenium-tests ---
[INFO] skip non existing resourceDirectory F:\git\Software-Testing\lab3\src\test\resources
[INFO]
[INFO] --- compiler:3.11.0:testCompile (default-testCompile) @ bongacams-selenium-tests ---
[INFO] Nothing to compile - all classes are up to date
[INFO]
[INFO] --- surefire:3.1.2:test (default-test) @ bongacams-selenium-tests ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running itmo.bonga.PageObjectLoginTest
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Using chromedriver 135.0.7049.114 (resolved driver for Chrome 135)
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Exporting webdriver.chrome.driver as C:\Users\art\.cache\selenium\chromedriver\win64\135.0.7049.114\chromedriver.exe   
Chrome запускается в режиме инкогнито
апр. 25, 2025 5:15:25 AM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
WARNING: Unable to find CDP implementation matching 135
апр. 25, 2025 5:15:25 AM org.openqa.selenium.chromium.ChromiumDriver lambda$new$5
WARNING: Unable to find version of CDP to use for 135.0.7049.115. You may need to include a dependency on a specific version of the CDP using something similar to `org.seleniumhq.selenium:selenium-devtools-v86:4.15.0` where the version ("v86") matches the version of the chromium-based browser you're using and the version number of the artifact is the same as Selenium's.
Test started, browser initialized
Trying to switch to login iframe
Successfully switched to login iframe
Clicked login button using primary locator
Looking for password field with primary locator
Looking for password field with primary locator
Clicked submit button using second locator
Successfully detected mailbox page
Current page title: (2) Входящие - VK WorkMail
Attempting to find filter button with locator: By.cssSelector: .filters-control
Successfully clicked on filter button
Attempting to find unread filter option with locator: By.cssSelector: #app-canvas > div > div.application-mail > div.application-mail__overlay.js-promo-id_new-toolbar > div > div.application-mail__layout.application-mail__layout_main > div > div.layout__main-frame > div > div > div > div > div > div.new-menu > div > div:nth-child(2) > div > div > div > div.select__items.select__items_expanded.select__items_align-right > div > div:nth-child(2)
Successfully clicked on unread filter option
Found 16 emails in the current folder
Current email count during wait: 16
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Found 2 emails in the current folder
Current email count during wait: 2
Failed to verify no unread emails left: Expected condition failed: waiting for itmo.bonga.pages.MailboxPage$$Lambda$861/0x000001b72027f258@7e46d648 (tried for 10 second(s) with 500 milliseconds interval)
Build info: version: '4.15.0', revision: '1d14b5521b'
System info: os.name: 'Windows 11', os.arch: 'amd64', os.version: '10.0', java.version: '17.0.10'
Driver info: org.openqa.selenium.chrome.ChromeDriver
Capabilities {acceptInsecureCerts: false, browserName: chrome, browserVersion: 135.0.7049.115, chrome: {chromedriverVersion: 135.0.7049.114 (63fd8a7d9d0..., userDataDir: C:\Users\art\AppData\Local\...}, fedcm:accounts: true, goog:chromeOptions: {debuggerAddress: localhost:57765}, networkConnectionEnabled: false, pageLoadStrategy: normal, platformName: windows, proxy: Proxy(), se:cdp: ws://localhost:57765/devtoo..., se:cdpVersion: 135.0.7049.115, setWindowRect: true, strictFileInteractability: false, timeouts: {implicit: 0, pageLoad: 300000, script: 30000}, unhandledPromptBehavior: dismiss and notify, webauthn:extension:credBlob: true, webauthn:extension:largeBlob: true, webauthn:extension:minPinLength: true, webauthn:extension:prf: true, webauthn:virtualAuthenticators: true}
Session ID: 949f6cbe60f6dbae8e15edf22e58eeed
Found 2 emails in the current folder
Number of unread emails before marking as read: 2
Attempting to find select all button with locator: By.xpath: //span[contains(@class, 'button2__explanation') and contains(text(), 'Выделить все')]
Failed to find select all button with locator: By.xpath: //span[contains(@class, 'button2__explanation') and contains(text(), 'Выделить все')]
Attempting to find select all button with locator: By.cssSelector: .button2__ico .ico_16-select-all
Successfully clicked on select all button
Attempting to find mark as read button with locator: By.xpath: //div[contains(@class, 'button2__txt') and contains(text(), 'Отметить все прочитанными')]
Failed to find mark as read button with locator: By.xpath: //div[contains(@class, 'button2__txt') and contains(text(), 'Отметить все прочитанными')]
Attempting to find mark as read button with locator: By.cssSelector: .button2__ico .ico_16-status\:read
Successfully clicked on mark as read button
Found 2 emails in the current folder
Found 0 emails in the current folder
Successfully waited for all emails to be marked as read
Found 0 emails in the current folder
Current email count during wait: 0
Successfully verified no unread emails left
Found 0 emails in the current folder
Number of unread emails after marking as read: 0
Filter unread and mark as read test completed successfully
Browser closed
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Using chromedriver 135.0.7049.114 (resolved driver for Chrome 135)
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Exporting webdriver.chrome.driver as C:\Users\art\.cache\selenium\chromedriver\win64\135.0.7049.114\chromedriver.exe   
Chrome запускается в режиме инкогнито
апр. 25, 2025 5:16:35 AM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
WARNING: Unable to find CDP implementation matching 135
апр. 25, 2025 5:16:35 AM org.openqa.selenium.chromium.ChromiumDriver lambda$new$5
WARNING: Unable to find version of CDP to use for 135.0.7049.115. You may need to include a dependency on a specific version of the CDP using something similar to `org.seleniumhq.selenium:selenium-devtools-v86:4.15.0` where the version ("v86") matches the version of the chromium-based browser you're using and the version number of the artifact is the same as Selenium's.
Test started, browser initialized
Trying to switch to login iframe
Successfully switched to login iframe
Clicked login button using primary locator
Looking for password field with primary locator
Looking for password field with primary locator
Clicked submit button using second locator
Successfully detected mailbox page
Current page title: Входящие - VK WorkMail
Step-by-step login test using Page Object Model completed successfully
Browser closed
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Using chromedriver 135.0.7049.114 (resolved driver for Chrome 135)
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Exporting webdriver.chrome.driver as C:\Users\art\.cache\selenium\chromedriver\win64\135.0.7049.114\chromedriver.exe   
Chrome запускается в режиме инкогнито
апр. 25, 2025 5:16:43 AM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
WARNING: Unable to find CDP implementation matching 135
апр. 25, 2025 5:16:43 AM org.openqa.selenium.chromium.ChromiumDriver lambda$new$5
WARNING: Unable to find version of CDP to use for 135.0.7049.115. You may need to include a dependency on a specific version of the CDP using something similar to `org.seleniumhq.selenium:selenium-devtools-v86:4.15.0` where the version ("v86") matches the version of the chromium-based browser you're using and the version number of the artifact is the same as Selenium's.
Test started, browser initialized
Trying to switch to login iframe
Successfully switched to login iframe
Clicked login button using primary locator
Looking for password field with primary locator
Looking for password field with primary locator
Clicked submit button using second locator
Successfully detected mailbox page
Current page title: Входящие - VK WorkMail
Attempting to find emails in list with locator: By.cssSelector: a.llc.js-letter-list-item
Successfully found email subject: 2
Subject of the first email before clicking: 2
Attempting to find emails in list with locator: By.cssSelector: a.llc.js-letter-list-item
Email with subject: '2' will be clicked
Successfully clicked on first email
Subject saved when clicking: 2
Attempting to find email subject in opened mail with locator: By.cssSelector: h2.thread-subject
Successfully found opened email subject: 2
Subject from opened email: 2
Attempting to find email subject in opened mail with locator: By.cssSelector: h2.thread-subject
Successfully found opened email subject: 2
Comparing subjects - Clicked: '2', Opened: '2'
Opened email subject matches clicked email subject (using flexible comparison)
Exact match!
Open email and verify subject test completed successfully
Browser closed
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Using chromedriver 135.0.7049.114 (resolved driver for Chrome 135)
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Exporting webdriver.chrome.driver as C:\Users\art\.cache\selenium\chromedriver\win64\135.0.7049.114\chromedriver.exe   
Chrome запускается в режиме инкогнито
апр. 25, 2025 5:16:53 AM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
WARNING: Unable to find CDP implementation matching 135
апр. 25, 2025 5:16:53 AM org.openqa.selenium.chromium.ChromiumDriver lambda$new$5
WARNING: Unable to find version of CDP to use for 135.0.7049.115. You may need to include a dependency on a specific version of the CDP using something similar to `org.seleniumhq.selenium:selenium-devtools-v86:4.15.0` where the version ("v86") matches the version of the chromium-based browser you're using and the version number of the artifact is the same as Selenium's.
Test started, browser initialized
Trying to switch to login iframe
Successfully switched to login iframe
Clicked login button using primary locator
Looking for password field with primary locator
Looking for password field with primary locator
Clicked submit button using second locator
Successfully detected mailbox page
Current page title: Входящие - VK WorkMail
Attempting to find Trash folder with locator: By.cssSelector: a[href='/trash/?']
Successfully navigated to Trash folder
Found 7 emails in the current folder
Number of emails in Trash before deletion: 7
Attempting to find Inbox folder with locator: By.cssSelector: a[href='/inbox/?']
Successfully navigated to Inbox folder
Clicking directly on the email element
Attempting to find delete button with locator: By.cssSelector: .button2_delete
Successfully clicked delete button
Attempting to find Trash folder with locator: By.cssSelector: a[href='/trash/?']
Successfully navigated to Trash folder
Found 8 emails in the current folder
Email count increased from 7 to 8
Delete email and verify trash test completed successfully
Browser closed
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Using chromedriver 135.0.7049.114 (resolved driver for Chrome 135)
[main] INFO io.github.bonigarcia.wdm.WebDriverManager - Exporting webdriver.chrome.driver as C:\Users\art\.cache\selenium\chromedriver\win64\135.0.7049.114\chromedriver.exe
Chrome запускается в режиме инкогнито
апр. 25, 2025 5:17:03 AM org.openqa.selenium.devtools.CdpVersionFinder findNearestMatch
WARNING: Unable to find CDP implementation matching 135
апр. 25, 2025 5:17:03 AM org.openqa.selenium.chromium.ChromiumDriver lambda$new$5
WARNING: Unable to find version of CDP to use for 135.0.7049.115. You may need to include a dependency on a specific version of the CDP using something similar to `org.seleniumhq.selenium:selenium-devtools-v86:4.15.0` where the version ("v86") matches the version of the chromium-based browser you're using and the version number of the artifact is the same as Selenium's.
Test started, browser initialized
Trying to switch to login iframe
Successfully switched to login iframe
Clicked login button using primary locator
Looking for password field with primary locator
Looking for password field with primary locator
Clicked submit button using second locator
Successfully detected mailbox page
Current page title: Входящие - VK WorkMail
Attempting to find Sent folder with locator: By.cssSelector: a[href='/sent/?']
Successfully navigated to Sent folder
Found 6 emails in the current folder
Number of sent emails before sending: 6
Attempting to find Inbox folder with locator: By.cssSelector: a[href='/inbox/?']
Successfully navigated to Inbox folder
Attempting to find burger menu with locator: By.cssSelector: .button2_navigation_drop_down
Successfully clicked on burger menu button
Attempting to find 'Write to yourself' option with locator: By.xpath: //*[@id="app-canvas"]/div/div[1]/div[1]/div/div[2]/div/div[4]/div[1]/div/div/span/div/div[1]/div/div/div/div/div/div/div[1]
Failed to find 'Write to yourself' option with locator: By.xpath: //*[@id="app-canvas"]/div/div[1]/div[1]/div/div[2]/div/div[4]/div[1]/div/div/span/div/div[1]/div/div/div/div/div/div/div[1]
Attempting to find 'Write to yourself' option with locator: By.xpath: //div[contains(text(), 'Написать себе')]
Failed to find 'Write to yourself' option with locator: By.xpath: //div[contains(text(), 'Написать себе')]
Attempting to find 'Write to yourself' option with locator: By.cssSelector: .list-item .compose-dropdown__text
Successfully clicked on 'Write to yourself' option
Attempting to find subject input with locator: By.xpath: /html/body/div[1]/div/div[2]/div/div/div/div[2]/div[3]/div[2]/div/div[2]/div/input
Successfully entered subject: chpoki chpoki
Attempting to find send button with locator: By.xpath: /html/body/div[1]/div/div[2]/div/div/div/div[4]/div[1]/div[1]/div/button
Successfully clicked send button
Checking for empty email dialog with locator: By.xpath: /html/body/div[18]/div/div/div[2]/h3
Empty email confirmation dialog is displayed
Attempting to find confirm send button with locator: By.xpath: /html/body/div[18]/div/div/div[2]/button[1]
Successfully clicked confirm send button
Attempting to find confirmation message with locator: By.xpath: //a[contains(text(), 'Письмо отправлено')]
Failed to find confirmation message with locator: By.xpath: //a[contains(text(), 'Письмо отправлено')]
Attempting to find confirmation message with locator: By.xpath: //a[contains(@class, 'layer__link')]
Successfully found confirmation message: Письмо отправлено
Attempting to find close button with locator: By.cssSelector: .button2.button2_has-ico.button2_close
Successfully clicked close button on confirmation popup
Confirmation popup is closed
Attempting to find Sent folder with locator: By.cssSelector: a[href='/sent/?']
Successfully navigated to Sent folder
Found 7 emails in the current folder
Email count increased from 6 to 7
Send email to yourself test completed successfully
Browser closed
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 149.8 s -- in itmo.bonga.PageObjectLoginTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:32 min
[INFO] Finished at: 2025-04-25T05:17:54+03:00
[INFO] ------------------------------------------------------------------------
```
