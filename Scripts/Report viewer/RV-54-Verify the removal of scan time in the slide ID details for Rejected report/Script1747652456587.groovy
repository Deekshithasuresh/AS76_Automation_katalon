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
    'xpath', ConditionType.EQUALS,
    "//div[starts-with(@class,'reportTypeComponent_report-type-container')]" +
    "[.//span[normalize-space()='Ready for review']]"
)
WebUI.waitForElementClickable(readyDropdown, 10)
WebUI.click(readyDropdown)

// ---------- STEP 4: Select “Reviewed” from the list ----------
TestObject reviewedOption = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[starts-with(@class,'reportTypeComponent_report-type-list-item')]" +
    "[.//span[normalize-space()='Reviewed']]"
)
WebUI.waitForElementClickable(reviewedOption, 10)
WebUI.click(reviewedOption)

// ---------- STEP 5: Pick a report with status “Rejected” ----------
TestObject rejectedStatus = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//span[normalize-space()='Rejected']"
)
WebUI.waitForElementPresent(rejectedStatus, 10)
WebUI.scrollToElement(rejectedStatus, 5)
WebUI.click(rejectedStatus)

// ---------- STEP 6: Click the slide-info icon ----------
TestObject slideInfoIcon = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 10)
WebUI.click(slideInfoIcon)

// ---------- STEP 7: Wait for and verify the slide-info drawer ----------
TestObject drawer = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// ---------- STEP 8: Verify headers including “Rejected” status ----------
String[] textHeaders = ['Slide ID:', 'Slide image', 'Scanned by', 'Scanned on']
textHeaders.each { txt ->
    WebUI.verifyElementPresent(
        new TestObject().addProperty('xpath', ConditionType.EQUALS,
            "//*[normalize-space(text())='${txt}']"),
        5, FailureHandling.CONTINUE_ON_FAILURE)
}
TestObject rejectedStatusInDrawer = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'slideInfoComponent_status__rejected')]//span[normalize-space()='Rejected']"
)
WebUI.verifyElementPresent(rejectedStatusInDrawer, 5, FailureHandling.CONTINUE_ON_FAILURE)

// ---------- STEP 9: Ensure old “Scan time” header is NOT present anywhere ----------
WebUI.verifyElementNotPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS,
        "//*[normalize-space(text())='Scan time']"),
    5, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.comment("✔ Verified no ‘Scan time’ header in slide-info popup.")


