import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebElement
import java.util.Arrays

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

// ────────────────────────────────────────────────────────────────────
// 5) CLICK RBC TAB 
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
