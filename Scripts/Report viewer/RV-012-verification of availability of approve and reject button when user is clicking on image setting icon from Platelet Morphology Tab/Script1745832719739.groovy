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


// ---------- STEP 4: Click on the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='Platelets']]"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// ---------- STEP 5: Click on the Morphology sub-tab ----------
TestObject morphTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab' and normalize-space()='Morphology']"
)
WebUI.waitForElementClickable(morphTab, 10)
WebUI.click(morphTab)

// ---------- STEP 6: Click on Image settings icon ----------
TestObject imgSettings = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='patch-img-settings']//img[@alt='image_setting']"
)
WebUI.waitForElementClickable(imgSettings, 10)
WebUI.click(imgSettings)

// ---------- STEP 7: Wait for the Image-settings pop-over ----------
TestObject popover = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper') and .//div[@class='img-utils-container']]"
)
WebUI.waitForElementVisible(popover, 10)

// ---------- STEP 8: Verify Approve & Reject buttons in this context ----------
// Approve report
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Approve report']"
)
WebUI.verifyElementPresent(approveBtn, 5)

// Reject report
TestObject rejectBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Reject report']"
)
WebUI.verifyElementPresent(rejectBtn, 5)

WebUI.comment("✅ Both Approve and Reject buttons are visible under Platelets → Morphology → Image settings.")
