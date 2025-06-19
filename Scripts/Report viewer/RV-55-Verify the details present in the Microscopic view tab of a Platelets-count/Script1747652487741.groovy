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


// ---------- STEP 9: Click on the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='Platelets']]"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)
// ---------- STEP 10: Verify “Count” header is selected by default ----------
TestObject countHeader = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[@id='plateleteCountTab' and contains(@class,'active-selection-btn') and normalize-space()='Count']"
)
WebUI.verifyElementPresent(countHeader, 10)

// ---------- STEP 11: Click on the microscopic-view icon ----------
TestObject microViewIcon = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.click(microViewIcon)

// ---------- STEP 12: Verify line-tool button ----------
TestObject lineTool = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[img[@alt='line-tool']]"
)
WebUI.verifyElementPresent(lineTool, 10)

// ---------- STEP 13: Verify circle-tool icon ----------
TestObject circleTool = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//img[@alt='circle-tool']"
)
WebUI.verifyElementPresent(circleTool, 10)

// ---------- STEP 14: Verify zoom-tool icon ----------
TestObject zoomTool = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//img[@alt='zoom-tool']"
)
WebUI.verifyElementPresent(zoomTool, 10)

// ---------- STEP 15: Verify home-icon button ----------
TestObject homeBtn = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//div[img[@alt='home']]"
)
WebUI.verifyElementPresent(homeBtn, 10)

// ---------- STEP 16: Verify Zoom-In button ----------
TestObject zoomInBtn = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[@title='Zoom in']"
)
WebUI.verifyElementPresent(zoomInBtn, 10)

// ---------- STEP 17: Verify Zoom-Out button ----------
TestObject zoomOutBtn = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[@title='Zoom out']"
)
WebUI.verifyElementPresent(zoomOutBtn, 10)

// ---------- STEP 18: Verify Overview (chevron) button ----------
TestObject overviewBtn = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[@title='Overview']"
)
WebUI.verifyElementPresent(overviewBtn, 10)

// ---------- STEP 19: Verify canvas is present (microscopic view itself) ----------
TestObject canvasViewport = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//canvas"
)
WebUI.verifyElementPresent(canvasViewport, 10)
