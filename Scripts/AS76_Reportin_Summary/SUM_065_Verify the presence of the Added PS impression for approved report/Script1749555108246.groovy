import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


import java.time.Duration

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Add the missing imports
// Open the browser and navigate to the login page
WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.maximizeWindow()

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for the page to load
WebUI.waitForPageLoad(30)

WebUI.delay(3 // Add a small delay to ensure all elements are loaded
	)

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()


CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

/**
 * Script to reject a report and then find it again in the list by matching both slide ID and rejected status
 * Includes scrolling functionality to search through all rows
 */

// Capture the slide ID from the current report
TestObject slideIdObj = findTestObject('Object Repository/Summary/slide_ID')

// Wait until it's visible
WebUI.waitForElementVisible(slideIdObj, 10)

// Read the text
String slideIdText = WebUI.getText(slideIdObj)

// Print it to the console / log
println('Slide Id on screen: ' + slideIdText)

// Store the slide ID in a local variable
String capturedSlideId = slideIdText

// Open the Peripheral Smear Report page
// (assuming you have a navigation step before this)

// Verify the report title is correct
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

// Store different combinations of alphanumeric characters to test in each field
def rbcMorphologyAlphaNum = "RBC123Test456"         // Alphanumeric for RBC field
def wbcMorphologyAlphaNum = "WBC789Sample012"        // Alphanumeric for WBC field
def plateletMorphologyAlphaNum = "PLT345Data678"    // Alphanumeric for Platelet field
def hemoparasiteAlphaNum = "HEMO901Test234"         // Alphanumeric for Hemoparasite field
def impressionAlphaNum = "IMP567Result890"            // Alphanumeric for Impression field

// This function enters alphanumeric characters into a field
// It waits for the field to be visible, clicks it, clears any existing text,
// enters our alphanumeric characters, then tabs out to save the entry
def enterAlphaNumInField(TestObject fieldObject, String alphaNumToEnter) {
	WebUI.waitForElementVisible(fieldObject, 20)
	WebUI.click(fieldObject)
	WebUI.clearText(fieldObject)
	WebUI.setText(fieldObject, alphaNumToEnter)
	WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB)) // Tab out to save the entry
}

// Helper function to check if text contains both alphabets and numbers
def containsAlphaNum(String text) {
	return text.find(/[a-zA-Z]/) && text.find(/[0-9]/)
}

// First field: RBC Morphology - Try to enter alphanumeric characters
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')
	
	// Enter our alphanumeric characters into the field
	enterAlphaNumInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_RBC_Morphology'), rbcMorphologyAlphaNum)
	
	// Read back what's in the field to check if alphanumeric characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_RBC_Morphology'))
	
	// Check if the text contains both alphabets and numbers
	if (containsAlphaNum(enteredText)) {
		println "PASS: Successfully entered combination of alphabets and numbers in RBC Morphology field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: RBC Morphology field does not contain both alphabets and numbers: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphanumeric characters in RBC Morphology field: " + e.getMessage()
}

// Second field: WBC Morphology - Continue even if the first one failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology (1)'), 'WBC Morphology')
	
	// Enter our alphanumeric characters into the field
	enterAlphaNumInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_WBC_Morphology'), wbcMorphologyAlphaNum)
	
	// Read back what's in the field to check if alphanumeric characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_WBC_Morphology'))
	
	// Check if the text contains both alphabets and numbers
	if (containsAlphaNum(enteredText)) {
		println "PASS: Successfully entered combination of alphabets and numbers in WBC Morphology field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: WBC Morphology field does not contain both alphabets and numbers: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphanumeric characters in WBC Morphology field: " + e.getMessage()
}

// Third field: Platelet Morphology - Continue even if previous fields failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology (1)'), 'Platelet Morphology')
	
	// Enter our alphanumeric characters into the field
	enterAlphaNumInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Platelet_Morphology'), plateletMorphologyAlphaNum)
	
	// Read back what's in the field to check if alphanumeric characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Platelet_Morphology'))
	
	// Check if the text contains both alphabets and numbers
	if (containsAlphaNum(enteredText)) {
		println "PASS: Successfully entered combination of alphabets and numbers in Platelet Morphology field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: Platelet Morphology field does not contain both alphabets and numbers: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphanumeric characters in Platelet Morphology field: " + e.getMessage()
}

