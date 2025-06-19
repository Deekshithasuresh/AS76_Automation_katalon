import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

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
// ---------- STEP 4: Click on the RBC tab ----------
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']")
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)

// ---------- STEP 5: Click on Microscopic view and wait 30s ----------
TestObject microView = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microView, 10)
WebUI.click(microView)
WebUI.delay(30)

// ---------- STEP 6: Click on the Overview (navigation) icon ----------
TestObject overviewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@title='Overview']")
WebUI.waitForElementClickable(overviewBtn, 10)
WebUI.click(overviewBtn)
WebUI.delay(30)

// ---------- STEP 7: Verify the overview overlay (canvas) appears ----------
TestObject overlayCanvas = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//canvas[contains(@class,'ol-layer')]")
WebUI.verifyElementPresent(overlayCanvas, 10)
WebUI.delay(30)

// ---------- STEP 8: Check for the flagged area box inside the overview map ----------
TestObject flaggedBox = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]")
WebUI.verifyElementPresent(flaggedBox, 10)

WebUI.comment(" Overview panel and flagged area verified on the RBC tab successfully.")
