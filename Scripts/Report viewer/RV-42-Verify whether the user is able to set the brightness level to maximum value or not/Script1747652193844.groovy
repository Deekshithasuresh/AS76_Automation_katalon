import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
String underReviewXpath = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, underReviewXpath)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)

WebUI.click(assignedDropdown)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment(" 'Approve report' is now visible.")

// Step 2: Navigate to the WBC section
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/td_18123-H-MGG'))
WebUI.verifyElementText(findTestObject('Object Repository/Report viewer/Page_PBS/button_Summary'), 'Summary')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_WBC'))

// Step 3: Open Image Settings
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))
WebUI.delay(1)

// Step 4: Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// Step 5: Locate the slider thumb for Brightness (index 0 since it's the first slider)
WebElement brightnessThumb = driver.findElement(By.xpath("//span[contains(@class, 'MuiSlider-thumb')]"))

// Check if the thumb is found
if (brightnessThumb == null) {
	WebUI.comment("❌ Brightness slider thumb not found.")
	assert false : "Slider element not found."
}

// Step 6: Scroll the thumb into view (optional)
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", brightnessThumb)
WebUI.delay(1)

// Step 7: Use Actions to drag the slider to the right (maximum value)
Actions action = new Actions(driver)
action.clickAndHold(brightnessThumb).moveByOffset(100, 0).release().perform()

// Step 8: Allow value to update
WebUI.delay(2)

// Step 9: Locate the Brightness input element (to check the value)
WebElement brightnessInput = driver.findElement(By.xpath("//span[contains(text(),'Brightness')]/following::input[1]"))
String actualValue = brightnessInput.getAttribute("value")

// Step 10: Verify if brightness value is set to 100
if (actualValue.equals("100")) {
	WebUI.comment("✅ Brightness is successfully set to maximum (100).")
} else {
	WebUI.comment("❌ Brightness is NOT set to 100. Current value: " + actualValue)
	assert false : "Expected brightness value to be 100, but got: " + actualValue
}

// Optional: Close the browser
// WebUI.closeBrowser()
