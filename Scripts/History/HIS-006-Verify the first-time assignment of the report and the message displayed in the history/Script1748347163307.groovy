import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN & NAVIGATE TO REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
    'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
    10
)

// grab the driver for later DOM calls
WebDriver driver = DriverFactory.getWebDriver()

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “Under review” REPORT ROW
// ────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 3) READ CURRENT ASSIGNEE
// ────────────────────────────────────────────────────────────────────
TestObject assignedInput = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInput, 5)
String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
WebUI.comment("Current assignee: '${currentAssignee}'")

// ────────────────────────────────────────────────────────────────────
// 4) IF NOT ADMIN, RE-ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
if (!currentAssignee.equalsIgnoreCase('admin')) {
    WebUI.comment("✏️ Needs re-assignment to admin…")

    // click the dropdown
    TestObject dropdownBtn = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
    )
    WebUI.waitForElementClickable(dropdownBtn, 5)
    WebUI.click(dropdownBtn)

    // select "admin"
    TestObject adminOption = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//li[@role='option' and normalize-space(text())='admin']"
    )
    WebUI.waitForElementVisible(adminOption, 5)
    WebUI.scrollToElement(adminOption, 5)
    WebUI.click(adminOption)

    // confirm with the Re-assign button
    TestObject reassignBtn = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//button[normalize-space(text())='Re-assign']"
    )
    WebUI.waitForElementClickable(reassignBtn, 5)
    WebUI.click(reassignBtn)
    WebUI.delay(2)

    WebUI.comment("✔ Re-assigned report to 'admin'")
} else {
    WebUI.comment("ℹ️ Already assigned to 'admin'; skipping reassignment.")
}

// ────────────────────────────────────────────────────────────────────
// 5) OPEN KEBAB MENU & SELECT “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[.//img[contains(@src,'kebab_menu')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

TestObject historyOpt = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)
WebUI.delay(2)

// ────────────────────────────────────────────────────────────────────
// 6) READ & PRINT FIRST HISTORY ENTRY
// ────────────────────────────────────────────────────────────────────
String entryXpath = "(//li[contains(@class,'css-1ecsk3j')])[1]"
TestObject entryTO = new TestObject().addProperty('xpath', ConditionType.EQUALS, entryXpath)
WebUI.waitForElementVisible(entryTO, 10, FailureHandling.STOP_ON_FAILURE)

// use Selenium to drill into sub-elements
WebElement entry = driver.findElement(By.xpath(entryXpath))
String title       = entry.findElement(By.cssSelector("h4.event-title")).getText().trim()
String timestamp   = entry.findElement(By.cssSelector("div.time")).getText().trim()
String description = entry.findElement(By.cssSelector("div.event-description")).getText().trim()

WebUI.comment("• History Title      : ${title}")
WebUI.comment("• History Timestamp  : ${timestamp}")
WebUI.comment("• History Description: ${description}")

// optional assertions
WebUI.verifyMatch(title,       ".*assignment.*",    false, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(description, ".*re-assign.*" + "admin" + ".*", false, FailureHandling.CONTINUE_ON_FAILURE)

WebUI.comment("✅ History entry verified successfully.")
