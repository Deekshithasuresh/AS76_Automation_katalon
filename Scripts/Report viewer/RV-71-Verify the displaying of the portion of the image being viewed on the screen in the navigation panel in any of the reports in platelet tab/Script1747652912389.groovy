import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

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

// ---------- STEP 6: Click the Overview (›) button ----------
TestObject overviewBtnPL = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@title='Overview']"
)
WebUI.waitForElementClickable(overviewBtnPL, 10)
WebUI.click(overviewBtnPL)
WebUI.comment("✔ Overview button clicked on Platelets view.")

// ---------- STEP 7: Verify the overview popup appears ----------
TestObject overviewBoxPL = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]"
)
WebUI.waitForElementPresent(overviewBoxPL, 10)
WebUI.verifyElementVisible(overviewBoxPL)
WebUI.comment("✔ Overview popup is displayed on Platelets view.")

