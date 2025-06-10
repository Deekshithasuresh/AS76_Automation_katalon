// Import static methods from Katalon libraries for finding test objects, data, and checkpoints
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
// Import Katalon classes and assign aliases for easier use
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

// Initialize a new Chrome browser instance
WebUI.openBrowser('')

// Navigate to the PBS login page
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Enter the username 'deekshithaS' in the login ID field
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

// Enter the encrypted password in the password field
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

// Press Enter to submit the login form
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Click on the report with ID SIG0004 to open it
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0004'))

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling as FailureHandling

// Define the expected non-WBC cells in their expected order
List<String> expectedNonWBCCells = [
	'NRBC',
	'Smudge Cells'
]

// Scroll to top of the page first
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)

// Scroll to ensure all cells are visible
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight/2)", null)
WebUI.delay(2)

WebUI.comment("=== VERIFYING NON-WBC CELLS PRESENCE, VALUES AND ORDER ===")

// Lists to track verification results for non-WBC cells
List<String> foundNonWBCCells = []
List<String> missingNonWBCCells = []
List<String> nonWbcCellsWithValues = []
List<String> nonWbcCellsWithDash = []
boolean nonWbcOrderCorrect = true
int currentNonWBCIndex = 0

for (String cellName : expectedNonWBCCells) {
	try {
		// Create test object to find the cell row
		TestObject cellRowObject = new TestObject()
		cellRowObject.addProperty('xpath', ConditionType.EQUALS, "//tr[td[text()='${cellName}']]")
		
		if (WebUI.verifyElementPresent(cellRowObject, 5, FailureHandling.OPTIONAL)) {
			foundNonWBCCells.add(cellName)
			
			// Get the cell value (last td in the row)
			TestObject cellValueObject = new TestObject()
			cellValueObject.addProperty('xpath', ConditionType.EQUALS, "//tr[td[text()='${cellName}']]/td[last()]")
			
			WebUI.waitForElementPresent(cellValueObject, 5)
			WebUI.scrollToElement(cellValueObject, 3)
			String cellValue = WebUI.getText(cellValueObject).trim()
			
			// Check if value is present (either numeric value or dash)
			if (cellValue.equals("-")) {
				nonWbcCellsWithDash.add(cellName)
				WebUI.comment("✓ ${cellName} - Present with dash value: ${cellValue}")
			} else if (!cellValue.isEmpty()) {
				nonWbcCellsWithValues.add(cellName)
				WebUI.comment("✓ ${cellName} - Present with calculated value: ${cellValue}")
			} else {
				WebUI.comment("⚠ ${cellName} - Present but no value found")
			}
			
			// Simple order verification by checking if previous expected cell exists above current cell
			if (currentNonWBCIndex > 0) {
				String previousCell = expectedNonWBCCells.get(currentNonWBCIndex - 1)
				TestObject orderCheckObject = new TestObject()
				orderCheckObject.addProperty('xpath', ConditionType.EQUALS,
					"//tr[td[text()='${previousCell}']]/following-sibling::tr[td[text()='${cellName}']]")
				
				if (!WebUI.verifyElementPresent(orderCheckObject, 3, FailureHandling.OPTIONAL)) {
					nonWbcOrderCorrect = false
					WebUI.comment("⚠ ${cellName} - Order issue: Not found after ${previousCell}")
				}
			}
			
		} else {
			missingNonWBCCells.add(cellName)
			WebUI.comment("✗ ${cellName} - NOT FOUND")
		}
		
		currentNonWBCIndex++
		
	} catch (Exception e) {
		missingNonWBCCells.add(cellName)
		WebUI.comment("✗ ${cellName} - ERROR: ${e.getMessage()}")
	}
}

// Scroll to show complete results
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)", null)
WebUI.delay(3)

// Generate comprehensive report
WebUI.comment("=== NON-WBC CELLS VERIFICATION RESULTS SUMMARY ===")
WebUI.comment("Total expected non-WBC cells: ${expectedNonWBCCells.size()}")
WebUI.comment("Non-WBC cells found: ${foundNonWBCCells.size()}")
WebUI.comment("Non-WBC cells missing: ${missingNonWBCCells.size()}")
WebUI.comment("Non-WBC cells with calculated values: ${nonWbcCellsWithValues.size()}")
WebUI.comment("Non-WBC cells with dash (-): ${nonWbcCellsWithDash.size()}")

WebUI.comment("=== DETAILED BREAKDOWN ===")

if (!foundNonWBCCells.isEmpty()) {
	WebUI.comment("✓ FOUND NON-WBC CELLS (${foundNonWBCCells.size()}):")
	for (String cell : foundNonWBCCells) {
		WebUI.comment("  - ${cell}")
	}
}

if (!missingNonWBCCells.isEmpty()) {
	WebUI.comment("✗ MISSING NON-WBC CELLS (${missingNonWBCCells.size()}):")
	for (String cell : missingNonWBCCells) {
		WebUI.comment("  - ${cell}")
	}
}

if (!nonWbcCellsWithValues.isEmpty()) {
	WebUI.comment("📊 NON-WBC CELLS WITH CALCULATED VALUES (${nonWbcCellsWithValues.size()}):")
	for (String cell : nonWbcCellsWithValues) {
		WebUI.comment("  - ${cell}")
	}
}

if (!nonWbcCellsWithDash.isEmpty()) {
	WebUI.comment("➖ NON-WBC CELLS WITH DASH VALUES (${nonWbcCellsWithDash.size()}):")
	for (String cell : nonWbcCellsWithDash) {
		WebUI.comment("  - ${cell}")
	}
}

// Order verification summary
WebUI.comment("=== ORDER VERIFICATION ===")
if (nonWbcOrderCorrect) {
	WebUI.comment("✓ All found non-WBC cells are in correct order")
} else {
	WebUI.comment("⚠ Some non-WBC cells are not in expected order - check individual cell comments above")
}

// Final status
WebUI.comment("=== FINAL STATUS ===")
if (missingNonWBCCells.isEmpty() && nonWbcOrderCorrect) {
	WebUI.comment("🎉 SUCCESS: All non-WBC cells present with values/dashes and in correct order!")
} else if (missingNonWBCCells.isEmpty()) {
	WebUI.comment("⚠ PARTIAL SUCCESS: All non-WBC cells present with values but order issues detected")
} else {
	WebUI.comment("❌ ISSUES DETECTED: Missing non-WBC cells or order problems found")
}

// Final scroll to top for user convenience
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)