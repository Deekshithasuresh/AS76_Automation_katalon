import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// for List<>
import java.util.List


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

// wait for the Approve button
WebUI.delay(2)
WebUI.waitForElementVisible(approveBtn, 10)
WebUI.comment(" 'Approve report' is now visible.")

// ────────────────────────────────────────────────────────────────────
// 3) APPROVE REPORT BUTTON
// ────────────────────────────────────────────────────────────────────
TestObject btnApprove = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//span[contains(normalize-space(),'Approve report')]]")
WebUI.waitForElementClickable(btnApprove, 10)
WebUI.click(btnApprove)

// confirmation popup
TestObject popupConfirm = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[text()='Are you sure you want to approve']")
WebUI.waitForElementVisible(popupConfirm, 5)
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space(.)='Confirm']"))

// 1) click the blue “Approve report” button in header
TestObject btnApproveHeader = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[normalize-space()='Approve report']/ancestor::button"
)
WebUI.waitForElementClickable(btnApproveHeader, 10)
WebUI.click(btnApproveHeader)

// 2) confirm the “Are you sure you want to approve?” popup
TestObject popupFirst = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'Are you sure you want to approve')]"
)
WebUI.waitForElementVisible(popupFirst, 5)
TestObject btnConfirmFirst = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space()='Confirm']"
)
WebUI.click(btnConfirmFirst)

// 3) handle the “Approve without supporting images?” warning
TestObject popupWarn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'without supporting images')]"
)
WebUI.waitForElementVisible(popupWarn, 5)
TestObject btnConfirmNoImgs = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[contains(@class,'backtoReportBtn') and normalize-space()='Approve report']"
)
WebUI.click(btnConfirmNoImgs)

// 4) wait for final processing
WebUI.delay(120)

// 5) Open kebab → History
TestObject btnKebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.click(btnKebab)
TestObject menuHistory = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]"
)
WebUI.click(menuHistory)

// 6) grab & assert the latest entry
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "li.css-1ecsk3j"),
	10
)
List<WebElement> historyEntries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
assert historyEntries.size() >= 1 : "No history entries found!"

String first = historyEntries.get(0).getText().trim()
println "Latest history entry: ${first}"
assert first.toLowerCase().contains("approved the report") :
	   "Expected entry to mention approval, but was: ${first}"

// optional: screenshot
WebUI.takeScreenshot("History_ApproveOnly.png")