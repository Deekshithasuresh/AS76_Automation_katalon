import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

// ---------- STEP 3: Platelets tab → Morphology sub‐tab ----------
def pltTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(pltTab,10); WebUI.click(pltTab)

// Morphology button
def morphTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//Button[normalize-space()='Morphology']")
WebUI.waitForElementClickable(morphTab,5);
 WebUI.click(morphTab)

// ---------- STEP 4: Activate Split view ----------
def splitBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@id='split-view' and @aria-label='Split view']")
WebUI.waitForElementClickable(splitBtn,10); WebUI.click(splitBtn)
WebUI.delay(2)

// ---------- STEP 5: Verify default 10 μm + controls + canvas ----------
def defaultScale = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-unselectable')]//div[contains(normalize-space(.),'10 μm')]")
def zoomIn      = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
def zoomOut     = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-out') and @title='Zoom out']")
def home        = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='home']")
def overview    = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@title='Overview']")
def canvasObj   = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-layer')]//canvas")

[ defaultScale, zoomIn, zoomOut, home, overview, canvasObj ].each {
	WebUI.verifyElementPresent(it, 5)
}

// ---------- STEP 6: Screenshot & encode default ----------
String reportDir   = RunConfiguration.getReportFolder()
String beforePath  = reportDir + '/plt_morph_split_default.png'
WebUI.takeScreenshot(beforePath)
byte[] beforeBytes = new File(beforePath).bytes
String before64   = beforeBytes.encodeBase64().toString()

// ---------- STEP 7: Zoom in once & verify “5 μm” ----------
WebUI.click(zoomIn); WebUI.delay(1)
def scale5 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-unselectable')]//div[contains(normalize-space(.),'5 μm')]")
WebUI.verifyElementPresent(scale5, 5)

// ---------- STEP 8: Screenshot & encode zoomed ----------
String afterPath  = reportDir + '/plt_morph_split_zoomed.png'
WebUI.takeScreenshot(afterPath)
byte[] afterBytes = new File(afterPath).bytes
String after64   = afterBytes.encodeBase64().toString()

// ---------- STEP 9: Compare Base64 to ensure they differ ----------
WebUI.verifyNotMatch(after64, before64, false, FailureHandling.STOP_ON_FAILURE)
WebUI.comment("✔ Platelets-Morphology split-view image changed on zoom (10 μm → 5 μm).")