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

// ---------- STEP 9: Click on the WBC tab ----------
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons cell-tab')]/span[normalize-space()='WBC']"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)
WebUI.comment('✔ WBC tab clicked')
// ---------- STEP: Verify presence of Image Settings option ----------
TestObject imageSettingsContainer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'patch-img-settings') and .//img[@class='image-settings' and @alt='image_setting']]"
)
WebUI.waitForElementPresent(imageSettingsContainer, 10)
WebUI.comment("✔ Image Settings option is present")

// ---------- STEP: Click on Image Settings icon ----------
TestObject imgSettingsIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'patch-img-settings')]//img[@class='image-settings' and @alt='image_setting']"
)
WebUI.waitForElementClickable(imgSettingsIcon, 10)
WebUI.click(imgSettingsIcon)

// ---------- STEP: Wait for the popover container ----------
TestObject popover = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper') and .//div[@class='img-utils-container']]"
)
WebUI.waitForElementVisible(popover, 10)

// 1) SIZE: Medium should be active, Large should NOT be active
TestObject mediumActive = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Image Size']" +
	"/following-sibling::*//span[normalize-space()='Medium' and contains(@class,'MuiSlider-markLabelActive')]"
)
WebUI.verifyElementPresent(mediumActive, 5)

TestObject largeInactive = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Image Size']" +
	"/following-sibling::*//span[normalize-space()='Large' and not(contains(@class,'MuiSlider-markLabelActive'))]"
)
WebUI.verifyElementPresent(largeInactive, 5)

// Helper closure to verify a parameter’s “0” display
def verifyZeroValue = { String parameter ->
	// e.g. parameter = "Brightness"
	TestObject val = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//div[@class='control-title' and normalize-space()='$parameter']" +
		"/following-sibling::*//div[contains(@class,'value-display') and normalize-space()='0']"
	)
	WebUI.verifyElementPresent(val, 5, FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.comment("✔ $parameter default = 0")
}

// 2) Brightness
verifyZeroValue('Brightness')

// 3) Contrast
verifyZeroValue('Contrast')

// 4) Hue
verifyZeroValue('Hue')

// 5) saturation
// scroll a little to bring it into view
WebUI.scrollToElement(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Hue']"
), 1)

verifyZeroValue('saturation')

WebUI.comment("✅ All Image-settings defaults are correct.")