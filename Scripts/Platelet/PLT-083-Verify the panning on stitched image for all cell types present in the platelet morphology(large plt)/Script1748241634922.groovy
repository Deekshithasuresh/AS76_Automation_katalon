import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

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
// Select a sample
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

    WebUI.delay(2)

    def getCanvasImageBase64 = { 
        JavascriptExecutor js = ((DriverFactory.getWebDriver()) as JavascriptExecutor)

        String base64 = js.executeScript('\n       const canvas = document.querySelector(\'#pbs-volumeViewport canvas\');\n       return canvas.toDataURL(\'image/png\');\n   ')

        return base64
    }

    WebUI.comment('Capturing canvas before pan...')

    String beforePanBase64 = getCanvasImageBase64()

    saveBase64Image(beforePanBase64, 'before_pan.png')

	int startX = 976
	int startY = 406
	int dragX = 1183
	int dragY = 576
	int steps = 50
	
    Robot robot = new Robot()

    robot.setAutoDelay(10)

    robot.mouseMove(startX, startY)

    Thread.sleep(200)

    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)

    Thread.sleep(100)

    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)

    Thread.sleep(300)

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

    WebUI.delay(1)

    WebUI.comment('Capturing canvas after pan...')

    String afterPanBase64 = getCanvasImageBase64()

    saveBase64Image(afterPanBase64, 'after_pan.png')

    if (beforePanBase64 == afterPanBase64) {
        WebUI.comment('❌ Canvas did NOT change — pan may have failed.')
    } else {
        WebUI.comment('✅ Canvas has changed — pan likely succeeded.')
    }
}

def saveBase64Image(String base64, String filePath) {
    String base64Data = base64.split(',')[1]

    byte[] imageBytes = Base64.decoder.decode(base64Data)

    InputStream is = new ByteArrayInputStream(imageBytes)

    BufferedImage image = ImageIO.read(is)

    ImageIO.write(image, 'png', new File(filePath))
}

