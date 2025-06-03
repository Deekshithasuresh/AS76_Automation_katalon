import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver

// ─── STEP 1: LOGIN ──────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					  'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ─── STEP 2: WAIT FOR REPORT LIST ─────────────────────────────────
TestObject listReady = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(listReady, 10)

// ─── STEP 3: PICK & OPEN A REPORT ─────────────────────────────────
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
	WebUI.click(underReview)
}

// ─── STEP 4: SWITCH TO WBC → MICROSCOPIC VIEW ─────────────────────
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='WBC']]"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)

// allow the OpenLayers canvas to render
WebUI.delay(6)

// ─── STEP 5: ZOOM IN TWICE ────────────────────────────────────────
TestObject zoomIn = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	"button.ol-zoom-in"
)
for (int i = 1; i <= 2; i++) {
	WebUI.click(zoomIn)
	WebUI.comment("🔍 Zoom-in #${i} clicked")
	WebUI.delay(60)
}

// ─── STEP 6: SWITCH TO RBC TAB ────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.comment("🔖 Switched to RBC tab")

// ─── STEP 7: BACK TO WBC TAB ──────────────────────────────────────
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)
WebUI.comment("🔖 Switched back to WBC tab")

// ─── STEP 8: TOGGLE MICROSCOPIC VIEW AGAIN ───────────────────────
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)
WebUI.comment("🔬 Toggled Microscopic view again")

// ─── STEP 9: VERIFY THAT SCALE READS “1000 µm” ────────────────────
TestObject scaleLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-unselectable')]//div[contains(@class,'inner')]"
)
WebUI.waitForElementVisible(scaleLabel, 10)

// grab whatever text is in there
String scaleText = WebUI.getText(scaleLabel).trim()

// normalize any Greek μ → micro sign µ
scaleText = scaleText.replace('μ', 'µ')

// now assert
assert scaleText == '1000 µm' :
	"❌ Expected ‘1000 µm’ but found ‘${scaleText}’"

WebUI.comment("✅ Default scale is ${scaleText}")

