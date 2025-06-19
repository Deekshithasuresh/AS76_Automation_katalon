import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
    findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
    'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementPresent(
    new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
    10
)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
String underReviewXpath = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, underReviewXpath)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//li[@role='option' and normalize-space(text())='admin']"
)
TestObject assignedInput = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//input[@id='assigned_to']"
)

WebUI.click(assignedDropdown)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment("'Approve report' is now visible.")
// ---------- STEP 3: Platelets tab → Morphology sub‐tab ----------
def pltTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(pltTab,10); WebUI.click(pltTab)

// Morphology button
def morphTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//span[normalize-space()='Morphology']]")
WebUI.waitForElementClickable(morphTab,5); WebUI.click(morphTab)

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