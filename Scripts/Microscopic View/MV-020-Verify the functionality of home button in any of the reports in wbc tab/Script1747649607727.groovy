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

// ---------- STEP 3: Click on the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

// ---------- STEP 4: Activate Microscopic view ----------
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)

// ---------- STEP 5: Wait for the viewer to finish rendering ----------
WebUI.delay(5)

// ---------- STEP 6: Zoom in twice ----------
TestObject zoomInBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']"
)
WebUI.waitForElementClickable(zoomInBtn, 10)
WebUI.click(zoomInBtn)
WebUI.delay(1)
WebUI.click(zoomInBtn)
WebUI.comment("✔ Zoomed in twice")

// ---------- STEP 7: Click the Home button to reset view ----------
TestObject homeBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='home']/parent::div"
)
WebUI.waitForElementClickable(homeBtn, 5)
WebUI.click(homeBtn)
WebUI.comment("✔ Home button clicked - view reset")




