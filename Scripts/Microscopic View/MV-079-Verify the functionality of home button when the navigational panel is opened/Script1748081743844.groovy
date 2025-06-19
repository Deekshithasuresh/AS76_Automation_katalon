import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// ────────────────────────────────────────────────────────────────────
// STEP 1: Login & open a report
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
                     'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// pick any “To be reviewed” or “Under review” report
TestObject toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS,
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


// STEP 2: Switch to WBC tab
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']")
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)


// STEP 3: Activate Microscopic view & wait 120s
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)
WebUI.delay(120)

// STEP 4: Click the Overview (navigation) panel icon
TestObject overviewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[@title='Overview']")
WebUI.waitForElementClickable(overviewBtn, 10)
WebUI.click(overviewBtn)

// STEP 5: Click the Zoom-In button twice
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
WebUI.waitForElementClickable(zoomInBtn, 10)
WebUI.click(zoomInBtn)
WebUI.delay(2)  // small pause between clicks
WebUI.click(zoomInBtn)

// STEP 6: Click the Home button in the overview map
TestObject homeBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
    "//div[.//img[@alt='home']][contains(@style,'position: absolute')]")
WebUI.waitForElementClickable(homeBtn, 10)
WebUI.click(homeBtn)

WebUI.comment("Completed WBC overview zoom-in & home-reset flow.")
