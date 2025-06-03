//import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
//import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
//import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
//import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
//import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
//import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
//import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
//import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
//import com.kms.katalon.core.model.FailureHandling as FailureHandling
//import com.kms.katalon.core.testcase.TestCase as TestCase
//import com.kms.katalon.core.testdata.TestData as TestData
//import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
//import com.kms.katalon.core.testobject.TestObject as TestObject
//import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
//import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
//import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
//import internal.GlobalVariable as GlobalVariable
//import org.openqa.selenium.By as By
//import org.openqa.selenium.Keys as Keys
//import org.openqa.selenium.WebDriver as WebDriver
//import org.openqa.selenium.WebElement as WebElement
//import java.util.List as List
//import com.kms.katalon.core.testobject.ConditionType as ConditionType
//
//WebUI.openBrowser('')
//
//WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')
//
//WebUI.setText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Username_loginId'), 'adminuserr')
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Password_loginPassword'), 
//    'JBaPNhID5RC7zcsLVwaWIA==')
//
//WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_Sign in'))
//
//WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/div_User'))
//
//WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_Users'))
//
////CREATION OF THE USER
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_User'))
//
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Users'))
//
//WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'), 'Create User')
//
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))
//
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'))
//
//WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'), 'pawan')
//
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'))
//
//WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'), 'testadmin@123.com')
//
//// Click to focus on the username input field
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'))
//
//// Get the WebDriver instance
//WebDriver driver = DriverFactory.getWebDriver()
//
//// Username input field object
//TestObject usernameField = findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box')
//
//// Method to generate a random username consisting of letters only
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
//
//// Flag to check whether the username is accepted
//boolean usernameAccepted = false;
//String uniqueUsername = "";
//
//// Loop to retry generating a unique username if necessary
//while (!usernameAccepted) {
//	uniqueUsername = generateUsername();
//	println('Trying username: ' + uniqueUsername);
//
//	// Enter the generated username in the input field
//	WebUI.setText(usernameField, uniqueUsername);
//	WebUI.delay(1);
//
//	// Check if the error message for "username already taken" is displayed
//	boolean isError = WebUI.verifyElementPresent(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_Username already taken'),
//		1, FailureHandling.OPTIONAL);
//
//	if (isError) {
//		println('Username already taken. Retrying...');
//
//		// Clear the username field (platform-independent way)
//		WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'));
//
//		// Platform-independent text selection and clearing
//		if (System.getProperty('os.name').toLowerCase().contains('mac')) {
//			WebUI.sendKeys(usernameField, Keys.chord(Keys.COMMAND, 'a'));
//		} else {
//			WebUI.sendKeys(usernameField, Keys.chord(Keys.CONTROL, 'a'));
//		}
//		WebUI.sendKeys(usernameField, Keys.chord(Keys.BACK_SPACE));
//	} else {
//		println('Username accepted: ' + uniqueUsername);
//		usernameAccepted = true;
//	}
//}
//// Steps ends for creation of the username random without special char.
//
//WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'))
//
//WebUI.sendKeys(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'),
//	findTestData('Contact_1').getValue(1, 1))
//
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'))
//
//WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'), 'Admin@123')
//
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Reviewer_userRole'))
//
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create new user'))
//
//WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/h2_Share the Credentials'), 'Share the Credentials')
//
//WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Please share the credentials to the regis_554608'),
//	'Please share the credentials to the registered user')
//
//WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Username'), 'Username')
//
//WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/p_Password'), 'Password')
//
//WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Cancel'), 'Cancel')
//
//WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create and copy'), 'Create and copy')
//
//WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create and copy'))
//
//// === Customize your expected dynamic message here ===
//String expectedPartial = 'has been successfully created'
//
//int timeout = 20
//
//// === Define Toast TestObject based on your structure ===
//TestObject toastObject = new TestObject('dynamicToast')
//
//toastObject.addProperty('xpath', ConditionType.EQUALS, '//div[@id=\'root\']/div[2]/div/div')
//
//// === Wait for the toast to appear ===
//if (WebUI.waitForElementPresent(toastObject, timeout)) {
//	String toastText = WebUI.getText(toastObject)
//
//	toastText = toastText.replaceAll('\\s+', ' ').trim( // normalize white space
//		)
//
//	WebUI.comment("✅ Toast message: '$toastText'")
//
//	if (toastText.contains(expectedPartial)) {
//		KeywordUtil.markPassed("✅ Toast matched: '$toastText'")
//	} else {
//		KeywordUtil.markFailed("⚠️ Toast appeared but message mismatch.Expected to contain: '$expectedPartial'Actual: '$toastText'")
//	}
//} else {
//	KeywordUtil.markFailed("❌ Toast message did not appear within $timeout seconds.")
//}
////Search filter
//
//WebUI.verifyElementVisible(findTestObject('Object Repository/Manage_user/Page_Admin Console/span_Platelet level limits_material-icons n_ecbb8f'))
//
//WebUI.verifyElementText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Platelet level limits_MuiInputBase-in_8eb492'), 
//    '')
//
//// Step 2: Enter the keyword in the search field
//String searchKeyword = 'pawan'
//
//String Exp_username = ''
//
//String Exp_role = 'administrator'
//
//String Exp_Status = 'Active'
//
//verifySearchUserByUsernameAndRole(searchKeyword, Exp_username, Exp_role, Exp_Status //search by roll acesss
//    )
//
//WebUI.verifyElementNotChecked(findTestObject('Object Repository/Manage_user/Page_Admin Console/update-button-disabled'), 
//    0)
//
//WebUI.verifyElementPresent(findTestObject('Manage_user/Page_Admin Console/button_Delete'), 0)
//
//WebUI.click(findTestObject('Manage_user/Page_Admin Console/button_Delete'))
//
//WebUI.verifyElementVisible(findTestObject('Manage_user/Page_Admin Console/img'))
//
//WebUI.verifyElementText(findTestObject('Manage_user/Page_Admin Console/h2_Confirm to delete'), 'Confirm to delete')
//
//WebUI.verifyElementText(findTestObject('Manage_user/Page_Admin Console/div_The deleted user cannot access the system and the reports ready for review with this user will be unassigned. Deleted user cannot be activated again. Do you wish to continue'), 
//    'The deleted user cannot access the system and the reports ready for review with this user will be unassigned. Deleted user cannot be activated again. Do you wish to continue')
//
//WebUI.verifyElementPresent(findTestObject('Manage_user/Page_Admin Console/button_No'), 0)
//
//WebUI.verifyElementPresent(findTestObject('Manage_user/Page_Admin Console/button_Yes'), 0)
//
//WebUI.click(findTestObject('Manage_user/Page_Admin Console/button_Yes'))
//
////toast message validation
//// === Customize your expected dynamic message here ===
//String expectedPartial = 'User deleted User deleted'
//
//int timeout = 20
//
//// === Define Toast TestObject based on your structure ===
//TestObject toastObject = new TestObject('dynamicToast')
//
//toastObject.addProperty('xpath', ConditionType.EQUALS, '//*[@role=\'alert\']')
//
//// === Wait for the toast to appear ===
//if (WebUI.waitForElementPresent(toastObject, timeout)) {
//    String toastText = WebUI.getText(toastObject)
//
//    toastText = toastText.replaceAll('\\s+', ' ').trim( // normalize white space
//        )
//
//    WebUI.comment("✅ Toast message: '$toastText'")
//
//    if (toastText.contains(expectedPartial)) {
//        KeywordUtil.markPassed("✅ Toast matched: '$toastText'")
//    } else {
//        KeywordUtil.markFailed("⚠️ Toast appeared but message mismatch.Expected to contain: '$expectedPartial'Actual: '$toastText'")
//    }
//} else {
//    KeywordUtil.markFailed("❌ Toast message did not appear within $timeout seconds.")
//}
//
//WebUI.delay(2)
//
//WebUI.click(findTestObject('View list of users/Page_Admin Console/search bar'))
//
//WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.COMMAND, 'a'))
//
//WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.BACK_SPACE //assert statusVerified == true
//        ))
//
//// Re-fetch the user to verify status update
//WebUI.setText(findTestObject('View list of users/Page_Admin Console/search bar'), searchKeyword)
//
//WebUI.delay(2 // wait for table refresh if necessary
//    )
//
//WebDriver driver = DriverFactory.getWebDriver()
//
//List<WebElement> updatedRows = driver.findElements(By.xpath('//table//tbody//tr'))
//
//boolean statusVerified = false
//
//for (WebElement row : updatedRows) {
//    List<WebElement> col = row.findElements(By.tagName('td'))
//
//    String name = (col[0]).getText()
//
//    String username = (col[1]).getText()
//
//    String userRole = (col[2]).getText()
//
//    String userStatus = (col[3]).getText()
//
//    if (username.equals(Exp_username) && userRole.equals(Exp_role)) {
//        if (userStatus.equalsIgnoreCase('Inactive')) {
//            println(userStatus)
//
//            statusVerified = true
//
//            WebUI.comment("✅ Status updated to Inactive for user: $username")
//        } else {
//            KeywordUtil.markFailed("❌ Status is '$userStatus' but expected 'Inactive' for user: $username")
//        }
//        
//        break
//    }
//}
//
//WebUI.delay(2)
//
//WebUI.click(findTestObject('View list of users/Page_Admin Console/search bar'))
//
//WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.COMMAND, 'a'))
//
//WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.BACK_SPACE //assert statusVerified == true
//        ))
//
//void verifySearchUserByUsernameAndRole(String keyword, String username, String role, String status) {
//    WebUI.setText(findTestObject('View list of users/Page_Admin Console/search bar'), keyword)
//
//    WebDriver driver = DriverFactory.getWebDriver()
//
//    List<WebElement> rows = driver.findElements(By.xpath('//table//tbody//tr'))
//
//    boolean isKeywordPresent = true
//
//    for (WebElement row : rows) {
//        List<WebElement> col = row.findElements(By.tagName('td'))
//
//        String Act_name = (col[0]).getText()
//
//        String Act_username = (col[1]).getText()
//
//        String Act_role = (col[2]).getText()
//
//        String Act_status = (col[3]).getText()
//
//        if (((Act_name.contains(keyword) && Act_username.equals(username)) && Act_role.equals(role)) && Act_status.equals(
//            status)) {
//            isKeywordPresent = true
//
//            println('name, username and role has been found and verified')
//
//            row.click()
//
//            break
//        }
//    }
//    
//    assert isKeywordPresent == true : 'Not all rows contain the keyword'
//
//    int rowCount = rows.size()
//
//    println('Total number of rows in the table: ' + rowCount)
//}
//

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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable as GlobalVariable

