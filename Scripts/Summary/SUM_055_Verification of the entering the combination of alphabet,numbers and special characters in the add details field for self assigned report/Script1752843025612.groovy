
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
