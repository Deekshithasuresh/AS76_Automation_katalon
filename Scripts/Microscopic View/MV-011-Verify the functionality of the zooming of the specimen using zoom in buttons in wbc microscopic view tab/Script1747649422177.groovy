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

