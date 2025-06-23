import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

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
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “To be reviewed” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject toBeReviewedRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[normalize-space(text())='To be reviewed']])[1]"
)
WebUI.waitForElementVisible(toBeReviewedRow, 10)
WebUI.scrollToElement(toBeReviewedRow, 5)
WebUI.click(toBeReviewedRow)

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
WebUI.waitForElementVisible(emptyMsg, 5)
WebUI.comment("Verified that the history panel shows: 'No events to show'.")

