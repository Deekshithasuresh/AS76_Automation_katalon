import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report_1'))

//WebUI.delay(5)

//WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))

WebUI.waitForElementClickable(findTestObject('Object Repository/Page_PBS/button_Approve report'), 10)
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))