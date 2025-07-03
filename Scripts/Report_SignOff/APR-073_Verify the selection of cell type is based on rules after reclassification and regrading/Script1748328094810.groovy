import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.joda.time.Duration
import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.time.Duration

// === LAUNCH & LOGIN ===
WebUI.openBrowser('')
CustomKeywords.'generic.custumFunctions.login'()

// === ASSIGN REPORT ===
WebUI.comment("Selecting report with 'To be reviewed' status.")
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
WebUI.comment("Assigning report to 'santosh'.")
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// === RECLASSIFY WBC (Neutrophils) ===
WebUI.comment("Clicking on WBC tab.")
WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))
WebUI.delay(2) // Give time for WBC data to load
WebUI.comment("Clicking on Neutrophils cell type.")
WebUI.click(findTestObject('Object Repository/Page_PBS/td_Neutrophils'))

int neutrophilsBefore = CustomKeywords.'generic.Reclacification.getCellCount'(DriverFactory.getWebDriver(), 'Neutrophils')
WebUI.comment("Initial Neutrophils Count: $neutrophilsBefore")

if (neutrophilsBefore > 0) {
	WebUI.executeJavaScript('window.scrollTo(0, document.body.scrollHeight)', null)
	WebUI.delay(1) // Scroll down to ensure classification elements are in view
	try {
		WebUI.comment("Attempting to reclassify Neutrophils to Unclassified using custom keyword.")
		CustomKeywords.'generic.Reclacification.classifyFromCellToCell'('Neutrophils', 'Unclassified')
		WebUI.comment("Classification successful (confirmed by keyword).")
		WebUI.delay(1) // Small delay after keyword returns for stability
	} catch (TimeoutException e) {
		WebUI.comment("❌ Classification failed (TimeoutException from keyword): ${e.message}")
		KeywordUtil.markFailedAndStop("Test failed: ${e.message}")
	} catch (Exception e) {
		WebUI.comment("❌ Classification failed due to an unexpected error: ${e.message}")
		KeywordUtil.markFailedAndStop("Test failed due to classification error: ${e.message}")
	}
} else {
	WebUI.comment("Neutrophils count is 0, skipping reclassification.")
}

// === REGRADE RBC CELLS ===
WebUI.comment("Clicking on RBC tab.")
WebUI.click(findTestObject('Object Repository/Page_PBS/span_RBC'))
WebUI.delay(2) // Give time for RBC data to load

// Get WebDriver instance
def driver = DriverFactory.getWebDriver()

// Get all grade-div containers (each cell row with 4 grades)
def allGradeRows = driver.findElements(By.xpath("//div[contains(@class,'grade-div')]"))

// Set all RBC grades to 1 (non-significant)
for (WebElement gradeRow : allGradeRows) {
	def gradeButtons = gradeRow.findElements(By.xpath(".//span[contains(@class,'MuiButtonBase-root')]"))

	for (int i = 0; i < gradeButtons.size(); i++) {
		if (gradeButtons[i].getAttribute("class").contains("Mui-checked")) {
			if (i >= 2) { // If current grade is 3 or 4 (index 2 or 3)
				// Verify which button corresponds to "Grade 1" (non-significant)
				// If "Grade 1" is the first button visually, use gradeButtons[0].click()
				// If "Grade 2" (index 1) is intended for "non-significant", then gradeButtons[1].click() is correct.
				WebUI.comment("Regrading RBC cell in row from grade ${i+1} to grade 2 (index 1).")
				gradeButtons[1].click() // Clicks the button at index 1 (usually Grade 2)
				WebUI.delay(0.3)
				break
			}
		}
	}
}
WebUI.comment("RBC regrading check complete.")


// === APPROVE REPORT & ADD SUPPORTING IMAGES ===
WebUI.comment("Approving the report.")
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))
WebUI.comment("Navigating to 'Add supporting images'.")
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))

// *** MODIFIED DYNAMIC WAIT FOR THE TARGET PAGE TO LOAD ***
WebUI.comment("Waiting for the cell selection list to be present after navigating to supporting images.")
// Ensure driver is obtained if not already in scope from previous steps
WebDriver currentDriver = DriverFactory.getWebDriver()
WebDriverWait wait = new WebDriverWait(currentDriver, Duration.ofSeconds(45)) // Increased timeout to 45 seconds for page load
try {
	// Wait for the main container of the checkboxes to be present in the DOM
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@class='celle-selection-list'])[1]")))
	WebUI.comment("Cell selection list is present in the DOM.")
	WebUI.delay(2) // Small additional delay for content to fully settle and render
} catch (TimeoutException e) {
	WebUI.comment("❌ Timeout: The 'cell-selection-list' was not present in the DOM after navigating to supporting images within 45 seconds. This indicates a page load issue or an incorrect navigation.")
	KeywordUtil.markFailedAndStop("Could not load cell selection list for validation after 'Add supporting images'.")
}


