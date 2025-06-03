import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ────────────────────────────────────────────────────────────────────
// STEP 1: Login to PBS
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// STEP 2: Open any “To be reviewed” or “Under review” report
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

// ────────────────────────────────────────────────────────────────────
// STEP 3: Switch to RBC tab
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']")
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)

// ────────────────────────────────────────────────────────────────────
// STEP 4: Activate Microscopic view & wait 120s
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)
WebUI.delay(120)

// ────────────────────────────────────────────────────────────────────
// STEP 5: Verify presence of the line-tool icon
TestObject lineTool = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='line-tool' and contains(@src,'/icons/line-icon.svg')]")
WebUI.verifyElementPresent(lineTool, 10)
WebUI.comment("✔ ‘line-tool’ icon is present in the RBC microscopic view")
