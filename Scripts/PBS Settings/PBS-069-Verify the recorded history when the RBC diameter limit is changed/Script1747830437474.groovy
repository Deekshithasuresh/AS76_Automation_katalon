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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import loginPackage.Login as Login
import adimin_pbs_Settings.PBS_Settings as PBS_Settings
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.util.ArrayList as ArrayList
import java.util.Arrays as Arrays
import java.util.List as List
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import java.time.Duration as Duration
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import java.util.regex.*
import org.openqa.selenium.Dimension as Dimension
import org.openqa.selenium.*

Login lg = new Login()

PBS_Settings pbs_set = new PBS_Settings()

lg.AdminLogin('prem', 'prem@2807')

WebUI.verifyElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 10)

WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_RBC diameter limits'),20)


WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_RBC diameter limits'))

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Edit settings'))

TestObject saveCTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_CTA')

TestObject save_cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_cancel_CTA')

TestObject save_confirm_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_Confirm_CTA')

TestObject ResetToDefaultCTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_to_Default_CTA')

TestObject Reset_cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_cancel_CTA')

TestObject Reset_confirm_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_Confirm_CTA')


pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_confirm_CTA,'reset_confirm')

ArrayList<String> Actaul_dia_values_before_saving=pbs_set.getValuesPresentInPlateletLevelFields()

pbs_set.enterValuesIntoMicroMacroCellFields('4','6',"macrocyte_higher")

pbs_set.checkFunctionalityOfCTAS(saveCTA,save_confirm_CTA,'save_confirm')

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/X_icon'))

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/svg_Admin Portal_MuiSvgIcon-root MuiSvgIcon_5f2eb8'))

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/li_History'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/li_History'), 10)

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/li_History'))

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/div_History'), 10)

String username='prem'  //change the username with which user we have logged in

assert pbs_set.verifyChangesGettingRecordedInHistory("RBC diameter",username+" updated RBC diamter of microcyte","7.0","4.0")== true

assert pbs_set.verifyChangesGettingRecordedInHistory("RBC diameter",username+" updated RBC diamter of macrocyte","8.5","6.0")== true













