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
import adimin_pbs_Settings.PBS_Settings

Login lg = new Login()

PBS_Settings pbs_set =new PBS_Settings()

lg.AdminLogin('prem', 'prem@2807')

WebUI.verifyElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 10)

WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))

WebUI.verifyElementPresent(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_RBC grade limits'), 10)

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_RBC grade limits'))

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Edit_button'))

WebUI.delay(3)

TestObject Cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Cancel_CTA')

TestObject Cancel_pop_up_cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_cancel_CTA')

TestObject Cancel_confirm_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Cancel_Confirm_CTA')


TestObject saveCTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_CTA')

TestObject save_cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_cancel_CTA')

TestObject save_confirm_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_Confirm_CTA')


TestObject ResetToDefaultCTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_to_Default_CTA')

TestObject Reset_cancel_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_cancel_CTA')

TestObject Reset_confirm_CTA = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Reset_Confirm_CTA')

//For size tab
//cancel-functionality
ArrayList<String> grade_values_before_entering=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.EneterValueInUpperLimitFields('23', '45','enter correct values')
pbs_set.checkFunctionalityOfCTAS(Cancel_CTA,Cancel_pop_up_cancel_CTA,'Cancel_cancel')
pbs_set.checkFunctionalityOfCTAS(Cancel_CTA,Cancel_confirm_CTA,'Cancel_confirm')
ArrayList<String> grade_values_after_cancelling_entered_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(grade_values_before_entering,grade_values_after_cancelling_entered_values,'cancel')

//save-functionality
pbs_set.EneterValueInUpperLimitFields('67', '91','enter correct values')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_cancel_CTA,'save_cancel')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_confirm_CTA,'save_confirm')
WebUI.delay(2)
ArrayList<String> Expected_passed_grade_size_values= new ArrayList<>(Arrays.asList('67', '91'))
ArrayList<String> Actaul_Grade_size_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Grade_size_values,Expected_passed_grade_size_values,'edit')

//reset-functionality
pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_cancel_CTA,'reset_cancel')
pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_confirm_CTA,'reset_confirm')
ArrayList<String> Exp_Default_RBC_size_grade_values= new ArrayList<>(Arrays.asList('5', '20','10','20','10','20')) //5,10,20
ArrayList<String> Actaul_Size_Grade_values_after_reset=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Size_Grade_values_after_reset,Exp_Default_RBC_size_grade_values,'reset_to_default')


//For shape tab
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Close_CTA'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Shape_button'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Edit_button'))

//cancel-functionality
ArrayList<String> shape_grade_values_before_entering=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.EneterValueInUpperLimitFields('39', '84','enter correct values')
pbs_set.checkFunctionalityOfCTAS(Cancel_CTA,Cancel_pop_up_cancel_CTA,'Cancel_cancel')
pbs_set.checkFunctionalityOfCTAS(Cancel_CTA,Cancel_confirm_CTA,'Cancel_confirm')
ArrayList<String> shape_grade_values_after_cancelling_entered_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(shape_grade_values_before_entering,shape_grade_values_after_cancelling_entered_values,'cancel')

//save-functionality
pbs_set.EneterValueInUpperLimitFields('01', '99','enter correct values')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_cancel_CTA, 'save_cancel')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_confirm_CTA,'save_confirm')
WebUI.delay(2)
ArrayList<String> Expected_passed_grade_shape_values= new ArrayList<>(Arrays.asList('01', '99'))
ArrayList<String> Actaul_Grade_shape_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Grade_shape_values,Expected_passed_grade_shape_values,'edit')

//reset-functionality
pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_cancel_CTA,'reset_cancel')
pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_confirm_CTA,'reset_confirm')
ArrayList<String> Exp_Default_RBC_shape_grade_values= new ArrayList<>(Arrays.asList('5', '20','5','20','5','20','1','2','6','20','10','20','1','2','5','20','5','20')) //5,10,20
ArrayList<String> Actaul_Shape_Grade_values_after_reset=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Shape_Grade_values_after_reset,Exp_Default_RBC_shape_grade_values,'reset_to_default')


//For color tab
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Close_CTA'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Color_button'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Edit_button'))

//cancel-functionality
ArrayList<String> color_grade_values_before_entering=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.EneterValueInUpperLimitFields('50', '92','enter correct values')
pbs_set.checkFunctionalityOfCTAS(Cancel_CTA,Cancel_pop_up_cancel_CTA,'Cancel_cancel')

pbs_set.checkFunctionalityOfCTAS(Cancel_CTA,Cancel_confirm_CTA,'Cancel_confirm')
ArrayList<String> color_grade_values_after_cancelling_entered_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(color_grade_values_before_entering,color_grade_values_after_cancelling_entered_values,'cancel')

//save-functionality
pbs_set.EneterValueInUpperLimitFields('34', '78','enter correct values')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_cancel_CTA, 'save_cancel')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_confirm_CTA,'save_confirm')
WebUI.delay(2)
ArrayList<String> Expected_passed_grade_color_values= new ArrayList<>(Arrays.asList('34', '78'))
ArrayList<String> Actaul_Grade_color_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Grade_color_values,Expected_passed_grade_color_values,'edit')

//reset-functionality
pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_cancel_CTA,'reset_cancel')
pbs_set.checkFunctionalityOfCTAS(ResetToDefaultCTA,Reset_confirm_CTA,'reset_confirm')
ArrayList<String> Exp_Default_RBC_color_grade_values= new ArrayList<>(Arrays.asList('10', '20','5','20')) //5,10,20
ArrayList<String> Actaul_Color_Grade_values_after_reset=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Color_Grade_values_after_reset,Exp_Default_RBC_color_grade_values,'reset_to_default')

