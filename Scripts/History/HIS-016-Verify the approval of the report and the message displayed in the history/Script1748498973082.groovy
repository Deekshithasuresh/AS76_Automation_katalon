import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

// —————————————————————————————————————————————
// Helpers to build locators on the fly
// —————————————————————————————————————————————
TestObject btnBySpan(String text) {
	TestObject t = new TestObject("btn_${text}")
	// grabs the first <button> that has a <span> equal to our text
	t.addProperty('xpath', ConditionType.EQUALS,
		"(//button[.//span[normalize-space()='$text']])[1]")
	return t
}

TestObject modalBtn(String text) {
	TestObject t = new TestObject("modalBtn_${text}")
	t.addProperty('xpath', ConditionType.EQUALS,
		"//div[contains(@class,'modal-actions')]//button[normalize-space()='$text']")
	return t
}

// —————————————————————————————————————————————
// 1) LOGIN, NAVIGATE TO “To be reviewed” and ASSIGN TO manju
// —————————————————————————————————————————————
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// —————————————————————————————————————————————
// 2) FIRST “Approve report” click → CONFIRM
// —————————————————————————————————————————————
TestObject headerApprove = btnBySpan('Approve report')
WebUI.waitForElementClickable(headerApprove, 10)
WebUI.click(headerApprove)

TestObject confirm1 = modalBtn('Confirm')
WebUI.waitForElementClickable(confirm1, 5)
WebUI.click(confirm1)

// —————————————————————————————————————————————
// 3) SECOND “Approve report” (warning) → CONFIRM
// —————————————————————————————————————————————
WebUI.waitForElementClickable(headerApprove, 10)
WebUI.click(headerApprove)

// wait for the “without supporting images” warning
TestObject warnNoImgs = new TestObject('warnNoImgs')
warnNoImgs.addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(text(),'without supporting images')]")
WebUI.waitForElementVisible(warnNoImgs, 5)

// now confirm inside the modal with the “Approve report” button
TestObject confirm2 = modalBtn('Approve report')
WebUI.waitForElementClickable(confirm2, 5)
WebUI.click(confirm2)

// —————————————————————————————————————————————
// 4) WAIT FOR “Report Approved” TO APPEAR
// —————————————————————————————————————————————
TestObject toastOk = new TestObject('toastOk')
toastOk.addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(text(),'Report Approved')]")
WebUI.waitForElementVisible(toastOk, 30)

// —————————————————————————————————————————————
// 5) OPEN KEBAB MENU & CLICK “History”
// —————————————————————————————————————————————
TestObject btnKebab = new TestObject('btnKebab')
btnKebab.addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[contains(@src,'kebab_menu.svg')]]")
TestObject optHistory = new TestObject('optHistory')
optHistory.addProperty('xpath', ConditionType.EQUALS,
	"//li[.//span[normalize-space()='History']]")

WebUI.waitForElementClickable(btnKebab, 10)
WebUI.click(btnKebab)
WebUI.waitForElementClickable(optHistory, 5)
WebUI.click(optHistory)

// —————————————————————————————————————————————
// 6) VERIFY FIRST HISTORY ENTRY
// —————————————————————————————————————————————
WebDriver driver = DriverFactory.getWebDriver()

// wait for the first history title to render
WebUI.waitForElementVisible(
	new TestObject('firstTitle').addProperty(
		'xpath', ConditionType.EQUALS,
		"(//h4[contains(@class,'event-title')])[1]"
	),
	10
)

String histTitle = driver
	.findElement(By.xpath("(//h4[contains(@class,'event-title')])[1]"))
	.getText().trim()
String histDesc = driver
	.findElement(By.xpath("(//div[contains(@class,'event-description')])[1]"))
	.getText().trim()

assert histTitle == "Report Sign off" :
	   "Expected first history title 'Report Sign off' but was '${histTitle}'"
assert histDesc.toLowerCase().contains("manju approved the report") :
	   "Expected history description to contain 'manju approved the report' but was '${histDesc}'"

WebUI.comment("✅ HIS-016: History verified: [${histTitle}] – [${histDesc}]")
WebUI.takeScreenshot("History_Approval.png")
