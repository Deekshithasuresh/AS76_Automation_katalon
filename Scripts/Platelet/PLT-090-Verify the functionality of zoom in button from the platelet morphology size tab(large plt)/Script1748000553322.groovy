import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.maximizeWindow()

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

//WebUI.delay(10)
// Select a 1st row report
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)

// Step 1: Get cell rows
List<WebElement> cell_rows = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Platelets/Page_PBS/morphology _row'), 
    10)

int clumpCount = 0

int largePlateletCount = 0

WebElement clumpRow = null

WebElement largePlateletRow = null

for (WebElement row : cell_rows) {
    String cellName = row.findElement(By.xpath('.//div[1]')).getText()

    String countText = row.findElement(By.xpath('.//div[2]')).getText()

    int count = countText.isInteger() ? countText.toInteger() : 0

    if (cellName.contains('Platelet Clumps')) {
        clumpCount = count

        clumpRow = row
    }
    
    if (cellName.contains('Large Platelets')) {
        largePlateletCount = count

        largePlateletRow = row
    }
}

println("Large Platelets Count: $largePlateletCount")

println("Platelet Clumps Count: $clumpCount")

// ✅ Step 2: If Large Platelets count is zero
if ((largePlateletCount == 0) || (largePlateletRow == null)) {
    KeywordUtil.markFailed('ℹ️ No Large Platelets found, no patches available.') 
	return
	
} else {
    WebUI.click(findTestObject('Platelets/Page_PBS/split_view_img'))

    WebUI.delay(5)

	
	// === Function to get base64 image from canvas ===
	def getCanvasImageBase64 = {
		JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
		String base64 = js.executeScript("""
       const canvas = document.querySelector('#pbs-volumeViewport canvas');
       return canvas.toDataURL('image/png');
   """)
		return base64
	}
	

	// === Step 1: Capture canvas before zoom in ===
	WebUI.comment("Capturing canvas before zoom in...")
	String beforePanBase64 = getCanvasImageBase64()

	
    TestObject scaleBar = findTestObject('Object Repository/Platelets/Page_PBS/scale_bar')
	scaleBarValue = WebUI.getText(scaleBar)
	WebUI.comment("Scale bar value: " + scaleBarValue)
	
	
	
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_zoom-in'))
	
	
	WebUI.delay(5) // Let canvas settle
	
	// === Step 3: Capture canvas after zoom in ===
	WebUI.comment("Capturing canvas after zoom in...")
	String afterPanBase64 = getCanvasImageBase64()

	
	scaleBarValue1 = WebUI.getText(scaleBar)
	WebUI.comment("Scale bar value: " + scaleBarValue1)
	
	// === Step 4: Compare base64 data ===
	if (beforePanBase64 == afterPanBase64) {
		WebUI.comment("❌ Canvas did NOT change ")
	} else {
		WebUI.comment("✅ Canvas has changed ")
	}
	
	if (scaleBarValue == scaleBarValue1) {
		WebUI.comment("❌ Scale bar value did not change ")
	} else {
		WebUI.comment("✅ Scale bar value has changed from"+scaleBarValue+" to "+scaleBarValue1)
	}
	   
}



// === Function to get base64 image from canvas ===
def getCanvasImageBase64_1 = {
	JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
	String base64 = js.executeScript("""
       const canvas = document.querySelector('#pbs-volumeViewport canvas');
       return canvas.toDataURL('image/png');
   """)
	return base64
}
// === Function to save base64 PNG to file (optional for debug) ===
def saveBase64Image(String base64, String filePath) {
	String base64Data = base64.split(",")[1]
	byte[] imageBytes = Base64.decoder.decode(base64Data)
	InputStream is = new ByteArrayInputStream(imageBytes)
	BufferedImage image = ImageIO.read(is)
	ImageIO.write(image, "png", new File(filePath))
}
// === Step 1: Capture canvas before pan ===
WebUI.comment("Capturing canvas before pan...")
String beforePanBase64 = getCanvasImageBase64_1()
saveBase64Image(beforePanBase64, "before_pan.png") // Optional
// === Step 2: Perform pan with Robot ===
// These are the coordinates you already confirmed
int startX = 976
int startY = 406
int dragX = 1183
int dragY = 576
int steps = 50
Robot robot = new Robot()
robot.setAutoDelay(10)
// Activate canvas with single click
robot.mouseMove(startX, startY)
Thread.sleep(200)
robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
Thread.sleep(100)
robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
Thread.sleep(300)
// Drag with hold
robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
Thread.sleep(200)
for (int i = 0; i <= steps; i++) {
	int moveX = startX + (dragX * i / steps)
	int moveY = startY + (dragY * i / steps)
	robot.mouseMove(moveX, moveY)
	Thread.sleep(10)
}
Thread.sleep(100)
robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
WebUI.delay(1) // Let canvas settle
// === Step 3: Capture canvas after pan ===
WebUI.comment("Capturing canvas after pan...")
String afterPanBase64 = getCanvasImageBase64_1()
saveBase64Image(afterPanBase64, "after_pan.png") // Optional
// === Step 4: Compare base64 data ===
if (beforePanBase64 == afterPanBase64) {
	WebUI.comment("❌ Canvas did NOT change — pan may have failed.")
} else {
	WebUI.comment("✅ Canvas has changed — pan likely succeeded.")
}


