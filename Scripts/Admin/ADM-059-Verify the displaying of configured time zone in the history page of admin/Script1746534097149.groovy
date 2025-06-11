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
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.TimeZone as TimeZone

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/home')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0300) AsiaBahrain'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'))

// Store selected zone string
String selectedZoneText = WebUI.getText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0300) AsiaBahrain'))

println('✅ Selected Time Zone: ' + selectedZoneText)

def selectedOffsetMatch = selectedZoneText =~ '\\(UTC([+-]\\d{2}):\\d{2}\\)'

String selectedOffset = selectedOffsetMatch ? (selectedOffsetMatch[0])[1] : null

println('Extracted Selected Offset: ' + selectedOffset // Output: +03
    )

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/span_(UTC0300) AsiaBahrain'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/back_icon_of_configured_time_zone'))

//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_PBS settings'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_RBC diameter limits'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit settings'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Macrocytes_rbc-input-box'))
//
//WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Macrocytes_rbc-input-box'), 
//    Keys.chord(Keys.COMMAND, 'a'))
//
//WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Macrocytes_rbc-input-box'), 
//    Keys.chord(Keys.BACK_SPACE))
//
//WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Macrocytes_rbc-input-box_1'), 
//    '8')
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save_1'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1'))
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/svg_Admin Portal_MuiSvgIcon-root MuiSvgIcon_5f2eb8'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_History'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_07-May-2025, 0752 AM (03)'), 
    30)

TestObject historyTimestamp = findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_07-May-2025, 0752 AM (03)')

String historyText = WebUI.getText(historyTimestamp)

println('🕓 History Entry: ' + historyText)

// Extract `+03` from "(+03)"
def historyOffsetMatch = historyText =~ '\\(\\+(\\d{2})\\)'

// Extract matched group using Groovy syntax
String historyOffset = historyOffsetMatch.find() ? "+" + (historyOffsetMatch[0][1]) : null

println("Extracted History Offset: $historyOffset")

println("Selected Offset: $selectedOffset")

// Compare both
assert historyOffset == selectedOffset

println('✅ Time zone match verified between Time Zone page and History page.')

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_icon_of_history'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Admin Portal'), 
    1)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Admin Portal'), 
    'Admin Portal')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Download CSV_MuiBackdrop-root MuiBackdr_c8f0be'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0530) AsiaCalcutta'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Time zone changed successfuly'), 
    'Time zone changed successfuly')

// Step 1: Extract selected offset from time zone page, e.g. "(UTC+03:00) Asia/Bahrain"
String selectedZoneText1 = WebUI.getText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0530) AsiaCalcutta'))

def selectedOffsetMatch1 = selectedZoneText1 =~ '\\(UTC([+-]\\d{2}:\\d{2})\\)'

String selectedOffset1 = selectedOffsetMatch1 ? (selectedOffsetMatch1[0])[1] : null

println('Selected Offset: ' + selectedOffset1 // e.g., +03:00
    )

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/span_(UTC0530) AsiaCalcutta'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/span_(UTC0530) AsiaCalcutta'), 
    '(UTC+05:30) Asia/Calcutta')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/svg_Admin Portal_MuiSvgIcon-root MuiSvgIcon_5f2eb8'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_History_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_08-May-2025, 1246 PM (IST)'), 
    30)

// Step 2: Extract the value inside parentheses from the history timestamp
String historyText1 = WebUI.getText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_08-May-2025, 1246 PM (IST)' // OR (IST)
        ))

def historyZoneMatch1 = historyText1 =~ '\\(([^)]+)\\)'

String historyZone1 = historyZoneMatch1 ? (historyZoneMatch1[0])[1] : null

println('History Display: ' + historyZone1)

// Step 3: Normalize the historyZone
Map<String, String> abbreviationToOffset = [('IST') : '+05:30', ('CET') : '+01:00', ('EET') : '+02:00', ('AST') : '+03:00'
    , ('GST') : '+04:00', ('PST') : '-08:00', ('EST') : '-05:00', ('JST') : '+09:00', ('CST') : '-06:00', ('MST') : '-07:00'
    , ('BST') : '+06:00', ('GMT') : '+00:00']

// If historyZone is already a raw offset like +03, pad it to match selectedOffset
String normalizedHistoryOffset

if (historyZone1 ==~ '^[+-]\\d{2}$') {
    normalizedHistoryOffset = (historyZone1 + ':00')
} else if (abbreviationToOffset.containsKey(historyZone1)) {
    normalizedHistoryOffset = (abbreviationToOffset[historyZone1])
} else {
    throw new AssertionError("❌ Unknown or unhandled history zone format: $historyZone1")
}

println('Normalized History Offset: ' + normalizedHistoryOffset)

println('Selected Offset: ' + selectedOffset1)

// Final comparison
assert normalizedHistoryOffset == selectedOffset1

println("✅ Time zone match: $selectedOffset1")

