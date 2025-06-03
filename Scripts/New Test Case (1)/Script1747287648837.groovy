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

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'C')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'))

WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_22-Apr-2025, 0646 PM (IST)'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_WBC'))

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_References'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_References'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Neutrophils'))

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Neutrophils'), 'Neutrophils')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Band Forms'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Neutrophils'))

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Band Forms'), 'Band Forms')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Hypersegmented'), 'Hypersegmented')

