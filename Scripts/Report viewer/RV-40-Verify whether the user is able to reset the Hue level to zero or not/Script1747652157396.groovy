import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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
WebUI.delay(2)  // wait for sliders

// 2) FETCH SLIDER INPUT ELEMENTS VIA CSS
WebDriver driver = DriverFactory.getWebDriver()
TestObject sliderInputsTO = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	".MuiSlider-thumb input"
)
// use Katalon helper to wait up to 10s
List<WebElement> sliderInputs = WebUiCommonHelper.findWebElements(sliderInputsTO, 10)

if (sliderInputs.size() < 3) {
	WebUI.comment("❌ Less than 3 slider inputs found. Cannot access Hue slider.")
	assert false : "Hue slider input not found."
}

// index 2 is Hue
WebElement hueInput = sliderInputs[2]

// 3) MOVE HUE TO 50 VIA JS
((JavascriptExecutor) driver).executeScript("""
    arguments[0].value = 50;
    arguments[0].setAttribute('aria-valuenow', '50');
    arguments[0].dispatchEvent(new Event('input', { bubbles: true }));
    arguments[0].dispatchEvent(new Event('change', { bubbles: true }));
""", hueInput)
WebUI.delay(1)

// verify hue moved
String increasedHueValue = hueInput.getAttribute("aria-valuenow")
WebUI.comment("🎨 Hue increased to: ${increasedHueValue}")
assert increasedHueValue != "0" : "Hue increase failed. Still 0."

// 4) CLICK RESET
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Reset'))
WebUI.delay(1)

// 5) VERIFY HUE RETURNS TO ZERO
// after reset, the same input element reflects the new state
String resetHueValue = hueInput.getAttribute("aria-valuenow")
if (resetHueValue == "0") {
	WebUI.comment("✅ Hue reset to 0 successfully.")
} else {
	WebUI.comment("❌ Hue reset failed. Current value: ${resetHueValue}")
	assert false : "Hue slider was not reset to 0."
}
