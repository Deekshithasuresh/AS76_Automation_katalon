import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Commontools/input_username_loginId (14)'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/input_password_loginPassword (14)'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Commontools/td_01-Jul-2025, 1221 PM (IST)'))

WebUI.click(findTestObject('Object Repository/Commontools/span_Platelets'))

WebUI.click(findTestObject('Object Repository/Commontools/button_Morphology (2)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/button_Morphology (2)'), 'Morphology')

WebUI.click(findTestObject('Object Repository/Commontools/span_References (9)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/div_Large Platelets (2)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/div_Large Platelets (2)'), 'Large Platelets')

WebUI.click(findTestObject('Object Repository/Commontools/div_Large Platelets (2)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/li_Platelet Clumps (1)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/li_Platelet Clumps (1)'), 'Platelet Clumps')

