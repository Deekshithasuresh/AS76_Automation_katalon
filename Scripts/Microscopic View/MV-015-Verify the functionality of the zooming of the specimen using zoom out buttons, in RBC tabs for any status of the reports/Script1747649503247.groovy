import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// ──────────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ──────────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(
	findTestObject('Report viewer/Page_PBS/input_username_loginId'),
	'adminuserr'
)
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(
	findTestObject('Report viewer/Page_PBS/button_Sign In'),
	FailureHandling.STOP_ON_FAILURE
)

// ──────────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ──────────────────────────────────────────────────────────────────────────
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(
	pbsText,
	10,
	FailureHandling.STOP_ON_FAILURE
)

// ──────────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ──────────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(
	underReviewRow,
	10,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.scrollToElement(
	underReviewRow,
	5,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.click(
	underReviewRow,
	FailureHandling.STOP_ON_FAILURE
)

// ──────────────────────────────────────────────────────────────────────────
// 4) CLICK on the RBC tab
// ──────────────────────────────────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']"
)
WebUI.waitForElementClickable(
	rbcTab,
	10,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.click(
	rbcTab,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.comment("✔ RBC tab clicked.")

// ──────────────────────────────────────────────────────────────────────────
// 5) ACTIVATE Microscopic view
// ──────────────────────────────────────────────────────────────────────────
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(
	microViewBtn,
	10,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.click(
	microViewBtn,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.comment("✔ Microscopic view activated for RBC.")

// give OpenLayers a moment to render
WebUI.delay(5)

// ──────────────────────────────────────────────────────────────────────────
// 6) ZOOM IN twice
// ──────────────────────────────────────────────────────────────────────────
TestObject zoomInBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)
WebUI.waitForElementClickable(
	zoomInBtn,
	10,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.click(
	zoomInBtn,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.delay(1)
WebUI.click(
	zoomInBtn,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.comment("✔ Zoom-in clicked twice on RBC view.")

// ──────────────────────────────────────────────────────────────────────────
// 7) ZOOM OUT twice
// ──────────────────────────────────────────────────────────────────────────
TestObject zoomOutBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-out') and @title='Zoom out']"
)
WebUI.waitForElementClickable(
	zoomOutBtn,
	10,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.click(
	zoomOutBtn,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.delay(1)
WebUI.click(
	zoomOutBtn,
	FailureHandling.STOP_ON_FAILURE
)
WebUI.comment("✔ Zoom-out clicked twice on RBC view.")


