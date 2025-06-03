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
// ---------- STEP: Click on WBC tab ----------
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='WBC']/ancestor::button"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// ---------- STEP: Microscopic view ----------
TestObject microViewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)
// Locate the “+” and “–” buttons by their classes and titles
TestObject wbcZoomInBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)
TestObject wbcZoomOutBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-out') and @title='Zoom out']"
)

// Wait up to 10s for them to appear and verify visibility
WebUI.waitForElementVisible(wbcZoomInBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementVisible(wbcZoomInBtn, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ WBC: Zoom-in button is present.")

WebUI.waitForElementVisible(wbcZoomOutBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementVisible(wbcZoomOutBtn, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ WBC: Zoom-out button is present.")
