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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Time zone'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

//WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Edit time zone settings'), 
//    'Edit time zone settings')
WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/span_Select a time zone'), 
    'Select a time zone')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_'), 30)

String TimeZone1 = '(UTC+03:00) Africa/Addis_Ababa'

setAndSaveTimeZone(TimeZone1)

//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1'), 0)
//
//WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_(UTC0000) AfricaBamako'))
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'), 
//    0)
//
//WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'), 
//    'Save')
//
//WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'))
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save'))
WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Logout'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Sign in'), 
    'Sign in')

//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/span_(UTC0000) AfricaBamako'), 
// 0)
WebUI.newTab('https://as76-pbs.sigtuple.com/login')

WebUI.delay(3)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Time zone'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/h2_Time zone'), 'Time zone')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/span_(UTC0000) AfricaBamako'), 
    TimeZone1)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Logout'))

WebUI.newTab('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Time zone'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

//WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Edit time zone settings'),
//    'Edit time zone settings')
WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/span_Select a time zone'), 
    'Select a time zone')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_'), 30)

String TimeZone2 = '(UTC+02:00) Africa/Blantyre'

setAndSaveTimeZone(TimeZone2)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Logout'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Sign in'), 
    'Sign in')

WebUI.newTab('https://as76-pbs.sigtuple.com/login')

WebUI.delay(3)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Time zone'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/h2_Time zone'), 'Time zone')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/span_(UTC0000) AfricaBamako'), 
    TimeZone2)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Logout'))

void setAndSaveTimeZone(String timeZone) {
    TestObject timezoneDropdown = findTestObject('Object Repository/Session management reporting/Page_Admin Console/Timezone_input_field')

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

