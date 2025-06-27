import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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

// wait for the main PBS list to appear
WebUI.waitForElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
    10
)

// grab Selenium driver
WebDriver driver = DriverFactory.getWebDriver()

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 3) ENSURE IT’S ASSIGNED TO ADMIN
// ────────────────────────────────────────────────────────────────────
TestObject assignedInput = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInput, 5)
String assignee = WebUI.getAttribute(assignedInput, 'value').trim()
if (!assignee.equalsIgnoreCase('admin')) {
    TestObject dropdown = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
    )
    TestObject adminOpt = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
    )
    TestObject reassignBtn = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//button[normalize-space()='Re-assign']"
    )
    WebUI.click(dropdown)
    WebUI.click(adminOpt)
    WebUI.click(reassignBtn)
    WebUI.delay(2)
}

// ────────────────────────────────────────────────────────────────────
// 4) OPEN KEBAB MENU & SELECT “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

// **Fixed locator** for the History item in the popover:
TestObject historyItem = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyItem, 5)
WebUI.click(historyItem)

// ────────────────────────────────────────────────────────────────────
// 5) VERIFY NO EDITABLE FIELDS IN HISTORY PANEL
// ────────────────────────────────────────────────────────────────────

// wait for at least one history entry
WebUI.waitForElementVisible(
    new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
    10
)

// assert there are no <textarea> or <input> inside the history container
List<WebElement> textAreas = driver.findElements(
    By.cssSelector("div.review-selected-category textarea")
)
assert textAreas.isEmpty() : "❌ Unexpected <textarea> found in the history panel!"

List<WebElement> inputs = driver.findElements(
    By.cssSelector("div.review-selected-category input")
)
assert inputs.isEmpty() : "❌ Unexpected <input> found in the history panel!"

WebUI.comment("✅ PASS: History panel is read-only—no editable controls present.")
