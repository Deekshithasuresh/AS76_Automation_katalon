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
// 1) LOGIN & NAVIGATE TO LIST
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('https://as76-pbs.sigtuple.com/login')
WebUI.maximizeWindow()
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// grab the WebDriver for DOM interactions
WebDriver driver = DriverFactory.getWebDriver()

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “To be reviewed” REPORT
// ────────────────────────────────────────────────────────────────────
String toBeReviewedXpath = "(//tr[.//span[normalize-space(text())='To be reviewed']])[1]"
TestObject toBeReviewedRow = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS, toBeReviewedXpath)
WebUI.waitForElementClickable(toBeReviewedRow, 10)
WebUI.click(toBeReviewedRow)

// ────────────────────────────────────────────────────────────────────
// 3) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button")
WebUI.click(assignedDropdown)

TestObject adminOption = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS,
		"//li[@role='option' and normalize-space(text())='admin']")
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)

WebUI.waitForElementAttributeValue(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//input[@id='assigned_to']"),
	'value', 'admin', 5
)

// ────────────────────────────────────────────────────────────────────
// 4) OPEN KEBAB MENU & CLICK “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS,
		"//button[.//img[contains(@src,'kebab_menu.svg')]]")
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

TestObject historyOpt = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS,
		"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li")
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)

// ────────────────────────────────────────────────────────────────────
// 5) VERIFY FIRST “Report assignment” ENTRY
// ────────────────────────────────────────────────────────────────────
// wait for the first history entry <li>
String entryXpath = "(//li[contains(@class,'css-1ecsk3j')])[1]"
TestObject entryObj = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS, entryXpath)
WebUI.waitForElementVisible(entryObj, 10)

// locate the entry via WebDriver
WebElement entry = driver.findElement(By.xpath(entryXpath))

// extract and verify Title
String title = entry.findElement(By.cssSelector("h4.event-title")).getText().trim()
println("Title       : " + title)
WebUI.verifyMatch(title, "Report assingment", true, FailureHandling.STOP_ON_FAILURE)

// extract and print Timestamp
String timestamp = entry.findElement(By.cssSelector("div.time")).getText().trim()
println("Timestamp   : " + timestamp)

// extract and verify Description
String description = entry.findElement(By.cssSelector("div.event-description")).getText().trim()
println("Description : " + description)
WebUI.verifyMatch(description, ".*assigned the report", true, FailureHandling.STOP_ON_FAILURE)

// extract and print From/To values
List<WebElement> values = entry.findElements(By.cssSelector("div.event-transaction .rendered-content"))
String fromValue = values.size() > 0 ? values.get(0).getText().trim() : ""
String toValue   = values.size() > 1 ? values.get(1).getText().trim() : ""
println("From value  : " + fromValue)
println("To value    : " + toValue)
WebUI.verifyMatch(fromValue, "-", true, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.verifyMatch(toValue, "admin", true, FailureHandling.CONTINUE_ON_FAILURE)
