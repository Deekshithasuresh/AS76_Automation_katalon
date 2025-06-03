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

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'), 'pawan')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'), 'testadmin@123.com')

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

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'), 
    findTestData('Contact_1').getValue(1, 1))

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


// Second user creation
WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'), 'kumar')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'), 'testadmin@123.com')

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'),
	findTestData('Contact_1').getValue(1, 1))


// Click to focus on the username input field
WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'))



// Flag to check whether the username is accepted
boolean uniqueUsername_reviwer_Accepted = false;
String uniqueUsername_reviwer = "";

// Loop to retry generating a unique username if necessary
while (!uniqueUsername_reviwer_Accepted) {
	uniqueUsername_reviwer = generateUsername();
	println('Trying username: ' + uniqueUsername_reviwer);

	// Enter the generated username in the input field
	WebUI.setText(usernameField, uniqueUsername_reviwer);
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
		println('Username accepted: ' + uniqueUsername_reviwer);
		uniqueUsername_reviwer_Accepted = true;
	}
}
// Steps ends for creation of the username random without special char.

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'), 'Admin@123')

WebUI.click(findTestObject('IAM Model/Page_Admin Console/input_Operator_userRole'))

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
String expectedPartialSecond = 'has been successfully created'

int timeoutForNewUser = 20

// === Define Toast TestObject for first toast ===
TestObject toastObjectFirst = new TestObject('dynamicToastFirst')

toastObjectFirst.addProperty('xpath', ConditionType.EQUALS, '//div[@id=\'root\']/div[2]/div/div')

// === Wait for the first toast to appear ===
if (WebUI.waitForElementPresent(toastObjectFirst, timeoutForNewUser)) {
    String toastText = WebUI.getText(toastObjectFirst).replaceAll('\\s+', ' ').trim()

    WebUI.comment("✅ Toast message: '$toastText'")

    if (toastText.contains(expectedPartialSecond)) {
        KeywordUtil.markPassed("✅ Toast matched: '$toastText'")
    } else {
        KeywordUtil.markFailed("⚠️ Toast appeared but message mismatch. Expected: '$expectedPartialSecond'. Actual: '$toastText'")
    }
} else {
    KeywordUtil.markFailed("❌ Toast message did not appear within $timeoutForNewUser seconds.")
}

//third time user creation

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'), 'pawan kumar')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'), 'testadmin@123.com')

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'),
	findTestData('Contact_1').getValue(1, 1))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'))

// Get the WebDriver instance
//WebDriver driver = DriverFactory.getWebDriver()

// Username input field object
//TestObject usernameField = findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box')

// Method to generate a random username consisting of letters only
//String generateUsername() {
//	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
//	StringBuilder username = new StringBuilder("User");
//	Random rand = new Random();
//
//	// Generate a random username of length 6
//	for (int i = 0; i < 6; i++) {
//		int index = rand.nextInt(alphabet.length());
//		username.append(alphabet.charAt(index));
//	}
//	return username.toString();
//}

// Flag to check whether the username is accepted
boolean uniqueUsername_operator_Accepted  = false;
String uniqueUsername_operator = "";

// Loop to retry generating a unique username if necessary
while (!uniqueUsername_operator_Accepted) {
	uniqueUsername_operator = generateUsername();
	println('Trying username: ' + uniqueUsername_operator);

	// Enter the generated username in the input field
	WebUI.setText(usernameField, uniqueUsername_operator);
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
		println('Username accepted: ' + uniqueUsername_operator);
		uniqueUsername_operator_Accepted = true;
	}
}
// Steps ends for creation of the username random without special char.




WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'), 'Admin@123')

WebUI.click(findTestObject('IAM Model/Page_Admin Console/input_Select role_userRole'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create new user'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/h2_Share the Credentials'), 'Share the Credentials')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Please share the credentials to the regis_554608'), 
    'Please share the credentials to the registered user')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Username'), 'Username')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Password'), 'Password')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Cancel'), 'Cancel')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create and copy'), 'Create and copy')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create and copy'))

// === Customize expected message for second toast ===
String expectedPartialThird = 'has been successfully created'

int timeoutForSecondToast = 20

// === Define Toast TestObject for second toast ===
TestObject toastObjectSecond = new TestObject('dynamicToastSecond')

toastObjectSecond.addProperty('xpath', ConditionType.EQUALS, '//div[@id=\'root\']/div[2]/div/div')

// === Wait for the second toast to appear ===
if (WebUI.waitForElementPresent(toastObjectSecond, timeoutForSecondToast)) {
    String toastText = WebUI.getText(toastObjectSecond).replaceAll('\\s+', ' ').trim()

    WebUI.comment("✅ Toast message: '$toastText'")

    if (toastText.contains(expectedPartialThird)) {
        KeywordUtil.markPassed("✅ Toast matched: '$toastText'")
    } else {
        KeywordUtil.markFailed("⚠️ Toast appeared but message mismatch. Expected: '$expectedPartialThird'. Actual: '$toastText'")
    }
} else {
    KeywordUtil.markFailed("❌ Toast message did not appear within $timeoutForSecondToast seconds.")
}



