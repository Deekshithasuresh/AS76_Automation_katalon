import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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
