import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN & NAVIGATE TO LIST
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('https://as76-pbs.sigtuple.com/login')
WebUI.maximizeWindow()
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// grab WebDriver for DOM interactions
WebDriver driver = DriverFactory.getWebDriver()

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “To be reviewed” REPORT
// ────────────────────────────────────────────────────────────────────
String toBeReviewedXpath = "(//tr[.//span[normalize-space(text())='To be reviewed']])[1]"
TestObject toBeReviewedRow = new TestObject()
	.addProperty('xpath', ConditionType.EQUALS, toBeReviewedXpath)
WebUI.waitForElementClickable(toBeReviewedRow, 10)
WebUI.click(toBeReviewedRow)


// ────────────────────────────────────────────────────────────────────
// 3) ASSIGN TO “admin” (scroll & click from dropdown)
// ────────────────────────────────────────────────────────────────────
// open the “Assigned to” dropdown
TestObject dropdownBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']/ancestor::div[contains(@class,'MuiAutocomplete-inputRoot')]//button"
)
WebUI.waitForElementClickable(dropdownBtn, 5)
WebUI.click(dropdownBtn)

// scroll to and click “admin”
TestObject adminOption = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//ul[contains(@class,'MuiAutocomplete-listbox')]//li[normalize-space(text())='admin']"
)
WebUI.waitForElementVisible(adminOption, 5)
WebUI.scrollToElement(adminOption, 5)
WebUI.click(adminOption)

// verify assignment
TestObject assignedInput = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//input[@id='assigned_to']"
)
WebUI.waitForElementAttributeValue(assignedInput, 'value', 'admin', 5)


// ────────────────────────────────────────────────────────────────────
// 4) FILL MORPHOLOGY FIELDS WITH “Test@1234”
// ────────────────────────────────────────────────────────────────────
// indices: 2=WBC, 3=Platelet, 4=Hemoparasite, 5=Impression
for (int idx = 2; idx <= 5; idx++) {
	String fieldXpath = "(//div[@data-placeholder='Add details' and @contenteditable='true'])[" + idx + "]"
	TestObject fieldObj = new TestObject().addProperty('xpath', ConditionType.EQUALS, fieldXpath)
	WebUI.waitForElementVisible(fieldObj, 5)
	WebUI.scrollToElement(fieldObj, 5)
	WebElement editor = driver.findElement(By.xpath(fieldXpath))
	editor.click()
	editor.sendKeys("Test@1234")
	WebUI.delay(1)
}


// ────────────────────────────────────────────────────────────────────
// 5) OPEN KEBAB MENU & CLICK “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

TestObject historyOpt = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)


// ────────────────────────────────────────────────────────────────────
// 6) VERIFY LATEST 5 HISTORY ENTRIES
// ────────────────────────────────────────────────────────────────────
List<WebElement> allEntries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
WebUI.verifyGreaterThanOrEqual(allEntries.size(), 5, FailureHandling.CONTINUE_ON_FAILURE)
for (int i = 0; i < 5; i++) {
	println("History entry ${i+1}: " + allEntries.get(i).getText().trim())
}
