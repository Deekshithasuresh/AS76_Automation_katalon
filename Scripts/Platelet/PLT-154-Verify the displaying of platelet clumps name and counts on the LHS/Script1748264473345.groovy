import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import ch.qos.logback.core.util.Duration
import com.kms.katalon.core.util.KeywordUtil	
import loginPackage.Login
import zoom.ZoomInOut
import platelet_Package.Platelet_P
Login lg = new Login()


Platelet_P plt = new Platelet_P()

lg.login()//logged in with 'jyothi' username

WebUI.delay(2)

lg.selectReportByStatus('Under review')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Count_Tab_CTA'))

WebDriver driver =DriverFactory.getWebDriver()

List<WebElement> cell_rows = driver.findElements(By.xpath("//tr[@class='count-table-body  ']"));

for (WebElement cell : cell_rows) {
	String cell_name=cell.findElement(By.xpath('./td[1]')).getText()
	
	if(cell_name.equals('Platelet Clumps'))
	{
		KeywordUtil.markFailed("Found a platelet count cell name in count tab")
		break
	}
	else 
		{
		continue
	}
	}