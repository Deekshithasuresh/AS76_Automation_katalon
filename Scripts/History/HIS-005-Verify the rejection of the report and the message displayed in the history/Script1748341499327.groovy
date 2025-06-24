import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORTS LIST
// ────────────────────────────────────────────────────────────────────
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 3) FIND THE FIRST “Under review” ROW AND CLICK IT
// ────────────────────────────────────────────────────────────────────
TestObject statusUnderReview = new TestObject('statusUnderReview').addProperty(
	'xpath',
	ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)

// if no “Under review” row, fail
if (!WebUI.waitForElementPresent(statusUnderReview, 5)) {
	KeywordUtil.markFailedAndStop("No ‘Under review’ report found on the page")
}

// bring it into view & open
WebUI.scrollToElement(statusUnderReview, 5)
WebUI.click(statusUnderReview)

// ────────────────────────────────────────────────────────────────────
// 4) CHECK ASSIGNEE & (RE-)ASSIGN IF NEEDED
// ────────────────────────────────────────────────────────────────────
TestObject assignedInput    = new TestObject('assignedInput').addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
TestObject assignedDropdown = new TestObject('assignedDropdown').addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption      = new TestObject('adminOption').addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject reassignButton   = new TestObject('reassignBtn').addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space(text())='Re-assign']"
)

String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()

if (currentAssignee != 'admin') {
	WebUI.click(assignedDropdown)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.click(adminOption)
	WebUI.waitForElementClickable(reassignButton, 5)
	WebUI.click(reassignButton)
	WebUI.comment("Re-assigned the ‘Under review’ report to admin.")
} else {
	WebUI.comment("‘Under review’ report is already assigned to admin. Opened for review.")
	// if you have a separate “Open” or “View” button, define its xpath here and click it
}


// ────────────────────────────────────────────────────────────────────
// 4) CLICK THE “Reject report” BUTTON
// ────────────────────────────────────────────────────────────────────
TestObject rejectBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'appBar_normal-button') and normalize-space(text())='Reject report']"
)
WebUI.waitForElementClickable(rejectBtn, 5)
WebUI.click(rejectBtn)

// ────────────────────────────────────────────────────────────────────
// 5) CONFIRM REJECTION IN POPUP
// ────────────────────────────────────────────────────────────────────
TestObject popupTitle = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'modal-title') and normalize-space(text())='Are you sure you want to reject this report?']"
)
WebUI.waitForElementVisible(popupTitle, 5)

TestObject confirmReject = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'modal-actions')]//button[contains(@class,'backtoReportBtn') and normalize-space(text())='Reject report']"
)
WebUI.click(confirmReject)
WebUI.delay(2)  // let the rejection process

// ────────────────────────────────────────────────────────────────────
// 6) OPEN KEBAB MENU & SELECT HISTORY
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
// 7) EXTRACT & VERIFY FIRST “Report Sign off” ENTRY
// ────────────────────────────────────────────────────────────────────
// locate the first history entry's <li> by its <h4>
String entryLiXpath = "(//h4[contains(@class,'event-title') and normalize-space(text())='Report Sign off']/ancestor::li)[1]"
TestObject firstEntryLi = new TestObject().addProperty('xpath', ConditionType.EQUALS, entryLiXpath)
WebUI.waitForElementVisible(firstEntryLi, 10)

// timestamp
TestObject timeObj = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	entryLiXpath + "//div[contains(@class,'time')]"
)
String timeText = WebUI.getText(timeObj).trim()
println("History timestamp: " + timeText)

// description
TestObject descObj = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	entryLiXpath + "//div[contains(@class,'event-description')]"
)
String descText = WebUI.getText(descObj).trim()
println("History description: " + descText)

// final assertion
WebUI.verifyMatch(descText, ".*rejected a report", true, FailureHandling.STOP_ON_FAILURE)
