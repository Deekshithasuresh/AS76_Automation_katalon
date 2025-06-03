import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO
import java.util.Base64

// === helper to grab the canvas data-URL ===
def getCanvasImageBase64 = {
	JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
	return js.executeScript("""
       const canvas = document.querySelector('#pbs-volumeViewport canvas');
       return canvas.toDataURL('image/png');
    """) as String
}

// === optional helper to dump Base64 to file for debugging ===
def saveBase64Image = { String base64, String filePath ->
	String payload = base64.split(',')[1]
	byte[] bytes = Base64.decoder.decode(payload)
	BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes))
	ImageIO.write(img, 'png', new File(filePath))
}

// ─── STEP 1: Login to PBS ──────────────────────────────────────────
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Report viewer/Page_PBS/input_username_loginId'), 'adminuserr')
WebUI.setEncryptedText(
	findTestObject('Report viewer/Page_PBS/input_password_loginPassword'),
	'JBaPNhID5RC7zcsLVwaWIA=='
)
WebUI.click(findTestObject('Report viewer/Page_PBS/button_Sign In'))

// ─── STEP 2: Open any “To be reviewed” or “Under review” report ──
TestObject toBe = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[normalize-space()='To be reviewed']")
TestObject under = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//span[contains(@class,'reportStatusComponent_text') and normalize-space()='Under review']")
if (WebUI.waitForElementPresent(toBe, 5)) {
	WebUI.click(toBe)
} else {
	WebUI.click(under)
}

// ─── STEP 3: Click on the Platelets tab ──────────────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='Platelets']"))

// ─── STEP 4: Activate Microscopic view & wait 30s ─────────────────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))
WebUI.delay(30)

// ─── STEP 5: Capture “before pan” ─────────────────────────────────
WebUI.comment("🔍 Capturing canvas before pan…")
String beforePan = getCanvasImageBase64()
saveBase64Image(beforePan, 'platelets_before_pan.png')

// ─── STEP 6: Perform pan with Robot (coords 650,500) ──────────────
int startX = 650
int startY = 500
int dragX  = 200
int dragY  = 100
int steps  = 50

Robot robot = new Robot()
robot.setAutoDelay(10)

// focus the canvas
robot.mouseMove(startX, startY); Thread.sleep(200)
robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); Thread.sleep(100)
robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK); Thread.sleep(300)

// drag
robot.mousePress(InputEvent.BUTTON1_DOWN_MASK); Thread.sleep(200)
(0..steps).each { i ->
	int x = startX + (dragX * i / steps)
	int y = startY + (dragY * i / steps)
	robot.mouseMove(x, y)
	Thread.sleep(10)
}
Thread.sleep(100)
robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
WebUI.delay(1)

// ─── STEP 7: Capture “after pan” ──────────────────────────────────
WebUI.comment("🔍 Capturing canvas after pan…")
String afterPan = getCanvasImageBase64()
saveBase64Image(afterPan, 'platelets_after_pan.png')

// ─── STEP 8: Compare base64 data ─────────────────────────────────
if (beforePan == afterPan) {
	WebUI.comment("❌ Platelets canvas did NOT change — pan may have failed.")
} else {
	WebUI.comment("✅ Platelets canvas changed — pan succeeded.")
}
