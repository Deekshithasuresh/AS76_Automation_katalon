import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.FilterFunctions.verifyAllFiltersApplied'('10-05-2025', '11-05-2025', ['manju'], ['To be reviewed'])

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Show applied filters'), 'Show applied filters')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Show applied filters'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Status'), 'Status')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Scan date'), 'Scan Date')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Assigned to'), 'Assigned to')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Hide applied filters'), 'Hide applied filters')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Hide applied filters'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Filters_MuiButtonBase-root MuiIconBu_2f02cd'))

TestObject clearBtn = new TestObject('clearFilter').addProperty('xpath', ConditionType.EQUALS, "//div[@class='clear-filter-btn']/button")
if (WebUI.waitForElementPresent(clearBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(clearBtn)
	WebUI.comment("Cleared Assigned‑To filter")
}

