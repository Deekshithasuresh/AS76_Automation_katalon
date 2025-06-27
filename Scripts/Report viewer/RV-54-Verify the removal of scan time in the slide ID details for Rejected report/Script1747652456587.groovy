import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on reports list ----------
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 3: Open the “Ready for review” dropdown ----------
TestObject readyDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[starts-with(@class,'reportTypeComponent_report-type-container') and .//span[normalize-space()='Ready for review']]"
)
WebUI.waitForElementClickable(readyDropdown, 10)
WebUI.click(readyDropdown)

// ---------- STEP 4: Select “Reviewed” from the list ----------
TestObject reviewedOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[starts-with(@class,'reportTypeComponent_report-type-list-item')]//span[normalize-space()='Reviewed']"
)
WebUI.waitForElementClickable(reviewedOption, 10)
WebUI.click(reviewedOption)

// ---------- STEP 5: Pick the first report with status “Rejected” ----------
TestObject rejectedRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Rejected']])[1]"
)
WebUI.waitForElementClickable(rejectedRow, 10)
WebUI.scrollToElement(rejectedRow, 5)
WebUI.click(rejectedRow)

// ---------- STEP 6: Open the slide-info drawer ----------
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// wait for the drawer panel to appear
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// ---------- STEP 7: Verify headers flexibly ----------
def verifyContains = { String txt ->
	TestObject to = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//*[contains(normalize-space(.), '${txt}')]"
	)
	WebUI.verifyElementPresent(to, 5, FailureHandling.CONTINUE_ON_FAILURE)
}

verifyContains('Slide ID')
verifyContains('Slide image')
verifyContains('Scanned by')
verifyContains('Scanned on')

// verify the “Rejected” status badge in the drawer
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//div[contains(@class,'slideInfoComponent_status')]//span[normalize-space()='Rejected']"
	),
	5, FailureHandling.CONTINUE_ON_FAILURE
)

// ---------- STEP 8: Ensure “Scan time” header is NOT present anywhere ----------
WebUI.verifyElementNotPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//*[contains(normalize-space(.), 'Scan time')]"
	),
	5, FailureHandling.CONTINUE_ON_FAILURE
)

WebUI.comment("✔ Slide-info for Rejected report is correct and “Scan time” has been removed.")