import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement

import java.util.List as List
import java.util.Random as Random
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

WebUI.setText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/div_User'))
WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_Users'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'), 'Create User')
WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'))
WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'), 'pawan')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'))
WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'), 'testadmin@123.com')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'))

WebDriver driver = DriverFactory.getWebDriver()
TestObject usernameField = findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box')

// Method to generate a random username
String generateUsername() {
	String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
	StringBuilder username = new StringBuilder("User")
	Random rand = new Random()
	for (int i = 0; i < 6; i++) {
		int index = rand.nextInt(alphabet.length())
		username.append(alphabet.charAt(index))
	}
	return username.toString()
}

// Generate and enter unique username
boolean usernameAccepted = false
String uniqueUsername = ""
while (!usernameAccepted) {
	uniqueUsername = generateUsername()
	println('Trying username: ' + uniqueUsername)
	WebUI.setText(usernameField, uniqueUsername)
	WebUI.delay(1)
	boolean isError = WebUI.verifyElementPresent(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_Username already taken'), 1, FailureHandling.OPTIONAL)
	if (isError) {
		println('Username already taken. Retrying...')
		WebUI.click(usernameField)
		if (System.getProperty('os.name').toLowerCase().contains('mac')) {
			WebUI.sendKeys(usernameField, Keys.chord(Keys.COMMAND, 'a'))
		} else {
			WebUI.sendKeys(usernameField, Keys.chord(Keys.CONTROL, 'a'))
		}
		WebUI.sendKeys(usernameField, Keys.BACK_SPACE)
	} else {
		println('Username accepted: ' + uniqueUsername)
		usernameAccepted = true
	}
}

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'))
WebUI.sendKeys(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Contact number (optional)_rbc-input-box'), findTestData('Contact_1').getValue(1, 1))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'))
WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'), 'Admin@123')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Reviewer_userRole'))
WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create new user'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/h2_Share the Credentials'), 'Share the Credentials')
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

