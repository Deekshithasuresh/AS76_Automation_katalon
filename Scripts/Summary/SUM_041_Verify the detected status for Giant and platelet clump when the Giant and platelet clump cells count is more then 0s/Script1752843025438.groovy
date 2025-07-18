
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
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.JavascriptExecutor

/**
 * SUM_041_Verify the detected status for Large platelets and platelet clumps when the count is >0
 * when the Large platelets and platelet clump cells count is more than 0
 * Continue checking reports until both features are found with count > 0
 */

// Initialize tracking variables to monitor test progress
boolean largePlateletsFound = false    // Tracks if we've found a case with Large Platelets count > 0
boolean plateletClumpsFound = false    // Tracks if we've found a case with Platelet Clumps count > 0
int reportNumber = 0                   // Counter for number of reports checked
int maxReports = 30                    // Maximum number of reports to check before stopping
String reportId = ""                   // Variable to store current report ID for better tracking

// Open browser and perform login to the PBS system
WebUI.openBrowser('')                  // Launch a new browser instance
WebUI.maximizeWindow()                 // Maximize browser window for better visibility
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')  // Navigate to the login page
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')  // Enter username
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')  // Enter encrypted password
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))  // Press Enter to submit login
WebUI.waitForPageLoad(30)              // Wait for page to load completely after login

