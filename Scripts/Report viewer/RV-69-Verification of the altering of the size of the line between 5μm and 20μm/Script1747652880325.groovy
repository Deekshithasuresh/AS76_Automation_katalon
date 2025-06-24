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


// ---------- STEP: Click on WBC tab ----------
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='WBC']/ancestor::button"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// ---------- STEP: Microscopic view ----------
TestObject microViewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)



// ---------- STEP: Activate the Line tool ----------
TestObject lineTool = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[.//img[@alt='line-tool']]"
)
WebUI.waitForElementClickable(lineTool, 10)
WebUI.click(lineTool)

// ---------- STEP: Draw a short line on the canvas ----------
WebDriver driver = DriverFactory.getWebDriver()
// locate the canvas element
WebElement canvas = driver.findElement(By.tagName("canvas"))

// click & drag 50px to the right
new Actions(driver)
    .moveToElement(canvas, 50, 50)
    .clickAndHold()
    .moveByOffset(100, 0)
    .release()
    .perform()

// ---------- STEP: Wait for the dimension INPUT to appear ----------
TestObject dimInput = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//input[contains(@id,'adornment') and @type='text']"
)
WebUI.waitForElementVisible(dimInput, 10)
WebUI.comment("✔ Dimension input is visible")

// ---------- STEP: Locate the little overlay box showing “X µm” ----------
TestObject measureOverlay = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'-inner') and contains(.,'µm')]"
)

// ---------- STEP: Loop through each target length and verify ----------
List<String> lengths = ['5','6','10','15','20']
for (String len : lengths) {
    // 1) Clear & set new value
    WebUI.click(dimInput)
    WebUI.clearText(dimInput)
    WebUI.setText(dimInput, len)
    WebUI.sendKeys(dimInput, Keys.ENTER)

    // 2) Verify the input updated
    String actual = WebUI.getAttribute(dimInput, 'value')
    WebUI.verifyMatch(actual, len, false, "✔ Input updated to ${len} µm")

    // 3) Verify the overlay tooltip reads “X µm”
    WebUI.waitForElementVisible(measureOverlay, 5)
    String overlay = WebUI.getText(measureOverlay).trim()
    WebUI.verifyMatch(overlay, "${len} µm", false, "✔ Overlay reads ${len} µm")
}

WebUI.comment(" All line‐length adjustments verified successfully.")

