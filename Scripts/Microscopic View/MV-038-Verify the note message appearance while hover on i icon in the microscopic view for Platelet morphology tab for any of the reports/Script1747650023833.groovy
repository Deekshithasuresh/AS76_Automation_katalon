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

// ---------- STEP 2: Click on the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
  'xpath', ConditionType.EQUALS,
  "//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// ---------- STEP 3: Click on the Morphology sub-tab ----------
TestObject morphTab = new TestObject().addProperty(
  'xpath', ConditionType.EQUALS,
  "//button[@id='plateleteMorphologyTab']"
)
WebUI.waitForElementClickable(morphTab, 10)
WebUI.click(morphTab)

// ---------- STEP 4: Activate Microscopic view for Platelet morphology ----------
TestObject microViewBtn = new TestObject().addProperty(
  'xpath', ConditionType.EQUALS,
  "//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)

// ---------- STEP 5: Wait for the canvas to render ----------
WebUI.delay(5)

// ---------- STEP 6: Hover over the “i” info-icon and verify tooltip ----------
TestObject infoBtn = new TestObject().addProperty(
  'xpath', ConditionType.EQUALS,
  "//div[contains(@class,'side-pane-btn')]//img[@alt='i']"
)
WebUI.waitForElementVisible(infoBtn, 5)
WebUI.mouseOver(infoBtn)

// tooltip contains two lines of text
String line1 = "Black outline demarcates the area used for extraction of Platelets"
String line2 = "Large platelet and platelet clumps are extracted from the entire scanned area"

// locate the blue tooltip
TestObject tooltip = new TestObject().addProperty(
  'xpath', ConditionType.EQUALS,
  "//div[contains(text(),'" + line1 + "') and contains(text(),'" + line2 + "')]"
)
WebUI.waitForElementVisible(tooltip, 5)
WebUI.verifyElementPresent(tooltip, 1)
WebUI.verifyMatch(WebUI.getText(tooltip).trim().replaceAll("\\r?\\n"," "), 
                 (line1 + " " + line2), false)

WebUI.comment("✔ Platelet-morphology tooltip appears correctly on hover over the info icon.")
