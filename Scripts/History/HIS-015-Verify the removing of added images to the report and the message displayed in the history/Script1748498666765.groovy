import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

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

WebDriver driver = DriverFactory.getWebDriver()

// 2) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 3) ASSIGN TO “admin” IF NEEDED (omitted here for brevity – assume it’s the same as before)

// 4) CLICK “Approve report” → CONFIRM first time
TestObject btnApprove  = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[.//span[normalize-space()='Approve report']]")
TestObject popupTitle1 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//span[contains(text(),'Are you sure you want to approve')]")
// **NEW**: generic confirm locator inside the modal footer
TestObject btnConfirm1 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//div[contains(@class,'modal-actions')]//button[normalize-space()='Confirm']")

WebUI.waitForElementClickable(btnApprove, 10)
WebUI.click(btnApprove)
WebUI.waitForElementVisible(popupTitle1, 5)
WebUI.waitForElementClickable(btnConfirm1, 5)
WebUI.click(btnConfirm1)

// 5) ADD SUPPORTING IMAGES
TestObject btnAddImgs = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[.//span[text()='Add supporting images']]")
WebUI.waitForElementClickable(btnAddImgs, 10)
WebUI.click(btnAddImgs)

// 6) REMOVE FIRST CHECKED PATCH
List<WebElement> checkedBoxes = driver.findElements(
    By.cssSelector("div.cell-selection-item input[type='checkbox']:checked")
)
assert checkedBoxes.size() > 0 : "❗ No checked patches found to remove"
WebElement firstBox = checkedBoxes.get(0)
String cellType = firstBox.getAttribute("name")
WebUI.comment("⏪ Removing patches of cell type: ${cellType}")
firstBox.findElement(By.xpath("..")).click()
WebUI.delay(1)

// 7) CLICK “Approve report” → CONFIRM second time
// reuse btnApprove and popupTitle1
// for the second confirm dialog the title is the same, so we can reuse popupTitle1
// but the confirm button can appear a bit later, so wrap in retry:
for (int i = 0; i < 3; i++) {
    if (WebUI.waitForElementClickable(btnApprove, 5, FailureHandling.OPTIONAL)) {
        WebUI.click(btnApprove)
        break
    }
}
WebUI.waitForElementVisible(popupTitle1, 5)
// **NEW**: the second confirm button text is “Confirm” as well
for (int i = 0; i < 3; i++) {
    if (WebUI.waitForElementClickable(btnConfirm1, 3, FailureHandling.OPTIONAL)) {
        WebUI.click(btnConfirm1)
        break
    }
}
WebUI.delay(2)

// 8) OPEN HISTORY & VERIFY
TestObject btnKebab     = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[.//img[contains(@src,'kebab_menu.svg')]]")
TestObject menuHistory = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//li[.//span[normalize-space()='History']]")
WebUI.click(btnKebab)
WebUI.click(menuHistory)
WebUI.waitForElementVisible(
    new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
    10
)

List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
assert entries.size() >= 2 : "Expected ≥2 history entries, found ${entries.size()}"

String removalEntry  = entries[0].getText().trim()
String approvalEntry = entries[1].getText().trim()

println "➤ History #1: ${removalEntry}"
println "➤ History #2: ${approvalEntry}"

assert removalEntry.contains("removed the patches of ${cellType}") :
       "Removal entry didn’t mention ‘removed the patches of ${cellType}’"
assert approvalEntry.toLowerCase().contains("approved the report") :
       "Approval entry didn’t mention approval"

WebUI.takeScreenshot("History_RemovalAndApproval.png")
WebUI.comment("✅ HIS-015: Removal & history verified!")
