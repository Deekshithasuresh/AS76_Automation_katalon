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
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// 1. Login
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId (21)'), 'Chidu')
WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword (21)'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In (21)'))

// 2. Open latest report
//WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 1145 AM (IST)'))
CustomKeywords.'chida.wbcFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'chida.wbcFunctions.assignOrReassignOnTabs'('Chidu',true)
// 3. Store the RBC Morph object
TestObject RBC_Morph = findTestObject('Object Repository/Commontools/Page_PBS/RBC_Morph')

// 4. Check initial value in RBC Morph (before entering new data)
String initialValue = WebUI.getAttribute(RBC_Morph, 'value')
println "Initial RBC Morph Value: " + initialValue

// 5. Enter new alphanumeric + special characters in RBC Morph
enterAlphaNumSpecialInField(RBC_Morph, 'testkatalon@123')

// 6. Click back arrow or logo to trigger save
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/img'))

// 7. Verify "Switch to original report" option is present
WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Switch to original report'), 5)
WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Switch to original report'), 'Switch to original report')

// 8. Click "Switch to original report"
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/span_Switch to original report'))

// 9. Verify that entered data is **not present** in original report
String valueInOriginal = WebUI.getAttribute(RBC_Morph, 'value')
println "Value in Original Report RBC Morph: " + valueInOriginal
assert valueInOriginal != '12as#' : 'Entered value is unexpectedly present in original report'

/* === Reusable method === */
def enterAlphaNumSpecialInField(TestObject fieldObject, String alphaNumSpecialToEnter) {
	WebUI.waitForElementVisible(fieldObject, 20)
	WebUI.click(fieldObject)
	WebUI.clearText(fieldObject)
	WebUI.setText(fieldObject, alphaNumSpecialToEnter)
	WebUI.sendKeys(fieldObject, Keys.chord(Keys.TAB)) // Tab out to trigger save
}

