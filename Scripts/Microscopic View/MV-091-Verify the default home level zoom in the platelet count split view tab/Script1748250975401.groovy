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

CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')


// 4) SWITCH TO PLATELETS TAB
TestObject plateletsTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(plateletsTab)

// 5) VERIFY “Count” SUB-TAB IS ACTIVE
TestObject countTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteCountTab' and @aria-selected='true']"
)
WebUI.waitForElementVisible(countTab, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ ‘Count’ sub-tab is active by default.")

// 6) ACTIVATE SPLIT VIEW & WAIT
TestObject splitBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Split view' and @aria-label='Split view']"
)
WebUI.waitForElementClickable(splitBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(splitBtn)
WebUI.delay(5)   
// 7) VERIFY DEFAULT SCALE “50 μm” & SCREENSHOT
String actual = fetchScale()
WebUI.verifyMatch(actual, '50 μm', false, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Platelets split-view default scale = ${actual}")

String shot = "${RunConfiguration.getReportFolder()}/platelets_split_default_scale.png"
WebUI.takeScreenshot(shot)
println "Saved screenshot: ${shot}"

// 8) DONE
WebUI.comment("✅ MV-091 passed: default home-level zoom verified.")
WebUI.closeBrowser()
