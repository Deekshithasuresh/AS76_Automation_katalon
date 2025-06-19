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

// 1) Click the “RBC” tab
// ──────────────────────────────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[normalize-space()='RBC']]"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.comment("🔖 Switched to RBC tab")

// ──────────────────────────────────────────────────────────────────────
// 2) Toggle Microscopic view
// ──────────────────────────────────────────────────────────────────────
TestObject microView = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
)
WebUI.waitForElementClickable(microView, 10)
WebUI.click(microView)
WebUI.comment("🔬 Microscopic view ON")
WebUI.delay(5)  // give OL time to render

// ──────────────────────────────────────────────────────────────────────
// 3) Zoom in twice (60 s pause each time)
// ──────────────────────────────────────────────────────────────────────
TestObject zoomIn = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	"button.ol-zoom-in"
)
for (int i = 1; i <= 2; i++) {
	WebUI.waitForElementClickable(zoomIn, 10)
	WebUI.click(zoomIn)
	WebUI.comment("🔍 Zoom-in #${i}")
	WebUI.delay(60)
}

// ──────────────────────────────────────────────────────────────────────
// 4) Open the Overview pane (the “›” button)
// ──────────────────────────────────────────────────────────────────────
TestObject overviewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@title='Overview']"
)
WebUI.scrollToElement(overviewBtn, 5)
WebUI.click(overviewBtn)
WebUI.comment("📐 Overview panel opened")
WebUI.delay(2)  

// ──────────────────────────────────────────────────────────────────────
// 5) Click the green flag in the overview
// ──────────────────────────────────────────────────────────────────────
TestObject flagIcon = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	"div.ol-overview-overlay.icon-flag-green"
)
WebUI.waitForElementClickable(flagIcon, 10)
WebUI.click(flagIcon)
WebUI.comment("🚩 Flag clicked")
WebUI.delay(2)

// ──────────────────────────────────────────────────────────────────────
// 6) Take a screenshot + print Base 64
// ──────────────────────────────────────────────────────────────────────
WebDriver driver = DriverFactory.getWebDriver()
// full-page PNG
WebUI.takeScreenshot("Screenshots/overview_flag.png")
// Base64 string
String b64 = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64)
println "\n🖼 Screenshot Base64:\n${b64}\n"