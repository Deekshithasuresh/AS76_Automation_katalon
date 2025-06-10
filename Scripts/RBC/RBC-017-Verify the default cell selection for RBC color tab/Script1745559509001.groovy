import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
Login lg = new Login()

lg.login()
WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'))

List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
	findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),1)

for (WebElement row : cellRows) {
// Get the percentage element (last div inside the row)
WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
String cellname = cellname_ele.getText()
println(cellname)
float percentage_flaot_value = Float.parseFloat(percentageElement.getText())
println(percentage_flaot_value)
if(percentage_flaot_value!=0.0)
{
	WebUI.comment(cellname+" is a defualt cell")
	break;
}
else {
	continue;
}
}