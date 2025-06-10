import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By

// STEP 1: LOGIN + NAVIGATE TO REPORT LIST
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Object Repository/Report viewer/Page_PBS/input_password_loginPassword'),
					  'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Object Repository/Report viewer/Page_PBS/button_Sign In'))
WebUI.waitForElementPresent(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"), 10)

// STEP 2: PICK AN “Under review” ROW
WebDriver driver = DriverFactory.getWebDriver()
List<WebElement> underRows = driver.findElements(
	By.xpath("//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']]")
)
assert !underRows.isEmpty() : "❌ No 'Under review' rows found"

// STEP 3: CLEAR FIRST NON-EMPTY “Assigned to”
boolean didClear = false
for (WebElement row : underRows) {
	WebElement inp = row.findElement(By.xpath(".//input[@id='assigned_to']"))
	if (inp.getAttribute("value").trim()) {
		WebElement clearBtn = row.findElement(
			By.xpath(".//button[.//svg[@data-testid='ClearIcon']]")
		)
		clearBtn.click()
		didClear = true
		break
	}
}
assert didClear : "❌ No non-empty 'Assigned to' field to clear"

// STEP 4: CONFIRM “Unassign” DIALOG
WebUI.waitForElementVisible(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'modal-title') and contains(.,'unassign this slide?')]/ancestor::div[@role='dialog']"), 5)
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[@role='dialog']//button[normalize-space()='Unassign']"))

// STEP 5: VERIFY FIELD IS BLANK
TestObject afterClear = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']]//input[@id='assigned_to']")
WebUI.waitForElementVisible(afterClear, 5)
String val = WebUI.getAttribute(afterClear, 'value').trim()
assert val == '' : "❌ Field not cleared; still has '${val}'"

// STEP 6: OPEN KEBAB MENU → HISTORY
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]"))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//li//span[normalize-space()='History']"))

// STEP 7: PRINT LATEST HISTORY ENTRY + SCREENSHOT
TestObject firstEntry = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//li[contains(@class,'css-1ecsk3j')])[1]")
WebUI.waitForElementVisible(firstEntry, 10)
String entryText = WebUI.getText(firstEntry).trim()
println "→ Latest history entry:\n${entryText}"
WebUI.takeScreenshot('HistoryPanel.png')
