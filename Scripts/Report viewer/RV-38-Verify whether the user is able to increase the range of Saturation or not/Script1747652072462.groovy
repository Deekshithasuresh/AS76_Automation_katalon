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

// 2) LOCATE ALL SLIDER THUMBS
WebDriver driver = DriverFactory.getWebDriver()
TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)
List<WebElement> allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)

// 3) TARGET THE SATURATION THUMB (index 4)
WebElement saturationThumb = allThumbs[4]

// 4) SCROLL INTO VIEW & DRAG RIGHT
((JavascriptExecutor) driver).executeScript(
	"arguments[0].scrollIntoView(true);", saturationThumb
)
Thread.sleep(500)

new Actions(driver)
	.clickAndHold(saturationThumb)
	.moveByOffset(100, 0)   // drag right by 100px (adjust if needed)
	.release()
	.perform()

WebUI.delay(2)
