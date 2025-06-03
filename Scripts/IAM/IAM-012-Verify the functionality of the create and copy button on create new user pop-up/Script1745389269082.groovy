
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
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.util.Random as Random
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Users'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'), 'Create User')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'), 'pawan,Roo')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'), 'testadmin@123.com')

////randmo generation of the user including special characters
//// Click to focus on the username input field
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'))
//
//// Get the WebDriver instance
//WebDriver driver = DriverFactory.getWebDriver()
//
//// Username input field object
//TestObject usernameField = findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box')
//
//// Method to generate a random username consisting of letters and specified special characters
//// Start with "User" as the base
//// Generate a random username of length 6
//// Append each random character
//// Flag to check whether the username is accepted
//boolean usernameAccepted = false
//
//String uniqueUsername = ''
//
//// Loop to retry generating a unique username if necessary
//while (!(usernameAccepted)) {
//    uniqueUsername = generateUsername()
//
//    println('Trying username: ' + uniqueUsername)
//
//    // Enter the generated username in the input field
//    WebUI.setText(usernameField, uniqueUsername)
//
//    WebUI.delay(1)
//
//    // Check if the error message for "username already taken" is displayed
//    boolean isError = WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Username already taken'), 
//        1, FailureHandling.OPTIONAL)
//
//    if (isError) {
//        println('Username already taken. Retrying...')
//
//        // Clear the username field (platform-independent way)
//        WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'))
//
//        // Platform-independent text selection and clearing
//        if (System.getProperty('os.name').toLowerCase().contains('mac')) {
//            WebUI.sendKeys(usernameField, Keys.chord(Keys.COMMAND, 'a'))
//        } else {
//            WebUI.sendKeys(usernameField, Keys.chord(Keys.CONTROL, 'a'))
//        }
//        
//        WebUI.sendKeys(usernameField, Keys.chord(Keys.BACK_SPACE))
//    } else {
//        println('Username accepted: ' + uniqueUsername)
//
//        usernameAccepted = true
//    }
//}

//random user creation steps ends here.

// second method of random creation with creating the the user name by special characters.
// Click to focus on the username input field
WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'))

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// Username input field object
TestObject usernameField = findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box')

// Method to generate a random username consisting of letters only
String generateUsername() {
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	StringBuilder username = new StringBuilder("User");
	Random rand = new Random();

	// Generate a random username of length 6
	for (int i = 0; i < 6; i++) {
		int index = rand.nextInt(alphabet.length());
		username.append(alphabet.charAt(index));
	}
	return username.toString();
}

// Flag to check whether the username is accepted
boolean usernameAccepted = false;
String uniqueUsername = "";

// Loop to retry generating a unique username if necessary
while (!usernameAccepted) {
	uniqueUsername = generateUsername();
	println('Trying username: ' + uniqueUsername);

	// Enter the generated username in the input field
	WebUI.setText(usernameField, uniqueUsername);
	WebUI.delay(1);

	// Check if the error message for "username already taken" is displayed
	
	boolean isError = WebUI.verifyElementPresent(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_Username already taken'),
		1, FailureHandling.OPTIONAL);

	if (isError) {
		println('Username already taken. Retrying...');

		// Clear the username field (platform-independent way)
		WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'));

		// Platform-independent text selection and clearing
		if (System.getProperty('os.name').toLowerCase().contains('mac')) {
			WebUI.sendKeys(usernameField, Keys.chord(Keys.COMMAND, 'a'));
		} else {
			WebUI.sendKeys(usernameField, Keys.chord(Keys.CONTROL, 'a'));
		}
		WebUI.sendKeys(usernameField, Keys.chord(Keys.BACK_SPACE));
	} else {
		println('Username accepted: ' + uniqueUsername);
		usernameAccepted = true;
	}
}
// Steps ends for creation of the username random without special char.
WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'), 'Admin@123')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Reviewer_userRole'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create new user'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/h2_Share the Credentials'), 'Share the Credentials')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Please share the credentials to the regis_554608'), 
    'Please share the credentials to the registered user')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Username'), 'Username')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Password'), 'Password')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Cancel'), 'Cancel')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create and copy'), 'Create and copy')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create and copy'))

// === Customize your expected dynamic message here ===
String expectedPartial = 'has been successfully created'

int timeout = 20

// === Define Toast TestObject based on your structure ===
TestObject toastObject = new TestObject('dynamicToast')

toastObject.addProperty('xpath', ConditionType.EQUALS, '//div[@id=\'root\']/div[2]/div/div')

// === Wait for the toast to appear ===
if (WebUI.waitForElementPresent(toastObject, timeout)) {
    String toastText = WebUI.getText(toastObject)

    toastText = toastText.replaceAll('\\s+', ' ').trim( // normalize white space
        )

    WebUI.comment("✅ Toast message: '$toastText'")

    if (toastText.contains(expectedPartial)) {
        KeywordUtil.markPassed("✅ Toast matched: '$toastText'")
    } else {
        KeywordUtil.markFailed("⚠️ Toast appeared but message mismatch.Expected to contain: '$expectedPartial'Actual: '$toastText'")
    }
} else {
    KeywordUtil.markFailed("❌ Toast message did not appear within $timeout seconds.")
}


