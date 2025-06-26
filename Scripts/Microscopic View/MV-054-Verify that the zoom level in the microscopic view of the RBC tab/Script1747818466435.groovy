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

// ---------- STEP 3: Click the RBC tab ----------
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']")
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)

// ---------- STEP 4: Activate Split view ----------
TestObject splitBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@id='split-view' and @aria-label='Split view']")
WebUI.waitForElementClickable(splitBtn, 10)
WebUI.click(splitBtn)
WebUI.delay(2)

// ---------- STEP 5: Verify default “10 μm” + controls + canvas ----------
TestObject defaultScale = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'-inner') and normalize-space(.)='10 μm']"
)

TestObject zoomIn    = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)
TestObject zoomOut   = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[contains(@class,'ol-zoom-out') and @title='Zoom out']"
)
TestObject homeIcon  = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//img[@alt='home']"
)
TestObject overview  = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[@title='Overview']"
)
TestObject canvasObj = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'ol-layer')]//canvas"
)

// verify all of them in one go
[ defaultScale, zoomIn, zoomOut, homeIcon, overview, canvasObj ].each {
    WebUI.verifyElementPresent(it, 5)
}

// ---------- STEP 6: Screenshot & encode default ----------
String reportDir   = RunConfiguration.getReportFolder()
String beforePath  = reportDir + '/rbc_split_default.png'
WebUI.takeScreenshot(beforePath)
byte[]    beforeB   = new File(beforePath).bytes
String    before64 = beforeB.encodeBase64().toString()

// ---------- STEP 7: Zoom in once & verify “5 μm” ----------
WebUI.click(zoomIn)
WebUI.delay(1)
TestObject fiveScale = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-unselectable')]//div[contains(normalize-space(.),'5 μm')]")
WebUI.verifyElementPresent(fiveScale, 5)

// ---------- STEP 8: Screenshot & encode zoomed ----------
String afterPath  = reportDir + '/rbc_split_zoomed.png'
WebUI.takeScreenshot(afterPath)
byte[]    afterB   = new File(afterPath).bytes
String    after64 = afterB.encodeBase64().toString()

// ---------- STEP 9: Compare Base64 to ensure they differ ----------
WebUI.verifyNotMatch(after64, before64, false, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ RBC split-view image changed on zoom (10 μm → 5 μm).")