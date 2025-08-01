import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.apache.poi.xssf.usermodel.*
import org.assertj.core.error.ConditionAndGroupGenericParameterTypeShouldBeTheSame
import org.openqa.selenium.WebElement

import com.github.fge.jsonschema.library.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Login
CustomKeywords.'generic.custumFunctions.login'()

// Get Slide ID from Excel
String filePath = 'Time_zone_data/Manual_data.xlsx'
String Slideid = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'P8K not retrival')
println "✅ Slide id from Excel: ${Slideid}"

// Click Row with Matching Slide ID
String xpath = "(//tr[.//td[contains(normalize-space(), '${Slideid}')]])[1]"
TestObject matchingRow = new TestObject('dynamicReportRow')
matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
WebUI.waitForElementClickable(matchingRow, 10)
WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
rowElement.click()

// Navigate to CBC Report
WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/button_WBC'))
WebUI.verifyElementText(findTestObject('Object Repository/View CBC Report Information/Page_PBS/span_CBC report_1'), 'CBC report')
WebUI.click(findTestObject('Object Repository/View CBC Report Information/Page_PBS/span_CBC report_1'))

TestObject cbcRetrivalMsg= new TestObject("retrivalMSG")
cbcRetrivalMsg.addProperty('xpath' , ConditionType.EQUALS, "//div[@class='no-report-msg']")

String notRetrivalMsg=WebUI.getText(cbcRetrivalMsg)
if(notRetrivalMsg.equals("CBC report could not be retrieved")) {
	KeywordUtil.markPassed("Massage is as per expected :${notRetrivalMsg}")
}
else {
	KeywordUtil.markFailedAndStop("Massage is not expected :${notRetrivalMsg}")
}