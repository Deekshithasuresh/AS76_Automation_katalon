import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// 1) LOGIN
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

// grab Selenium driver
WebDriver driver = DriverFactory.getWebDriver()

// 2) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 3) (Re-)ASSIGN TO “admin” IF NEEDED
TestObject assignedInputTO = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS, "//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInputTO, 5)
String currentAssignee = WebUI.getAttribute(assignedInputTO, 'value').trim()
if (!currentAssignee.equalsIgnoreCase('admin')) {
    TestObject dropdownTO = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
    )
    TestObject adminOptionTO = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
    )
    TestObject reassignBtnTO = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS,
        "//button[normalize-space()='Re-assign']"
    )
    WebUI.click(dropdownTO)
    WebUI.waitForElementClickable(adminOptionTO, 5)
    WebUI.click(adminOptionTO)
    WebUI.waitForElementClickable(reassignBtnTO, 5)
    WebUI.click(reassignBtnTO)
    WebUI.delay(2)
}

// 4) CLICK “Approve report” → FIRST CONFIRM
TestObject btnApprove    = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[.//span[normalize-space()='Approve report']]")
TestObject popupConfirm1 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//span[contains(text(),'Are you sure you want to approve')]")
// generic “Confirm” button in modal-actions
TestObject btnConfirm    = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//div[contains(@class,'modal-actions')]//button[normalize-space()='Confirm']")

WebUI.waitForElementClickable(btnApprove, 10)
WebUI.click(btnApprove)
WebUI.waitForElementVisible(popupConfirm1, 5)
WebUI.waitForElementClickable(btnConfirm, 5)
WebUI.click(btnConfirm)

// 5) SECOND WARNING (“without supporting images”) → SECOND CONFIRM
TestObject popupWarn2 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//span[contains(text(),'without supporting images')]")

// give the warning dialog a moment
WebUI.waitForElementVisible(popupWarn2, 5)
// reuse the same btnConfirm locator
for (int i = 0; i < 3; i++) {
    if (WebUI.waitForElementClickable(btnConfirm, 3, FailureHandling.OPTIONAL)) {
        WebUI.click(btnConfirm)
        break
    }
}
WebUI.delay(2)

// 6) OPEN HISTORY & VERIFY
TestObject kebabBtn   = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[.//img[contains(@src,'kebab_menu.svg')]]")
TestObject historyOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//li[.//span[normalize-space()='History']]")

WebUI.click(kebabBtn)
WebUI.click(historyOpt)
WebUI.waitForElementVisible(
    new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
    10
)

List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
assert entries.size() >= 1 : "No history entries found!"

String latest = entries.get(0).getText().trim()
println "➤ Latest history entry: ${latest}"
assert latest.toLowerCase().contains("approved the report") :
    "Expected approval entry, but got: ${latest}"

// screenshot for proof
WebUI.takeScreenshot("History_Approve.png")
WebUI.comment("✅ HIS-016: Approval & history verified!")
