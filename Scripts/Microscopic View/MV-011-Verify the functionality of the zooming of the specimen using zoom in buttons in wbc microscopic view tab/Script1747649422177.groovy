import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebDriver

import java.nio.file.Files
import java.nio.file.Paths

// ───────────────
// 1) LOGIN
// ───────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ───────────────
// 2) VERIFY LANDING
// ───────────────
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10, FailureHandling.STOP_ON_FAILURE)

// ───────────────
// 3) OPEN “Under review” REPORT
// ───────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ───────────────
// 4) SWITCH TO WBC → MICROSCOPIC VIEW
// ───────────────
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

// give OL a moment to render
WebUI.delay(6)

// grab a driver once
WebDriver driver = DriverFactory.getWebDriver()

// helper to build scale‐label TestObjects
def makeScaleTO = { String label ->
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//div[contains(@class,'-inner') and normalize-space()='$label']"
	)
}

// zoom‐in button
TestObject zoomInBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)

// folder for screenshots
String reportFolder = RunConfiguration.getReportFolder()

// ───────────────
// 5) VERIFY DEFAULT = “1000 μm”
// ───────────────
TestObject s1000 = makeScaleTO('1000 μm')
WebUI.waitForElementPresent(s1000, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Default scale label = " + WebUI.getText(s1000))

String defaultShot = Paths.get(reportFolder, 'wbc_default.png').toString()
WebUI.takeScreenshot(defaultShot)
byte[] defaultBytes = Files.readAllBytes(Paths.get(defaultShot))
String defaultB64 = defaultBytes.encodeBase64().toString()

// ───────────────
// 6) ZOOM IN ONCE + VERIFY = “500 μm”
// ───────────────
WebUI.click(zoomInBtn)
WebUI.delay(3)

TestObject s500 = makeScaleTO('500 μm')
WebUI.waitForElementPresent(s500, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ After zoom label = " + WebUI.getText(s500))

String zoomShot = Paths.get(reportFolder, 'wbc_zoom.png').toString()
WebUI.takeScreenshot(zoomShot)
byte[] zoomBytes = Files.readAllBytes(Paths.get(zoomShot))
String zoomB64 = zoomBytes.encodeBase64().toString()

// ───────────────
// 7) VERIFY IMAGES DIFFER
// ───────────────
WebUI.verifyNotMatch(defaultB64, zoomB64, false,
	FailureHandling.STOP_ON_FAILURE,
	"🔍 Default vs zoomed images should differ")

WebUI.comment("✅ Zoom in from 1000 μm → 500 μm verified (screenshots/Base64).")


