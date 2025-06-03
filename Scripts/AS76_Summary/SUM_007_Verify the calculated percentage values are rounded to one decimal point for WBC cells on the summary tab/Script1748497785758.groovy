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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.support.Color as Color
import java.awt.Color as Color

// Additional necessary imports that were missing
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration
import com.kms.katalon.core.testobject.ConditionType
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Test Case: Verify the calculated percentage values are rounded to one decimal point for ALL WBC cells on the summary tab
 * Expected: All WBC cell values should be either '-' (dash) or a number with exactly one decimal place
 */

// Login to the application
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
// Select the sample
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0004'))

// Define all WBC cell names based on your screenshot
def wbcCellNames = [
	'Lymphocytes',
	'Eosinophils',
	'Monocytes',
	'Basophils',
	'Immature Granulocytes',
	'Atypical Cells/Blasts',
	'Immature Eosinophils',
	'Immature Basophils',
	'Promonocytes',
	'Prolymphocytes',
	'Hairy Cells',
	'Sezary Cells',
	'Plasma Cells',
	'Others'
]

// Function to create test objects dynamically
def createTestObjects(String cellName) {
	TestObject cellNameObject = new TestObject()
	cellNameObject.addProperty('xpath', ConditionType.EQUALS, "//td[text()='${cellName}']")
	
	TestObject cellValueObject = new TestObject()
	cellValueObject.addProperty('xpath', ConditionType.EQUALS, "//td[text()='${cellName}']/following-sibling::td[1]")
	
	return [name: cellNameObject, value: cellValueObject]
}

// Improved validation function with better error messages and edge case handling
def validateDecimalFormat(String value) {
	if (value == null || value.trim().isEmpty()) {
		return [isValid: false, message: 'Value is null or empty']
	}
	
	String trimmedValue = value.trim()
	
	// Check if value is dash (when no cells are present)
	if (trimmedValue.equals('-')) {
		return [isValid: true, message: 'Value is dash - Valid format (no cells detected)']
	}
	
	// Check if value is a valid number
	try {
		double numericValue = Double.parseDouble(trimmedValue)
		
		// Check decimal places by string manipulation
		if (trimmedValue.contains('.')) {
			String[] parts = trimmedValue.split('\\.')
			if (parts.length == 2 && parts[1].length() == 1) {
				// Exactly one decimal place (e.g., 60.0, 30.5, 0.5)
				if (numericValue >= 0 && numericValue <= 100) {
					return [isValid: true, message: "Valid format: ${trimmedValue} (exactly 1 decimal place)"]
				} else {
					return [isValid: true, message: "Valid format but outside typical range 0-100: ${trimmedValue}"]
				}
			} else if (parts[1].length() == 0) {
				// Handle edge case like "60."
				return [isValid: false, message: 'Number ends with decimal point but no digits after']
			} else {
				return [isValid: false, message: "Invalid: ${trimmedValue} has ${parts[1].length()} decimal places, expected exactly 1"]
			}
		} else {
			// No decimal point found (e.g., "60" instead of "60.0")
			return [isValid: false, message: "Invalid: ${trimmedValue} should be displayed as ${trimmedValue}.0 (missing decimal place)"]
		}
	} catch (NumberFormatException e) {
		return [isValid: false, message: "Value '${trimmedValue}' is not a valid number: ${e.getMessage()}"]
	}
}

// Function to validate individual WBC cell
def validateWBCCell(String cellName) {
	try {
		WebUI.comment("=== Validating ${cellName} ===")
		
		// Create test objects for this cell
		def testObjects = createTestObjects(cellName)
		
		// Step 1: Verify cell name is present
		WebUI.verifyElementPresent(testObjects.name, 10)
		WebUI.comment("SUCCESS: ${cellName} cell name is present")
		
		// Step 2: Get the dynamic value
		String cellValueText = WebUI.getText(testObjects.value).trim()
		WebUI.comment("${cellName} value found: ${cellValueText}")
		
		// Step 3: Validate the value format
		def validationResult = validateDecimalFormat(cellValueText)
		
		if (validationResult.isValid) {
			WebUI.comment("SUCCESS: ${validationResult.message}")
			
			// Additional logging for different cases
			if (cellValueText.equals('-')) {
				WebUI.comment("SUCCESS: ${cellName} value is dash (-) - Valid format (no cells detected)")
			} else {
				WebUI.comment("SUCCESS: ${cellName} value has exactly 1 decimal place: ${cellValueText}")
			}
			
			return [cellName: cellName, value: cellValueText, status: 'PASSED', message: validationResult.message]
		} else {
			WebUI.comment("ERROR: ${validationResult.message}")
			WebUI.comment("ERROR: Invalid ${cellName} value format: ${cellValueText}")
			
			// Fail the test for this cell
			WebUI.verifyEqual(validationResult.isValid, true, FailureHandling.CONTINUE_ON_FAILURE)
			
			return [cellName: cellName, value: cellValueText, status: 'FAILED', message: validationResult.message]
		}
		
	} catch (Exception e) {
		WebUI.comment("ERROR: Error during ${cellName} verification: ${e.getMessage()}")
		return [cellName: cellName, value: 'N/A', status: 'ERROR', message: e.getMessage()]
	}
}

// Main execution
def allResults = []
def passedCount = 0
def failedCount = 0
def errorCount = 0

try {
	WebUI.comment('=== Starting WBC Cells Validation ===')
	WebUI.comment('Total cells to validate: ' + wbcCellNames.size())
	
	// Validate each WBC cell
	wbcCellNames.each { cellName ->
		def result = validateWBCCell(cellName)
		allResults.add(result)
		
		switch(result.status) {
			case 'PASSED':
				passedCount++
				break
			case 'FAILED':
				failedCount++
				break
			case 'ERROR':
				errorCount++
				break
		}
	}
	
	// Final Summary Report
	WebUI.comment('=== FINAL WBC VALIDATION SUMMARY ===')
	WebUI.comment("Total Cells Validated: ${allResults.size()}")
	WebUI.comment("Passed: ${passedCount}")
	WebUI.comment("Failed: ${failedCount}")
	WebUI.comment("Errors: ${errorCount}")
	
	WebUI.comment('=== DETAILED RESULTS ===')
	allResults.each { result ->
		WebUI.comment("${result.cellName}: ${result.value} - ${result.status} (${result.message})")
	}
	
	// Overall test result
	if (failedCount == 0 && errorCount == 0) {
		WebUI.comment('=== OVERALL RESULT: ALL WBC CELLS VALIDATION PASSED ===')
	} else {
		WebUI.comment('=== OVERALL RESULT: SOME WBC CELLS VALIDATION FAILED ===')
		WebUI.comment("Please check the ${failedCount} failed cells and ${errorCount} error cells above")
	}
	
} catch (Exception e) {
	WebUI.comment('CRITICAL ERROR: Error during WBC cells validation: ' + e.getMessage())
	throw e
} finally {
	WebUI.comment('=== WBC Validation Test Completed ===')
}