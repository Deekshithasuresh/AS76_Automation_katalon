import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')


// ────────────────────────────────────────────────────────────────────
// 3) OPEN KEBAB MENU & SELECT “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebab, 5)
WebUI.click(kebab)

TestObject historyItem = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyItem, 5)
WebUI.click(historyItem)

// ────────────────────────────────────────────────────────────────────
// 4) VERIFY “No events to show” MESSAGE
// ────────────────────────────────────────────────────────────────────
TestObject emptyMsg = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//*[normalize-space(text())='No events to show']"
)

if (WebUI.waitForElementVisible(emptyMsg, 5, FailureHandling.STOP_ON_FAILURE)) {
	WebUI.comment("Empty message is visible")
} else {
	KeywordUtil.markFailed( "No events to show massage not visible")
	WebUI.comment("Empty message is NOT visible, continue execution")
}

WebUI.comment("Verified that the history panel shows: 'No events to show'.")

