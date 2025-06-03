import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory



// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 3: Pick & assign a report ----------
TestObject statusToBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']"
)
TestObject statusUnderReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[normalize-space(text())='admin']"
)
TestObject reassignButton = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Re-assign']"
)
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)

if (WebUI.waitForElementPresent(statusToBeReviewed, 3)) {
	WebUI.scrollToElement(statusToBeReviewed, 5)
	WebUI.click(statusToBeReviewed)
	WebUI.click(assignedDropdown)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.click(adminOption)

	WebUI.comment("Assigned a ‘To be reviewed’ report to admin.")
} else if (WebUI.waitForElementPresent(statusUnderReview, 3)) {
	WebUI.scrollToElement(statusUnderReview, 5)
	WebUI.click(statusUnderReview)

	// check current assignee
	String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
	if (currentAssignee != 'admin') {
		// reassign only if not already admin
		WebUI.click(assignedDropdown)
		WebUI.waitForElementClickable(adminOption, 5)
		WebUI.click(adminOption)
		WebUI.waitForElementClickable(reassignButton, 5)
		WebUI.click(reassignButton)
		WebUI.comment("Re-assigned an ‘Under review’ report to admin.")
	} else {
		WebUI.comment("‘Under review’ report already assigned to admin—no reassignment needed.")
	}
} else {
	WebUI.comment("❌ No report in ‘To be reviewed’ or ‘Under review’ status.")
	WebUI.takeScreenshot()
	WebUI.closeBrowser()
	return
}

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment(" 'Approve report' is now visible.")


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

