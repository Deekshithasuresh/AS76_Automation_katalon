import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// ---------- STEP 1: Log in ----------
CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
// 3) OPEN SLIDE-INFO DRAWER
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// wait for the drawer panel to appear
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// ---------- STEP 4: Verify headers flexibly ----------
def verifyHeaderContains = { String txt ->
	TestObject to = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//*[contains(normalize-space(.), '${txt}')]")
	WebUI.verifyElementPresent(to, 5, FailureHandling.CONTINUE_ON_FAILURE)
}

verifyHeaderContains('Slide Id')
verifyHeaderContains('Slide image')
verifyHeaderContains('Scanned by')
verifyHeaderContains('Scanned on')

// verify the “Under review” status badge
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

WebUI.comment("✔ Slide-info for Under review report is correct and “Scan time” has been removed.")
