import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Open a “To be reviewed” or “Under review” report ----------
TestObject toBeReviewed2 = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//span[normalize-space()='To be reviewed']"
)
TestObject underReview2 = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
)
if (WebUI.waitForElementPresent(toBeReviewed2, 5)) {
    WebUI.scrollToElement(toBeReviewed2, 5)
    WebUI.click(toBeReviewed2)
} else {
    WebUI.scrollToElement(underReview2, 5)
    WebUI.click(underReview2)
}

// ---------- STEP 3: Click on the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// ---------- STEP 4: Activate Microscopic view ----------
TestObject microViewBtnPL = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtnPL, 10)
WebUI.click(microViewBtnPL)

// ---------- STEP 5: Wait for the view to finish rendering ----------
WebUI.delay(5)

// ---------- STEP 6: Verify the “info” (i) button is present ----------
TestObject infoBtnPL = new TestObject().addProperty(
    'xpath', ConditionType.EQUALS,
    "//div[contains(@class,'side-pane-btn')]//img[@alt='i']"
)
WebUI.verifyElementPresent(infoBtnPL, 10)
WebUI.comment("✔ ‘i’ info-button is present on Platelets microscopic view.")

// (Optional) close browser
// WebUI.closeBrowser()
