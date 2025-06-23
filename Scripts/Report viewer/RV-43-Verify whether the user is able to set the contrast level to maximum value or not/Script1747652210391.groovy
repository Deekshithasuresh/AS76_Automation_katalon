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

// Step 5: Locate the slider thumb for Contrast (index 1 for second slider)
WebElement contrastThumb = driver.findElements(By.xpath("//span[contains(@class, 'MuiSlider-thumb')]")).get(1)
if (contrastThumb == null) {
    WebUI.comment(" Contrast slider thumb not found.")
    assert false : "Slider element not found."
}

// Step 6: Scroll to the Contrast slider thumb
((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", contrastThumb)
WebUI.delay(1)

// Step 7: Drag the Contrast slider to the right (maximum value)
Actions action = new Actions(driver)
action.clickAndHold(contrastThumb).moveByOffset(100, 0).release().perform()
WebUI.delay(2)

// Step 8: Locate the Contrast input element and verify value
WebElement contrastInput = driver.findElement(By.xpath("//span[contains(text(),'Contrast')]/following::input[1]"))
String contrastValue = contrastInput.getAttribute("value")

if (contrastValue.equals("100")) {
    WebUI.comment("✅ Contrast is successfully set to maximum (100).")
} else {
    WebUI.comment(" Contrast is NOT set to 100. Current value: " + contrastValue)
    assert false : "Expected contrast value to be 100, but got: " + contrastValue
}

