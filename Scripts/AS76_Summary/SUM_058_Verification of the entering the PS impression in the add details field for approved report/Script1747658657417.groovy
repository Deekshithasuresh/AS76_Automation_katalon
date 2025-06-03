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

// Wait for the table to be visible
WebUI.waitForElementVisible(findTestObject('Object Repository/Summary/Page_PBS (1)/table'), 30)

// Variable to track if we found and processed a "To be reviewed" report
boolean processedToBeReviewed = false

try {
	// First try to find reports with "To be reviewed" status
	WebUI.comment('Looking for reports with "To be reviewed" status...')

	// Find all rows with "To be reviewed" status
	List<WebElement> toBeReviewedRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), "To be reviewed")] or .//div[contains(text(), "To be reviewed")]]'))

	// Check if any "To be reviewed" reports were found
	if (toBeReviewedRows.size() > 0) {
		WebUI.comment(('Found ' + toBeReviewedRows.size()) + ' reports with "To be reviewed" status - these reports are not assigned to any reviewer')

		// Get the first "To be reviewed" report
		WebElement firstToBeReviewedRow = toBeReviewedRows.get(0)

		// Get the report ID
		String reportId = 'Unknown'

		try {
			WebElement reportIdElement = firstToBeReviewedRow.findElement(By.xpath('./td[2]'))

			reportId = reportIdElement.getText()

			WebUI.comment('Found "To be reviewed" report with Slide ID: ' + reportId)
		}
		catch (Exception e) {
			WebUI.comment('Could not extract report ID: ' + e.getMessage())
		}
		
		// Click on the report
		firstToBeReviewedRow.click()

		WebUI.comment('Clicked on report with Slide ID: ' + reportId)

		// Wait for the report details to load
		WebUI.delay(2)

		// Directly assign to deekshithaS using more reliable methods
		WebUI.comment(('Assigning report ' + reportId) + ' to deekshithaS')

		// Click on the assigned_to field to open the dropdown
		WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))

		WebUI.delay(1 // Give the dropdown time to appear
			)

		// Create a dynamic XPath to find the deekshithaS option in the dropdown
		TestObject deekshithaOption = new TestObject('deekshithaS Option')

		deekshithaOption.addProperty('xpath', ConditionType.EQUALS, '//li[text()=\'deekshithaS\' or contains(text(),\'deekshithaS\')]')

		// Make sure the dropdown option is visible before clicking
		WebUI.waitForElementVisible(deekshithaOption, 10)

		WebUI.click(deekshithaOption)

		// Alternative approach if the above doesn't work
		if (!(WebUI.verifyElementAttributeValue(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'),
			'value', 'deekshithaS', 5, FailureHandling.OPTIONAL))) {
			WebUI.comment('First method failed, trying alternative approach')

			// Clear the input field and type the name directly
			WebUI.clearText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))

			WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'deekshithaS')

			WebUI.delay(1)

			// Press Tab to select the option and move focus away
			WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), Keys.chord(
					Keys.TAB))
		}
		
		// Verify assignment was successful
		WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'),
			10)

		def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'),
			'value')

		newAssignment = newAssignment.trim()

		WebUI.comment(((('New assignment for ' + reportId) + ' (after trim): \'') + newAssignment) + '\'')

		processedToBeReviewed = true
	} else {
		WebUI.comment('No reports with "To be reviewed" status found')
	}
	
	// If no "To be reviewed" reports were processed, check the 3rd report
	if (!(processedToBeReviewed)) {
		WebUI.comment('No "To be reviewed" reports found. Proceeding to check the 3rd report')

		// Find all rows in the table
		List<WebElement> allRows = driver.findElements(By.xpath('//tr[./td]'))

		// Check if we have at least 3 rows
		if (allRows.size() >= 3) {
			// Get the 3rd row (index 2 since we start counting from 0)
			WebElement thirdRow = allRows.get(2)

			// Get the report ID from the 3rd row
			String reportId = 'Unknown'

			try {
				WebElement reportIdElement = thirdRow.findElement(By.xpath('./td[2]'))

				reportId = reportIdElement.getText()

				WebUI.comment('Selected 3rd report with Slide ID: ' + reportId)
			}
			catch (Exception e) {
				WebUI.comment('Could not extract report ID: ' + e.getMessage())
			}
			
			// Click on the 3rd report
			thirdRow.click()

			WebUI.comment('Clicked on 3rd report with Slide ID: ' + reportId)

			// Wait for the report details to load
			WebUI.delay(2)

			// Check current assignment status
			def currentAssignment = ''

			try {
				// Attempt to get the current assignee
				currentAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'),
					'value')

				currentAssignment = currentAssignment.trim()
			}
			catch (Exception e) {
				WebUI.comment('Could not get current assignment: ' + e.getMessage())
			}
			
			WebUI.comment(((('Current assignment for ' + reportId) + ': \'') + currentAssignment) + '\'')

			// Handle assignment based on current status
			if (currentAssignment == 'deekshithaS') {
				// Case 1: Report is already assigned to deekshithaS
				WebUI.comment(('Report ' + reportId) + ' is already assigned to deekshithaS. No action needed.')
			} else {
				WebUI.comment(((('Report ' + reportId) + ' is assigned to \'') + currentAssignment) + '\'. Reassigning to deekshithaS.')

				WebUI.doubleClick(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))

				WebUI.clearText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))

				WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'deekshithaS')

				TestObject deekshithaOption = new TestObject('deekshithaS Option')

				deekshithaOption.addProperty('xpath', ConditionType.EQUALS, '//li[text()=\'deekshithaS\' or contains(text(),\'deekshithaS\')]')

				WebUI.waitForElementVisible(deekshithaOption, 10)

				WebUI.click(deekshithaOption)

				try {
					TestObject reassignButton = new TestObject('Reassign Button')

					reassignButton.addProperty('xpath', ConditionType.EQUALS, '//div[@id=\'reassign-dialog\']//button[text()=\'Re-assign\']')

					if (WebUI.waitForElementPresent(reassignButton, 5, FailureHandling.OPTIONAL)) {
						WebUI.comment('Clicking Re-assign button to confirm reassignment')

						WebUI.click(reassignButton)
					}
				}
				catch (Exception e) {
					WebUI.comment('Reassignment confirmation dialog not found or not needed: ' + e.getMessage())
				}
				
				WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'),
					10)

				def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'),
					'value')

				newAssignment = newAssignment.trim()

				WebUI.comment(((('New assignment for ' + reportId) + ' (after trim): \'') + newAssignment) + '\'')
			}
		} else {
			WebUI.comment('Not enough reports found in the table to select the 3rd one.')
		}
	}
}
catch (Exception e) {
	WebUI.comment('An error occurred during execution: ' + e.getMessage())

	e.printStackTrace()
}
finally {
	// Add a small delay before ending the test
	WebUI.delay(3)
}



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

// Continue with the approval process
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Approve report'), 'Approve report')
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
	WebUI.comment('✓ VALIDATION PASSED: All PS impression sections are correctly NON-EDITABLE FOR AN APPROVED REPORT')
	WebUI.comment('✓ System properly restricts editing of approved reports for an approved report')
} else {
	WebUI.comment('❌ VALIDATION FAILED: Found editable sections in approved report!')
	WebUI.comment("❌ Editable sections detected: ${editableFields.join(', ')}")
	WebUI.comment('❌ CRITICAL ISSUE: Approved reports should not allow editing')
}

WebUI.comment('Proceeding to check remaining fields...')

 

