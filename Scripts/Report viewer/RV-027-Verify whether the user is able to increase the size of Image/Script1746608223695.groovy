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

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

// Step 2: Navigate to WBC > Image Settings
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/td_tstt'))
WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/button_Summary'), 'Summary')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_WBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/div_Image settings'), 'Image settings')

// Step 3: Open Image Settings panel
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))
WebUI.delay(2)  // Wait for panel to load

// ** Step 4: Move the slider to the right **
// 4a. Get the Selenium WebDriver
WebDriver driver = DriverFactory.getWebDriver()

// 4b. Create a TestObject for the thumb
TestObject sliderThumbTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class, 'MuiSlider-thumb')]"
)

// 4c. Convert it to a WebElement (wait up to 10s)
WebElement sliderThumb = WebUiCommonHelper.findWebElement(sliderThumbTO, 10)

// 4d. Scroll into view (optional)
((org.openqa.selenium.JavascriptExecutor) driver)
	.executeScript("arguments[0].scrollIntoView(true);", sliderThumb)
Thread.sleep(500)

// 4e. Drag it right by 100 pixels
Actions action = new Actions(driver)
action.clickAndHold(sliderThumb)
	  .moveByOffset(100, 0)
	  .release()
	  .perform()

WebUI.delay(2)
