import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.time.Duration;
import com.kms.katalon.core.util.KeywordUtil


import loginPackage.Login
import platelet_Package.Platelet_P
Login lg = new Login()


Platelet_P plt = new Platelet_P()

lg.login()//logged in with 'jyothi' username

WebUI.delay(2)

lg.selectReportByStatus('Under Review')

lg.assignOrReassignOnTabs('premkumar')// pass username other than logged in user

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 30)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 30)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'), 30)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'))

plt.verifyModifingPlateletDetetctedNotDetecetdStatus()

plt.verifyModifingPlateletDetetctedNotDetecetdStatus()

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/split-view_button'), 30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/split-view_button'))

plt.verifyModifingPlateletDetetctedNotDetecetdStatus()

plt.verifyModifingPlateletDetetctedNotDetecetdStatus()

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/Microscopic_view_CTA'), 30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/Microscopic_view_CTA'))

WebDriver driver = DriverFactory.getWebDriver()
Actions act = new Actions(driver)
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5))

List<WebElement> cellRows = driver.findElements(By.xpath('//tbody/tr'))

for(WebElement cell_row:cellRows)
{
	String cellname=cell_row.findElement(By.xpath(".//div[@class='cell-label ']")).getText()
	String count_css=cell_row.findElement(By.xpath("./td/div")).getCssValue('text-decortaion')
	println(count_css)
	if (!count_css.contains("line-through")) 
		{
    System.out.println("✅ Text is not struck through.");
	KeywordUtil.markPassed("Text is struck through when it should not be.")	
} 
else {
    System.out.println("❌ Text is struck through.");
	KeywordUtil.markFailed("Text is struck through when it should not be.")
}
}