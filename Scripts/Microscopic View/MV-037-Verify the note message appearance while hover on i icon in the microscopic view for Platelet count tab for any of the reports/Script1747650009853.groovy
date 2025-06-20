import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

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

// ---------- STEP 2: Click on Platelets tab  ----------
def plateletsTab = new TestObject().addProperty(
  'xpath', ConditionType.EQUALS,
  "//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// ---------- STEP 3: Verify “Count” is the default sub-tab  ----------
def countTab = new TestObject().addProperty(
  'xpath', ConditionType.EQUALS,
  "//button[@id='plateleteCountTab']"
)
WebUI.verifyElementAttributeValue(countTab, 'aria-selected', 'true', 5)
WebUI.comment("✔ ‘Count’ sub-tab is active by default.")

// ---------- STEP 4: Switch to Microscopic view  ----------
def microView = new TestObject().addProperty(
  'xpath', ConditionType.EQUALS,
  "//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microView, 10)
WebUI.click(microView)

// allow the OpenLayers canvas to finish rendering
WebUI.delay(5)

// ---------- STEP 6: Hover over the “info” (i) button and verify tooltip ----------
TestObject infoBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'side-pane-btn')]//img[@alt='i']"
)
WebUI.waitForElementVisible(infoBtn, 5)
WebUI.mouseOver(infoBtn)

// tooltip text as shown in UI:
String expectedTooltip = "Black outline demarcates the area used for extraction of Platelets"
TestObject tooltip = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(text(),'" + expectedTooltip + "')]"
)
WebUI.waitForElementVisible(tooltip, 5)
WebUI.verifyElementText(tooltip, expectedTooltip)

WebUI.comment("✔ Hovering over ‘i’ shows correct Platelets extraction tooltip.")