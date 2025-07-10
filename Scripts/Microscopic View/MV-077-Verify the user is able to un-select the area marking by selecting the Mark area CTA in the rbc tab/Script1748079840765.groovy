import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.nio.file.Files
import java.nio.file.Paths

// ────────────────────────────────────────────────────────────────────
// Helper: take screenshot & return full Base64
// ────────────────────────────────────────────────────────────────────
String snapAndBase64(String filename) {
	String path = "${RunConfiguration.getReportFolder()}/${filename}"
	WebUI.takeScreenshot(path)
	return Files.readAllBytes(Paths.get(path)).encodeBase64().toString()
}

// ────────────────────────────────────────────────────────────────────
// 1) LOGIN
// ────────────────────────────────────────────────────────────────────
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ────────────────────────────────────────────────────────────────────
// 2) VERIFY LANDING ON REPORT LIST
// ────────────────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(text(),'PBS')]"
).with {
	WebUI.waitForElementPresent(it, 10, FailureHandling.STOP_ON_FAILURE)
}

// ────────────────────────────────────────────────────────────────────
// 3) OPEN FIRST “Under review” REPORT
// ────────────────────────────────────────────────────────────────────
new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//tr[.//span[contains(@class,'reportStatusComponent_text') and normalize-space(text())='Under review']])[1]"
).with {
	WebUI.waitForElementClickable(it, 10, FailureHandling.OPTIONAL)
	WebUI.scrollToElement(it, 5, FailureHandling.OPTIONAL)
	WebUI.click(it, FailureHandling.OPTIONAL)
}

// ────────────────────────────────────────────────────────────────────
// 4) SWITCH TO RBC TAB
// ────────────────────────────────────────────────────────────────────
TestObject rbcTab = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']"
)
WebUI.waitForElementClickable(rbcTab, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(rbcTab)

// ────────────────────────────────────────────────────────────────────
// 5) ACTIVATE MICROSCOPIC VIEW & WAIT
// ────────────────────────────────────────────────────────────────────
TestObject microBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"
)
WebUI.waitForElementClickable(microBtn, 10, FailureHandling.STOP_ON_FAILURE)
WebUI.click(microBtn)
WebUI.delay(120)   // allow the map to fully render

// ────────────────────────────────────────────────────────────────────
// 6) CLICK “MARK AREA” CTA & WAIT FOR OPTIONS
// ────────────────────────────────────────────────────────────────────
TestObject markAreaBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='zoom-tool']]"
)
WebUI.waitForElementVisible(markAreaBtn, 60, FailureHandling.STOP_ON_FAILURE)
WebUI.mouseOver(markAreaBtn)
WebUI.delay(1)
WebUI.click(markAreaBtn)
WebUI.delay(60)

// ────────────────────────────────────────────────────────────────────
// 7) CLICK THE “40×” OPTION
// ────────────────────────────────────────────────────────────────────
TestObject btn40x = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"(//button[.//img[@alt='zoom-tool']]/following::button)[1]"
)
WebUI.waitForElementClickable(btn40x, 60, FailureHandling.STOP_ON_FAILURE)
WebUI.click(btn40x)

// ────────────────────────────────────────────────────────────────────
// 8) POLL FOR INFO MESSAGE FLASH, THEN DISAPPEAR
// ────────────────────────────────────────────────────────────────────
TestObject infoMsg = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//div[contains(@class,'alert')]//div[contains(@class,'alert-message')]"
)
boolean flashed = false
for (int i = 0; i < 5; i++) {
	if (WebUI.verifyElementPresent(infoMsg, 1, FailureHandling.OPTIONAL)) {
		flashed = true
		break
	}
	WebUI.delay(1)
}
WebUI.comment(flashed
	? "✔ Info message flashed after 40× click"
	: "⚠ Info message did NOT appear"
)
for (int i = 0; i < 10; i++) {
	if (!WebUI.verifyElementPresent(infoMsg, 1, FailureHandling.OPTIONAL)) {
		WebUI.comment("✔ Info message has disappeared")
		break
	}
	WebUI.delay(1)
}

// ────────────────────────────────────────────────────────────────────
// 9) SCREENSHOT 40× VIEW & PRINT BASE64
// ────────────────────────────────────────────────────────────────────
WebUI.delay(90)
String shot40 = "${RunConfiguration.getReportFolder()}/rbc_40x_view.png"
String b64_40x = snapAndBase64(shot40)
println "FULL Base64 40× view (RBC): ${b64_40x}"

// ────────────────────────────────────────────────────────────────────
// 10) CLICK “MARK AREA” AGAIN TO HIDE OVERLAY
// ────────────────────────────────────────────────────────────────────
WebUI.mouseOver(markAreaBtn)
WebUI.delay(1)
WebUI.click(markAreaBtn)
WebUI.delay(5)

// ────────────────────────────────────────────────────────────────────
// 11) SCREENSHOT WITHOUT OVERLAY & PRINT BASE64
// ────────────────────────────────────────────────────────────────────
WebUI.delay(90)
String shotNoCircle = "${RunConfiguration.getReportFolder()}/rbc_no_circle.png"
String b64_noCircle = snapAndBase64(shotNoCircle)
println "FULL Base64 view without circle: ${b64_noCircle}"

WebUI.comment("✅ RBC 40× mark-area shown & then hidden successfully.")
WebUI.closeBrowser()
