import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.testng.Assert.assertTrue

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI






CustomKeywords.'generic.custumFunctions.login'()

              
CustomKeywords.'generic.FilterFunctions.verifyCombineFilters'("Under review", "prem")


def getIconObj = { int index ->
    String xpath = "(//tr//td[1]//img[@alt='bookmark'])[$index]"
    return new TestObject("bookmarkIcon_$index").addProperty("xpath", ConditionType.EQUALS, xpath)
}

// Get total number of bookmark icons
int count = WebUI.findWebElements(new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tr//td[1]//img[@alt='bookmark']"), 10).size()

for (int i = 1; i <= count; i++) {
	try {
    TestObject icon = getIconObj(i)
    WebUI.waitForElementVisible(icon, 5)
    String src = WebUI.getAttribute(icon, "src")

    if (src.contains("bookmark-filled")) {
        WebUI.comment("Row $i: bookmark is Selected")
        WebUI.delay(1) 
		assertTrue(false)
    } 
	} catch (Exception e) {
        WebUI.comment("All the bookmark are unselected.")
	}
}





TestObject clearBtn = new TestObject('clearFilter').addProperty('xpath', ConditionType.EQUALS, "//div[@class='clear-filter-btn']/button")
if (WebUI.waitForElementPresent(clearBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(clearBtn)
	WebUI.comment("Cleared Assigned‑To filter")
}


