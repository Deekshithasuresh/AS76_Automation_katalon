import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

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

// ─── STEP 4: Click on the WBC tab ─────────────────────────────────
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']")
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// ─── STEP 5: Click on Microscopic view ────────────────────────────
TestObject microView = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microView, 10)
WebUI.click(microView)

// allow the map to render
WebUI.delay(5)

// ─── STEP 6: Click on the Overview (navigation) icon ──────────────
TestObject overviewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@title='Overview']")
WebUI.waitForElementClickable(overviewBtn, 10)
WebUI.click(overviewBtn)

// ─── STEP 7: Verify the overview overlay (canvas) appears ────────
TestObject overlayCanvas = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//canvas[contains(@class,'ol-layer')]")
WebUI.verifyElementPresent(overlayCanvas, 10)

// ─── STEP 8: Check for the flagged area box inside the overview map ─
TestObject flaggedBox = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]")
WebUI.verifyElementPresent(flaggedBox, 10)

WebUI.comment("Overview panel and flagged area verified successfully.")
