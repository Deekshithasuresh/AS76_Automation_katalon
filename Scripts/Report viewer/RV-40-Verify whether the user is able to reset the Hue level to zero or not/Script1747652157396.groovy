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

// Step 5: Locate all slider inputs (Hue is 3rd one, index = 2)
List<WebElement> sliderInputs = driver.findElements(By.cssSelector(".MuiSlider-thumb input"))

if (sliderInputs.size() < 3) {
	WebUI.comment("❌ Less than 3 sliders found. Cannot access Hue slider.")
	assert false : "Hue slider not found."
}

WebElement hueInput = sliderInputs[2]

// Step 6: Increase Hue to 50 using JavaScript
JavascriptExecutor js = (JavascriptExecutor) driver
js.executeScript("""
    arguments[0].value = 50;
    arguments[0].setAttribute('aria-valuenow', '50');
    arguments[0].dispatchEvent(new Event('input', { bubbles: true }));
    arguments[0].dispatchEvent(new Event('change', { bubbles: true }));
""", hueInput)

WebUI.delay(1)

// Step 7: Verify hue is now non-zero
String increasedHueValue = hueInput.getAttribute("aria-valuenow")
WebUI.comment("🎨 Hue increased to: " + increasedHueValue)
assert increasedHueValue != "0" : "Hue increase failed. Still 0."

// Step 8: Click the Reset button
WebElement resetButton = driver.findElement(By.cssSelector("button.reset-btn"))
resetButton.click()
WebUI.delay(2) // Wait for UI to reflect changes

// Step 9: Verify Hue is reset to 0
String resetHueValue = hueInput.getAttribute("aria-valuenow")
if (resetHueValue == "0") {
	WebUI.comment("✅ Hue reset to 0 successfully.")
} else {
	WebUI.comment("❌ Hue reset failed. Current value: " + resetHueValue)
	assert false : "Hue slider was not reset to 0."
}
