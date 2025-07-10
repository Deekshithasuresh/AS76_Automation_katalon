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
// 2) WAIT FOR REPORT LIST
// ────────────────────────────────────────────────────────────────────
def pbsHeader = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]")
WebUI.waitForElementPresent(pbsHeader, 10, FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
def underReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']])[1]"
)
WebUI.scrollToElement(underReview, 5, FailureHandling.OPTIONAL)
WebUI.click(underReview, FailureHandling.OPTIONAL)

// ────────────────────────────────────────────────────────────────────
// 4) PLATELETS → MORPHOLOGY → SPLIT VIEW
// ────────────────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons')]/span[text()='Platelets']").with {
	WebUI.click(it, FailureHandling.OPTIONAL)
}
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab']").with {
	WebUI.click(it, FailureHandling.OPTIONAL)
}
def splitView = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@id='split-view' and @alt='Split view']")
WebUI.waitForElementClickable(splitView, 5, FailureHandling.OPTIONAL)
WebUI.click(splitView, FailureHandling.OPTIONAL)

// give split‐view a moment
WebUI.delay(3)

// ────────────────────────────────────────────────────────────────────
// 5) PREPARE ZOOM & WARNING LOCATORS
// ────────────────────────────────────────────────────────────────────
int maxClicks = 10

def zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
def zoomWarning = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(text(),'100 to 200')]")   // tweak this to your exact message

// ────────────────────────────────────────────────────────────────────
// 6) CLICK ZOOM‐IN UNTIL WARNING OR EXHAUST
// ────────────────────────────────────────────────────────────────────
boolean warningShown = false
for (int i = 1; i <= maxClicks; i++) {
	if (!WebUI.waitForElementClickable(zoomInBtn, 3, FailureHandling.OPTIONAL)) {
		WebUI.comment("⚠️ Zoom-in button not clickable on attempt ${i}; stopping.")
		break
	}
	WebUI.click(zoomInBtn, FailureHandling.OPTIONAL)
	WebUI.delay(1)
	if (WebUI.verifyElementPresent(zoomWarning, 1, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔️ Warning appeared after ${i} zoom-in click(s).")
		warningShown = true
		break
	}
}
if (!warningShown) {
	WebUI.comment("⚠️ No warning found after ${maxClicks} clicks.")
}

// ────────────────────────────────────────────────────────────────────
// 7) CLEAN UP
// ────────────────────────────────────────────────────────────────────
WebUI.comment("✅ MV-052 run complete.")

