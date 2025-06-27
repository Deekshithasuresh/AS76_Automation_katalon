import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

// 1) LOGIN & NAVIGATE
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

// grab driver
WebDriver driver = DriverFactory.getWebDriver()

// 2) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 3) ASSIGN TO ADMIN IF NEEDED (same as before) …
TestObject assignedInput  = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//input[@id='assigned_to']")
String currentAssignee    = WebUI.getAttribute(assignedInput, 'value').trim()
if (!currentAssignee.equalsIgnoreCase('admin')) {
    TestObject dropdownBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
        "//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button")
    WebUI.click(dropdownBtn)
    TestObject adminOption = new TestObject().addProperty('xpath', ConditionType.EQUALS,
        "//li[@role='option' and normalize-space(text())='admin']")
    WebUI.waitForElementClickable(adminOption, 5)
    WebUI.click(adminOption)
    TestObject reassignBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
        "//button[normalize-space(text())='Re-assign']")
    WebUI.waitForElementClickable(reassignBtn, 5)
    WebUI.click(reassignBtn)
    WebUI.delay(2)
    WebUI.comment("✔ Re-assigned to admin")
} else {
    WebUI.comment("ℹ️ Already assigned to admin")
}

// 4) OPEN HISTORY
TestObject kebabBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[.//img[contains(@src,'kebab_menu')]]")
WebUI.click(kebabBtn)
TestObject historyOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li")
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)
WebUI.delay(2)

// 5) PICK THE “Assignment” ENTRY
String assignLiXpath = "(//h4[contains(@class,'event-title') and contains(normalize-space(text()),'Assignment')]/ancestor::li)[1]"
TestObject assignEntry = new TestObject().addProperty('xpath', ConditionType.EQUALS, assignLiXpath)
WebUI.waitForElementVisible(assignEntry, 10)

// 6) VERIFY TITLE & DESCRIPTION
WebElement entry = driver.findElement(By.xpath(assignLiXpath))
String title       = entry.findElement(By.cssSelector("h4.event-title")).getText().trim()
String description = entry.findElement(By.cssSelector("div.event-description")).getText().trim()

WebUI.comment("History title: ${title}")
WebUI.comment("History desc : ${description}")

WebUI.verifyMatch(title,       ".*Assignment.*",    false, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyMatch(description, ".*assigned to admin.*", false, FailureHandling.STOP_ON_FAILURE)

WebUI.comment("✅ First-time assignment history verified.")
