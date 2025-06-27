import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Fetches the first exact “NNN μm” label under the map container via JavaScript.
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
 * Takes a screenshot and returns its Base64 encoding.
 */
String snapAndBase64(String filename) {
	String path = "${RunConfiguration.getReportFolder()}/${filename}"
	WebUI.takeScreenshot(path)
	return Files.readAllBytes(Paths.get(path)).encodeBase64().toString()
}

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN + NAVIGATE
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login', FailureHandling.STOP_ON_FAILURE)
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr', FailureHandling.STOP_ON_FAILURE)
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==', FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'), FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 2) LAND ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]").with {
	WebUI.waitForElementPresent(it, 10, FailureHandling.STOP_ON_FAILURE)
}

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']])[1]"
).with {
	WebUI.waitForElementClickable(it, 10, FailureHandling.OPTIONAL)
	WebUI.scrollToElement(it, 5, FailureHandling.OPTIONAL)
	WebUI.click(it, FailureHandling.OPTIONAL)
}

// ────────────────────────────────────────────────────────────────────
// 4) PLATELETS → MICROSCOPE
// ────────────────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
).with { WebUI.click(it, FailureHandling.OPTIONAL) }

new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
).with {
	WebUI.waitForElementClickable(it, 10, FailureHandling.OPTIONAL)
	WebUI.click(it, FailureHandling.OPTIONAL)
}

// let the viewer render
WebUI.delay(5)

// ────────────────────────────────────────────────────────────────────
// 5) PREPARE ZOOM & WARNING LOCATORS
// ────────────────────────────────────────────────────────────────────
def zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
def zoomWarning = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(normalize-space(.),'Digital zoom only')]")

List<String> scales = ['500 μm','200 μm','100 μm','50 μm','20 μm','10 μm']

// ────────────────────────────────────────────────────────────────────
// 6) ZOOM THROUGH SCALES
// ────────────────────────────────────────────────────────────────────
for (String expected : scales) {
	WebUI.click(zoomInBtn, FailureHandling.OPTIONAL)
	WebUI.delay(5)

	String actual = fetchScale()
	boolean matched = WebUI.verifyMatch(actual, expected, false, FailureHandling.CONTINUE_ON_FAILURE)
	println "✔ Scale: expected=${expected}, actual=${actual}, matched=${matched}"

	String b64 = snapAndBase64("platelet_${expected.replaceAll('\\s+','')}.png")
	println "Captured Base64 @${expected}: ${b64.take(60)}…"
}

// ────────────────────────────────────────────────────────────────────
// 7) AFTER 10 μm: WARNINGS & FINAL ZOOM
// ────────────────────────────────────────────────────────────────────
boolean warningAt10 = false
try {
	// wait up to 5s, but don’t fail if absent
	warningAt10 = WebUI.waitForElementPresent(zoomWarning, 5, FailureHandling.OPTIONAL)
} catch (Exception ignored) { /* no-op */ }
println "⚠ Warning at 10 μm shown? ${warningAt10}"

WebUI.click(zoomInBtn, FailureHandling.OPTIONAL)
WebUI.delay(5)

String finalScale = fetchScale()
boolean matched5 = WebUI.verifyMatch(finalScale, '5 μm', false, FailureHandling.CONTINUE_ON_FAILURE)
println "✔ Final zoom: expected=5 μm, actual=${finalScale}, matched=${matched5}"

String b64_5 = snapAndBase64('platelet_5um.png')
println "Captured Base64 @5 μm: ${b64_5.take(60)}…"

boolean warningAt5 = false
try {
	warningAt5 = WebUI.waitForElementPresent(zoomWarning, 5, FailureHandling.OPTIONAL)
} catch (Exception ignored) { /* no-op */ }
println "⚠ Warning at 5 μm shown? ${warningAt5}"

// ────────────────────────────────────────────────────────────────────
WebUI.comment("✴ Completed Platelets max-zoom verification (no hard failures).")


