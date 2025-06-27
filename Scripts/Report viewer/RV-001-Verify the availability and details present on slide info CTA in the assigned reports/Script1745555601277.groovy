import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import imageutils.blurCheckZoomInOut               
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 4) OPEN SLIDE-INFO DRAWER
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementVisible(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// 5) VERIFY SLIDE-INFO DRAWER ELEMENTS
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// — Slide ID label (note uppercase “ID”)
TestObject slideIdLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'slideInfoComponent_drawer__header-title') and normalize-space(.)='Slide ID:']"
)
WebUI.verifyElementPresent(slideIdLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

// — Status label (either “To be reviewed” or “Under review”)
TestObject statusLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_status')]/span[normalize-space(.)='To be reviewed' or normalize-space(.)='Under review']"
)
WebUI.verifyElementPresent(statusLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

// — Other info labels
for (String text : ['Slide image', 'Scanned by', 'Scanned on']) {
	TestObject lbl = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//*[normalize-space(text())='${text}']"
	)
	WebUI.verifyElementPresent(lbl, 5, FailureHandling.CONTINUE_ON_FAILURE)
}

// — Close-drawer button
TestObject closeBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[@src='/icons/cancel.svg']]"
)
WebUI.verifyElementPresent(closeBtn, 5, FailureHandling.CONTINUE_ON_FAILURE)

// OPTIONAL: use your helper now that it’s imported
boolean blurry = blurCheckZoomInOut.isCanvasBlurry()
WebUI.comment("⚙️ Canvas blur check = ${blurry}")

WebUI.comment("✅ Slide-info drawer contains all required elements.")

