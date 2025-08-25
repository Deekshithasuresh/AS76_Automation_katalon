import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

WebUI.maximizeWindow()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Summary'), 5)

WebUI.verifyElementText(findTestObject('Object Repository/WBC_m/Page_PBS/other'), 'Others')