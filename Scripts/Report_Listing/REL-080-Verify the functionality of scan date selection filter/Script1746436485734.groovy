import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.junit.Assert.assertTrue

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'generic.custumFunctions.login'()

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'), 0)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1'), 0)

Boolean result=CustomKeywords.'generic.FilterFunctions.applyAndVerifyDateFilter'("20-06-2025","21-06-2025")

assertTrue(result)

TestObject clearBtn = new TestObject('clearBtn').addProperty('xpath', ConditionType.EQUALS, "//div[@class='clear-filter-btn']/button")
// wait for it to appear (returns true/false)
boolean exists = WebUI.waitForElementPresent(clearBtn, 5, FailureHandling.OPTIONAL)
	if (exists) {
		WebUI.click(clearBtn)
	}