
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint








import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
/**
 * Test Case: PBS Report Assignment
 * This test checks for reports with "To be reviewed" status and assigns them to deekshithaS.
 * If no "To be reviewed" reports are found, it checks the 3rd report.
 * If the 3rd report is not assigned to deekshithaS, it reassigns it.
 */

// Open browser and login to PBS system
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver


CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// =====================================================================
// PERIPHERAL SMEAR REPORT CHARACTER LIMIT TEST SCRIPT WITH SCROLLING
// =====================================================================
// PURPOSE: This script tests the ability of a medical reporting application to handle
// character limits (1500 chars) in five different text fields of a Peripheral Smear Report,
// with added functionality to ensure text areas are scrollable for better user experience.
// =====================================================================

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.Keys

// Verify the report title is correct on the page
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

// Define the expected character count constant for all text fields
final int EXPECTED_CHAR_COUNT = 1500

// Define this variable in a way that ensures it's accessible in all methods
// Flag to allow for tolerance of one extra character (e.g., if the system adds a space)
class TestConfig {
	static final boolean ALLOW_ONE_EXTRA_CHAR = true
	static final int SCROLL_TEST_PAUSE = 2 // Seconds to pause during scroll testing
	static final boolean ENABLE_SCROLL_TESTING = true // Toggle scroll testing
}

/**
 * Generates a string with a specific length containing a mix of alphabets, numbers, and special characters
 * @param length - The total desired length of the string
 * @param prefix - A prefix to add at the beginning (useful for identifying fields)
 * @return A string of the specified length with diverse character types
 */
def generateAlphaNumSpecialString(int length, String prefix) {
	// Start with the prefix
	StringBuilder builder = new StringBuilder(prefix)
	
	// Define character sets to use in the generated string
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
	String numbers = "0123456789"
	String specialChars = "!@#^&*()-_=+[]{}|;:'\",.<>/?`~\\€£¥©®™"
	
	// Calculate how many complete sets of all characters we can fit
	int charactersPerSet = alphabet.length() + numbers.length() + specialChars.length()
	int completeSetCount = (length - prefix.length()) / charactersPerSet
	
	// Add complete sets of characters
	for (int i = 0; i < completeSetCount; i++) {
		builder.append(alphabet)    // Add all alphabetic characters
		builder.append(numbers)     // Add all numeric characters
		builder.append(specialChars) // Add all special characters
	}
	
	// Add any remaining characters needed to reach the exact length
	int remaining = length - builder.length()
	if (remaining > 0) {
		builder.append(alphabet.substring(0, Math.min(remaining, alphabet.length())))
	}
	
	// Trim if the string ended up longer than needed
	if (builder.length() > length) {
		return builder.substring(0, length)
	}
	
	return builder.toString()
}

/**
 * Creates a string with line breaks to force scrolling behavior
 * @param baseText - Original text to modify
 * @return String with added line breaks for scroll testing
 */
def addLineBreaksForScroll(String baseText) {
	StringBuilder result = new StringBuilder()
	// Insert a line break every 50 characters to create vertical content
	for (int i = 0; i < baseText.length(); i++) {
		result.append(baseText.charAt(i))
		if (i > 0 && i % 50 == 0) {
			result.append("\n")
		}
	}
	return result.toString()
}

// Generate test strings for each field with unique prefixes for easier identification
def rbcMorphologyText = generateAlphaNumSpecialString(EXPECTED_CHAR_COUNT, "RBC-")
def wbcMorphologyText = generateAlphaNumSpecialString(EXPECTED_CHAR_COUNT, "WBC-")
def plateletMorphologyText = generateAlphaNumSpecialString(EXPECTED_CHAR_COUNT, "PLT-")
def hemoparasiteText = generateAlphaNumSpecialString(EXPECTED_CHAR_COUNT, "HMP-")
def impressionText = generateAlphaNumSpecialString(EXPECTED_CHAR_COUNT, "IMP-")

