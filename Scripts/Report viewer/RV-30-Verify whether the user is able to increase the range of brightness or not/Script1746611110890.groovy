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

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 4) NAVIGATE TO IMAGE SETTINGS
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/td_tstt'))
WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/button_Summary'), 'Summary')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_WBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/div_Image settings'), 'Image settings')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))
WebUI.delay(2)  // wait for panel

// 5) DRAG THE BRIGHTNESS THUMB RIGHT
// 5a. Get WebDriver
WebDriver driver = DriverFactory.getWebDriver()

// 5b. Grab all slider thumbs via XPath
TestObject thumbsTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'MuiSlider-thumb')]"
)
List<WebElement> allThumbs = WebUiCommonHelper.findWebElements(thumbsTO, 10)

// 5c. The brightness thumb is typically the second one (index 1)
WebElement brightnessThumb = allThumbs[1]

// 5d. Scroll it into view
((org.openqa.selenium.JavascriptExecutor) driver)
	.executeScript("arguments[0].scrollIntoView(true);", brightnessThumb)
Thread.sleep(500)

// 5e. Drag it right by 100px (adjust offset as needed)
Actions action = new Actions(driver)
action.clickAndHold(brightnessThumb)
	  .moveByOffset(100, 0)
	  .release()
	  .perform()

WebUI.delay(2)
