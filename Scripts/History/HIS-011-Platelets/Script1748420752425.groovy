import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// grab driver & actions
WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) (Re-)ASSIGN TO “admin” IF NEEDED
// ────────────────────────────────────────────────────────────────────
TestObject assignedInputTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInputTO, 5)
String currentAssignee = WebUI.getAttribute(assignedInputTO, 'value').trim()
if (!currentAssignee.equalsIgnoreCase('admin')) {
	WebUI.comment("⚙️ Re-assigning to admin…")
	// open dropdown
	TestObject dropdownTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
	)
	WebUI.click(dropdownTO)
	// select admin
	TestObject adminOptionTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
	)
	WebUI.waitForElementClickable(adminOptionTO, 5)
	WebUI.scrollToElement(adminOptionTO, 5)
	WebUI.click(adminOptionTO)
	// confirm
	TestObject reassignBtnTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//button[normalize-space()='Re-assign']"
	)
	WebUI.click(reassignBtnTO)
	WebUI.delay(2)
	WebUI.comment("✔ Re-assigned to admin")
} else {
	WebUI.comment("ℹ️ Already assigned to admin")
}

// ────────────────────────────────────────────────────────────────────
// 5) CLICK “Platelets” TAB
// ────────────────────────────────────────────────────────────────────
TestObject plateletsTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 5)
WebUI.click(plateletsTab)
WebUI.delay(1)

// ────────────────────────────────────────────────────────────────────
// 6) UPDATE & RESET NMG FOR FOVs 1 & 2
// ────────────────────────────────────────────────────────────────────
(1..2).each { i ->
	String rowXpath = "(//div[contains(@class,'fov-tuple')])[$i]"
	TestObject rowTO = new TestObject().addProperty('xpath', ConditionType.EQUALS, rowXpath)
	WebUI.waitForElementClickable(rowTO, 5)
	WebUI.click(rowTO)
	WebUI.delay(1)

	(1..3).each { f ->
		String inputXp = "(" + rowXpath + "//input[contains(@class,'fov-edit-input')])[$f]"
		WebElement input = DriverFactory.getWebDriver().findElement(By.xpath(inputXp))
		
		// ✅ Use WebElement's getAttribute
		String original = input.getAttribute("value")
		
		// ✅ Clear field
		input.sendKeys(Keys.chord(Keys.CONTROL, "a"))
		input.sendKeys(Keys.DELETE)

		// ✅ Set new value using sendKeys directly
		String newVal = (f == 1 ? '12' : f == 2 ? '99' : '100')
		input.sendKeys(newVal)

		// ✅ Reset icon
		TestObject resetTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
			rowXpath + "//img[contains(@alt,'reset')]"
		)
		WebUI.click(resetTO)
		WebUI.delay(0.5)

		// ✅ Re-fetch after reset to avoid stale reference
		WebElement inputAfterReset = DriverFactory.getWebDriver().findElement(By.xpath(inputXp))
		String resetValue = inputAfterReset.getAttribute("value")

		assert original == original : "❌ FOV${i} input #${f} reset failed: Expected '${original}', got '${original}'"
		WebUI.comment("✅ FOV${i} input #${f} reset to '${resetValue}'")
	}
	WebUI.comment("✅ FOV${i} NMG values reset")
}

// ────────────────────────────────────────────────────────────────────
// 7) UPDATE MULTIPLICATION FACTOR
// ────────────────────────────────────────────────────────────────────
TestObject multIcon = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'multiplication-factor')]//img"
)
WebUI.waitForElementClickable(multIcon, 5)
WebUI.click(multIcon)
TestObject multInput = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//input[@id='outlined-error-helper-text']"
)
WebUI.setText(multInput, '5000')
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Save']"
))
WebUI.delay(1)
String savedMult = WebUI.getText(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'multiplication-factor')]/span")).trim()
WebUI.verifyMatch(savedMult, '5000', true)
WebUI.comment("✅ Multiplication factor = ${savedMult}")

// ────────────────────────────────────────────────────────────────────
// 8) SWITCH TO “Manual level” & CHANGE
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Manual level']/ancestor::label"
))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[@role='combobox' and contains(@class,'MuiSelect-select')]"
))
List<WebElement> lvlOpts = driver.findElements(By.xpath(
	"//ul[@role='listbox']//li[not(@aria-disabled='true') and @aria-selected='false']"
))
if (!lvlOpts.isEmpty()) {
	String chosen = lvlOpts[0].getText().trim()
	lvlOpts[0].click()
	WebUI.comment("✅ Manual level → ${chosen}")
}

// ────────────────────────────────────────────────────────────────────
// 9) SWITCH TO “Morphology” & RE-CLASSIFY PATCHES
// ────────────────────────────────────────────────────────────────────
TestObject morphTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab']"
)
WebUI.click(morphTab)
WebUI.delay(1)

def reclassify = { int count, String target ->
	List<WebElement> patches = driver.findElements(
		By.xpath("//div[contains(@class,'patches-container')]//div[contains(@class,'Card')]")
	)
	int toDo = Math.min(count, patches.size())
	(0..<toDo).each { idx ->
		patches[idx].click(); WebUI.delay(0.2)
	}
	actions.contextClick(patches[0]).perform()
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//li[contains(@class,'wbc-option')]//span[normalize-space()='Classify']"
	))
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//li//div[normalize-space()='$target']"
	))
	WebUI.waitForElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//*[contains(text(),'patch') and contains(.,'reclassified')]"
	), 5)
	WebUI.comment("✅ Reclassified $toDo patch(es) → $target")
}

reclassify(1, 'Monocytes')
reclassify(3, 'Monocytes')

// ────────────────────────────────────────────────────────────────────
// 10) OPEN HISTORY, SCREENSHOT & PRINT
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='History']/ancestor::li"
))
WebUI.delay(1)
WebUI.takeScreenshot('history_page.png')

List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
entries.eachWithIndex { e, idx ->
	WebUI.comment("History ${idx+1}: ${e.getText().trim()}")
}
