import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))
new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(),'PBS')]").with {
	WebUI.waitForElementPresent(it, 10)
}

// 2) BOOKMARK the first “To be reviewed” report
String rowXp = "(//tr[.//span[normalize-space(text())='To be reviewed']])[1]"
TestObject toBeReviewed = new TestObject().addProperty('xpath', ConditionType.EQUALS, rowXp)
assert WebUI.waitForElementClickable(toBeReviewed, 5) : "No 'To be reviewed' row found"

TestObject bkIcon = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	rowXp + "//td[1]//img[contains(@src,'bookmark')]")
WebUI.waitForElementVisible(bkIcon, 5)
if (!WebUI.getAttribute(bkIcon, 'src').contains('bookmark-filled')) {
	WebUI.click(bkIcon)
	new TestObject().addProperty('xpath', ConditionType.EQUALS,
		"//div[normalize-space(text())='Bookmark saved']").with {
		WebUI.waitForElementVisible(it, 5)
	}
	WebUI.comment("✅ Bookmark saved")
} else {
	WebUI.comment("🏷️ Already bookmarked")
}

// 3) OPEN that report
TestObject slideCell = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	rowXp + "//td[3]")
WebUI.click(slideCell)
WebUI.waitForPageLoad(10)

WebDriver driver = DriverFactory.getWebDriver()

// 4) COMMENT LIFECYCLE
// 4a) ADD
TestObject commentIcon = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	rowXp + "//td[2]//img[@alt='comment-icon']")
WebUI.waitForElementClickable(commentIcon, 5)
WebUI.click(commentIcon)

TestObject textarea = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'comment-container')]//textarea")
WebUI.waitForElementVisible(textarea, 5)
WebUI.clearText(textarea)
WebUI.setText(textarea, "This is my FIRST comment")

TestObject saveBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'comment-buttons')]//img[contains(@src,'save-icon')]")
WebUI.click(saveBtn)
WebUI.waitForElementNotVisible(textarea, 5)
WebUI.comment("✏️ Comment added")

// 4b) UPDATE
WebUI.click(commentIcon)
WebUI.waitForElementVisible(textarea, 5)
WebUI.clearText(textarea)
WebUI.setText(textarea, "This is my UPDATED comment")
WebUI.click(saveBtn)
WebUI.waitForElementNotVisible(textarea, 5)
WebUI.comment("🔄 Comment updated")

// 4c) DELETE
WebUI.click(commentIcon)
WebUI.waitForElementVisible(textarea, 5)
TestObject deleteBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'comment-buttons')]//img[contains(@src,'delete-icon')]")
WebUI.click(deleteBtn)
WebUI.waitForElementNotVisible(textarea, 5)
WebUI.comment("🗑️ Comment deleted")

// 5) HISTORY
TestObject kebab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]")
WebUI.click(kebab)
TestObject historyOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space(text())='History']]")
WebUI.click(historyOpt)
WebUI.waitForPageLoad(5)

// 6) VERIFY & SCREENSHOT
List<WebElement> entries = driver.findElements(By.cssSelector("li.css-1ecsk3j"))
assert entries.size() > 0 : "❌ No history entries!"
entries.eachWithIndex { WebElement e, int i ->
	println("History ${i+1}: ${e.getText().trim()}")
}
WebUI.takeScreenshot("HistoryPage.png")