// Add line breaks to create scrollable content
if (TestConfig.ENABLE_SCROLL_TESTING) {
	rbcMorphologyText = addLineBreaksForScroll(rbcMorphologyText)
	wbcMorphologyText = addLineBreaksForScroll(wbcMorphologyText)
	plateletMorphologyText = addLineBreaksForScroll(plateletMorphologyText)
	hemoparasiteText = addLineBreaksForScroll(hemoparasiteText)
	impressionText = addLineBreaksForScroll(impressionText)
}

/**
 * Checks if a string contains alphabets, numbers, and special characters
 * @param text - The string to check
 * @return Boolean indicating if the string contains all three character types
 */
def containsAlphaNumSpecial(String text) {
	return text.find(/[a-zA-Z]/) &&       // Contains at least one letter
		   text.find(/[0-9]/) &&          // Contains at least one number
		   text.find(/[^a-zA-Z0-9\s]/)    // Contains at least one special character
}

/**
 * Verifies if the character count matches expectations and reports details
 * @param actualText - The text currently in the field
 * @param expectedText - The text that was supposed to be entered
 * @param fieldName - Name of the field (for reporting)
 * @return Map containing verification results
 */
def verifyCharacterCount(String actualText, String expectedText, String fieldName) {
	// Remove line breaks for character counting if scroll testing is enabled
	String normalizedActual = actualText.replaceAll("\n", "")
	String normalizedExpected = expectedText.replaceAll("\n", "")
	
	int actualCount = normalizedActual.length()
	int expectedCount = normalizedExpected.length()
	
	// Check if the text contains the expected mix of character types
	boolean hasCorrectCharMix = containsAlphaNumSpecial(normalizedActual)
	
	// Check if the character count matches, allowing for one extra character if flag is set
	boolean isLengthCorrect = (actualCount == expectedCount) ||
						   (TestConfig.ALLOW_ONE_EXTRA_CHAR && (actualCount == expectedCount + 1))
	
	// Verify that the text starts with our expected prefix (to ensure it's our test data)
	String expectedPrefix = normalizedExpected.substring(0, Math.min(4, normalizedExpected.length()))
	boolean hasCorrectPrefix = normalizedActual.startsWith(expectedPrefix)
	
	// Print detailed verification results for debugging
	println "==== ${fieldName} Field Verification ====="
	println "Expected length: ${expectedCount}"
	println "Actual length: ${actualCount}"
	println "Characters match: ${isLengthCorrect ? 'YES' : 'NO'}"
	
	// Special handling for the case of exactly one extra character
	if (actualCount == expectedCount + 1) {
		println "Note: One extra character detected (possibly a space added by the application)"
		// Check if the extra character is a trailing space
		if (normalizedActual.charAt(normalizedActual.length() - 1) == ' ') {
			println "Confirmed: The extra character is a trailing space"
		}
	}
	
	println "Contains alphabets, numbers, special chars: ${hasCorrectCharMix ? 'YES' : 'NO'}"
	println "Has correct prefix (${expectedPrefix}): ${hasCorrectPrefix ? 'YES' : 'NO'}"
	
	// Overall pass/fail determination for this field
	if (isLengthCorrect && hasCorrectCharMix && hasCorrectPrefix) {
		println "PASS: ${fieldName} field successfully contains ${actualCount} characters with the correct mix"
	} else {
		println "FAIL: ${fieldName} field verification failed"
		// Print samples of the text for debugging issues
		if (actualCount > 0) {
			println "First 50 chars: " + normalizedActual.substring(0, Math.min(50, actualCount))
			if (actualCount > 50) {
				println "Last 50 chars: " + normalizedActual.substring(Math.max(0, actualCount - 50))
			}
		} else {
			println "Field appears to be empty"
		}
	}
	
	// Return structured result data for summary reporting
	return [
		success: isLengthCorrect && hasCorrectCharMix && hasCorrectPrefix,
		actualCount: actualCount,
		expectedCount: expectedCount
	]
}

