import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
import zoom.ZoomInOut

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Significant'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Non Significant'), 10)

//Verifying significant colour code
String signifiacnt_color_code = WebUI.getCSSValue(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Significant_significant default'),
'background-color')
println(signifiacnt_color_code)
assert signifiacnt_color_code == 'rgba(198, 27, 28, 1)'


//Verifying non-significant colour code
String non_signifiacnt_color_code = WebUI.getCSSValue(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Non Significant_non-significant default'),
'background-color')
println(non_signifiacnt_color_code)

assert non_signifiacnt_color_code == 'rgba(50, 152, 93, 1)'