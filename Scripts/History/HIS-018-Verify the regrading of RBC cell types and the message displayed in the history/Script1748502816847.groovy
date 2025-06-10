import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys

// ────────────────────────────────────────────────────────────────────
// STEP 1: LOGIN & NAVIGATE TO LIST
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

// grab Selenium driver
WebDriver driver = DriverFactory.getWebDriver()

// ────────────────────────────────────────────────────────────────────
// STEP 2: OPEN FIRST “To be reviewed” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject toBe = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//span[normalize-space()='To be reviewed']/ancestor::tr)[1]"
)
WebUI.waitForElementClickable(toBe, 10)
WebUI.click(toBe)

// ────────────────────────────────────────────────────────────────────
// STEP 3: ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
// click the “Open” icon of the assigned-to combobox
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/following-sibling::div//button[@title='Open']"
)
WebUI.waitForElementClickable(assignedDropdown, 5)
WebUI.click(assignedDropdown)

// select “admin”
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(.)='admin']"
)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.scrollToElement(adminOption, 5)
WebUI.click(adminOption)

// verify assignment
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)
WebUI.comment("✅ Assigned to admin")

// ────────────────────────────────────────────────────────────────────
// STEP 4: FILL THE FIVE MORPHOLOGY EDITORS
// ────────────────────────────────────────────────────────────────────
List<WebElement> editors = driver.findElements(
	By.cssSelector("div.dx-htmleditor-content[contenteditable='true']")
)
assert editors.size() >= 5 : "Expected ≥5 editors, found ${editors.size()}"

for (int i = 0; i < 5; i++) {
	String editorXpath = "(//div[contains(@class,'dx-htmleditor-content') and @contenteditable='true'])[${i+1}]"
	WebUI.scrollToElement(
		new TestObject().addProperty('xpath', ConditionType.EQUALS, editorXpath),
		5
	)
	WebElement ed = driver.findElement(By.xpath(editorXpath))
	ed.click()
	ed.clear()
	ed.sendKeys("Test@1234")
	ed.sendKeys(Keys.TAB)  // blur to auto-save
	WebUI.delay(1)
	WebUI.comment("✅ Editor #${i+1} filled")
}

// ────────────────────────────────────────────────────────────────────
// STEP 5: SWITCH TO RBC & RE-GRADE FIRST CELL
// ────────────────────────────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[normalize-space()='RBC']]"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.delay(1)

// find each selected RBC cell row
List<WebElement> rows = driver.findElements(By.cssSelector("div.rbc-cell-body > div.selected.cell-row"))
boolean didRegrade = false
for (WebElement row : rows) {
	List<WebElement> radios = row.findElements(By.xpath(".//input[@type='radio']"))
	int current = radios.findIndexOf { it.isSelected() }
	if (current < 0) continue
	for (int offset = 1; offset < radios.size(); offset++) {
		int idx = (current + offset) % radios.size()
		if (radios[idx].isEnabled()) {
			radios[idx].click()
			WebUI.delay(1)
			WebUI.comment("✅ Regraded RBC cell ${current}→${idx}")
			didRegrade = true
			break
		}
	}
	if (didRegrade) break
}
assert didRegrade : "❌ Could not regrade any RBC cell"

// ────────────────────────────────────────────────────────────────────
// STEP 6: OPEN KEBAB MENU → HISTORY
// ────────────────────────────────────────────────────────────────────
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebab, 5)
WebUI.click(kebab)

TestObject historyOpt = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(.)='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)

// ────────────────────────────────────────────────────────────────────
// STEP 7: PRINT FIRST TWO HISTORY ENTRIES + SCREENSHOT
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
entries.take(2).eachWithIndex { e, idx ->
	println "History ${idx+1}: ${e.getText().trim()}"
}
WebUI.takeScreenshot("final-history.png")

