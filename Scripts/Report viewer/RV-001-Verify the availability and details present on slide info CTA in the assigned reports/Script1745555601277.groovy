import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

// 4) OPEN SLIDE-INFO DRAWER
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementVisible(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// 5) VERIFY SLIDE-INFO DRAWER ELEMENTS
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// — Slide ID label (note uppercase “ID”)
TestObject slideIdLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'slideInfoComponent_drawer__header-title')][1]"
)
WebUI.verifyElementPresent(slideIdLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

// — Status label (either “To be reviewed” or “Under review”)
TestObject statusLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_status')]/span[normalize-space(.)='To be reviewed' or normalize-space(.)='Under review']"
)
WebUI.verifyElementPresent(statusLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

// — Other info labels
for (String text : ['Slide image', 'Scanned by', 'Scanned on']) {
	TestObject lbl = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//*[normalize-space(text())='${text}']"
	)
	WebUI.verifyElementPresent(lbl, 5, FailureHandling.CONTINUE_ON_FAILURE)
}

// — Close-drawer button
TestObject closeBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[@src='/icons/cancel.svg']]"
)
WebUI.verifyElementPresent(closeBtn, 5, FailureHandling.CONTINUE_ON_FAILURE)


