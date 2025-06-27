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
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]")
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin” (if not already)
// ────────────────────────────────────────────────────────────────────
TestObject assignedInput = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']")
String current = WebUI.getAttribute(assignedInput, 'value').trim()
if (!current.equalsIgnoreCase('admin')) {
	WebUI.comment("Re-assigning from '${current}' → 'admin'")
	TestObject dropdown = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button")
	TestObject adminOption = new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//li[@role='option' and normalize-space(text())='admin']")
	WebUI.click(dropdown)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.click(adminOption)
	WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)
} else {
	WebUI.comment("✅ Already assigned to admin")
}

// ────────────────────────────────────────────────────────────────────
// 5) CLICK RBC TAB (with JS fallback)
// ────────────────────────────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons cell-tab')]//span[normalize-space()='RBC']")
WebUI.waitForElementVisible(rbcTab, 10)
WebUI.scrollToElement(rbcTab, 5)
try {
	WebUI.click(rbcTab, FailureHandling.STOP_ON_FAILURE)
} catch (com.kms.katalon.core.exception.StepFailedException e) {
	WebUI.comment("⚠ RBC-tab intercepted, clicking via JS")
	WebElement el = WebUI.findWebElement(rbcTab)
	WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(el))
}
WebUI.comment("✔ RBC tab clicked")

// ────────────────────────────────────────────────────────────────────
// 6) VERIFY IMAGE SETTINGS ICON
// ────────────────────────────────────────────────────────────────────
TestObject imgSettingsIcon = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'patch-img-settings')]//img[@class='image-settings' and @alt='image_setting']")
WebUI.waitForElementPresent(imgSettingsIcon, 10)
WebUI.comment("✔ Image-Settings icon present")

// ────────────────────────────────────────────────────────────────────
// 7) CLICK IMAGE SETTINGS (with JS fallback)
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementVisible(imgSettingsIcon, 5)
WebUI.scrollToElement(imgSettingsIcon, 5)
try {
	WebUI.click(imgSettingsIcon, FailureHandling.STOP_ON_FAILURE)
} catch (com.kms.katalon.core.exception.StepFailedException e) {
	WebUI.comment("⚠ Image-Settings click intercepted, clicking via JS")
	WebElement el = WebUI.findWebElement(imgSettingsIcon)
	WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(el))
}

// ────────────────────────────────────────────────────────────────────
// 8) WAIT FOR POPOVER
// ────────────────────────────────────────────────────────────────────
TestObject popover = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper') and .//div[@class='img-utils-container']]")
WebUI.waitForElementVisible(popover, 10)

// ────────────────────────────────────────────────────────────────────
// 9) VERIFY APPROVE & REJECT BUTTONS
// ────────────────────────────────────────────────────────────────────
TestObject popApprove = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Approve report']")
TestObject popReject  = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Reject report']")
WebUI.verifyElementPresent(popApprove, 5, FailureHandling.STOP_ON_FAILURE)
WebUI.verifyElementPresent(popReject,  5, FailureHandling.STOP_ON_FAILURE)

WebUI.comment("✅ Approve & Reject buttons are both visible in the Image-Settings popover.")
