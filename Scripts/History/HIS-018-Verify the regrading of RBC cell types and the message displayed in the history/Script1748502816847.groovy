import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ─── STEP 1: LOGIN ──────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ─── STEP 2: VERIFY LANDING ────────────────────────────────────────
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ─── STEP 3: PICK & ASSIGN ─────────────────────────────────────────
TestObject statusToBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']"
)
TestObject statusUnderReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)

// ** UPDATED ASSIGNMENT LOCATORS **
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Assigned to:']/following-sibling::div//button"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Assigned to:']/following-sibling::div//input"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[normalize-space(text())='admin']"
)
TestObject reassignButton = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Re-assign']"
)

if (WebUI.waitForElementPresent(statusToBeReviewed, 3)) {
	WebUI.click(statusToBeReviewed)
	WebUI.click(assignedDropdown)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.click(adminOption)
	WebUI.comment("✅ Assigned a ‘To be reviewed’ to admin.")
} else if (WebUI.waitForElementPresent(statusUnderReview, 3)) {
	WebUI.click(statusUnderReview)
	String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
	if (currentAssignee != 'admin') {
		WebUI.click(assignedDropdown)
		WebUI.waitForElementClickable(adminOption, 5)
		WebUI.click(adminOption)
		WebUI.waitForElementClickable(reassignButton, 5)
		WebUI.click(reassignButton)
		WebUI.comment("✅ Re-assigned an ‘Under review’ to admin.")
	} else {
		WebUI.comment("ℹ️ ‘Under review’ report already assigned to admin.")
	}
} else {
	WebUI.comment("❌ No report in ‘To be reviewed’ or ‘Under review’ status.")
	WebUI.takeScreenshot()
	WebUI.closeBrowser()
	return
}

// ─── STEP 4: WAIT FOR “Approve report” BUTTON ───────────────────────
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)
WebUI.waitForElementVisible(approveBtn, 10)

// ─── STEP 5: CLICK RBC TAB & REGRADE ────────────────────────────────
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and .//span[normalize-space()='RBC']]"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> rows = driver.findElements(
	By.cssSelector("div.rbc-cell-body > div.selected.cell-row")
)

boolean didRegrade = false
for (WebElement row : rows) {
	List<WebElement> radios = row.findElements(
		By.cssSelector("div.grade-div span.MuiRadio-root")
	)
	if (radios.isEmpty()) continue

	int current = -1
	for (int i = 0; i < radios.size(); i++) {
		if (radios.get(i).getAttribute("class").contains("Mui-checked")) {
			current = i
			break
		}
	}
	if (current < 0) continue

	for (int offset = 1; offset < radios.size(); offset++) {
		int idx = (current + offset) % radios.size()
		if (!radios.get(idx).getAttribute("class").contains("Mui-disabled")) {
			radios.get(idx).click()
			WebUI.delay(1)
			WebUI.comment("✅ Regraded row ${rows.indexOf(row)}: ${current} → ${idx}")
			didRegrade = true
			break
		}
	}
	if (didRegrade) break
}
assert didRegrade : "❌ No clickable grade-radio found in any RBC row."

// ─── STEP 6: OPEN HISTORY ──────────────────────────────────────────
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'dots')]]"
)
WebUI.click(kebab)
WebUI.delay(1)

TestObject historyOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//ul[@role='menu']//li//span[normalize-space()='History']"
)
WebUI.click(historyOption)

// ─── STEP 7: VERIFY HISTORY & PRINT ─────────────────────────────────
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
String latest = entries.get(0).getText().trim()
println "→ Latest history entry:\n$latest"
assert latest.startsWith("Regrading") && latest.contains("admin regarded")