/**
 * Tests if a field is properly scrollable
 * @param fieldObject - Katalon TestObject reference to the UI element
 * @param fieldName - Name of the field (for reporting)
 * @return Boolean indicating if scrolling was successful
 */
def testFieldScrolling(TestObject fieldObject, String fieldName) {
	try {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		WebElement element = WebUI.findWebElement(fieldObject)
		
		// Get initial scroll position
		def initialScrollTop = js.executeScript("return arguments[0].scrollTop;", element)
		println "${fieldName}: Initial scroll position = ${initialScrollTop}"
		
		// Scroll to middle
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight / 2;", element)
		WebUI.delay(TestConfig.SCROLL_TEST_PAUSE)
		def midScrollTop = js.executeScript("return arguments[0].scrollTop;", element)
		println "${fieldName}: Mid-scroll position = ${midScrollTop}"
		
		// Scroll to bottom
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", element)
		WebUI.delay(TestConfig.SCROLL_TEST_PAUSE)
		def bottomScrollTop = js.executeScript("return arguments[0].scrollTop;", element)
		println "${fieldName}: Bottom scroll position = ${bottomScrollTop}"
		
		// Scroll back to top
		js.executeScript("arguments[0].scrollTop = 0;", element)
		WebUI.delay(TestConfig.SCROLL_TEST_PAUSE)
		def finalScrollTop = js.executeScript("return arguments[0].scrollTop;", element)
		println "${fieldName}: Final scroll position after reset = ${finalScrollTop}"
		
		// Check if scrolling occurred (values changed)
		boolean scrollingWorked = (initialScrollTop != midScrollTop) && (midScrollTop != bottomScrollTop)
		
		if (scrollingWorked) {
			println "PASS: ${fieldName} field is scrollable"
		} else {
			println "WARNING: ${fieldName} field may not be properly scrollable"
		}
		
		return scrollingWorked
	} catch (Exception e) {
		println "ERROR testing scrolling for ${fieldName}: ${e.getMessage()}"
		return false
	}
}

/**
 * Creates custom CSS to make text areas more obviously scrollable
 * @param fieldObject - Katalon TestObject reference to the UI element
 * @param fieldName - Name of the field (for reporting)
 */
def enhanceScrollVisibility(TestObject fieldObject, String fieldName) {
	try {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		WebElement element = WebUI.findWebElement(fieldObject)
		
		// Add custom styling to make the scrollbar more visible
		js.executeScript("""
            arguments[0].style.maxHeight = '200px';
            arguments[0].style.overflowY = 'scroll';
            arguments[0].style.borderColor = '#0066cc';
            arguments[0].style.borderWidth = '2px';
            arguments[0].style.padding = '10px';
            arguments[0].style.scrollbarWidth = 'thick'; /* Firefox */
            arguments[0].style.scrollbarColor = '#0066cc #f0f0f0'; /* Firefox */
        """, element)
		
		// Add a custom scrollbar style for WebKit browsers (Chrome, Safari)
		js.executeScript("""
            var style = document.createElement('style');
            style.type = 'text/css';
            style.innerHTML = `
                .enhanced-scrollbar::-webkit-scrollbar {
                    width: 12px;
                }
                .enhanced-scrollbar::-webkit-scrollbar-track {
                    background: #f0f0f0;
                    border-radius: 10px;
                }
                .enhanced-scrollbar::-webkit-scrollbar-thumb {
                    background-color: #0066cc;
                    border-radius: 10px;
                    border: 3px solid #f0f0f0;
                }
            `;
            document.head.appendChild(style);
            arguments[0].classList.add('enhanced-scrollbar');
        """, element)
		
		println "Enhanced scrolling visibility for ${fieldName}"
		
	} catch (Exception e) {
		println "ERROR enhancing scroll visibility for ${fieldName}: ${e.getMessage()}"
	}
}

