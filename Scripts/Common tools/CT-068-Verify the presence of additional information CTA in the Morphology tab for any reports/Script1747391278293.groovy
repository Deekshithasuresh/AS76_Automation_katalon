import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under Review")


WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology'), 'Morphology')

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyTextNotPresent('Additional info',true)

