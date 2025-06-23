import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import java.util.List

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

// grab the driver for later
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
	WebUI.comment("⚙️ Currently assigned to '${currentAssignee}', changing to admin…")
	TestObject dropdownTO = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
	)
	TestObject adminOptTO = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//li[@role='option' and normalize-space(text())='admin']"
	)
	TestObject reassignBtnTO = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[normalize-space()='Re-assign']"
	)

	WebUI.click(dropdownTO)
	WebUI.waitForElementClickable(adminOptTO, 5)
	WebUI.scrollToElement(adminOptTO, 5)
	WebUI.click(adminOptTO)
	WebUI.waitForElementClickable(reassignBtnTO, 5)
	WebUI.click(reassignBtnTO)
	WebUI.delay(2)
	WebUI.comment("✔ Re-assigned to admin.")
} else {
	WebUI.comment("ℹ️ Already assigned to admin; skipping re-assignment.")
}

// ────────────────────────────────────────────────────────────────────
// 5) CLICK “Approve report” & HANDLE POPUPS
// ────────────────────────────────────────────────────────────────────
TestObject btnApprove = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[contains(normalize-space(),'Approve report')]]"
)
WebUI.waitForElementClickable(btnApprove, 10)
WebUI.click(btnApprove)

// first “Are you sure you want to approve” dialog
TestObject popupConfirm = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'Are you sure you want to approve')]"
)
WebUI.waitForElementVisible(popupConfirm, 5)
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space()='Confirm']"))

// second warning about “without supporting images?”
TestObject popupWarn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'without supporting images')]"
)
WebUI.waitForElementVisible(popupWarn, 5)
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space()='Approve report']"))

// give it time to complete
WebUI.delay(120)

// ────────────────────────────────────────────────────────────────────
// 6) OPEN HISTORY & VERIFY LATEST ENTRY
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

TestObject historyOpt = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]"
)
WebUI.waitForElementClickable(historyOpt, 5)
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

// optional screenshot
WebUI.takeScreenshot("History_ApproveOnly.png")
