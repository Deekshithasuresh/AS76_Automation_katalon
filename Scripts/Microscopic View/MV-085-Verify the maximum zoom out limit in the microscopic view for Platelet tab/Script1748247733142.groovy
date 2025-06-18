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
 * Takes a screenshot and returns the full Base64 string.
 */
String snapAndBase64(String filename) {
	String path = "${RunConfiguration.getReportFolder()}/${filename}"
	WebUI.takeScreenshot(path)
	return Files.readAllBytes(Paths.get(path)).encodeBase64().toString()
}

// STEP 1: Login to PBS
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// STEP 2: Open any “To be reviewed” or “Under review” report
TestObject toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']")
TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.click(toBe)
} else {
	WebUI.click(under)
}

// STEP 3: Switch to Platelets tab
TestObject pltTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(pltTab, 10)
WebUI.click(pltTab)

// STEP 4: Activate Microscopic view & wait 120s for render
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)
WebUI.delay(120)

// Prepare zoom-in and zoom-out buttons
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
TestObject zoomOutBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-out') and @title='Zoom out']")

// STEP 5: Zoom in 4× with 60s between
WebUI.waitForElementClickable(zoomInBtn, 30)
(1..4).each { i ->
	WebUI.click(zoomInBtn)
	WebUI.delay(60)
	String s = fetchScale()
	println "After zoom-in ${i}: ${s}"
}

// STEP 6: Zoom out 4× with 60s, capture screenshot + Base64 each time
WebUI.waitForElementClickable(zoomOutBtn, 30)
(1..4).each { i ->
	WebUI.click(zoomOutBtn)
	WebUI.delay(60)
	String s = fetchScale()
	println "After zoom-out ${i}: ${s}"
	String b64 = snapAndBase64("plt_zoomOut${i}.png")
	println "BASE64 zoom-out ${i}: ${b64}"
}

// STEP 7: Fifth zoom-out should return to 1000 μm
WebUI.click(zoomOutBtn)
WebUI.delay(60)
String finalScale = fetchScale()
WebUI.verifyMatch(finalScale, '1000 μm', false, FailureHandling.CONTINUE_ON_FAILURE)
println "Final scale (should be 1000 μm): ${finalScale}"
String b64final = snapAndBase64("plt_zoomOut5_1000um.png")
println "BASE64 zoom-out 5 (1000 μm): ${b64final}"

WebUI.comment("✅ Completed Platelets zoom in/out verification.")
