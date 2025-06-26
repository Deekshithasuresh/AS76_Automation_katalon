import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN + LAND ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'),'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//span[contains(text(),'PBS')]"),
	10
)

WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow,10)
WebUI.scrollToElement(underReviewRow,5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 3) (RE-)ASSIGN TO admin IF NEEDED
// ────────────────────────────────────────────────────────────────────
TestObject assignedInputTO = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInputTO,5)
String currentAssignee = WebUI.getAttribute(assignedInputTO,'value').trim()
if (!currentAssignee.equalsIgnoreCase('admin')) {
	// open dropdown
	TestObject dropdown = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
	)
	WebUI.click(dropdown)
	// pick admin
	TestObject adminOpt = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//li[normalize-space(text())='admin']"
	)
	WebUI.waitForElementClickable(adminOpt,5)
	WebUI.scrollToElement(adminOpt,5)
	WebUI.click(adminOpt)
	// click Re-assign
	TestObject reassignBtn = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//button[normalize-space(text())='Re-assign']"
	)
	WebUI.waitForElementClickable(reassignBtn,5)
	WebUI.click(reassignBtn)
	WebUI.delay(2)
	WebUI.comment("✔ Re-assigned to admin")
} else {
	WebUI.comment("ℹ️ Already assigned to admin")
}

// ────────────────────────────────────────────────────────────────────
// 4) SWITCH TO WBC TAB
// ────────────────────────────────────────────────────────────────────
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons')][.//span[normalize-space(text())='WBC']]"
)
WebUI.waitForElementClickable(wbcTab,10)
WebUI.click(wbcTab)

// ────────────────────────────────────────────────────────────────────
// 5) RE-CLASSIFY PATCHES
// ────────────────────────────────────────────────────────────────────
// collect all patch elements
List<WebElement> patches = driver.findElements(
	By.cssSelector("div.patches-container .patch-item")
)
assert patches.size()>0 : "❌ No WBC patches found"

// 5a) Single patch
WebElement firstPatch = patches[0]
firstPatch.click()
WebUI.comment("✔ Selected patch #1")
actions.contextClick(firstPatch).perform()
WebUI.comment("→ Opened context menu")
// click “Classify”
new TestObject().addProperty('xpath',ConditionType.EQUALS,
	"//li[contains(@class,'wbc-option')]/span[normalize-space(text())='Classify']"
).with {
	WebUI.waitForElementClickable(it,5)
	WebUI.click(it)
}
// wait for the category list to appear
WebUI.delay(1)
// grab all classification options
List<WebElement> categories = driver.findElements(
	By.xpath("//ul[@role='menu']//li[not(contains(@class,'wbc-option'))]")
)
assert categories.size()>0 : "❌ No classification options!"
String chosenCat = categories[0].getText().trim()
categories[0].click()
WebUI.comment("✅ Reclassified single patch → ${chosenCat}")
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath',ConditionType.EQUALS,
		"//*[contains(text(),'reclassified') and contains(.,'${chosenCat}')]"
	),5
)

// 5b) Multiple patches (up to 4)
int multiCount = Math.min(4, patches.size())
for(int i=1; i<multiCount; i++){
	patches[i].click()
	WebUI.comment("✔ Selected patch #${i+1}")
	WebUI.delay(0.2)
}
actions.contextClick(patches[1]).perform()
WebUI.comment("→ Opened context menu for multiple")
WebUI.scrollToElement(
	new TestObject().addProperty('xpath',ConditionType.EQUALS,
		"//li[contains(@class,'wbc-option')]/span[normalize-space(text())='Classify']"
	),5
)
WebUI.click(new TestObject().addProperty('xpath',ConditionType.EQUALS,
	"//li[contains(@class,'wbc-option')]/span[normalize-space(text())='Classify']"
))
WebUI.delay(1)
WebUI.click(new TestObject().addProperty('xpath',ConditionType.EQUALS,
	"//ul[@role='menu']//li[normalize-space(text())='${chosenCat}']"
))
WebUI.comment("✅ Reclassified ${multiCount} patches → ${chosenCat}")
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath',ConditionType.EQUALS,
		"//*[contains(text(),'patches reclassified') and contains(.,'${chosenCat}')]"
	),5
)

// ────────────────────────────────────────────────────────────────────
// 6) OPEN HISTORY & VERIFY
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath',ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
))
WebUI.click(new TestObject().addProperty('xpath',ConditionType.EQUALS,
	"//li[.//span[normalize-space(text())='History']]"
))
WebUI.delay(1)

// grab the top history entry
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
assert entries.size()>0 : "❌ No history entries"
String text0 = entries[0].getText().trim()
WebUI.comment("✔ History #1: ${text0}")

// ensure the chosen category appears in that entry
assert text0.contains(chosenCat) : "❌ History did not mention '${chosenCat}'"

WebUI.comment("✅ All done — reclassification and history verified!")
