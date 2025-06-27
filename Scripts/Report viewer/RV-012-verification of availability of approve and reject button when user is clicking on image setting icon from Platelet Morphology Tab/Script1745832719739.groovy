import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import java.util.Arrays

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]").with {
		WebUI.waitForElementPresent(it, 10)
}

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject underReview = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and text()='Under review']])[1]")
WebUI.waitForElementClickable(underReview, 10)
WebUI.scrollToElement(underReview, 5)
WebUI.click(underReview)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin” IF NEEDED
// ────────────────────────────────────────────────────────────────────
TestObject assignedIn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']")
String current = WebUI.getAttribute(assignedIn, 'value').trim()
if (!current.equalsIgnoreCase('admin')) {
	TestObject dropdown = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button")
	TestObject option   = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//li[@role='option' and normalize-space(text())='admin']")
	WebUI.click(dropdown)
	WebUI.waitForElementClickable(option, 5)
	WebUI.click(option)
	WebUI.waitForElementAttributeValue(assignedIn, 'value', 'admin', 5)
}

// ────────────────────────────────────────────────────────────────────
// 5) CLICK “Platelets” TAB (with JS-fallback)
// ────────────────────────────────────────────────────────────────────
def clickWithJS = { TestObject to ->
	WebUI.scrollToElement(to, 5)
	try {
		WebUI.click(to, FailureHandling.STOP_ON_FAILURE)
	} catch (Exception e) {
		WebElement el = WebUI.findWebElement(to)
		WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(el))
	}
}

// Platelets tab
TestObject plateletsTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[.='Platelets']")
WebUI.waitForElementVisible(plateletsTab, 10)
clickWithJS(plateletsTab)
WebUI.comment("✔ Platelets tab clicked")

// ────────────────────────────────────────────────────────────────────
// 6) CLICK “Morphology” SUB-TAB (with JS-fallback)
// ────────────────────────────────────────────────────────────────────
TestObject morphTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[@id='plateleteMorphologyTab' and normalize-space()='Morphology']")
WebUI.waitForElementVisible(morphTab, 10)
clickWithJS(morphTab)
WebUI.comment("✔ Morphology sub-tab clicked")

// ────────────────────────────────────────────────────────────────────
// 7) CLICK IMAGE-SETTINGS ICON (with JS-fallback)
// ────────────────────────────────────────────────────────────────────
TestObject imgSettings = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'patch-img-settings')]//img[@alt='image_setting']")
WebUI.waitForElementVisible(imgSettings, 10)
clickWithJS(imgSettings)
WebUI.comment("✔ Image-Settings icon clicked")

// ────────────────────────────────────────────────────────────────────
// 8) WAIT FOR POPOVER
// ────────────────────────────────────────────────────────────────────
TestObject popover = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper') and .//div[@class='img-utils-container']]")
WebUI.waitForElementVisible(popover, 10)

// ────────────────────────────────────────────────────────────────────
// 9) VERIFY “Approve report” & “Reject report” BUTTONS
// ────────────────────────────────────────────────────────────────────
TestObject btnApprove = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Approve report']")
TestObject btnReject  = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Reject report']")

WebUI.verifyElementPresent(btnApprove, 5, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementPresent(btnReject,  5, FailureHandling.STOP_ON_FAILURE)

WebUI.comment("✅ Approve & Reject buttons are visible under Platelets → Morphology → Image-Settings.")

