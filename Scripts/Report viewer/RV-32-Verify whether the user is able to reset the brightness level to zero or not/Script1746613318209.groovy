import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

// Assuming you're already logged in and Image Settings panel is open
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

// Assuming brightness is the second thumb (index 1)
WebElement brightnessThumb = allThumbs[1]

// Scroll into view (optional but safer)
    ((driver) as org.openqa.selenium.JavascriptExecutor).executeScript('arguments[0].scrollIntoView(true);', brightnessThumb)

Thread.sleep(500)

// Move brightness to +100
Actions action = new Actions(driver)

action.clickAndHold(brightnessThumb).moveByOffset(100, 0).release().perform()

WebUI.delay(1)

// Click Reset button
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Reset'))

WebUI.delay(1)

// Re-locate the thumb after reset to verify position
WebElement resetBrightnessThumb = driver.findElements(By.xpath('//span[contains(@class, \'MuiSlider-thumb\')]'))[1]

// Get its style or position value for verification (e.g., left % or pixel position)
String leftStyle = resetBrightnessThumb.getAttribute('style')

println('Brightness thumb style after reset: ' + leftStyle)

// Optional: Assert position is near center
assert leftStyle.contains('left: 50%') : 'Brightness not reset to center'

WebUI.delay(2)

