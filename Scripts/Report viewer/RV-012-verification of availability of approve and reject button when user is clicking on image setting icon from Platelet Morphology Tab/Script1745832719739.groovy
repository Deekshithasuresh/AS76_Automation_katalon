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
// 5) CLICK “Platelets” TAB 
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

