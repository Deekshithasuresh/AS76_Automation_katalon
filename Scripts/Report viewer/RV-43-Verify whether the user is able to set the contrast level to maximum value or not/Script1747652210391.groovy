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
import java.util.List

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
WebUI.delay(2)  // wait for slider panel

// 2) GET DRIVER & LOCATE ALL THUMBS
WebDriver driver = DriverFactory.getWebDriver()
TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)
List<WebElement> allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)
if (allThumbs.size() < 2) {
	assert false : "Contrast slider thumb not found."
}

// 3) TARGET THE SECOND THUMB (index 1 = Contrast)
WebElement contrastThumb = allThumbs[1]

// 4) SCROLL INTO VIEW & DRAG RIGHT
((JavascriptExecutor) driver)
	.executeScript("arguments[0].scrollIntoView(true);", contrastThumb)
Thread.sleep(500)

new Actions(driver)
	.clickAndHold(contrastThumb)
	.moveByOffset(100, 0)   // adjust offset if needed
	.release()
	.perform()
WebUI.delay(1)

// 5) VERIFY THE INPUT VALUE IS 100
WebElement contrastInput = driver.findElement(
	By.xpath("//span[contains(text(),'Contrast')]/following::input[1]")
)
String contrastValue = contrastInput.getAttribute("value")

if (contrastValue == '100') {
	WebUI.comment("✅ Contrast set to maximum (100).")
} else {
	WebUI.comment("❌ Expected 100 but got: ${contrastValue}")
	assert false : "Contrast not at 100."
}


