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

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/h4_Sign in'), 
    'Sign in')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/label_Username'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/label_Username'), 
    'Username')

WebUI.verifyElementPresent(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    30)

// Get the placeholder attribute
String placeholder = WebUI.getAttribute(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'),
	'placeholder')

// Verify placeholder
WebUI.verifyMatch(placeholder, 'Enter username', false)