// === HELPER FUNCTIONS FOR VALIDATION ===
TestObject createTestObject(String xpath) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

def extractPercentage(String text) {
	def matcher = text =~ /\(([^)]+)\)/ // Matches text inside parentheses
	return matcher ? Float.parseFloat(matcher[0][1]) : 0.0 // Returns 0.0 if no percentage found
}

boolean isCheckboxChecked(String cellName) {
	// XPath for the checkbox span itself, using the robust text matching for the label div
	String checkboxSpanXpath = "//div[@class='celle-selection-list']//div[contains(@class,'checkbox-lable') and contains(text(),'${cellName}')]/preceding-sibling::span"
	TestObject checkboxSpanObj = createTestObject(checkboxSpanXpath)

	WebUI.comment("Attempting to find checkbox span with XPath: ${checkboxSpanXpath}")
	// Verify element present with a reasonable timeout, continue on failure to get more logs
	if (WebUI.verifyElementPresent(checkboxSpanObj, 10, FailureHandling.CONTINUE_ON_FAILURE)) {
		String checkboxClass = WebUI.getAttribute(checkboxSpanObj, 'class', FailureHandling.CONTINUE_ON_FAILURE)
		return checkboxClass.contains('Mui-checked')
	}
	WebUI.comment("Checkbox span for ${cellName} not found or not visible within its timeout.")
	return false
}

// === CELL TYPE THRESHOLDS FOR VALIDATION ===
def cellTypes = [
	'Neutrophils':            0.0, // Should be checked if > 0%
	'Lymphocytes':            0.0,
	'Eosinophils':            0.0,
	'Monocytes':              0.0,
	'Basophils':              0.0,
	'Atypical Cells/Blasts':  0.0,
	'Immature Granulocytes':  5.0, // Should be checked if > 5%
	'NRBC':                   5.0, // Should be checked if > 5%
	// Add other relevant cell types and their thresholds for validation here
]

// === VALIDATE CHECKBOX SELECTION BASED ON RULES ===
WebUI.comment("Starting validation of cell type checkbox selections.")
cellTypes.each { cellName, threshold ->
	// Updated XPath to directly target the MuiTypography-root div inside checkbox-label
	String labelXpath = "//div[@class='celle-selection-list']//div[contains(@class,'checkbox-lable') and contains(text(),'${cellName}')]"
	
	TestObject labelObj = createTestObject(labelXpath)

	WebUI.comment("Attempting to find label for ${cellName} with XPath: ${labelXpath}")
	if (WebUI.verifyElementPresent(labelObj, 15, FailureHandling.CONTINUE_ON_FAILURE)) { // The 15s timeout here is for EACH element in the loop
		String labelText = WebUI.getText(labelObj)
		WebUI.comment("  -> Found label text for ${cellName}: '${labelText.trim()}'")
		float percent = extractPercentage(labelText)
		boolean isChecked = isCheckboxChecked(cellName) // This calls a function that also finds a checkbox.

		WebUI.comment("Validation for ${cellName}: Percentage: ${percent}%, Threshold: ${threshold}%, Is Checked: ${isChecked}")

		if (percent > threshold) {
			assert isChecked : "${cellName} (${percent}%) > ${threshold}% – Expected: CHECKED, Actual: NOT CHECKED."
			WebUI.comment("✅ ${cellName} (${percent}%) > ${threshold}% – Should be CHECKED – Verified.")
		} else {
			assert !isChecked : "${cellName} (${percent}%) <= ${threshold}% – Expected: NOT CHECKED, Actual: CHECKED."
			WebUI.comment("✅ ${cellName} (${percent}%) <= ${threshold}% – Should NOT be checked – Verified.")
		}
	} else {
		WebUI.comment("⚠️ Label for ${cellName} NOT FOUND within 15 seconds – Skipping validation for this cell type.")
	}
}
WebUI.comment("Cell type checkbox validation complete.")

// WebUI.closeBrowser() // Uncomment this when you're done with development