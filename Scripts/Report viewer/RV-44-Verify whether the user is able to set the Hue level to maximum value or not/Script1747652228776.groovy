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

// ---------- STEP 7: Click on the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='WBC']]"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// Step 3: Open Image Settings
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/img_Manual sub-classification_image-settings'))
WebUI.delay(1)

// Step 4: Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// Step 5: Locate the slider thumb for Hue (index 0 for first slider)
WebElement hueThumb = driver.findElement(By.xpath("//span[contains(@class, 'MuiSlider-thumb')]"))
if (hueThumb == null) {
	WebUI.comment("❌ Hue slider thumb not found.")
	assert false : "Slider element not found."
}

// Step 6: Scroll to the Hue slider thumb
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hueThumb)
WebUI.delay(1)

// Step 7: Drag the Hue slider to the right (maximum value)
Actions action = new Actions(driver)
action.clickAndHold(hueThumb).moveByOffset(100, 0).release().perform()
WebUI.delay(2)

// Step 8: Locate the Hue input element and verify value
WebElement hueInput = driver.findElement(By.xpath("//span[contains(text(),'Hue')]/following::input[1]"))
String hueValue = hueInput.getAttribute("value")

if (hueValue.equals("100")) {
	WebUI.comment("✅ Hue is successfully set to maximum (100).")
} else {
	WebUI.comment("❌ Hue is NOT set to 100. Current value: " + hueValue)
	assert false : "Expected hue value to be 100, but got: " + hueValue
}

// Optional: Close the browser
// WebUI.closeBrowser()

