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

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// Verify and click on RBC
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC'), 'RBC')
WebUI.click(findTestObject('Object Repository/Summary/span_RBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Shape'), 'Shape')
WebUI.click(findTestObject('Object Repository/Summary/button_Shape'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')

println("\n========================================================================================")

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Ovalocytes_row'),'Ovalocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Ovalocytes_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Ovalocytes_grade0'))
	
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Elliptocytes_row'),'Elliptocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Elliptocytes_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Elliptocytes_grade0'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Teardrop Cells_row'),'Teardrop Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Teardrop Cells_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Teardrop Cells_grade0'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Fragmented_Cells_row'),'Fragmented Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Fragmented Cells_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Fragmented Cells_grade0'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Target_Cells_row'),'Target Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Target_Cells_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Target_cells_grade0'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Echinocytes_cells_row'),'Echinocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Echinocytes_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Echinocytes_grade0'))



WebUI.click(findTestObject('Object Repository/Summary/Acanthocytes_grade0'))
WebUI.click(findTestObject('Object Repository/Summary/Sickle Cells_grade0'))


WebUI.verifyElementText(findTestObject('Object Repository/Summary/Poikilocytosis_cells_row'),'Poikilocytosis')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Poikilocytosis_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Poikilocytosis_grade0'))

// Now navigate to Summary tab and verify presence of cells based on significance
println("\n==== Navigating to Summary Tab ====")
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load

// Simple verification that non-significant cells are not present
println("\n==== Verifying non-significant cells are NOT present on Summary page ====")

// Define verification result variables for all cell types
boolean ovalocytesNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/018_sum_Ovalocytes'), 2, FailureHandling.OPTIONAL)
println(ovalocytesNotPresent ? "PASS: Ovalocytes correctly not displayed (non-significant)" : "FAIL: Ovalocytes incorrectly displayed despite being non-significant")

boolean elliptocytesNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/018_sum_Elliptocytes'), 2, FailureHandling.OPTIONAL)
println(elliptocytesNotPresent ? "PASS: Elliptocytes correctly not displayed (non-significant)" : "FAIL: Elliptocytes incorrectly displayed despite being non-significant")

boolean teardropCellsNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/018_sum_Teardrop_Cells'), 2, FailureHandling.OPTIONAL)
println(teardropCellsNotPresent ? "PASS: Teardrop Cells correctly not displayed (non-significant)" : "FAIL: Teardrop Cells incorrectly displayed despite being non-significant")

boolean fragmentedCellsNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/018_sum_Fragmented Cells'), 2, FailureHandling.OPTIONAL)
println(fragmentedCellsNotPresent ? "PASS: Fragmented Cells correctly not displayed (non-significant)" : "FAIL: Fragmented Cells incorrectly displayed despite being non-significant")

boolean targetCellsNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/018_sum_Target Cells'), 2, FailureHandling.OPTIONAL)
println(targetCellsNotPresent ? "PASS: Target Cells correctly not displayed (non-significant)" : "FAIL: Target Cells incorrectly displayed despite being non-significant")

boolean echinocytesNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/018_sum_Echinocytes'), 2, FailureHandling.OPTIONAL)
println(echinocytesNotPresent ? "PASS: Echinocytes correctly not displayed (non-significant)" : "FAIL: Echinocytes incorrectly displayed despite being non-significant")

boolean poikilocytosisNotPresent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/018_sum_Poikilocytosis'), 2, FailureHandling.OPTIONAL)
println(poikilocytosisNotPresent ? "PASS: Poikilocytosis correctly not displayed (non-significant)" : "FAIL: Poikilocytosis incorrectly displayed despite being non-significant")

// Overall verification
if (ovalocytesNotPresent && elliptocytesNotPresent && teardropCellsNotPresent &&
	fragmentedCellsNotPresent && targetCellsNotPresent && echinocytesNotPresent && poikilocytosisNotPresent) {
	println("\nOVERALL RESULT: PASS - All non-significant RBC shape cells are correctly not displayed on the summary page")
} else {
	println("\nOVERALL RESULT: FAIL - Some non-significant RBC shape cells are incorrectly displayed on the summary page")
}
