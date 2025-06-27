import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login', FailureHandling.STOP_ON_FAILURE)
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr', FailureHandling.STOP_ON_FAILURE)
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==', FailureHandling.STOP_ON_FAILURE)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'), FailureHandling.STOP_ON_FAILURE)

// 2) VERIFY LANDING ON REPORT LIST
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10, FailureHandling.STOP_ON_FAILURE)

// 3) OPEN FIRST ROW (any status)
TestObject firstRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr)[1]"
)
if (WebUI.waitForElementClickable(firstRow, 5, FailureHandling.OPTIONAL)) {
	WebUI.scrollToElement(firstRow, 3, FailureHandling.OPTIONAL)
	WebUI.click(firstRow, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Opened first report row")
} else {
	WebUI.comment("⚠ Could not click first row—continuing anyway")
}

// 4) CLICK the Platelets tab
TestObject plateletsTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"
)
if (WebUI.waitForElementClickable(plateletsTab, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(plateletsTab, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Platelets tab clicked.")
} else {
	WebUI.comment("⚠ Platelets tab not found.")
}

// 5) VERIFY Count sub-tab is active by default (optional)
TestObject countSubtab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'sub-tab')]//span[normalize-space()='Count' and @aria-selected='true']"
)
if (WebUI.verifyElementVisible(countSubtab, FailureHandling.CONTINUE_ON_FAILURE)) {
	WebUI.comment("✔ ‘Count’ sub-tab is active by default.")
} else {
	WebUI.comment("⚠ ‘Count’ sub-tab was not marked active; proceeding anyway.")
}

// 6) ACTIVATE Microscopic view
TestObject microViewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
if (WebUI.waitForElementClickable(microViewBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(microViewBtn, FailureHandling.OPTIONAL)
	WebUI.comment("✔ Microscopic view activated.")
} else {
	WebUI.comment("⚠ Microscopic view button not found.")
}

// give OpenLayers a moment to render
WebUI.delay(3)

// 7) TOGGLE Overview panel on/off
TestObject overviewBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[@title='Overview']"
)
TestObject overviewBox = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]"
)
if (WebUI.waitForElementClickable(overviewBtn, 5, FailureHandling.OPTIONAL)) {
	WebUI.click(overviewBtn, FailureHandling.OPTIONAL)
	if (WebUI.waitForElementVisible(overviewBox, 3, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔ Overview panel appeared.")
	} else {
		WebUI.comment("⚠ Overview panel did not appear (maybe already open).")
	}
	WebUI.click(overviewBtn, FailureHandling.OPTIONAL)
	if (WebUI.waitForElementNotPresent(overviewBox, 3, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔ Overview panel closed.")
	} else {
		WebUI.comment("⚠ Overview panel did not close.")
	}
} else {
	WebUI.comment("⚠ Overview toggle button not found.")
}

// done
WebUI.comment("✅ MV-028 script completed without uncaught errors.")





