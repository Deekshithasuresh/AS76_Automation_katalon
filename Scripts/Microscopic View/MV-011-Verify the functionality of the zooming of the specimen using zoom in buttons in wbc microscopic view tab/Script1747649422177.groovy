import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.nio.file.Files
import java.nio.file.Paths

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// ─────────────────────────────────────────────
// Utility to create scale label TestObject
// ─────────────────────────────────────────────
def makeScaleTO = { String label ->
	new TestObject().addProperty(
		'xpath',
		ConditionType.EQUALS,
		"//div[contains(@class,'-inner') and normalize-space()='$label']"
	)
}

// ─────────────────────────────────────────────
// Utility to take screenshot and return Base64
// ─────────────────────────────────────────────
def takeScreenshotAsBase64 = { String fileName ->
	String fullPath = Paths.get(RunConfiguration.getReportFolder(), fileName).toString()
	WebUI.takeScreenshot(fullPath)
	byte[] bytes = Files.readAllBytes(Paths.get(fullPath))
	return bytes.encodeBase64().toString()
}

// ─────────────────────────────────────────────
// 1) LOGIN
// ─────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://pbsreview.as76.local/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))



// ─────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ─────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ─────────────────────────────────────────────
// 4) SWITCH TO WBC → MICROSCOPIC VIEW
// ─────────────────────────────────────────────
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"
)
WebUI.waitForElementClickable(wbcTab, 5)
WebUI.click(wbcTab)

TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 5)
WebUI.click(microViewBtn)

// Wait for microscopic view to stabilize
WebUI.delay(5)

// ─────────────────────────────────────────────
// 5) VERIFY DEFAULT ZOOM (1000 μm) + SCREENSHOT
// ─────────────────────────────────────────────
TestObject s1000 = makeScaleTO('1000 μm')
WebUI.waitForElementPresent(s1000, 10)
WebUI.comment("✔ Default scale label: " + WebUI.getText(s1000))

String defaultB64 = takeScreenshotAsBase64('wbc_default.png')

// ─────────────────────────────────────────────
// 6) ZOOM IN → VERIFY (500 μm) + SCREENSHOT
// ─────────────────────────────────────────────
TestObject zoomInBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)
WebUI.click(zoomInBtn)
WebUI.delay(3)

TestObject s500 = makeScaleTO('500 μm')
WebUI.waitForElementPresent(s500, 10)
WebUI.comment("✔ Zoomed-in scale label: " + WebUI.getText(s500))

String zoomB64 = takeScreenshotAsBase64('wbc_zoom.png')

assert defaultB64 != zoomB64 : "🔍 Default vs zoomed image should differ"

// ─────────────────────────────────────────────
// 7) VERIFY SCREENSHOTS DIFFER
// ─────────────────────────────────────────────
//WebUI.verifyNotMatch(
//	defaultB64,
//	zoomB64,
//	false,
//	FailureHandling.STOP_ON_FAILURE,
//	"🔍 Default vs Zoomed-in image should differ"
//)

WebUI.comment("✅ Zoom-in verification from 1000 μm → 500 μm successful via image comparison.")
WebUI.closeBrowser()
