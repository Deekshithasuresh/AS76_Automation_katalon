import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.OutputType

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

// ─── STEP 3: SWITCH TO WBC TAB ──────────────────────────────────────
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// ─── STEP 4: TOGGLE MICROSCOPIC VIEW ───────────────────────────────
TestObject microView = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
)
WebUI.waitForElementClickable(microView, 10)
WebUI.click(microView)
// allow OL to render
WebUI.delay(6)

// ─── STEP 5: ZOOM IN TWICE (120s WAIT) ─────────────────────────────
TestObject zoomIn = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	"button.ol-zoom-in"
)
for (int i = 1; i <= 2; i++) {
	WebUI.click(zoomIn)
	WebUI.comment("🔍 Zoom-in #${i} clicked")
	WebUI.delay(120)
}

// ─── STEP 6: OPEN OVERVIEW PANEL ────────────────────────────────────
TestObject overviewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@title='Overview']"
)
WebUI.waitForElementClickable(overviewBtn, 10)
WebUI.click(overviewBtn)
WebUI.delay(2)

// ─── STEP 7: WAIT FOR OVERVIEW BOX ──────────────────────────────────
TestObject ovBox = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	"div.ol-overviewmap-box"
)
WebUI.waitForElementVisible(ovBox, 10)

// ─── STEP 8: CLICK THE FLAG ICON ────────────────────────────────────
TestObject flagIcon = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	"div.ol-overview-overlay.icon-flag-green"
)
WebUI.waitForElementClickable(flagIcon, 10)
WebUI.click(flagIcon)
WebUI.delay(2)

// ─── STEP 9: SCREENSHOT + BASE64 ─────────────────────────────────────
WebDriver driver = DriverFactory.getWebDriver()
String base64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64)
println "🖼️ Base64 screenshot:\n${base64}"
WebUI.comment("✅ Highlight taken and printed as Base64.")


