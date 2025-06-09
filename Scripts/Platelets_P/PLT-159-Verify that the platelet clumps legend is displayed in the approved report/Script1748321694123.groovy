import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
import platelet_Package.Platelet_P
Login lg = new Login()


Platelet_P plt = new Platelet_P()

lg.login()//logged in with 'jyothi' username

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

//lg.assignOrReassignOnTabs('premkumar')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'))

WebDriver driver =DriverFactory.getWebDriver()

List <WebElement> FOV_rows =driver.findElements(By.xpath("//div[@class='fov-tuple' or @class='fov-tuple selected-fov-tuple']"))


for(WebElement FOV :FOV_rows)
{
	FOV.click()
WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_legend'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_legend'), 10)

String color_code=WebUI.getCSSValue(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelet_clumps_legend'), 'color')

WebUI.comment(color_code)

assert color_code.equals('rgba(51, 51, 51, 1)')

}