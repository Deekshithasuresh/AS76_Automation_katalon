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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Users'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'), 'Create User')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/h2_Create User'), 'Create User')

WebUI.verifyElementVisible(findTestObject('Object Repository/IAM Model/Page_Admin Console/Page_Admin Console/Page_Admin Console/button_Create User_Xicon'))

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/h4_User details'), 'User details')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/span_Email'), 'Email')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/span_Contact number (optional)'), 
    'Contact number (optional)')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/span_Username'), 'Username')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/span_Password'), 'Password')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/h4_Select role'), 'Select role')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/h4_Select role'), 'Select role')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/label_Operator'), 'Operator')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/label_Reviewer'), 'Reviewer')

WebUI.verifyElementText(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Cancel'), 'Cancel')

WebUI.verifyElementNotClickable(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_Disabled-Create new user'))

