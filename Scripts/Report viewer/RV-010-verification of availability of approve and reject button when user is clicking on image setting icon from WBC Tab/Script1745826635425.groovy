import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.util.Arrays
import org.openqa.selenium.WebElement

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")


// ────────────────────────────────────────────────────────────────────
// 5) CLICK WBC TAB
// ────────────────────────────────────────────────────────────────────
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons cell-tab')]//span[normalize-space()='WBC']")
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)
WebUI.comment("✔ WBC tab clicked")

// ────────────────────────────────────────────────────────────────────
// 6) VERIFY IMAGE SETTINGS OPTION
// ────────────────────────────────────────────────────────────────────
TestObject imgSettingsContainer = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'patch-img-settings') and .//img[@class='image-settings' and @alt='image_setting']]")
WebUI.waitForElementPresent(imgSettingsContainer, 10)
WebUI.comment("✔ Image Settings option is present")

// ────────────────────────────────────────────────────────────────────
// 7) CLICK IMAGE SETTINGS ICON WITH FALLBACK
// ────────────────────────────────────────────────────────────────────
TestObject imgSettingsIcon = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'patch-img-settings')]//img[@class='image-settings' and @alt='image_setting']")
WebUI.waitForElementVisible(imgSettingsIcon, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.scrollToElement(imgSettingsIcon, 5)
WebUI.waitForElementClickable(imgSettingsIcon, 10, FailureHandling.STOP_ON_FAILURE)
try {
	WebUI.click(imgSettingsIcon, FailureHandling.STOP_ON_FAILURE)
} catch (com.kms.katalon.core.exception.StepFailedException e) {
	WebUI.comment("⚠ Normal click intercepted – clicking via JS")
	WebElement el = WebUI.findWebElement(imgSettingsIcon)
	WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(el))
}

// ────────────────────────────────────────────────────────────────────
// 8) WAIT FOR POPOVER
// ────────────────────────────────────────────────────────────────────
TestObject popover = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper') and .//div[@class='img-utils-container']]")
WebUI.waitForElementVisible(popover, 10, FailureHandling.STOP_ON_FAILURE)

// ────────────────────────────────────────────────────────────────────
// 9) VERIFY APPROVE & REJECT BUTTONS
// ────────────────────────────────────────────────────────────────────
TestObject popoverApproveBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Approve report']")
WebUI.verifyElementPresent(popoverApproveBtn, 5, FailureHandling.STOP_ON_FAILURE)

TestObject popoverRejectBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Reject report']")
WebUI.verifyElementPresent(popoverRejectBtn, 5, FailureHandling.STOP_ON_FAILURE)

WebUI.comment("✅ Both Approve report and Reject report buttons are visible.")

