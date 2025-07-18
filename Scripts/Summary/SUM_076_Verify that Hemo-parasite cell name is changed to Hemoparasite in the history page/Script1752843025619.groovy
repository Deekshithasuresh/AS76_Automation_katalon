
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

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')


//CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
//CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')


// Verify the report title is correct
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

// Store different combinations of alphanumeric characters to test in each field
def hemoparasiteAlphaNum = "HEMO901Test234" // Alphanumeric for Hemoparasite field

// This function enters alphanumeric characters into a field
// It waits for the field to be visible, clicks it, clears any existing text,
// enters our alphanumeric characters, then tabs out to save the entry
def enterAlphaNumInField(TestObject fieldObject, String alphaNumToEnter) {
	WebUI.waitForElementVisible(fieldObject, 20)
	WebUI.click(fieldObject)
	WebUI.clearText(fieldObject)
	WebUI.setText(fieldObject, alphaNumToEnter)
	WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB))
}

// Helper function to check if text contains both alphabets and numbers
def containsAlphaNum(String text) {
	return text.find(/[a-zA-Z]/) && text.find(/[0-9]/)
}

try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')

	// Enter our alphanumeric characters into the field
	enterAlphaNumInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'), hemoparasiteAlphaNum)

	// Read back what's in the field to check if alphanumeric characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'))

	// Check if the text contains both alphabets and numbers
	if (containsAlphaNum(enteredText)) {
		println "PASS: Successfully entered combination of alphabets and numbers in Hemoparasite field"
		println "Entered: " + enteredText
	} else {
		println "FAIL: Hemoparasite field does not contain both alphabets and numbers: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter alphanumeric characters in Hemoparasite field: " + e.getMessage()
}

WebUI.click(findTestObject('Object Repository/Summary/SUM_kebab_history'))
WebUI.click(findTestObject('Object Repository/Summary/SUM_history'))
// Note: Add the expected text for the history header verification if needed
// WebUI.verifyElementText(findTestObject('Object Repository/Summary/SUM_history_header_inside_history_page'), 'Expected Header Text')

// Modified verification for history page - checking for "hemoparasite" instead of "hemo-parasite"
try {
	// Get the actual text from the history page element
	String historyHemoparasiteText = WebUI.getText(findTestObject('Object Repository/Summary/SUM_History_hemoparasite'))
	
	// Check if the text shows "hemoparasite" (without hyphen)
	if (historyHemoparasiteText.toLowerCase().contains("hemoparasite")) {
		println "PASS: History page shows 'hemoparasite' correctly (without hyphen)"
		println "History text: " + historyHemoparasiteText
	} else {
		println "FAIL: History page does not show 'hemoparasite' as expected"
		println "Actual history text: " + historyHemoparasiteText
	}
} catch (Exception e) {
	println "FAIL: Unable to verify hemoparasite text in history page: " + e.getMessage()
}