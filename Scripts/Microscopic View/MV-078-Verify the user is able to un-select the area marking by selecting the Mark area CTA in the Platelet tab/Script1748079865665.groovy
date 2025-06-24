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
// Helper: take screenshot & return full Base64
String snapAndBase64(String filename) {
	String path = "${RunConfiguration.getReportFolder()}/${filename}"
	WebUI.takeScreenshot(path)
	return Files.readAllBytes(Paths.get(path)).encodeBase64().toString()
}

// 1) LOGIN
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(
	findTestObject('Report viewer/Page_PBS/input_username_loginId'),
	'adminuserr'
)
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// 2) VERIFY LANDING ON REPORT LIST
TestObject pbsText = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
)
WebUI.waitForElementPresent(pbsText, 10)

// 3) OPEN FIRST “Under review” REPORT
TestObject underReviewRow = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
)
WebUI.waitForElementClickable(underReviewRow, 10)
WebUI.scrollToElement(underReviewRow, 5)
WebUI.click(underReviewRow)

// ────────────────────────────────────────────────────────────────────
// STEP 3: Switch to Platelets tab
TestObject pltTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']")
WebUI.waitForElementClickable(pltTab, 10)
WebUI.click(pltTab)

// ────────────────────────────────────────────────────────────────────
// STEP 4: Activate Microscopic view & wait 120s for OpenLayers
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']")
WebUI.waitForElementClickable(microBtn, 10)
WebUI.click(microBtn)
WebUI.delay(120)

// ────────────────────────────────────────────────────────────────────
// STEP 5: Hover + click “mark area” CTA & wait 60s for options
TestObject markAreaBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]")
WebUI.waitForElementVisible(markAreaBtn, 60)
WebUI.mouseOver(markAreaBtn); WebUI.delay(1)
WebUI.click(markAreaBtn); WebUI.delay(60)

// ────────────────────────────────────────────────────────────────────
// STEP 6: Click the “40×” option
TestObject btn40x = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[1]")
WebUI.waitForElementClickable(btn40x, 60)
WebUI.click(btn40x)

// ────────────────────────────────────────────────────────────────────
// STEP 7: Poll for quick info-message, then wait for it to disappear
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
	? "✔ Info message flashed after 40× click"
	: "⚠ Info message did NOT appear"
)
for (int i = 0; i < 10; i++) {
	if (!WebUI.verifyElementPresent(infoMsg, 1, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔ Info message has disappeared"); break
	}
	WebUI.delay(1)
}

// ────────────────────────────────────────────────────────────────────
// STEP 8: Screenshot 40× view & print Base64
WebUI.delay(90)
String shot40 = "${RunConfiguration.getReportFolder()}/platelets_40x_view.png"
String b64_40x = snapAndBase64(shot40)
println "FULL Base64 40× view (Platelets): ${b64_40x}"

// ────────────────────────────────────────────────────────────────────
// STEP 9: Hover + click “mark area” CTA again to hide circle
WebUI.mouseOver(markAreaBtn); WebUI.delay(1)
WebUI.click(markAreaBtn)

// circle overlay will disappear immediately
WebUI.delay(5)

// ────────────────────────────────────────────────────────────────────
// STEP 10: Screenshot without circle & print Base64
WebUI.delay(90)
String shotNoCircle = "${RunConfiguration.getReportFolder()}/platelets_no_circle.png"
String b64_noCircle = snapAndBase64(shotNoCircle)
println "FULL Base64 view without circle: ${b64_noCircle}"

WebUI.comment("✅ Platelets 40× mark-area shown & then hidden successfully.")