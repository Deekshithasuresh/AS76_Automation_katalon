import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Selenium & Katalon helpers
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.Dimension

// 1) LOGIN & OPEN IMAGE SETTINGS
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// wait, open first “Under review” report, click WBC, open settings… (omitted for brevity)
// …
// after you’ve done:
//   WebUI.click(wbcTab)
//   WebUI.click(imgSettingsIcon)
//   WebUI.verifyElementPresent(popup, 10)

WebUI.delay(1)  // ensure the panel is fully rendered

WebDriver driver = DriverFactory.getWebDriver()

// 2) LOCATE THE IMAGE ELEMENT AND MEASURE WIDTH BEFORE
TestObject imgTO = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	'div.microscopic-right-pane img'
)
WebElement imageBefore = WebUiCommonHelper.findWebElement(imgTO, 10)
int widthBefore = imageBefore.getSize().getWidth()
WebUI.comment("Width before resize: ${widthBefore}px")

// 3) LOCATE THE IMAGE SIZE SLIDER THUMB
TestObject thumbTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[normalize-space()='Image Size']/following-sibling::div//span[contains(@class,'MuiSlider-thumb')]"
)
WebElement sizeThumb = WebUiCommonHelper.findWebElement(thumbTO, 10)

// 4) DRAG IT ALL THE WAY RIGHT
new Actions(driver)
	.clickAndHold(sizeThumb)
	.moveByOffset(100, 0)  // adjust if your slider track is longer
	.release()
	.perform()
WebUI.delay(1)

// 5) RE-MEASURE THE IMAGE WIDTH
WebElement imageAfter = WebUiCommonHelper.findWebElement(imgTO, 10)
int widthAfter = imageAfter.getSize().getWidth()
WebUI.comment("Width after resize:  ${widthAfter}px")

// 6) ASSERT IT GREW
WebUI.verifyGreaterThan(widthAfter, widthBefore)
WebUI.comment("✅ Thumbnail grew from ${widthBefore}px to ${widthAfter}px")
