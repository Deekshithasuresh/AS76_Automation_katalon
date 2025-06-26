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

// grab Selenium driver
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
// 4) ASSIGN TO “admin” IF NOT ALREADY
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
	WebUI.comment("️ Currently assigned to '${currentAssignee}', re-assigning to admin…")
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
// 5) NAVIGATE TO RBC → SHAPE
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementClickable(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[contains(@class,'cell-buttons') and .//span[normalize-space(text())='RBC']]"
	),
	10
)
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[normalize-space(text())='RBC']]"
))
WebUI.delay(1)

WebUI.waitForElementClickable(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[contains(@class,'rbc-size-btn') and normalize-space(text())='Shape']"
	),
	5
)
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'rbc-size-btn') and normalize-space(text())='Shape']"
))
WebUI.delay(1)

// ────────────────────────────────────────────────────────────────────
// 6) CLICK “Acanthocytes” & RE-GRADE IT
// ────────────────────────────────────────────────────────────────────
WebElement acanthocytesRow = driver.findElement(By.xpath(
	"//div[contains(@class,'cell-row') and not(contains(@class,'not-clickable'))]" +
	"[.//div[text()='Acanthocytes']]"
))
acanthocytesRow.click()
WebUI.delay(1)

List<WebElement> grades = acanthocytesRow.findElements(By.xpath(".//input[@type='radio']"))
for (WebElement g : grades) {
	if (!g.isSelected()) {
		g.click()
		WebUI.delay(1)
		List<WebElement> strikes = acanthocytesRow.findElements(By.xpath(".//del"))
		if (strikes) {
			assert strikes.get(0).getCssValue("text-decoration-line").contains("line-through")
			WebUI.comment(" Strike-through verified")
		} else {
			WebUI.comment(" No <del> — skipping strike-through check")
		}
		break
	}
}

// ────────────────────────────────────────────────────────────────────
// 7) UNDO/RESET THE CHANGE
// ────────────────────────────────────────────────────────────────────
WebElement undoIcon = driver.findElement(By.xpath("//img[contains(@src,'icon-reset.svg')]"))
undoIcon.click()
WebUI.delay(1)

// ────────────────────────────────────────────────────────────────────
// 8) OPEN HISTORY & VERIFY “Regrading” ENTRY
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
))
WebUI.delay(1)
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[contains(@class,'appBar_popover__list-item') and .//span[text()='History']]"
))
WebUI.delay(1)

WebUI.waitForElementVisible(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//h4[contains(@class,'event-title') and text()='Regrading']"
	),
	10
)

WebElement latest = driver.findElement(
	By.xpath("//ul[contains(@class,'events-container')]/li[1]")
)
String title = latest.findElement(By.cssSelector("h4.event-title")).getText().trim()
String desc  = latest.findElement(By.cssSelector("div.event-description")).getText().trim()

WebUI.comment(" Latest history entry: ${title} — ${desc}")
assert title == 'Regrading'
assert desc.toLowerCase().contains("acanthocytes")
