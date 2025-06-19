import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

/ ────────────────────────────────────────────────────────────────────
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
// 1) Click the kebab (three-dots) icon
TestObject kebabBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementVisible(kebabBtn, 10)
WebUI.click(kebabBtn)

// 2) Give it a moment to render the menu
WebUI.delay(1)

// 3) Click “History” by looking for the role=menuitem
TestObject historyOption = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//ul[@role='menu']//li[@role='menuitem']//span[normalize-space()='History']"
)
WebUI.waitForElementClickable(historyOption, 10)
WebUI.click(historyOption)


// 3) WAIT FOR HISTORY ENTRIES TO APPEAR
TestObject firstEntry = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	"li.css-1ecsk3j"
)
WebUI.waitForElementVisible(firstEntry, 10)

// 4) ASSERT NO EDITABLE FIELDS IN HISTORY PANEL
WebDriver driver = DriverFactory.getWebDriver()

// look for any <textarea> inside the history container
List<WebElement> textareas = driver.findElements(
	By.cssSelector("div.review-selected-category textarea")
)
assert textareas.isEmpty(): "❌ Found unexpected <textarea> in history!"

// look for any <input> inside the history container
List<WebElement> inputs = driver.findElements(
	By.cssSelector("div.review-selected-category input")
)
assert inputs.isEmpty(): "❌ Found unexpected <input> in history!"

WebUI.comment("✅ History entries are read-only — no editable fields found.")

