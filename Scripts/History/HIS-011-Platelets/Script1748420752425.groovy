import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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

// prepare driver & actions
WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

// 3) CLICK “Platelets” TAB
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons cell-tab') and .//span[normalize-space(text())='Platelets']]"
))
WebUI.delay(1)

// 4) UPDATE & RESET NMG FOR FOVs 1 & 2
for (int i = 1; i <= 2; i++) {
	String rowXpath = "(//div[contains(@class,'fov-tuple')])[" + i + "]"
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS, rowXpath))
	WebUI.delay(0.5)
	for (int f = 1; f <= 3; f++) {
		TestObject input = new TestObject().addProperty('xpath', ConditionType.EQUALS,
			rowXpath + "//input[@class='fov-edit-input'][" + f + "]"
		)
		String original = WebUI.getAttribute(input, 'value')
		// enter test values
		String newVal = (f == 1 ? '12' : f == 2 ? '99' : '100')
		WebUI.setText(input, newVal)
		// reset
		WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
			rowXpath + "//img[@alt='icon-reset']"
		))
		WebUI.delay(0.5)
		WebUI.verifyMatch(WebUI.getAttribute(input, 'value'), original, false)
	}
	WebUI.comment("✅ FOV${i} NMG values reset")
}

// 5) UPDATE MULTIPLICATION FACTOR
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'multiplication-factor')]//i"
))
WebUI.setText(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//input[@placeholder='Enter multiplication factor']"
), '5000')
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Save']"
))
WebUI.delay(1)
String savedMult = WebUI.getText(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'multiplication-factor')]/span"
)).trim()
WebUI.verifyMatch(savedMult, '5000', false)
WebUI.comment("✅ Multiplication factor set to 5000")

// 6) SWITCH TO “Manual level” & CHANGE COUNT LEVEL
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space(text())='Manual level']/ancestor::label"
))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[@role='combobox' and contains(@class,'MuiSelect-select')]"
))
List<WebElement> lvlOpts = driver.findElements(By.xpath(
	"//ul[@role='listbox']//li[not(@aria-disabled='true') and @aria-selected='false']"
))
if (!lvlOpts.isEmpty()) {
	lvlOpts.get(0).click()
	WebUI.comment("✅ Manual level changed to '${lvlOpts.get(0).getText().trim()}'")
} else {
	WebUI.comment("⚠️ No alternative level option available")
}

// 7) CLICK “Morphology” TAB
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[normalize-space(text())='Morphology']]"
))
WebUI.delay(1)

// 8) CLASSIFY SINGLE & MULTIPLE PATCHES
def classifyPatches = { int cnt, String tgt ->
	List<WebElement> patches = driver.findElements(By.xpath(
		"//div[contains(@class,'patches-section')]//div[contains(@class,'Card patches-container')]"
	))
	int toSelect = Math.min(cnt, patches.size())
	for (int j = 0; j < toSelect; j++) {
		patches[j].click()
		WebUI.delay(0.2)
	}
	actions.contextClick(patches[0]).perform()
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//li[contains(@class,'wbc-option')]//span[normalize-space(text())='Classify']"
	))
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//li//div[normalize-space(text())='${tgt}']"
	))
	WebUI.waitForElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//div[contains(text(),'patch reclassified')]"
	), 5)
	WebUI.comment("✅ Reclassified ${toSelect} patch(es) to ${tgt}")
}
classifyPatches(1, 'Monocytes')
classifyPatches(3, 'Monocytes')

// 9) OPEN HISTORY, SCREENSHOT, PRINT & SCROLL
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space(text())='History']/ancestor::li"
))
WebUI.delay(1)
WebUI.takeScreenshot('history_page.png')
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
entries.eachWithIndex { WebElement e, int k ->
	WebUI.comment("History entry ${k+1}: ${e.getText().trim()}")
}
WebUI.scrollToPosition(0, 800)
WebUI.delay(0.5)
WebUI.scrollToPosition(0, 0)

// 10) VERIFY CHRONOLOGICAL ORDER
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm a", Locale.ENGLISH)
List<LocalDateTime> times = []
entries.each { WebElement ent ->
	String raw = ent.findElement(By.cssSelector("div.time")).getText().trim()
	String cleaned = raw.replaceAll(/\s*\(.+\)$/, "")
	times << LocalDateTime.parse(cleaned, fmt)
}
for (int m = 0; m < times.size() - 1; m++) {
	assert !times[m].isBefore(times[m+1]) :
		"Timestamps out of order: entry ${m+1} (${times[m]}) before entry ${m+2} (${times[m+1]})"
}
WebUI.comment("✅ History entries are in descending chronological order")
