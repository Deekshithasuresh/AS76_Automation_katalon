package loginPack

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

import internal.GlobalVariable

public class Login {

	public void login() {

		WebUI.openBrowser('')

		WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

		WebUI.waitForElementVisible(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), 10)

		WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), 'jyothi')

		WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'),
				'jyothi@1995')

		WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In_for_PBS login'))
	}

	public void adminLogin() {
		WebUI.openBrowser('')

		WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

		WebUI.waitForElementVisible(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'),10)

		WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'),
				'jyothi')

		WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'),
				'jyothi@1995')

		WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))
	}
}
