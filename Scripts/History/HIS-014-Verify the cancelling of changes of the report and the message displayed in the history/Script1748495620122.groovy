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
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// grab driver
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
// 3) ASSIGN TO “admin” IF NOT ALREADY
// ────────────────────────────────────────────────────────────────────
TestObject assignedInputTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInputTO, 5)
String currentAssignee = WebUI.getAttribute(assignedInputTO, 'value').trim()

if (!currentAssignee.equalsIgnoreCase('admin')) {
	WebUI.comment("⚙️ Re-assigning from '${currentAssignee}' to 'admin'…")

	// open dropdown
	TestObject dropdownTO = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
	)
	WebUI.click(dropdownTO)

	// retry-click the admin option to avoid stale-element
	def adminOptionXpath = "//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
	TestObject adminOptionTO = new TestObject().addProperty('xpath', ConditionType.EQUALS, adminOptionXpath)
	for (int i = 0; i < 3; i++) {
		if (WebUI.waitForElementClickable(adminOptionTO, 3, FailureHandling.OPTIONAL)) {
			WebUI.click(adminOptionTO)
			break
		}
		// if stale or not clickable, re-create the TestObject and retry
		adminOptionTO = new TestObject().addProperty('xpath', ConditionType.EQUALS, adminOptionXpath)
	}

	// confirm re-assign
	TestObject reassignBtnTO = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[normalize-space()='Re-assign']"
	)
	WebUI.waitForElementClickable(reassignBtnTO, 5)
	WebUI.click(reassignBtnTO)
	WebUI.delay(2)
	WebUI.comment("✔ Re-assigned to admin.")

} else {
	WebUI.comment("ℹ️ Already assigned to admin; skipping.")
}

// ────────────────────────────────────────────────────────────────────
// 4) CLICK “Approve report” & CONFIRM
// ────────────────────────────────────────────────────────────────────
TestObject btnApprove = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[normalize-space()='Approve report']]"
)
WebUI.waitForElementClickable(btnApprove, 10)
WebUI.click(btnApprove)

TestObject popupConfirm = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(.,'Are you sure you want to approve')]"
)
WebUI.waitForElementVisible(popupConfirm, 5)
TestObject btnConfirm = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space(text())='Confirm']"
)
WebUI.click(btnConfirm)

// ────────────────────────────────────────────────────────────────────
// 5) ADD SUPPORTING IMAGES (click “Add supporting images”)
// ────────────────────────────────────────────────────────────────────
TestObject btnAddImgs = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[text()='Add supporting images']]"
)
WebUI.waitForElementClickable(btnAddImgs, 10)
WebUI.click(btnAddImgs)

// ────────────────────────────────────────────────────────────────────
// 6) MODIFY PATCHES UNTIL AT LEAST 4 AVAILABLE, THEN SAVE
// ────────────────────────────────────────────────────────────────────
List<WebElement> modifyBtns = driver.findElements(
	By.xpath("//button[.//span[text()='Modify']]")
)
int desiredTotal = 4

modifyLoop:
for (int i = 0; i < modifyBtns.size(); i++) {
	modifyBtns[i].click()
	WebUI.delay(1)

	// wait for patch thumbnails
	TestObject thumbList = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//ul[contains(@class,'patches-image-list')]"
	)
	WebUI.waitForElementVisible(thumbList, 5)

	List<WebElement> allPatches = driver.findElements(
		By.cssSelector("ul.patches-image-list li.MuiImageListItem-root")
	)

	if (allPatches.size() < desiredTotal) {
		// cancel and try next
		WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//button[contains(@class,'cancel-btn') and normalize-space()='Cancel']"
		))
		WebUI.delay(1)
		continue
	}

	// select first 4 patches
	for (int j = 0; j < desiredTotal; j++) {
		allPatches[j].click()
	}
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//button[contains(@class,'save-btn') and normalize-space()='Add to report']"
	))
	WebUI.delay(1)
	break modifyLoop
}

// ────────────────────────────────────────────────────────────────────
// 7) CLICK BACK & CONFIRM DISCARD
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@class='action-icon' and contains(@src,'back-btn-icon.svg')]"
))
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
// 8) OPEN HISTORY
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
))
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]"
))
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 9) VERIFY LATEST HISTORY ENTRY
// ────────────────────────────────────────────────────────────────────
WebElement latest = driver.findElement(By.cssSelector("li.css-1ecsk3j"))
String title = latest.findElement(By.cssSelector("h4.event-title")).getText().trim()
String desc  = latest.findElement(By.cssSelector("div.event-description")).getText().trim()

println "History Title: ${title}"
println "History Desc : ${desc}"
assert desc.toLowerCase().contains("cancelled the final report selections")

WebUI.takeScreenshot("History_Final_Cancel.png")

WebUI.comment("✅ HIS-014: Cancel & history verified!")
