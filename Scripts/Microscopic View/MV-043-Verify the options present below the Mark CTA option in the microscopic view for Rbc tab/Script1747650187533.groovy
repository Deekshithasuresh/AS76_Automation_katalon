import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import org.openqa.selenium.Keys

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

// ---------- STEP 3: Click the RBC tab ----------
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']")
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)
WebUI.comment("✔ RBC tab clicked")

// ---------- STEP 4: Activate Microscopic view ----------
TestObject micro = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(micro, 10)
WebUI.click(micro)
WebUI.comment("✔ Microscopic view activated for RBC")

// give OpenLayers viewer time to stitch & render
WebUI.delay(5)

// ---------- STEP 5: Click the “mark area” (zoom-tool) button ----------
TestObject markAreaBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]")
WebUI.waitForElementClickable(markAreaBtn, 5)
WebUI.click(markAreaBtn)
WebUI.comment("✔ Mark-area (zoom-tool) clicked")

// allow the 40×/100× popover to appear
WebUI.delay(2)

// ---------- STEP 6: Verify the 40× and 100× options appear ----------
TestObject btn40x = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[1]"
)
TestObject btn100x = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[2]"
)

WebUI.waitForElementVisible(btn40x, 10)
WebUI.verifyElementPresent(btn40x, 1)
WebUI.comment("✔ ‘40×’ option is present")

WebUI.waitForElementVisible(btn100x, 10)
WebUI.verifyElementPresent(btn100x, 1)
WebUI.comment("✔ ‘100×’ option is present")

