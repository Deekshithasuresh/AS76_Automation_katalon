import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor
import java.nio.file.Files
import java.nio.file.Paths

// ────────────────────────────────────────────────────────────────────
// Helper: take screenshot + return full Base64
String snapAndBase64(String filename) {
	String path = "${RunConfiguration.getReportFolder()}/${filename}"
	WebUI.takeScreenshot(path)
	return Files.readAllBytes(Paths.get(path)).encodeBase64().toString()
}

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
// STEP 3: Switch to Platelets tab
TestObject pltTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(pltTab, 10)
WebUI.click(pltTab)

// ────────────────────────────────────────────────────────────────────
// STEP 4: Activate Microscopic view & wait 120s for render
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)
WebUI.delay(120)

// ────────────────────────────────────────────────────────────────────
// STEP 5: Hover & click “mark area” CTA, wait 60s
TestObject markAreaBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]")
WebUI.waitForElementVisible(markAreaBtn, 60)
WebUI.mouseOver(markAreaBtn)
WebUI.delay(1)
WebUI.click(markAreaBtn)
WebUI.delay(60)

// ────────────────────────────────────────────────────────────────────
// STEP 6: Click the “100×” option (second after zoom-tool)
TestObject btn100x = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[2]")
WebUI.waitForElementClickable(btn100x, 60)
WebUI.click(btn100x)

// ────────────────────────────────────────────────────────────────────
// STEP 7: Poll for quick info‐message, then wait for it to disappear
TestObject infoMsg = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'alert')]//div[contains(@class,'alert-message')]")

boolean flashed = false
for (int i = 0; i < 5; i++) {
	if (WebUI.verifyElementPresent(infoMsg, 1, FailureHandling.OPTIONAL)) {
		flashed = true; break
	}
	WebUI.delay(1)
}
WebUI.comment(flashed
	? "✔ Info message flashed after 100× click"
	: "⚠ Info message did NOT appear")

for (int i = 0; i < 10; i++) {
	if (!WebUI.verifyElementPresent(infoMsg, 1, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔ Info message has disappeared"); break
	}
	WebUI.delay(1)
}

// ────────────────────────────────────────────────────────────────────
// STEP 8: Wait 90s, screenshot & print full Base64 of 100× view
WebUI.delay(90)
String shot100 = "${RunConfiguration.getReportFolder()}/platelets_100x_view.png"
String b64_100x = snapAndBase64(shot100)
println "FULL Base64 100× view (Platelets): ${b64_100x}"

WebUI.comment("✅ 100× mark-area flow on Platelets tab complete.")
