import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.testng.Assert.assertTrue

import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Step 1: Login
CustomKeywords.'generic.custumFunctions.login'()

// Step 2: Verify login landed correctly
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_My reports'), 'My reports')

// Step 3: Click Assigned-to filter checkbox
WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/input_Clear filters_PrivateSwitchBase-input_068da8'), 5)
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/input_Clear filters_PrivateSwitchBase-input_068da8'))

// Step 4: Click Filter button
WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'), 5)
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

// Step 5: Verify filter result
WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1'), 5)

// Step 6: Apply date filter and verify
Boolean result = CustomKeywords.'generic.FilterFunctions.applyAndVerifyDateFilter'("11-05-2025", "12-05-2025")
assertTrue(result)

// Step 7: Check if checkbox is selected
TestObject checkboxObj = new TestObject("dynamicCheckbox")
checkboxObj.addProperty("xpath", ConditionType.EQUALS, "//div[@class='clear-filter-btn']/following-sibling::span[1]")

boolean isChecked = false
try {
	WebElement checkboxElement = WebUiCommonHelper.findWebElement(checkboxObj, 10)
	String classAttr = checkboxElement.getAttribute("class")
	isChecked = classAttr.contains("Mui-checked")
	WebUI.comment("Checkbox is selected? " + isChecked)
} catch (Exception e) {
	WebUI.comment("Checkbox not found or error occurred: " + e.getMessage())
	isChecked = false
}
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/input_Clear filters_PrivateSwitchBase-input_068da8'))


// Step 8: Clear filter if present
TestObject clearBtn = new TestObject('clearFilter')
clearBtn.addProperty('xpath', ConditionType.EQUALS, "//div[@class='clear-filter-btn']/button")

if (WebUI.waitForElementPresent(clearBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(clearBtn)
	WebUI.comment("Cleared Assigned‑To filter")
}