// Main loop: Continue checking reports until both features are found or max reports reached
while ((!largePlateletsFound || !plateletClumpsFound) && (reportNumber < maxReports)) {
	reportNumber++                     // Increment report counter
	println("Checking report #" + reportNumber)  // Log which report number is being checked
	
	// Create a test object for the current row in the table based on row number
	TestObject rowObject = new TestObject('Row ' + reportNumber)
	rowObject.addProperty('xpath', ConditionType.EQUALS, '//tbody/tr[' + reportNumber + ']')
	
	// Check if row exists - if not, we've reached the end of available reports
	if (!WebUI.waitForElementPresent(rowObject, 10, FailureHandling.OPTIONAL)) {
		println("No more reports available after #" + (reportNumber - 1))  // Log that no more reports are available
		break  // Exit the loop if no more reports are found
	}
	
	// Get report ID from the first column before clicking on the row
	TestObject reportIdObject = new TestObject('Report ID')
	reportIdObject.addProperty('xpath', ConditionType.EQUALS, '//tbody/tr[' + reportNumber + ']/td[3]')
	if (WebUI.waitForElementPresent(reportIdObject, 5, FailureHandling.OPTIONAL)) {
		reportId = WebUI.getText(reportIdObject)  // Extract report ID text from the table cell
		println("Checking Report ID: " + reportId)  // Log which report ID is being checked
	} else {
		reportId = "Unknown"  // Set to Unknown if ID can't be retrieved
		println("Could not retrieve Report ID for row " + reportNumber)  // Log error if ID retrieval fails
	}
	
	// Click on the row to open the slide details
	WebUI.waitForElementVisible(rowObject, 20)  // Wait for row to be visible before clicking
	WebUI.click(rowObject)  // Click on the row to open detailed view
	WebUI.delay(3)  // Wait for slide data to load
	
	// Navigate to Platelets tab
	TestObject plateletsTabObject = new TestObject('Platelets Tab')
	plateletsTabObject.addProperty('xpath', ConditionType.EQUALS, '//span[text()="Platelets"]')
	WebUI.waitForElementVisible(plateletsTabObject, 20)  // Wait for Platelets tab to be visible
	WebUI.click(plateletsTabObject)  // Click on Platelets tab
	
	// Navigate to Morphology tab within Platelets section
	TestObject morphologyTabObject = new TestObject('Morphology Tab')
	morphologyTabObject.addProperty('xpath', ConditionType.EQUALS, '//button[text()="Morphology"]')
	WebUI.waitForElementVisible(morphologyTabObject, 20)  // Wait for Morphology tab to be visible
	WebUI.click(morphologyTabObject)  // Click on Morphology tab
	WebUI.delay(2)  // Wait for morphology data to load
	
	/* Define test objects for Large Platelets elements */
	// Object for the Large Platelets label
	TestObject largePlateletsNameObject = new TestObject('Large Platelets Name')
	largePlateletsNameObject.addProperty('xpath', ConditionType.EQUALS, '//*/text()[normalize-space(.)="Large Platelets"]/parent::*')
	
	// Object for the Large Platelets count value
	TestObject largePlateletsCountObject = new TestObject('Large Platelets Count')
	largePlateletsCountObject.addProperty('xpath', ConditionType.EQUALS, '//*[@id="root"]/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div[2]')
	
	// Object for the Large Platelets detection status
	TestObject largePlateletsStatusObject = new TestObject('Large Platelets Status')
	largePlateletsStatusObject.addProperty('xpath', ConditionType.EQUALS, '//div[text()="Large Platelets"]/parent::div/div[3]')
	
	/* Define test objects for Platelet Clumps elements */
	// Object for the Platelet Clumps label
	TestObject plateletClumpsNameObject = new TestObject('Platelet Clumps Name')
	plateletClumpsNameObject.addProperty('xpath', ConditionType.EQUALS, '//*[@id="root"]/div/div[2]/div[2]/div/div/div/div[2]/div[3]/div[1]')
	
	// Object for the Platelet Clumps count value
	TestObject plateletClumpsCountObject = new TestObject('Platelet Clumps Count')
	plateletClumpsCountObject.addProperty('xpath', ConditionType.EQUALS, '//*[@id="root"]/div/div[2]/div[2]/div/div/div/div[2]/div[3]/div[2]')
	
	// Object for the Platelet Clumps detection status
	TestObject plateletClumpsStatusObject = new TestObject('Platelet Clumps Status')
	plateletClumpsStatusObject.addProperty('xpath', ConditionType.EQUALS, '//*[@id="root"]/div/div[2]/div[2]/div/div/div/div[2]/div[3]/div[3]')
	
	/* Define alternative XPath selectors in case primary selectors don't work */
	// Alternative object for Large Platelets count
	TestObject altLargePlateletsCountObject = new TestObject('Alt Large Platelets Count')
	altLargePlateletsCountObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(text(),"Large Platelets")]/following-sibling::div[1]')
	
	// Alternative object for Platelet Clumps count
	TestObject altPlateletClumpsCountObject = new TestObject('Alt Platelet Clumps Count')
	altPlateletClumpsCountObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(text(),"Platelet Clumps")]/following-sibling::div[1]')
	
	// Try to find elements with reasonable timeout
	boolean foundLargePlatelets = WebUI.waitForElementVisible(largePlateletsNameObject, 5, FailureHandling.OPTIONAL)  // Check if Large Platelets label is visible
	boolean foundPlateletClumps = WebUI.waitForElementVisible(plateletClumpsNameObject, 5, FailureHandling.OPTIONAL)  // Check if Platelet Clumps label is visible
	
	/* Check Large Platelets if not already found in previous reports */
	if (!largePlateletsFound && foundLargePlatelets) {
		// Determine which count object to use (primary or alternative)
		TestObject currentLargePlateletsCountObject = WebUI.waitForElementVisible(largePlateletsCountObject, 5, FailureHandling.OPTIONAL) ?
			largePlateletsCountObject : altLargePlateletsCountObject
		
		// Process Large Platelets count if the element is found
		if (WebUI.waitForElementVisible(currentLargePlateletsCountObject, 5, FailureHandling.OPTIONAL)) {
			String largePlateletsCountText = WebUI.getText(currentLargePlateletsCountObject)  // Get the count text
			try {
				int largePlateletsCount = Integer.parseInt(largePlateletsCountText.trim())  // Convert text to integer
				
				// Check if count is greater than 0
				if (largePlateletsCount > 0) {
					String largeDetectionStatus = WebUI.getText(largePlateletsStatusObject)  // Get the detection status text
					WebUI.verifyMatch(largeDetectionStatus.trim(), "Detected", true)  // Verify status is "Detected"
					println("Report ID " + reportId + " (#" + reportNumber + "): Large Platelets count is " + largePlateletsCount + " and status is correctly set to 'Detected'")
					largePlateletsFound = true  // Mark Large Platelets as found
				} else {
					println("Report ID " + reportId + " (#" + reportNumber + "): Large Platelets count is 0 (Not detected)")
				}
			} catch (NumberFormatException e) {
				// Handle case where count text is not a valid number
				println("Report ID " + reportId + " (#" + reportNumber + "): Could not parse Large Platelets count from: " + largePlateletsCountText)
			}
		}
	}
	
	/* Check Platelet Clumps if not already found in previous reports */
	if (!plateletClumpsFound && foundPlateletClumps) {
		// Determine which count object to use (primary or alternative)
		TestObject currentPlateletClumpsCountObject = WebUI.waitForElementVisible(plateletClumpsCountObject, 5, FailureHandling.OPTIONAL) ?
			plateletClumpsCountObject : altPlateletClumpsCountObject
		
		// Process Platelet Clumps count if the element is found
		if (WebUI.waitForElementVisible(currentPlateletClumpsCountObject, 5, FailureHandling.OPTIONAL)) {
			String plateletClumpsCountText = WebUI.getText(currentPlateletClumpsCountObject)  // Get the count text
			try {
				int plateletClumpsCount = Integer.parseInt(plateletClumpsCountText.trim())  // Convert text to integer
				
				// Check if count is greater than 0
				if (plateletClumpsCount > 0) {
					String clumpsDetectionStatus = WebUI.getText(plateletClumpsStatusObject)  // Get the detection status text
					WebUI.verifyMatch(clumpsDetectionStatus.trim(), "Detected", true)  // Verify status is "Detected"
					println("Report ID " + reportId + " (#" + reportNumber + "): Platelet Clumps count is " + plateletClumpsCount + " and status is correctly set to 'Detected'")
					plateletClumpsFound = true  // Mark Platelet Clumps as found
				} else {
					println("Report ID " + reportId + " (#" + reportNumber + "): Platelet Clumps count is 0 (Not detected)")
				}
			} catch (NumberFormatException e) {
				// Handle case where count text is not a valid number
				println("Report ID " + reportId + " (#" + reportNumber + "): Could not parse Platelet Clumps count from: " + plateletClumpsCountText)
			}
		}
	}
	
	// If we've found both features, we can stop looking and exit the loop early
	if (largePlateletsFound && plateletClumpsFound) {
		println("Both features found! Large Platelets and Platelet Clumps with count > 0 detected.")
		break  // Exit loop after finding both features
	}
	
	// Go back to the list page using a dedicated function with improved reliability
	navigateBackToListPage()
}

