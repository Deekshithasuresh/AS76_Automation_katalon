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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

// Open browser and login
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for the page to load after login
WebUI.waitForPageLoad(30)

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.delay(3) // Add a short delay to ensure the page loads completely

// Verify WBC differential table headers
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC differential count ()'), 'WBC differential count (%)')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_'), '%')

// Define the cell types and their expected values
def cellTypes = [
	['Neutrophils', '33.9'],
	['Lymphocytes', '-'],
	['Eosinophils', '10.7'],
	['Monocytes', '30.4'],
	['Basophils', '-'],
	['Immature Granulocytes', '-'],
	['Atypical CellsBlasts', '17.9'],
	['Immature Eosinophils', '7.1'],
	['Immature Basophils', '-'],
	['Promonocytes', '-'],
	['Prolymphocytes', '-'],
	['Hairy Cells', '-'],
	['Sezary Cells', '-'],
	['Plasma Cells', '-'],
	['Others', '-']
]

// Iterate through each cell type and verify if it's editable
cellTypes.each { cell ->
	def cellName = cell[0]
	def cellValue = cell[1]
	
	// Create a dynamic XPath to find the row with the cell name and get its value column
	def dynamicXPath = "//td[contains(text(), '" + cellName + "')]/following-sibling::td[1]"
	
	// Create a dynamic TestObject using the XPath
	TestObject dynamicTestObject = new TestObject("dynamic_" + cellName.replaceAll("\\s+", "_"))
	dynamicTestObject.addProperty("xpath", ConditionType.EQUALS, dynamicXPath)
	
	try {
		// Wait for the element to be present
		if (WebUI.waitForElementPresent(dynamicTestObject, 5, FailureHandling.OPTIONAL)) {
			// Store the original value before attempting to edit
			def originalValue = WebUI.getText(dynamicTestObject)
			println(cellName + " original value: " + originalValue)
			
			// Double-click to attempt to edit the cell
			WebUI.doubleClick(dynamicTestObject)
			WebUI.delay(1)
			
			// Check if any input field appeared after double-clicking
			TestObject inputObject = new TestObject("input_" + cellName.replaceAll("\\s+", "_"))
			inputObject.addProperty("xpath", ConditionType.EQUALS, dynamicXPath + "//input")
			
			boolean isEditable = WebUI.waitForElementPresent(inputObject, 2, FailureHandling.OPTIONAL)
			
			if (isEditable) {
				// If an input field is found, try to type something and check if value changes
				WebUI.setText(inputObject, "99.9")
				WebUI.sendKeys(inputObject, Keys.chord(Keys.ENTER))
				WebUI.delay(1)
				
				// Check if the value changed
				def newValue = WebUI.getText(dynamicTestObject)
				
				if (newValue == "99.9") {
					WebUI.comment("FAIL: " + cellName + " value is editable. Changed from " + originalValue + " to " + newValue)
					println("FAIL: " + cellName + " value is editable")
				} else {
					WebUI.comment("PASS: " + cellName + " value is not editable, as expected")
					println("PASS: " + cellName + " value is not editable, as expected")
				}
			} else {
				// No input field appeared, cell is not editable
				WebUI.comment("PASS: " + cellName + " is not editable (no input field appeared after double-click)")
				println("PASS: " + cellName + " is not editable (no input field appeared after double-click)")
			}
		} else {
			println("Element for " + cellName + " not found within timeout")
		}
	} catch (Exception e) {
		println("Error processing " + cellName + ": " + e.getMessage())
	}
}

// Close the browser
WebUI.closeBrowser()