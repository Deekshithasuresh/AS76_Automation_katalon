import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
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

// 2) DRAG THE BRIGHTNESS SLIDER LEFT
WebDriver driver = DriverFactory.getWebDriver()

// single TestObject for all thumbs
TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)

// locate both thumbs (image size + brightness)
List<WebElement> allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)

// pick the brightness thumb (index 1)
WebElement brightnessThumb = allThumbs[1]

// scroll it into view
((JavascriptExecutor) driver)
	.executeScript("arguments[0].scrollIntoView(true);", brightnessThumb)
Thread.sleep(500)

// drag it left by 100px (adjust as needed)
new Actions(driver)
	.clickAndHold(brightnessThumb)
	.moveByOffset(-100, 0)
	.release()
	.perform()

WebUI.delay(2)
