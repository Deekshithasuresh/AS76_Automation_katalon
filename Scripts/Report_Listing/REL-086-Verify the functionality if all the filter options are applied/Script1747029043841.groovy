import java.time.LocalDate

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.FilterFunctions.verifyAllFiltersApplied'("11-05-2025","12-05-2025", ['manju'], ['To be reviewed'])

TestObject clearBtn = new TestObject('clearFilter').addProperty('xpath', ConditionType.EQUALS, "//div[@class='clear-filter-btn']/button")
	if (WebUI.waitForElementPresent(clearBtn, 5, FailureHandling.OPTIONAL)) {
		WebUI.click(clearBtn)
		WebUI.comment("Cleared Assigned‑To filter")
	}