import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

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
// 3) PICK & ASSIGN A REPORT
// ────────────────────────────────────────────────────────────────────
TestObject statusToBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//tr[.//span[normalize-space(text())='To be reviewed']][1]"
)
TestObject statusUnderReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']][1]"
)
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject reassignButton = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space(text())='Re-assign']"
)

if (WebUI.waitForElementPresent(statusToBeReviewed, 3)) {
	WebUI.scrollToElement(statusToBeReviewed, 5)
	WebUI.click(statusToBeReviewed)
	WebUI.click(assignedDropdown)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.click(adminOption)
	WebUI.comment("Assigned a ‘To be reviewed’ report to admin.")
	
} else if (WebUI.waitForElementPresent(statusUnderReview, 3)) {
	WebUI.scrollToElement(statusUnderReview, 5)
	WebUI.click(statusUnderReview)
	
	// check current assignee
	String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
	if (currentAssignee != 'admin') {
		WebUI.click(assignedDropdown)
		WebUI.waitForElementClickable(adminOption, 5)
		WebUI.click(adminOption)
		WebUI.waitForElementClickable(reassignButton, 5)
		WebUI.click(reassignButton)
		WebUI.comment("Re-assigned an ‘Under review’ report to admin.")
	} else {
		WebUI.comment("‘Under review’ report already assigned to admin—no reassignment needed.")
	}
	
} else {
	WebUI.comment("No report in ‘To be reviewed’ or ‘Under review’ status.")
	WebUI.takeScreenshot()
	return
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
