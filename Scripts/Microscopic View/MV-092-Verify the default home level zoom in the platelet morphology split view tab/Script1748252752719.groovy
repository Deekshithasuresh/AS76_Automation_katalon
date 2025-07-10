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

CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')


// STEP 3: Click on the Platelets tab
TestObject pltTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(pltTab, 10)
WebUI.click(pltTab)

// STEP 4: Click on the Morphology sub-tab
TestObject morphTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab' and normalize-space()='Morphology']")
WebUI.waitForElementClickable(morphTab, 10)
WebUI.click(morphTab)

// STEP 5: Switch to Split view & wait 120s
TestObject splitBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Split view' and @aria-label='Split view']")
WebUI.waitForElementClickable(splitBtn, 10)
WebUI.click(splitBtn)
WebUI.delay(5)

// STEP 6: Verify default scale “10 μm” and take screenshot
String scale = fetchScale()
WebUI.verifyMatch(scale, '10 μm', false, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Platelets-Morphology split-view default scale = ${scale}")

String screenshotPath = "${RunConfiguration.getReportFolder()}/platelets_morph_split_default_scale.png"
WebUI.takeScreenshot(screenshotPath)
println "Screenshot saved: ${screenshotPath}"
