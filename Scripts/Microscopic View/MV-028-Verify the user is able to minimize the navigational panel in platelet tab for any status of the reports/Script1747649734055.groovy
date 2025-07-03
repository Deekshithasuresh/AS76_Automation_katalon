import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")


// 4) CLICK the Platelets tab
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
)
if (WebUI.waitForElementClickable(plateletsTab, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(plateletsTab, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Platelets tab clicked.")
} else {
	WebUI.comment("⚠ Platelets tab not found.")
}


// 6) ACTIVATE Microscopic view
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
if (WebUI.waitForElementClickable(microViewBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(microViewBtn, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Microscopic view activated.")
} else {
	WebUI.comment("⚠ Microscopic view button not found.")
}

// give OpenLayers a moment to render
WebUI.delay(3)

// 7) TOGGLE Overview panel on/off
TestObject overviewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@title='Overview']"
)
TestObject overviewBox = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]"
)
if (WebUI.waitForElementClickable(overviewBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(overviewBtn, FailureHandling.OPTIONAL)
	if (WebUI.waitForElementVisible(overviewBox, 3, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔ Overview panel appeared.")
	} else {
		WebUI.comment("⚠ Overview panel did not appear (maybe already open).")
	}
	WebUI.click(overviewBtn, FailureHandling.OPTIONAL)
	if (WebUI.waitForElementNotPresent(overviewBox, 3, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔ Overview panel closed.")
	} else {
		WebUI.comment("⚠ Overview panel did not close.")
	}
} else {
	WebUI.comment("⚠ Overview toggle button not found.")
}

// done
WebUI.comment("✅ MV-028 script completed without uncaught errors.")





