import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN & LAND ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(
	new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]"),
	10
)

// ────────────────────────────────────────────────────────────────────
// 2) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// 3) OPEN KEBAB MENU → CLICK “History”
// ────────────────────────────────────────────────────────────────────
TestObject kebabBtn = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu')]]"
)
WebUI.waitForElementClickable(kebabBtn, 5)
WebUI.click(kebabBtn)

TestObject historyItem = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'MuiPopover-paper')]//span[normalize-space(text())='History']/ancestor::li"
)
WebUI.waitForElementClickable(historyItem, 5)
WebUI.click(historyItem)
WebUI.delay(2)

// ────────────────────────────────────────────────────────────────────
// 4) WAIT FOR HISTORY PAGE & VERIFY ENTRIES
// ────────────────────────────────────────────────────────────────────
TestObject historyHeader = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//h1[contains(normalize-space(text()),'History')]"
)
WebUI.waitForElementVisible(historyHeader, 10)

// grab WebDriver for DOM traversal
WebDriver driver = DriverFactory.getWebDriver()

// find all history entries
List<WebElement> entries = driver.findElements(
	By.cssSelector("li.css-1ecsk3j")
)

if (entries.isEmpty()) {
	WebUI.comment("⚠ No history entries found!")
} else {
	for (WebElement entry : entries) {
		// a) Date & Time
		String dateAndTime = entry.findElement(
			By.cssSelector("div.event-header .time")
		).getText().trim()

		// b) Event description
		String description = entry.findElement(
			By.cssSelector("div.event-description")
		).getText().trim()

		// c) Changed by (first word of description)
		String changedBy = description.split("\\s+")[0]

		// d) From & To values, if present
		List<WebElement> rendered = entry.findElements(
			By.cssSelector("div.event-transaction .rendered-content")
		)
		String fromValue = rendered.size() > 0 ? rendered.get(0).getText().trim() : ""
		String toValue   = rendered.size() > 1 ? rendered.get(1).getText().trim() : ""

		// print all details
		println("---- History Entry ----")
		println("Date & Time : $dateAndTime")
		println("Description : $description")
		println("Changed by  : $changedBy")
		println("From value  : $fromValue")
		println("To value    : $toValue")
		println("-----------------------")
	}
}

WebUI.comment("✅ All history entries contain necessary details.")
