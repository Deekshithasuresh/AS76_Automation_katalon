import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ───── STEP 1: Login & open first “To be reviewed”/“Under review” ─────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					   'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// wait for PBS list
new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]").with {
	WebUI.waitForElementPresent(it, 10)
}

// helper to open a status
def openStatus = { String status ->
	def to = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//span[normalize-space()='$status']")
	if (WebUI.waitForElementPresent(to, 5, FailureHandling.OPTIONAL)) {
		WebUI.scrollToElement(to, 3)
		WebUI.click(to)
		return true
	}
	return false
}

if (!openStatus('To be reviewed') && !openStatus('Under review')) {
	WebUI.comment "❌ No report in ‘To be reviewed’/‘Under review’ – stopping."
	WebUI.closeBrowser()
	return
}

// ───── STEP 2: Click RBC tab & Microscopic view ─────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
}

new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10)
	WebUI.click(it)
	WebUI.delay(2)
}

// ───── STEP 3: Verify right‐side tools ─────
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

// ───── STEP 4: Verify left‐side rows and order ─────
List<String> expected = ['Microcytes','Macrocytes']

// find all rendered cell-rows under RBC microscopic left pane
TestObject rowsTO = new TestObject().addProperty('xpath', ConditionType.EQUALS,
  "//div[contains(@class,'rbc-microscopic-left-pane')]//div[contains(@class,'rbc-cell-body')]//div[contains(@class,'cell-row')]"
)
List<WebElement> rows = WebUiCommonHelper.findWebElements(rowsTO, 10)

// extract their labels
List<String> actualLabels = rows.collect { it.getText().trim() }
WebUI.comment "🔎 Rendered size‐rows: ${actualLabels}"

// check presence
expected.each { name ->
	if (actualLabels.contains(name)) {
		WebUI.comment "✔ Found '$name'"
	} else {
		WebUI.comment "⚠ Missing '$name'"
	}
}

// if both present, verify order
if (actualLabels.containsAll(expected)) {
	int idxMicro = actualLabels.indexOf('Microcytes')
	int idxMacro = actualLabels.indexOf('Macrocytes')
	if (idxMicro < idxMacro) {
		WebUI.comment "✔ Order ok: Microcytes (idx $idxMicro) before Macrocytes (idx $idxMacro)"
	} else {
		WebUI.verifyMatch("$idxMicro", "$idxMacro", false,
			FailureHandling.STOP_ON_FAILURE)  // forces a failure here
	}
}

WebUI.comment "✅ Done checking RBC-Size microscopic view."


