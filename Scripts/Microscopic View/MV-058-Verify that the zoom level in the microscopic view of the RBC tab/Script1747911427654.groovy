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
	WebUI.delay(1)  // let DOM settle
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

// ────────────────────────────────────────────────────────────────────
// STEP 1: Login to PBS
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// STEP 2: Verify PBS header present
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(normalize-space(.),'PBS')]"
).with { WebUI.verifyElementPresent(it, 10) }

// STEP 3: Open any “To be reviewed” or “Under review” report
TestObject toBe  = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']")
TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.scrollToElement(toBe, 5); WebUI.click(toBe)
} else {
	WebUI.scrollToElement(under, 5); WebUI.click(under)
}

// STEP 4: Switch to RBC → Microscopic view
TestObject rbcTab   = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']")
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(rbcTab, 10);  WebUI.click(rbcTab)
WebUI.waitForElementClickable(microBtn, 10); WebUI.click(microBtn)

// STEP 5: Wait 150s for full load, then capture default (1000 μm)
WebUI.delay(150)
String scale0 = fetchScale()
WebUI.verifyMatch(scale0, '1000 μm', false, FailureHandling.STOP_ON_FAILURE)
println "RBC Scale @1000μm: ${scale0}"
println "RBC BASE64 @1000μm: ${snapAndBase64('rbc_default.png')}"

// Prepare zoom-in button
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")

// STEP 6: Zoom once → wait 120s, verify 500 μm
WebUI.click(zoomInBtn)
WebUI.delay(120)
String scale1 = fetchScale()
WebUI.verifyMatch(scale1, '500 μm', false, FailureHandling.STOP_ON_FAILURE)
println "RBC Scale @500μm: ${scale1}"
println "RBC BASE64 @500μm: ${snapAndBase64('rbc_zoom1.png')}"

// STEP 7: Zoom again → wait 120s, verify 200 μm
WebUI.click(zoomInBtn)
WebUI.delay(120)
String scale2 = fetchScale()
WebUI.verifyMatch(scale2, '200 μm', false, FailureHandling.STOP_ON_FAILURE)
println "RBC Scale @200μm: ${scale2}"
println "RBC BASE64 @200μm: ${snapAndBase64('rbc_zoom2.png')}"

WebUI.comment('✅ All RBC microscopic-view zoom levels verified successfully.')
