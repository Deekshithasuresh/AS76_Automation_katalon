import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.Keys

// 1) LOGIN
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

// ---------- STEP: Click on Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Platelets']/ancestor::button"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)
WebUI.comment("✔ Platelets tab clicked.")

// ---------- STEP: Switch to Microscopic view on Platelets ----------
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)
WebUI.comment("✔ Microscopic view activated for Platelets.")

// ---------- STEP: Verify Zoom Controls on Platelets Micro view ----------
TestObject pltZoomInBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)
TestObject pltZoomOutBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-out') and @title='Zoom out']"
)

WebUI.waitForElementVisible(pltZoomInBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementVisible(pltZoomInBtn, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Platelets: Zoom-in button is present.")

WebUI.waitForElementVisible(pltZoomOutBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementVisible(pltZoomOutBtn, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Platelets: Zoom-out button is present.")
