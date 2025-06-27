import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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
WebUI.delay(2)  // wait for panel

// 2) GET DRIVER & LOCATE THE FIRST SLIDER THUMB (Brightness)
WebDriver driver = DriverFactory.getWebDriver()
TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)
// wait up to 10s for the first thumb
List<WebElement> thumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)
if (thumbs.isEmpty()) {
	assert false : "Brightness slider thumb not found."
}
WebElement brightnessThumb = thumbs[0]

// 3) SCROLL IT INTO VIEW
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", brightnessThumb)
Thread.sleep(500)

// 4) DRAG TO THE RIGHT (approx full range)
new Actions(driver)
	.clickAndHold(brightnessThumb)
	.moveByOffset(100, 0)  // adjust if needed
	.release()
	.perform()
WebUI.delay(1)

// 5) LOCATE THE CORRESPONDING INPUT & VERIFY VALUE
// Assuming the input immediately follows the “Brightness” label
WebElement brightnessInput = driver.findElement(
	By.xpath("//span[contains(text(),'Brightness')]/following::input[1]")
)
String actualValue = brightnessInput.getAttribute("value")

if (actualValue == '100') {
	WebUI.comment("✅ Brightness set to maximum (100).")
} else {
	WebUI.comment("❌ Expected 100 but got: ${actualValue}")
	assert false : "Brightness not at 100."
}


