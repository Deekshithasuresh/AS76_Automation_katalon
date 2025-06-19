import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ──────────────────────────────────────────────────────────
// STEP 1: Login & open first “To be reviewed”/“Under review”
// ──────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					   'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// wait for report-list header
new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]").with {
	WebUI.waitForElementPresent(it, 10)
}

// helper to click a status if present
def openStatus = { String status ->
	TestObject to = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//span[normalize-space()='$status']")
	if (WebUI.waitForElementPresent(to, 3, FailureHandling.OPTIONAL)) {
		WebUI.scrollToElement(to, 3)
		WebUI.click(to)
		WebUI.comment "✔ Opened report with status ‘$status’"
		return true
	}
	return false
}

if (!openStatus('To be reviewed') && !openStatus('Under review')) {
	WebUI.comment "❌ No ‘To be reviewed’ or ‘Under review’ report found"
	WebUI.closeBrowser()
	return
}

// ──────────────────────────────────────────────────────────
// STEP 2: Click RBC → Shape → Microscopic view
// ──────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
}

new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Shape']"
).with {
	WebUI.waitForElementClickable(it, 5)
	WebUI.click(it)
}

new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.delay(2)
}

// ──────────────────────────────────────────────────────────
// STEP 3: Verify right-side controls
// ──────────────────────────────────────────────────────────
[
	"//img[@alt='line-tool']",
	"//img[@alt='circle-tool']",
	"//img[@alt='zoom-tool']",
	"//button[@class='ol-zoom-in'  and @title='Zoom in']",
	"//button[@class='ol-zoom-out' and @title='Zoom out']",
	"//button[@title='Overview']",
	"//div[.//img[@alt='home']]"
].each { xpath ->
	WebUI.verifyElementPresent(
		new TestObject().addProperty('xpath', ConditionType.EQUALS, xpath),
		5, FailureHandling.CONTINUE_ON_FAILURE
	)
}

// ──────────────────────────────────────────────────────────
// STEP 4: Verify left-side Shape table names & order
// ──────────────────────────────────────────────────────────
// the full expected order for the Shape classification
List<String> expected = [
	'Ovalocytes',
	'Elliptocytes',
	'Teardrop Cells',
	'Fragmented Cells',
	'Target Cells',
	'Echinocytes'
]

// grab all rows in that shape table
TestObject rowsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'rbc-microscopic-left-pane')]//table//tbody/tr"
)
List<WebElement> rows = WebUiCommonHelper.findWebElements(rowsTO, 10)
List<String> actualLabels = rows.collect {
	it.findElement(By.xpath(".//td[2]")).getText().trim()
}
WebUI.comment "🔎 Shape rows found: ${actualLabels}"

// now verify presence & order
expected.eachWithIndex { name, idx ->
	if (actualLabels.contains(name)) {
		WebUI.comment "✔ Found '$name'"
		// if previous expected also found, ensure order
		if (idx > 0 && actualLabels.contains(expected[idx-1])) {
			int prevIndex = actualLabels.indexOf(expected[idx-1])
			int thisIndex = actualLabels.indexOf(name)
			WebUI.verifyTrue(
				thisIndex > prevIndex,
				FailureHandling.CONTINUE_ON_FAILURE,
				"❌ '$name' (idx ${thisIndex}) should come after '${expected[idx-1]}' (idx ${prevIndex})"
			)
		}
	} else {
		WebUI.comment "⚠ Missing expected shape '$name'"
	}
}

WebUI.comment "✅ Finished RBC-Shape microscopic view checks."

