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
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin” (only if not already)
// ────────────────────────────────────────────────────────────────────
TestObject assignedInputTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
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

WebUI.waitForElementVisible(assignedInputTO, 5)
String currentAssignee = WebUI.getAttribute(assignedInputTO, 'value').trim()
if (!currentAssignee.equalsIgnoreCase('admin')) {
	WebUI.comment("⚙️ Currently assigned to '${currentAssignee}', re-assigning to admin…")
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
// 5) WAIT FOR “Approve report” BUTTON, THEN CLICK & CONFIRM
// ────────────────────────────────────────────────────────────────────
TestObject btnApprove = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[normalize-space()='Approve report']]"
)
TestObject popupConfirm = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(.,'Are you sure you want to approve')]"
)
TestObject btnConfirm = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space(text())='Confirm']"
)

WebUI.delay(2)
WebUI.waitForElementVisible(btnApprove, 10)
WebUI.comment("✔ 'Approve report' is now visible.")
WebUI.click(btnApprove)

WebUI.waitForElementVisible(popupConfirm, 5)
WebUI.click(btnConfirm)

// ────────────────────────────────────────────────────────────────────
// 6) ADD SUPPORTING IMAGES
// ────────────────────────────────────────────────────────────────────
TestObject btnAddImgs = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[text()='Add supporting images']]"
)
WebUI.waitForElementClickable(btnAddImgs, 10)
WebUI.click(btnAddImgs)

// ────────────────────────────────────────────────────────────────────
// 7) MODIFY PATCHES
// ────────────────────────────────────────────────────────────────────
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> modifyBtns = driver.findElements(By.xpath("//button[.//span[text()='Modify']]"))
int desiredTotal = 4

modifyLoop:
for (int i = 0; i < modifyBtns.size(); i++) {
	modifyBtns.get(i).click()
	WebUI.delay(1)
	WebUI.waitForElementVisible(
		new TestObject().addProperty('xpath', ConditionType.EQUALS, "//ul[contains(@class,'patches-image-list')]"),
		5
	)
	List<WebElement> allPatches = driver.findElements(
		By.cssSelector("ul.patches-image-list li.MuiImageListItem-root")
	)
	if (allPatches.size() < desiredTotal) {
		WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//button[contains(@class,'cancel-btn') and normalize-space()='Cancel']"))
		WebUI.delay(1)
		continue
	}
	// click first `desiredTotal` patches
	for (int j = 0; j < desiredTotal; j++) {
		allPatches.get(j).click()
	}
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//button[contains(@class,'save-btn') and normalize-space()='Add to report']"))
	WebUI.delay(1)
	break modifyLoop
}

// ────────────────────────────────────────────────────────────────────
// 8) CLICK BACK → CONFIRM DISCARD
// ────────────────────────────────────────────────────────────────────
TestObject backIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@class='action-icon' and contains(@src,'back-btn-icon.svg')]"
)
WebUI.click(backIcon)

TestObject discardPopup = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(.,'All changes made will be lost')]"
)
WebUI.waitForElementVisible(discardPopup, 5)

TestObject btnBackToReport = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space(text())='Back to report']"
)
WebUI.click(btnBackToReport)

// ────────────────────────────────────────────────────────────────────
// 9) OPEN HISTORY
// ────────────────────────────────────────────────────────────────────
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
TestObject hist = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]"
)
WebUI.waitForElementClickable(kebab, 5)
WebUI.click(kebab)
WebUI.waitForElementClickable(hist, 5)
WebUI.click(hist)

// ────────────────────────────────────────────────────────────────────
// 10) VERIFY LATEST HISTORY ENTRY & SCREENSHOT
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)

WebElement latest = driver.findElement(By.cssSelector("li.css-1ecsk3j"))
String title = latest.findElement(By.cssSelector("h4.event-title")).getText().trim()
String desc  = latest.findElement(By.cssSelector("div.event-description")).getText().trim()

println "History Title: ${title}"
println "History Desc : ${desc}"

// make sure the description matches your expected text
assert desc.toLowerCase().contains("cancelled the final report selections")

WebUI.takeScreenshot("History_Final_Cancel.png")
