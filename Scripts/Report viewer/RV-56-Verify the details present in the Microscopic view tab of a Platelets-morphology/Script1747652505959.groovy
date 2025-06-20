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

// ---------- STEP 10: Click on the Morphology header ----------
TestObject morphologyTab = new TestObject().addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//button[@id='plateleteMorphologyTab' and normalize-space()='Morphology']"
)
WebUI.waitForElementClickable(morphologyTab, 10)
WebUI.click(morphologyTab)

// ---------- STEP 11: (Optional) Verify the list of morphology cell names is visible ----------
List<String> cellNames = ['Normal Platelets', 'Macro Platelets', 'Giant Platelets']
cellNames.each { name ->
    TestObject cellBtn = new TestObject().addProperty(
        'xpath',
        ConditionType.EQUALS,
        "//label[normalize-space()='$name']"
    )
    WebUI.verifyElementPresent(cellBtn, 5)
}

// ---------- STEP 12: Click on the microscopic‐view icon ----------
TestObject microViewIcon = new TestObject().addProperty(
    'xpath',
    ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.click(microViewIcon)

// ---------- STEP 13: Verify the drawing tools and controls exist ----------
// line-tool
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//button[img[@alt='line-tool']]"),
    5
)
// circle-tool
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//img[@alt='circle-tool']"),
    5
)
// zoom-tool
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//img[@alt='zoom-tool']"),
    5
)
// home icon
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//div[img[@alt='home']]"),
    5
)
// zoom in/out
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//button[@title='Zoom in']"),
    5
)
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//button[@title='Zoom out']"),
    5
)

// overview chevron
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//button[@title='Overview']"),
    5
)

// the microscopic canvas itself
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//canvas"),
    5
)
