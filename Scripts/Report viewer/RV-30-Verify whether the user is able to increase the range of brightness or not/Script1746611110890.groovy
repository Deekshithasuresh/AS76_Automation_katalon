import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

// Assuming you are already logged in and Image Settings is opened
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

// Locate the brightness slider thumb (it will be the 2nd thumb typically after image size slider)
List<WebElement> allThumbs = driver.findElements(By.xpath('//span[contains(@class, \'MuiSlider-thumb\')]'))

// Assuming 0 = Image Size, 1 = Brightness (adjust index if needed)
WebElement brightnessThumb = allThumbs[1]

// Scroll into view
    ((driver) as org.openqa.selenium.JavascriptExecutor).executeScript('arguments[0].scrollIntoView(true);', brightnessThumb)

Thread.sleep(500)

// Move to 100 (adjust pixel offset as needed, e.g., 100px for full range)
Actions action = new Actions(driver)

action.clickAndHold(brightnessThumb).moveByOffset(100, 0).release().perform()

WebUI.delay(2)

