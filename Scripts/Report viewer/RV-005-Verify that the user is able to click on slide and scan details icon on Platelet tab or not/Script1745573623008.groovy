import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

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
// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment("✅ 'Approve report' is now visible.")

// ---------- Click on the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons cell-tab')]/span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)
WebUI.comment('✔ Platelets tab clicked')

// ---------- STEP 10: Click on Slide-info icon ----------
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 10)
WebUI.click(slideInfoIcon)


// ---------- STEP 11: Wait for the slide-info drawer to appear ----------
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)


// ---------- STEP 12: Verify required elements in the slide-info popup ----------

// 12.1 Slide Id:
TestObject slideIdLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Slide Id:' or normalize-space()='Slide Id']"
)
WebUI.verifyElementPresent(slideIdLabel, 5)

// 12.2 Under review status + icon
TestObject underReviewStatus = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_status__under-review')]" +
	"//span[normalize-space()='Under review']"
)
WebUI.verifyElementPresent(underReviewStatus, 5)

// 12.3 Slide image section
TestObject slideImage = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Slide image']"
)
WebUI.verifyElementPresent(slideImage, 5)

// 12.4 Scanned by label
TestObject scannedBy = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Scanned by']"
)
WebUI.verifyElementPresent(scannedBy, 5)

// 12.5 Scanned on label
TestObject scannedOn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Scanned on']"
)
WebUI.verifyElementPresent(scannedOn, 5)

// 12.6 Close button (cancel icon)
TestObject closeButton = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[@src='/icons/cancel.svg']]"
)
WebUI.verifyElementPresent(closeButton, 5)

WebUI.comment('✔ Slide-info popup elements verified: Slide Id, Under review, Slide image, Scanned by, Scanned on, Close button.')
