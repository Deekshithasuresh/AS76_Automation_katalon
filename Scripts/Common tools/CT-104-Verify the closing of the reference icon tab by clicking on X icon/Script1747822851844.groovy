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

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId (18)'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword (18)'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In (18)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_02-May-2025, 0938 AM (IST) (2)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (8)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/div_References (9)'), 'References')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_References (9)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_References (9)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/img (3)'), 0)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/img (3)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/div_References (9)'), 'References')

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/div_CBC report (2)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/div_CBC report (2)'), 'CBC report')

