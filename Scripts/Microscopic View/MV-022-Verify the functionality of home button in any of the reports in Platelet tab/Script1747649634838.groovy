import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ---------- STEP 1: Login & open a report ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// choose a “To be reviewed” or “Under review” report
TestObject toBe2 = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']")
TestObject under2 = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe2, 5)) {
    WebUI.scrollToElement(toBe2, 5)
    WebUI.click(toBe2)
} else {
    WebUI.scrollToElement(under2, 5)
    WebUI.click(under2)
}

// ---------- STEP 2: Click the Platelets tab ----------
TestObject plTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(plTab, 10)
WebUI.click(plTab)

// ---------- STEP 3: Activate Microscopic view ----------
TestObject microPL = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microPL, 10)
WebUI.click(microPL)

// let the viewer render
WebUI.delay(5)

// ---------- STEP 4: Zoom in twice ----------
TestObject zoomInPL = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
WebUI.waitForElementClickable(zoomInPL, 10)
WebUI.click(zoomInPL)
WebUI.delay(1)
WebUI.click(zoomInPL)
WebUI.comment("✔ Zoomed in twice on Platelets")

// ---------- STEP 5: Click Home to reset ----------
TestObject homeBtnPL = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//img[@alt='home']/parent::div")
WebUI.waitForElementClickable(homeBtnPL, 5)
WebUI.click(homeBtnPL)
WebUI.comment("✔ Home button clicked on Platelets")
