import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

CustomKeywords.'generic.custumFunctions.login'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Ready for review'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed_1'), 'Reviewed')

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Approved")


// ---------- STEP 6: Open the slide-info drawer ----------
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// wait for the drawer panel
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// ---------- STEP 7: Verify headers flexibly ----------
def verifyContains = { String txt ->
	TestObject to = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//*[contains(normalize-space(.), '${txt}')]"
	)
	WebUI.verifyElementPresent(to, 5, FailureHandling.CONTINUE_ON_FAILURE)
}

verifyContains('Slide Id')
verifyContains('Slide image')
verifyContains('Scanned by')
verifyContains('Scanned on')

// verify the “Approved” status badge
WebUI.verifyElementPresent(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//div[contains(@class,'slideInfoComponent_status')]//span[normalize-space()='Approved']"
	),
	5, FailureHandling.CONTINUE_ON_FAILURE
)

// ---------- STEP 8: Ensure “Scan time” header is NOT present ----------
WebUI.verifyElementNotPresent(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//*[contains(normalize-space(.), 'Scan time')]"
	),
	5, FailureHandling.CONTINUE_ON_FAILURE
)

WebUI.comment("✔ Slide-info for Approved report is correct and “Scan time” has been removed.")
