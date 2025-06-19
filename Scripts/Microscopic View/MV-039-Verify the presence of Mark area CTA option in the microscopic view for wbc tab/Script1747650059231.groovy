import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ---------- STEP 1: Login & open a “To be reviewed” or “Under review” report ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

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

// ---------- STEP 2: Click on the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// ---------- STEP 3: Activate Microscopic view ----------
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)

// ---------- STEP 4: Wait for the viewer to render ----------
WebUI.delay(5)

// ---------- STEP 5: Verify the “mark area” CTA is present ----------
TestObject zoomToolBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]"
)
WebUI.verifyElementPresent(zoomToolBtn, 10)
WebUI.comment("✔ ‘Mark area’  button is present in the WBC microscopic view.")
