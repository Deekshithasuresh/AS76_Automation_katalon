import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Fetches the first exact “NNN μm” label under the map container via JS.
 */
String fetchScale() {
	WebUI.delay(1)
	String js = '''
      for (const el of document.querySelectorAll('#pbs-volumeViewport *')) {
        const t = el.textContent.trim();
        if (/^[0-9]+\\s*μm$/.test(t)) return t;
      }
      return '';
    '''
	return WebUI.executeJavaScript(js, null) as String
}

/**
 * Takes a screenshot and returns the full Base64 string.
 */
String snapAndBase64(String filename) {
	String path = "${RunConfiguration.getReportFolder()}/${filename}"
	WebUI.takeScreenshot(path)
	return Files.readAllBytes(Paths.get(path)).encodeBase64().toString()
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

// STEP 3: Click on WBC tab
def wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']")
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// STEP 4: Click on Microscopic view
def microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)

// STEP 5: Wait for initial load
WebUI.delay(120)

// prepare zoom-in and warning locators
def zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
def zoomWarning = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'zoom-warning') and contains(.,'Digital zoom only')]")

// scales to verify
List<String> scales = ['500 μm','200 μm','100 μm','50 μm','20 μm','10 μm']

// STEP 6–11: zoom through scales without stopping on failure
for (String expected : scales) {
	try {
		WebUI.click(zoomInBtn)
		WebUI.delay(120)
		String actual = fetchScale()
		boolean matched = WebUI.verifyMatch(actual, expected, false, FailureHandling.CONTINUE_ON_FAILURE)
		println "Expected: ${expected}, Actual: ${actual}, Matched: ${matched}"
		String b64 = snapAndBase64("wbc_${expected.replaceAll(' ','')}.png")
		println "BASE64 @${expected}: ${b64}"
	} catch (Exception e) {
		println "Error verifying scale ${expected}: ${e.message}"
	}
}

// after 10 μm, check warning then zoom to 5 μm
try {
	// check warning at 10 μm
	boolean warningShown10 = WebUI.verifyElementPresent(zoomWarning, 5, FailureHandling.CONTINUE_ON_FAILURE)
	println "Warning at 10 μm shown: ${warningShown10}"
	// final zoom to 5 μm
	WebUI.click(zoomInBtn)
	WebUI.delay(120)
	String actual5 = fetchScale()
	boolean matched5 = WebUI.verifyMatch(actual5, '5 μm', false, FailureHandling.CONTINUE_ON_FAILURE)
	println "Expected: 5 μm, Actual: ${actual5}, Matched: ${matched5}"
	String b64_5 = snapAndBase64('wbc_5μm.png')
	println "BASE64 @5 μm: ${b64_5}"
	boolean warningShown5 = WebUI.verifyElementPresent(zoomWarning, 5, FailureHandling.CONTINUE_ON_FAILURE)
	println "Warning at 5 μm shown: ${warningShown5}"
} catch (Exception e) {
	println "Error verifying max zoom 5 μm: ${e.message}"
}

WebUI.comment('✴ Completed WBC max-zoom verification (failures logged above).')
