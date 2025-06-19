import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.JavascriptExecutor
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import javax.imageio.ImageIO
import java.util.Base64

// === Function to get base64 image from canvas ===
def getCanvasImageBase64 = {
	JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
	return js.executeScript("""
       const canvas = document.querySelector('#pbs-volumeViewport canvas');
       return canvas.toDataURL('image/png');
    """) as String
}

// === Optional helper to save base64 PNG to file (for debug) ===
def saveBase64Image = { String base64, String filePath ->
	String payload = base64.split(',')[1]
	byte[] bytes = Base64.decoder.decode(payload)
	BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes))
	ImageIO.write(img, 'png', new File(filePath))
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

// ─── STEP 3: Click on the WBC tab and then Microscopic view ───────
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'cell-tab')]//span[normalize-space()='WBC']"))
WebUI.click(new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//img[@alt='Microscopic view' and @aria-label='Microscopic view']"))

// ─── STEP 4: Wait 120s before zoom ─────────────────────────────────
WebUI.delay(120)

// ─── STEP 5: Click the “Zoom in” button once ──────────────────────
TestObject zoomInBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS,
	"//button[contains(@class,'ol-zoom-in') and @title='Zoom in']")
WebUI.waitForElementClickable(zoomInBtn, 30)
WebUI.click(zoomInBtn)

// ─── STEP 6: Let the map settle ────────────────────────────────────
WebUI.delay(5)

// ─── STEP 7: Capture “before pan” base64 ───────────────────────────
WebUI.comment("🔍 Capturing canvas before pan…")
String beforePan = getCanvasImageBase64()
saveBase64Image(beforePan, 'before_pan.png')

// ─── STEP 8: Perform pan with Robot at (865,432) ─────────────────
int startX = 865
int startY = 432
int dragX  = 200   // adjust if needed
int dragY  = 100   // adjust if needed
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
WebUI.delay(1)  // let canvas settle

// ─── STEP 9: Capture “after pan” base64 ────────────────────────────
WebUI.comment("🔍 Capturing canvas after pan…")
String afterPan = getCanvasImageBase64()
saveBase64Image(afterPan, 'after_pan.png')

// ─── STEP 10: Compare base64 data ─────────────────────────────────
if (beforePan == afterPan) {
	WebUI.comment("❌ Canvas did NOT change — pan may have failed.")
} else {
	WebUI.comment("✅ Canvas has changed — pan succeeded.")
}
