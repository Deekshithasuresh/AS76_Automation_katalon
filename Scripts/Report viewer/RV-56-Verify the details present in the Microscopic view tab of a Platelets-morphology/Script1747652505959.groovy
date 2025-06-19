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
