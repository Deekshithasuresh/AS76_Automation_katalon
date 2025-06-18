import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ---------- STEP 1: Login & open a report ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Pick “To be reviewed” or “Under review” ----------
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

// ---------- STEP 3: Click the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)
WebUI.comment("✔ WBC tab clicked")

// ---------- STEP 4: Activate Microscopic view ----------
TestObject microBtn = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)
WebUI.comment("✔ Microscopic view activated")

// give the OpenLayers viewer time to stitch & render
WebUI.delay(5)

// ─── STEP 5: Hover + click “mark area” CTA & wait 60s ──────────────
TestObject markAreaBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]"
)
WebUI.waitForElementVisible(markAreaBtn, 60)
WebUI.mouseOver(markAreaBtn)
WebUI.delay(1)
WebUI.click(markAreaBtn)
WebUI.delay(60)   // allow the 40x/100x popover to appear

// allow the 40x/100x popover to animate in
WebUI.delay(10)

// Locate the 40x and 100x buttons by their position after the zoom-tool CTA:
TestObject btn40x = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[1]"
)
TestObject btn100x = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[2]"
)

// Wait up to 10s for each to appear
WebUI.waitForElementVisible(btn40x, 10)
WebUI.verifyElementPresent(btn40x, 1)
WebUI.comment("✔ ‘40x’ option is present")

WebUI.waitForElementVisible(btn100x, 10)
WebUI.verifyElementPresent(btn100x, 1)
WebUI.comment("✔ ‘100x’ option is present")