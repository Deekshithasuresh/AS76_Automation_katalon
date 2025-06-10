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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

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
    'kolkata')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC0530) AsiaKolkata'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

//WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
//    'Time zone changed successfully')
WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Success'), 'Success')

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_(UTC0000) AfricaAbidjan_icon-tick-mark-filled'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
    'Time zone changed successfully')

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'), 
    30)

WebUI.verifyElementClickable(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'))

// Navigate to the time zone dropdown and get selected value
String timeZoneValue = WebUI.getAttribute(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    'value')

// Verify it includes Asia/Kolkata
assert timeZoneValue.contains('Asia/Kolkata')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0530) AsiaKolkata'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0530) AsiaKolkata'), 
    '(UTC+05:30) Asia/Kolkata')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/td_tstt'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1_2'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_07-May-2025, 0111 PM (IST)'), 
    30)

// Get the slide scanned time text
String scannedTimeText = WebUI.getText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_07-May-2025, 0111 PM (IST)'))

// Verify it contains "IST"
assert scannedTimeText.contains('IST')

// Optionally print for debug
println('Time Zone Configured: ' + timeZoneValue)

println('Slide Scanned Time: ' + scannedTimeText)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Under review_MuiButtonBase-root MuiI_3a26e9'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1_2_3'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC0900) Japan'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

//WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
//    'Time zone changed successfully')
WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Success'), 'Success')

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_(UTC0000) AfricaAbidjan_icon-tick-mark-filled'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
    'Time zone changed successfully')

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'), 
    30)

WebUI.verifyElementClickable(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'))

// Navigate to the time zone dropdown and get selected value
String timeZoneValue1 = WebUI.getAttribute(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    'value')

// Verify it includes Asia/Kolkata
assert timeZoneValue1.contains('Japan')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0900) Japan'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0900) Japan'), 
    '(UTC+09:00) Japan')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/td_9141-W'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1_2'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_06-May-2025, 0931 PM (JST)'), 
    30)

// Get the slide scanned time text
String scannedTimeText1 = WebUI.getText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_06-May-2025, 0931 PM (JST)'))

// Verify it contains "JST"
assert scannedTimeText1.contains('JST')

// Optionally print for debug
println('Time Zone Configured: ' + timeZoneValue1)

println('Slide Scanned Time: ' + scannedTimeText1)

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Under review_MuiButtonBase-root MuiI_3a26e9'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1_2_3'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/th_Scan date'), 30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_07-May-2025, 0441 PM (JST)'), 
    30)

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// List all rows from the scan date column
List<WebElement> scanDateElements = driver.findElements(By.xpath('//tbody//tr//td[4]'))

// Flag to track JST suffix
boolean allInJST = true

// Iterate through each scan date element
for (WebElement el : scanDateElements) {
    // Get WebDriver and JS executor
    WebDriver driver1 = DriverFactory.getWebDriver()

    JavascriptExecutor js = driver1 as JavascriptExecutor

    // Scroll container CSS selector
    String scrollSelector = '#reportListingTable > div > div'

    int scrollStep = 300

    int delaySeconds = 1

    int maxTries = 100

    int tries = 0

    WebUI.delay(2)

    // Scroll loop
    while (tries < maxTries) {
        def result = js.executeScript('\n       const el = document.querySelector(arguments[0]);\n       const before = el.scrollTop;\n       el.scrollBy(0, arguments[1]);\n       return {\n           scrollTop: el.scrollTop,\n           scrollHeight: el.scrollHeight,\n           clientHeight: el.clientHeight,\n           before: before\n       };\n   ', 
            scrollSelector, scrollStep)

        long scrollTop = result.scrollTop

        long scrollHeight = result.scrollHeight

        long clientHeight = result.clientHeight

        long before = result.before

        KeywordUtil.logInfo((('Scroll position: ' + scrollTop) + ' / ') + (scrollHeight - clientHeight))

        WebUI.delay(delaySeconds)

        if (scrollTop >= ((scrollHeight - clientHeight) - 10)) {
            break
        }
        
        if (scrollTop == before) {
            KeywordUtil.logInfo('ScrollTop unchanged. Possibly stuck. Breaking.')

            break
        }
        
        tries++
    }
    
    // ✅ Count all rows after scrolling
    String rowXPath = '//*[@id=\'reportListingTable\']/div/div/div/tbody/tr'

    int totalRows = driver.findElements(By.xpath(rowXPath)).size()

    KeywordUtil.logInfo('✅ Scrolling complete. Total rows loaded: ' + totalRows)

    String scanDateText = el.getText()

    println('Scan Date: ' + scanDateText)

    if (!(scanDateText.endsWith('(JST)'))) {
        allInJST = false

        println('❌ Not in JST: ' + scanDateText)
    }
}

if (allInJST) {
    println('✅ All scan dates are in JST.')
} else {
    println('⚠️ Some scan dates are NOT in JST.')
}

