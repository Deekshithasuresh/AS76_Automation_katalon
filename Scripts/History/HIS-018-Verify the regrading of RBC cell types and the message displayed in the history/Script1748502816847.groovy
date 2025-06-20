import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

/ ────────────────────────────────────────────────────────────────────
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

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
String underReviewXpath = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, underReviewXpath)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//li[@role='option' and normalize-space(text())='admin']"
)
TestObject assignedInput = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//input[@id='assigned_to']"
)

WebUI.click(assignedDropdown)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)
// ────────────────────────────────────────────────────────────────────
// STEP 4: CLICK THE RBC TAB
// ────────────────────────────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[text()='RBC']]"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.delay(2)   // let the RBC panel render

// ────────────────────────────────────────────────────────────────────
// STEP 5: CLICK A CLICKABLE CELL AND REGRADE IT
// ────────────────────────────────────────────────────────────────────
List<WebElement> clickableRows = driver.findElements(
	By.xpath("//div[contains(@class,'cell-row') and not(contains(@class,'not-clickable'))]")
)
if (clickableRows.isEmpty()) {
	WebUI.comment("❌ No clickable RBC cells found")
	WebUI.closeBrowser()
	return
}
// click the first one
clickableRows.get(0).click()
WebUI.delay(1)

// now find its grade‐radios and pick the first that isn’t already selected
WebElement row = clickableRows.get(0)
List<WebElement> grades = row.findElements(By.xpath(".//input[@type='radio']"))
boolean regraded = false
for (WebElement g : grades) {
	if (!g.isSelected()) {
		g.click()
		WebUI.delay(1)
		// verify old percentage is struck through
		WebElement strikeElem = row.findElement(By.xpath(".//div[3]/del"))
		String td = strikeElem.getCssValue("text-decoration-line")
		assert td.contains("line-through")
		WebUI.comment("✅ “${row.findElement(By.xpath('.//div[1]')).getText()}” got regraded and struck through")
		regraded = true
		break
	}
}
if (!regraded) {
	WebUI.comment("ℹ️ All grades were already selected, no change made")
}
// ────────────────────────────────────────────────────────────────────
// 6: OPEN KEBAB MENU & SELECT HISTORY
// ────────────────────────────────────────────────────────────────────
// click three‐dots kebab
TestObject kebabBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

// click History option
TestObject historyOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[contains(@class,'appBar_popover__list-item') and .//span[text()='History']]"
)
WebUI.waitForElementClickable(historyOption, 5)
WebUI.click(historyOption)

// ────────────────────────────────────────────────────────────────────
// 7: READ & PRINT LATEST HISTORY ENTRY
// ────────────────────────────────────────────────────────────────────
// wait for the Regrading entry to show up
WebUI.waitForElementVisible(
	new TestObject().addProperty('xpath', "//h4[contains(@class,'event-title') and text()='Regrading']"),
	10
)

// grab the very first <li> under the history list
WebElement latest = driver.findElement(
	By.xpath("//ul[contains(@class,'events-container')]/li[1]")
)
String title = latest.findElement(By.xpath(".//h4[@class='event-title']")).getText()
String desc  = latest.findElement(By.xpath(".//div[contains(@class,'event-description')]")).getText()
WebUI.comment("🔍 Latest history: “${title}” — ${desc}")

