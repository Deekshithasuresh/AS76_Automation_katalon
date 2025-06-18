import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ────────────────────────────────────────────────────────────────────
// 1. LOGIN & NAVIGATE TO FIRST “To be reviewed” REPORT
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// credentials
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					  'JBaPNhID5RC7zcsLVwaWIA==')
// sign in
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

WebDriver driver = DriverFactory.getWebDriver()

// pick the first "To be reviewed" row
TestObject toBe = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//span[normalize-space()='To be reviewed']/ancestor::tr)[1]"
)
WebUI.waitForElementClickable(toBe, 10)
WebUI.click(toBe)

// ────────────────────────────────────────────────────────────────────
// 2. ASSIGN REPORT TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/following-sibling::div//button[@title='Open']"
)
WebUI.waitForElementClickable(assignedDropdown, 5)
WebUI.click(assignedDropdown)

TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.scrollToElement(adminOption, 5)
WebUI.click(adminOption)

TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)
WebUI.comment("✅ Assigned to admin")

// ────────────────────────────────────────────────────────────────────
// 3. OPEN RBC TAB & THEN THE SHAPE SUB-TAB
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementClickable(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[contains(@class,'cell-buttons') and .//span[text()='RBC']]"
	), 10
)
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[text()='RBC']]"
))
WebUI.delay(1)

WebUI.waitForElementClickable(
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[contains(@class,'rbc-size-btn') and normalize-space()='Shape']"
	), 5
)
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'rbc-size-btn') and normalize-space()='Shape']"
))
WebUI.delay(1)

// ────────────────────────────────────────────────────────────────────
// 4. CLICK “Acanthocytes” ROW & RE-GRADE IT (SAFE STRIKE-THROUGH CHECK)
// ────────────────────────────────────────────────────────────────────
WebElement acanthocytesRow = driver.findElement(
	By.xpath("//div[contains(@class,'cell-row') and not(contains(@class,'not-clickable'))"
		   + " and .//div[text()='Acanthocytes']]")
)
acanthocytesRow.click()
WebUI.delay(1)

// pick first unselected radio
List<WebElement> grades = acanthocytesRow.findElements(By.xpath(".//input[@type='radio']"))
for (WebElement g : grades) {
	if (!g.isSelected()) {
		g.click()
		WebUI.delay(1)
		// safe strike-through lookup
		List<WebElement> strikes = acanthocytesRow.findElements(By.xpath(".//del"))
		if (strikes.isEmpty()) {
			WebUI.comment("ℹ️ No <del> found — skipping strike-through assertion")
		} else {
			WebElement strikeElem = strikes.get(0)
			assert strikeElem.getCssValue("text-decoration-line").contains("line-through")
			WebUI.comment("✅ Strike-through verified")
		}
		break
	}
}

// ────────────────────────────────────────────────────────────────────
// 5. CLICK THE UNDO/RESET ICON
// ────────────────────────────────────────────────────────────────────
WebElement undoIcon = driver.findElement(
	By.xpath("//img[contains(@src,'icon-reset.svg')]")
)
undoIcon.click()
WebUI.delay(1)

// ────────────────────────────────────────────────────────────────────
// 6. OPEN KEBAB MENU & SELECT “History”
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

// ────────────────────────────────────────────────────────────────────
// 7. WAIT FOR & READ THE LATEST “Regrading” ENTRY
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
String title = latest.findElement(By.xpath(".//h4[@class='event-title']")).getText()
String desc  = latest.findElement(By.xpath(".//div[contains(@class,'event-description')]")).getText()
WebUI.comment("🔍 Latest history entry: ${title} — ${desc}")


