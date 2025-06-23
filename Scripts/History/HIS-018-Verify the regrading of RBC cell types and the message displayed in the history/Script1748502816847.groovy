import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

// grab the Selenium WebDriver
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
// 5) CLICK THE RBC TAB
// ────────────────────────────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[normalize-space(text())='RBC']]"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.delay(2)   // let the RBC panel render

// ────────────────────────────────────────────────────────────────────
// 6) CLICK FIRST CLICKABLE CELL & RE-GRADE IT
// ────────────────────────────────────────────────────────────────────
List<WebElement> clickableRows = driver.findElements(
	By.xpath("//div[contains(@class,'cell-row') and not(contains(@class,'not-clickable'))]")
)
if (clickableRows.isEmpty()) {
	WebUI.comment("No clickable RBC cells found")
	WebUI.closeBrowser()
	return
}

WebElement targetRow = clickableRows.get(0)
String cellName = targetRow.findElement(By.xpath(".//div[1]")).getText().trim()
targetRow.click()
WebUI.delay(1)

// pick the first radio that isn’t selected
List<WebElement> grades = targetRow.findElements(By.xpath(".//input[@type='radio']"))
boolean regraded = false
for (WebElement g : grades) {
	if (!g.isSelected()) {
		g.click()
		WebUI.delay(1)
		// verify strike-through on the old value
		WebElement strikeElem = targetRow.findElement(By.xpath(".//div[3]/del"))
		assert strikeElem.getCssValue("text-decoration-line").contains("line-through")
		WebUI.comment("✅ “${cellName}” re-graded and strike-through verified")
		regraded = true
		break
	}
}
if (!regraded) {
	WebUI.comment("⚠ All grades already selected—no re-grade performed")
}

// ────────────────────────────────────────────────────────────────────
// 7) OPEN KEBAB MENU & SELECT “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

TestObject historyOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[contains(@class,'appBar_popover__list-item') and .//span[text()='History']]"
)
WebUI.waitForElementClickable(historyOption, 5)
WebUI.click(historyOption)

// ────────────────────────────────────────────────────────────────────
// 8) WAIT FOR & VERIFY “Regrading” HISTORY ENTRY
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementVisible(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//h4[contains(@class,'event-title') and text()='Regrading']"
	), 10
)

WebElement latest = driver.findElement(
	By.xpath("//ul[contains(@class,'events-container')]/li[1]")
)
String title = latest.findElement(By.cssSelector("h4.event-title")).getText().trim()
String desc  = latest.findElement(By.cssSelector("div.event-description")).getText().trim()

WebUI.comment("🔍 History: ${title} — ${desc}")
assert title == 'Regrading'
assert desc.toLowerCase().contains(cellName.toLowerCase())

