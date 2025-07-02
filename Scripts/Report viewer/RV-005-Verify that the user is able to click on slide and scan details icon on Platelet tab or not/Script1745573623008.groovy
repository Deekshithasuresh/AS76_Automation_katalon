import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.Keys

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
def pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10, FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
def underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.scrollToElement(underReviewRow, 5, FailureHandling.OPTIONAL)
WebUI.click(underReviewRow, FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 4) CLICK THE PLATELETS TAB
// ────────────────────────────────────────────────────────────────────
def plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons cell-tab')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(plateletsTab, FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 5) OPEN SLIDE-INFO DRAWER
// ────────────────────────────────────────────────────────────────────
def slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(slideInfoIcon, FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 6) WAIT FOR DRAWER & VERIFY CONTENTS
// ────────────────────────────────────────────────────────────────────
def drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10, FailureHandling.STOP_ON_FAILURE)

// 6.1 Slide Id
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(@class,'slideInfoComponent_drawer__header-title') and normalize-space(.)='Slide Id:']"
).with { WebUI.verifyElementPresent(it, 5, FailureHandling.CONTINUE_ON_FAILURE) }

// 6.2 Under review status
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_status')]//span[normalize-space(text())='Under review']"
).with { WebUI.verifyElementPresent(it, 5, FailureHandling.CONTINUE_ON_FAILURE) }

// 6.3 Slide image
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Slide image']"
).with { WebUI.verifyElementPresent(it, 5, FailureHandling.CONTINUE_ON_FAILURE) }

// 6.4 Scanned by
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Scanned by']"
).with { WebUI.verifyElementPresent(it, 5, FailureHandling.CONTINUE_ON_FAILURE) }

// 6.5 Scanned on
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Scanned on']"
).with { WebUI.verifyElementPresent(it, 5, FailureHandling.CONTINUE_ON_FAILURE) }

// ────────────────────────────────────────────────────────────────────
// 7) CLOSE THE DRAWER ROBUSTLY
// ────────────────────────────────────────────────────────────────────
def closeBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]//button[.//img[contains(@src,'cancel.svg')]]"
)
try {
	WebUI.waitForElementVisible(closeBtn, 5, FailureHandling.OPTIONAL)
	WebUI.waitForElementClickable(closeBtn, 5, FailureHandling.OPTIONAL)
	WebUI.click(closeBtn, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Clicked drawer-close button")
} catch (Exception e) {
	WebUI.comment("⚠ Close button not clickable, sending ESC")
	WebUI.sendKeys(drawer, Keys.ESCAPE)
}
// ensure it’s gone
WebUI.verifyElementNotPresent(drawer, 5, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment("✔ Platelets slide-info drawer closed successfully")

