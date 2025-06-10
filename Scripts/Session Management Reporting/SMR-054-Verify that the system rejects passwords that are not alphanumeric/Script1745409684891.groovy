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

Login log = new Login()

log.login()

//WebUI.openBrowser('')
//
//WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
//
//WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_username_loginId'), 'vishwajyothi')
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_password_loginPassword'), 
//    'jFUrjnaiyLP15wudMGXDibWIAswG2Ynx')
//
//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/button_Sign In'))
WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/li_Change Password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Current password_old-password'), 
    'jyothi@1995')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_New password_new-password'), 
    'jyothi@@')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_PBS/input_Confirm password_confirm-password'), 
    'jyothi@@')

//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_PBS/img_1'))
WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/p_Password must have atleast 1 alphabet and number'), 
    'Password must have atleast 1 alphabet and number')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Is alphanumeric'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_PBS/div_Is alphanumeric'), 'Is alphanumeric')

