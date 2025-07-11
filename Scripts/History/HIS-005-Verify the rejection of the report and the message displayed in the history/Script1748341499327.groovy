import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.util.KeywordUtil

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORTS LIST
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// 3) OPEN THE FIRST “Under review” REPORT
TestObject statusUnderReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
if (!WebUI.waitForElementPresent(statusUnderReview, 5, FailureHandling.OPTIONAL)) {
	KeywordUtil.markFailedAndStop("No ‘Under review’ report found")
}
WebUI.scrollToElement(statusUnderReview, 5)
WebUI.click(statusUnderReview)

// 4) ASSIGN TO ADMIN IF NEEDED
TestObject assignedInput    = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption      = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject reassignButton   = new TestObject().addProperty(
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
	WebUI.comment("Re-assigned ‘Under review’ report to admin.")
} else {
	WebUI.comment("‘Under review’ report already assigned to admin.")
}

// 5) CLICK “Reject report”
TestObject rejectBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'appBar_normal-button') and normalize-space(text())='Reject report']"
)
WebUI.waitForElementClickable(rejectBtn, 5)
WebUI.click(rejectBtn)

// 6) CONFIRM REJECTION
TestObject popupTitle = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'modal-title') and normalize-space(text())='Are you sure you want to reject this report?']"
)
WebUI.waitForElementVisible(popupTitle, 5)

// **Updated locator:** look for the “Reject” button inside modal-actions
TestObject confirmReject = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='modal-actions']//button[normalize-space(text())='Reject report']"
)
WebUI.waitForElementClickable(confirmReject, 5)
WebUI.click(confirmReject)
WebUI.delay(2)

// 7) OPEN KEBAB MENU & SELECT “History”
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
WebUI.delay(2)

// 8) VERIFY “Report Sign off” ENTRY IN HISTORY
String entryLiXpath = "(//h4[contains(@class,'event-title') and normalize-space(text())='Report Sign off']/ancestor::li)[1]"
TestObject firstEntryLi = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	entryLiXpath
)
WebUI.waitForElementVisible(firstEntryLi, 10)

TestObject descObj = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	entryLiXpath + "//div[contains(@class,'event-description')]"
)
String descText = WebUI.getText(descObj).trim()
WebUI.comment("History description: ${descText}")
WebUI.verifyMatch(descText, ".*rejected a report.*", true)

WebUI.comment("✅ Rejection flow and history entry verified.")
