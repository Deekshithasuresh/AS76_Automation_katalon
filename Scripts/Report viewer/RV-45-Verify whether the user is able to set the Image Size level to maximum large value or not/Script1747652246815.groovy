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

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://pbsreview.as76.local/login')
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
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_WBC'))
//WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))
WebUI.delay(2)

WebDriver driver = DriverFactory.getWebDriver()

// 2) LOCATE THE IMAGE ELEMENT AND MEASURE WIDTH BEFORE
TestObject imgTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	'//div[contains(@id,"wbc")]'
)
WebElement imageBefore = WebUiCommonHelper.findWebElement(imgTO, 10)
int widthBefore = imageBefore.getSize().getWidth()
WebUI.comment("Width before resize: ${widthBefore}px")

// 3) LOCATE THE IMAGE SIZE SLIDER THUMB
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))

TestObject thumbTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[normalize-space()='Image Size']/following-sibling::div//span[contains(@class,'MuiSlider-thumb')]"
)
WebElement sizeThumb = WebUiCommonHelper.findWebElement(thumbTO, 10)

// 4) DRAG IT ALL THE WAY RIGHT
new Actions(driver)
	.clickAndHold(sizeThumb)
	.moveByOffset(120, 0)  // adjust if your slider track is longer
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
