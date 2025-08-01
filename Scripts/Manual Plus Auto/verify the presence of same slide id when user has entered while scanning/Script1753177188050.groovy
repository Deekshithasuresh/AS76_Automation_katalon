import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// ----------------------------
// ✅ Login
// ----------------------------
CustomKeywords.'generic.custumFunctions.login'()

// ----------------------------
// ✅ Load Slide ID from Excel
// ----------------------------
String filePath = 'Time_zone_data/Manual_data.xlsx'
String Slideid = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'Slide ID Additional')
String SlideInfo = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'Slide ID Additional Info')

println "✅ Slide ID from Excel:${Slideid}"
println "✅ Slide ID info from Excel:${SlideInfo}"


// ----------------------------
// ✅ Click the matching slide row in UI
// ----------------------------
String xpath = "(//tr[.//td[contains(normalize-space(), '${Slideid}')]])[1]//td//img[@alt='info-icon']"
TestObject matchingRow = new TestObject('dynamicReportRow')
matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
WebUI.waitForElementClickable(matchingRow, 10)
WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
rowElement.click()
WebUI.comment("✅ Clicked first row with slide id = '${Slideid}'")

WebDriver driver = DriverFactory.getWebDriver()
String slideName=driver.findElement(By.xpath("//span[@class='slideid-detail']")).getText()
String AdditionalInformation=driver.findElement(By.xpath("//span[@class='info-details']")).getText()
if (slideName.equals(Slideid) && AdditionalInformation.equals(SlideInfo)) {
	WebUI.comment("✅  Same slide id = '${Slideid}' is present in reporting and device")
	WebUI.comment("✅ Same slide id = '${SlideInfo}' with additional information")
	
}
else {
	KeywordUtil.markFailed("Additional info and Slide id is miss match")
}


