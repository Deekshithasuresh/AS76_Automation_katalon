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

WebUI.setText(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/input_Username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/input_Password_loginPassword'), 
    'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/button_Sign in'))

//Script for verifying the  profile icon options in all the options in admin.
WebUI.verifyElementPresent(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/img'), 0)

WebUI.click(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/img_1'))

WebUI.click(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/button_Users'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/img'), 0)

WebUI.click(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/img_1_2'))

WebUI.click(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/button_RBC diameter limits'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Admin-Profile-icon/Page_Admin Console/img'), 0)

