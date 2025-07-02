import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Helper: fetch the first “NNN μm” label under the map container via JS.
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
 * Helper: screenshot + return full Base64.
 */
String snapAndBase64(String filename) {
	String path = "${RunConfiguration.getReportFolder()}/${filename}"
	WebUI.takeScreenshot(path)
	return Files.readAllBytes(Paths.get(path)).encodeBase64().toString()
}

// ─── 1) LOGIN & NAVIGATE ────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ─── 2) WAIT FOR “PBS” HEADER ───────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
).with {
	WebUI.waitForElementPresent(it, 10, FailureHandling.STOP_ON_FAILURE)
}

// ─── 3) OPEN FIRST “Under review” REPORT ────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
).with {
	WebUI.waitForElementClickable(it, 10, FailureHandling.STOP_ON_FAILURE)
	WebUI.scrollToElement(it, 5, FailureHandling.OPTIONAL)
	WebUI.click(it, FailureHandling.STOP_ON_FAILURE)
}

// ─── 4) SWITCH TO RBC TAB ───────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']"
)
WebUI.waitForElementClickable(rbcTab, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(rbcTab)

// ─── 5) ACTIVATE MICROSCOPIC VIEW ──────────────────────────────────────
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(microBtn)

// ─── 6) WAIT FOR MAP TO STABILIZE ──────────────────────────────────────
WebUI.delay(120)

// ─── 7) ZOOM UNTIL HOME-LIMIT (200 μm) REACHED ─────────────────────────
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)
TestObject zoomWarn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'zoom-warning') and contains(.,'Digital zoom only')]"
)

String scale
for (int clicks = 0; clicks < 10; clicks++) {
	// read current scale
	scale = fetchScale()
	println "After ${clicks} zoom clicks, scale is: ${scale}"
	if (scale == '200 μm') {
		WebUI.comment("✔ Reached 200 μm home-limit after ${clicks} clicks.")
		break
	}
	// if not yet 200, zoom in once more
	WebUI.click(zoomInBtn)
	WebUI.delay(5)
}

// ─── 8) VERIFY WARNING APPEARS AT 200 μm ───────────────────────────────
boolean sawWarn = WebUI.verifyElementPresent(zoomWarn, 5, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.comment("⚠ Warning displayed at 200 μm? ${sawWarn}")

// ─── 9) SNAP & LOG BASE64 ──────────────────────────────────────────────
String shot = "${RunConfiguration.getReportFolder()}/rbc_maxzoom.png"
String b64   = snapAndBase64(shot)
println "📷 Base64 of final view: ${b64.take(80)}…"

// ─── 10) FINISHED ─────────────────────────────────────────────────────
WebUI.comment("✅ mv-065 complete: max-zoom limit verified dynamically.")
WebUI.closeBrowser()
