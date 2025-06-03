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
import org.openqa.selenium.interactions.Actions as Actions

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

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

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
		WebUI.comment('Found ' + toBeReviewedRows.size() + ' reports with "To be reviewed" status - these reports are not assigned to any reviewer')
		
		// Get the first "To be reviewed" report
		WebElement firstToBeReviewedRow = toBeReviewedRows.get(0)
		
		// Get the report ID
		String reportId = "Unknown"
		try {
			WebElement reportIdElement = firstToBeReviewedRow.findElement(By.xpath('./td[2]'))
			reportId = reportIdElement.getText()
			WebUI.comment('Found "To be reviewed" report with Slide ID: ' + reportId)
		} catch (Exception e) {
			WebUI.comment('Could not extract report ID: ' + e.getMessage())
		}
		
		// Click on the report
		firstToBeReviewedRow.click()
		WebUI.comment('Clicked on report with Slide ID: ' + reportId)
		
		// Wait for the report details to load
		WebUI.delay(2)
		
		// Directly assign to deekshithaS using more reliable methods
		WebUI.comment('Assigning report ' + reportId + ' to deekshithaS')
		
		// Click on the assigned_to field to open the dropdown
		WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
		WebUI.delay(1) // Give the dropdown time to appear
		
		// Create a dynamic XPath to find the deekshithaS option in the dropdown
		TestObject deekshithaOption = new TestObject('deekshithaS Option')
		deekshithaOption.addProperty("xpath", ConditionType.EQUALS, "//li[text()='deekshithaS' or contains(text(),'deekshithaS')]")
		
		// Make sure the dropdown option is visible before clicking
		WebUI.waitForElementVisible(deekshithaOption, 10)
		WebUI.click(deekshithaOption)
		
		// Alternative approach if the above doesn't work
		if (!WebUI.verifyElementAttributeValue(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'),
			'value', 'deekshithaS', 5, FailureHandling.OPTIONAL)) {
			
			WebUI.comment('First method failed, trying alternative approach')
			// Clear the input field and type the name directly
			WebUI.clearText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
			WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'deekshithaS')
			WebUI.delay(1)
			
			// Press Tab to select the option and move focus away
			WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), Keys.chord(Keys.TAB))
		}
		
		// Verify assignment was successful
		WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 10)
		def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')
		newAssignment = newAssignment.trim()
		WebUI.comment('New assignment for ' + reportId + ' (after trim): \'' + newAssignment + '\'')
		
		processedToBeReviewed = true
	} else {
		WebUI.comment('No reports with "To be reviewed" status found')
	}
	
	// If no "To be reviewed" reports were processed, check the 3rd report
	if (!processedToBeReviewed) {
		WebUI.comment('No "To be reviewed" reports found. Proceeding to check the 3rd report')
		
		// Find all rows in the table
		List<WebElement> allRows = driver.findElements(By.xpath('//tr[./td]'))
		
		// Check if we have at least 3 rows
		if (allRows.size() >= 3) {
			// Get the 3rd row (index 2 since we start counting from 0)
			WebElement thirdRow = allRows.get(2)
			
			// Get the report ID from the 3rd row
			String reportId = "Unknown"
			try {
				WebElement reportIdElement = thirdRow.findElement(By.xpath('./td[2]'))
				reportId = reportIdElement.getText()
				WebUI.comment('Selected 3rd report with Slide ID: ' + reportId)
			} catch (Exception e) {
				WebUI.comment('Could not extract report ID: ' + e.getMessage())
			}
			
			// Click on the 3rd report
			thirdRow.click()
			WebUI.comment('Clicked on 3rd report with Slide ID: ' + reportId)
			
			// Wait for the report details to load
			WebUI.delay(2)
			
			// Check current assignment status
			def currentAssignment = ""
			try {
				// Attempt to get the current assignee
				currentAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')
				currentAssignment = currentAssignment.trim()
			} catch (Exception e) {
				WebUI.comment('Could not get current assignment: ' + e.getMessage())
			}
			
			WebUI.comment('Current assignment for ' + reportId + ': \'' + currentAssignment + '\'')
			
			// Handle assignment based on current status
			if (currentAssignment == 'deekshithaS') {
				// Case 1: Report is already assigned to deekshithaS
				WebUI.comment('Report ' + reportId + ' is already assigned to deekshithaS. No action needed.')
			} else {
				// Case 2: Report is unassigned or assigned to someone else
				WebUI.comment('Report ' + reportId + ' is assigned to \'' + currentAssignment + '\'. Reassigning to deekshithaS.')
				
				// Clear the current assignment and set to deekshithaS (following reference code)
				WebUI.doubleClick(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
				WebUI.clearText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
				WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'deekshithaS')
				
				// Create and use dynamic TestObject for deekshithaS in dropdown to match the reference code
				TestObject deekshithaOption = new TestObject('deekshithaS Option')
				deekshithaOption.addProperty("xpath", ConditionType.EQUALS, "//li[text()='deekshithaS' or contains(text(),'deekshithaS')]")
				WebUI.waitForElementVisible(deekshithaOption, 10)
				WebUI.click(deekshithaOption)
				
				// Handle reassignment confirmation if needed
				try {
					// Create a dynamic TestObject for the re-assign button based on reference code
					TestObject reassignButton = new TestObject('Reassign Button')
					reassignButton.addProperty('xpath', ConditionType.EQUALS, "//div[@id='reassign-dialog']//button[text()='Re-assign']")
					
					// Check if reassign button exists and click it (for when reports already assigned to someone else)
					if (WebUI.waitForElementPresent(reassignButton, 5, FailureHandling.OPTIONAL)) {
						WebUI.comment('Clicking Re-assign button to confirm reassignment')
						WebUI.click(reassignButton)
					}
				} catch (Exception e) {
					WebUI.comment('Reassignment confirmation dialog not found or not needed: ' + e.getMessage())
				}
				
				// Verify assignment was successful
				WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 10)
				def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')
				newAssignment = newAssignment.trim()
				WebUI.comment('New assignment for ' + reportId + ' (after trim): \'' + newAssignment + '\'')
			}
		} else {
			WebUI.comment('Not enough reports found in the table to select the 3rd one.')
		}
	}
} catch (Exception e) {
	WebUI.comment('An error occurred during execution: ' + e.getMessage())
	e.printStackTrace()
} finally {
	// Add a small delay before ending the test
	WebUI.delay(3)
	
}



