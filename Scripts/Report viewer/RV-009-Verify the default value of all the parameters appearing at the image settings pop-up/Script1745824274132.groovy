import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// 4) CLICK ON THE WBC TAB
TestObject wbcTab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-buttons cell-tab')]//span[normalize-space()='WBC']"
)
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)
WebUI.comment("✔ WBC tab clicked")

// 5) VERIFY PRESENCE OF IMAGE SETTINGS OPTION
TestObject imageSettingsContainer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'patch-img-settings') and .//img[@class='image-settings' and @alt='image_setting']]"
)
WebUI.waitForElementPresent(imageSettingsContainer, 10)
WebUI.comment("✔ Image Settings option is present")

// 6) CLICK ON IMAGE SETTINGS ICON
TestObject imgSettingsIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'patch-img-settings')]//img[@class='image-settings' and @alt='image_setting']"
)
WebUI.waitForElementClickable(imgSettingsIcon, 10)
WebUI.click(imgSettingsIcon)

// 7) WAIT FOR THE POPOVER CONTAINER
TestObject popover = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper') and .//div[@class='img-utils-container']]"
)
WebUI.waitForElementVisible(popover, 10)

// 8) VERIFY IMAGE SIZE SLIDER DEFAULTS
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
	TestObject val = new TestObject().addProperty(
		'xpath', ConditionType.EQUALS,
		"//div[@class='control-title' and normalize-space()='$parameter']" +
		"/following-sibling::*//div[contains(@class,'value-display') and normalize-space()='0']"
	)
	WebUI.verifyElementPresent(val, 5, FailureHandling.CONTINUE_ON_FAILURE)
	WebUI.comment("✔ $parameter default = 0")
}

// 9) Brightness default
verifyZeroValue('Brightness')

// 10) Contrast default
verifyZeroValue('Contrast')

// 11) Hue default
verifyZeroValue('Hue')

// 12) Saturation default (scroll into view first)
WebUI.scrollToElement(new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[@class='control-title' and normalize-space()='Hue']"
), 1)
verifyZeroValue('saturation')

WebUI.comment("✅ All Image Settings defaults are correct.")
