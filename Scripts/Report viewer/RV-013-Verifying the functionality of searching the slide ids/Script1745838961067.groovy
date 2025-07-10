import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.Keys
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) WAIT FOR PBS HEADER
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// 3) LOCATORS & SLIDE-ID EXTRACTION
TestObject searchBox = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'searchBarComponent_search-input__')]//input[@placeholder='Search']"
)
TestObject suggestionItem = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'searchBarComponent_search-popover-list__')]//div[normalize-space()!='']"
)
TestObject patternRows = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//tbody[contains(@class,'MuiTableBody-root')]/tr"
)
TestObject allRows = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//tbody//tr"
)

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

// 4) SEARCH-BOX TESTS
Map<String, String> testCases = [
	"First 2 chars": fullSlideId.take(2),
	"Last 1 char"  : fullSlideId[-1],
	"Last 2 chars" : fullSlideId.takeRight(2),
	"First 1 char" : fullSlideId[0],
	"Special char" : "@#&S",
	"Invalid char" : "ZZZZZZ"
]

testCases.each { desc, input ->
	WebUI.comment("🔍 Testing ${desc} with '${input}'")

	// clear, type, press Enter via Keys.chord(...)
	WebUI.waitForElementVisible(searchBox, 5)
	WebUI.clearText(searchBox)
	WebUI.setText(searchBox, input)
	WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
	WebUI.delay(2)

	List<WebElement> results = WebUiCommonHelper.findWebElements(patternRows, 5)
	if (desc.contains("Invalid") || desc.contains("Special")) {
		WebUI.comment(results.isEmpty()
			? "✅ [${desc}] no results as expected."
			: "❌ [${desc}] expected 0, found ${results.size()}.")
	} else {
		boolean ok = results.any { row ->
			row.findElement(By.xpath(".//td[3]")).getText().contains(input)
		}
		WebUI.comment(ok
			? "✅ [${desc}] contains '${input}'."
			: "❌ [${desc}] missing '${input}'.")
	}

	// reset to all
	WebUI.clearText(searchBox)
	WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
	WebUI.delay(1)
}

// 5) CLEAR-SEARCH EXPANSION TEST
WebUI.comment("🔄 Verifying clear-search expands results")
WebUI.clearText(searchBox)
WebUI.setText(searchBox, fullSlideId.take(2))
WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
WebUI.delay(2)
List<WebElement> filtered = WebUiCommonHelper.findWebElements(allRows, 5)
WebUI.comment("🔹 Filtered → ${filtered.size()} rows")

WebUI.clearText(searchBox)
WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
WebUI.delay(2)
List<WebElement> reset = WebUiCommonHelper.findWebElements(allRows, 5)
if (reset.size() > filtered.size()) {
	WebUI.comment("✅ Clear-search reset to ${reset.size()} rows")
} else {
	WebUI.comment("❌ Clear-search did not expand (still ${reset.size()})")
}
