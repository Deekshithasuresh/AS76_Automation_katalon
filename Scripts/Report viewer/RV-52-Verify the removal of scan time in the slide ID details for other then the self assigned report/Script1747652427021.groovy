import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("prem")

// 3) OPEN SLIDE-INFO DRAWER
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='info.svg']"
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

verifyContains('Slide Id')
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
