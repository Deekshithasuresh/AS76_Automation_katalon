import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

/ ────────────────────────────────────────────────────────────────────
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

// ─── 4) WAIT FOR THE “APPROVE REPORT” BUTTON ─────────────────────────────
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment("‘Approve report’ is now visible.")

// ─── 5) CLICK APPROVE & CONFIRM ──────────────────────────────────────────
WebUI.click(approveBtn)
TestObject confirmPopupTitle = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(normalize-space(),'Are you sure you want to approve')]"
)
WebUI.waitForElementVisible(confirmPopupTitle, 5)
WebUI.click(new TestObject()
	.addProperty('xpath', ConditionType.EQUALS,
		"//button[contains(@class,'backtoReportBtn') and normalize-space()='Confirm']"))

// ─── 6) ADD SUPPORTING IMAGES ────────────────────────────────────────────
TestObject btnAddImgs = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[text()='Add supporting images']]"
)
WebUI.waitForElementClickable(btnAddImgs, 10)
WebUI.click(btnAddImgs)

// ─── 7) MODIFY PATCHES (updated) ────────────────────────────────────────
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> modifyBtns = driver.findElements(By.xpath("//button[.//span[text()='Modify']]"))

// how many total patches you want selected (including the first two)
int desiredTotal = 4

modifyLoop:
for (int i = 0; i < modifyBtns.size(); i++) {
	modifyBtns.get(i).click()
	WebUI.delay(1)

	// wait for the list of patches to render
	WebUI.waitForElementVisible(
		new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//ul[contains(@class,'patches-image-list')]"),
		5
	)

	// grab **all** the <li> items in the grid
	List<WebElement> allPatches = driver.findElements(
		By.cssSelector("ul.patches-image-list li.MuiImageListItem-root")
	)
	int total = allPatches.size()

	if (total < 4) {
		// not enough to bother: cancel and try next
		WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
			"//button[contains(@class,'cancel-btn') and normalize-space()='Cancel']"))
		WebUI.delay(1)
		continue
	}

	// first two are auto-selected; click the next ones up to desiredTotal
	int toClick = Math.min(total, desiredTotal)
	for (int j = 2; j < toClick; j++) {
		allPatches.get(j).click()
	}

	// now confirm “Add to report”
	WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//button[contains(@class,'save-btn') and normalize-space()='Add to report']"))
	WebUI.delay(1)
	break modifyLoop
}

// ─── 8) FINAL APPROVE (reuse approveBtn) ─────────────────────────────────
WebUI.click(approveBtn)
WebUI.waitForElementVisible(confirmPopupTitle, 5)
WebUI.click(new TestObject()
	.addProperty('xpath', ConditionType.EQUALS,
		"//button[contains(@class,'backtoReportBtn') and normalize-space()='Approve report']"))
WebUI.delay(120)

// ─── 9) OPEN HISTORY & CAPTURE ───────────────────────────────────────────
TestObject kebab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
TestObject history = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]"
)

WebUI.click(kebab)
WebUI.waitForElementClickable(history, 5)
WebUI.click(history)

WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
for (int k = 0; k < Math.min(2, entries.size()); k++) {
	println entries.get(k).getText().trim()
}

WebUI.takeScreenshot("HistoryPage.png")

