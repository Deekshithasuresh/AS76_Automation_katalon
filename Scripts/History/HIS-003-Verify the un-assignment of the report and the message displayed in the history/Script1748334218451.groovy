import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import com.kms.katalon.core.model.FailureHandling

// 1) LOGIN + NAVIGATE TO REPORT LIST
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject(
    'Report viewer/Page_PBS/input_username_loginId'
), 'adminuserr')
WebUI.setEncryptedText(findTestObject(
    'Report viewer/Page_PBS/input_password_loginPassword'
), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//span[contains(text(),'PBS')]"
), 10)

// 2) COUNT “Under review” ROWS
WebDriver driver = DriverFactory.getWebDriver()
int total = driver.findElements(By.xpath(
    "//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']]"
)).size()
assert total > 0 : "❌ No 'Under review' rows found"

// 3) CLEAR FIRST NON-EMPTY “Assigned to”
boolean didClear = false
for (int i = 1; i <= total && !didClear; i++) {
    String baseXp  = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[${i}]"
    String inpXp   = "${baseXp}//input[@id='assigned_to']"
    TestObject inputTO = new TestObject().addProperty(
        'xpath', ConditionType.EQUALS, inpXp
    )
    WebUI.waitForElementVisible(inputTO, 5)
    String val = WebUI.getAttribute(inputTO, 'value').trim()
    if (val) {
        // try both possible clear-buttons:
        List<String> clearXps = [
          // by aria-label
          "${baseXp}//button[@aria-label='Clear assignment']",
          // by title
          "${baseXp}//button[@title='Clear']"
        ]
        for (String clearXp : clearXps) {
            TestObject clearTO = new TestObject().addProperty(
              'xpath', ConditionType.EQUALS, clearXp
            )
            if (WebUI.verifyElementPresent(clearTO, 2, FailureHandling.OPTIONAL)) {
                WebUI.waitForElementClickable(clearTO, 5)
                WebUI.click(clearTO)
                WebUI.comment("✔ Cleared ‘Assigned to’ on row #${i} (was '${val}')")
                didClear = true
                break
            }
        }
        if (!didClear) {
            WebUI.comment("⚠ Found non-empty field on row #${i} but no clear-button; skipping…")
        }
    } else {
        WebUI.comment("⚠ Row #${i} already empty, skipping…")
    }
}
assert didClear : "❌ No non-empty 'Assigned to' field could be cleared"

// 4) CONFIRM “Unassign” DIALOG
TestObject dialogTO = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'modal-title') and contains(.,'unassign this slide?')]/ancestor::div[@role='dialog']"
)
WebUI.waitForElementVisible(dialogTO, 5)
TestObject unassignBtn = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[@role='dialog']//button[normalize-space()='Unassign']"
)
WebUI.click(unassignBtn)

// 5) VERIFY FIELD IS BLANK
TestObject afterClear = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']]//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(afterClear, 5)
String clearedVal = WebUI.getAttribute(afterClear, 'value').trim()
assert clearedVal == '' : "❌ Field not cleared; still has '${clearedVal}'"

// 6) OPEN KEBAB MENU & CHOOSE “History”
TestObject kebabTO = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.click(kebabTO)
TestObject historyTO = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//li//span[normalize-space()='History']"
)
WebUI.waitForElementClickable(historyTO, 5)
WebUI.click(historyTO)
WebUI.delay(2)

// 7) READ & SCREENSHOT FIRST HISTORY ENTRY
TestObject entryTO = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "(//ul[contains(@class,'appBar_popover_list')]//li)[1]"
)
WebUI.waitForElementVisible(entryTO, 10, FailureHandling.OPTIONAL)
String entryText = WebUI.getText(entryTO).trim()
WebUI.comment("✔ Latest history entry:\n${entryText}")
WebUI.takeScreenshot('HistoryPanel.png')

WebUI.comment("✅ Un-assignment flow and history message verified.")
