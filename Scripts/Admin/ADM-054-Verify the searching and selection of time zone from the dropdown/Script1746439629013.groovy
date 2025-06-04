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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.testobject.ConditionType as ConditionType

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

TestObject input_select_tz = findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to')

WebUI.click(input_select_tz)

WebUI.sendKeys(input_select_tz, Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(input_select_tz, Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to'), 
    'abc')

WebUI.delay(1)

// Define a dynamic TestObject for the "No options" text
TestObject noOptionsText = new TestObject('dynamicNoOptions')

noOptionsText.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(), \'No options\')]')

// Verify if "No options" is present
boolean isNoOptionsVisible = WebUI.verifyElementPresent(noOptionsText, 2, FailureHandling.OPTIONAL)

if (isNoOptionsVisible) {
    WebUI.comment('✅ \'No options\' message is visible as expected.')
} else {
    WebUI.comment('❌ \'No options\' message was not found.')
}

String TimeZone1 = '(UTC+03:00) Africa/Cairo'

setAndSaveTimeZone(TimeZone1)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/alter_popup_img'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Success'), 
    'Success')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Time zone changed successfuly'), 
    'Time zone changed successfuly')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'), 
    30)

String TimeZone2 = '(UTC+01:00) Africa/Casablanca'

setAndSaveTimeZone(TimeZone2)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/alter_popup_img'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Success'), 
    'Success')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Time zone changed successfuly'), 
    'Time zone changed successfuly')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'), 
    30)

void setAndSaveTimeZone(String timeZone) {
    TestObject timezoneDropdown = findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to')

    WebUI.click(timezoneDropdown)

    WebUI.sendKeys(timezoneDropdown, Keys.chord(Keys.COMMAND, 'a'))

    WebUI.sendKeys(timezoneDropdown, Keys.chord(Keys.BACK_SPACE))

    WebUI.sendKeys(timezoneDropdown, timeZone)

    WebDriver driver = DriverFactory.getWebDriver()

    WebElement firstOption = driver.findElement(By.id('assigned_to-option-0'))

    firstOption.click()

    TestObject saveButton = findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save')

    WebUI.click(saveButton)
}

