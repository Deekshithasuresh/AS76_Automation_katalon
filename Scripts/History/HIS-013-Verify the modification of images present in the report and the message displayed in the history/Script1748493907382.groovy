import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
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
// 4) CONDITIONAL REASSIGN TO “admin”
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
// 4b) DEFINE APPROVE BUTTONS
// ────────────────────────────────────────────────────────────────────
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)
TestObject confirmBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space(text())='Confirm']"
)
TestObject finalApproveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space(text())='Approve report']"
)

// ────────────────────────────────────────────────────────────────────
// 5) WAIT FOR “Approve report”, CLICK & CONFIRM
// ────────────────────────────────────────────────────────────────────
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment("'Approve report' is visible, clicking…")
WebUI.click(approveBtn)

WebUI.waitForElementVisible(confirmBtn, 5)
WebUI.click(confirmBtn)

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
List<WebElement> modifyBtns = driver.findElements(
	By.xpath("//button[.//span[text()='Modify']]")
)

int desiredTotal = 4
modifyLoop:
for (int i = 0; i < modifyBtns.size(); i++) {
	modifyBtns.get(i).click()
	WebUI.delay(1)

	WebUI.waitForElementVisible(
		new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//ul[contains(@class,'patches-image-list')]"),
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

	// click first 4 patches
	for (int j = 0; j < desiredTotal; j++) {
		allPatches.get(j).click()
	}
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//button[contains(@class,'save-btn') and normalize-space()='Add to report']"))
	WebUI.delay(1)
	break modifyLoop
}

// ────────────────────────────────────────────────────────────────────
// 8) FINAL APPROVE WITH IMAGES
// ────────────────────────────────────────────────────────────────────
WebUI.click(approveBtn)
WebUI.waitForElementVisible(finalApproveBtn, 5)
WebUI.click(finalApproveBtn)
WebUI.delay(120)

// ────────────────────────────────────────────────────────────────────
// 9) OPEN HISTORY & PRINT
// ────────────────────────────────────────────────────────────────────
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
TestObject historyOpt = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]"
)

WebUI.click(kebab)
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)

WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
for (int k = 0; k < Math.min(2, entries.size()); k++) {
	WebUI.comment(entries.get(k).getText().trim())
}

WebUI.takeScreenshot("HistoryPage.png")
