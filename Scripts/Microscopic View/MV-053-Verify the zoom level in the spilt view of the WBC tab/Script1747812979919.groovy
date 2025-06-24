import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

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

// ---------- STEP 4: Click on the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']")
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)
WebUI.comment("✔ WBC tab clicked.")

// ---------- STEP 5: Activate Split view ----------
TestObject splitViewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@id='split-view' and @aria-label='Split view']")
WebUI.waitForElementClickable(splitViewBtn, 10)
WebUI.click(splitViewBtn)
WebUI.delay(2)

// ---------- STEP 6: Verify default scale “10 μm” ----------
TestObject scale10 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-unselectable')]//div[contains(normalize-space(.),'10 μm')]")
WebUI.verifyElementVisible(scale10)

// ---------- STEP 7: Verify controls present ----------
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
TestObject zoomOutBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-out') and @title='Zoom out']")
TestObject homeIcon = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='home']")
TestObject overviewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@title='Overview']")
TestObject canvasObj = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-layer')]//canvas")

[zoomInBtn, zoomOutBtn, homeIcon, overviewBtn, canvasObj].each {
	WebUI.verifyElementPresent(it, 5)
}

// ---------- STEP 8: Capture “default” screenshot & encode ----------
String reportFolder  = RunConfiguration.getReportFolder()
String defaultPath   = reportFolder + '/wbc_split_default.png'
WebUI.takeScreenshot(defaultPath)
byte[] defaultBytes  = new File(defaultPath).bytes
String defaultBase64 = defaultBytes.encodeBase64().toString()

// ---------- STEP 9: Zoom in once & verify “5 μm” ----------
WebUI.click(zoomInBtn)
WebUI.delay(1)
TestObject scale5 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-unselectable')]//div[contains(normalize-space(.),'5 μm')]")
WebUI.verifyElementVisible(scale5)

// ---------- STEP 10: Capture “zoomed” screenshot & encode ----------
String zoomedPath   = reportFolder + '/wbc_split_zoomed.png'
WebUI.takeScreenshot(zoomedPath)
byte[] zoomedBytes   = new File(zoomedPath).bytes
String zoomedBase64  = zoomedBytes.encodeBase64().toString()

// ---------- STEP 11: Compare Base64 to ensure image changed ----------
WebUI.verifyNotMatch(zoomedBase64, defaultBase64, false, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ WBC split‐view image changed on zoom in (default vs zoomed).")
