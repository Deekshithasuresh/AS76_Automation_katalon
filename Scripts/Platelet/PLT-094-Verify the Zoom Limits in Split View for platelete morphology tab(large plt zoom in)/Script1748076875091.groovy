import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Files
import java.nio.file.Paths

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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

// STEP 1: Login
WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.maximizeWindow()

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

// STEP 2: Open any “To be reviewed” or “Under review” report
//def toBe  = new TestObject().addProperty('xpath', ConditionType.EQUALS,
//	"//span[normalize-space()='To be reviewed']")
//def under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
//	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
//if (WebUI.waitForElementPresent(toBe, 5)) {
//	WebUI.click(toBe)
//} else {
//	WebUI.click(under)
//}

// Select a 1st row report
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)

// Step 1: Get cell rows
List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Platelets/Page_PBS/morphology _row'),
	10)

int clumpCount = 0

int largePlateletCount = 0

WebElement clumpRow = null

WebElement largePlateletRow = null

for (WebElement row : cell_rows) {
	String cellName = row.findElement(By.xpath('.//div[1]')).getText()

	String countText = row.findElement(By.xpath('.//div[2]')).getText()

	int count = countText.isInteger() ? countText.toInteger() : 0

	if (cellName.contains('Platelet Clumps')) {
		clumpCount = count

		clumpRow = row
	}
	
	if (cellName.contains('Large Platelets')) {
		largePlateletCount = count

		largePlateletRow = row
	}
}

println("Large Platelets Count: $largePlateletCount")

println("Platelet Clumps Count: $clumpCount")

// ✅ Step 2: If Large Platelets count is zero
if ((largePlateletCount == 0) || (largePlateletRow == null)) {
	KeywordUtil.markFailed('ℹ️ No Large Platelets found, no patches available.')
	return
	
} else {
	
	WebUI.click(findTestObject('Platelets/Page_PBS/split_view_img'))
    WebUI.waitForElementVisible(findTestObject('Object Repository/Platelets/Page_PBS/img_home_zoom'), 5)
	WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/img_home_zoom'))
	
// STEP 5: Wait for initial load
WebUI.delay(5)

// prepare zoom-in and warning locators
def zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
def zoomWarning = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'zoom-warning') and contains(.,'Digital zoom only')]")

// scales to verify
List<String> scales = ['500 μm','500 μm','200 μm','100 μm','50 μm','20 μm','10 μm','5 μm']

// STEP 6–11: zoom through scales without stopping on failure
for (String expected : scales) {
	try {
		WebUI.click(zoomInBtn)
		WebUI.delay(2)
		String actual = fetchScale()
		boolean matched = WebUI.verifyMatch(actual, expected, false, FailureHandling.CONTINUE_ON_FAILURE)
		println "Expected: ${expected}, Actual: ${actual}, Matched: ${matched}"
		String b64 = snapAndBase64("plt_${expected.replaceAll(' ','')}.png")
		println "BASE64 @${expected}: ${b64}"
	} catch (Exception e) {
		println "Error verifying scale ${expected}: ${e.message}"
	}
}
// after 10 μm, check warning then zoom to 5 μm
try {
	// check warning at 10 μm
	boolean warningShown10 = WebUI.verifyElementPresent(zoomWarning, 5, FailureHandling.CONTINUE_ON_FAILURE)
	println "Warning at 10 μm shown: ${warningShown10}"
	// final zoom to 5 μm
	WebUI.click(zoomInBtn)
	WebUI.delay(2)
	String actual5 = fetchScale()
	boolean matched5 = WebUI.verifyMatch(actual5, '5 μm', false, FailureHandling.CONTINUE_ON_FAILURE)
	println "Expected: 5 μm, Actual: ${actual5}, Matched: ${matched5}"
	String b64_5 = snapAndBase64('plt_5μm.png')
	println "BASE64 @5 μm: ${b64_5}"
	boolean warningShown5 = WebUI.verifyElementPresent(zoomWarning, 5, FailureHandling.CONTINUE_ON_FAILURE)
	println "Warning at 5 μm shown: ${warningShown5}"
} catch (Exception e) {
	println "Error verifying max zoom 5 μm: ${e.message}"
}
WebUI.comment('Completed Platelet max-zoom verification.')
}