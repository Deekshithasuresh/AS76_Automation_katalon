import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.common.WebUiCommonHelper

// ---------- STEP 1: Login ----------
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))

// ---------- STEP 2: Verify PBS header ----------
WebUI.verifyElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ---------- STEP 3: Pick & assign a report ----------
TestObject toBeReviewed = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']"
)
TestObject underReview = new TestObject().addProperty(
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

String pickedStatus = null

if (WebUI.waitForElementPresent(toBeReviewed, 3)) {
	WebUI.scrollToElement(toBeReviewed, 5)
	WebUI.click(toBeReviewed)
	pickedStatus = 'To be reviewed'

	WebUI.click(assignedDropdown)
	WebUI.waitForElementClickable(adminOption, 5)
	WebUI.click(adminOption)
	WebUI.comment("Assigned a ‘To be reviewed’ report to admin.")
}
else if (WebUI.waitForElementPresent(underReview, 3)) {
	WebUI.scrollToElement(underReview, 5)
	WebUI.click(underReview)
	pickedStatus = 'Under review'

	String currentAssignee = WebUI.getAttribute(assignedInput, 'value').trim()
	if (currentAssignee != 'admin') {
		WebUI.click(assignedDropdown)
		WebUI.waitForElementClickable(adminOption, 5)
		WebUI.click(adminOption)
		WebUI.waitForElementClickable(reassignButton, 5)
		WebUI.click(reassignButton)
		WebUI.comment("Re-assigned an ‘Under review’ report to admin.")
	} else {
		WebUI.comment("‘Under review’ report already assigned to admin; no change.")
	}
}
else {
	WebUI.comment("❌ No report found in either ‘To be reviewed’ or ‘Under review’ status.")
	WebUI.takeScreenshot()
	WebUI.closeBrowser()
	return
}

// ---------- STEP 4: Verify Approve Button ----------
TestObject approveBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment("✅ 'Approve report' is now visible.")

// ---------- STEP 5: Open Slide-info Drawer ----------
TestObject slideInfoIcon = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//img[@src='/icons/slide-info.svg' and @alt='info.svg']"
)
WebUI.waitForElementClickable(slideInfoIcon, 5)
WebUI.click(slideInfoIcon)

// ---------- STEP 6: Wait for Drawer ----------
TestObject drawer = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]"
)
WebUI.waitForElementVisible(drawer, 10)

// ---------- STEP 7: Verify Slide Info Labels ----------
TestObject slideIdLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(@class,'slideInfoComponent_drawer__header-title') and normalize-space(.)='Slide Id:']"
)
WebUI.verifyElementPresent(slideIdLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

// ** NEW STATUS LOCATOR **
TestObject statusLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'slideInfoComponent_status') " +
	  "  and (contains(normalize-space(.),'To be reviewed') " +
	  "    or contains(normalize-space(.),'Under review'))]"
)
WebUI.verifyElementPresent(statusLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

TestObject slideImageLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space(.)='Slide image']"
)
WebUI.verifyElementPresent(slideImageLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

TestObject scannedByLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space(.)='Scanned by']"
)
WebUI.verifyElementPresent(scannedByLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

TestObject scannedOnLbl = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space(.)='Scanned on']"
)
WebUI.verifyElementPresent(scannedOnLbl, 5, FailureHandling.CONTINUE_ON_FAILURE)

// ---------- STEP 8: Close Slide-info Drawer ----------
TestObject drawerCloseBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiDrawer-paperAnchorLeft')]//button[contains(@class,'MuiIconButton-root')]"
)
WebUI.waitForElementVisible(drawerCloseBtn, 10)
WebUI.waitForElementClickable(drawerCloseBtn, 10)
WebUI.click(drawerCloseBtn)

WebUI.comment("✅ Slide-info popup opened and closed successfully.")