WebUI.verifyElementVisible(findTestObject('Object Repository/Manage_user/Page_Admin Console/span_Platelet level limits_material-icons n_ecbb8f'))

WebUI.verifyElementText(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Platelet level limits_MuiInputBase-in_8eb492'), 
    '')

// Step 2: Enter the keyword in the search field
String searchKeyword = 'pawan'

String Exp_username = uniqueUsername

String Exp_role = 'administrator'

String Exp_Status = 'Active'

verifySearchUserByUsernameAndRole(searchKeyword, Exp_username, Exp_role, Exp_Status //search by roll acesss
    )

WebUI.verifyElementNotChecked(findTestObject('Object Repository/Manage_user/Page_Admin Console/update-button-disabled'), 
    0)

WebUI.verifyElementPresent(findTestObject('Manage_user/Page_Admin Console/button_Delete'), 0)

WebUI.click(findTestObject('Manage_user/Page_Admin Console/button_Delete'))

WebUI.verifyElementVisible(findTestObject('Manage_user/Page_Admin Console/img'))

WebUI.verifyElementText(findTestObject('Manage_user/Page_Admin Console/h2_Confirm to delete'), 'Confirm to delete')

WebUI.verifyElementText(findTestObject('Manage_user/Page_Admin Console/div_The deleted user cannot access the system and the reports ready for review with this user will be unassigned. Deleted user cannot be activated again. Do you wish to continue'), 
    'The deleted user cannot access the system and the reports ready for review with this user will be unassigned. Deleted user cannot be activated again. Do you wish to continue')

