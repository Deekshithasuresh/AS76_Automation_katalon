import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN & NAVIGATE TO LIST
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// wait for the list to load
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//span[contains(text(),'PBS')]"
	), 10
)


// ────────────────────────────────────────────────────────────────────
// 2) FIND FIRST “Under review” ROW WITH A NON-EMPTY ASSIGNEE (updated)
// ────────────────────────────────────────────────────────────────────
String rowXpath = """(
  //tr[
    .//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']
    and normalize-space(.//input/@value)!=''
  ]
)[1]"""

TestObject row = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS, rowXpath)

WebUI.verifyElementPresent(row, 10, FailureHandling.STOP_ON_FAILURE)


// ────────────────────────────────────────────────────────────────────
// 3) CLICK THE CLEAR (×) BUTTON TO UNASSIGN (updated)
// ────────────────────────────────────────────────────────────────────
TestObject clearBtn = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS,
		rowXpath + "//button[.//svg[@data-testid='ClearIcon']]"
	)

WebUI.waitForElementClickable(clearBtn, 5)
WebUI.click(clearBtn)


// ────────────────────────────────────────────────────────────────────
// 4) CONFIRM THE “Unassign” DIALOG
// ────────────────────────────────────────────────────────────────────
TestObject unassignDialog = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'modal-title') and contains(.,'unassign this slide?')]/ancestor::div[@role='dialog']"
)
WebUI.waitForElementVisible(unassignDialog, 5)
TestObject unassignBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@role='dialog']//button[normalize-space()='Unassign']"
)
WebUI.click(unassignBtn)


// ────────────────────────────────────────────────────────────────────
// 5) VERIFY “Assigned to” IS NOW BLANK
// ────────────────────────────────────────────────────────────────────
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	rowXpath + "//input[@id='assigned_to']"
)
WebUI.waitForElementVisible(assignedInput, 5)
String afterAssign = WebUI.getAttribute(assignedInput, 'value')
WebUI.verifyMatch(afterAssign, '', false, FailureHandling.CONTINUE_ON_FAILURE)
WebUI.comment("Assigned-to field is now empty (unassigned).")


// ────────────────────────────────────────────────────────────────────
// 6) OPEN KEBAB MENU & CLICK HISTORY
// ────────────────────────────────────────────────────────────────────
TestObject kebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	rowXpath + "//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebab, 5)
WebUI.click(kebab)

TestObject historyOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[normalize-space()='History']"
)
WebUI.waitForElementClickable(historyOption, 5)
WebUI.click(historyOption)


// ────────────────────────────────────────────────────────────────────
// 7) GRAB THE FIRST HISTORY ENTRY & PRINT
// ────────────────────────────────────────────────────────────────────
TestObject firstEvent = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//div[contains(@class,'history-list')]//div[contains(normalize-space(),'Report Assignment')])[1]"
)
WebUI.waitForElementVisible(firstEvent, 30)
String eventText = WebUI.getText(firstEvent).trim()
println "🕑 First history event:\n${eventText}"

WebUI.comment("History event captured successfully.")
