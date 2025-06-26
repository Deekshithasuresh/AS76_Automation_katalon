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

// ---------- STEP 2: Click the Platelets tab ----------
def plateletsTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// ---------- STEP 3: Click the Morphology sub-tab ----------
def morphTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab']")
WebUI.waitForElementClickable(morphTab, 10)
WebUI.click(morphTab)

// ---------- STEP 4: Activate Microscopic view ----------
def microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)

// give the viewer a moment to render
WebUI.delay(5)

// ---------- STEP 5: Verify the “mark area” (zoom-tool) button ----------
def zoomTool = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]")
WebUI.verifyElementPresent(zoomTool, 10)
WebUI.comment("✔ Zoom-tool (‘mark area’) button is present on Platelets→Morphology microscopic view.")
