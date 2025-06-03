import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


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
	WebUI.comment("❌ No report in ‘To be reviewed’ or ‘Under review’ status.")
	WebUI.takeScreenshot()
	WebUI.closeBrowser()
	return
}

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment(" 'Approve report' is now visible.")


// ---------- STEP 9: Click on the Platelets tab ----------
TestObject plateletsTab = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab') and .//span[normalize-space()='Platelets']]"
)
WebUI.waitForElementClickable(plateletsTab, 10)
WebUI.click(plateletsTab)

// ---------- STEP 10: Verify “Count” header is selected by default ----------
TestObject countHeader = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[@id='plateleteCountTab' and contains(@class,'active-selection-btn') and normalize-space()='Count']"
)
WebUI.verifyElementPresent(countHeader, 10)

// ---------- STEP 11: Click on the microscopic-view icon ----------
TestObject microViewIcon = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microViewIcon, 10)
WebUI.click(microViewIcon)

// ---------- STEP 12: Click on the “Overview” chevron button ----------
TestObject overviewBtn = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//button[@title='Overview']"
)
WebUI.waitForElementClickable(overviewBtn, 10)
WebUI.click(overviewBtn)

// ---------- STEP 13: Verify the overview-map overlay appears ----------
TestObject overviewMapBox = new TestObject().addProperty(
	'xpath',
	ConditionType.EQUALS,
	"//div[contains(@class,'ol-overviewmap-box')]"
)
WebUI.waitForElementVisible(overviewMapBox, 10)
WebUI.comment("Overview map overlay is visible")
