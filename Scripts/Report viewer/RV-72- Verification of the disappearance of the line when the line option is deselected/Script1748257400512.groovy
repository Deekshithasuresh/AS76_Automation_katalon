import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

/**
 * Returns the PNG DataURL of the main canvas under #pbs-volumeViewport.
 */
String getCanvasBase64() {
	String js = '''
      const c = document.querySelector('#pbs-volumeViewport canvas');
      return c ? c.toDataURL('image/png') : '';
    '''
	return WebUI.executeJavaScript(js, null) as String
}

// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
String underReviewXpath = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, underReviewXpath)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)

WebUI.click(assignedDropdown)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)
// ────────────────────────────────────────────────────────────────────
// 2) SWITCH TO WBC TAB & MICROSCOPIC VIEW
// ────────────────────────────────────────────────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))

// allow full viewer load
WebUI.delay(120)

// ────────────────────────────────────────────────────────────────────
// 3) TOGGLE LINE TOOL & VERIFY CANVAS CHANGE
// ────────────────────────────────────────────────────────────────────
// locator for the line tool button
TestObject lineTool = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='line-tool' and contains(@src,'/icons/line-icon')]"
)

WebUI.waitForElementClickable(lineTool, 30)

// capture canvas before
String before = getCanvasBase64()
WebUI.comment("Canvas snapshot before line-tool click.")

// click to draw green line
WebUI.click(lineTool)
WebUI.delay(2)

// capture after first click
String afterDraw = getCanvasBase64()
WebUI.comment("Canvas snapshot after drawing line.")

// verify that the canvas changed (line appeared)
if (before == afterDraw) {
	WebUI.comment("❌ Line did NOT appear on canvas.")
} else {
	WebUI.comment("✔ Green line appeared.")
}

// click again to remove line
WebUI.click(lineTool)
WebUI.delay(2)

// capture after second click
String afterRemove = getCanvasBase64()
WebUI.comment("Canvas snapshot after removing line.")

// verify that canvas returned to original state
if (afterRemove == before) {
	WebUI.comment("✔ Green line removed; canvas restored.")
} else {
	WebUI.comment("❌ Canvas did not restore to original after removing line.")
}

