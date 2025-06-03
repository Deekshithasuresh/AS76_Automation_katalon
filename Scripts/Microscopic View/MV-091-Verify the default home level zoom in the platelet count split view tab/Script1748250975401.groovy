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

// STEP 3: Click on the Platelets tab
TestObject pltTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(pltTab, 10)
WebUI.click(pltTab)

// STEP 4: Verify “Count” sub-tab is selected by default
TestObject countTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab' and contains(@class,'selected') and normalize-space()='Count']"
)
WebUI.verifyElementPresent(countTab, 10)
WebUI.comment("✔ ‘Count’ sub-tab is selected by default")

// STEP 5: Switch to Split view & wait 120s
TestObject splitBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Split view' and @aria-label='Split view']"
)
WebUI.waitForElementClickable(splitBtn, 10)
WebUI.click(splitBtn)
WebUI.delay(120)

// STEP 6: Verify default scale “50 μm” and take screenshot
String scale = fetchScale()
WebUI.verifyMatch(scale, '50 μm', false, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Platelets split-view default scale = ${scale}")

String screenshotPath = "${RunConfiguration.getReportFolder()}/platelets_split_default_scale.png"
WebUI.takeScreenshot(screenshotPath)
println "Screenshot saved: ${screenshotPath}"
