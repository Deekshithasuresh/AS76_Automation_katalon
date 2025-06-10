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

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Commontools/input_username_loginId (1)'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/input_password_loginPassword (1)'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/button_Sign In (1)'))

WebUI.click(findTestObject('Object Repository/Commontools/td_29-May-2025, 1154 AM (IST)'))

WebUI.click(findTestObject('Object Repository/Commontools/button_WBC'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/button_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/Commontools/span_References'))

WebUI.click(findTestObject('Object Repository/Commontools/span_References'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/div_Neutrophils'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Neutrophils'), 'Neutrophils')

WebUI.click(findTestObject('Object Repository/Commontools/div_Neutrophils'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Band Forms'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Band Forms'), 'Band Forms')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Hypersegmented'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Hypersegmented'), 'Hypersegmented')