WebUI.verifyElementPresent(findTestObject('Manage_user/Page_Admin Console/button_No'), 0)

WebUI.verifyElementPresent(findTestObject('Manage_user/Page_Admin Console/button_Yes'), 0)

WebUI.click(findTestObject('Manage_user/Page_Admin Console/button_Yes'))

//toast message validation
// === Customize your expected dynamic message here ===
String expectedPartial1 = 'User deleted User deleted'

int deletetimeout = 20

// === Define Toast TestObject based on your structure ===
TestObject toastObject1 = new TestObject('dynamicToast')

toastObject.addProperty('xpath', ConditionType.EQUALS, '//*[@role=\'alert\']')

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

WebUI.delay(2)

WebUI.click(findTestObject('View list of users/Page_Admin Console/search bar'))

WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.BACK_SPACE //assert statusVerified == true
        ))

// Re-fetch the user to verify status update
WebUI.setText(findTestObject('View list of users/Page_Admin Console/search bar'), searchKeyword)

WebUI.delay(2)// wait for table refresh if necessary 

WebDriver driver1 = DriverFactory.getWebDriver()

List<WebElement> updatedRows = driver.findElements(By.xpath('//table//tbody//tr'))

boolean statusVerified = false

for (WebElement row : updatedRows) {
    List<WebElement> col = row.findElements(By.tagName('td'))

    String name = (col[0]).getText()

    String username = (col[1]).getText()

    String userRole = (col[2]).getText()

    String userStatus = (col[3]).getText()

    if (username.equals(Exp_username) && userRole.equals(Exp_role)) {
        if (userStatus.equalsIgnoreCase('Inactive')) {
            println(userStatus)

            statusVerified = true

            WebUI.comment("✅ Status updated to Inactive for user: $username")
        } else {
            KeywordUtil.markFailed("❌ Status is '$userStatus' but expected 'Inactive' for user: $username")
        }
        
        break
    }
}

WebUI.delay(2)

WebUI.click(findTestObject('View list of users/Page_Admin Console/search bar'))

WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('View list of users/Page_Admin Console/search bar'), Keys.chord(Keys.BACK_SPACE //assert statusVerified == true
        ))

void verifySearchUserByUsernameAndRole(String keyword, String username, String role, String status) {
    WebUI.setText(findTestObject('View list of users/Page_Admin Console/search bar'), keyword)

    WebDriver driver = DriverFactory.getWebDriver()

    List<WebElement> rows = driver.findElements(By.xpath('//table//tbody//tr'))

    boolean isKeywordPresent = true

    for (WebElement row : rows) {
        List<WebElement> col = row.findElements(By.tagName('td'))

        String Act_name = (col[0]).getText()

        String Act_username = (col[1]).getText()

        String Act_role = (col[2]).getText()

        String Act_status = (col[3]).getText()

        if (((Act_name.contains(keyword) && Act_username.equals(username)) && Act_role.equals(role)) && Act_status.equals(
            status)) {
            isKeywordPresent = true

            println('name, username and role has been found and verified')

            row.click()

            break
        }
    }
    
    assert isKeywordPresent == true : 'Not all rows contain the keyword'

    int rowCount = rows.size()

    println('Total number of rows in the table: ' + rowCount)
}

