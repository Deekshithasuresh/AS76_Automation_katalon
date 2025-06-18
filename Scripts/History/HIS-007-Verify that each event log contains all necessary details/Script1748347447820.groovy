import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
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

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “To be reviewed” REPORT
// ────────────────────────────────────────────────────────────────────
String toBeReviewedXpath = "(//tr[.//span[normalize-space(text())='To be reviewed']])[1]"
TestObject toBeReviewedRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, toBeReviewedXpath)
WebUI.waitForElementClickable(toBeReviewedRow, 10)
WebUI.click(toBeReviewedRow)

// ────────────────────────────────────────────────────────────────────
// 3) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
WebUI.click(assignedDropdown)

TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)

WebUI.waitForElementAttributeValue(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//input[@id='assigned_to']"),
	'value', 'admin', 5
)

// ────────────────────────────────────────────────────────────────────
// 4) OPEN KEBAB MENU → CLICK “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebab, 5)
WebUI.click(kebab)

TestObject historyItem = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyItem, 5)
WebUI.click(historyItem)

// ────────────────────────────────────────────────────────────────────
// 5) WAIT FOR HISTORY PAGE & VERIFY ENTRIES
// ────────────────────────────────────────────────────────────────────
// wait for the History header
TestObject historyHeader = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//h1[contains(normalize-space(text()),'History')]"
)
WebUI.waitForElementVisible(historyHeader, 10)

// grab WebDriver for DOM traversal
WebDriver driver = DriverFactory.getWebDriver()

// find all history entries
List<WebElement> entries = driver.findElements(
	By.cssSelector("li.css-1ecsk3j")
)

if (entries.isEmpty()) {
	WebUI.comment("No history entries found!")
} else {
	for (WebElement entry : entries) {
		// 5a) Date & Time
		String dateAndTime = entry.findElement(
			By.cssSelector("div.event-header .time")
		).getText().trim()
		
		// 5b) Event description
		String description = entry.findElement(
			By.cssSelector("div.event-description")
		).getText().trim()
		
		// 5c) Changed by
		// assume description starts with "<user> …"
		String changedBy = description.split("\\s+")[0]
		
		// 5d) From & To values, if present
		List<WebElement> rendered = entry.findElements(
			By.cssSelector("div.event-transaction .rendered-content")
		)
		String fromValue = rendered.size() > 0 ? rendered.get(0).getText().trim() : ""
		String toValue   = rendered.size() > 1 ? rendered.get(1).getText().trim() : ""
		
		// print out all details
		println("---- History Entry ----")
		println("Date & Time   : " + dateAndTime)
		println("Description   : " + description)
		println("Changed by    : " + changedBy)
		println("From value    : " + fromValue)
		println("To value      : " + toValue)
		println("-----------------------")
	}
}
