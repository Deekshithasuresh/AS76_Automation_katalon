import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// Selenium & Katalon drivers/helpers
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

// 2) GET DRIVER & SLIDER THUMBS
WebDriver driver = DriverFactory.getWebDriver()

TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)
List<WebElement> allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)
// allThumbs[0] = image size, allThumbs[1] = brightness
WebElement brightnessThumb = allThumbs[1]

// 3) MOVE BRIGHTNESS TO MAX
((JavascriptExecutor) driver)
	.executeScript("arguments[0].scrollIntoView(true);", brightnessThumb)
Thread.sleep(500)

new Actions(driver)
	.clickAndHold(brightnessThumb)
	.moveByOffset(100, 0)
	.release()
	.perform()

WebUI.delay(1)

// 4) CLICK RESET
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Reset'))
WebUI.delay(1)

// 5) VERIFY SLIDER BACK TO CENTER
// re-fetch thumbs after reset
allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)
brightnessThumb = allThumbs[1]

String style = brightnessThumb.getAttribute('style')
println "Post-reset brightness thumb style: ${style}"
assert style.contains('left: 50%') : "Expected brightness at center but was '${style}'"

WebUI.delay(2)
