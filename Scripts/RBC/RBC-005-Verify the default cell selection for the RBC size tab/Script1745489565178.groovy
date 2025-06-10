import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login

Login lg= new Login()
lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Size'), 10)

//String micro_per=WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_0.7'))
//String macro_per=WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_24.0'))
//String Anisocytosis_per=WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_24.7'))
//
//if(!micro_per.equals("0.0"))
//{
//println("Microcytes has a some cells count, so its a default cell")
//println(micro_per)
//}
//
//else if (!macro_per.equals("0.0"))
//{
//	println("Macrocytes has a some cells count, so its a default cell")
//}
//else {
//	println("Anisocytosis has a some cells count, so its a default cell")
//}
//
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