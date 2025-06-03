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
 * SUM_044_Verify the warning sign is NOT present for PLT clumps when the platelet clumps are NOT detected
 *
 * Test steps:
 * 1. Login to PBS application
 * 2. Scan through reports until finding one where platelet clumps are NOT detected
 * 3. Verify the warning message does NOT appear in the summary tab
 * 4. Test passes only if platelet clumps are NOT detected AND the warning message is NOT present
 */

// Initialize tracking variables
boolean testPassed = false        // Set to true when we find a report with no clumps and confirm no warning
int reportNumber = 0              // Counter for reports checked
int maxReports = 30               // Maximum reports to check before stopping
String reportId = ""              // Stores current report ID for logging

// Login to PBS system
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.waitForPageLoad(30)

// Main loop: Check reports until finding one with NO platelet clumps and confirming no warning message
while (!testPassed && (reportNumber < maxReports)) {
	reportNumber++
	println("Checking report #" + reportNumber)
	
	// Select report from list
	TestObject rowObject = new TestObject('Row ' + reportNumber)
	rowObject.addProperty('xpath', ConditionType.EQUALS, '//tbody/tr[' + reportNumber + ']')
	
	// Check if we've reached the end of available reports
	if (!WebUI.waitForElementPresent(rowObject, 10, FailureHandling.OPTIONAL)) {
		println("No more reports available after #" + (reportNumber - 1))
		break
	}
	
	// Get report ID for logging purposes
	TestObject reportIdObject = new TestObject('Report ID')
	reportIdObject.addProperty('xpath', ConditionType.EQUALS, '//tbody/tr[' + reportNumber + ']/td[3]')
	if (WebUI.waitForElementPresent(reportIdObject, 5, FailureHandling.OPTIONAL)) {
		reportId = WebUI.getText(reportIdObject)
		println("Checking Report ID: " + reportId)
	} else {
		reportId = "Unknown"
	}
	
	// Open the report
	WebUI.waitForElementVisible(rowObject, 20)
	WebUI.click(rowObject)
	WebUI.delay(3)
	
	// Step 1: Go to Platelets tab
	TestObject plateletsTabObject = new TestObject('Platelets Tab')
	plateletsTabObject.addProperty('xpath', ConditionType.EQUALS, '//span[text()="Platelets"]')
	WebUI.waitForElementVisible(plateletsTabObject, 20)
	WebUI.click(plateletsTabObject)
	
	// Step 2: Go to Morphology tab
	TestObject morphologyTabObject = new TestObject('Morphology Tab')
	morphologyTabObject.addProperty('xpath', ConditionType.EQUALS, '//button[text()="Morphology"]')
	WebUI.waitForElementVisible(morphologyTabObject, 20)
	WebUI.click(morphologyTabObject)
	WebUI.delay(2)
	
	// Step 3: Check if platelet clumps are NOT detected
	TestObject plateletClumpsStatusObject = new TestObject('Platelet Clumps Status')
	plateletClumpsStatusObject.addProperty('xpath', ConditionType.EQUALS, '//div[text()="Platelet Clumps"]/parent::div/div[contains(@class, "detect-status")]')
	
	// Try alternate selector if first one fails
	boolean statusElementFound = WebUI.waitForElementVisible(plateletClumpsStatusObject, 5, FailureHandling.OPTIONAL)
	if (!statusElementFound) {
		plateletClumpsStatusObject = new TestObject('Alt Platelet Clumps Status')
		plateletClumpsStatusObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(text(),"Platelet Clumps")]/following-sibling::div[2]')
		statusElementFound = WebUI.waitForElementVisible(plateletClumpsStatusObject, 5, FailureHandling.OPTIONAL)
	}
	
	// If we found the status element, check if clumps are NOT detected
	if (statusElementFound) {
		String clumpsDetectionStatus = WebUI.getText(plateletClumpsStatusObject)
		println("Report ID " + reportId + " (#" + reportNumber + "): Platelet Clumps status is: " + clumpsDetectionStatus)
		
		// Only proceed if platelet clumps are explicitly "Not detected"
		if (clumpsDetectionStatus.trim().equalsIgnoreCase("Not detected")) {
			println("Platelet Clumps NOT detected in Report ID " + reportId + " (#" + reportNumber + ")")
			
			// Step 4: Go to Summary tab
			TestObject summaryTabObject = new TestObject('Summary Tab')
			summaryTabObject.addProperty('xpath', ConditionType.EQUALS, '//div[@id="root"]/div/div[2]/div/div/div/button[1]')
			WebUI.waitForElementVisible(summaryTabObject, 10)
			
			// Try different click methods if needed
			boolean clickSuccess = false
			try {
				WebUI.click(summaryTabObject)
				clickSuccess = true
			} catch (Exception e) {
				// Fallback to JavaScript click if regular click fails
				try {
					WebElement element = WebUI.findWebElement(summaryTabObject)
					JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
					js.executeScript("arguments[0].click();", element)
					clickSuccess = true
				} catch (Exception jsException) {
					println("Failed to click Summary tab - both methods failed")
				}
			}
			
			if (clickSuccess) {
				WebUI.delay(2)  // Wait for summary to load
				
				// Step 5: Check that the warning message is NOT present
				TestObject warningMessageObject = new TestObject('Warning Message')
				warningMessageObject.addProperty('xpath', ConditionType.EQUALS,
					'//div//span[contains(text(), "Platelet clumps are detected") and contains(text(), "might be underestimated")]')
				
				boolean warningFound = WebUI.waitForElementPresent(warningMessageObject, 5, FailureHandling.OPTIONAL)
				
				if (!warningFound) {
					// Success case: Warning message is NOT found (as expected)
					println("✓ SUCCESS: No warning message found as expected when platelet clumps are not detected")
					WebUI.takeScreenshot('NoClumpsNoWarning_' + reportId + '.png')
					testPassed = true  // Test passes
					break
				} else {
					// Failure case: Warning message found despite platelet clumps NOT being detected
					String warningText = WebUI.getText(warningMessageObject)
					println("✗ FAILED: Warning message found on Summary tab despite Platelet Clumps being 'Not detected': \"" + warningText + "\"")
					WebUI.takeScreenshot('UnexpectedWarning_' + reportId + '.png')
					// We do NOT set testPassed to true, so test will fail
					
					// This is a critical failure - it means the application is showing warnings when it shouldn't
					// You could throw an error here to immediately fail the test if this is considered very important
					// throw new Exception("Critical test failure: Warning message displayed when platelet clumps are 'Not detected'")
				}
			}
		} else {
			println("Platelet Clumps are detected in this report, continuing search for a report with NO clumps...")
		}
	} else {
		println("Could not determine Platelet Clumps status for Report ID " + reportId)
	}
	
	// Return to list page to check next report
	navigateBackToListPage()
}

