import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// grab the WebDriver
WebDriver driver = DriverFactory.getWebDriver()

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin” IF NOT ALREADY
// ────────────────────────────────────────────────────────────────────
TestObject assignedInputTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInputTO, 5)
String currentAssignee = WebUI.getAttribute(assignedInputTO, 'value').trim()
if (!currentAssignee.equalsIgnoreCase('admin')) {
	WebUI.comment("⚙️ Currently assigned to '${currentAssignee}', re-assigning to admin…")
	TestObject dropdownTO = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
	)
	TestObject adminOptionTO = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//li[contains(@role,'option') and normalize-space(text())='admin']"
	)
	TestObject reassignBtnTO = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[normalize-space()='Re-assign']"
	)
	WebUI.click(dropdownTO)
	WebUI.waitForElementClickable(adminOptionTO, 5)
	WebUI.scrollToElement(adminOptionTO, 5)
	WebUI.click(adminOptionTO)
	WebUI.waitForElementClickable(reassignBtnTO, 5)
	WebUI.click(reassignBtnTO)
	WebUI.delay(2)
	WebUI.comment("✔ Re-assigned to admin.")
} else {
	WebUI.comment("ℹ️ Already assigned to admin; skipping reassignment.")
}

// ────────────────────────────────────────────────────────────────────
// 5) OPEN KEBAB MENU & SELECT “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)
WebUI.delay(1)

TestObject historyOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//ul[@role='menu']//li[@role='menuitem']//span[normalize-space()='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyOption, 10)
WebUI.click(historyOption)

// ────────────────────────────────────────────────────────────────────
// 6) VERIFY NO EDITABLE FIELDS IN HISTORY PANEL
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)

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
