import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.awt.Robot as Robot
import java.awt.event.InputEvent as InputEvent
import java.awt.image.BufferedImage as BufferedImage
import java.io.ByteArrayInputStream as ByteArrayInputStream
import java.io.File as File
import javax.imageio.ImageIO as ImageIO
import java.util.Base64 as Base64

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.maximizeWindow()

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

WebUI.delay(2)

// Select a sample
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.delay(2)

// === Function to get base64 image from canvas ===
def getCanvasImageBase64 = { 
    JavascriptExecutor js = ((DriverFactory.getWebDriver()) as JavascriptExecutor)

    String base64 = js.executeScript('\n       const canvas = document.querySelector(\'#pbs-volumeViewport canvas\');\n       return canvas.toDataURL(\'image/png\');\n   ')

    return base64
}

// === Function to save base64 PNG to file (optional for debug) ===
// === Step 1: Capture canvas before pan ===
WebUI.comment('Capturing canvas before pan...')

String beforePanBase64 = getCanvasImageBase64()

saveBase64Image(beforePanBase64, 'before_pan.png' // Optional
    )

// === Step 2: Perform pan with Robot ===
// These are the coordinates you already confirmed
int startX = 650

int startY = 500

int dragX = 200

int dragY = 100

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
    int moveX = startX + ((dragX * i) / steps)

    int moveY = startY + ((dragY * i) / steps)

    robot.mouseMove(moveX, moveY)

    Thread.sleep(10)
}

Thread.sleep(100)

robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)

WebUI.delay(1 // Let canvas settle
    )

// === Step 3: Capture canvas after pan ===
WebUI.comment('Capturing canvas after pan...')

String afterPanBase64 = getCanvasImageBase64()

saveBase64Image(afterPanBase64, 'after_pan.png' // Optional
    )

// === Step 4: Compare base64 data ===
if (beforePanBase64 == afterPanBase64) {
    WebUI.comment('❌ Canvas did NOT change — pan may have failed.')
} else {
    WebUI.comment('✅ Canvas has changed — pan likely succeeded.')
}

def saveBase64Image(String base64, String filePath) {
    String base64Data = base64.split(',')[1]

    byte[] imageBytes = Base64.decoder.decode(base64Data)

    InputStream is = new ByteArrayInputStream(imageBytes)

    BufferedImage image = ImageIO.read(is)

    ImageIO.write(image, 'png', new File(filePath))
}

