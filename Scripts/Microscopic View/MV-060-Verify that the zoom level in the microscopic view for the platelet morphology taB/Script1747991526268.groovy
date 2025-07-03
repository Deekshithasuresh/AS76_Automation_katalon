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
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(
	findTestObject('Report viewer/Page_PBS/input_username_loginId'),
	'adminuserr'
)
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// STEP 4: Click on the Platelets tab
TestObject plateletsTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// STEP 5: Click on the Morphology sub-tab
TestObject morphSubTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[@id='plateleteMorphologyTab']")
WebUI.waitForElementClickable(morphSubTab, 10)
WebUI.click(morphSubTab)

// STEP 6: Click on Microscopic view and wait 150s
TestObject microView = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microView, 10)
WebUI.click(microView)
WebUI.delay(5)

// Verify default 1000 μm
String s0 = fetchScale()
WebUI.verifyMatch(s0, '1000 μm', false, FailureHandling.STOP_ON_FAILURE)
println "Morphology Scale @1000μm: ${s0}"
println "Morphology BASE64 @1000μm: ${snapAndBase64('morph_default.png')}"

// Prepare zoom-in button
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")

// STEP 7: Zoom once → wait 120s, verify 500 μm
WebUI.click(zoomInBtn)
WebUI.delay(5)
String s1 = fetchScale()
WebUI.verifyMatch(s1, '500 μm', false, FailureHandling.STOP_ON_FAILURE)
println "Morphology Scale @500μm: ${s1}"
println "Morphology BASE64 @500μm: ${snapAndBase64('morph_zoom1.png')}"

// STEP 8: Zoom again → wait 120s, verify 200 μm
WebUI.click(zoomInBtn)
WebUI.delay(5)
String s2 = fetchScale()
WebUI.verifyMatch(s2, '200 μm', false, FailureHandling.STOP_ON_FAILURE)
println "Morphology Scale @200μm: ${s2}"
println "Morphology BASE64 @200μm: ${snapAndBase64('morph_zoom2.png')}"

WebUI.comment('✅ Platelets Morphology microscopic-view zoom levels verified successfully.')
