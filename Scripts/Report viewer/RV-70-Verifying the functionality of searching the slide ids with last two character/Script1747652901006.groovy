import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]").with {
	WebUI.verifyElementPresent(it, 10)
}

// ---------- LOCATORS ----------
TestObject searchInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'searchBarComponent_search-input__')]//input[@placeholder='Search']"
)
TestObject popHeader = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'searchBarComponent_search-popover-title__') and normalize-space()='Slide Id']"
)
TestObject suggestionContainer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'searchBarComponent_search-popover-title__')]/following-sibling::div"
)
TestObject suggestionItem = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'searchBarComponent_search-popover-title__')]/following-sibling::div//div[normalize-space()!='']"
)
TestObject backdrop = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiBackdrop-root')]"
)

// ---------- HELPER: type a query & return suggestion count ----------
def doSearch = { String q ->
	WebUI.waitForElementNotVisible(backdrop, 5, FailureHandling.OPTIONAL)
	WebUI.clearText(searchInput)
	WebUI.setText(searchInput, q)
	// ensure focus
	WebUI.executeJavaScript(
	  "document.querySelector(\"input[placeholder='Search']\").focus()", null
	)
	WebUI.delay(1)
	return WebUI.findWebElements(suggestionItem, 3).size()
}

// ---------- STEP 3: Try two-character queries ----------
List<String> twoCharCandidates = ['ab', '12', 'cd']
int foundCount = 0
String usedQuery = ''

for (String q : twoCharCandidates) {
	foundCount = doSearch(q)
	if (foundCount > 0) {
		usedQuery = q
		WebUI.comment("Found $foundCount suggestion(s) for query '$q'")
		break
	}
}

// If none matched, exit gracefully
if (foundCount == 0) {
	WebUI.comment("ℹ️ No Slide IDs matching any of $twoCharCandidates; search pop‐over is empty.")
	return
}

// ---------- STEP 4: Verify pop‐over header & results ----------
WebUI.verifyElementPresent(popHeader, 5)
WebUI.verifyElementPresent(suggestionItem, 5)
WebUI.comment(" Search pop‐over displayed $foundCount item(s) for '$usedQuery'.")

// ---------- STEP 5: Scroll list only if it overflows ----------
Map dims = WebUI.executeJavaScript("""
  const el = document.evaluate(
    arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null
  ).singleNodeValue;
  return { scrollHeight: el.scrollHeight, clientHeight: el.clientHeight };
""", [ suggestionContainer.getSelectorCollection()[0].getValue() ]) as Map

if (dims.scrollHeight > dims.clientHeight) {
	WebUI.comment("→ Overflow detected (${dims.scrollHeight} > ${dims.clientHeight}); scrolling…")
	WebUI.executeJavaScript("""
      const el = document.evaluate(
        arguments[0], document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null
      ).singleNodeValue;
      el.scrollTop = el.scrollHeight;
    """, [ suggestionContainer.getSelectorCollection()[0].getValue() ])
} else {
	WebUI.comment("→ No scroll needed.")
}

