// Define test conditions to be tested
boolean wbcLessThan200WithWarningFound = false
boolean wbcMoreThan200WithoutWarningFound = false
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

// Import missing ConditionType
import com.kms.katalon.core.testobject.ConditionType

// Login to the system
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.doubleClick(findTestObject('Object Repository/Summary/input_username_loginId'))
WebUI.setText(findTestObject('Object Repository/Summary/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for login to complete and page to load
WebUI.delay(5)
println("Waiting for page to load completely...")

// Define the main reports table object using multiple possible locators
TestObject reportsTableObject = new TestObject('Reports_Table')
reportsTableObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'MuiTableContainer-root')]")

// Alternative table locators in case the first one fails
TestObject altTableObject1 = new TestObject('Alt_Table_1')
altTableObject1.addProperty('css', ConditionType.EQUALS, "table.MuiTable-root")

TestObject altTableObject2 = new TestObject('Alt_Table_2')
altTableObject2.addProperty('xpath', ConditionType.EQUALS, "//table")

// Wait for the table to be present after login
boolean tableFound = false
println("Looking for reports table...")

if (WebUI.waitForElementPresent(reportsTableObject, 10, FailureHandling.OPTIONAL)) {
	println("Found table using primary locator")
	tableFound = true
} else if (WebUI.waitForElementPresent(altTableObject1, 5, FailureHandling.OPTIONAL)) {
	println("Found table using alternative CSS locator")
	tableFound = true
} else if (WebUI.waitForElementPresent(altTableObject2, 5, FailureHandling.OPTIONAL)) {
	println("Found table using generic table locator")
	tableFound = true
}

if (!tableFound) {
	println("Could not find the reports table. Taking screenshot for debugging...")
	WebUI.takeScreenshot('debug_table_not_found.png')
	// Try taking screenshot of entire page
	TestObject bodyObject = new TestObject('Body')
	bodyObject.addProperty('css', ConditionType.EQUALS, "body")
	if (WebUI.waitForElementPresent(bodyObject, 5, FailureHandling.OPTIONAL)) {
		println("Page loaded, but table not found. Current URL: " + WebUI.getUrl())
	}
}

// Define improved row locators
println("Setting up row locators...")

// Attempt to find any row in the table to verify table has content
TestObject anyRowObject = new TestObject('Any Row')
anyRowObject.addProperty('xpath', ConditionType.EQUALS, "//table/tbody/tr")

// Also try alternative row locators
TestObject altRowObject = new TestObject('Alt Row')
altRowObject.addProperty('css', ConditionType.EQUALS, "tbody tr")

boolean rowsFound = false
if (WebUI.waitForElementPresent(anyRowObject, 10, FailureHandling.OPTIONAL)) {
	println("Found table rows using primary xpath locator")
	rowsFound = true
} else if (WebUI.waitForElementPresent(altRowObject, 5, FailureHandling.OPTIONAL)) {
	println("Found table rows using alternative CSS locator")
	rowsFound = true
}

if (!rowsFound) {
	println("No rows found in the table. Taking screenshot...")
	WebUI.takeScreenshot('debug_no_rows_found.png')
	println("Current page title: " + WebUI.getWindowTitle())
}

// Main loop: Continue checking reports until both conditions are satisfied or max reports reached
int reportNumber = 0
int maxReports = 20 // Adjust this number as needed

