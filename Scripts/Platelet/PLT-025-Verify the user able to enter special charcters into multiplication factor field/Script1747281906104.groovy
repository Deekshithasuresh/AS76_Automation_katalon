
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('pawan', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'), 0)

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'))


WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), 0)

// Append special characters without clearing the existing value
WebUI.sendKeys(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '@!)()#%^&&&')
