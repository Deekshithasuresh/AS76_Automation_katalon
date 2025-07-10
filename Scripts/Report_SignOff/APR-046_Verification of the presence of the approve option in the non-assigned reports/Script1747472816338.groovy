import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.delay(5)

//WebUI.verifyElementText(findTestObject('Object Repository/Report-Signoff/span_Approve report'), 'Approve report')

WebUI.verifyElementNotPresent(findTestObject('Object Repository/Report-Signoff/summary_Approve report_button'), 0)
