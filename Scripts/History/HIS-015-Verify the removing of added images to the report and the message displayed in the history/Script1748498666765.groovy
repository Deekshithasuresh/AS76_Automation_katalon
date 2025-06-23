
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

// grab driver once
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
// 4) ASSIGN TO “admin” IF NEEDED
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
		"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
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
// 5) CLICK “Approve report” → CONFIRM
// ────────────────────────────────────────────────────────────────────
TestObject btnApprove = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[normalize-space()='Approve report']]"
)
TestObject popupConfirm = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'Are you sure you want to approve')]"
)
TestObject btnConfirm = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space(text())='Confirm']"
)

WebUI.delay(2)
WebUI.waitForElementVisible(btnApprove, 10)
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
// 7) REMOVE FIRST CHECKED PATCH
// ────────────────────────────────────────────────────────────────────
List<WebElement> checkedBoxes = driver.findElements(
	By.cssSelector("div.cell-selection-item input[type='checkbox']:checked")
)
if (checkedBoxes.isEmpty()) {
	WebUI.comment("❗ No checked patches found to remove")
} else {
	WebElement firstBox = checkedBoxes.get(0)
	String cellType = firstBox.getAttribute("name")
	WebUI.comment("⏪ Removing patches of cell type: ${cellType}")
	firstBox.findElement(By.xpath("..")).click()
	WebUI.delay(1)

	// ────────────────────────────────────────────────────────────────────
	// 8) APPROVE AGAIN → CONFIRM
	// ────────────────────────────────────────────────────────────────────
	WebUI.waitForElementClickable(btnApprove, 10)
	WebUI.click(btnApprove)
	WebUI.waitForElementVisible(popupConfirm, 5)
	WebUI.click(btnConfirm)
	WebUI.delay(2)

	// ────────────────────────────────────────────────────────────────────
	// 9) OPEN HISTORY & VERIFY
	// ────────────────────────────────────────────────────────────────────
	TestObject btnKebab = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[.//img[contains(@src,'kebab_menu.svg')]]"
	)
	WebUI.click(btnKebab)
	TestObject menuHistory = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//li[.//span[normalize-space()='History']]"
	)
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
}

