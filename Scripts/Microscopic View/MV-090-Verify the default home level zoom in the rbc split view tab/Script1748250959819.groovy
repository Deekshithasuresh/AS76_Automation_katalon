import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/**
 * Helper: grab the first “NNN μm” label under the map container via JS.
 */
String fetchScale() {
	WebUI.delay(1)  // let the DOM settle
	String js = '''
      for (const el of document.querySelectorAll('#pbs-volumeViewport *')) {
        const t = el.textContent.trim();
        if (/^[0-9]+\\s*μm$/.test(t)) return t;
      }
      return '';
    '''
	return WebUI.executeJavaScript(js, null) as String
}

// ────────────────────────────────────────────────────────────────────
// STEP 1: Login to PBS
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
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

// STEP 3: Click on the RBC tab
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']")
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)

// STEP 4: Switch to Split view & wait 120s
TestObject splitBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Split view' and @aria-label='Split view']")
WebUI.waitForElementClickable(splitBtn, 10)
WebUI.click(splitBtn)
WebUI.delay(120)

// STEP 5: Verify default scale “10 μm” and take screenshot
String scale = fetchScale()
WebUI.verifyMatch(scale, '10 μm', false, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ RBC split view default scale = ${scale}")

String screenshotPath = "${RunConfiguration.getReportFolder()}/rbc_split_default_scale.png"
WebUI.takeScreenshot(screenshotPath)
println "Screenshot saved: ${screenshotPath}"
