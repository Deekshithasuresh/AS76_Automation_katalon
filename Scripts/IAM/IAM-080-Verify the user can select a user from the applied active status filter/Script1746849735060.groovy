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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import applyFilterAndVerify.ApplyFilterAndVerifyTheList as ApplyFilterAndVerifyTheList

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/View list of users/Page_Admin Console/input_Username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/View list of users/Page_Admin Console/input_Password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Users'))

WebUI.verifyElementVisible(findTestObject('Object Repository/View list of users/Page_Admin Console/span_Platelet level limits_material-icons n_ecbb8f'))

WebUI.verifyElementText(findTestObject('Object Repository/View list of users/Page_Admin Console/input_Platelet level limits_MuiInputBase-in_8eb492'),
	'')

WebUI.verifyElementVisible(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Platelet level limits_MuiButtonBase-_5dde88'))


////For active role users
//// ✅ Apply same flow for all roles
//ApplyFilterAndVerifyTheList applyFilter = new ApplyFilterAndVerifyTheList()
//
//def roles = ['operator', 'administrator', 'reviewer']
//def statuses = ['Active']
//
//for (String role : roles) {
//	KeywordUtil.logInfo(">>> Applying filter for role: " + role)
//
//	// Apply filters
//	applyFilter.applyRoleAndStatusFiltersM([role], statuses)
//
//	// Verify filtered users
//	applyFilter.verifyFilteredUsersByRoleAndStatusM([role], statuses)
//
//	// Wait for filtered results
//	WebUI.delay(2)
//
//	// Click first user in the filtered list
//	TestObject firstUser = new TestObject('dynamicUser')
//	firstUser.addProperty('xpath', ConditionType.EQUALS, "(//table//tr)[2]") // Adjust if your list format is different
//	WebUI.click(firstUser)
//
//	// click on cancel button
//
//	WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/path'))
//
//
//	// Clear filters before the next iteration
//	WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Clear filters'))
//	WebUI.delay(1)
//}

// ✅ Apply same flow for all roles and both statuses
ApplyFilterAndVerifyTheList applyFilter = new ApplyFilterAndVerifyTheList()

def roles = ['operator', 'administrator', 'reviewer']
def statuses = ['Active', 'Inactive'] // ⬅️ Still filter both, but skip clicking inactive

for (String role : roles) {
	KeywordUtil.logInfo(">>> Applying filter for role: " + role)

	// Apply filters
	applyFilter.applyRoleAndStatusFiltersM([role], statuses)

	// Verify filtered users
	applyFilter.verifyFilteredUsersByRoleAndStatusM([role], statuses)

	// Wait for filtered results
	WebUI.delay(2)

	// ✅ Get status of the first user in the list
	TestObject statusCell = new TestObject('statusCell')
	statusCell.addProperty('xpath', ConditionType.EQUALS, "(//table//tr)[2]/td[last()]") // Adjust index if needed

	String userStatus = WebUI.getText(statusCell).trim()
	KeywordUtil.logInfo("First user status: " + userStatus)

	if (userStatus == 'Active') {
		// ✅ Click first user if Active
		TestObject firstUser = new TestObject('dynamicUser')
		firstUser.addProperty('xpath', ConditionType.EQUALS, "(//table//tr)[2]") // Adjust if needed
		WebUI.click(firstUser)

		// ✅ Click cancel/back
		WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/Page_Admin Console/Page_Admin Console/button_Create User_Xicon'))
	} else {
		KeywordUtil.logInfo("User is Inactive. Skipping click.")
	}

	// Clear filters before the next role
	WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Clear filters'))
	WebUI.delay(1)
}



// this method is for selecting random active user.
//import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
//import org.openqa.selenium.WebElement as WebElement
//import java.util.Random
//
//ApplyFilterAndVerifyTheList applyFilter = new ApplyFilterAndVerifyTheList()
//
//def roles = ['operator', 'administrator', 'reviewer']
//def statuses = ['Active', 'Inactive']
//
//for (String role : roles) {
//	KeywordUtil.logInfo(">>> Applying filter for role: " + role)
//
//	// Apply filters
//	applyFilter.applyRoleAndStatusFiltersM([role], statuses)
//
//	// Verify filtered users
//	applyFilter.verifyFilteredUsersByRoleAndStatusM([role], statuses)
//
//	WebUI.delay(2)
//
//	// Get total user rows (excluding header row)
//	List<WebElement> userRows = WebUiCommonHelper.findWebElements(
//		new TestObject().addProperty('xpath', ConditionType.EQUALS, "//table//tr[position()>1]"),
//		30
//	)
//	int totalUsers = userRows.size()
//	KeywordUtil.logInfo("Total users in filtered list: " + totalUsers)
//
//	if (totalUsers == 0) {
//		KeywordUtil.logInfo("No users found for role: " + role)
//		continue
//	}
//
//	// Pick a random row (2-based because tr[1] is header)
//	int randomRowIndex = new Random().nextInt(totalUsers) + 2
//	String statusXpath = "(//table//tr)[" + randomRowIndex + "]/td[last()]"
//	TestObject statusCell = new TestObject('statusCell')
//	statusCell.addProperty('xpath', ConditionType.EQUALS, statusXpath)
//
//	String userStatus = WebUI.getText(statusCell).trim()
//	KeywordUtil.logInfo("Random user status: " + userStatus)
//
//	if (userStatus == 'Active') {
//		TestObject randomUser = new TestObject('randomUser')
//		randomUser.addProperty('xpath', ConditionType.EQUALS, "(//table//tr)[" + randomRowIndex + "]")
//		WebUI.waitForElementVisible(randomUser, 10)
//		WebUI.waitForElementClickable(randomUser, 10)
//		WebUI.click(randomUser)
//
//		// Click cancel/back
//		WebUI.click(findTestObject('Object Repository/Manage_user/Page_Admin Console/path'))
//	} else {
//		KeywordUtil.logInfo("Random user is Inactive. Skipping click.")
//	}
//
//	// Clear filters
//	WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Clear filters'))
//	WebUI.delay(1)
//}







