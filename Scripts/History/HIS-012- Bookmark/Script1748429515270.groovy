import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.unassignOrCancel'('Under review', true)

CustomKeywords.'generic.bookmark.manageCommentOnBookmarkedReport'("To be reviewed","hiii manju","How are you?")

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

// 6) OPEN HISTORY
TestObject kebab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
WebUI.click(kebab)
TestObject historyOpt = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space(text())='History']]"
)
WebUI.waitForElementClickable(historyOpt, 5)
WebUI.click(historyOpt)
WebUI.waitForPageLoad(5)

WebDriver driver = DriverFactory.getWebDriver()
// 7) DUMP & SCREENSHOT HISTORY ENTRIES

// 1️⃣ Get all history rows (li elements)
TestObject allHistoryItems = new TestObject('history_items')
allHistoryItems.addProperty('xpath', ConditionType.EQUALS, "//ul//li[@class='css-1ecsk3j']")

List<WebElement> historyRows = WebUiCommonHelper.findWebElements(allHistoryItems, 10)
assert historyRows.size() > 0 : "❌ No history rows found"

// 2️⃣ Get the first row
WebElement firstRow = historyRows.get(0)

// 3️⃣ Check header is "Bookmark Report"
WebElement firstHeader = firstRow.findElement(By.xpath(".//h4[@class='event-title']"))
String firstHeaderText = firstHeader.getText().trim()
println("📌 First entry title: " + firstHeaderText)
assert firstHeaderText == "Bookmark Report" : "❌ First entry is not 'Bookmark Report'"

// 4️⃣ Extract user and validate it's 'manju'
WebElement descElement = firstRow.findElement(By.xpath(".//div[@class='event-description']"))
String descriptionText = descElement.getText().trim()
println("👤 Description: " + descriptionText)
assert descriptionText.toLowerCase().contains("manju") : "❌ First entry was not bookmarked by 'manju'"

// 5️⃣ Extract timestamp (optional)
WebElement timeElement = firstRow.findElement(By.xpath(".//div[@class='time']"))
String timeText = timeElement.getText().trim()
println("🕒 Time of bookmark: " + timeText)
List<WebElement> entries = driver.findElements(By.xpath("(//div[@class='event-header']//h4)[1]"))
assert entries.size() > 0 : "❌ No history entries!"
entries.eachWithIndex { WebElement e, int i ->
	println("History ${i+1}: ${e.getText().trim()}")
}
WebUI.takeScreenshot("HistoryPage.png")
