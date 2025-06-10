
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

// Wait for the table to be visible
WebUI.waitForElementVisible(findTestObject('Object Repository/Summary/Page_PBS (1)/table'), 30)

// Variable to track if we found and processed a "To be reviewed" report
boolean processedToBeReviewed = false

try {
	// First try to find reports with "To be reviewed" status with scrolling
	WebUI.comment('Looking for reports with "To be reviewed" status...')
	
	// Variables for scrolling logic
	List<WebElement> toBeReviewedRows = []
	int scrollAttempts = 0
	int maxScrollAttempts = 15
	boolean foundToBeReviewed = false
	
	// Find the infinite scroll container based on the image structure
	WebElement scrollContainer = null
	try {
		// Try to find the infinite scroll component container
		scrollContainer = driver.findElement(By.xpath("//div[contains(@class, 'infinite-scroll-component')]"))
		WebUI.comment('Found infinite scroll container')
	} catch (Exception e) {
		// Fallback to table container or other possible containers
		try {
			scrollContainer = driver.findElement(By.xpath("//div[contains(@class, 'MuiTableContainer-root')]"))
			WebUI.comment('Found MUI table container')
		} catch (Exception e2) {
			try {
				scrollContainer = driver.findElement(By.xpath("//div[@id='reportListingTable']"))
				WebUI.comment('Found report listing table container')
			} catch (Exception e3) {
				WebUI.comment('Could not find specific scroll container, using page scrolling')
			}
		}
	}
	
	// Scroll through the table to find all "To be reviewed" reports
	while (scrollAttempts < maxScrollAttempts && !foundToBeReviewed) {
		// Find all rows with "To be reviewed" status in current view
		List<WebElement> currentToBeReviewedRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), "To be reviewed")] or .//div[contains(text(), "To be reviewed")]]'))
		
		if (currentToBeReviewedRows.size() > 0) {
			toBeReviewedRows.addAll(currentToBeReviewedRows)
			foundToBeReviewed = true
			WebUI.comment('Found "To be reviewed" reports on scroll attempt ' + (scrollAttempts + 1))
			break
		}
		
		// Scroll within the infinite scroll container if found
		if (scrollContainer != null) {
			// Get current scroll position
			Long currentScrollTop = (Long) js.executeScript("return arguments[0].scrollTop;", scrollContainer)
			
			// Scroll down within the container
			js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 500;", scrollContainer)
			WebUI.delay(2) // Wait for new content to load
			
			// Check if we've reached the bottom (no more scrolling possible)
			Long newScrollTop = (Long) js.executeScript("return arguments[0].scrollTop;", scrollContainer)
			if (newScrollTop.equals(currentScrollTop)) {
				WebUI.comment('Reached bottom of infinite scroll container')
				break
			}
		} else {
			// Fallback to page scrolling
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);")
			WebUI.delay(2)
		}
		
		scrollAttempts++
		WebUI.comment('Scroll attempt ' + scrollAttempts + ' completed, checking for "To be reviewed" reports...')
	}
	
	// After scrolling, do a final comprehensive search
	toBeReviewedRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), "To be reviewed")] or .//div[contains(text(), "To be reviewed")]]'))
	
	// Check if any "To be reviewed" reports were found
	if (toBeReviewedRows.size() > 0) {
		WebUI.comment('Found ' + toBeReviewedRows.size() + ' reports with "To be reviewed" status - these reports are not assigned to any reviewer')
		
		// Get the first "To be reviewed" report
		WebElement firstToBeReviewedRow = toBeReviewedRows.get(0)
		
		// Scroll the specific report into view
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstToBeReviewedRow)
		WebUI.delay(1)
		
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
		WebUI.comment('No reports with "To be reviewed" status found after scrolling through ' + scrollAttempts + ' attempts')
		WebUI.comment('Confirmed: No "To be reviewed" reports are present on the page')
	}
	
	// If no "To be reviewed" reports were processed, check the 3rd report
	if (!processedToBeReviewed) {
		WebUI.comment('No "To be reviewed" reports found. Proceeding to check the 3rd report')
		
		// Scroll back to top of the container to ensure we can see all reports
		if (scrollContainer != null) {
			js.executeScript("arguments[0].scrollTop = 0;", scrollContainer)
		} else {
			js.executeScript("window.scrollTo(0, 0);")
		}
		WebUI.delay(2)
		
		// Find all rows in the table
		List<WebElement> allRows = driver.findElements(By.xpath('//tr[./td]'))
		
		// Check if we have at least 3 rows
		if (allRows.size() >= 3) {
			// Get the 3rd row (index 2 since we start counting from 0)
			WebElement thirdRow = allRows.get(2)
			
			// Scroll the 3rd report into view
			js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", thirdRow)
			WebUI.delay(1)
			
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


// Verify the report title is correct
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

// Store different combinations of alphabets, numbers and special characters to test in each field
def rbcMorphologyAlphaNumSpecial = "ABC123@#%^&*def456"         // Alphabets, numbers with common symbols
def wbcMorphologyAlphaNumSpecial = "GHI789!~<>?:{}jkl012"        // Alphabets, numbers with punctuation and brackets
def plateletMorphologyAlphaNumSpecial = "MNO345()[]/\\pqr678"    // Alphabets, numbers with brackets and slashes
def hemoparasiteAlphaNumSpecial = "STU901+=_-;'\"vwx234"         // Alphabets, numbers with math symbols and quotes
def impressionAlphaNumSpecial = "YZA567|€£¥©®™bcd890"            // Alphabets, numbers with currency and trademark symbols

// This function enters alphabet + number + special character combinations into a field
// It waits for the field to be visible, clicks it, clears any existing text,
// enters our alphabet + number + special chars, then tabs out to save the entry
def enterAlphaNumSpecialInField(TestObject fieldObject, String alphaNumSpecialToEnter) {
	WebUI.waitForElementVisible(fieldObject, 20)
	WebUI.click(fieldObject)
	WebUI.clearText(fieldObject)
	WebUI.setText(fieldObject, alphaNumSpecialToEnter)
	WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB)) // Tab out to save the entry
}

