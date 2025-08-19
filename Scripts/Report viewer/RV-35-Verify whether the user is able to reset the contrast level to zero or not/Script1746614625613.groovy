import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// ← Selenium & Katalon driver/helpers ↓
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_WBC'))
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))
WebUI.delay(2)  // wait for panel

// 1) GET DRIVER
WebDriver driver = DriverFactory.getWebDriver()

// 2) LOCATE ALL SLIDER THUMBS
TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)
List<WebElement> allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)

// 3) TARGET THE CONTRAST THUMB (index 2)
WebElement contrastThumb = allThumbs[2]

// 4) MOVE IT TO PROVE IT CHANGES
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", contrastThumb)
Thread.sleep(500)
new Actions(driver)
	.clickAndHold(contrastThumb)
	.moveByOffset(100, 0)   // drag right
	.release()
	.perform()
WebUI.delay(1)

// 5) CLICK RESET BUTTON
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Reset'))
WebUI.delay(1)

// 6) RE-LOCATE SLIDER ELEMENT WITH aria-valuenow (not the thumb!)
TestObject contrastSliderTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//input[@type='range'])[3]"   // 3rd slider = Contrast
)
WebElement contrastSlider = WebUiCommonHelper.findWebElement(contrastSliderTO, 10)

// 7) VERIFY VALUE BACK TO ZERO
String ariaValue = contrastSlider.getAttribute('aria-valuenow')
println "Contrast after reset: ${ariaValue}"
assert ariaValue == '0' : "Expected contrast=0 but was ${ariaValue}"

WebUI.delay(2)