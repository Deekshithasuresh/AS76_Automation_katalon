import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.common.WebUiCommonHelper

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

