import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Takes a full‐page screenshot into the report folder and returns its Base64 string.
 */
String snapAndBase64(String filename) {
	String path = "${RunConfiguration.getReportFolder()}/${filename}"
	WebUI.takeScreenshot(path)
	return Files.readAllBytes(Paths.get(path)).encodeBase64().toString()
}

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

// ────────────────────────────────────────────────────────────────────
// 2) plt → MICROSCOPIC VIEW & WAIT 120s
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))
WebUI.delay(120)

// ────────────────────────────────────────────────────────────────────
// 3) ZOOM IN TWICE (120s BETWEEN)
// ────────────────────────────────────────────────────────────────────
def zoomIn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
WebUI.waitForElementClickable(zoomIn, 30)
(1..2).each { i ->
	WebUI.click(zoomIn)
	WebUI.delay(120)
	WebUI.comment("✔ Completed zoom-in #${i}")
}

// ────────────────────────────────────────────────────────────────────
// 4) OPEN OVERVIEW (NAVIGATION) PANEL
// ────────────────────────────────────────────────────────────────────
def overviewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@title='Overview']")
WebUI.waitForElementClickable(overviewBtn, 30)
WebUI.click(overviewBtn)

// ────────────────────────────────────────────────────────────────────
// 5) PAN INSET-MAP CANVAS 3× (LEFT/RIGHT/UP/DOWN)
// ────────────────────────────────────────────────────────────────────
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import org.openqa.selenium.WebElement

// wait for the inset-map container to appear…
TestObject insetTest = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-map')]//canvas[contains(@class,'ol-layer')]"
)
WebUI.waitForElementVisible(insetTest, 30)

// now grab it as a WebElement
WebElement inset = DriverFactory
	.getWebDriver()
	.findElement(By.xpath(insetTest.findPropertyValue('xpath')))

// pan offsets
def pans = [ left:[-50,0], right:[50,0], up:[0,-50], down:[0,50] ]
def actions = new Actions(DriverFactory.getWebDriver())

pans.each { dir, delta ->
	int dx = delta[0], dy = delta[1]

	// 5a) drag the inset-map
	actions.clickAndHold(inset)
		   .moveByOffset(dx, dy)
		   .release()
		   .perform()
	WebUI.delay(1)

	// 5b) screenshot with panel still open
	String openShot = "overview_pan_${dir}_open.png"
	String openB64  = snapAndBase64(openShot)
	WebUI.comment("🔍 [${dir}] WITH panel open → ${openShot}")

	// 5c) close panel
	WebUI.click(overviewBtn)
	WebUI.delay(2)

	// 5d) screenshot with panel closed
	String closedShot = "overview_pan_${dir}_closed.png"
	String closedB64  = snapAndBase64(closedShot)
	WebUI.comment("🔍 [${dir}] WITH panel closed → ${closedShot}")

	// 5e) reopen for next direction
	WebUI.click(overviewBtn)
	WebUI.delay(2)
}