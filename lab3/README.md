# Laboratory Work #3 - Functional Testing with Selenium

## Overview
This project implements automated functional testing of the BongaCams.com website interface using Selenium WebDriver. The tests are designed to execute in both Firefox and Chrome browsers.

## Use Cases
The following use cases have been defined and implemented in the test suite:

1. **UC1: Homepage Load** - User navigates to the homepage and it loads properly
   - Verifies the homepage loads correctly
   - Handles age verification popup if present

2. **UC2: Model Count** - User can see available models on the homepage
   - Verifies that more than 100 models are displayed as per requirements
   - Uses multiple XPath patterns to reliably find model elements

3. **UC3: Model Profile Navigation** - User can click on a model to view their profile
   - Clicks on the first available model
   - Verifies successful navigation to the model's profile page

4. **UC4: Live Stream** - User can view a live stream on a model's profile
   - Verifies that a live stream element is present and visible
   - Tests using multiple XPath patterns to reliably find the stream element

5. **UC5: Search Functionality** - User can search for models
   - Navigates back to the main page
   - Finds the search input field
   - Performs a search for "blonde"
   - Verifies search results are displayed

6. **UC6: Filter Functionality** - User can filter models by category
   - Navigates back to the main page
   - Finds and clicks on a filter/category element
   - Verifies the filter was successfully applied

## Test Implementation
- The tests use XPath selectors as required (not relying on IDs)
- Multiple XPath patterns are tried for each element to increase test reliability
- Tests run automatically in both Firefox and Chrome browsers
- Proper test reporting with clear pass/fail indicators

## Prerequisites
- Python 3.x
- Selenium WebDriver
- Chrome and Firefox browsers installed
- Appropriate WebDriver executables for Chrome and Firefox

## Running the Tests
```bash
# Run tests in both browsers
python lab.py

# Run tests in a specific browser
python lab.py chrome
# OR
python lab.py firefox
```

## Test Results
Test results are displayed in the console with pass/fail indicators for each test case.

## Notes
- The script handles age verification popups automatically
- Browser notifications and audio are disabled for better test execution
- The script includes error handling and detailed reporting
- Test cases are structured to be independent but also follow a logical flow 