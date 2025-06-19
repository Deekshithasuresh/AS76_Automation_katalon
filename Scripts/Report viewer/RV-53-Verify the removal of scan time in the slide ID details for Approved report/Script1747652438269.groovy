import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 3: Open the “Ready for review” dropdown ----------
TestObject readyDropdown = new TestObject().addProperty(
    'xpath',
    ConditionType.EQUALS,
    // match the outer container by its prefix and the inner span text
    "//div[starts-with(@class,'reportTypeComponent_report-type-container')]" +
    "[.//span[normalize-space()='Ready for review']]"
)
WebUI.waitForElementClickable(readyDropdown, 10)
WebUI.click(readyDropdown)


// ---------- STEP 4: Select “Reviewed” from the list ----------
TestObject reviewedOption = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'reportTypeComponent_report-type-list-item')]" +
    "//span[normalize-space()='Reviewed']"
)
WebUI.waitForElementClickable(reviewedOption, 10)
WebUI.click(reviewedOption)

// ---------- STEP 5: Pick a report with status “Approved” ----------
TestObject approvedStatus = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//span[normalize-space()='Approved']"
)
WebUI.waitForElementPresent(approvedStatus, 10)
WebUI.scrollToElement(approvedStatus, 5)
WebUI.click(approvedStatus)

// ---------- STEP 6: Open the slide‐info drawer ----------
TestObject slideInfoIcon = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// wait for drawer
TestObject drawer = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// ---------- STEP 7: Verify Slide‐info headers include “Approved” status ----------
String[] headers = [
    'Slide ID:',
    // status container for “Approved”
    "//div[contains(@class,'slideInfoComponent_status')]" +
    "//span[normalize-space()='Approved']",
    'Slide image',
    'Scanned by',
    'Scanned on'
]

// text‐only headers
['Slide ID:', 'Slide image', 'Scanned by', 'Scanned on'].each { txt ->
    WebUI.verifyElementPresent(
        new TestObject().addProperty('xpath', ConditionType.EQUALS,
            "//*[normalize-space(text())='${txt}']"),
        5, FailureHandling.CONTINUE_ON_FAILURE)
}

// status header
WebUI.verifyElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, headers[1]),
    5, FailureHandling.CONTINUE_ON_FAILURE)

// ---------- STEP 8: Ensure old “Scan time” header is NOT present ----------
WebUI.verifyElementNotPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS,
        "//*[normalize-space(text())='Scan time']"),
    5, FailureHandling.CONTINUE_ON_FAILURE)