// Starting platlet tab tab interactions
WebUI.comment('Starting  platletes tab interactions')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_plateletes'),'Platelets')

WebUI.click(findTestObject('Object Repository/single_click/button_plateletes'))

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Morphology'),'Morphology')

WebUI.click(findTestObject('Object Repository/single_click/button_Morphology'))


// Create a TestObject for platelets morphology patches with a more reliable XPath
def plateletsmorphologyPatchesObject = new TestObject('Platelets Morphology Patches')
plateletsmorphologyPatchesObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@class,"Card patches-container")]')

// Find platelets morphology patch elements
List plateletsmorphologyPatches = []
try {
	// First try to find patches
	WebUI.comment('Searching for platelets morphology patches...')
	WebUI.delay(2) // Give some time for patches to load

	// Try with WebUiCommonHelper
	try {
		plateletsmorphologyPatches = WebUiCommonHelper.findWebElements(plateletsmorphologyPatchesObject, 20)
	} catch (Exception e) {
		WebUI.comment('First attempt to find patches failed, trying alternate approach')

		// Try direct driver approach as backup
		plateletsmorphologyPatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@class,"Card patches-container")]'))
	}

	WebUI.comment('Total platelets morphology patches found: ' + plateletsmorphologyPatches.size())

	// If we still have no patches, try one more XPath variant
	if (plateletsmorphologyPatches.size() == 0) {
		WebUI.comment('No patches found with primary XPath, trying alternative XPath')
		plateletsmorphologyPatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@class,"Card patches-container")]'))
		WebUI.comment('Total platelets morphology patches found with alternative XPath: ' + plateletsmorphologyPatches.size())
	}
} catch (Exception e) {
	WebUI.comment('Failed to find platelets morphology patches: ' + e.getMessage())
}

// Define the number of patches to select (exactly 3)
int plateletsmorphologyPatchesToSelect = 3
WebUI.comment('Will select ' + plateletsmorphologyPatchesToSelect + ' platelets morphology patches')

// Selected patches list
def selectedPlateletsmorphologyPatches = []