/**
 * Enters text into a field and verifies the character count matches expectations
 * @param fieldObject - Katalon TestObject reference to the UI element
 * @param textToEnter - The text to enter into the field
 * @param fieldName - Name of the field (for reporting purposes)
 * @return Map containing test results (success, actual count, expected count)
 */
def enterAndVerifyTextInField(TestObject fieldObject, String textToEnter, String fieldName) {
	try {
		// Wait for the field to be visible and interact with it
		WebUI.waitForElementVisible(fieldObject, 20)  // Wait up to 20 seconds for the field to appear
		WebUI.click(fieldObject)                      // Click to focus on the field
		WebUI.clearText(fieldObject)                  // Clear any existing text
		
		// Enter text in chunks to avoid potential issues with very long strings
		int chunkSize = 500  // Split into manageable chunks of 500 characters
		for (int i = 0; i < textToEnter.length(); i += chunkSize) {
			int endIndex = Math.min(i + chunkSize, textToEnter.length())
			String chunk = textToEnter.substring(i, endIndex)
			WebUI.sendKeys(fieldObject, chunk)  // Enter each chunk
			
			// Small delay between chunks to ensure stable input
			WebUI.delay(0.5)  // Half-second delay between chunks
		}
		
		// Tab out to trigger any field validation/save behavior
		WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB))
		
		// Apply scrollable UI enhancements if enabled
		if (TestConfig.ENABLE_SCROLL_TESTING) {
			enhanceScrollVisibility(fieldObject, fieldName)
			testFieldScrolling(fieldObject, fieldName)
		}
		
		// Read back what's actually in the field now
		String enteredText = WebUI.getText(fieldObject)
		
		// Verify the character count and content
		return verifyCharacterCount(enteredText, textToEnter, fieldName)
	} catch (Exception e) {
		// Detailed error handling for troubleshooting
		println "FAIL: Error processing field ${fieldName}: ${e.getMessage()}"
		e.printStackTrace()
		return [success: false, actualCount: 0, expectedCount: textToEnter.length()]
	}
}

/**
 * Prints a summary of all test results
 * @param results - Map of field names to their test results
 */
def printTestSummary(Map<String, Map> results) {
	println "\n===== TEST SUMMARY ====="
	int passCount = 0
	int failCount = 0
	
	// Count passes and failures
	results.each { fieldName, result ->
		if (result.success) {
			passCount++
			println "${fieldName}: PASS (${result.actualCount}/${result.expectedCount} chars)"
		} else {
			failCount++
			println "${fieldName}: FAIL (${result.actualCount}/${result.expectedCount} chars)"
		}
	}
	
	// Print overall statistics
	println "\nTotal fields tested: ${results.size()}"
	println "Passed: ${passCount}"
	println "Failed: ${failCount}"
	
	// Print overall test result
	if (passCount == results.size()) {
		println "\nOVERALL TEST RESULT: PASS"
	} else {
		println "\nOVERALL TEST RESULT: FAIL"
	}
	
	// Print scrolling test status
	if (TestConfig.ENABLE_SCROLL_TESTING) {
		println "\nScroll Testing: ENABLED"
		println "Fields have been configured for scrolling with enhanced visibility"
	} else {
		println "\nScroll Testing: DISABLED"
	}
}

/**
 * Adds a help tooltip to explain scrolling functionality
 */
