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

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0000) AfricaBissau'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'))

WebUI.waitForElementVisible(findTestObject('Session management reporting/Page_Admin Console/toaste_msg_alert'), 30)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/alter_popup_img'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Success'), 
    'Success')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Time zone changed successfuly'), 
    'Time zone changed successfuly')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0000) AfricaFreetown'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'))

WebUI.waitForElementVisible(findTestObject('Session management reporting/Page_Admin Console/toaste_msg_alert'), 30)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/alter_popup_img'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Success_1'), 
    'Success')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Time zone changed successfuly'), 
    'Time zone changed successfuly')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0200) AfricaLusaka'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'))

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/alter_popup_img'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Success_1'), 
    'Success')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Time zone changed successfuly'), 
    'Time zone changed successfuly')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

