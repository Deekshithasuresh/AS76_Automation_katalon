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

CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

// STEP 4: Switch to WBC → Microscopic view
TestObject wbcTab   = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']")
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(wbcTab, 10);  WebUI.click(wbcTab)
WebUI.waitForElementClickable(microBtn, 10); WebUI.click(microBtn)

// STEP 5: Wait 150s for full load, then capture default
WebUI.delay(5)
String scale0 = fetchScale()
WebUI.verifyMatch(scale0, '1000 μm', false, FailureHandling.STOP_ON_FAILURE)
println "Scale @1000μm: ${scale0}"
println "BASE64 @1000μm: ${snapAndBase64('wbc_default.png')}"

// Prepare zoom-in button
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")

// STEP 6: Zoom once → wait 120s, verify 500 μm
WebUI.click(zoomInBtn)
WebUI.delay(5)
String scale1 = fetchScale()
WebUI.verifyMatch(scale1, '500 μm', false, FailureHandling.STOP_ON_FAILURE)
println "Scale @500μm: ${scale1}"
println "BASE64 @500μm: ${snapAndBase64('wbc_zoom1.png')}"

// STEP 7: Zoom again → wait 120s, verify 200 μm
WebUI.click(zoomInBtn)
WebUI.delay(5)
String scale2 = fetchScale()
WebUI.verifyMatch(scale2, '200 μm', false, FailureHandling.STOP_ON_FAILURE)
println "Scale @200μm: ${scale2}"
println "BASE64 @200μm: ${snapAndBase64('wbc_zoom2.png')}"

WebUI.comment('All microscopic-view zoom levels verified successfully.')
