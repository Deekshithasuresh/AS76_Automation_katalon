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

WebUI.setText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/td_18123-F-MGG'))

WebUI.verifyElementClickable(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/span_CBC report'), 'CBC report')

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'), 
    30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/span_Peripheral Smear Report'), 
    30)

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_Summary'), 30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_WBC'))

WebUI.verifyElementClickable(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 'CBC report')

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'), 
    30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_References'), 30)

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_WBC'), 30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_RBC'))

WebUI.verifyElementClickable(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 'CBC report')

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'), 
    30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_References'), 30)

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_Size'), 30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_Shape'))

WebUI.verifyElementClickable(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 'CBC report')

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'), 
    30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_References'), 30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_Color'))

WebUI.verifyElementClickable(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 'CBC report')

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'), 
    30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_References'), 30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_Inclusions'))

WebUI.verifyElementClickable(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 'CBC report')

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'), 
    30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_References'), 30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_Platelets'))

WebUI.verifyElementClickable(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 'CBC report')

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'), 
    30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/p_Platelet Clumps'), 30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_Morphology'))

WebUI.verifyElementClickable(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_CBC report_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report'), 'CBC report')

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'), 
    30)

WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_CBC report_MuiButtonBase-root MuiIco_ee341f'))

WebUI.verifyElementPresent(findTestObject('Object Repository/View CBC Report Information/Page_PBS/div_References'), 30)

