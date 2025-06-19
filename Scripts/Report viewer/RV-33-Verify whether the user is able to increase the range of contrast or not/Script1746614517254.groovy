import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

// Assumes you are already logged in and Image Settings is open
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

// Assuming contrast is the third slider thumb (index 2)
WebElement contrastThumb = allThumbs[2]

// Scroll into view to ensure visibility (optional but recommended)
    ((driver) as org.openqa.selenium.JavascriptExecutor).executeScript('arguments[0].scrollIntoView(true);', contrastThumb)

Thread.sleep(500)

// Move contrast slider to the right (increase contrast)
Actions action = new Actions(driver)

action.clickAndHold(contrastThumb).moveByOffset(100, 0).release().perform()

WebUI.delay(2)

