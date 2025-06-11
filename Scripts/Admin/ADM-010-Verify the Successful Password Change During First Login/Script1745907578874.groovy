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
import loginPack.Login as Login
import java.util.UUID as UUID
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.*

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Users'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Create User'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Name_rbc-input-box'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Name_rbc-input-box'), 
    'vishwajyothi')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Email_rbc-input-box'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Email_rbc-input-box_1_2'), 
    'vishwajyothi@gmail.com')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_rbc-input-box'))

//  Generate random username
WebDriver driver = DriverFactory.getWebDriver()

// Your Username input field object
TestObject usernameField = findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_rbc-input-box')

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
    boolean isError = WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Username already taken'), 
        1, FailureHandling.OPTIONAL)

    if (isError) {
        println('Username already taken. Retrying...')

        //WebUI.clearText(usernameField)
        WebUI.click(usernameField)

        WebUI.sendKeys(usernameField, Keys.chord(Keys.COMMAND, 'a'))

        WebUI.sendKeys(usernameField, Keys.chord(Keys.BACK_SPACE))
    } else {
        println('Username accepted: ' + uniqueUsername)

        usernameAccepted = true
    }
}

//WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_rbc-input-box_1_2_3_4_5_6_7_8_9_10_11'),
//	uniqueUsername)
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_rbc-input-box'))
WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_rbc-input-box_1_2_3'), 
    'vishwajyothi@1996')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Reviewer_userRole'))

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Create new user'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Create new user'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Create and copy'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_User created'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.waitForElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Logout'), 
    30)

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Logout'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Logout'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Sign in'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Sign in'), 
    'Sign in')

// login with created user
WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    uniqueUsername)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'vishwajyothi@1996')

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Reset password'), 
    'Reset password')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/label_New password'), 
    'New password')

// Get the placeholder attribute
String placeholder = WebUI.getAttribute(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    'placeholder')

// Verify placeholder
WebUI.verifyMatch(placeholder, 'Enter new password', false)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/label_Confirm password'), 
    'Confirm password')

// Get the placeholder attribute
String placeholder1 = WebUI.getAttribute(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    'placeholder')

// Verify placeholder
WebUI.verifyMatch(placeholder1, 'Confirm password', false)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    'Is alphanumeric')

//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1'), 0)
WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    'Contains at-least 8 characters')

//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2'), 
//    0)
WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    'Has at-least 1 special character')

//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3'), 
//    0)
WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    'vishwa1996')

//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_New password_MuiButtonBase-root MuiI_508013'))
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    'vishwa1996')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_Password must have atleast 1 special character'), 
    'Password must have atleast 1 special character')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    30)

//WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6_7'), 
//    0)
//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
//    '9NLz+4tGZcQ=')
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
//    '9NLz+4tGZcQ=')
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    'vish@19')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_Password must be atleast 8 characters long'), 
    'Password must be atleast 8 characters long')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    'Is alphanumeric')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    'Contains at-least 8 characters')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    'Has at-least 1 special character')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    30)

//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
//    'ujqDvdBllLY=')
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
//    'ujqDvdBllLY=')
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_Password must be atleast 8 characters long'), 
    'Password must be atleast 8 characters long')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    30)

//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
//    '9NLz+4tGZcQ=')
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
//    '9NLz+4tGZcQ=')
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    'vishwa@@')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    'vishwa@@')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_Password must have atleast 1 alphabet and number'), 
    'Password must have atleast 1 alphabet and number')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Is alphanumeric'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Has at-least 1 special character'), 
    30)

//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
//    '9NLz+4tGZcQ=')
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
//    '9NLz+4tGZcQ=')
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    'vishwa@1996')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    'vishwa@1997')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_New password and Confirm password does not match'), 
    'New password and Confirm password does not match')

//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
//    '9NLz+4tGZcQ=')
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
//    '9NLz+4tGZcQ=')
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    'vishwa@1996')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    'vishwa@1996')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'), 
    'Confirm password')

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password_1'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6_7'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6_7'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Successful password reset'), 
    'Successful password reset!')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_You can now use your new password to sign_6f5a89'), 
    'You can now use your new password to sign in to your account!')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'), 
    30)

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Sign in'), 
    30)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    uniqueUsername)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'vishwa@1996')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Admin Portal'), 
    30)

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Change password'), 
    30)

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Change password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Current password_old-password'), 
    'vishwa@1996')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    'vishwa@2000')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6_7_8'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    'vishwa@2000')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6_7_8_9'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6_7_8_9_10'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password_1_2'), 
    30)

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password_1_2'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Confirm password_1_2'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1_2_3_4_5_6_7'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Password changed'), 
    'Password changed')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_Password changed'), 
    'Password changed')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'), 
    30)

WebUI.verifyElementClickable(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in_1'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    uniqueUsername)

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'vishwa@2000')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Admin Portal'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h2_Admin Portal'), 
    'Admin Portal')

String generateUsername() {
    String randomText = UUID.randomUUID().toString().replaceAll('-', '').replaceAll('[^a-zA-Z]', '')

    return 'user-' + randomText.substring(0, 3)
}

