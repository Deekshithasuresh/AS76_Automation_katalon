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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import loginPackage.Login as Login
import adimin_pbs_Settings.PBS_Settings
Login lg = new Login()

PBS_Settings pbs_set = new PBS_Settings()

lg.AdminLogin('prem', 'prem@2807')

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 
    10)

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_RBC diameter limits'))

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Edit settings'))

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_Microcytes'), 
    0)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_Microcytes'), 10)

pbs_set.enterValuesIntoMicroMacroCellFields('5','5',"macrocyte_higher")

pbs_set.enterValuesIntoMicroMacroCellFields('4','3',"microcyte_higher")

pbs_set.enterValuesIntoMicroMacroCellFields('1','2',"macrocyte_higher")


