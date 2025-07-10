import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import adimin_pbs_Settings.PBS_Settings
import loginPackage.Login

Login lg = new Login()

PBS_Settings pbs_set = new PBS_Settings()

lg.AdminLogin('prem', 'prem@2807')

WebUI.verifyElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 10)

WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Platelet level limits'))

WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Edit settings'))

// ✅ Define save button BEFORE referencing it in any assertions
WebElement save_btn = WebUiCommonHelper.findWebElement(
	findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_CTA'), 10
)


TestObject Significant_decresed = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Signifiant_decresed_input')

WebElement Significant_decresed_input = WebUiCommonHelper.findWebElement(Significant_decresed, 10)

TestObject decresed = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Decresed_input')

WebElement decresed_input = WebUiCommonHelper.findWebElement(decresed, 10)

TestObject normal = findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Platelet_normal_input')

WebElement normal_input = WebUiCommonHelper.findWebElement(normal, 10)

ArrayList<String> values = pbs_set.getValuesPresentInPlateletLevelFields()

println(values)

//Significant-decresed
/*String signif_decrese_lower_than_decre = (values[1]) - 1

pbs_set.enterValueIntoPlateLetLevelField(Significant_decresed_input, signif_decrese_lower_than_decre)

String signif_decrese_higher_than_decre = (values[1]) + 1

pbs_set.enterValueIntoPlateLetLevelField(Significant_decresed_input, signif_decrese_higher_than_decre)

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 
    20)

String error_msg1 = WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))

WebElement save_btn = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Save_CTA'), 
    10)

assert save_btn.isEnabled() == false

assert error_msg1.equals('Limit of "Significantly Decreased" level cannot be higher than Upper limit of "Decreased" level')
*/
//Significant-decreased test
int decresed_limit = Integer.parseInt(values[1])
int signif_decrese_lower_than_decre = Math.max(decresed_limit - 1, 0)
int signif_decrese_higher_than_decre = Math.min(decresed_limit + 1, 999)

//pbs_set.enterValueIntoPlateLetLevelField(Significant_decresed_input, signif_decrese_lower_than_decre)
pbs_set.enterValueIntoPlateLetLevelField(Significant_decresed_input, signif_decrese_lower_than_decre.toString())

pbs_set.enterValueIntoPlateLetLevelField(Significant_decresed_input, signif_decrese_higher_than_decre.toString())

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)

String error_msg1 = WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))

assert error_msg1 == 'Limit of "Significantly Decreased" level cannot be higher than Upper limit of "Decreased" level'


//Decresed
/*String decrese_value_lower_than_normal = (values[2]) - 1

pbs_set.enterValueIntoPlateLetLevelField(decresed_input, decrese_value_lower_than_normal)

String decrese_value_higher_than_normal = (values[2]) + 1

pbs_set.enterValueIntoPlateLetLevelField(decresed_input, decrese_value_higher_than_normal)
*/
int normal_limit = Integer.parseInt(values[2])
int decrese_value_lower_than_normal = Math.max(normal_limit - 1, 0)
int decrese_value_higher_than_normal = Math.min(normal_limit + 1, 1000)

pbs_set.enterValueIntoPlateLetLevelField(decresed_input, decrese_value_higher_than_normal.toString())


WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 
    20)

String error_msg2 = WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))


assert save_btn.isEnabled() == false

assert error_msg2.equals('Upper limit of "Decreased" level cannot be higher than Upper limit of "Normal" level')

//Normal
String normal_value_lower_than_decresed = (values[1]) - 1

pbs_set.enterValueIntoPlateLetLevelField(normal_input, normal_value_lower_than_decresed)

WebUI.waitForElementVisible(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 
    20)

String error_msg3 = WebUI.getText(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'))

assert save_btn.isEnabled() == false

assert error_msg3.equals('Upper limit of "Normal" level cannot be lower than "Decreased" level')


//verify that user can add a value between 0-1000 
pbs_set.enterValueIntoPlateLetLevelField(normal_input, '999')

pbs_set.enterValueIntoPlateLetLevelField(decresed_input, '500')

pbs_set.enterValueIntoPlateLetLevelField(Significant_decresed_input, '1')

WebUI.verifyElementNotPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/Error_toast_msg'), 20)

//verify fields donot accept decimal values
//pbs_set.enterValueIntoPlateLetLevelField(Significant_decresed_input, '1.1')
//pbs_set.enterValueIntoPlateLetLevelField(decresed_input, '499.9')
//pbs_set.enterValueIntoPlateLetLevelField(normal_input, '999.99999999')

