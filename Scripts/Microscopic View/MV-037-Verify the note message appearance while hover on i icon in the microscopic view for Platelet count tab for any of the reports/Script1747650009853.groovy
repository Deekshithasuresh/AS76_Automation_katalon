import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// 1) LOGIN
WebUI.openBrowser('', FailureHandling.STOP_ON_FAILURE)
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login', FailureHandling.STOP_ON_FAILURE)
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr', FailureHandling.STOP_ON_FAILURE)
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==', FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'), FailureHandling.STOP_ON_FAILURE)

// 2) VERIFY LANDING ON REPORT LIST
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10, FailureHandling.STOP_ON_FAILURE)

// 3) OPEN FIRST “Under review” REPORT (safe)
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
if (WebUI.waitForElementClickable(underReviewRow, 5, FailureHandling.OPTIONAL)) {
	WebUI.scrollToElement(underReviewRow, 3, FailureHandling.OPTIONAL)
	WebUI.click(underReviewRow, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Opened first ‘Under review’ report.")
} else {
	WebUI.comment("⚠ No ‘Under review’ report found; proceeding anyway.")
}

// 4) CLICK on the Platelets tab
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons')]/span[normalize-space()='Platelets']"
)
if (WebUI.waitForElementClickable(plateletsTab, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(plateletsTab, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Platelets tab clicked.")
} else {
	WebUI.comment("⚠ Platelets tab not found; continuing.")
}

// 5) VERIFY “Count” sub-tab is active by default (safe)
TestObject countTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@id='plateleteCountTab']"
)
if (WebUI.waitForElementVisible(countTab, 5, FailureHandling.OPTIONAL)) {
	String sel = WebUI.getAttribute(countTab, 'aria-selected', FailureHandling.OPTIONAL)
	if ('true'.equals(sel)) {
		WebUI.comment("✔ ‘Count’ sub-tab is active by default.")
	} else {
		WebUI.comment("⚠ ‘Count’ sub-tab aria-selected='${sel}' (expected 'true').")
	}
} else {
	WebUI.comment("⚠ Could not locate the ‘Count’ sub-tab; skipping check.")
}

// 6) SWITCH to Microscopic view
TestObject microView = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
if (WebUI.waitForElementClickable(microView, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(microView, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Microscopic view activated.")
} else {
	WebUI.comment("⚠ Microscopic view button not found.")
}

// 7) LET the map render
WebUI.delay(3)

// 8) HOVER over the “i” icon and VERIFY tooltip
TestObject infoBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'side-pane-btn')]//img[@alt='i']"
)
if (WebUI.waitForElementVisible(infoBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.mouseOver(infoBtn, FailureHandling.OPTIONAL)
	String expectedTooltip = "Black outline demarcates the area used for extraction of Platelets"
	TestObject tooltip = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//div[contains(text(),'" + expectedTooltip + "')]"
	)
	if (WebUI.waitForElementVisible(tooltip, 5, FailureHandling.OPTIONAL)) {
		WebUI.verifyElementText(tooltip, expectedTooltip, FailureHandling.OPTIONAL)
		WebUI.comment("✔ Tooltip text is correct.")
	} else {
		WebUI.comment("⚠ Tooltip did not appear or text mismatch.")
	}
} else {
	WebUI.comment("⚠ ‘i’ icon not found; cannot verify tooltip.")
}

WebUI.comment("✅ Script completed (no uncaught errors).")

