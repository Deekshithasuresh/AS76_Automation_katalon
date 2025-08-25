import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.maximizeWindow()

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/span_WBC'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Rejected'))

WebUI.doubleClick(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/div_Image settings_default-patch  patch-foc_a6a738'))

WebUI.delay(5)

WebUI.waitForElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/canvas'), 5)

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/canvas'), 5)

WebUI.verifyElementVisible(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/canvas'))

