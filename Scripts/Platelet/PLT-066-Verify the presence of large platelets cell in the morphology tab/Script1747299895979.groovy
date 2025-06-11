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

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/span_My reports'), 30)

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146') // click on 1st row report
    )

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'), 'Morphology')

WebUI.verifyElementClickable(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/div_Large Platelets'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Platelets/Page_PBS/div_Large Platelets'), 'Large Platelets')

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/img'), 30)

WebUI.mouseOver(findTestObject('Object Repository/Platelets/Page_PBS/img'))

WebUI.verifyElementText(findTestObject('Object Repository/Platelets/Page_PBS/div_Large Platelets include Macro and Giant_2714a9'), 
    'Large Platelets include Macro and Giant Platelets')

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/div_Platelet Clumps'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Platelets/Page_PBS/div_Platelet Clumps'), 'Platelet Clumps')

