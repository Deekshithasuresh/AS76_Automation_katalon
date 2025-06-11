import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.googlecode.javacv.FrameGrabber.Array
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login as Login
import zoom.ZoomInOut as ZoomInOut
import platelet_Package.Platelet_P

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

Platelet_P plt = new Platelet_P()

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

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'))

plt.getAPlateletCellPatchSize()


