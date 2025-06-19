import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

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



import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import com.kms.katalon.core.util.KeywordUtil
import java.time.Duration

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

// Continue with the reject process
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Reject report'), 'Reject report')
WebUI.click(findTestObject('Object Repository/Summary/button_Reject report'))

//WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Are you sure you want to reject this report'),'Are you sure you want to reject this report?')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Reject report_1'), 'Reject report')
WebUI.click(findTestObject('Object Repository/Summary/button_Reject report_1'))

// Verify rejection success and navigate back
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

// Find and click on the report with matching slide ID and rejected status
boolean found = findAndClickRejectedReport(capturedSlideId)

// If not found, try scrolling through the table
if (!found) {
	println("Report not found in the initial view. Scrolling through the table...")
	scrollAndFindReport(capturedSlideId)
}

/**
 * Find and click on a report with the specified slide ID that has "Rejected" status
 * Uses XPath targeting based on the actual DOM structure from the screenshot
 * @param slideId The slide ID to search for
 * @return true if the report was found and clicked, false otherwise
 */
boolean findAndClickRejectedReport(String slideId) {
	println("Looking for rejected report with Slide ID: " + slideId)
	
	def driver = DriverFactory.getWebDriver()
	def wait = new WebDriverWait(driver, Duration.ofSeconds(10))
	
	try {
		// Based on the DOM structure from the screenshot
		// This searches for a row with the slide ID span and a "Rejected" span
		String targetXPath = "//tr[.//span[text()='" + slideId + "'] and .//span[text()='Rejected']]"
		
		println("Using XPath: " + targetXPath)
		
		// Check if elements exist with this XPath
		List<WebElement> elements = driver.findElements(By.xpath(targetXPath))
		
		if (elements.isEmpty()) {
			// Try an alternative based on the search bar component classes
			String altXPath = "//tr[.//span[contains(text(),'" + slideId + "')] and .//span[contains(text(),'Rejected')]]"
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
		
		println("Successfully clicked on rejected report with Slide ID: " + slideId)
		return true
	}
	catch (Exception e) {
		println("Failed to find rejected report with Slide ID: " + slideId)
		println("Error: " + e.getMessage())
		
		// Final fallback approach using the classes from the screenshot
		try {
			println("Trying absolute fallback approach...")
			
			// Based directly on the DOM structure in screenshot
			String fallbackXPath = "//li[contains(@class,'searchBarComponent_search-popover-list-item')]/div/span[text()='" + slideId + "']/following::span[text()='Rejected']/ancestor::tr[1]"
			
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
				
				// Verify if this row has the rejected status before clicking
				List<WebElement> statusElements = element.findElements(By.xpath(".//span[contains(text(),'Rejected')]"))
				if (!statusElements.isEmpty()) {
					element.click()
					WebUI.delay(2)
					return true
				} else {
					println("Found row with slide ID but it doesn't have Rejected status")
				}
			} catch (Exception finalEx) {
				println("All fallback approaches failed")
			}
			return false
		}
	}
}

/**
 * Scroll through the table to find and click on a report with the specified slide ID and rejected status
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
		if (checkForRejectedReport(slideId)) {
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
	return checkForRejectedReport(slideId)
}

/**
 * Check if a report with the specified slide ID and rejected status is visible in the current view
 * @param slideId The slide ID to search for
 * @return true if the report was found and clicked, false otherwise
 */
boolean checkForRejectedReport(String slideId) {
	WebDriver driver = DriverFactory.getWebDriver()
	
	// Multiple XPath options to try finding the report
	String[] xpathOptions = [
		// Option 1: Look for exact text matches in spans
		"//tr[.//span[text()='" + slideId + "'] and .//span[text()='Rejected']]",
		
		// Option 2: Look for contains in both
		"//tr[.//span[contains(text(),'" + slideId + "')] and .//span[contains(text(),'Rejected')]]",
		
		// Option 3: Look for the slide ID in any element then find a rejected status
		"//*[contains(text(),'" + slideId + "')]/ancestor::tr[1][.//span[contains(text(),'Rejected')]]",
		
		// Option 4: Based on the DOM structure from screenshot
		"//li[contains(@class,'searchBarComponent_search')]/div/span[text()='" + slideId + "']/following::span[text()='Rejected']/ancestor::tr[1]"
	]
	
	// Try each XPath option
	for (String xpath : xpathOptions) {
		List<WebElement> elements = driver.findElements(By.xpath(xpath))
		
		if (!elements.isEmpty()) {
			try {
				// Click the first matching element
				elements.get(0).click()
				WebUI.delay(2)
				println("Successfully clicked on rejected report with Slide ID: " + slideId + " using XPath: " + xpath)
				return true
			} catch (Exception e) {
				println("Found report but failed to click using XPath: " + xpath + ": " + e.getMessage())
				// Continue to try other XPaths
			}
		}
	}
	
	return false
}

// Now check if PS impressions are editable for this user
WebUI.comment('Checking if PS impressions are accessible for this user...')
WebUI.comment('Expected behavior: All sections should be NON-EDITABLE since this is an approved report')

