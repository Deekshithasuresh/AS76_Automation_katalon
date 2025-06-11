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

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Time zone'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'), 
    'Edit time zone settings')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Edit time zone settings'), 
    30)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/back_icon_of_configured_time_zone'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/back_icon_of_configured_time_zone'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Admin Portal'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_PBS settings'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_RBC diameter limits'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_RBC diameter limits (microns)'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Time zone'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Time zone'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Configured time zone'),30)
