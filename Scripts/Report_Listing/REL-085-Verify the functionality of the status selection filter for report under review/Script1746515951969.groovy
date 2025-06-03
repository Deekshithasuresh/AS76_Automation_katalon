import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.testng.Assert.assertTrue

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




CustomKeywords.'generic.custumFunctions.login'()

Boolean result= CustomKeywords.'generic.FilterFunctions.statusFilter'("Under review")
assertTrue(result)



TestObject clearBtn = new TestObject('clearFilter').addProperty('xpath', ConditionType.EQUALS, "//div[@class='clear-filter-btn']/button")
	if (WebUI.waitForElementPresent(clearBtn, 5, FailureHandling.OPTIONAL)) {
		WebUI.click(clearBtn)
		WebUI.comment("Cleared Assigned‑To filter")
	}


