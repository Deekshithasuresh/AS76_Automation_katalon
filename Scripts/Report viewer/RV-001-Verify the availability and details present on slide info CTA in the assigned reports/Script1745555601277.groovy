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
WebUI.comment(" 'Approve report' is now visible.")

// ---------- STEP 4: Click on Slide Info icon ----------
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementVisible(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// ---------- STEP 5: Verify Slide Info Drawer Elements ----------
TestObject drawer = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// Verify Slide Id:
TestObject slideIdLbl = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//span[contains(@class,'slideInfoComponent_drawer__header-title') and normalize-space(.)='Slide Id:']"
)
WebUI.verifyElementPresent(slideIdLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

// Verify status (either To be reviewed or Under review):
TestObject statusLbl = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'slideInfoComponent_status')]/span[normalize-space(.)='To be reviewed' or normalize-space(.)='Under review']"
)
WebUI.verifyElementPresent(statusLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

// Verify the remaining labels:
String[] labels = ['Slide image', 'Scanned by', 'Scanned on']
for (String txt : labels) {
    TestObject lbl = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//*[contains(normalize-space(.), '${txt}')]"
    )
    WebUI.verifyElementPresent(lbl, 5, FailureHandling.CONTINUE_ON_FAILURE)
}

// verify close-icon
TestObject closeBtn = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[.//img[@src='/icons/cancel.svg']]"
)
WebUI.verifyElementPresent(closeBtn, 5)

WebUI.comment("✅ Slide-info popup contains all required elements.")


WebUI.comment("✅ Slide-info popup contains all required elements.")

