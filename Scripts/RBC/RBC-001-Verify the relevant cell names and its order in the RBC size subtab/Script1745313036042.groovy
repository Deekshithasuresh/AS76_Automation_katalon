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

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Size'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Size'), 10)

WebDriver driver =DriverFactory.getWebDriver()

List<WebElement> cellRows = WebUiCommonHelper.findWebElements(
	findTestObject('Object Repository/RBC_Objects/Page_PBS/Cell_rows'),1)

ArrayList<String> Cellnames= new ArrayList<>(Arrays.asList('Microcytes','Macrocytes','Anisocytosis'))

for (int i=0; i<cellRows.size();i++) 
	{
WebElement cellname_ele = cellRows.get(i).findElement(By.xpath(".//div[1]"))
String cellname = cellname_ele.getText()
assert cellname.equals(Cellnames[i])
}