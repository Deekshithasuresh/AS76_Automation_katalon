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
WebUI.maximizeWindow()

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
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Inclusions'),'Inclusions')
WebUI.click(findTestObject('Object Repository/Summary/button_Inclusions'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/howell_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Howell-Jolly Bodies_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/pappenheimer_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Pappenheimer Bodies_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Basophilic_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Basophilic Stippling_grade3'))

// Now navigate to Summary tab and verify presence of cells based on significance
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load

// Check if Howell-Jolly Bodies element is present
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/33_sum_howell_jolly_bodies'), 10)) {
	println("Howell-Jolly Bodies - Present in Summary")
} else {
	println("Howell-Jolly Bodies - Not Present in Summary")
}

// Check if Pappenheimer Bodies element is present
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/33_sum_Pappenheimer Bodies'), 10)) {
	println("Pappenheimer Bodies - Present in Summary")
} else {
	println("Pappenheimer Bodies - Not Present in Summary")
}

// Check if Basophilic Stippling element is present
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/33_sum_Basophilic Stippling'), 10)) {
	println("Basophilic Stippling - Present in Summary")
} else {
	println("Basophilic Stippling - Not Present in Summary")
}

// Verify and click on RBC
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC'), 'RBC')
WebUI.click(findTestObject('Object Repository/Summary/span_RBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Inclusions'),'Inclusions')
WebUI.click(findTestObject('Object Repository/Summary/button_Inclusions'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')


WebUI.verifyElementText(findTestObject('Object Repository/Summary/howell_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Howell_jolly bodies_grade0'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/pappenheimer_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/pappenheimer_grade0'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Basophilic_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Basophilic stippling_grade0'))

// Now navigate to Summary tab and verify absence of cells based on significance
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load

// Verify Howell-Jolly Bodies element is NOT present
if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/33_sum_howell_jolly_bodies'), 10)) {
	println("✅ PASS: Howell-Jolly Bodies - Correctly Not Present in Summary")
} else {
	println("❌ FAIL: Howell-Jolly Bodies - Should Not Be Present in Summary")
}

// Verify Pappenheimer Bodies element is NOT present
if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/33_sum_Pappenheimer Bodies'), 10)) {
	println("✅ PASS: Pappenheimer Bodies - Correctly Not Present in Summary")
} else {
	println("❌ FAIL: Pappenheimer Bodies - Should Not Be Present in Summary")
}

// Verify Basophilic Stippling element is NOT present
if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/33_sum_Basophilic Stippling'), 10)) {
	println("✅ PASS: Basophilic Stippling - Correctly Not Present in Summary")
} else {
	println("❌ FAIL: Basophilic Stippling - Should Not Be Present in Summary")
}

