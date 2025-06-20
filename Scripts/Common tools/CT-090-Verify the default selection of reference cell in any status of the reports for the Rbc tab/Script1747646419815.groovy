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

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId (11)'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword (11)'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In (11)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/td_19-May-2025, 0931 AM (IST)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_RBC (1)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_RBC (1)'), 'RBC')

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_Size'), 'Size')

WebUI.click(findTestObject('Object Repository/Commontools/span_References (4)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Microcytes (1)'), 'Microcytes')

WebUI.verifyElementNotPresent(findTestObject('Object Repository/Commontools/div_Microcytes (1)'), 0)