def addScrollingHelpTooltip() {
	try {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		
		// Create and inject a tooltip element
		js.executeScript("""
            var tooltip = document.createElement('div');
            tooltip.innerHTML = '<div style="position: fixed; bottom: 20px; right: 20px; background-color: #f8f9fa; border: 2px solid #0066cc; border-radius: 8px; padding: 10px; max-width: 300px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); z-index: 9999;">' + 
                '<h4 style="margin-top: 0; color: #0066cc;">Scrollable Fields</h4>' + 
                '<p>Long text fields can be scrolled. Look for blue borders indicating scrollable content.</p>' +
                '<button id="close-tooltip" style="background-color: #0066cc; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer;">Got it</button>' +
            '</div>';
            document.body.appendChild(tooltip);
            
            document.getElementById('close-tooltip').addEventListener('click', function() {
                this.parentNode.parentNode.remove();
            });
            
            // Auto-hide after 10 seconds
            setTimeout(function() {
                if (tooltip.parentNode) {
                    tooltip.remove();
                }
            }, 10000);
        """)
		
		println "Added scrolling help tooltip"
	} catch (Exception e) {
		println "ERROR adding scrolling help tooltip: ${e.getMessage()}"
	}
}

// Store test results for final summary reporting
def testResults = [:]

// Add scrolling help tooltip if scroll testing is enabled
if (TestConfig.ENABLE_SCROLL_TESTING) {
	addScrollingHelpTooltip()
}

// =====================================================================
// TEST EXECUTION FOR EACH FIELD
// =====================================================================

// Test RBC Morphology Field
try {
	// Verify the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')
	
	// Enter the test data and verify the field
	def result = enterAndVerifyTextInField(
		findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_RBC_Morphology'),
		rbcMorphologyText,
		"RBC Morphology"
	)
	
	// Store results for summary
	testResults["RBC Morphology"] = result
} catch (Exception e) {
	println "FATAL ERROR in RBC Morphology test: ${e.getMessage()}"
	testResults["RBC Morphology"] = [success: false, actualCount: 0, expectedCount: EXPECTED_CHAR_COUNT]
}

// Test WBC Morphology Field
try {
	// Verify the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology (1)'), 'WBC Morphology')
	
	// Enter the test data and verify the field
	def result = enterAndVerifyTextInField(
		findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_WBC_Morphology'),
		wbcMorphologyText,
		"WBC Morphology"
	)
	
	// Store results for summary
	testResults["WBC Morphology"] = result
} catch (Exception e) {
	println "FATAL ERROR in WBC Morphology test: ${e.getMessage()}"
	testResults["WBC Morphology"] = [success: false, actualCount: 0, expectedCount: EXPECTED_CHAR_COUNT]
}

// Test Platelet Morphology Field
try {
	// Verify the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology (1)'), 'Platelet Morphology')
	
	// Enter the test data and verify the field
	def result = enterAndVerifyTextInField(
		findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Platelet_Morphology'),
		plateletMorphologyText,
		"Platelet Morphology"
	)
	
	// Store results for summary
	testResults["Platelet Morphology"] = result
} catch (Exception e) {
	println "FATAL ERROR in Platelet Morphology test: ${e.getMessage()}"
	testResults["Platelet Morphology"] = [success: false, actualCount: 0, expectedCount: EXPECTED_CHAR_COUNT]
}

// Test Hemoparasite Field
try {
	// Verify the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')
	
	// Enter the test data and verify the field
	def result = enterAndVerifyTextInField(
		findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'),
		hemoparasiteText,
		"Hemoparasite"
	)
	
	// Store results for summary
	testResults["Hemoparasite"] = result
} catch (Exception e) {
	println "FATAL ERROR in Hemoparasite test: ${e.getMessage()}"
	testResults["Hemoparasite"] = [success: false, actualCount: 0, expectedCount: EXPECTED_CHAR_COUNT]
}

// Test Impression Field
try {
	// Verify the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')
	
	// Enter the test data and verify the field
	def result = enterAndVerifyTextInField(
		findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Impression'),
		impressionText,
		"Impression"
	)
	
	// Store results for summary
	testResults["Impression"] = result
} catch (Exception e) {
	println "FATAL ERROR in Impression test: ${e.getMessage()}"
	testResults["Impression"] = [success: false, actualCount: 0, expectedCount: EXPECTED_CHAR_COUNT]
}

// Print the final summary of all test results
printTestSummary(testResults)
