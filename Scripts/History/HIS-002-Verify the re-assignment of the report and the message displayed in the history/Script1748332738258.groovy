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

// grab native driver
WebDriver driver = DriverFactory.getWebDriver()

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
String underReviewXpath = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, underReviewXpath)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 3) CHECK & (IF NEEDED) RE-ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInput, 5)
String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
WebUI.comment("Current assignee: ${currentAssignee}")

if (!currentAssignee.equalsIgnoreCase('admin')) {
	// open the dropdown
	TestObject dropdownBtn = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
	)
	WebUI.click(dropdownBtn)

	// select “admin”
	TestObject adminOption = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
	)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.scrollToElement(adminOption, 5)
	WebUI.click(adminOption)

	// confirm re-assign
	TestObject reassignBtn = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[normalize-space(text())='Re-assign']"
	)
	WebUI.waitForElementClickable(reassignBtn, 5)
	WebUI.click(reassignBtn)
	WebUI.comment("✔ Report re-assigned to admin")
} else {
	WebUI.comment("✔ Report already assigned to admin")
}

// ────────────────────────────────────────────────────────────────────
// 4) OPEN KEBAB MENU → HISTORY
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

TestObject historyOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyOption, 5)
WebUI.click(historyOption)
WebUI.delay(2)

// ────────────────────────────────────────────────────────────────────
// 5) READ & LOG FIRST HISTORY ENTRY
// ────────────────────────────────────────────────────────────────────
TestObject firstEntry = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//ul[contains(@class,'appBar_popover_list')]//li)[1]"
)
if (WebUI.verifyElementPresent(firstEntry, 5, FailureHandling.OPTIONAL)) {
	String entryText = WebUI.getText(firstEntry).trim()
	WebUI.comment("✔ First history entry:\n${entryText}")
} else {
	WebUI.comment("⚠ No history entries found.")
}

WebUI.comment("✅ Done.")
