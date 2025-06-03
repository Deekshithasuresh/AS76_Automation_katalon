import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.testng.Assert.assertTrue

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'generic.custumFunctions.login'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))


WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1'), 5)

Boolean result = CustomKeywords.'generic.FilterFunctions.assignedToFilter'(['prem'])

assertTrue(result)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))


WebUI.verifyElementClickable(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reset'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reset'))

TestObject applyBtn = new TestObject('applyBtn').addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'bottombar-buttons')]/span[2]")
WebUI.waitForElementVisible(applyBtn, 5)
WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUiCommonHelper.findWebElement(applyBtn, 5)))

//WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1'))

WebUI.delay(5)


