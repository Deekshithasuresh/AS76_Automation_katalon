import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO
import java.util.Base64

// ── helpers ────────────────────────────────────────────────────────────
// grab the #pbs-volumeViewport canvas as a data-URL
def getCanvasImageBase64 = {
	JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
	return js.executeScript("""
       const canvas = document.querySelector('#pbs-volumeViewport canvas');
       return canvas.toDataURL('image/png');
    """) as String
}

// write a base64 → PNG file
def saveBase64Image = { String b64, String path ->
	String payload = b64.split(',')[1]
	byte[] bytes = Base64.decoder.decode(payload)
	BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes))
	ImageIO.write(img, 'png', new File(path))
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

// ── 2) SWITCH TO RBC → MICROSCOPIC VIEW & WAIT 120s ─────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='RBC']"))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))
WebUI.delay(120)

// ── 3) ZOOM IN TWICE (120s BETWEEN) ─────────────────────────────────
TestObject zoomIn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
WebUI.waitForElementClickable(zoomIn, 30)
(1..2).each { i ->
	WebUI.click(zoomIn)
	WebUI.delay(120)
	WebUI.comment("✔ Completed zoom-in #${i}")
}

// ── 4) HOVER & CLICK LINE TOOL → WAIT 30s ────────────────────────────
TestObject lineTool = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[.//img[@alt='line-tool']]")
WebUI.waitForElementClickable(lineTool, 30)
WebUI.mouseOver(lineTool)
WebUI.click(lineTool)
WebUI.delay(30)

// ── 5) CAPTURE “BEFORE PAN” ─────────────────────────────────────────
WebUI.comment("🔍 Capturing canvas before pan…")
String beforeB64 = getCanvasImageBase64()
saveBase64Image(beforeB64, "${RunConfiguration.getReportFolder()}/rbc_before_pan.png")

// ── 6) PAN USING ROBOT (at 879,544) ─────────────────────────────────
int startX = 879, startY = 544
int dragX  = 200, dragY  = 100
int steps  = 50

Robot robot = new Robot()
robot.setAutoDelay(10)

// click-to-focus
robot.mouseMove(startX, startY)
Thread.sleep(200)
robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
Thread.sleep(100)
robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
Thread.sleep(300)

// drag loop
for (int i = 0; i <= steps; i++) {
	int x = (startX + (dragX * i / steps)).toInteger()
	int y = (startY + (dragY * i / steps)).toInteger()
	robot.mouseMove(x, y)
	Thread.sleep(10)
}
Thread.sleep(100)
robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
WebUI.delay(1)

// ── 7) CAPTURE “AFTER PAN” & LOG RESULT ────────────────────────────
WebUI.comment("🔍 Capturing canvas after pan…")
String afterB64 = getCanvasImageBase64()
saveBase64Image(afterB64, "${RunConfiguration.getReportFolder()}/rbc_after_pan.png")

if (beforeB64 == afterB64) {
	WebUI.comment("❌ Pan did NOT change the view.")
} else {
	WebUI.comment("✅ Pan succeeded — canvas has changed.")
}
