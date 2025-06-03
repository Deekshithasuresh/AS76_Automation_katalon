import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
					 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify PBS header is present ----------
TestObject pbsHeader = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(normalize-space(.),'PBS')]")
WebUI.verifyElementPresent(pbsHeader, 10)

// ---------- STEP 3: Open any “To be reviewed” or “Under review” report ----------
TestObject toBe  = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']")
TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.scrollToElement(toBe, 5)
	WebUI.click(toBe)
} else {
	WebUI.scrollToElement(under, 5)
	WebUI.click(under)
}

// ---------- STEP 4: Click on the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// ---------- STEP 5: Click on the Morphology sub-tab ----------
TestObject morphSubTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[@id='plateleteMorphologyTab']")
WebUI.waitForElementClickable(morphSubTab, 10)
WebUI.click(morphSubTab)

// ---------- STEP 6: Click on Microscopic view and wait 30s ----------
TestObject microView = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microView, 10)
WebUI.click(microView)
WebUI.delay(30)

// ---------- STEP 7: Click on the Overview (navigation) icon ----------
TestObject overviewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@title='Overview']")
WebUI.waitForElementClickable(overviewBtn, 10)
WebUI.click(overviewBtn)
WebUI.delay(30)

// ---------- STEP 8: Verify the overview overlay (canvas) appears ----------
TestObject overlayCanvas = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//canvas[contains(@class,'ol-layer')]")
WebUI.verifyElementPresent(overlayCanvas, 10)
WebUI.delay(30)

// ---------- STEP 9: Check for the flagged area box inside overview map ----------
TestObject flaggedBox = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]")
WebUI.verifyElementPresent(flaggedBox, 10)

WebUI.comment("Platelets › Morphology › Microscopic overview panel and flagged area verified successfully.")