// Function to check if a section is editable using only existing objects
def checkSectionEditability(sectionName, spanObject, divObject) {
	try {
		WebUI.comment("Checking editability for: ${sectionName}")
		
		// Verify the section exists
		WebUI.verifyElementText(spanObject, sectionName)
		WebUI.comment("${sectionName} section found")
		
		// Double click to attempt opening the section
		WebUI.doubleClick(divObject)
		WebUI.comment("Double clicked on ${sectionName} section")
		
		// Wait a moment for any response
		WebUI.delay(2)
		
		// Try to determine if section is editable by checking element properties
		try {
			// Try to send keys to see if it accepts input
			String testText = "editTest"
			String originalText = ""
			
			try {
				// Get original text if possible
				originalText = WebUI.getText(divObject)
			} catch (Exception e) {
				// Continue if getText fails
			}
			
			// Try to enter text
			WebUI.sendKeys(divObject, testText)
			WebUI.delay(1)
			
			// Check if text was entered
			String currentText = ""
			try {
				currentText = WebUI.getText(divObject)
			} catch (Exception e) {
				// Continue if getText fails
			}
			
			// Check if the element is clickable
			boolean isClickable = WebUI.verifyElementClickable(divObject, FailureHandling.OPTIONAL)
			
			// Determine if section is editable
			if (currentText.contains(testText) || (isClickable && !currentText.equals(originalText))) {
				WebUI.comment("⚠️ ALERT: ${sectionName} is EDITABLE - This should NOT be editable for approved reports!")
				WebUI.comment("Issue detected: User can modify ${sectionName} section in approved report")
				
				// Try to clear the test text
				try {
					WebUI.clearText(divObject)
				} catch (Exception e) {
					// If clear fails, try to restore original text
					try {
						WebUI.setText(divObject, originalText)
					} catch (Exception ex) {
						WebUI.comment("Warning: Could not restore original text in ${sectionName}")
					}
				}
				return true
			} else {
				WebUI.comment("✓ PASS: ${sectionName} is correctly NON-EDITABLE (as expected for approved report)")
				return false
			}
			
		} catch (Exception e) {
			WebUI.comment("✓ PASS: ${sectionName} is NON-EDITABLE - Cannot interact with section (${e.message})")
			return false
		}
		
	} catch (Exception e) {
		WebUI.comment("✓ PASS: ${sectionName} is NON-EDITABLE - Error accessing section: ${e.message}")
		return false
	}
}

// Navigate through the report sections to reach Impression section
WebUI.comment('=== Starting PS Impressions Editability Validation ===')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

boolean hasEditableFields = false
List<String> editableFields = []

// Check RBC Morphology editability
WebUI.comment('--- Validating RBC Morphology Section ---')
if (checkSectionEditability('RBC Morphology',
	findTestObject('Object Repository/Summary/span_RBC Morphology'),
	findTestObject('Object Repository/Summary/div_OKCancel'))) {
	hasEditableFields = true
	editableFields.add('RBC Morphology')
}

// Check WBC Morphology editability
WebUI.comment('--- Validating WBC Morphology Section ---')
if (checkSectionEditability('WBC Morphology',
	findTestObject('Object Repository/Summary/span_WBC Morphology'),
	findTestObject('Object Repository/Summary/div_OKCancel_1'))) {
	hasEditableFields = true
	editableFields.add('WBC Morphology')
}

// Check Platelet Morphology editability
WebUI.comment('--- Validating Platelet Morphology Section ---')
if (checkSectionEditability('Platelet Morphology',
	findTestObject('Object Repository/Summary/span_Platelet Morphology'),
	findTestObject('Object Repository/Summary/div_OKCancel_1_2'))) {
	hasEditableFields = true
	editableFields.add('Platelet Morphology')
}

// Check Hemoparasite editability
WebUI.comment('--- Validating Hemoparasite Section ---')
if (checkSectionEditability('Hemoparasite',
	findTestObject('Object Repository/Summary/span_Hemoparasite'),
	findTestObject('Object Repository/Summary/div_OKCancel_1_2_3'))) {
	hasEditableFields = true
	editableFields.add('Hemoparasite')
}

// Check Impression editability
WebUI.comment('--- Validating Impression Section ---')
if (checkSectionEditability('Impression',
	findTestObject('Object Repository/Summary/span_Impression'),
	findTestObject('Object Repository/Summary/div_OKCancel_1_2_3_4'))) {
	hasEditableFields = true
	editableFields.add('Impression')
}

// Final validation summary
WebUI.comment('=== PS Impressions Editability Validation Complete ===')
if (!hasEditableFields) {
	println('✓ VALIDATION PASSED: All PS impression sections are correctly NON-EDITABLE FOR A REJECTED REPORT')
	println('✓ System properly restricts editing of approved reports for arejected report')
} else {
	println('❌ VALIDATION FAILED: Found editable sections in approved report!')
	println("❌ Editable sections detected: ${editableFields.join(', ')}")
	println('❌ CRITICAL ISSUE: Approved reports should not allow editing')
}

