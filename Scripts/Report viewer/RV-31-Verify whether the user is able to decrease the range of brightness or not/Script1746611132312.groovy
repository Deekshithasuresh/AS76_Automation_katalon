import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(
	findTestObject('Report viewer/Page_PBS/input_username_loginId'),
	'adminuserr'
)
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
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

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/button_Summary'), 'Summary')

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_WBC'))

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/div_WBC'), 'WBC')

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/div_Image settings'), 'Image settings')

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

// Locate the brightness slider thumb (2nd thumb after image size slider)
List<WebElement> allThumbs = driver.findElements(By.xpath('//span[contains(@class, \'MuiSlider-thumb\')]'))

// Assuming index 1 = Brightness (adjust if needed)
WebElement brightnessThumb = allThumbs[1]

// Scroll into view
    ((driver) as org.openqa.selenium.JavascriptExecutor).executeScript('arguments[0].scrollIntoView(true);', brightnessThumb)

Thread.sleep(500)

// Move to -100 (left side)
Actions action = new Actions(driver)

action.clickAndHold(brightnessThumb).moveByOffset(-100, 0).release().perform()

WebUI.delay(2)

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

// Locate the brightness slider thumb (2nd thumb after image size slider)
List<WebElement> allThumbs = driver.findElements(By.xpath('//span[contains(@class, \'MuiSlider-thumb\')]'))

// Assuming index 1 = Brightness (adjust if needed)
WebElement brightnessThumb = allThumbs[1]

// Scroll into view
    ((driver) as org.openqa.selenium.JavascriptExecutor).executeScript('arguments[0].scrollIntoView(true);', brightnessThumb)

Thread.sleep(500)

// Move to -100 (left side)
Actions action = new Actions(driver)

action.clickAndHold(brightnessThumb).moveByOffset(-100, 0).release().perform()

WebUI.delay(2)

