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

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Sign In'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_My reports'), 30)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'), 
    30)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_Select a time zone'), 
    30)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC0200) AfricaKigali'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
    'Time zone changed successfully')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0200) AfricaKigali'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0200) AfricaKigali'), 
    '(UTC+02:00) Africa/Kigali')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Logout'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/h4_Sign In'), 0)

WebUI.newTab('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/button_Sign in'))

WebUI.refresh()

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/h2_Admin Portal'), 
    30)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/li_Time zone'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/h2_Time zone'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/span_(UTC0200) AfricaKigali'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/span_(UTC0200) AfricaKigali'), 
    '(UTC+02:00) Africa/Kigali')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/li_Logout'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/h4_Sign in'), 
    30)

//
WebUI.newTab('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC0100) AfricaKinshasa'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0100) AfricaKinshasa'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0100) AfricaKinshasa'), 
    '(UTC+01:00) Africa/Kinshasa')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Logout'))

//
WebUI.newTab('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/li_Time zone'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/span_(UTC0100) AfricaKinshasa'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/span_(UTC0100) AfricaKinshasa'), 
    '(UTC+01:00) Africa/Kinshasa')

