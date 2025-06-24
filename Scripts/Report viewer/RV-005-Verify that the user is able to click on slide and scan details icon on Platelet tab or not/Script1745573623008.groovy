import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

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
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) CLICK THE PLATELETS TAB
// ────────────────────────────────────────────────────────────────────
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons cell-tab')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// ────────────────────────────────────────────────────────────────────
// 5) OPEN SLIDE-INFO DRAWER
// ────────────────────────────────────────────────────────────────────
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 10)
WebUI.click(slideInfoIcon)

// ────────────────────────────────────────────────────────────────────
// 6) WAIT FOR DRAWER & VERIFY CONTENTS
// ────────────────────────────────────────────────────────────────────
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// 6.1 Slide Id
TestObject slideIdLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'slideInfoComponent_drawer__header-title') and normalize-space(.)='Slide Id:']"
)
WebUI.verifyElementPresent(slideIdLabel, 5, FailureHandling.CONTINUE_ON_FAILURE)

// 6.2 Under review status
TestObject statusLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_status')][.//span[normalize-space(text())='Under review']]"
)
WebUI.verifyElementPresent(statusLabel, 5, FailureHandling.CONTINUE_ON_FAILURE)

// 6.3 Slide image
TestObject slideImage = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Slide image']"
)
WebUI.verifyElementPresent(slideImage, 5, FailureHandling.CONTINUE_ON_FAILURE)

// 6.4 Scanned by
TestObject scannedBy = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Scanned by']"
)
WebUI.verifyElementPresent(scannedBy, 5, FailureHandling.CONTINUE_ON_FAILURE)

// 6.5 Scanned on
TestObject scannedOn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Scanned on']"
)
WebUI.verifyElementPresent(scannedOn, 5, FailureHandling.CONTINUE_ON_FAILURE)

// 6.6 Close drawer button
TestObject closeButton = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[@src='/icons/cancel.svg']]"
)
WebUI.waitForElementClickable(closeButton, 5)
WebUI.click(closeButton)

WebUI.comment("✔ Opened an 'Under review' report on Platelets tab, verified slide-info drawer elements, and closed it.")
