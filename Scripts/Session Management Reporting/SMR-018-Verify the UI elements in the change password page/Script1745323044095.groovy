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

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Session management reporting/Page_PBS/button_Sign In_for_PBS login'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/img_PBS_icon-img'), 30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_PBS_icon-img'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Time zone'), 'Time zone')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Change Password'), 'Change Password')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Logout'), 'Logout')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Change Password'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/h4_Change password'), 'Change password')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/label_Current password'), 
    'Current password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Current password_old-password'), 
    30)

// Get the placeholder attribute
String Current_password_placeholder = WebUI.getAttribute(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Current password_old-password'), 
    'placeholder')

// Verify placeholder
WebUI.verifyMatch(Current_password_placeholder, 'Enter current password', false)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/label_New password'), 'New password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    30)

// Get the placeholder attribute
String New_password_placeholder = WebUI.getAttribute(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'placeholder')

// Verify placeholder
WebUI.verifyMatch(New_password_placeholder, 'Enter new password', false)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/label_Confirm password'), 
    'Confirm password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    30)

// Get the placeholder attribute
String Confirm_password_placeholder = WebUI.getAttribute(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'placeholder')

// Verify placeholder
WebUI.verifyMatch(Confirm_password_placeholder, 'Confirm password', false)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/div_Is alphanumeric'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Is alphanumeric'), 'Is alphanumeric')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/div_Contains at-least 8 characters'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Contains at-least 8 characters'), 
    'Contains at-least 8 characters')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_PBS/div_Has at-least 1 special character'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Has at-least 1 special character'), 
    'Has at-least 1 special character')

WebUI.verifyElementText(findTestObject('Session management reporting/Page_PBS/Confirm_password'), 'Confirm password')

WebUI.verifyElementNotClickable(findTestObject('Session management reporting/Page_PBS/Confirm_password'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Cancel'), 'Cancel')

