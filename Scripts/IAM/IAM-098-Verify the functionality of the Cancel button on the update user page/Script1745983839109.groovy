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
WebUI.sendKeys(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'), 'pawan kumar')

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

 timeout = 20

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
String searchKeyword = 'pawan kumar'

String Exp_username = uniqueUsername

String Exp_role = 'administrator'

String Exp_Status = 'Active'


verifySearchUserByUsernameAndRole(searchKeyword, Exp_username, Exp_role, Exp_Status //search by roll acesss
    )
verifySearchUserByUsernameAndRole(searchKeyword, Exp_username, Exp_role, Exp_Status //search by roll acesss
    )

WebUI.verifyElementNotChecked(findTestObject('Object Repository/Manage_user/Page_Admin Console/update-button-disabled'), 
    0)

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Name_rbc-input-box'))

WebUI.sendKeys(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Name_rbc-input-box'), Keys.chord(Keys.COMMAND, 
        'a'))

WebUI.sendKeys(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Name_rbc-input-box'), Keys.chord(Keys.BACK_SPACE))

WebUI.sendKeys(findTestObject('Object Repository/Manage_user/Page_Admin Console/input_Name_rbc-input-box'), 'adminsigtuple')

WebUI.click(findTestObject('Manage_user/Page_Admin Console/button_Update'))

WebUI.click(findTestObject('Manage_user/Page_Admin Console/button_Cancel'))

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_Cancel'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Manage_user/Page_Admin Console/img'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Manage_user/Page_Admin Console/h2_Confirm to cancel'), 'Confirm to cancel')

WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/div_Are you sure you want to cancel'))

WebUI.verifyElementText(findTestObject('Object Repository/Manage_user/Page_Admin Console/div_Are you sure you want to cancel'), 
    'Are you sure you want to cancel?')

WebUI.verifyElementText(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_No'), 'No')

WebUI.verifyElementText(findTestObject('Object Repository/Manage_user/Page_Admin Console/button_Yes'), 'Yes')

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

