import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

// 4) WAIT FOR MICROSCOPE ICON AND CLICK IT
TestObject microViewIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[contains(@class,'microscope-button') or @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.verifyElementVisible(microViewIcon, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Microscopic view icon is present")
WebUI.click(microViewIcon)

// 5) VERIFY THE CIRCLE TOOL ICON IS PRESENT
TestObject circleToolBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='circle-tool']"
)
WebUI.waitForElementVisible(circleToolBtn, 10)
WebUI.verifyElementClickable(circleToolBtn, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Circle tool icon is present")