// Print summary of test results
println("\n----- SUMMARY -----")
if (largePlateletsFound) {
	// Success message for Large Platelets
	println("✓ Large Platelets with count > 0 found and status correctly displayed as 'Detected'")
} else {
	// Failure message for Large Platelets
	println("✗ Large Platelets with count > 0 NOT found after checking " + reportNumber + " reports")
}

if (plateletClumpsFound) {
	// Success message for Platelet Clumps
	println("✓ Platelet Clumps with count > 0 found and status correctly displayed as 'Detected'")
} else {
	// Failure message for Platelet Clumps
	println("✗ Platelet Clumps with count > 0 NOT found after checking " + reportNumber + " reports")
}

// Keep browser open for further manual inspection if needed
println("\nTest complete. Browser left open for further inspection.")

/**
 * Improved function to navigate back to the list page
 * This function prioritizes reliability in finding and clicking the back button
 * by trying multiple different selectors and clicking methods
 */
def navigateBackToListPage() {
	println("Navigating back to list page...")
	
	// Define various possible selectors for back buttons based on common patterns
	def backButtonSelectors = [
		'//button[contains(@class, "back-btn")]',          // Button with class containing "back-btn"
		'//button[contains(@class, "back")]',              // Button with class containing "back"
		'//button[.//i[contains(@class, "back")]]',        // Button containing an icon with "back" class
		'//button[.//span[contains(text(), "Back")]]',     // Button containing span with "Back" text
		'//a[contains(@class, "back")]',                   // Anchor link with class containing "back"
		'//div[contains(@class, "back-button")]',          // Div with class containing "back-button"
		'//button[contains(@title, "Back")]',              // Button with title containing "Back"
		'//*[contains(@class, "backButton")]',             // Any element with class containing "backButton"
		'//button[contains(text(), "Back")]',              // Button with text containing "Back"
		'//i[contains(@class, "back")]',                   // Icon with class containing "back"
		'//span[contains(text(), "Back to list")]'         // Span with text containing "Back to list"
	]
	
	boolean backButtonClicked = false  // Flag to track if any back button was successfully clicked
	
	// Try each selector with enhanced error handling
	for (String selector in backButtonSelectors) {
		TestObject backButton = new TestObject('Back Button')
		backButton.addProperty('xpath', ConditionType.EQUALS, selector)
		
		// Check if element with this selector exists
		if (WebUI.waitForElementPresent(backButton, 2, FailureHandling.OPTIONAL)) {
			try {
				// First scroll to the element to ensure it's in view
				WebUI.scrollToElement(backButton, 2)
				
				// Make sure it's clickable before attempting to click
				if (WebUI.waitForElementClickable(backButton, 3, FailureHandling.OPTIONAL)) {
					// Try standard click with a short delay to stabilize
					WebUI.delay(0.5)
					WebUI.click(backButton)
					backButtonClicked = true
					println("Successfully clicked back button with selector: " + selector)
					break  // Exit loop after successful click
				} else {
					println("Back button found but not clickable with selector: " + selector)
				}
				
			} catch (Exception e) {
				// Standard click failed, try JavaScript click as a fallback
				println("Standard click failed, trying JavaScript click for selector: " + selector)
				
				try {
					// Get WebElement and use JavaScript to click it
					WebElement element = WebUI.findWebElement(backButton)
					JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
					js.executeScript("arguments[0].click();", element)
					backButtonClicked = true
					println("Successfully clicked back button with JavaScript using selector: " + selector)
					break  // Exit loop after successful JavaScript click
				} catch (Exception jsException) {
					println("JavaScript click also failed for selector: " + selector)
				}
			}
		}
	}
	
	// If all selectors and click attempts failed, use browser's native back button
	if (!backButtonClicked) {
		println("WARNING: Could not find or click any back button. Trying browser's native back function.")
		WebUI.executeJavaScript("window.history.back();", null)  // Execute JavaScript to go back in browser history
		println("Browser back function executed")
	}
	
	// Wait for list page to load with a generous timeout
	WebUI.delay(3)  // Static delay to allow page transition
	
	// Check if we successfully returned to the list by looking for table elements
	TestObject tableObject = new TestObject('Table')
	tableObject.addProperty('xpath', ConditionType.EQUALS, '//table|//tbody|//div[contains(@class, "table")]')
	
	// Wait for table elements to be visible to confirm navigation worked
	boolean listPageLoaded = WebUI.waitForElementVisible(tableObject, 10, FailureHandling.OPTIONAL)
	
	if (listPageLoaded) {
		println("Successfully navigated back to list page")  // Log successful navigation
	} else {
		// If table not found, try one more browser back as last resort
		println("WARNING: Could not confirm if back navigation was successful")
		WebUI.executeJavaScript("window.history.back();", null)  // Try one more browser back
		WebUI.delay(2)  // Additional delay after second attempt
	}
}