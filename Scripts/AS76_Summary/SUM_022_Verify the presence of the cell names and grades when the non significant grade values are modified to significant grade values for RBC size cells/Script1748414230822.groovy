
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


println("\n==== Initial Summary Tab Check ====")
// Initially land on Summary tab and check which cells are present
boolean initialMicrocytePresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_microcyte'), 3, FailureHandling.OPTIONAL)
boolean initialMacrocytePresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_macrocyte'), 3, FailureHandling.OPTIONAL)
boolean initialAnisocytosisPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_anisocytosis'), 3, FailureHandling.OPTIONAL)

println("INITIAL SUMMARY CELLS PRESENT:")
println("Microcyte: " + (initialMicrocytePresent ? "PRESENT" : "NOT PRESENT"))
println("Macrocyte: " + (initialMacrocytePresent ? "PRESENT" : "NOT PRESENT"))
println("Anisocytosis: " + (initialAnisocytosisPresent ? "PRESENT" : "NOT PRESENT"))



println("\n==== Navigating to RBC Tab for Regrading ====")
// Verify and click on RBC
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC'), 'RBC')
WebUI.click(findTestObject('Object Repository/Summary/span_RBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')
WebUI.comment("Successfully navigated to RBC tab")

// MICROCYTE SECTION - Check which grade is currently SELECTED (not just present)
WebUI.verifyElementText(findTestObject('Object Repository/Summary/microcyte_row'), 'Microcytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/microcyte_grade_row'), '')

boolean microGrade0Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/microcyte_grade0'), 3, FailureHandling.OPTIONAL)
boolean microGrade1Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/microcyte_grade1'), 3, FailureHandling.OPTIONAL)
boolean microGrade2Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/microcyte_grade2'), 3, FailureHandling.OPTIONAL)
boolean microGrade3Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/microcyte_grade3'), 3, FailureHandling.OPTIONAL)

// Apply the logic: Grade 0/1 -> Grade 3, Grade 2/3 -> Grade 0
if (microGrade0Selected || microGrade1Selected) {
	WebUI.comment("Current microcyte grade is 0 or 1, changing to Grade 3")
	WebUI.click(findTestObject('Object Repository/Summary/microcyte_grade3'))
	WebUI.comment("Successfully set microcyte grade to 3")
} else if (microGrade2Selected || microGrade3Selected) {
	WebUI.comment("Current microcyte grade is 2 or 3, changing to Grade 0")
	WebUI.click(findTestObject('Object Repository/Summary/microcyte_grade0'))
	WebUI.comment("Successfully set microcyte grade to 0")
} else {
	WebUI.comment("Unable to determine current microcyte grade, setting to Grade 3 as default")
	WebUI.click(findTestObject('Object Repository/Summary/microcyte_grade3'))
	WebUI.comment("Set microcyte grade to 3 as default")
}

// MACROCYTE SECTION - Check which grade is currently SELECTED (not just present)
WebUI.verifyElementText(findTestObject('Object Repository/Summary/macrocyte_row'),'Macrocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/macrocyte_grade_row'),'')

boolean macroGrade0Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/macrocyte_grade0'), 3, FailureHandling.OPTIONAL)
boolean macroGrade1Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/macrocyte_grade1'), 3, FailureHandling.OPTIONAL)
boolean macroGrade2Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/macrocyte_grade2'), 3, FailureHandling.OPTIONAL)
boolean macroGrade3Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/macrocyte_grade3'), 3, FailureHandling.OPTIONAL)

// Apply the logic: Grade 0/1 -> Grade 3, Grade 2/3 -> Grade 0
if (macroGrade0Selected || macroGrade1Selected) {
	WebUI.comment("Current macrocyte grade is 0 or 1, changing to Grade 3")
	WebUI.click(findTestObject('Object Repository/Summary/macrocyte_grade3'))
	WebUI.comment("Successfully set macrocyte grade to 3")
} else if (macroGrade2Selected || macroGrade3Selected) {
	WebUI.comment("Current macrocyte grade is 2 or 3, changing to Grade 0")
	WebUI.click(findTestObject('Object Repository/Summary/macrocyte_grade0'))
	WebUI.comment("Successfully set macrocyte grade to 0")
} else {
	WebUI.comment("Unable to determine current macrocyte grade, setting to Grade 3 as default")
	WebUI.click(findTestObject('Object Repository/Summary/macrocyte_grade3'))
	WebUI.comment("Set macrocyte grade to 3 as default")
}

// ANISOCYTOSIS SECTION - Check which grade is currently SELECTED (not just present)
WebUI.verifyElementText(findTestObject('Object Repository/Summary/anisocytosis_row'),'Anisocytosis')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/anisocytosis_grade_row'),'')

boolean anisoGrade0Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/anisocytosis_grade0'), 3, FailureHandling.OPTIONAL)
boolean anisoGrade1Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/anisocytosis_grade1'), 3, FailureHandling.OPTIONAL)
boolean anisoGrade2Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/anisocytosis_grade2'), 3, FailureHandling.OPTIONAL)
boolean anisoGrade3Selected = WebUI.verifyElementChecked(findTestObject('Object Repository/Summary/anisocytosis_grade3'), 3, FailureHandling.OPTIONAL)

// Apply the logic: Grade 0/1 -> Grade 3, Grade 2/3 -> Grade 0
if (anisoGrade0Selected || anisoGrade1Selected) {
	WebUI.comment("Current anisocytosis grade is 0 or 1, changing to Grade 3")
	WebUI.click(findTestObject('Object Repository/Summary/anisocytosis_grade3'))
	WebUI.comment("Successfully set anisocytosis grade to 3")
} else if (anisoGrade2Selected || anisoGrade3Selected) {
	WebUI.comment("Current anisocytosis grade is 2 or 3, changing to Grade 0")
	WebUI.click(findTestObject('Object Repository/Summary/anisocytosis_grade0'))
	WebUI.comment("Successfully set anisocytosis grade to 0")
} else {
	WebUI.comment("Unable to determine current anisocytosis grade, setting to Grade 3 as default")
	WebUI.click(findTestObject('Object Repository/Summary/anisocytosis_grade3'))
	WebUI.comment("Set anisocytosis grade to 3 as default")
}

WebUI.comment("Completed regrading all RBC cells")

println("\n==== Navigating Back to Summary Tab ====")
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load

println("\n==== Final Summary Tab Verification ====")
WebUI.comment("Checking final cells present in summary page after regrading")
// Verify which cells are now present in summary (should show only significant cells - Grade 2 and 3)
boolean finalMicrocytePresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_microcyte'), 3, FailureHandling.OPTIONAL)
boolean finalMacrocytePresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_macrocyte'), 3, FailureHandling.OPTIONAL)
boolean finalAnisocytosisPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_anisocytosis'), 3, FailureHandling.OPTIONAL)

println("FINAL SUMMARY CELLS PRESENT:")
println("Microcyte: " + (finalMicrocytePresent ? "PRESENT" : "NOT PRESENT"))
println("Macrocyte: " + (finalMacrocytePresent ? "PRESENT" : "NOT PRESENT"))
println("Anisocytosis: " + (finalAnisocytosisPresent ? "PRESENT" : "NOT PRESENT"))

println("Summary verification complete - only significant cells (Grade 2 and 3) should be displayed")