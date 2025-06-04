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
import generic.custumFunctions

custumFunctions cus = new custumFunctions();


WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Sign In'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_My reports'), 
    30)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Select a time zone_assigned_to'))
WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Select a time zone_assigned_to'), Keys.chord(
	Keys.COMMAND, 'a'))
WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Select a time zone_assigned_to'), Keys.chord(
	Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Select a time zone_assigned_to'), 
    'maz')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_(UTC-0700) AmericaMazatlan'))

WebUI.verifyElementClickable(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Save'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Save'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_Success'), 
    30)

// Navigate to the time zone dropdown and get selected value
String timeZoneValue1 = WebUI.getAttribute(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
	'value')

// Verify it includes Asia/Kolkata
assert timeZoneValue1.contains('America/Mazatlan')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_(UTC-0700) AmericaMazatlan'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_(UTC-0700) AmericaMazatlan'), 
    '(UTC-07:00) America/Mazatlan')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1'))

//selected 1sr row report
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/td_wwwww'))
cus.selectReportByStatus('Under review')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button__MuiButtonBase-root MuiIconButton-ro_10bdc2'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_History'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_27-May-2025, 0126 AM (MST)'), 
    30)

// Get the slide scanned time text
String historyText1 = WebUI.getText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_27-May-2025, 0126 AM (MST)'))

// Verify it contains "JST"
assert historyText1.contains('MST')

// Optionally print for debug
println('Time Zone Configured: ' + timeZoneValue1)

println('History: ' + historyText1)


WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1_2'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_Ready for review'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_Reviewed'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1_2_3'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_Status'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Status_PrivateSwitchBase-input css-1m9pwf3'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_Apply'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_Approved'), 
    30)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/td_yy'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Chidu_MuiButtonBase-root MuiIconButt_3dc005'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_History'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_02-May-2025, 0206 AM (MST)'), 
    30)

// Get the slide scanned time text
String approved_historyText = WebUI.getText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_27-May-2025, 0126 AM (MST)'))

// Verify it contains "JST"
assert approved_historyText.contains('MST')

println('Approved report History page: ' + approved_historyText)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1_2'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Clear filters'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1_2_3'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_Status'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Approved_PrivateSwitchBase-input css-1m9pwf3'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_Apply'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_Rejected'), 
    30)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/td_9141-W'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Chidu_MuiButtonBase-root MuiIconButt_3dc005'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_History'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_17-May-2025, 1228 AM (MST)'), 
    30)

// Get the slide scanned time text
String reject_historyText = WebUI.getText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_27-May-2025, 0126 AM (MST)'))

// Verify it contains "JST"
assert reject_historyText.contains('MST')

println('Rejected report History page: ' + reject_historyText)


//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1_2'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_PBS_icon-img'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_Time zone'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Edit time zone settings'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Select a time zone_assigned_to'))
//WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Select a time zone_assigned_to'), Keys.chord(
//	Keys.COMMAND, 'a'))
//WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Select a time zone_assigned_to'), Keys.chord(
//	Keys.BACK_SPACE))
//
//WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Select a time zone_assigned_to'), 
//    'kolk')
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_(UTC0530) AsiaKolkata'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Save'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1'))
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_Rejected'), 
//    0)
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/td_9141-W'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Chidu_MuiButtonBase-root MuiIconButt_3dc005'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_History'))
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_17-May-2025, 1258 PM (IST)'), 
//    0)
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1_2'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Clear filters'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1_2_3'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_Status'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/input_Status_PrivateSwitchBase-input css-1m9pwf3'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_Apply'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/td_yy'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button_Chidu_MuiButtonBase-root MuiIconButt_3dc005'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/span_History'))
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_02-May-2025, 0236 PM (IST)'), 
//    0)
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/img_1_2'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_Reviewed_1'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_Ready for review_1'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/td_wwwww'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/button__MuiButtonBase-root MuiIconButton-ro_10bdc2'))
//
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/li_History'))
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/Page_PBS/div_27-May-2025, 0232 PM (IST)'), 
//    0)
//
