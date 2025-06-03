import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Fetches the first “NNN μm” text anywhere in the DOM using XPath.
 * Returns empty string if none found.
 */
String fetchScale() {
	String script = """
      var xp = \"//*[contains(normalize-space(text()), 'μm')]\";
	  var res = document.evaluate(xp, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
	  for (var i = 0; i < res.snapshotLength; i++) {
		var t = res.snapshotItem(i).textContent.trim();
		if (/\\d+\\s*μm/.test(t)) return t;
	  }
	  return '';
	"""
	return WebUI.executeJavaScript(script, null) as String
}

// --- STEPS 1-4: login & navigate to RBC → Microscopic view  ---
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					  'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

WebUI.waitForElementPresent(
  new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
  10
)
TestObject toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS,
  "//span[normalize-space()='To be reviewed']")
TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
  "//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
  WebUI.scrollToElement(toBe, 5); WebUI.click(toBe)
} else {
  WebUI.click(under)
}

WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
  "//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']"))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
  "//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))

// give OpenLayers a bit of time to render
WebUI.delay(5)

// --- STEP 5: Poll until we see “1000 μm” for the default scale ---
String scale0 = ''
int tries = 0
while ((scale0 == null || scale0.isEmpty()) && tries < 20) {
	WebUI.delay(1)
	scale0 = fetchScale()
	tries++
}
WebUI.comment("Fetched default scale='${scale0}' after ${tries} attempts")
WebUI.verifyMatch(scale0, "1000 μm", false, FailureHandling.STOP_ON_FAILURE)

String outDir = RunConfiguration.getReportFolder()
String shot0  = "${outDir}/rbc_micro_default.png"
WebUI.takeScreenshot(shot0)

// --- STEP 6: Zoom in and poll until it changes to “500 μm” ---
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
  "//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")

WebUI.waitForElementClickable(zoomInBtn, 10)
WebUI.click(zoomInBtn)

String scale1 = ''
tries = 0
while ((scale1 == null || scale1.isEmpty() || scale1 == scale0) && tries < 20) {
	WebUI.delay(1)
	scale1 = fetchScale()
	tries++
}
WebUI.comment("Fetched zoomed scale='${scale1}' after ${tries} attempts")
WebUI.verifyMatch(scale1, "500 μm", false, FailureHandling.STOP_ON_FAILURE)

String shot1 = "${outDir}/rbc_micro_zoomed_500.png"
WebUI.takeScreenshot(shot1)

// --- STEP 7: Ensure the two screenshots differ ---
String b0 = Files.readAllBytes(Paths.get(shot0)).encodeBase64().toString()
String b1 = Files.readAllBytes(Paths.get(shot1)).encodeBase64().toString()
assert b0 != b1 : "Map did NOT change after zoom—something’s wrong!"

WebUI.comment(" RBC microscopic-view zoom & screenshot verification PASSED!")
