import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
new TestObject()
	.addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]")
	.with { WebUI.verifyElementPresent(it, 10) }

// ---------- STEP 3: Pick & assign a report ----------
TestObject toBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']"
)
TestObject underReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//input[@id='assigned_to']"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//li[normalize-space(text())='admin']"
)
TestObject reassignButton = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//button[normalize-space()='Re-assign']"
)
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)

if (WebUI.waitForElementPresent(toBeReviewed, 3)) {
	WebUI.scrollToElement(toBeReviewed, 5)
	WebUI.click(toBeReviewed)
	WebUI.click(assignedDropdown)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.click(adminOption)
	WebUI.comment("Assigned a ‘To be reviewed’ report to admin.")
} else if (WebUI.waitForElementPresent(underReview, 3)) {
	WebUI.scrollToElement(underReview, 5)
	WebUI.click(underReview)
	String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
	if (currentAssignee != 'admin') {
		WebUI.click(assignedDropdown)
		WebUI.waitForElementClickable(adminOption, 5)
		WebUI.click(adminOption)
		WebUI.waitForElementClickable(reassignButton, 5)
		WebUI.click(reassignButton)
		WebUI.comment("Re-assigned an ‘Under review’ report to admin.")
	} else {
		WebUI.comment("‘Under review’ report already assigned to admin—no reassignment needed.")
	}
} else {
	WebUI.comment("No report in ‘To be reviewed’ or ‘Under review’ status.")
	WebUI.takeScreenshot()
	WebUI.closeBrowser()
	return
}

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment("'Approve report' is now visible.")

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