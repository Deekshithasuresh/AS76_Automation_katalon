import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Helper: fetch any “NNN μm” label via XPath.
 */
String fetchScale() {
	String script = """
      var xp = "//*[contains(normalize-space(text()), 'μm')]";
	  var res = document.evaluate(xp, document, null,
					 XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
	  for (var i = 0; i < res.snapshotLength; i++) {
		var t = res.snapshotItem(i).textContent.trim();
		if (/\\d+\\s*μm/.test(t)) return t;
	  }
	  return '';
	"""
	return WebUI.executeJavaScript(script, null) as String
}

/ ────────────────────────────────────────────────────────────────────
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

// ----------------- STEP 3: Switch to Platelets → Microscopic -----------------
WebUI.comment("🔄 Testing Platelets microscopic‐view zoom…")

TestObject plateletsTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons') and contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)

// give OpenLayers a moment to render
WebUI.delay(5)

// ----------------- STEP 4: Prepare for screenshots -----------------
String outDir    = RunConfiguration.getReportFolder()
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")

// ----------------- STEP 5: Capture DEFAULT Platelets scale -----------------
String plateScale0 = ''
for (int i = 0; i < 20; i++) {
	WebUI.delay(1)
	plateScale0 = fetchScale()
	if (plateScale0) break
}
WebUI.comment("✔ Platelets default scale = ${plateScale0}")
// assert it's *some* μm value
WebUI.verifyMatch(plateScale0, "\\d+\\s*μm", true, FailureHandling.STOP_ON_FAILURE)

String plateShot0 = "${outDir}/platelets_micro_default.png"
WebUI.takeScreenshot(plateShot0)

// ----------------- STEP 6: Zoom in Platelets → Capture new scale -----------------
WebUI.waitForElementClickable(zoomInBtn, 10)
WebUI.click(zoomInBtn)

String plateScale1 = ''
for (int i = 0; i < 20; i++) {
	WebUI.delay(1)
	String cand = fetchScale()
	if (cand && cand != plateScale0) {
		plateScale1 = cand
		break
	}
}
WebUI.comment("✔ Platelets zoomed scale = ${plateScale1}")
// assert it's *some* μm and different from before
WebUI.verifyMatch(plateScale1, "\\d+\\s*μm", true, FailureHandling.STOP_ON_FAILURE)
assert plateScale1 != plateScale0 : " Platelets scale did NOT change after zoom!"

String plateShot1 = "${outDir}/platelets_micro_zoomed.png"
WebUI.takeScreenshot(plateShot1)

// ----------------- STEP 7: Sanity‐check Platelets screenshots changed -----------------
String bP0 = Files.readAllBytes(Paths.get(plateShot0)).encodeBase64().toString()
String bP1 = Files.readAllBytes(Paths.get(plateShot1)).encodeBase64().toString()
assert bP0 != bP1 : " Platelets map did NOT change after zoom!"
WebUI.comment(" Platelets microscopic-view zoom & screenshot verification PASSED!")
