import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
import zoom.ZoomInOut as ZoomInOut

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Report_status_drop_down'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/Report_status_drop_down'))

List<WebElement> status_options = WebUiCommonHelper.findWebElements(
	findTestObject('Object Repository/RBC_Objects/Page_PBS/Report_status_drop_down_options'),10)

for(WebElement status:status_options)
{
	String status_text=status.findElement(By.xpath("./span")).getText()
	if(status_text.equals('Reviewed'))
	{
		status.click()
	}
}

lg.selectReportByStatus('Approved')

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

assert zoom.checkUserNotAbleRegradeForDifferentStatusOfReport()==true

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'))

assert zoom.checkUserNotAbleRegradeForDifferentStatusOfReport()== true

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'))

assert zoom.checkUserNotAbleRegradeForDifferentStatusOfReport()== true

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Inclusions'))

assert zoom.checkUserNotAbleRegradeForDifferentStatusOfReport()== true
