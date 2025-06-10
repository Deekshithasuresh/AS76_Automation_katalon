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
import java.util.Random as Random
import java.util.ArrayList as ArrayList
import java.util.Collections as Collections
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    findTestData('New Test Data').getValue(1, 3))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Change password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Current password_old-password'), 
    findTestData('New Test Data').getValue(1, 3))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/hide_passwd_img_for_current_password'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/show_passwd_img_for_current_password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    findTestData('New Test Data').getValue(1, 4))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/hide_passwd_img_for_new_password'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/show_passwd_img_for_new_password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    findTestData('New Test Data').getValue(1, 4))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/hide_passwd_img_for_confirm_password'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/show_passwd_img_for_confirm_password'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'), 
    'Confirm password')

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6_7'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Password changed'), 
    'Password changed')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_Password changed'), 
    'Password changed')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'), 
    'Sign in')

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    findTestData('New Test Data').getValue(1, 4))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Admin Portal'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Change password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Current password_old-password'), 
    findTestData('New Test Data').getValue(1, 4))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/hide_passwd_img_for_current_password'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/show_passwd_img_for_current_password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    findTestData('New Test Data').getValue(1, 3))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/hide_passwd_img_for_new_password'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/show_passwd_img_for_new_password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    findTestData('New Test Data').getValue(1, 3))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/hide_passwd_img_for_confirm_password'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/show_passwd_img_for_confirm_password'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'))

