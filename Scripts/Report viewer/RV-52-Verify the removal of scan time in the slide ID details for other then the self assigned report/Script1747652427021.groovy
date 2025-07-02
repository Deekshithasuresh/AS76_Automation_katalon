import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// ---------- STEP 1: Log in ----------
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Open an Under-review report NOT assigned to admin ----------
TestObject targetRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr["
  +     ".//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']"
  +   " and not(contains(normalize-space(.), 'admin'))"
  + "])[1]"
)
WebUI.waitForElementClickable(targetRow, 10)
WebUI.scrollToElement(targetRow, 5)
WebUI.click(targetRow)

// ---------- STEP 3: Open the slide-info drawer ----------
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// wait for drawer panel
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// ---------- STEP 4: Verify expected headers flexibly ----------
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

// verify the “Under review” badge
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//div[contains(@class,'slideInfoComponent_status')]//span[normalize-space()='Under review']"
	),
	5, FailureHandling.CONTINUE_ON_FAILURE
)

// ---------- STEP 5: Ensure “Scan time” is NOT present anywhere ----------
WebUI.verifyElementNotPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//*[contains(normalize-space(.), 'Scan time')]"
	),
	5, FailureHandling.CONTINUE_ON_FAILURE
)

WebUI.comment("✔ Slide-info for an under-review, non-admin-assigned report is correct and “Scan time” has been removed.")
