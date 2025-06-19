import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject

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

// ---------- STEP 4: Click on the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//button[contains(@class,\'cell-tab\') and .//span[normalize-space()=\'WBC\']]')

WebUI.waitForElementClickable(wbcTab, 10)

WebUI.click(wbcTab)

// ---------- STEP 5: Verify that the WBC header is present ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//div[@class=\'table-cell-name\' and normalize-space()=\'WBC\']'), 
    10)

// ---------- STEP 6: Click on the microscopic‐view icon ----------
TestObject microViewIcon = new TestObject().addProperty('xpath', ConditionType.EQUALS, '//img[@alt=\'Microscopic view\']')

WebUI.waitForElementClickable(microViewIcon, 10)

WebUI.click(microViewIcon)

WebUI.delay(120 // give the tools time to render
    )

// ---------- STEP 7: Verify the line‐tool icon ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//img[@alt=\'line-tool\']'), 10)

// ---------- STEP 8: Verify the circle‐tool icon ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//img[@alt=\'circle-tool\']'), 10)

// ---------- STEP 9: Verify the zoom‐tool icon ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//img[@alt=\'zoom-tool\']'), 10)

// ---------- STEP 10: Verify the home‐icon button ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//div[.//img[@alt=\'home\']]'), 
    10)

// ---------- STEP 11: Verify the Zoom-In button ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//button[@class=\'ol-zoom-in\' and @title=\'Zoom in\']'), 
    10)

// ---------- STEP 12: Verify the Zoom-Out button ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//button[@class=\'ol-zoom-out\' and @title=\'Zoom out\']'), 
    10)

// ---------- STEP 13: Verify the Overview button ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//button[@title=\'Overview\']'), 
    10)

// ---------- STEP 14: Verify the microscopic canvas is rendered ----------
WebUI.verifyElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS, '//canvas'), 10)

