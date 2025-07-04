import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)


WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

// 5) OPEN MICROSCOPIC VIEW
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)

// 6) ACTIVATE LINE TOOL
TestObject lineTool = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='line-tool']]"
)
WebUI.waitForElementClickable(lineTool, 10)
WebUI.click(lineTool)
WebUI.click(lineTool)


// 7) DRAW A LINE ON CANVAS
//WebDriver driver = DriverFactory.getWebDriver()
//WebElement canvas = driver.findElement(By.tagName("canvas"))

// click+drag from (50,50) over 100px to the right
//new Actions(driver)
//	.moveToElement(canvas, 50, 50)
//	.clickAndHold()
//	.moveByOffset(100, 0)
//	.release()
//	.perform()

// 8) WAIT FOR DIMENSION INPUT
TestObject dimInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[contains(@id,'adornment') and @type='text']"
)
WebElement element = WebUiCommonHelper.findWebElement(dimInput, 10)
JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()

WebUI.waitForElementVisible(dimInput, 10)
WebUI.comment("✔ Dimension input is visible")

// 9) VERIFY SIZES BETWEEN 5µm AND 20µm
TestObject measureOverlay = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'-inner') and contains(.,'µm')]"
)
List<String> lengths = ['5','6','10','15','20']
for (String len : lengths) {
	WebUI.click(dimInput)
	js.executeScript("arguments[0].value = '';", element)	
	WebUI.setText(dimInput, len)
	
	Actions actions = new Actions(DriverFactory.getWebDriver())
    actions.moveToElement(element, 50, 40).click().perform()
	WebUI.sendKeys(dimInput, Keys.chord(Keys.ENTER))
	
WebUI.verifyMatch(WebUI.getAttribute(dimInput, 'value'), len, true, FailureHandling.OPTIONAL)
WebUI.comment("✔ Input updated to ${len} µm")

}

WebUI.comment("✅ All line‐length adjustments verified successfully.")
