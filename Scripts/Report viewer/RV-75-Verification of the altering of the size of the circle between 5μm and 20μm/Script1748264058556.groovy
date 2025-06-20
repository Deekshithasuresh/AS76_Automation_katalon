import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys

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
// ────────────────────────────────────────────────────────────────────
// 2) SWITCH TO WBC → MICROSCOPIC VIEW & WAIT 120s
// ────────────────────────────────────────────────────────────────────
TestObject wbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']")
WebUI.waitForElementClickable(wbcTab, 10)
WebUI.click(wbcTab)

TestObject microViewBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microViewBtn, 10)
WebUI.click(microViewBtn)

WebUI.delay(120)  // wait for the OpenLayers viewer to render

// ────────────────────────────────────────────────────────────────────
// 3) HOVER & CLICK CIRCLE TOOL → WAIT 30s
// ────────────────────────────────────────────────────────────────────
TestObject circleButton = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='circle-tool']]")
WebUI.waitForElementClickable(circleButton, 30)
WebUI.mouseOver(circleButton)
WebUI.click(circleButton)
WebUI.delay(30)

// screenshot the initial 7 μm circle
String circle7Screenshot = "${RunConfiguration.getReportFolder()}/wbc_circle_7um.png"
WebUI.takeScreenshot(circle7Screenshot)
WebUI.comment("✔ Captured 7 μm circle screenshot: ${circle7Screenshot}")

// ────────────────────────────────────────────────────────────────────
// 4) AFTER DRAWING THE FIRST CIRCLE, ITERATE THROUGH SIZES 5→20
// ────────────────────────────────────────────────────────────────────
TestObject measInput = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//input[@id='outlined-start-adornment']")
TestObject overlayText = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'-inner') and contains(text(),'µm')]")

// your list of diameters to test:
List<String> diameters = ['5','6','10','15','20']

for (String dia : diameters) {
	// 1) focus & clear the old value
	WebUI.waitForElementVisible(measInput, 10)
	WebUI.click(measInput)
	WebUI.sendKeys(measInput, Keys.chord(Keys.COMMAND, 'a', Keys.DELETE))
	// 2) type the new diameter + Enter
	WebUI.setText(measInput, dia)
	WebUI.sendKeys(measInput, Keys.ENTER)
	WebUI.delay(5)  // let the circle redraw

	// 3) verify the input field
	String actual = WebUI.getAttribute(measInput, 'value').trim()
	WebUI.verifyMatch(actual, dia, false, FailureHandling.STOP_ON_FAILURE)
	WebUI.comment("✔ Input field set to ${dia} µm")

	// 4) verify the overlay in the canvas
	WebUI.waitForElementVisible(overlayText, 5)
	String ov = WebUI.getText(overlayText).trim()
	WebUI.verifyMatch(ov, "${dia} µm", false, FailureHandling.STOP_ON_FAILURE)
	WebUI.comment("✔ Canvas overlay reads ${dia} µm")

	// 5) screenshot each state if you like
	String shot = "${RunConfiguration.getReportFolder()}/circle_${dia}um.png"
	WebUI.takeScreenshot(shot)
	WebUI.comment("🔍 Captured circle at ${dia} µm: ${shot}")
}

WebUI.comment("✅ All circle‐tool diameters verified.")