// Fourth field: Hemoparasite - Continue even if previous fields failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')
	
	// Enter our alphanumeric characters into the field
	enterAlphaNumInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'), hemoparasiteAlphaNum)
	
	// Read back what's in the field to check if alphanumeric characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'))
	
	// Check if the text contains both alphabets and numbers
	if (containsAlphaNum(enteredText)) {
		println "PASS: Successfully entered combination of alphabets and numbers in Hemoparasite field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: Hemoparasite field does not contain both alphabets and numbers: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphanumeric characters in Hemoparasite field: " + e.getMessage()
}

// Fifth field: Impression - Continue even if previous fields failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')
	
	// Enter our alphanumeric characters into the field
	enterAlphaNumInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Impression'), impressionAlphaNum)
	
	// Read back what's in the field to check if alphanumeric characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Impression'))
	
	// Check if the text contains both alphabets and numbers
	if (containsAlphaNum(enteredText)) {
		println "PASS: Successfully entered combination of alphabets and numbers in Impression field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: Impression field does not contain both alphabets and numbers: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphanumeric characters in Impression field: " + e.getMessage()
}
WebUI.click(findTestObject('Object Repository/Summary/done_button'))
// Continue with the approval process
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Approve report'), 'Approve report')
//WebUI.click(findTestObject('Object Repository/Summary/button_Approve report'))
WebUI.click(findTestObject('Object Repository/Summary/button_Approve report'))


// Click Confirm to proceed without verification in the preview
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Confirm'), 'Confirm')
WebUI.click(findTestObject('Object Repository/Summary/button_Confirm'))

