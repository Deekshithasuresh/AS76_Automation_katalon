
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 3: Pick & assign a report ----------
TestObject statusToBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']"
)
TestObject statusUnderReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[normalize-space(text())='admin']"
)
TestObject reassignButton = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Re-assign']"
)
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)

if (WebUI.waitForElementPresent(statusToBeReviewed, 3)) {
	WebUI.scrollToElement(statusToBeReviewed, 5)
	WebUI.click(statusToBeReviewed)
	WebUI.click(assignedDropdown)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.click(adminOption)

	WebUI.comment("Assigned a ‘To be reviewed’ report to admin.")
} else if (WebUI.waitForElementPresent(statusUnderReview, 3)) {
	WebUI.scrollToElement(statusUnderReview, 5)
	WebUI.click(statusUnderReview)

	// check current assignee
	String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
	if (currentAssignee != 'admin') {
		// reassign only if not already admin
		WebUI.click(assignedDropdown)
		WebUI.waitForElementClickable(adminOption, 5)
		WebUI.click(adminOption)
		WebUI.waitForElementClickable(reassignButton, 5)
		WebUI.click(reassignButton)
		WebUI.comment("Re-assigned an ‘Under review’ report to admin.")
	} else {
		WebUI.comment("‘Under review’ report already assigned to admin—no reassignment needed.")
	}
} else {
	WebUI.comment("❌ No report in ‘To be reviewed’ or ‘Under review’ status.")
	WebUI.takeScreenshot()
	WebUI.closeBrowser()
	return
}

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment(" 'Approve report' is now visible.")

// ────────────────────────────────────────────────────────────────────
// 3) APPROVE REPORT BUTTON
// ────────────────────────────────────────────────────────────────────
TestObject btnApprove = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//span[contains(normalize-space(),'Approve report')]]")
WebUI.waitForElementClickable(btnApprove, 10)
WebUI.click(btnApprove)

// confirmation popup
TestObject popupConfirm = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[text()='Are you sure you want to approve']")
WebUI.waitForElementVisible(popupConfirm, 5)
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space(.)='Confirm']"))

// ────────────────────────────────────────────────────────────────────
// 4) ADD SUPPORTING IMAGES
// ────────────────────────────────────────────────────────────────────
TestObject btnAddImgs = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//span[text()='Add supporting images']]")
WebUI.waitForElementClickable(btnAddImgs, 10)
WebUI.click(btnAddImgs)


// 1) Find the first checked checkbox and uncheck it
List<WebElement> checkedBoxes = driver.findElements(
	By.cssSelector("div.cell-selection-item span.MuiCheckbox-root.Mui-checked input")
)
if (checkedBoxes.isEmpty()) {
	WebUI.comment("❗ No checked cells found to remove")
	return
}
WebElement box = checkedBoxes[0]
String cellType = box.getAttribute("name")
WebUI.comment("⏪ Removing patches for cell type: ${cellType}")
// click the parent <span> to toggle off
WebElement span = box.findElement(By.xpath(".."))
span.click()
WebUI.delay(1)

// ——— B) Approve the report ———
// click the blue “Approve report” button in the header
TestObject btnApprove = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)
WebUI.waitForElementClickable(btnApprove, 10)
WebUI.click(btnApprove)

// confirm the “Are you sure?” modal
TestObject confirmTitle = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'Are you sure you want to approve')]"
)
WebUI.waitForElementVisible(confirmTitle, 5)
TestObject btnConfirm = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space()='Approve report']"
)
WebUI.click(btnConfirm)
WebUI.delay(2)

// ——— C) Open History & verify entries ———
// open the kebab (⋮) menu
TestObject btnKebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.click(btnKebab)
// click “History”
TestObject menuHistory = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]"
)
WebUI.click(menuHistory)

// wait for the history items to show up
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)

// pull out the first two
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
assert entries.size() >= 2 : "Expected at least 2 history entries, found ${entries.size()}"

// print them
String removalEntry = entries[0].getText().trim()
String approvalEntry = entries[1].getText().trim()
println "➤ History #1: ${removalEntry}"
println "➤ History #2: ${approvalEntry}"

// basic sanity checks
assert removalEntry.contains("removed the patches of ${cellType}") :
	   "Removal entry didn’t mention ‘removed the patches of ${cellType}’"
assert approvalEntry.toLowerCase().contains("approved the report") :
	   "Second entry didn’t mention approval"

// snapshot
WebUI.takeScreenshot("History_RemovalAndApproval.png")