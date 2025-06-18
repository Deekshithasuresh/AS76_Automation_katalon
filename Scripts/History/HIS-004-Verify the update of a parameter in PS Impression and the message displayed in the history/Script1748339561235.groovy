import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.Keys as Keys

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					  'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// grab Selenium driver
WebDriver driver = DriverFactory.getWebDriver()

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “To be reviewed” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject toBeRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[normalize-space(text())='To be reviewed']])[1]"
)
WebUI.waitForElementClickable(toBeRow, 10)
WebUI.click(toBeRow)

// ────────────────────────────────────────────────────────────────────
// 3) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject dropdownBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
WebUI.waitForElementClickable(dropdownBtn, 5)
WebUI.click(dropdownBtn)

TestObject adminLi = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space()='admin']"
)
WebUI.waitForElementVisible(adminLi, 5)
WebUI.scrollToElement(adminLi, 5)
WebUI.click(adminLi)

// verify assignment
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)

// ────────────────────────────────────────────────────────────────────
// 4) FILL THE FIVE “Add details” EDITORS
// ────────────────────────────────────────────────────────────────────
List<WebElement> editors = driver.findElements(
	By.xpath("//div[@data-placeholder='Add details' and @contenteditable='true']")
)
assert editors.size() >= 5 : "Expected ≥5 editors, found ${editors.size()}"

for (int i = 0; i < 5; i++) {
	// build an index‐aware TestObject so we can scroll to it
	String editorXpath = "(//div[@data-placeholder='Add details' and @contenteditable='true'])[" + (i+1) + "]"
	TestObject edTo = new TestObject().addProperty('xpath', ConditionType.EQUALS, editorXpath)
	WebUI.scrollToElement(edTo, 5)

	WebElement ed = driver.findElement(By.xpath(editorXpath))
	ed.click()
	ed.clear()
	ed.sendKeys("Test@1234")
	ed.sendKeys(Keys.TAB)         // blur to trigger autosave
	WebUI.delay(1)
}

// ────────────────────────────────────────────────────────────────────
// 5) OPEN “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebab, 5)
WebUI.click(kebab)

TestObject historyOpt = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space()='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)

// ────────────────────────────────────────────────────────────────────
// 6) PRINT FIRST TWO HISTORY ENTRIES & SCREENSHOT
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
entries.take(2).eachWithIndex { e, idx ->
	println "History ${idx+1}: " + e.getText().trim()
}

WebUI.takeScreenshot("morphology-history.png")

