import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
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

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
).with {
	WebUI.waitForElementPresent(it, 10, FailureHandling.STOP_ON_FAILURE)
}

// 3) OPEN FIRST “Under review” REPORT
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
).with {
	WebUI.waitForElementClickable(it, 10, FailureHandling.STOP_ON_FAILURE)
	WebUI.scrollToElement(it, 5, FailureHandling.OPTIONAL)
	WebUI.click(it, FailureHandling.STOP_ON_FAILURE)
}

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
WebUI.delay(120)    // enough time for the tiled map to load

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