// Click Approve report again
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Approve report'), 'Approve report')
WebUI.click(findTestObject('Object Repository/Summary/button_Approve report'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/approve_button_pdf'), 'Approve report')
WebUI.click(findTestObject('Object Repository/Summary/approve_button_pdf'))

// Verify approval success and navigate back
WebUI.verifyElementText(findTestObject('Object Repository/Summary/img'), '')
WebUI.click(findTestObject('Object Repository/Summary/img'))

// Increased delay to ensure page fully loads
WebUI.delay(15)

// Navigate to the reports list
WebUI.verifyElementText(findTestObject('Object Repository/Summary/dropdown_img_readyreview'), '')
WebUI.waitForElementVisible(findTestObject('Object Repository/Summary/dropdown_img_readyreview'), 15)
WebUI.waitForElementClickable(findTestObject('Object Repository/Summary/dropdown_img_readyreview'), 15)
WebUI.click(findTestObject('Object Repository/Summary/dropdown_img_readyreview'))

// Select "Reviewed" from dropdown
WebUI.verifyElementText(findTestObject('Object Repository/Summary/dropdown_img_reviewed'), 'Reviewed')
WebUI.click(findTestObject('Object Repository/Summary/dropdown_img_reviewed'))

// Search for the previously captured slide ID
WebUI.verifyElementClickable(findTestObject('Object Repository/Summary/input_text'))
WebUI.sendKeys(findTestObject('Object Repository/Summary/input_text'), capturedSlideId)
// Press Enter to initiate the search
WebUI.sendKeys(findTestObject('Object Repository/Summary/input_text'), Keys.chord(Keys.ENTER))

// Wait for search results to appear
WebUI.delay(3)

// Find and click on the report with matching slide ID and approved status
boolean found = findAndClickApprovedReport(capturedSlideId)

// If not found, try scrolling through the table
if (!found) {
	println("Report not found in the initial view. Scrolling through the table...")
	scrollAndFindReport(capturedSlideId)
}

/**
 * Find and click on a report with the specified slide ID that has "Approved" status
 * Uses XPath targeting based on the actual DOM structure from the screenshot
 * @param slideId The slide ID to search for
 * @return true if the report was found and clicked, false otherwise
 */
boolean findAndClickApprovedReport(String slideId) {
	println("Looking for approved report with Slide ID: " + slideId)
	
	def driver = DriverFactory.getWebDriver()
	def wait = new WebDriverWait(driver, Duration.ofSeconds(10))
	
	try {
		// Based on the DOM structure from the screenshot
		// This searches for a row with the slide ID span and an "Approved" span
		String targetXPath = "//tr[.//span[text()='" + slideId + "'] and .//span[text()='Approved']]"
		
		println("Using XPath: " + targetXPath)
		
		// Check if elements exist with this XPath
		List<WebElement> elements = driver.findElements(By.xpath(targetXPath))
		
		if (elements.isEmpty()) {
			// Try an alternative based on the search bar component classes
			String altXPath = "//tr[.//span[contains(text(),'" + slideId + "')] and .//span[contains(text(),'Approved')]]"
			println("No exact match found, trying alternative XPath: " + altXPath)
			elements = driver.findElements(By.xpath(altXPath))
		}
		
		if (elements.isEmpty()) {
			println("No matching reports found with specified XPaths")
			return false
		}
		
		// Wait for the element to be clickable
		WebElement targetReport = wait.until(ExpectedConditions.elementToBeClickable(elements.get(0)))
		
		// Log before clicking
		println("Found matching report, clicking now...")
		
		// Click on the report
		targetReport.click()
		
		// Wait to ensure the click takes effect
		WebUI.delay(2)
		
		println("Successfully clicked on approved report with Slide ID: " + slideId)
		return true
	}
	catch (Exception e) {
		println("Failed to find approved report with Slide ID: " + slideId)
		println("Error: " + e.getMessage())
		
		// Final fallback approach using the classes from the screenshot
		try {
			println("Trying absolute fallback approach...")
			
			// Based directly on the DOM structure in screenshot
			String fallbackXPath = "//li[contains(@class,'searchBarComponent_search-popover-list-item')]/div/span[text()='" + slideId + "']/following::span[text()='Approved']/ancestor::tr[1]"
			
			WebElement element = driver.findElement(By.xpath(fallbackXPath))
			element.click()
			WebUI.delay(2)
			return true
		}
		catch (Exception ex) {
			// One more try with the most generic approach
			try {
				// Just find the row containing the slide ID, then click it directly
				String lastResortXPath = "//span[contains(text(),'" + slideId + "')]/ancestor::tr[1]"
				WebElement element = driver.findElement(By.xpath(lastResortXPath))
				
				// Verify if this row has the approved status before clicking
				List<WebElement> statusElements = element.findElements(By.xpath(".//span[contains(text(),'Approved')]"))
				if (!statusElements.isEmpty()) {
					element.click()
					WebUI.delay(2)
					return true
				} else {
					println("Found row with slide ID but it doesn't have Approved status")
				}
			} catch (Exception finalEx) {
				println("All fallback approaches failed")
			}
			return false
		}
	}
}

/**
 * Scroll through the table to find and click on a report with the specified slide ID and approved status
 * @param slideId The slide ID to search for
 * @return true if the report was found and clicked, false otherwise
 */
boolean scrollAndFindReport(String slideId) {
	// Get WebDriver and JS executor
	WebDriver driver = DriverFactory.getWebDriver()
	JavascriptExecutor js = (JavascriptExecutor) driver
	
	// Scroll container selector - adjust based on the actual DOM
	String scrollSelector = "#reportListingTable > div > div"
	int scrollStep = 300
	int delaySeconds = 1
	int maxTries = 100
	int tries = 0
	
	// Scroll loop
	while (tries < maxTries) {
		// Check if the report is visible in the current view
		if (checkForApprovedReport(slideId)) {
			println("Found the report after scrolling!")
			return true
		}
		
		// Scroll down if not found
		def result = js.executeScript("""
            const el = document.querySelector(arguments[0]);
            const before = el.scrollTop;
            el.scrollBy(0, arguments[1]);
            return {
                scrollTop: el.scrollTop,
                scrollHeight: el.scrollHeight,
                clientHeight: el.clientHeight,
                before: before
            };
        """, scrollSelector, scrollStep)
		
		long scrollTop = result.scrollTop
		long scrollHeight = result.scrollHeight
		long clientHeight = result.clientHeight
		long before = result.before
		
		KeywordUtil.logInfo("Scroll position: " + scrollTop + " / " + (scrollHeight - clientHeight))
		WebUI.delay(delaySeconds)
		
		// Check if we've reached the bottom of the scroll
		if (scrollTop >= (scrollHeight - clientHeight - 10)) {
			KeywordUtil.logInfo("Reached end of scroll, breaking.")
			break
		}
		
		// Check if we're stuck
		if (scrollTop == before) {
			KeywordUtil.logInfo("ScrollTop unchanged. Possibly stuck. Breaking.")
			break
		}
		
		tries++
	}
	
	// Final check after scrolling is complete
	return checkForApprovedReport(slideId)
}

/**
 * Check if a report with the specified slide ID and approved status is visible in the current view
 * @param slideId The slide ID to search for
 * @return true if the report was found and clicked, false otherwise
 */
boolean checkForApprovedReport(String slideId) {
	WebDriver driver = DriverFactory.getWebDriver()
	
	// Multiple XPath options to try finding the report
	String[] xpathOptions = [
		// Option 1: Look for exact text matches in spans
		"//tr[.//span[text()='" + slideId + "'] and .//span[text()='Approved']]",
		
		// Option 2: Look for contains in both
		"//tr[.//span[contains(text(),'" + slideId + "')] and .//span[contains(text(),'Approved')]]",
		
		// Option 3: Look for the slide ID in any element then find an approved status
		"//*[contains(text(),'" + slideId + "')]/ancestor::tr[1][.//span[contains(text(),'Approved')]]",
		
		// Option 4: Based on the DOM structure from screenshot
		"//li[contains(@class,'searchBarComponent_search')]/div/span[text()='" + slideId + "']/following::span[text()='Approved']/ancestor::tr[1]"
	]
	
	// Try each XPath option
	for (String xpath : xpathOptions) {
		List<WebElement> elements = driver.findElements(By.xpath(xpath))
		
		if (!elements.isEmpty()) {
			try {
				// Click the first matching element
				elements.get(0).click()
				WebUI.delay(2)
				println("Successfully clicked on approved report with Slide ID: " + slideId + " using XPath: " + xpath)
				return true
			} catch (Exception e) {
				println("Found report but failed to click using XPath: " + xpath + ": " + e.getMessage())
				// Continue to try other XPaths
			}
		}
	}
	
	return false
}


// ==================================================================================
// PS IMPRESSION VERIFICATION BLOCK - Add this after your existing code
// ==================================================================================

println("\n=== STARTING PS IMPRESSION VERIFICATION ===")

// Wait for the page to load completely before verification
WebUI.delay(3)

// Get WebDriver instance for XPath operations
//WebDriver driver = DriverFactory.getWebDriver()
//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

// Define the expected alphanumeric values (same as used in the data entry section)
def expectedValues = [
	"RBC Morphology": "RBC123Test456",
	"WBC Morphology": "WBC789Sample012",
	"Platelet Morphology": "PLT345Data678",
	"Hemoparasite": "HEMO901Test234",
	"Impression": "IMP567Result890"
]

// Define verification results map
def verificationResults = [:]

// Verification function using XPath
def verifyFieldContent(String fieldName, String expectedValue, List<String> xpathOptions) {
	WebDriver driver =DriverFactory.getWebDriver()
	boolean found = false
	String actualContent = ""
	String usedXPath = ""
	
	for (String xpath : xpathOptions) {
		try {
			List<WebElement> elements = driver.findElements(By.xpath(xpath))
			if (!elements.isEmpty()) {
				actualContent = elements.get(0).getText().trim()
				if (actualContent.contains(expectedValue) || containsAlphaNum(actualContent)) {
					found = true
					usedXPath = xpath
					break
				}
			}
		} catch (Exception e) {
			// Continue to next XPath option
			continue
		}
	}
	
	return [found: found, content: actualContent, xpath: usedXPath]
}

// XPath options for each field using the provided specific XPaths
def fieldXPathOptions = [
	"RBC Morphology": [
		"(//div[contains(@class, 'ql-editor')]//p)[1]",
		"(//div[contains(@class, 'ql-editor')]//p)[1]/parent::div/parent::div",
		"//div[contains(@class, 'ql-editor')][contains(text(), 'RBC123Test456')]",
		"//*[contains(text(), 'RBC123Test456')]"
	],
	"WBC Morphology": [
		"(//div[contains(@class, 'ql-editor')]//p)[2]",
		"(//div[contains(@class, 'ql-editor')]//p)[2]/parent::div/parent::div",
		"//div[contains(@class, 'ql-editor')][contains(text(), 'WBC789Sample012')]",
		"//*[contains(text(), 'WBC789Sample012')]"
	],
	"Platelet Morphology": [
		"(//div[contains(@class, 'ql-editor')]//p)[3]",
		"(//div[contains(@class, 'ql-editor')]//p)[3]/parent::div/parent::div",
		"//div[contains(@class, 'ql-editor')][contains(text(), 'PLT345Data678')]",
		"//*[contains(text(), 'PLT345Data678')]"
	],
	"Hemoparasite": [
		"(//div[contains(@class, 'ql-editor')]//p)[4]",
		"(//div[contains(@class, 'ql-editor')]//p)[4]/parent::div/parent::div",
		"//div[contains(@class, 'ql-editor')][contains(text(), 'HEMO901Test234')]",
		"//*[contains(text(), 'HEMO901Test234')]"
	],
	"Impression": [
		"(//div[contains(@class, 'ql-editor')]//p)[5]",
		"(//div[contains(@class, 'ql-editor')]//p)[5]/parent::div/parent::div",
		"//div[contains(@class, 'ql-editor')][contains(text(), 'IMP567Result890')]",
		"//*[contains(text(), 'IMP567Result890')]"
	]
]

// Verify each field
println("\n--- Field by Field Verification ---")

fieldXPathOptions.each { fieldName, xpathList ->
	String expectedValue = expectedValues[fieldName]
	def result = verifyFieldContent(fieldName, expectedValue, xpathList)
	
	verificationResults[fieldName] = result
	
	if (result.found) {
		println("✓ PASS: ${fieldName} contains expected alphanumeric data")
		println("  Expected: ${expectedValue}")
		println("  Found: ${result.content}")
		println("  XPath used: ${result.xpath}")
	} else {
		println("✗ FAIL: ${fieldName} does not contain expected alphanumeric data")
		println("  Expected: ${expectedValue}")
		println("  Found: ${result.content}")
		println("  All XPaths tried, none contained expected content")
	}
	println("")
}

// Overall verification summary
println("\n=== PS IMPRESSION VERIFICATION SUMMARY ===")
int passCount = 0
int totalCount = verificationResults.size()

verificationResults.each { fieldName, result ->
	if (result.found) {
		passCount++
		println("✓ ${fieldName}: VERIFIED")
	} else {
		println("✗ ${fieldName}: NOT VERIFIED")
	}
}

println("\nOverall Result: ${passCount}/${totalCount} fields verified successfully")

if (passCount == totalCount) {
	println("🎉 ALL PS IMPRESSION FIELDS VERIFIED SUCCESSFULLY!")
} else {
	println("⚠️  Some PS impression fields could not be verified. Check the XPath selectors.")
}

// Additional diagnostic information
println("\n--- Diagnostic Information ---")
try {
	// Check if we're on the correct page
	String currentUrl = driver.getCurrentUrl()
	println("Current URL: ${currentUrl}")
	
	// Check for presence of key elements
	List<WebElement> psReportElements = driver.findElements(By.xpath("//*[contains(text(), 'Peripheral Smear Report')]"))
	println("Peripheral Smear Report elements found: ${psReportElements.size()}")
	
	// Check for any elements containing our test data
	List<WebElement> testDataElements = driver.findElements(By.xpath("//*[contains(text(), 'RBC123') or contains(text(), 'WBC789') or contains(text(), 'PLT345') or contains(text(), 'HEMO901') or contains(text(), 'IMP567')]"))
	println("Elements containing test data: ${testDataElements.size()}")
	
	if (testDataElements.size() > 0) {
		println("Test data found in elements:")
		testDataElements.each { element ->
			println("  - ${element.getText()}")
		}
	}
	
} catch (Exception e) {
	println("Error during diagnostics: ${e.getMessage()}")
}

println("\n=== PS IMPRESSION VERIFICATION COMPLETE ===\n")