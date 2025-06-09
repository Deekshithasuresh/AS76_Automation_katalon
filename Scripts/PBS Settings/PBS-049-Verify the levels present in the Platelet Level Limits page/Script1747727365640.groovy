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
import loginPackage.Login as Login
import adimin_pbs_Settings.PBS_Settings as PBS_Settings

Login lg = new Login()

PBS_Settings pbs_set = new PBS_Settings()

lg.AdminLogin('prem', 'prem@2807')

WebUI.verifyElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 20)

WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Platelet level limits'))

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Edit settings'))

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/h2_Platelet level limits ( x 109L)'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/h2_Platelet level limits ( x 109L)'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/span_Significantly decreased'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Significantly decreased_rbc-input-box'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/span_Decreased'), 20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Decreased_input-box'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Decreased_rbc-input-box'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/span_Normal'), 20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Normal_input-box'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Normal_rbc-input-box'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/input_Increased_input-box'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_Platelet level limits'), 
    20)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/X_icon'), 20)

