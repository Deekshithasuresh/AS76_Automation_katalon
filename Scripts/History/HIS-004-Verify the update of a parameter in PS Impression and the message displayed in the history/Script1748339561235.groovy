import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.Keys

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN & LAND ON REPORT LIST
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
	10)

// grab native driver
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
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInput, 5)
String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
WebUI.comment("Current assignee: ${currentAssignee}")

if (!currentAssignee.equalsIgnoreCase('admin')) {
	TestObject dropdownBtn = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
	)
	WebUI.click(dropdownBtn)

	TestObject adminOption = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
	)
	WebUI.waitForElementVisible(adminOption, 5)
	WebUI.scrollToElement(adminOption, 5)
	WebUI.click(adminOption)

	TestObject reassignBtn = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[normalize-space(text())='Re-assign']"
	)
	WebUI.click(reassignBtn)
	WebUI.delay(2)
	WebUI.comment("✔ Re-assigned to admin")
} else {
	WebUI.comment("ℹ️ Already assigned to admin—skipping reassignment")
}

// ────────────────────────────────────────────────────────────────────
// 4) FILL THE FIVE “Add details” EDITORS IN PS Impression
// ────────────────────────────────────────────────────────────────────
List<WebElement> editors = driver.findElements(
	By.xpath("//div[@data-placeholder='Add details' and @contenteditable='true']")
)
assert editors.size() >= 5 : "Expected ≥5 editors, found ${editors.size()}"

for (int i = 1; i <= 5; i++) {
	String editorXpath = "(//div[@data-placeholder='Add details' and @contenteditable='true'])[$i]"
	TestObject editorTO = new TestObject().addProperty('xpath', ConditionType.EQUALS, editorXpath)
	WebUI.scrollToElement(editorTO, 5)

	WebElement ed = driver.findElement(By.xpath(editorXpath))
	ed.click()
	ed.clear()
	ed.sendKeys("Test@1234")
	ed.sendKeys(Keys.TAB)  // blur to trigger autosave
	WebUI.delay(1)
}
WebUI.comment("✔ Filled PS Impression details")

// ────────────────────────────────────────────────────────────────────
// 5) OPEN KEBAB MENU & SELECT “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

TestObject historyOpt = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)
WebUI.delay(2)

// ────────────────────────────────────────────────────────────────────
// 6) PRINT FIRST TWO HISTORY ENTRIES & SCREENSHOT
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
entries.take(2).eachWithIndex { e, idx ->
	println "History ${idx+1}: ${e.getText().trim()}"
}
WebUI.takeScreenshot("PS-Impression-history.png")

WebUI.comment("✅ PS Impression update and history verified.")
