import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Selenium & Katalon driver/helpers ↓
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions

// 1) LOGIN & OPEN IMAGE SETTINGS
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
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
WebUI.delay(2)  // wait for the sliders to render

// 2) LOCATE ALL SLIDER THUMBS
WebDriver driver = DriverFactory.getWebDriver()
TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)
List<WebElement> allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)
if (allThumbs.size() < 4) {
	assert false : "Expected at least 4 slider thumbs but found ${allThumbs.size()}"
}

// 3) TARGET THE HUE THUMB (index 3)
WebElement hueThumb = allThumbs[3]

// 4) SCROLL INTO VIEW & DRAG IT TO MAXIMUM
((JavascriptExecutor) driver)
	.executeScript("arguments[0].scrollIntoView(true);", hueThumb)
Thread.sleep(500)

new Actions(driver)
	.clickAndHold(hueThumb)
	.moveByOffset(100, 0)   // drag right by ~100px
	.release()
	.perform()
WebUI.delay(1)

// 5) VERIFY THE HUE INPUT VALUE IS 100
WebElement hueInput = driver.findElement(
	By.xpath("//span[contains(text(),'Hue')]/following::input[1]")
)
String hueValue = hueInput.getAttribute("value")
if (hueValue == '100') {
	WebUI.comment("✅ Hue set to maximum (100).")
} else {
	WebUI.comment("❌ Expected Hue=100 but got: ${hueValue}")
	assert false : "Hue not at max; current value = ${hueValue}"
}


