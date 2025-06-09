import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.time.Duration

import loginPackage.Login
import zoom.ZoomInOut
import zoom.ZoomInOut as Platelet_P

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

Platelet_P plt = new Platelet_P()

lg.login() //logged in with 'jyothi' username

WebUI.delay(2)

lg.selectReportByStatus('Under Review')

lg.assignOrReassignOnTabs('prem')// pass username other than logged in user

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Platelets_CTA (1)'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'), 10)

WebUI.click(findTestObject('Object Repository/Platelet_Objects/Page_PBS/Morphology_Tab_CTA'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected_1'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet_Objects/Page_PBS/div_Not detected_1'), 10)

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
    println("❌ Element is clickable")
} else {
    println("✅Element is NOT clickable")
    KeywordUtil.markPassed("Element is not clickable")
}
}