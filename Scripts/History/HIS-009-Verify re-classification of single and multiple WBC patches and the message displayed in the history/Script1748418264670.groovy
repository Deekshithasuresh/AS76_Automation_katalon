import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

// ────────────────────────────────────────────────────────────────────
// CONFIGURATION
// ────────────────────────────────────────────────────────────────────
String baseUrl  = 'https://as76-pbs.sigtuple.com/login'
String username = 'adminuserr'
String password = 'JBaPNhID5RC7zcsLVwaWIA=='

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser(baseUrl)
WebUI.maximizeWindow()
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), username)
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'), password)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// grab WebDriver & Actions
WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)


// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “To be reviewed” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject toBeReviewedRow = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//tr[.//span[normalize-space(text())='To be reviewed']])[1]"
)
WebUI.waitForElementClickable(toBeReviewedRow, 10)
WebUI.click(toBeReviewedRow)


// ────────────────────────────────────────────────────────────────────
// 3) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
))
TestObject adminOption = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
)
WebUI.waitForElementVisible(adminOption, 5)
WebUI.scrollToElement(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//input[@id='assigned_to']"),
	'value', 'admin', 5
)


// ────────────────────────────────────────────────────────────────────
// 4) CLICK ON WBC TAB
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[normalize-space(text())='WBC']]"
))


// ────────────────────────────────────────────────────────────────────
// 5) RECLASSIFY SINGLE WBC PATCH → “Monocytes”
// ────────────────────────────────────────────────────────────────────
List<WebElement> patches = driver.findElements(
	By.xpath("//div[contains(@class,'patches-container')]")
)
if (patches.size() >= 1) {
	WebElement firstPatch = patches.get(0)
	firstPatch.click()
	WebUI.comment("✔️ Selected first patch")

	actions.contextClick(firstPatch).perform()
	WebUI.comment("→ Opened context menu")

	// click “Classify”
	TestObject classifyItem = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//li[contains(@class,'wbc-option')]//span[normalize-space(text())='Classify']"
	)
	WebUI.waitForElementClickable(classifyItem, 5)
	WebUI.click(classifyItem)

	// click “Monocytes”
	TestObject monoOption = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//li[not(contains(@class,'wbc-option'))]//div[normalize-space(text())='Monocytes']"
	)
	WebUI.waitForElementClickable(monoOption, 5)
	WebUI.click(monoOption)

	WebUI.comment("✅ Reclassified single patch to Monocytes")
	WebUI.waitForElementPresent(
		new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//div[contains(text(),'patch reclassified') or contains(text(),'patches reclassified')]"
		), 5
	)
}


// ────────────────────────────────────────────────────────────────────
// 6) RECLASSIFY MULTIPLE WBC PATCHES → “Monocytes”
// ────────────────────────────────────────────────────────────────────
if (patches.size() > 1) {
	int multiCount = Math.min(4, patches.size())
	for (int i = 1; i < multiCount; i++) {
		patches.get(i).click()
		WebUI.comment("✔️ Selected patch #${i+1}")
		WebUI.delay(0.2)
	}

	WebElement anySelected = patches.get(1)
	actions.contextClick(anySelected).perform()
	WebUI.comment("→ Opened context menu for multiple patches")

	// click “Classify”
	WebUI.waitForElementClickable(classifyItem, 5)
	WebUI.click(classifyItem)
	// click “Monocytes”
	WebUI.waitForElementClickable(monoOption, 5)
	WebUI.click(monoOption)

	WebUI.comment("✅ Reclassified ${multiCount} patches to Monocytes")
	WebUI.waitForElementPresent(
		new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//div[contains(text(),'patch reclassified') or contains(text(),'patches reclassified')]"
		), 5
	)
}


// ────────────────────────────────────────────────────────────────────
// 7) OPEN KEBAB MENU & PRINT LATEST 5 HISTORY ENTRIES
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space(text())='History']/ancestor::li"
))
WebUI.delay(1)

List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
int toPrint = Math.min(5, entries.size())
for (int i = 0; i < toPrint; i++) {
	println("History entry ${i+1}: " + entries.get(i).getText().trim())
}
