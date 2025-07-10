import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// Selenium & Katalon driver/helpers
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions

// 1) LOGIN & NAVIGATE TO IMAGE SETTINGS
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
))
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/td_18123-H-MGG'))
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_WBC'))
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))
WebUI.delay(2)  // wait for slider panel

// 2) MOVE HUE SLIDER AWAY FROM ZERO TO PROVE IT CAN CHANGE
WebDriver driver = DriverFactory.getWebDriver()
TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)
List<WebElement> allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)
// index 3 = Hue thumb
WebElement hueThumb = allThumbs[3]
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hueThumb)
Thread.sleep(500)
new Actions(driver)
	.clickAndHold(hueThumb)
	.moveByOffset(100, 0)    // bump it right
	.release()
	.perform()
WebUI.delay(1)

// 3) CLICK RESET
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Reset'))
WebUI.delay(1)

// 4) VERIFY HUE RETURNS TO ZERO
allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)
hueThumb = allThumbs[3]
String ariaValue = hueThumb.getAttribute('aria-valuenow')
println "Hue after reset: ${ariaValue}"
assert ariaValue == '0' : "Expected Hue=0 but was ${ariaValue}"

WebUI.delay(1)
