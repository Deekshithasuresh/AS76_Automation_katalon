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

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC0100) WET'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
    'Time zone changed successfully')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0100) WET'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0100) WET'), '(UTC+01:00) WET')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC0300) W-SU'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
    'Time zone changed successfully')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0300) W-SU'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0300) W-SU'), 
    '(UTC+03:00) W-SU')

