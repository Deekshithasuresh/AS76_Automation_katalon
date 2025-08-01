import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import adimin_pbs_Settings.PBS_Settings
import loginPackage.Login
import zoom.ZoomInOut


Login lg = new Login()

PBS_Settings pbs_set =new PBS_Settings()

ZoomInOut zoom = new ZoomInOut()


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

//save-functionality for size
pbs_set.EneterValueInUpperLimitFields('10', '20','enter correct values')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_confirm_CTA,'save_confirm')
WebUI.delay(2)
ArrayList<String> Expected_passed_grade_size_values= new ArrayList<>(Arrays.asList('10', '20'))
ArrayList<String> Actaul_Grade_size_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Grade_size_values,Expected_passed_grade_size_values,'edit')


//For shape tab
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Close_CTA'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Shape_button'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Edit_button'))

//save-functionality
pbs_set.EneterValueInUpperLimitFields('10', '20','enter correct values')
//pbs_set.checkFunctionalityOfCTAS(saveCTA,save_cancel_CTA, 'save_cancel')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_confirm_CTA,'save_confirm')
WebUI.delay(2)
ArrayList<String> Expected_passed_grade_shape_values= new ArrayList<>(Arrays.asList('10', '20'))
ArrayList<String> Actaul_Grade_shape_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Grade_shape_values,Expected_passed_grade_shape_values,'edit')


//For color tab
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Close_CTA'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Color_button'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Edit_button'))
//save-functionality
pbs_set.EneterValueInUpperLimitFields('10', '20','enter correct values')
//pbs_set.checkFunctionalityOfCTAS(saveCTA,save_cancel_CTA, 'save_cancel')
pbs_set.checkFunctionalityOfCTAS(saveCTA,save_confirm_CTA,'save_confirm')
WebUI.delay(2)
ArrayList<String> Expected_passed_grade_color_values= new ArrayList<>(Arrays.asList('10', '20'))
ArrayList<String> Actaul_Grade_color_values=pbs_set.getValuesPresentInUpperLimitFields()
pbs_set.checkThatValuesGotUpdated(Actaul_Grade_color_values,Expected_passed_grade_color_values,'edit')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_Admin Console/X_img_platelet'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/profile_img'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/li_Logout (1)'))

// Step 2: Login to PBS Portal as Reviewer
WebUI.executeJavaScript("window.open('https://as76-pbs.sigtuple.com/login','_blank');", null)
WebUI.switchToWindowIndex(1)
WebUI.setText(findTestObject('Object Repository/Report_Listing/Login_page/input_username_loginId'), "manju")
WebUI.setText(findTestObject('Object Repository/Report_Listing/Login_page/input_password_loginPassword'), "Sigtuple@123")
WebUI.click(findTestObject('Object Repository/Report_Listing/Login_page/button_Sign In'))
WebUI.waitForPageLoad(10)

// Step 3: Open Report Slide from Excel ID
String filePath = 'Time_zone_data/Manual_data.xlsx'
String Slideid = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'pbs setting slide id 2')
println "✅ Slide id from Excel: ${Slideid}"

String xpath = "(//tr[.//td[contains(normalize-space(), '${Slideid}')]])[1]"
TestObject matchingRow = new TestObject('dynamicReportRow')
matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
WebUI.waitForElementClickable(matchingRow, 10)
WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
rowElement.click()

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'),20)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.delay(5)

CustomKeywords.'manual.helper.verifyCorrectnessOfGradeAccordingToPercentageValue'()

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'))

WebUI.delay(5)

CustomKeywords.'manual.helper.verifyCorrectnessOfGradeAccordingToPercentageValue'()

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_color'))

WebUI.delay(5)

CustomKeywords.'manual.helper.verifyCorrectnessOfGradeAccordingToPercentageValue'()


