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
import java.util.UUID as UUID
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/button_Users'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/button_Create User'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Name_rbc-input-box'))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Name_rbc-input-box'), 
    'jyo')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Email_rbc-input-box'))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Email_rbc-input-box'), 
    'jyo@gmail.com')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Username_rbc-input-box'))

//  Generate random username
WebDriver driver = DriverFactory.getWebDriver()

// Your Username input field object
TestObject usernameField = findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Username_rbc-input-box')

// Generate username method
// Only letters
// Fill username, check, retry if needed
boolean usernameAccepted = false

String uniqueUsername = ''

while (!(usernameAccepted)) {
    uniqueUsername = generateUsername()

    println('Trying username: ' + uniqueUsername)

    WebUI.setText(usernameField, uniqueUsername)

    WebUI.delay(1)

    // Check if error message is displayed
    boolean isError = WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/div_Username already taken'), 
        1, FailureHandling.OPTIONAL)

    if (isError) {
        println('Username already taken. Retrying...')

        //        WebUI.clearText(usernameField)
        WebUI.click(usernameField)

        WebUI.sendKeys(usernameField, Keys.chord(Keys.COMMAND, 'a'))

        WebUI.sendKeys(usernameField, Keys.chord(Keys.BACK_SPACE))
    } else {
        println('Username accepted: ' + uniqueUsername)

        usernameAccepted = true
    }
}

//WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Username_rbc-input-box'), 
//    'jyo')
WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Password_rbc-input-box'))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Password_rbc-input-box'), 
    'jyo@1996')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/input_Operator_userRole'))

WebUI.verifyElementClickable(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/button_Create new user'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/button_Create new user'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/button_Create and copy'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/li_Logout'))

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_Admin Console/h4_Sign in'), 
    'Sign in')

WebUI.newTab('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_username_loginId'), uniqueUsername)

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_password_loginPassword'), 'jyo@1996')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_I Accept'))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_New password_new-password'), 
    'jyo@1995')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'jyo@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Confirm password'))

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/h4_Successful password reset'), 
    'Successful password reset!')

WebUI.click(findTestObject('Configure Time Zone Reporting/Page_PBS/button_for_successful_reset_passwd_Sign_in'))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_username_loginId'), uniqueUsername)

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_password_loginPassword'), 'jyo@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Sign In'))

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_My reports'), 'My reports')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/h2_Time zone'), 'Time zone')

WebUI.refresh()

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0000) EtcGMT'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/span_(UTC0000) EtcGMT'), 
    '(UTC+00:00) Etc/GMT')

String generateUsername() {
    String randomText = UUID.randomUUID().toString().replaceAll('-', '').replaceAll('[^a-zA-Z]', '')

    return 'user-' + randomText.substring(0, 3)
}

