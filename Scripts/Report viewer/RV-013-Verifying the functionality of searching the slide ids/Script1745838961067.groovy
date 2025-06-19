import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.Keys

// ────────── STEP 1: Login ──────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// user/pass fields and button (using your existing repository IDs)
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// wait for PBS header
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────── STEP 2: Dynamic locators ──────────
// Search input
TestObject searchBox = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'searchBarComponent_search-input__')]//input[@placeholder='Search']"
)
// Suggestion items in the pop-over
TestObject suggestionItem = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'searchBarComponent_search-popover-list__')]//div[normalize-space()!='']"
)
// Rows matching the filtered table (for the six-pattern test)
TestObject patternRows = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//tbody[contains(@class,'MuiTableBody-root')]/tr"
)
// All rows (for clear-search expansion test)
TestObject allRows = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//tbody//tr"
)

// Get the first Slide-ID (column 3) so we have a base string to slice
List<WebElement> firstCell = WebUiCommonHelper.findWebElements(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//tbody//tr[1]/td[3]"),
	5
)
if (firstCell.isEmpty()) {
	WebUI.comment("❌ No Slide-ID present to test.")
	WebUI.closeBrowser()
	return
}
String fullSlideId = firstCell[0].getText().trim()

// ────────── STEP 3: Six-pattern search checks ──────────
Map<String, String> testCases = [
	"First 2 chars" : fullSlideId.take(2),
	"Last 1 char"   : fullSlideId[-1],
	"Last 2 chars"  : fullSlideId.takeRight(2),
	"First 1 char"  : fullSlideId[0],
	"Special char"  : "@#&S",
	"Invalid char"  : "ZZZZZZ"
]

testCases.each { desc, input ->
	WebUI.comment("🔍 Testing ${desc} with '${input}'")

	// clear & type
	WebUI.waitForElementVisible(searchBox, 5)
	WebUI.clearText(searchBox)
	WebUI.setText(searchBox, input)
	WebUI.sendKeys(searchBox, Keys.ENTER)
	WebUI.delay(2)

	// collect filtered rows
	List<WebElement> results = WebUiCommonHelper.findWebElements(patternRows, 5)

	if (desc.contains("Invalid") || desc.contains("Special")) {
		if (results.isEmpty()) {
			WebUI.comment("✅ [${desc}] no results as expected.")
		} else {
			WebUI.comment("❌ [${desc}] expected 0, found ${results.size()}.")
		}
	} else {
		boolean ok = results.any { row ->
			row.findElement(By.xpath(".//td[3]")).getText().contains(input)
		}
		if (ok) {
			WebUI.comment("✅ [${desc}] contains '${input}'.")
		} else {
			WebUI.comment("❌ [${desc}] missing '${input}'.")
		}
	}

	// reset back to all
	WebUI.clearText(searchBox)
	WebUI.sendKeys(searchBox, Keys.ENTER)
	WebUI.delay(1)
}

// ────────── STEP 4: Clear-search expands list ──────────
WebUI.comment("🔄 Verifying clear-search expands results")

// apply a simple 2-char filter
WebUI.clearText(searchBox)
WebUI.setText(searchBox, fullSlideId.take(2))
WebUI.sendKeys(searchBox, Keys.ENTER)
WebUI.delay(2)
List<WebElement> filtered = WebUiCommonHelper.findWebElements(allRows, 5)
WebUI.comment("🔹 Filtered → ${filtered.size()} rows")

// now clear it
WebUI.clearText(searchBox)
WebUI.sendKeys(searchBox, Keys.ENTER)
WebUI.delay(2)
List<WebElement> reset = WebUiCommonHelper.findWebElements(allRows, 5)
if (reset.size() > filtered.size()) {
	WebUI.comment("✅ Clear-search reset to ${reset.size()} rows")
} else {
	WebUI.comment("❌ Clear-search did not expand (still ${reset.size()})")
}


