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

// Add these missing imports
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper

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

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// Wait for the table to be visible
//WebUI.waitForElementVisible(findTestObject('Object Repository/Summary/Page_PBS (1)/table'), 30)

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// Verify and click on RBC
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC'), 'RBC')
WebUI.click(findTestObject('Object Repository/Summary/span_RBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Size'), 'Size')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/microcyte_row'),'Microcytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/microcyte_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/microcyte_grade0'))
	
WebUI.verifyElementText(findTestObject('Object Repository/Summary/macrocyte_row'),'Macrocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/macrocyte_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/macrocyte_grade0'))


WebUI.verifyElementText(findTestObject('Object Repository/Summary/anisocytosis_row'),'Anisocytosis')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/anisocytosis_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/anisocytosis_grade0'))




// Now navigate to Summary tab and verify presence of cells based on significance
println("\n==== Navigating to Summary Tab ====")
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load



// Simple verification that non-significant cells are not present
println("\n==== Verifying non-significant cells are NOT present on Summary page ====")

// Verify Microcytes are not present
boolean microcytesNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/017_sum_microcyte_NonS'), 2, FailureHandling.OPTIONAL)
println(microcytesNotPresent ? "PASS: Microcytes correctly not displayed in the summary tab(non-significant)" : "FAIL: Microcytes incorrectly displayed in the summary tab despite being non-significant")

// Verify Macrocytes are not present
boolean macrocytesNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/017_sum_macrocyte_NonS'), 2, FailureHandling.OPTIONAL)
println(macrocytesNotPresent ? "PASS: Macrocytes correctly not displayed in the summary tab (non-significant)" : "FAIL: Macrocytes incorrectly displayed in the summary tab despite being non-significant")

// Verify Anisocytosis is not present
boolean anisocytosisNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/017_sum_Anisocytosis_NonS'), 2, FailureHandling.OPTIONAL)
println(anisocytosisNotPresent ? "PASS: Anisocytosis correctly not displayed in the summary tab (non-significant)" : "FAIL: Anisocytosis incorrectly displayed in the summary tab despite being non-significant")

// Overall verification
if (microcytesNotPresent && macrocytesNotPresent && anisocytosisNotPresent) {
	println("\nOVERALL RESULT: PASS - All non-significant RBC size cells are correctly not displayed on the summary page")
} else {
	println("\nOVERALL RESULT: FAIL - Some non-significant RBC size cells are incorrectly displayed on the summary page")
}

//WebUI.verifyElementText(findTestObject('Object Repository/Summary/016_sum_microcyte'),'')
//WebUI.verifyElementText(findTestObject('Object Repository/Summary/016_sum_microcyte'),'')
//WebUI.verifyElementText(findTestObject('Object Repository/Summary/016_sum_microcyte'),'')











//// Get the WebDriver
//WebDriver driver = DriverFactory.getWebDriver()
//JavascriptExecutor js = (JavascriptExecutor) driver


