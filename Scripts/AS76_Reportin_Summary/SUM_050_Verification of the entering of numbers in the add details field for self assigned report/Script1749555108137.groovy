
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

// Define text entries for each morphology field
def rbcMorphologyText = "23456789076543245678"
def wbcMorphologyText = " 8765432567890876543256789"
def plateletMorphologyText = "87654321345678908765432"
def hemoparasiteText = "9876543214567890-87654324567890"
def impressionText = "98765432134567890-876543"

// Function to handle text entry for each section
def enternumberInField(TestObject fieldObject, String textToEnter) {
	WebUI.waitForElementVisible(fieldObject, 20)
	WebUI.click(fieldObject)
	WebUI.clearText(fieldObject)
	WebUI.setText(fieldObject, textToEnter)
	WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB)) // Tab out to save the entry
}

// Process each field one by one, continuing to the next even if previous fails
// RBC Morphology
try {
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')
	enternumberInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_RBC_Morphology'), rbcMorphologyText)
	println "PASS: Successfully entered text in RBC Morphology field"
} catch (Exception e) {
	println "FAIL: Unable to enter text in RBC Morphology field: " + e.getMessage()
}

// WBC Morphology - Continue regardless of RBC result
try {
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology (1)'), 'WBC Morphology')
	enternumberInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_WBC_Morphology'), wbcMorphologyText)
	println "PASS: Successfully entered text in WBC Morphology field"
} catch (Exception e) {
	println "FAIL: Unable to enter text in WBC Morphology field: " + e.getMessage()
}

// Platelet Morphology - Continue regardless of previous results
try {
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology (1)'), 'Platelet Morphology')
	enternumberInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Platelet_Morphology'), plateletMorphologyText)
	println "PASS: Successfully entered text in Platelet Morphology field"
} catch (Exception e) {
	println "FAIL: Unable to enter text in Platelet Morphology field: " + e.getMessage()
}

// Hemoparasite - Continue regardless of previous results
try {
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')
	enternumberInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Hemoparasite'), hemoparasiteText)
	println "PASS: Successfully entered text in Hemoparasite field"
} catch (Exception e) {
	println "FAIL: Unable to enter text in Hemoparasite field: " + e.getMessage()
}

// Impression - Continue regardless of previous results
try {
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')
	enternumberInField(findTestObject('Object Repository/Summary/div_PS_Impression_Add_detail_Impression'), impressionText)
	println "PASS: Successfully entered text in Impression field"
} catch (Exception e) {
	println "FAIL: Unable to enter text in Impression field: " + e.getMessage()
}
