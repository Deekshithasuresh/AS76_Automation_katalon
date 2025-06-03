import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling


// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify landing on list reports page ----------
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 3: Pick & assign a report ----------
TestObject statusToBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS, "//span[normalize-space()='To be reviewed']"
)
TestObject statusUnderReview = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']"
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
	"//li[normalize-space(text())='admin']"
)
TestObject reassignButton = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Re-assign']"
)
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
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
		// reassign only if not already admin
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
	WebUI.comment(" No report in ‘To be reviewed’ or ‘Under review’ status.")
	WebUI.takeScreenshot()
	WebUI.closeBrowser()
	return
}

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment("✅ 'Approve report' is now visible.")

// ---------- STEP 4: Click on the RBC tab ----------
TestObject rbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='RBC']]"
)
WebUI.waitForElementClickable(rbcTab, 10)
WebUI.click(rbcTab)

// ---------- STEP 5: Click on Image settings icon ----------
TestObject imgSettings = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='patch-img-settings']//img[@alt='image_setting']"
)
WebUI.waitForElementClickable(imgSettings, 10)
WebUI.click(imgSettings)

// ---------- STEP 6: Wait for the Image-settings pop-over ----------
TestObject popover = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper') and .//div[@class='img-utils-container']]"
)
WebUI.waitForElementVisible(popover, 10)

// ---------- STEP 7: Verify Approve & Reject buttons are present ----------
// Approve report
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Approve report']"
)
WebUI.verifyElementPresent(approveBtn, 5)

// Reject report
TestObject rejectBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Reject report']"
)
WebUI.verifyElementPresent(rejectBtn, 5)

WebUI.comment("✅ Both Approve report and Reject report buttons are visible under RBC → Image settings.")