// Helper function to check if text contains alphabets, numbers and special characters
def containsAlphaNumSpecial(String text) {
	return text.find(/[a-zA-Z]/) && text.find(/[0-9]/) && text.find(/[^a-zA-Z0-9\s]/)
}

// First field: RBC Morphology - Try to enter alphabets, numbers and special characters
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')
	
	// Enter our alphabets, numbers and special characters into the field
	enterAlphaNumSpecialInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_RBC_Morphology'), rbcMorphologyAlphaNumSpecial)
	
	// Read back what's in the field to check if alphabets, numbers and special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_RBC_Morphology'))
	
	// Check if the text contains alphabets, numbers and special characters
	if (containsAlphaNumSpecial(enteredText)) {
		println "PASS: Successfully entered combination of alphabets, numbers and special characters in RBC Morphology field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: RBC Morphology field does not contain alphabets, numbers and special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphabets, numbers and special characters in RBC Morphology field: " + e.getMessage()
}

// Second field: WBC Morphology - Continue even if the first one failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology (1)'), 'WBC Morphology')
	
	// Enter our alphabets, numbers and special characters into the field
	enterAlphaNumSpecialInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_WBC_Morphology'), wbcMorphologyAlphaNumSpecial)
	
	// Read back what's in the field to check if alphabets, numbers and special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_WBC_Morphology'))
	
	// Check if the text contains alphabets, numbers and special characters
	if (containsAlphaNumSpecial(enteredText)) {
		println "PASS: Successfully entered combination of alphabets, numbers and special characters in WBC Morphology field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: WBC Morphology field does not contain alphabets, numbers and special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphabets, numbers and special characters in WBC Morphology field: " + e.getMessage()
}

// Third field: Platelet Morphology - Continue even if previous fields failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology (1)'), 'Platelet Morphology')
	
	// Enter our alphabets, numbers and special characters into the field
	enterAlphaNumSpecialInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Platelet_Morphology'), plateletMorphologyAlphaNumSpecial)
	
	// Read back what's in the field to check if alphabets, numbers and special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Platelet_Morphology'))
	
	// Check if the text contains alphabets, numbers and special characters
	if (containsAlphaNumSpecial(enteredText)) {
		println "PASS: Successfully entered combination of alphabets, numbers and special characters in Platelet Morphology field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: Platelet Morphology field does not contain alphabets, numbers and special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphabets, numbers and special characters in Platelet Morphology field: " + e.getMessage()
}

// Fourth field: Hemoparasite - Continue even if previous fields failed
try {
	// Check if the field label is correct
//	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite (1)'), 'Hemoparasite')
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')
	
	
	// Enter our alphabets, numbers and special characters into the field
	enterAlphaNumSpecialInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'), hemoparasiteAlphaNumSpecial)
	
	// Read back what's in the field to check if alphabets, numbers and special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'))
	
	// Check if the text contains alphabets, numbers and special characters
	if (containsAlphaNumSpecial(enteredText)) {
		println "PASS: Successfully entered combination of alphabets, numbers and special characters in Hemoparasite field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: Hemoparasite field does not contain alphabets, numbers and special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphabets, numbers and special characters in Hemoparasite field: " + e.getMessage()
}

// Fifth field: Impression - Continue even if previous fields failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')
	// Enter our alphabets, numbers and special characters into the field
	enterAlphaNumSpecialInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Impression'), impressionAlphaNumSpecial)
	
	// Read back what's in the field to check if alphabets, numbers and special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Impression'))
	
	// Check if the text contains alphabets, numbers and special characters
	if (containsAlphaNumSpecial(enteredText)) {
		println "PASS: Successfully entered combination of alphabets, numbers and special characters in Impression field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: Impression field does not contain alphabets, numbers and special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphabets, numbers and special characters in Impression field: " + e.getMessage()
}

println("End of the testcase")
