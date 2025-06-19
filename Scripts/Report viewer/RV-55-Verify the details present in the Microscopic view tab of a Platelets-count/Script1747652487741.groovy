import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
TestObject pbsText = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]")
WebUI.verifyElementPresent(pbsText, 10)

// ---------- STEP 3: Scroll & Find Report with "To be reviewed" ----------
TestObject toBeReviewed = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[text()='To be reviewed']")
boolean foundToBeReviewed = WebUI.waitForElementPresent(toBeReviewed, 5)

if (foundToBeReviewed) {
	WebUI.click(toBeReviewed)
} else {
	// If not found, fallback to "Under review"
	TestObject underReview = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[text()='Under review']")
	boolean foundUnderReview = WebUI.waitForElementPresent(underReview, 5)
	if (foundUnderReview) {
		WebUI.click(underReview)
	} else {
		WebUI.comment('No report found with "To be reviewed" or "Under review" status.')
		WebUI.closeBrowser()
		return
	}
}

// ---------- STEP 4: Open Assigned To dropdown ----------
TestObject assignedDropdown = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button")
WebUI.click(assignedDropdown)

// ---------- STEP 5: Scroll & Select ONLY user named "admin" ----------
TestObject adminUser = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//li[normalize-space(text())='admin']")
WebUI.waitForElementVisible(adminUser, 5)
WebUI.click(adminUser)

// ---------- STEP 6: Wait for Re-assign popup and click "Re-assign" ----------
TestObject reassignBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//button[normalize-space()='Re-assign']")
WebUI.waitForElementVisible(reassignBtn, 5)
WebUI.click(reassignBtn)

// ---------- STEP 7: Wait for reassignment to take place ----------
WebUI.delay(5)

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
