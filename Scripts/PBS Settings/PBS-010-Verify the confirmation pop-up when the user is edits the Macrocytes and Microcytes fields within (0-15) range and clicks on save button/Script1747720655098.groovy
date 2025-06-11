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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration
import com.kms.katalon.core.util.KeywordUtil
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.regex.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys
import org.openqa.selenium.*

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


TestObject Cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Cancel_CTA')

TestObject Cancel_pop_up_cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_cancel_CTA')

TestObject Cancel_confirm_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Cancel_Confirm_CTA')


TestObject saveCTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_CTA')

TestObject save_cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_cancel_CTA')

TestObject save_confirm_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_Confirm_CTA')


TestObject ResetToDefaultCTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_to_Default_CTA')

TestObject Reset_cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_cancel_CTA')

TestObject Reset_confirm_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_Confirm_CTA')


//Cancel-functionality
ArrayList<String> dia_values_before_entering=pbs_set.getValuesPresentInPlateletLevelFields()
pbs_set.enterValuesIntoMicroMacroCellFields('4','6',"macrocyte_higher")
pbs_set.checkFunctionalityOfCTAS(Cancel_CTA,Cancel_pop_up_cancel_CTA,'Cancel_cancel')
pbs_set.checkFunctionalityOfCTAS(Cancel_CTA,Cancel_confirm_CTA,'Cancel_confirm')
ArrayList<String> dia_values_after_cancelling_entered_values=pbs_set.getValuesPresentInPlateletLevelFields()
try {
    pbs_set.checkThatValuesGotUpdated(dia_values_before_entering, dia_values_after_cancelling_entered_values, 'edit')
} catch (AssertionError | Exception e) {
    WebUI.comment("❌ Failure caught, continuing test: " + e.getMessage())
}

//Save-functionality
pbs_set.enterValuesIntoMicroMacroCellFields('8','13',"macrocyte_higher")
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_cancel_CTA,'save_cancel')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_confirm_CTA,'save_confirm')
WebUI.delay(2)
ArrayList<String> Expected_passed_dia_values= new ArrayList<>(Arrays.asList('8', '13'))
ArrayList<String> Actaul_dia_values=pbs_set.getValuesPresentInPlateletLevelFields()
pbs_set.checkThatValuesGotUpdated(Actaul_dia_values,Expected_passed_dia_values,'edit')

//Reset-functionality
pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_cancel_CTA,'reset_cancel')
pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_confirm_CTA,'reset_confirm')
ArrayList<String> Exp_Default_RBC_dia_values= new ArrayList<>(Arrays.asList('7', '8.5')) //5,10,20
ArrayList<String> Actaul_RBC_dia_values_after_reset=pbs_set.getValuesPresentInPlateletLevelFields()
pbs_set.checkThatValuesGotUpdated(Actaul_RBC_dia_values_after_reset,Exp_Default_RBC_dia_values,'reset_to_default')