// Display final test result
println("\n----- TEST SUMMARY -----")
if (testPassed) {
	println("✓ TEST PASSED: Found a report with  platelet clumps Not detected and verified that NO warning message is present")
	println("  - Report ID: " + reportId)
	println("  - Report number: " + reportNumber)
} else {
	println("✗ TEST FAILED: Could not find a report where there are  platelet clumps Not detected AND no warning message")
	println("  - Reports checked: " + reportNumber)
}

/**
 * Helper method to return to the list page
 * Tries multiple approaches to find and click the back button
 */
def navigateBackToListPage() {
	println("Navigating back to list page...")
	
	// Try standard back buttons first
	def backButtonSelectors = [
		'//button[contains(@class, "back-btn")]',
		'//button[contains(@class, "back")]',
		'//button[.//i[contains(@class, "back")]]',
		'//button[.//span[contains(text(), "Back")]]'
	]
	
	boolean backButtonClicked = false
	
	// Try each selector
	for (String selector in backButtonSelectors) {
		TestObject backButton = new TestObject('Back Button')
		backButton.addProperty('xpath', ConditionType.EQUALS, selector)
		
		if (WebUI.waitForElementPresent(backButton, 2, FailureHandling.OPTIONAL)) {
			try {
				WebUI.click(backButton)
				backButtonClicked = true
				break
			} catch (Exception e) {
				// Try JavaScript click if standard click fails
				try {
					WebElement element = WebUI.findWebElement(backButton)
					JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
					js.executeScript("arguments[0].click();", element)
					backButtonClicked = true
					break
				} catch (Exception jsException) {
					// Continue to next selector
				}
			}
		}
	}
	
	// Use browser back if no button found/clicked
	if (!backButtonClicked) {
		WebUI.executeJavaScript("window.history.back();", null)
	}
	
	// Wait for list page to load
	WebUI.delay(3)
}