while ((!wbcLessThan200WithWarningFound || !wbcMoreThan200WithoutWarningFound) && (reportNumber < maxReports)) {
	reportNumber++ // Increment report counter
	println("Attempting to check report #" + reportNumber) // Log which report number is being checked

	// Create multiple test objects for the current row with different locator strategies
	TestObject rowObject = new TestObject('Row ' + reportNumber)
	rowObject.addProperty('xpath', ConditionType.EQUALS, "//table/tbody/tr[" + reportNumber + "]")
	
	TestObject rowObjectAlt1 = new TestObject('Row Alt 1 ' + reportNumber)
	rowObjectAlt1.addProperty('css', ConditionType.EQUALS, "tbody tr:nth-child(" + reportNumber + ")")
	
	TestObject rowObjectAlt2 = new TestObject('Row Alt 2 ' + reportNumber)
	rowObjectAlt2.addProperty('xpath', ConditionType.EQUALS, "(//table/tbody/tr)[" + reportNumber + "]")

	// Check if row exists using multiple strategies
	boolean rowFound = false
	TestObject foundRowObject = null
	
	// Try first locator
	if (WebUI.waitForElementPresent(rowObject, 5, FailureHandling.OPTIONAL)) {
		rowFound = true
		foundRowObject = rowObject
		println("Found row #" + reportNumber + " using primary xpath locator")
	}
	// Try second locator if first fails
	else if (WebUI.waitForElementPresent(rowObjectAlt1, 3, FailureHandling.OPTIONAL)) {
		rowFound = true
		foundRowObject = rowObjectAlt1
		println("Found row #" + reportNumber + " using CSS nth-child locator")
	}
	// Try third locator if first two fail
	else if (WebUI.waitForElementPresent(rowObjectAlt2, 3, FailureHandling.OPTIONAL)) {
		rowFound = true
		foundRowObject = rowObjectAlt2
		println("Found row #" + reportNumber + " using alternative xpath locator")
	}
	
	// If no row is found with any strategy, we've reached the end of available reports
	if (!rowFound) {
		println("No more reports available after #" + (reportNumber - 1)) // Log that no more reports are available
		break // Exit the loop if no more reports are found
	}

	// Debug info about the row
	WebUI.takeScreenshot('report_row_' + reportNumber + '.png')
	
	// Try to get slide ID (using the found row object as reference)
	String slideId = "Unknown"
	try {
		TestObject slideIdObject = new TestObject('Slide ID ' + reportNumber)
		slideIdObject.addProperty('xpath', ConditionType.EQUALS, "(//table/tbody/tr)[" + reportNumber + "]/td[3]")
		
		if (WebUI.waitForElementPresent(slideIdObject, 5, FailureHandling.OPTIONAL)) {
			slideId = WebUI.getText(slideIdObject) // Extract slide ID text from the table cell
			println("Found Slide ID: " + slideId + " for row " + reportNumber)
		} else {
			println("Could not retrieve Slide ID for row " + reportNumber + ", continuing anyway")
		}
	} catch (Exception e) {
		println("Error getting slide ID: " + e.getMessage())
	}

	// Click on the row to open the slide details
	try {
		println("Attempting to click on row " + reportNumber)
		WebUI.waitForElementClickable(foundRowObject, 10)
		WebUI.click(foundRowObject)
		println("Successfully clicked on row " + reportNumber)
		WebUI.delay(5) // Wait for slide data to load completely
		
		// Take screenshot after clicking to verify we're on the details page
		WebUI.takeScreenshot('after_row_click_' + reportNumber + '.png')
		
		// Define the WBC span element with multiple strategies
		TestObject wbcSpanObject = new TestObject('WBC Span')
		wbcSpanObject.addProperty('xpath', ConditionType.EQUALS, "//span[text()='WBC']")
		
		TestObject wbcSpanAlt = new TestObject('WBC Span Alt')
		wbcSpanAlt.addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'WBC')]")
		
		// Wait for any WBC section to be available
		boolean wbcFound = false
		TestObject foundWbcObject = null
		
		if (WebUI.waitForElementPresent(wbcSpanObject, 10, FailureHandling.OPTIONAL)) {
			wbcFound = true
			foundWbcObject = wbcSpanObject
			println("Found WBC section using exact text match")
		} else if (WebUI.waitForElementPresent(wbcSpanAlt, 5, FailureHandling.OPTIONAL)) {
			wbcFound = true
			foundWbcObject = wbcSpanAlt
			println("Found WBC section using partial text match")
		}
		
		if (!wbcFound) {
			println("Could not find WBC section for this report. Taking screenshot and continuing...")
			WebUI.takeScreenshot('wbc_section_not_found_' + reportNumber + '.png')
			throw new Exception("WBC section not found")
		}
		
		// Click on WBC section
		WebUI.waitForElementClickable(foundWbcObject, 10)
		WebUI.click(foundWbcObject)
		println("Successfully clicked on WBC section")
		WebUI.delay(2) // Wait for WBC section to expand
		
		// Define the WBC total count element with multiple locator strategies
		TestObject wbcTotalObject = new TestObject('WBC Total Count')
		wbcTotalObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class, 'wbc-total-count')]")
		
		TestObject wbcTotalAlt1 = new TestObject('WBC Total Alt 1')
		wbcTotalAlt1.addProperty('xpath', ConditionType.EQUALS, "//div[contains(text(), 'Total') and contains(@class, 'wbc')]")
		
		TestObject wbcTotalAlt2 = new TestObject('WBC Total Alt 2')
		wbcTotalAlt2.addProperty('xpath', ConditionType.EQUALS, "//div[contains(text(), 'WBC') and contains(text(), 'count')]")
		
		// Get WBC count using multiple strategies
		String actualWbcTotal = null
		
		if (WebUI.waitForElementVisible(wbcTotalObject, 5, FailureHandling.OPTIONAL)) {
			actualWbcTotal = WebUI.getText(wbcTotalObject)
			println("Found WBC count using primary locator: " + actualWbcTotal)
		} else if (WebUI.waitForElementVisible(wbcTotalAlt1, 3, FailureHandling.OPTIONAL)) {
			actualWbcTotal = WebUI.getText(wbcTotalAlt1)
			println("Found WBC count using alternative locator 1: " + actualWbcTotal)
		} else if (WebUI.waitForElementVisible(wbcTotalAlt2, 3, FailureHandling.OPTIONAL)) {
			actualWbcTotal = WebUI.getText(wbcTotalAlt2)
			println("Found WBC count using alternative locator 2: " + actualWbcTotal)
		}
		
		if (actualWbcTotal == null) {
			println("Could not find WBC count display. Taking screenshot and continuing...")
			WebUI.takeScreenshot('wbc_count_not_found_' + reportNumber + '.png')
			throw new Exception("WBC count not found")
		}
		
		// Extract numeric value from WBC count
		println("WBC Total from UI: " + actualWbcTotal)
		String numericValue = actualWbcTotal.replaceAll("[^0-9]", "")
		
		if (numericValue.isEmpty()) {
			println("Could not extract numeric value from: " + actualWbcTotal)
			throw new Exception("Failed to extract numeric WBC count")
		}
		
		int wbcCount = Integer.parseInt(numericValue)
		println("Extracted WBC count: " + wbcCount)
		
		// Define the warning message element with multiple strategies
		TestObject warningMsgObject = new TestObject('WBC Warning Message')
		warningMsgObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(text(), 'Number of WBCs counted is <200')]")
		
		TestObject warningMsgAlt1 = new TestObject('WBC Warning Alt 1')
		warningMsgAlt1.addProperty('xpath', ConditionType.EQUALS, "//div[contains(text(), 'WBC') and contains(text(), '<200')]")
		
		TestObject warningMsgAlt2 = new TestObject('WBC Warning Alt 2')
		warningMsgAlt2.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class, 'warning') or contains(@class, 'alert')]")
		
		// Initialize variable to track if warning was found
		boolean warningFound = false
		String warningMsg = null
		
		// Check for warning message using multiple strategies
		if (WebUI.waitForElementPresent(warningMsgObject, 3, FailureHandling.OPTIONAL)) {
			warningFound = true
			warningMsg = WebUI.getText(warningMsgObject)
			println("Warning message found with primary locator: " + warningMsg)
		} else if (WebUI.waitForElementPresent(warningMsgAlt1, 3, FailureHandling.OPTIONAL)) {
			warningFound = true
			warningMsg = WebUI.getText(warningMsgAlt1)
			println("Warning message found with alternative locator 1: " + warningMsg)
		} else if (WebUI.waitForElementPresent(warningMsgAlt2, 3, FailureHandling.OPTIONAL)) {
			warningFound = true
			warningMsg = WebUI.getText(warningMsgAlt2)
			println("Warning message found with alternative locator 2: " + warningMsg)
		} else {
			warningFound = false
			println("No warning message found")
		}
		
		// Take screenshot for verification
		WebUI.takeScreenshot('wbc_verification_' + reportNumber + '_' + wbcCount + '.png')
		
		// Check the test conditions based on WBC count and warning presence
		if (wbcCount < 200) {
			if (warningFound && (warningMsg != null) &&
				(warningMsg.toLowerCase().contains("wbc") || warningMsg.toLowerCase().contains("<200"))) {
				println("PASS: Found report with WBC count < 200 (" + wbcCount + ") and warning message: Slide ID " + slideId)
				wbcLessThan200WithWarningFound = true
			} else {
				println("FAIL: Report has WBC count < 200 (" + wbcCount + ") but warning message is missing or incorrect")
			}
		} else {
			if (!warningFound) {
				println("PASS: Found report with WBC count >= 200 (" + wbcCount + ") and NO warning message: Slide ID " + slideId)
				wbcMoreThan200WithoutWarningFound = true
			} else {
				println("FAIL: Report has WBC count >= 200 (" + wbcCount + ") but warning message is incorrectly present: " + warningMsg)
			}
		}
		
	} catch (Exception e) {
		println("Error processing report " + reportNumber + ": " + e.getMessage())
		WebUI.takeScreenshot('error_processing_report_' + reportNumber + '.png')
	} finally {
		// Navigate back to the reports list using multiple strategies
		try {
			println("Attempting to navigate back to reports list")
			
			// Try to find back button using multiple locators
			TestObject backButton = new TestObject('Back Button')
			backButton.addProperty('xpath', ConditionType.EQUALS, "//button[contains(@aria-label, 'back') or contains(@class, 'back-button') or contains(text(), 'Back')]")
			
			TestObject backButtonAlt1 = new TestObject('Back Button Alt 1')
			backButtonAlt1.addProperty('css', ConditionType.EQUALS, "button.back-button, button[aria-label='back']")
			
			TestObject backButtonAlt2 = new TestObject('Back Button Alt 2')
			backButtonAlt2.addProperty('xpath', ConditionType.EQUALS, "//button[contains(@class, 'MuiButtonBase')]")
			
			boolean backButtonFound = false
			
			if (WebUI.waitForElementPresent(backButton, 5, FailureHandling.OPTIONAL)) {
				backButtonFound = true
				WebUI.click(backButton)
				println("Clicked back button using primary locator")
			} else if (WebUI.waitForElementPresent(backButtonAlt1, 3, FailureHandling.OPTIONAL)) {
				backButtonFound = true
				WebUI.click(backButtonAlt1)
				println("Clicked back button using CSS selector")
			} else if (WebUI.waitForElementPresent(backButtonAlt2, 3, FailureHandling.OPTIONAL)) {
				backButtonFound = true
				WebUI.click(backButtonAlt2)
				println("Clicked any MuiButtonBase button as fallback")
			}
			
			if (!backButtonFound) {
				// No back button found, try browser back
				println("No back button found, trying browser back button")
				WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/')
			}
			
			// Wait to ensure we're back at the reports list
			WebUI.delay(5)
			WebUI.takeScreenshot('after_navigation_back_' + reportNumber + '.png')
		} catch (Exception e) {
			println("Error navigating back: " + e.getMessage())
			// Last resort - just go back to the URL
			WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/')
			WebUI.delay(5)
		}
	}
	
	// If both conditions are satisfied, we can exit the loop
	if (wbcLessThan200WithWarningFound && wbcMoreThan200WithoutWarningFound) {
		println("All test conditions have been satisfied!")
		break
	}
}

// Output test results
println("Test Results Summary:")
println("1. WBC < 200 with warning message: " + (wbcLessThan200WithWarningFound ? "PASSED" : "NOT FOUND"))
println("2. WBC >= 200 without warning message: " + (wbcMoreThan200WithoutWarningFound ? "PASSED" : "NOT FOUND"))

// Overall test result
if (wbcLessThan200WithWarningFound && wbcMoreThan200WithoutWarningFound) {
	println("TEST PASSED: Both test conditions were satisfied")
} else if (wbcLessThan200WithWarningFound) {
	println("TEST PARTIALLY PASSED: Found report with WBC < 200 and warning message, but no report with WBC >= 200 without warning message")
} else if (wbcMoreThan200WithoutWarningFound) {
	println("TEST PARTIALLY PASSED: Found report with WBC >= 200 without warning message, but no report with WBC < 200 with warning message")
} else {
	println("TEST FAILED: No test conditions were satisfied after checking " + reportNumber + " reports")
}

// Close the browser
WebUI.closeBrowser()