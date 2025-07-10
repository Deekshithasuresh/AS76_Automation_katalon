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
import internal.GlobalVariable

import org.assertj.core.error.AssertionErrorMessagesAggregator
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil


// Open browser and login
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('IAM Model/Page_Admin Console/input_Username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('IAM Model/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('IAM Model/Page_Admin Console/button_Sign in'))

// Navigate to Create User
WebUI.click(findTestObject('IAM Model/Page_Admin Console/div_User'))
WebUI.click(findTestObject('IAM Model/Page_Admin Console/button_Users'))
WebUI.click(findTestObject('IAM Model/Page_Admin Console/button_Create User'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/span_Password'), 'Password')

TestObject passwordInput = findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box')
WebUI.setText(passwordInput, 'Test1234')
TestObject validationMessage = findTestObject('Object Repository/IAM Model/Page_Admin Console/Page_Admin Console/Page_Admin Console/div_Invalid_Password_Validation')
boolean isErrorVisible = WebUI.verifyElementPresent(validationMessage, 3, FailureHandling.OPTIONAL)

assert isErrorVisible : "❌ Validation error not shown for password missing special character"

String errorText = WebUI.getText(validationMessage)
WebUI.comment("✅ Validation message displayed: $errorText")

// Step 5: Optional — check CSS color of validation message
String color = WebUI.getCSSValue(passwordInput, "color")
assert color == 'rgba(224, 66, 77, 1)' : "❌ Color mismatch: Found '${color}', expected 'rgba(224, 66, 77, 1)'"
WebUI.comment("⚠ Validation message color: $color")