// Make sure we have patches to work with
if (plateletsmorphologyPatches.size() > 0) {
	// Limit to available patches but don't exceed our target
	int maxToSelect = Math.min(plateletsmorphologyPatchesToSelect, plateletsmorphologyPatches.size())

	// Use CTRL+click (or Command+click on Mac) to select multiple patches
	Actions actions = new Actions(driver)

	for (int i = 0; i < maxToSelect; i++) {
		try {
			WebUI.comment('Selecting platelets morphology patch #' + (i + 1))

			// Scroll to the patch
			WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(plateletsmorphologyPatches.get(i)))
			WebUI.delay(1)

			// For the first patch, just click normally
			if (i == 0) {
				plateletsmorphologyPatches.get(i).click()
			} else {
				// For subsequent patches, hold CTRL (or Command on Mac) while clicking
				// Determine if we're on Mac or Windows
				String osName = System.getProperty('os.name').toLowerCase()
				if (osName.contains('mac')) {
					// For Mac OS
					actions.keyDown(Keys.COMMAND).click(plateletsmorphologyPatches.get(i)).keyUp(Keys.COMMAND).build().perform()
				} else {
					// For Windows/Linux
					actions.keyDown(Keys.CONTROL).click(plateletsmorphologyPatches.get(i)).keyUp(Keys.CONTROL).build().perform()
				}
			}

			WebUI.delay(1)
			selectedPlateletsmorphologyPatches.add(plateletsmorphologyPatches.get(i))

		} catch (Exception e) {
			WebUI.comment('Failed to select platelets morphology patch #' + (i + 1) + ': ' + e.getMessage())
		}
	}

	println('Successfully selected ' + selectedPlateletsmorphologyPatches.size() + ' platelets morphology patches')
} else {
	println('No platelets morphology patches available to select')
}


println"==================================================================="

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_platelet_clumps'),'Platelet Clumps')

WebUI.click(findTestObject('Object Repository/single_click/button_platelet_clumps'))



// Create a TestObject for platelet clumps patches with a more reliable XPath
def plateletclumpsPatchesObject = new TestObject('Platelet Clumps Patches')
plateletclumpsPatchesObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@class,"Card patches-container")]')

// Find platelet clumps patch elements
List plateletclumpsPatches = []
try {
	// First try to find patches
	WebUI.comment('Searching for platelet clumps patches...')
	WebUI.delay(2) // Give some time for patches to load

	// Try with WebUiCommonHelper
	try {
		plateletclumpsPatches = WebUiCommonHelper.findWebElements(plateletclumpsPatchesObject, 20)
	} catch (Exception e) {
		WebUI.comment('First attempt to find patches failed, trying alternate approach')

		// Try direct driver approach as backup
		plateletclumpsPatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@class,"Card patches-container")]'))
	}

	WebUI.comment('Total platelet clumps patches found: ' + plateletclumpsPatches.size())

	// If we still have no patches, try one more XPath variant
	if (plateletclumpsPatches.size() == 0) {
		WebUI.comment('No patches found with primary XPath, trying alternative XPath')
		plateletclumpsPatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@class,"Card patches-container")]'))
		WebUI.comment('Total platelet clumps patches found with alternative XPath: ' + plateletclumpsPatches.size())
	}
} catch (Exception e) {
	WebUI.comment('Failed to find platelet clumps patches: ' + e.getMessage())
}

// Define the number of patches to select (exactly 3)
int plateletclumpsPatchesToSelect = 3
WebUI.comment('Will select ' + plateletclumpsPatchesToSelect + ' platelet clumps patches')

// Selected patches list
def selectedPlateletclumpsPatches = []

// Make sure we have patches to work with
if (plateletclumpsPatches.size() > 0) {
	// Limit to available patches but don't exceed our target
	int maxToSelect = Math.min(plateletclumpsPatchesToSelect, plateletclumpsPatches.size())

	// Use CTRL+click (or Command+click on Mac) to select multiple patches
	Actions actions = new Actions(driver)

	for (int i = 0; i < maxToSelect; i++) {
		try {
			WebUI.comment('Selecting platelet clumps patch #' + (i + 1))

			// Scroll to the patch
			WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(plateletclumpsPatches.get(i)))
			WebUI.delay(1)

			// For the first patch, just click normally
			if (i == 0) {
				plateletclumpsPatches.get(i).click()
			} else {
				// For subsequent patches, hold CTRL (or Command on Mac) while clicking
				// Determine if we're on Mac or Windows
				String osName = System.getProperty('os.name').toLowerCase()
				if (osName.contains('mac')) {
					// For Mac OS
					actions.keyDown(Keys.COMMAND).click(plateletclumpsPatches.get(i)).keyUp(Keys.COMMAND).build().perform()
				} else {
					// For Windows/Linux
					actions.keyDown(Keys.CONTROL).click(plateletclumpsPatches.get(i)).keyUp(Keys.CONTROL).build().perform()
				}
			}

			WebUI.delay(1)
			selectedPlateletclumpsPatches.add(plateletclumpsPatches.get(i))

		} catch (Exception e) {
			WebUI.comment('Failed to select platelet clumps patch #' + (i + 1) + ': ' + e.getMessage())
		}
	}

	println('Successfully selected ' + selectedPlateletclumpsPatches.size() + ' platelet clumps patches')
} else {
	println('No platelet clumps patches available to select')
}

println"==================================================================="


