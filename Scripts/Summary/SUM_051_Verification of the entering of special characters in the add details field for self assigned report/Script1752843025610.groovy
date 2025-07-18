
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



// Open the Peripheral Smear Report page
// (assuming you have a navigation step before this)

// Verify the report title is correct
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

// Store different sets of special characters to test in each field
def rbcMorphologySpecial = "@#%^&*"         // Common symbols
def wbcMorphologySpecial = "!~<>?:{}"        // Punctuation and brackets
def plateletMorphologySpecial = "()[]/\\"    // More brackets and slashes
def hemoparasiteSpecial = "+=_-;'\""         // Math symbols and quotes
def impressionSpecial = "|€£¥©®™"            // Currency and trademark symbols

// This function enters special characters into a field
// It waits for the field to be visible, clicks it, clears any existing text,
// enters our special characters, then tabs out to save the entry
def enterSpecialCharInField(TestObject fieldObject, String specialCharsToEnter) {
	WebUI.waitForElementVisible(fieldObject, 20)
	WebUI.click(fieldObject)
	WebUI.clearText(fieldObject)
	WebUI.setText(fieldObject, specialCharsToEnter)
	WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB)) // Tab out to save the entry
}

// This function checks if the text contains at least one special character
// It returns true if there's at least one character that isn't a letter, number, or space
// This helps us verify that special characters were actually saved in the field
def containsSpecialChars(String text) {
	return text.trim().matches(".*[^a-zA-Z0-9\\s].*")
}

// First field: RBC Morphology - Try to enter special characters
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')
	
	// Enter our special characters into the field
	enterSpecialCharInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_RBC_Morphology'), rbcMorphologySpecial)
	
	// Read back what's in the field to check if special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_RBC_Morphology'))
	
	// Check if the text contains at least one special character
	if (containsSpecialChars(enteredText)) {
		println "PASS: Successfully entered special characters in RBC Morphology field"
	} else {
		println "FAIL: RBC Morphology field does not contain special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter special characters in RBC Morphology field: " + e.getMessage()
}

// Second field: WBC Morphology - Continue even if the first one failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology (1)'), 'WBC Morphology')
	
	// Enter our special characters into the field
	enterSpecialCharInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_WBC_Morphology'), wbcMorphologySpecial)
	
	// Read back what's in the field to check if special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_WBC_Morphology'))
	
	// Check if the text contains at least one special character
	if (containsSpecialChars(enteredText)) {
		println "PASS: Successfully entered special characters in WBC Morphology field"
	} else {
		println "FAIL: WBC Morphology field does not contain special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter special characters in WBC Morphology field: " + e.getMessage()
}

// Third field: Platelet Morphology - Continue even if previous fields failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology (1)'), 'Platelet Morphology')
	
	// Enter our special characters into the field
	enterSpecialCharInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Platelet_Morphology'), plateletMorphologySpecial)
	
	// Read back what's in the field to check if special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Platelet_Morphology'))
	
	// Check if the text contains at least one special character
	if (containsSpecialChars(enteredText)) {
		println "PASS: Successfully entered special characters in Platelet Morphology field"
	} else {
		println "FAIL: Platelet Morphology field does not contain special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter special characters in Platelet Morphology field: " + e.getMessage()
}

// Fourth field: Hemoparasite - Continue even if previous fields failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite '), 'Hemoparasite')
	
	// Enter our special characters into the field
	enterSpecialCharInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'), hemoparasiteSpecial)
	
	// Read back what's in the field to check if special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'))
	
	// Check if the text contains at least one special character
	if (containsSpecialChars(enteredText)) {
		println "PASS: Successfully entered special characters in Hemoparasite field"
	} else {
		println "FAIL: Hemoparasite field does not contain special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter special characters in Hemoparasite field: " + e.getMessage()
}

// Fifth field: Impression - Continue even if previous fields failed
try {
	// Check if the field label is correct
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')
	
	// Enter our special characters into the field
	enterSpecialCharInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Impression'), impressionSpecial)
	
	// Read back what's in the field to check if special characters were saved
	String enteredText = WebUI.getText(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Impression'))
	
	// Check if the text contains at least one special character
	if (containsSpecialChars(enteredText)) {
		println "PASS: Successfully entered special characters in Impression field"
	} else {
		println "FAIL: Impression field does not contain special characters: " + enteredText
	}
} catch (Exception e) {
	// If anything goes wrong, print an error message but continue to the next field
	println "FAIL: Unable to enter special characters in Impression field: " + e.getMessage()
}
