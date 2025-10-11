import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.FilterFunctions.verifyAllFiltersApplied'('10-10-2025', '11-10-2025', ['manju'], ['Under review'])

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Show applied filters'), 'Show applied filters')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Show applied filters'))