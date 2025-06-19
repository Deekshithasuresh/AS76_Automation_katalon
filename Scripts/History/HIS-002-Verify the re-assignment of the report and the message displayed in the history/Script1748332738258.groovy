import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

// ──────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ──────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					  'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ──────────────────────────────────────────────────────────────────────
// 2) LOCATE & OPEN AN “Under review” REPORT WHOSE ASSIGNEE ≠ admin
// ──────────────────────────────────────────────────────────────────────
// We’ll click each “Under review” in turn until we open one whose assigned_to ≠ "admin"
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> underElems = driver.findElements(
	By.xpath("//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
)
boolean opened = false

for (int i = 0; i < underElems.size(); i++) {
	WebElement elem = underElems[i]
	// scroll into view & click it
	WebUI.scrollToElement(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"(//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review'])[${i+1}]"), 5)
	elem.click()
	// wait for the details panel to load
	WebUI.delay(2)
	// read the assigned_to field
	TestObject assignedInput = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']")
	WebUI.waitForElementVisible(assignedInput, 5)
	String current = WebUI.getAttribute(assignedInput, 'value').trim()
	if (current.toLowerCase() != 'admin') {
		opened = true
		WebUI.comment("✔ Opened Under review report #${i+1} assigned to '${current}'")
		break
	}
	WebUI.comment("⚠ Report #${i+1} already assigned to admin, trying next…")
	WebUI.back()
	WebUI.delay(1)
}

if (!opened) {
	WebUI.comment("❌ No Under review report found whose assignee ≠ admin.")
	
	return
}

// ──────────────────────────────────────────────────────────────────────
// 3) RE-ASSIGN IT TO admin
// ──────────────────────────────────────────────────────────────────────
// click the dropdown trigger
TestObject dropdownBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button")
WebUI.click(dropdownBtn)
// pick “admin” from the list
TestObject adminOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//li[normalize-space(text())='admin']")
WebUI.waitForElementClickable(adminOpt, 5)
WebUI.click(adminOpt)
// confirm via the Re-assign button
TestObject reassignBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Re-assign']")
WebUI.waitForElementClickable(reassignBtn, 5)
WebUI.click(reassignBtn)
WebUI.comment("✔ Clicked Re-assign → now waiting for Approve button…")

// ──────────────────────────────────────────────────────────────────────
// 4) VERIFY “Approve report” APPEARS 
// ──────────────────────────────────────────────────────────────────────
TestObject approveBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'tick-active')]]")
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment("✔ 'Approve report' button is visible")


// ──────────────────────────────────────────────────────────────────────
// 5) OPEN KEBAB (⋮) MENU & CHOOSE “History”
// ──────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu')]]")
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

// click “History”
TestObject historyOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//li[contains(normalize-space(.),'History')]")
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)
WebUI.delay(30) 

// ──────────────────────────────────────────────────────────────────────
// 6) READ & PRINT THE FIRST HISTORY ENTRY
// ──────────────────────────────────────────────────────────────────────
// usually the first <li> under the popover list is the latest entry
TestObject firstEntry = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//ul[contains(@class,'appBar_popover_list')]//li)[1]")
if (WebUI.verifyElementPresent(firstEntry, 5, FailureHandling.OPTIONAL)) {
	String entryText = WebUI.getText(firstEntry).trim()
	WebUI.comment("✔ First history entry:\n${entryText}")
} else {
	WebUI.comment("⚠ Could not find any history entries.")
}

WebUI.comment("✅ Done.")

