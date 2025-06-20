import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver

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
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
String underReviewXpath = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, underReviewXpath)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)

WebUI.click(assignedDropdown)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)
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

