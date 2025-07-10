import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
// 3) OPEN SLIDE-INFO DRAWER
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='info.svg']"
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
verifyHeader('Slide Id')
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
