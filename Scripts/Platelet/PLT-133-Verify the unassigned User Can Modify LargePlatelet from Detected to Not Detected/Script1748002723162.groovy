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
import loginPackage.Login
import platelet_Package.Platelet_P
Login lg = new Login()


Platelet_P plt = new Platelet_P()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')


WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'))

WebDriver driver =DriverFactory.getWebDriver()
List<WebElement> drop_downs = driver.findElements(By.xpath("//div[contains(@class,'dropdown-container')]"));

for (WebElement dropdown : drop_downs) {
	boolean isClickable = false
try {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5))
wait.until(ExpectedConditions.elementToBeClickable(dropdown))
	isClickable = true
} catch (Exception e) {
	isClickable = false
}

if (isClickable) {
	println("❌ Element is clickable and able to modify the status")
	println("please check once that which status of of the report it has opened")
	KeywordUtil.markFailed("Element is clickable")
} else {
	println("✅Element is NOT clickable and unable to modify the detected and not detected status")
	KeywordUtil.markPassed("Element is not clickable")
}
}