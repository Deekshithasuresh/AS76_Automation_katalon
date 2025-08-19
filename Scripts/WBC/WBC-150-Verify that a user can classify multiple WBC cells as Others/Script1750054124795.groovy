import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

CustomKeywords.'generic.Reclassification.classifyMultiplePatchesToCell'("Neutrophils", "Others", 5)