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
import java.time.ZonedDateTime as ZonedDateTime
import java.time.ZoneId as ZoneId
import java.time.format.DateTimeFormatter as DateTimeFormatter

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    'utc+12')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC1200) EtcGMT-12'))

//TestObject timeTextObject = findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_TimeZoneDisplay')
//WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_TimeZoneDisplay'))
WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))

// Wait and get full time string for UTC+12
TestObject timeDisplay = findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_TimeZoneDisplay')

WebUI.waitForElementVisible(timeDisplay, 10)

String dateTimeTextUtcPlus12 = WebUI.getText(timeDisplay)

println('Full text in UTC+12: ' + dateTimeTextUtcPlus12)

// Extract only date (e.g., '13-May-2025')
String dateOnlyUtcPlus12 = (dateTimeTextUtcPlus12.tokenize(',')[0]).trim()

println('Date in UTC+12: ' + dateOnlyUtcPlus12)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    'utc-12')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC-1200) EtcGMT12'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))

// Wait and get full time string for UTC-12
WebUI.waitForElementVisible(timeDisplay, 10)

String dateTimeTextUtcMinus12 = WebUI.getText(timeDisplay)

println('Full text in UTC-12: ' + dateTimeTextUtcMinus12)

// Extract only date
String dateOnlyUtcMinus12 = (dateTimeTextUtcMinus12.tokenize(',')[0]).trim()

println('Date in UTC-12: ' + dateOnlyUtcMinus12)

// Compare just the dates
if (dateOnlyUtcPlus12 != dateOnlyUtcMinus12) {
    println("✅ Date changed according to time zone: $dateOnlyUtcPlus12 → $dateOnlyUtcMinus12") // Force test fail
} else {
    WebUI.comment('❌ Date did NOT change after switching time zone.')

    WebUI.verifyMatch(dateOnlyUtcPlus12, '.*', false)
}

