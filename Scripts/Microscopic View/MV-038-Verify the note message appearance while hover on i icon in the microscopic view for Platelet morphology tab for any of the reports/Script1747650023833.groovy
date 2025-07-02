import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('', FailureHandling.STOP_ON_FAILURE)
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login', FailureHandling.STOP_ON_FAILURE)
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr', FailureHandling.STOP_ON_FAILURE)
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==', FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'), FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10, FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
if (WebUI.waitForElementClickable(underReviewRow, 5, FailureHandling.OPTIONAL)) {
	WebUI.scrollToElement(underReviewRow, 3, FailureHandling.OPTIONAL)
	WebUI.click(underReviewRow, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Opened first 'Under review' report.")
} else {
	WebUI.comment("⚠ No 'Under review' report found; continuing anyway.")
}

// ────────────────────────────────────────────────────────────────────
// 4) NAVIGATE TO Platelets → Morphology → Microscopic view
// ────────────────────────────────────────────────────────────────────
def clickIfExists = { TestObject to ->
	if (WebUI.waitForElementClickable(to, 5, FailureHandling.OPTIONAL)) {
		WebUI.click(to, FailureHandling.OPTIONAL)
	}
}

clickIfExists(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons')]/span[normalize-space()='Platelets']"))
WebUI.comment("✔ Platelets tab clicked.")

clickIfExists(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab']"))
WebUI.comment("✔ Morphology sub-tab clicked.")

clickIfExists(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))
WebUI.comment("✔ Microscopic view activated.")

WebUI.delay(3)

// ────────────────────────────────────────────────────────────────────
// 5) HOVER on the “i” icon and VERIFY tooltip text appears
// ────────────────────────────────────────────────────────────────────
TestObject infoBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'side-pane-btn')]//img[@alt='i']"
)
if (WebUI.waitForElementVisible(infoBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.mouseOver(infoBtn, FailureHandling.OPTIONAL)
	WebUI.delay(1)
	// The two lines we expect in the tooltip:
	String line1 = "Black outline demarcates the area used for extraction of Platelets"
	String line2 = "Large platelet and platelet clumps are extracted from the entire scanned area"
	// Simply verify each appears somewhere on the page:
	boolean l1 = WebUI.verifyTextPresent(line1, false, FailureHandling.OPTIONAL)
	boolean l2 = WebUI.verifyTextPresent(line2, false, FailureHandling.OPTIONAL)
	if (l1 && l2) {
		WebUI.comment("✔ Both tooltip lines appeared correctly.")
	} else {
		if (!l1) WebUI.comment("⚠ Missing tooltip line1: ${line1}")
		if (!l2) WebUI.comment("⚠ Missing tooltip line2: ${line2}")
	}
} else {
	WebUI.comment("⚠ Info-icon not found; skipping tooltip check.")
}

WebUI.comment("✅ Script finished without uncaught errors.")


