import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.Keys

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)
// — STEP 3: Open a “To be reviewed” or “Under review” report —
TestObject toBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']"
)
TestObject underReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)

if (WebUI.waitForElementPresent(toBeReviewed, 5)) {
	WebUI.scrollToElement(toBeReviewed, 5)
	WebUI.click(toBeReviewed)
} else {
	WebUI.scrollToElement(underReview, 5)
	WebUI.click(underReview)
}
WebUI.comment("✔ Opened a report in ‘To be reviewed’ or ‘Under review’ status.")
// ---------- STEP: Click on RBC tab ----------
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='RBC']/ancestor::button"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.comment("✔ RBC tab clicked.")

// ---------- STEP: Switch to Microscopic view on RBC ----------
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)
WebUI.comment("✔ Microscopic view activated for RBC.")

// ---------- STEP: Verify Zoom Controls on RBC Micro view ----------
TestObject rbcZoomInBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)
TestObject rbcZoomOutBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-out') and @title='Zoom out']"
)

WebUI.waitForElementVisible(rbcZoomInBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementVisible(rbcZoomInBtn, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ RBC: Zoom-in button is present.")

WebUI.waitForElementVisible(rbcZoomOutBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementVisible(rbcZoomOutBtn, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ RBC: Zoom-out button is present.")