import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// 1) LOGIN (same as before) …
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) OPEN FIRST “Under review” REPORT
TestObject underReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReview, 10)
WebUI.scrollToElement(underReview, 5)
WebUI.click(underReview)

// 3) OPEN SLIDE-INFO DRAWER
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='info.svg' and contains(@class,'slide-info')]"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// 4) WAIT FOR DRAWER PANEL
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// 5) VERIFY HEADERS USING `contains(normalize-space(),…)`
def verifyHeader = { String txt ->
	TestObject to = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//*[contains(normalize-space(), '${txt}')]"
	)
	WebUI.verifyElementPresent(to, 5, FailureHandling.CONTINUE_ON_FAILURE)
}

// mandatory headers
verifyHeader('Slide ID')
verifyHeader('Slide image')
verifyHeader('Scanned by')
verifyHeader('Scanned on')

// status badge
WebUI.verifyElementPresent(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//div[contains(@class,'slideInfoComponent_status__to-be-reviewed')]//span[normalize-space()='To be reviewed']"
	),
	5, FailureHandling.CONTINUE_ON_FAILURE
)

// 6) VERIFY “Scan time” IS ABSENT
TestObject scanTimeTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//*[contains(normalize-space(), 'Scan time')]"
)
WebUI.verifyElementNotPresent(scanTimeTO, 5, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment("✔ Slide-info panel headers look correct and “Scan time” was removed.")
