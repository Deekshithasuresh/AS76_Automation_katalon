
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.JavascriptExecutor
import java.time.Duration

// Open browser and login
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/View list of users/Page_Admin Console/input_Username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/View list of users/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Sign in'))

// Navigate to Users tab
WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/div_User'))
WebUI.click(findTestObject('Object Repository/View list of users/Page_Admin Console/button_Users'))

// Scroll the table using JavaScript
WebDriver driver = DriverFactory.getWebDriver()
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

WebElement tableElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")))

JavascriptExecutor js = (JavascriptExecutor) driver
js.executeScript("arguments[0].scrollIntoView(false);", tableElement)  // Scroll to bottom
WebUI.delay(2)
js.executeScript("arguments[0].scrollIntoView(true);", tableElement)   // Scroll back to top
