import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/span_PBS'), 'PBS')

// Step 2: Navigate to WBC > Image Settings
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/td_tstt'))

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/button_Summary'), 'Summary')

WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_WBC'))

WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/div_Image settings'), 'Image settings')

// Step 3: Open Image Settings panel
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

// Locate all slider thumbs
List<WebElement> allThumbs = driver.findElements(By.xpath('//span[contains(@class, \'MuiSlider-thumb\')]'))

// Contrast slider = index 2
WebElement contrastThumb = allThumbs[2]

// Scroll into view
    ((driver) as JavascriptExecutor).executeScript('arguments[0].scrollIntoView(true);', contrastThumb)

Thread.sleep(500)

// Move contrast to right (increase)
Actions action = new Actions(driver)

action.clickAndHold(contrastThumb).moveByOffset(100, 0).release().perform()

WebUI.delay(2)

// Click the reset button (adjust XPath if needed)
WebElement resetButton = driver.findElement(By.xpath('//button[contains(text(),\'Reset\')]'))

resetButton.click()

WebUI.delay(2)

// Check contrast is back to center
int sliderValue = Integer.parseInt(contrastThumb.getAttribute('aria-valuenow'))

assert sliderValue == 0

