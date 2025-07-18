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
//WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0004'))

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')


import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling as FailureHandling
// Define the expected cells in their expected order
List<String> expectedCellsInOrder = [
	'Neutrophils',
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
	'Others',
	'NRBC',
	'Smudge Cells'
]

// Scroll to top of the page first
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)

// Scroll to ensure all cells are visible
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight/2)", null)
WebUI.delay(2)

WebUI.comment("=== VERIFYING CELL PRESENCE, VALUES AND ORDER ===")

// Lists to track verification results
List<String> foundCells = []
List<String> missingCells = []
List<String> cellsWithValues = []
List<String> cellsWithDash = []
boolean orderCorrect = true
int currentIndex = 0

for (String cellName : expectedCellsInOrder) {
	try {
		// Create test object to find the cell row
		TestObject cellRowObject = new TestObject()
		cellRowObject.addProperty('xpath', ConditionType.EQUALS, "//tr[td[text()='${cellName}']]")
		
		if (WebUI.verifyElementPresent(cellRowObject, 5, FailureHandling.OPTIONAL)) {
			foundCells.add(cellName)
			
			// Get the cell value (last td in the row)
			TestObject cellValueObject = new TestObject()
			cellValueObject.addProperty('xpath', ConditionType.EQUALS, "//tr[td[text()='${cellName}']]/td[last()]")
			
			WebUI.waitForElementPresent(cellValueObject, 5)
			WebUI.scrollToElement(cellValueObject, 3)
			String cellValue = WebUI.getText(cellValueObject).trim()
			
			// Check if value is present (either numeric value or dash)
			if (cellValue.equals("-")) {
				cellsWithDash.add(cellName)
				WebUI.comment("✓ ${cellName} - Present with dash value: ${cellValue}")
			} else if (!cellValue.isEmpty()) {
				cellsWithValues.add(cellName)
				WebUI.comment("✓ ${cellName} - Present with calculated value: ${cellValue}")
			} else {
				WebUI.comment("⚠ ${cellName} - Present but no value found")
			}
			
			// Simple order verification by checking if previous expected cell exists above current cell
			if (currentIndex > 0) {
				String previousCell = expectedCellsInOrder.get(currentIndex - 1)
				TestObject orderCheckObject = new TestObject()
				orderCheckObject.addProperty('xpath', ConditionType.EQUALS,
					"//tr[td[text()='${previousCell}']]/following-sibling::tr[td[text()='${cellName}']]")
				
				if (!WebUI.verifyElementPresent(orderCheckObject, 3, FailureHandling.OPTIONAL)) {
					orderCorrect = false
					WebUI.comment("⚠ ${cellName} - Order issue: Not found after ${previousCell}")
				}
			}
			
		} else {
			missingCells.add(cellName)
			WebUI.comment("✗ ${cellName} - NOT FOUND")
		}
		
		currentIndex++
		
	} catch (Exception e) {
		missingCells.add(cellName)
		WebUI.comment("✗ ${cellName} - ERROR: ${e.getMessage()}")
	}
}

// Scroll to show complete results
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)", null)
WebUI.delay(3)

// Generate comprehensive report
WebUI.comment("=== VERIFICATION RESULTS SUMMARY ===")
WebUI.comment("Total expected cells: ${expectedCellsInOrder.size()}")
WebUI.comment("Cells found: ${foundCells.size()}")
WebUI.comment("Cells missing: ${missingCells.size()}")
WebUI.comment("Cells with calculated values: ${cellsWithValues.size()}")
WebUI.comment("Cells with dash (-): ${cellsWithDash.size()}")

WebUI.comment("=== DETAILED BREAKDOWN ===")

if (!foundCells.isEmpty()) {
	WebUI.comment("✓ FOUND CELLS (${foundCells.size()}):")
	for (String cell : foundCells) {
		WebUI.comment("  - ${cell}")
	}
}

if (!missingCells.isEmpty()) {
	WebUI.comment("✗ MISSING CELLS (${missingCells.size()}):")
	for (String cell : missingCells) {
		WebUI.comment("  - ${cell}")
	}
}

if (!cellsWithValues.isEmpty()) {
	WebUI.comment("📊 CELLS WITH CALCULATED VALUES (${cellsWithValues.size()}):")
	for (String cell : cellsWithValues) {
		WebUI.comment("  - ${cell}")
	}
}

if (!cellsWithDash.isEmpty()) {
	WebUI.comment("➖ CELLS WITH DASH VALUES (${cellsWithDash.size()}):")
	for (String cell : cellsWithDash) {
		WebUI.comment("  - ${cell}")
	}
}

// Order verification summary
WebUI.comment("=== ORDER VERIFICATION ===")
if (orderCorrect) {
	WebUI.comment("✓ All found cells are in correct order")
} else {
	WebUI.comment("⚠ Some cells are not in expected order - check individual cell comments above")
}

// Final status
WebUI.comment("=== FINAL STATUS ===")
if (missingCells.isEmpty() && orderCorrect) {
	WebUI.comment("🎉 SUCCESS: All cells present with values/dashes and in correct order!")
} else {
	WebUI.comment("❌ ISSUES DETECTED: Missing cells or order problems found")
}

// Final scroll to top for user convenience
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)