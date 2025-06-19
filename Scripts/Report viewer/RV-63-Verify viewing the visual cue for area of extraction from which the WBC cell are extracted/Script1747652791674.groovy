import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

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

// ---------- STEP 8: Verify "Approve report" button is present ----------
TestObject approveBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[normalize-space()='Approve report']/ancestor::button")
WebUI.verifyElementVisible(approveBtn)
WebUI.comment('Test Passed: Report assigned to "admin" and Approve button is visible.')
// ---------- STEP 9: Click on the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//span[normalize-space()='WBC']"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)


// ---------- STEP 10: Verify the microscopic‐view icon is present ----------
TestObject microViewIcon = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementPresent(microViewIcon, 10)
WebUI.comment('Microscopic view icon is present')

// ---------- STEP 11: Click on the microscopic‐view icon ----------
WebUI.click(microViewIcon)

// ---------- STEP 12: Click on the “Overview” chevron button ----------
TestObject overviewBtn = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[@title='Overview']"
)
WebUI.waitForElementClickable(overviewBtn, 10)
WebUI.click(overviewBtn)

// ---------- STEP 13: Verify the overview-map popup appears ----------
TestObject overviewMapBox = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]"
)
WebUI.waitForElementVisible(overviewMapBox, 10)
WebUI.comment("Overview map box is visible")

// ---------- STEP 14: Verify the green-flag overlay is present ----------
TestObject flagOverlay = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//div[contains(@class,'ol-overview-overlay') and contains(@class,'icon-flag-green')]"
)
WebUI.verifyElementPresent(flagOverlay, 10)
WebUI.comment("Green flag overlay is present")
