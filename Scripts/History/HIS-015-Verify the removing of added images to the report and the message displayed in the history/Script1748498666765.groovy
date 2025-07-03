import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// —————————————————————————————————————————————
// 1) LOGIN & NAVIGATE
// —————————————————————————————————————————————
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under Review")
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

// —————————————————————————————————————————————
// 2) APPROVE REPORT → CONFIRM
// —————————————————————————————————————————————
TestObject btnApprove = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[normalize-space()='Approve report']]"
)
TestObject popupTitleApprove = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'Are you sure you want to approve')]"
)
TestObject btnConfirmApprove = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'modal-actions')]//button[normalize-space()='Confirm']"
)

WebUI.waitForElementClickable(btnApprove, 10)
WebUI.click(btnApprove)
WebUI.waitForElementVisible(popupTitleApprove, 5)
WebUI.click(btnConfirmApprove)

// —————————————————————————————————————————————
// 3) ADD SUPPORTING IMAGES
// —————————————————————————————————————————————
TestObject btnAddImgs = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//span[normalize-space()='Add supporting images']]"
)
WebUI.waitForElementClickable(btnAddImgs, 10)
WebUI.click(btnAddImgs)

// —————————————————————————————————————————————
// 4) REMOVE FIRST CHECKED PATCH
// —————————————————————————————————————————————
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> checkedBoxes = driver.findElements(
	By.cssSelector("div.cell-selection-item input[type='checkbox']:checked")
)
assert checkedBoxes.size() > 0 : "❗ No checked patches found to remove"
WebElement firstBox = checkedBoxes[0]
String cellTypeRaw = firstBox.getAttribute("name")               // e.g. "neutrophils"
String cellType = cellTypeRaw.capitalize()                       // "Neutrophils"
WebUI.comment("⏪ Removing patches of cell type: ${cellType}")
firstBox.findElement(By.xpath("..")).click()
WebUI.delay(1)

// —————————————————————————————————————————————
// 5) HEADER “Back to report” → OPEN CONFIRMATION POPUP
// —————————————————————————————————————————————
TestObject lblHeaderBack = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'action-label') and normalize-space()='Back to report']"
)
WebUI.waitForElementVisible(lblHeaderBack, 10)

TestObject btnHeaderBack = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'action-label') and normalize-space()='Back to report']/preceding-sibling::button"
)
WebUI.waitForElementClickable(btnHeaderBack, 10)
WebUI.click(btnHeaderBack)

// —————————————————————————————————————————————
// 6) POPUP “Back to report” → CONFIRM LOSS OF CHANGES
// —————————————————————————————————————————————
TestObject btnPopupBack = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//div[contains(@class,'modal-actions')]//button[normalize-space()='Back to report']"
)
WebUI.waitForElementVisible(btnPopupBack, 10)
WebUI.click(btnPopupBack)
WebUI.delay(1)

// —————————————————————————————————————————————
// 7) OPEN KEBAB & CLICK “History”
// —————————————————————————————————————————————
TestObject btnKebab = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"
)
TestObject menuHistory = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]"
)
WebUI.waitForElementClickable(btnKebab, 10)
WebUI.click(btnKebab)
WebUI.waitForElementClickable(menuHistory, 5)
WebUI.click(menuHistory)

// —————————————————————————————————————————————
// 8) VERIFY HISTORY ENTRIES (find first matching only)
//    • “Report Sign off”
//    • “manju removed the patches of {cellType} for the final report”
// —————————————————————————————————————————————
// wait for items to appear
WebUI.waitForElementVisible(
	new TestObject().addProperty('css', ConditionType.EQUALS, "ul.events-container > li.css-1ecsk3j"),
	10
)
List<WebElement> historyItems = driver.findElements(
	By.cssSelector("ul.events-container > li.css-1ecsk3j")
)
assert historyItems.size() > 0 : "No history items found"

// find first li containing “Report Sign off”
WebElement signOffItem = historyItems.find { li ->
	li.getText().contains("Report Sign off")
}
assert signOffItem != null : "‘Report Sign off’ entry not found"

// find first li containing the exact removal text
String expectedRemoval = "manju removed the patches of ${cellType} for the final report"
WebElement removalItem = historyItems.find { li ->
	li.getText().toLowerCase().contains(expectedRemoval.toLowerCase())
}
assert removalItem != null : "Removal entry ‘${expectedRemoval}’ not found"

WebUI.comment("✅ Found history entries:")
WebUI.comment("   • Report Sign off")
WebUI.comment("   • ${expectedRemoval}")

// —————————————————————————————————————————————
// 9) SCREENSHOT & COMPLETE
// —————————————————————————————————————————————
WebUI.takeScreenshot("History_Verification.png")
WebUI.comment("🎉 HIS-015: All checks passed!")
