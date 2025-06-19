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

// 1) Header: "Image settings"
TestObject hdr = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='img-utils-header' and normalize-space()='Image settings']"
)
WebUI.verifyElementPresent(hdr, 5)

// 2) Close-icon in header
TestObject popClose = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='img-utils-header']//button[contains(@class,'close-btn')]"
)
WebUI.verifyElementPresent(popClose, 5)

// 3) "Image Size" subheader + its slider
TestObject szLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Image Size']"
)
WebUI.verifyElementPresent(szLabel, 5)
TestObject szSlider = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Image Size']/following-sibling::*//span[contains(@class,'MuiSlider-root')]"
)
WebUI.verifyElementPresent(szSlider, 5)

// 4) "Brightness" subheader + slider + value=0
TestObject brLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Brightness']"
)
WebUI.verifyElementPresent(brLabel, 5)
TestObject brSlider = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Brightness']/following-sibling::*//span[contains(@class,'MuiSlider-root')]"
)
WebUI.verifyElementPresent(brSlider, 5)
TestObject brValue = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Brightness']/following-sibling::*//div[contains(@class,'value-display') and normalize-space()='0']"
)
WebUI.verifyElementPresent(brValue, 5)

// 5) "Contrast" subheader + slider + value=0
TestObject coLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Contrast']"
)
WebUI.verifyElementPresent(coLabel, 5)
TestObject coSlider = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Contrast']/following-sibling::*//span[contains(@class,'MuiSlider-root')]"
)
WebUI.verifyElementPresent(coSlider, 5)
TestObject coValue = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Contrast']/following-sibling::*//div[contains(@class,'value-display') and normalize-space()='0']"
)
WebUI.verifyElementPresent(coValue, 5)

// 6) "Hue" subheader + slider + value=0
TestObject hueLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Hue']"
)
WebUI.verifyElementPresent(hueLabel, 5)
TestObject hueSlider = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Hue']/following-sibling::*//span[contains(@class,'MuiSlider-root')]"
)
WebUI.verifyElementPresent(hueSlider, 5)
TestObject hueValue = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Hue']/following-sibling::*//div[contains(@class,'value-display') and normalize-space()='0']"
)
WebUI.verifyElementPresent(hueValue, 5)

// 7) Scroll down slightly to reveal "saturation"
WebUI.scrollToElement(hueLabel, 1) // scroll enough to bring next section into view

// 8) "saturation" subheader + slider + value=0
TestObject satLabel = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='saturation']"
)
WebUI.verifyElementPresent(satLabel, 5)
TestObject satSlider = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='saturation']/following-sibling::*//span[contains(@class,'MuiSlider-root')]"
)
WebUI.verifyElementPresent(satSlider, 5)
TestObject satValue = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='saturation']/following-sibling::*//div[contains(@class,'value-display') and normalize-space()='0']"
)
WebUI.verifyElementPresent(satValue, 5)

// 9) Reset button
TestObject resetBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'reset-btn') and normalize-space()='Reset']"
)
WebUI.verifyElementPresent(resetBtn, 5)

// 10) Final close-icon inside popover
TestObject popClose2 = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'img-utils-header')]//button[contains(@class,'icon-close-thin')]"
)
WebUI.verifyElementPresent(popClose2, 5)

WebUI.comment("✅ All Image settings controls are present and correct.")

