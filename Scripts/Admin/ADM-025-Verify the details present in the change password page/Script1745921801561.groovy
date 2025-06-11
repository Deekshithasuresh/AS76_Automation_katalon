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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Change password'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Change password'), 
    'Change password')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/label_Current password'), 
    'Current password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Current password_old-password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/label_New password'), 
    'New password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/label_Confirm password'), 
    'Confirm password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    30)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/round_empty_is_alphanumeric'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    'Is alphanumeric')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    30)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/round_empty_contains_at_least_8_char'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    'Contains at-least 8 characters')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    30)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/round_empty_has_atleast_1_special_char'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    'Has at-least 1 special character')

WebUI.verifyElementNotClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'), 
    'Confirm password')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Cancel'), 
    'Cancel')

