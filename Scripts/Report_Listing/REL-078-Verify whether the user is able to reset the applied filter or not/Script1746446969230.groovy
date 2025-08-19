import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.testng.Assert.assertTrue

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'generic.custumFunctions.login'()

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'), 0)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1'), 0)

Boolean result = CustomKeywords.'generic.FilterFunctions.assignedToFilter'(['prem'])

assertTrue(result)

List<TestObject> beforeRows = WebUI.findWebElements(
	new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody/tr"),
	10
)
int beforeCount = beforeRows.size()
WebUI.comment("📊 Reports before filter: ${beforeCount}")


WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

WebUI.verifyElementClickable(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reset'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reset'))

TestObject applyBtn = new TestObject('applyBtn')
.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'bottombar-buttons')]/span[2]")
WebUI.waitForElementVisible(applyBtn, 5)
WebUI.executeJavaScript("arguments[0].click();",
Arrays.asList(WebUiCommonHelper.findWebElement(applyBtn, 5)))
WebUI.delay(5)

//WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1'))

//WebUI.delay(5)

List<TestObject> afterRows = WebUI.findWebElements(
	new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody/tr"),
	10
)
int afterCount = afterRows.size()
WebUI.comment("📊 Reports after filter: ${afterCount}")

// 5) Verify that the report count has changed
assert beforeCount != afterCount :
	"❌ Report count did not change after applying filter. Count = ${beforeCount}"

WebUI.comment("✅ Report count changed: ${beforeCount} ➡ ${afterCount}")

