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

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Change password'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Current password_old-password'), 
    'jyothi@1995')

//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Current password_MuiButtonBase-root _951963'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_New password_new-password'), 
    'jyo@199')

//WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/img_1'))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Confirm password_confirm-password'), 
    'jyo@199')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/p_Password must be atleast 8 characters long'), 
    'Password must be atleast 8 characters long')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Contains at-least 8 characters'), 
    'Contains at-least 8 characters')

