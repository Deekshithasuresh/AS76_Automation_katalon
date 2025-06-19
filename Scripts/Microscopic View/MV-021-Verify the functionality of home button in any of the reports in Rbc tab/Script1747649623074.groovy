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
TestObject toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']")
TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
    WebUI.scrollToElement(toBe, 5)
    WebUI.click(toBe)
} else {
    WebUI.scrollToElement(under, 5)
    WebUI.click(under)
}

// ---------- STEP 2: Click the RBC tab ----------
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']")
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)

// ---------- STEP 3: Activate Microscopic view ----------
TestObject microRBC = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microRBC, 10)
WebUI.click(microRBC)

// let the viewer render
WebUI.delay(5)

// ---------- STEP 4: Zoom in twice ----------
TestObject zoomIn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
WebUI.waitForElementClickable(zoomIn, 10)
WebUI.click(zoomIn)
WebUI.delay(1)
WebUI.click(zoomIn)
WebUI.comment("✔ Zoomed in twice on RBC")

// ---------- STEP 5: Click Home to reset ----------
TestObject homeBtnRBC = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//img[@alt='home']/parent::div")
WebUI.waitForElementClickable(homeBtnRBC, 5)
WebUI.click(homeBtnRBC)
WebUI.comment("✔ Home button clicked on RBC")
