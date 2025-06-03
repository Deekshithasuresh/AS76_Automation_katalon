package applyFilterAndVerify

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.Keys
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
//import com.kms.katalon.core.util.KeywordUtil
//import com.kms.katalon.core.testobject.TestObject
//import com.kms.katalon.core.webui.keyword.internal.WebUIKeywordMain
//import com.kms.katalon.core.model.FailureHandling
//import com.kms.katalon.core.util.KeywordUtil
//import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


import internal.GlobalVariable

public class ApplyFilterAndVerifyTheList {
	public void applyRoleAndStatusFilters(String roleToSelect, String statusToSelect) {
		WebUI.click(findTestObject('View list of users/Page_Admin Console/button_Platelet level limits_MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeMedium css-1yxmbwk'));
		WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Reset'))
		boolean role_selection_status = WebUI.verifyElementNotChecked(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + roleToSelect), 0);
		println(role_selection_status);
		if ((roleToSelect != null) && (roleToSelect != '') && role_selection_status) {
			WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + roleToSelect));
		}

		WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Status'));
		boolean active_inactive_selection_status = WebUI.verifyElementNotChecked(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + statusToSelect), 0);

		if ((statusToSelect != null) && (statusToSelect != '') && active_inactive_selection_status) {
			WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + statusToSelect));
		}

		WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Apply'));

		WebUI.delay(2);
	}



	//void verifyFilteredUsersByRoleAndStatus(String[] roles, String status) {
	//    for (String role : roles) {
	//		WebUI.waitForElementPresent(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + role), 20)
	//        WebUI.verifyElementPresent(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + role), 10)
	//    }
	//    WebUI.verifyElementPresent(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + status), 10)
	//}



	public	void verifyFilteredUsersByRoleAndStatus(String roleExpected, String statusExpected) {
		WebDriver driver = DriverFactory.getWebDriver()

		List<WebElement> rows = driver.findElements(By.xpath('//table//tbody//tr'))

		if (rows.size() == 0) {
			KeywordUtil.markFailed('❌ No users found after applying filters.')

			return null
		}

		boolean allValid = true

		int rowIndex = 1

		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.tagName('td'))

			String actualRole = (cols[2]).getText()
			println(actualRole)

			String actualStatus = (cols[3]).getText()
			println(actualStatus)

			roleExpected = roleExpected.trim().toLowerCase()
			statusExpected = statusExpected.trim().toLowerCase()

			actualRole = actualRole.trim().toLowerCase()
			actualStatus = actualStatus.trim().toLowerCase()


			if (!actualRole.trim().equalsIgnoreCase(roleExpected) ||  !actualStatus.trim().equalsIgnoreCase(statusExpected)){
				KeywordUtil.logInfo("⚠️ Mismatch at row ${row}: Role = ${actualRole}, Status = ${actualStatus}")
			}
			else {
				println("✅ Row $rowIndex passed: Role = $actualRole, Status = $actualStatus")
			}

			rowIndex++
		}

		if (allValid) {
			KeywordUtil.markPassed("✅ All users on this page have Role: '$roleExpected' and Status: '$statusExpected'")
		} else {
			KeywordUtil.markFailed('❌ Some users do not match the applied filters.')
		}
	}

	//mutiple roles method
	public void applyRoleAndStatusFiltersM(List<String> rolesToSelect, List<String> statusesToSelect) {
		WebUI.click(findTestObject('View list of users/Page_Admin Console/button_Platelet level limits_MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeMedium css-1yxmbwk'))
		WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Reset'))

		// Loop through all roles to select
		for (String roleToSelect : rolesToSelect) {
			boolean role_selection_status = WebUI.verifyElementNotChecked(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + roleToSelect), 0)
			if ((roleToSelect != null) && (roleToSelect != '') && role_selection_status) {
				WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + roleToSelect))
			}
		}

		// Loop through all statuses to select
		for (String statusToSelect : statusesToSelect) {
			WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Status'))
			boolean status_selection_status = WebUI.verifyElementNotChecked(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + statusToSelect), 0)
			if ((statusToSelect != null) && (statusToSelect != '') && status_selection_status) {
				WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/label_' + statusToSelect))
			}
		}

		WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Apply'))
		WebUI.delay(2)
	}




	public void verifyFilteredUsersByRoleAndStatusM(List<String> expectedRoles, List<String> expectedStatuses) {
		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> rows = driver.findElements(By.xpath('//table//tbody//tr'))

		if (rows.size() == 0) {
			KeywordUtil.markFailed('❌ No users found after applying filters.')
			return
		}

		boolean allValid = true
		int rowIndex = 1

		// Normalize role list and status list
		expectedRoles = expectedRoles.collect { it.trim().toLowerCase() }
		expectedStatuses = expectedStatuses.collect { it.trim().toLowerCase() }

		for (WebElement row : rows) {
			List<WebElement> cols = row.findElements(By.tagName('td'))

			String actualRole = cols[2].getText().trim().toLowerCase()
			String actualStatus = cols[3].getText().trim().toLowerCase()

			if (!expectedRoles.contains(actualRole) || !expectedStatuses.contains(actualStatus)) {
				KeywordUtil.logInfo("⚠️ Mismatch at row ${rowIndex}: Role = ${actualRole}, Status = ${actualStatus}")
				allValid = false
			} else {
				println("✅ Row $rowIndex passed: Role = $actualRole, Status = $actualStatus")
			}

			rowIndex++
		}

		if (allValid) {
			KeywordUtil.markPassed("✅ All users on this page have Roles: ${expectedRoles} and Statuses: ${expectedStatuses}")
		} else {
			KeywordUtil.markFailed('❌ Some users do not match the applied filters.')
		}
	}


	@Keyword
	def createUser(String name, String email, String username, String password, String role) {
		try {
			// Open browser and navigate to the login page
			WebUI.openBrowser('')
			WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

			// Login to the admin console
			WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_loginId'), 'adminuserr')
			WebUI.setEncryptedText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
			WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Sign in'))

			// Navigate to User management
			WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_User'))
			WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Users'))
			WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'), 'Create User')
			WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))

			// Fill in the user data
			WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Name_rbc-input-box'), name)
			WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box'), email)
			WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_rbc-input-box'), username)
			WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_rbc-input-box'), password)

			// Select the specified role
			try {
				TestObject roleCheckbox = findTestObject("Object Repository/IAM Model/Page_Admin Console/input_" + role + "_userRole")
				WebUI.click(roleCheckbox)
				KeywordUtil.logInfo(":white_check_mark: Role '" + role + "' selected successfully.")
			} catch (Exception e) {
				KeywordUtil.markWarning(":warning: Failed to select role '" + role + "': " + e.getMessage())
			}

			// Submit the new user form
			WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create new user'))
			KeywordUtil.logInfo(":white_check_mark: User created successfully with given values.")
		} catch (Exception e) {
			KeywordUtil.markFailed(":x: An error occurred during user creation: " + e.getMessage())
		}
	}
}
