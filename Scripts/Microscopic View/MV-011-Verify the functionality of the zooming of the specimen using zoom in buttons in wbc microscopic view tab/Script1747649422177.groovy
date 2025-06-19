import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.configuration.RunConfiguration
import java.nio.file.Files
import java.nio.file.Paths

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
// use your existing repo paths for these
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					  'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Wait for the report list to appear ----------
TestObject listReady = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(listReady, 10)

// ---------- STEP 3: Pick & open a report ----------
TestObject toBe = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']"
)
TestObject under = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.scrollToElement(toBe, 5)
	WebUI.click(toBe)
} else {
	WebUI.click(under)
}

// ---------- STEP 4: Switch to WBC → Microscopic view ----------
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"
))
WebUI.click(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
))
// give OpenLayers time to stitch & render
WebUI.delay(6)

// ---------- STEP 5: Helpers for scale-labels & zoom button ----------
String reportFolder = RunConfiguration.getReportFolder()
def makeScaleTO = { String label ->
	new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//div[contains(@class,'-inner') and normalize-space(.)='${label}']"
	)
}
def zoomInBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)

// ---------- STEP 6: Default view → verify “1000 μm” + screenshot ----------
def s1000 = makeScaleTO('1000 μm')
WebUI.waitForElementPresent(s1000, 10)
WebUI.comment("✔ Default scale label: ${WebUI.getText(s1000)}")

String defaultShot = "${reportFolder}/wbc_micro_default.png"
WebUI.takeScreenshot(defaultShot)
String defaultB64 = Files.readAllBytes(Paths.get(defaultShot)).encodeBase64().toString()
WebUI.comment("✔ Captured default image → Base64 starts: ${defaultB64.take(80)}…")

// ---------- STEP 7: Zoom in once → verify “500 μm” + screenshot ----------
WebUI.click(zoomInBtn)
WebUI.delay(3)

def s500 = makeScaleTO('500 μm')
WebUI.waitForElementPresent(s500, 10)
WebUI.comment("✔ After 1× zoom label: ${WebUI.getText(s500)}")

String zoomShot = "${reportFolder}/wbc_micro_zoomed.png"
WebUI.takeScreenshot(zoomShot)
String zoomB64 = Files.readAllBytes(Paths.get(zoomShot)).encodeBase64().toString()
WebUI.comment("✔ Captured zoomed image → Base64 starts: ${zoomB64.take(80)}…")

// ---------- STEP 8: Sanity-check that the two images differ ----------
assert defaultB64 != zoomB64 : "The zoomed image is identical to the default!"
WebUI.comment(" WBC microscopic-view: 1000→500 μm zoom + screenshots/Base64 verified.")

