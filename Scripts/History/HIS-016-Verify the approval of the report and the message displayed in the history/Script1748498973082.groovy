import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// for List<>
import java.util.List


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

// 1) click the blue “Approve report” button in header
TestObject btnApproveHeader = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)
WebUI.waitForElementClickable(btnApproveHeader, 10)
WebUI.click(btnApproveHeader)

// 2) confirm the “Are you sure you want to approve?” popup
TestObject popupFirst = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'Are you sure you want to approve')]"
)
WebUI.waitForElementVisible(popupFirst, 5)
TestObject btnConfirmFirst = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space()='Confirm']"
)
WebUI.click(btnConfirmFirst)

// 3) handle the “Approve without supporting images?” warning
TestObject popupWarn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'without supporting images')]"
)
WebUI.waitForElementVisible(popupWarn, 5)
TestObject btnConfirmNoImgs = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space()='Approve report']"
)
WebUI.click(btnConfirmNoImgs)

// 4) wait for final processing
WebUI.delay(120)

// 5) Open kebab → History
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

// 6) grab & assert the latest entry
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)
List<WebElement> historyEntries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
assert historyEntries.size() >= 1 : "No history entries found!"

String first = historyEntries.get(0).getText().trim()
println "Latest history entry: ${first}"
assert first.toLowerCase().contains("approved the report") :
	   "Expected entry to mention approval, but was: ${first}"

// optional: screenshot
WebUI.takeScreenshot("History_ApproveOnly.png")