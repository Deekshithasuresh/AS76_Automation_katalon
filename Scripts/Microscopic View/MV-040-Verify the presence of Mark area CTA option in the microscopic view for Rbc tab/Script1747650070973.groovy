import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ---------- LOGIN & OPEN A REPORT ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// open “To be reviewed” or “Under review”
def toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']")
def under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.scrollToElement(toBe, 5)
	WebUI.click(toBe)
} else {
	WebUI.scrollToElement(under, 5)
	WebUI.click(under)
}

// ---------- CLICK RBC TAB ----------
def rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']")
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)

// ---------- ACTIVATE MICROSCOPIC VIEW ----------
def microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)

// let it render
WebUI.delay(5)

// ---------- VERIFY ZOOM-TOOL BUTTON ----------
def zoomTool = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]")
WebUI.verifyElementPresent(zoomTool, 10)
WebUI.comment("✔ Zoom‐tool (‘mark area’) button is present on RBC microscopic view.")
