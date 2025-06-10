import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ────────────────────────────────────────────────────────────────────
// 1. LOGIN, NAVIGATE & ASSIGN TO “admin”
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

WebDriver driver = DriverFactory.getWebDriver()

// open first “To be reviewed”
TestObject toBe = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//span[normalize-space()='To be reviewed']/ancestor::tr)[1]"
)
WebUI.waitForElementClickable(toBe, 10)
WebUI.click(toBe)

// assign to admin
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/following-sibling::div//button[@title='Open']"
)
WebUI.click(assignedDropdown)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space()='admin']"
)
WebUI.scrollToElement(adminOption, 5)
WebUI.click(adminOption)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)
WebUI.comment("✅ Assigned to admin")

// ────────────────────────────────────────────────────────────────────
// 2. CLICK RBC TAB → SHAPE SUB-TAB
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[text()='RBC']]"
))
WebUI.delay(1)
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'rbc-size-btn') and normalize-space()='Shape']"
))
WebUI.delay(1)

// ────────────────────────────────────────────────────────────────────
// 3. CLICK “Acanthocytes” ROW & REGRADE
// ────────────────────────────────────────────────────────────────────
WebElement acanthocytesRow = driver.findElement(
	By.xpath("//div[contains(@class,'cell-row') and not(contains(@class,'not-clickable'))"
			+ " and .//div[text()='Acanthocytes']]")
)
acanthocytesRow.click()
WebUI.delay(1)

List<WebElement> grades = acanthocytesRow.findElements(By.xpath(".//input[@type='radio']"))
boolean regraded = false
for (WebElement g : grades) {
	if (!g.isSelected()) {
		g.click()
		WebUI.delay(1)
		// verify strikethrough on old value
		WebElement strikeElem = acanthocytesRow.findElement(By.xpath(".//div[3]/del"))
		assert strikeElem.getCssValue("text-decoration-line").contains("line-through")
		WebUI.comment("✅ Acanthocytes regraded")
		regraded = true
		break
	}
}
if (!regraded) {
	WebUI.comment("ℹ️ No un-selected grade found for Acanthocytes")
}

// ────────────────────────────────────────────────────────────────────
// 4. CLICK UNDO/RESET ICON
// ────────────────────────────────────────────────────────────────────
WebElement undoIcon = driver.findElement(
	By.xpath("//img[contains(@src,'icon-reset.svg')]")
)
undoIcon.click()
WebUI.delay(1)

// ────────────────────────────────────────────────────────────────────
// 5. OPEN KEBAB MENU → HISTORY
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
// 6. VERIFY LATEST HISTORY ENTRY
// ────────────────────────────────────────────────────────────────────
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
String title = latest.findElement(By.xpath(".//h4[@class='event-title']")).getText()
String desc  = latest.findElement(By.xpath(".//div[contains(@class,'event-description')]")).getText()
WebUI.comment("🔍 Latest history entry: ${title} — ${desc}")


