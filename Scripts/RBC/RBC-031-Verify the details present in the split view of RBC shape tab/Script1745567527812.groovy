import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login as Login

Login lg = new Login()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))

List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
	findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),1)

for (WebElement row : cellRows) {

// Get the percentage element (last div inside the row)
	row.click()
	
WebElement percentageElement = row.findElement(By.xpath(".//div[3]"))
WebElement cellname_ele = row.findElement(By.xpath(".//div[1]"))
String cellname = cellname_ele.getText()
println(cellname)
if(cellname.equals('Acanthocytes*') || cellname.equals('Sikle Cells*') ||cellname.equals('Stomatocytes*') || cellname.equals('Poikilocytosis'))
{
	WebUI.comment("skipping the iterartion as "+cellname+" is manually graded cell");
	continue;
}
else {
String percentageText = percentageElement.getText().trim();
float percentage
if (!percentageText.isEmpty()) {
    percentage = Float.parseFloat(percentageText);
    WebUI.comment("Parsed percentage: " + percentage);
} else {
    WebUI.comment("Percentage text is empty, skipping parsing.");
    // Optionally: softAssert.fail("Percentage should not be empty");
}
println(percentage)
if(percentage!=0.0)
{
	
	WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/Home_zoom_CTA'), 10)
	
	WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/zoom_in_button'), 10)
	
	WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/zoom_out_button'), 10)
	
	WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/Overview_CTA'), 10)
	
	WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/Overview_CTA'), 10)

}
else {
	WebUI.comment(cellname+" don't have any pathes detected, so skipped and proceded to next cell to verify the test case")
	continue;
}
}
}


