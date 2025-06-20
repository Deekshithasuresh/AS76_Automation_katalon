import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling

// 1) LOGIN
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

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
String underReviewXpath = "(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
TestObject underReviewRow = new TestObject().addProperty('xpath', ConditionType.EQUALS, underReviewXpath)

WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 4) ASSIGN TO “admin”
// ────────────────────────────────────────────────────────────────────
TestObject assignedDropdown = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[@role='option' and normalize-space(text())='admin']"
)
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)

WebUI.click(assignedDropdown)
WebUI.waitForElementClickable(adminOption, 5)
WebUI.click(adminOption)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)

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