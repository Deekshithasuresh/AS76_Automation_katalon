import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ─────────────────────────────────────────────────────────────────────────────
// STEP 1: LOGIN
// ─────────────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					  'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ─────────────────────────────────────────────────────────────────────────────
// STEP 2: PICK & ASSIGN REPORT
// ─────────────────────────────────────────────────────────────────────────────
TestObject toBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']"
)
TestObject underReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)
TestObject assignDrop = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject assignInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
TestObject adminOpt = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//li[normalize-space(text())='admin']"
)
TestObject reassignBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//button[normalize-space()='Re-assign']"
)

if ( WebUI.waitForElementPresent(toBeReviewed, 3) ) {
	WebUI.click(toBeReviewed)
	WebUI.click(assignDrop)
	WebUI.waitForElementClickable(adminOpt, 5)
	WebUI.click(adminOpt)
	WebUI.comment "Assigned a ‘To be reviewed’ report to admin"
}
else if ( WebUI.waitForElementPresent(underReview, 3) ) {
	WebUI.click(underReview)
	String curr = WebUI.getAttribute(assignInput, 'value').trim()
	if ( curr != 'admin' ) {
		WebUI.click(assignDrop)
		WebUI.waitForElementClickable(adminOpt, 5)
		WebUI.click(adminOpt)
		WebUI.waitForElementClickable(reassignBtn, 5)
		WebUI.click(reassignBtn)
		WebUI.comment "Re-assigned an ‘Under review’ report to admin"
	} else {
		WebUI.comment "‘Under review’ already assigned to admin"
	}
}
else {
	WebUI.comment "❌ No report in ‘To be reviewed’ or ‘Under review’"
	WebUI.closeBrowser()
	return
}

// ─────────────────────────────────────────────────────────────────────────────
// STEP 3: WAIT FOR “Approve report” BUTTON
// ─────────────────────────────────────────────────────────────────────────────
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)
WebUI.waitForElementVisible(approveBtn, 10)

// ─────────────────────────────────────────────────────────────────────────────
// STEP 4: CLICK RBC TAB
// ─────────────────────────────────────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[normalize-space()='RBC']]"
)
WebUI.click(rbcTab)
WebUI.delay(1)

// ─────────────────────────────────────────────────────────────────────────────
// STEP 5: “REGRADE” FIRST AVAILABLE CELL
// ─────────────────────────────────────────────────────────────────────────────
WebDriver driver = DriverFactory.getWebDriver()

// find all grade-div containers
List<WebElement> cells = driver.findElements(By.cssSelector("div.grade-div"))
assert !cells.isEmpty(): "❌ No RBC cells found to regrade"

boolean didRegrade = false

for (WebElement cell : cells) {
	// within this cell, find all the radio‐button spans
	List<WebElement> radios = cell.findElements(
		By.cssSelector("span.MuiButtonBase-root.MuiRadio-root")
	)
	if (radios.isEmpty()) {
		continue    // nothing to click here
	}
	// determine which one is currently checked
	int selected = -1
	for (int i = 0; i < radios.size(); i++) {
		if (radios.get(i).getAttribute("class").contains("Mui-checked")) {
			selected = i
			break
		}
	}
	// try to find a different non-disabled radio
	for (int offset = 1; offset <= radios.size(); offset++) {
		int idx = (selected + offset) % radios.size()
		String cls = radios.get(idx).getAttribute("class")
		if (!cls.contains("Mui-disabled")) {
			radios.get(idx).click()
			WebUI.delay(1)
			WebUI.comment("✅ Regraded cell → clicked radio index ${idx} (was ${selected})")
			didRegrade = true
			break
		}
	}
	if (didRegrade) break
}

assert didRegrade : "❌ Could not regrade any RBC cell (all were disabled?)"

// ─────────────────────────────────────────────────────────────────────────────
// STEP 6: OPEN KEBAB MENU → HISTORY
// ─────────────────────────────────────────────────────────────────────────────
// **Adjust the src‐substring below to match your three-dots icon**
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab') or contains(@src,'dots')]]"
)
WebUI.click(kebab)
WebUI.delay(1)

TestObject historyOpt = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//ul[@role='menu']//li//span[normalize-space()='History']"
)
WebUI.click(historyOpt)

// ─────────────────────────────────────────────────────────────────────────────
// STEP 7: WAIT & PRINT LATEST ENTRY
// ─────────────────────────────────────────────────────────────────────────────
TestObject entryLi = new TestObject().addProperty(
	'css', ConditionType.EQUALS,
	"li.css-1ecsk3j"
)
WebUI.waitForElementVisible(entryLi, 10)

List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
String latest  = entries.get(0).getText().trim()
println "→ Latest History Entry:\n$latest"


