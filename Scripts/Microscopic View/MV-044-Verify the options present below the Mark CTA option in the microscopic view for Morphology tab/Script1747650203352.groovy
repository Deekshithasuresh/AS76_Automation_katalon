import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType

// ---------- STEP 1: Login & open a report ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// wait for list to load
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 2: Open “To be reviewed” or “Under review” ----------
TestObject toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']")
TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.scrollToElement(toBe, 5)
	WebUI.click(toBe)
} else {
	WebUI.scrollToElement(under, 5)
	WebUI.click(under)
}
WebUI.comment("✔ Opened a ‘To be reviewed’ or ‘Under review’ report")

// ---------- STEP 3: Click the Platelets tab ----------
TestObject pltTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(pltTab, 10)
WebUI.click(pltTab)
WebUI.comment("✔ Platelets tab clicked")

// ---------- STEP 4: Switch to Morphology sub-tab ----------
TestObject morphTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[normalize-space()='Morphology']")
WebUI.waitForElementClickable(morphTab, 10)
WebUI.click(morphTab)
WebUI.comment("✔ Platelets → Morphology sub-tab clicked")

// ---------- STEP 5: Activate Microscopic view ----------
TestObject micro = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(micro, 10)
WebUI.click(micro)
WebUI.comment("✔ Microscopic view activated for Platelet morphology")

// allow the OpenLayers viewer to stitch & render
WebUI.delay(5)

// ---------- STEP 6: Click the “mark area” (zoom-tool) button ----------
TestObject markAreaBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]"
)
WebUI.waitForElementClickable(markAreaBtn, 5)
WebUI.click(markAreaBtn)
WebUI.comment("✔ Mark-area (zoom-tool) clicked")

// allow the 40×/100× popover to animate in
WebUI.delay(2)

// ---------- STEP 7: Verify the 40× and 100× options appear ----------
TestObject btn40x = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[1]"
)
TestObject btn100x = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[2]"
)

WebUI.waitForElementVisible(btn40x, 10)
WebUI.verifyElementPresent(btn40x, 1)
WebUI.comment("✔ ‘40×’ option is present")

WebUI.waitForElementVisible(btn100x, 10)
WebUI.verifyElementPresent(btn100x, 1)
WebUI.comment("✔ ‘100×’ option is present")


