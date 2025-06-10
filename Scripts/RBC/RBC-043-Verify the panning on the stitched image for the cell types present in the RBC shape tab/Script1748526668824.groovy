import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import org.openqa.selenium.JavascriptExecutor

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.File
import java.util.Base64
import javax.imageio.ImageIO


import loginPackage.Login
import zoom.ZoomInOut

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('Under review')

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'),30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'),30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'))

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/split-view_button'),30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/split-view_button'))

WebUI.delay(10)

TestObject zoomout= findTestObject('Object Repository/RBC_Objects/Page_PBS/zoom_out_button')

zoom.checkPanningFunctionality2(zoomout,0)

//// === Function to get base64 image from canvas ===
//def getCanvasImageBase64_1 = {
//	JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
//	String base64 = js.executeScript("""
//       const canvas = document.querySelector('#pbs-volumeViewport canvas');
//       return canvas.toDataURL('image/png');
//   """)
//	return base64
//}
//// === Function to save base64 PNG to file (optional for debug) ===
//def saveBase64Image(String base64, String filePath) {
//	String base64Data = base64.split(",")[1]
//	byte[] imageBytes = Base64.decoder.decode(base64Data)
//	InputStream is = new ByteArrayInputStream(imageBytes)
//	BufferedImage image = ImageIO.read(is)
//	ImageIO.write(image, "png", new File(filePath))
//}
//// === Step 1: Capture canvas before pan ===
//WebUI.comment("Capturing canvas before pan...")
//String beforePanBase64 = getCanvasImageBase64_1()
//saveBase64Image(beforePanBase64, "before_pan.png") // Optional
//println(beforePanBase64)
//// === Step 2: Perform pan with Robot ===
//// These are the coordinates you already confirmed
//int startX = 1100
//int startY = 550
//int dragX = 200
//int dragY = 100
//int steps = 50
//
//Robot robot = new Robot()
//robot.setAutoDelay(10)
//// Activate canvas with single click
//robot.mouseMove(startX, startY)
//Thread.sleep(200)
//robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
//Thread.sleep(100)
//robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
//Thread.sleep(300)
//// Drag with hold
//robot.mousePress(InputEvent.BUTTON1_DOWN_MASK)
//Thread.sleep(200)
//for (int i = 0; i <= steps; i++) {
//	int moveX = startX + (dragX * i / steps)
//	int moveY = startY + (dragY * i / steps)
//	robot.mouseMove(moveX, moveY)
//	Thread.sleep(10)
//}
//Thread.sleep(100)
//robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK)
//WebUI.delay(1) // Let canvas settle
//// === Step 3: Capture canvas after pan ===
//WebUI.comment("Capturing canvas after pan...")
//String afterPanBase64 = getCanvasImageBase64_1()
//saveBase64Image(afterPanBase64, "after_pan.png") // Optional
//// === Step 4: Compare base64 data ===
//if (beforePanBase64 == afterPanBase64) {
//	WebUI.comment(":x: Canvas did NOT change — pan may have failed.")
//} else {
//	WebUI.comment(":white_check_mark: Canvas has changed — pan likely succeeded.")
//}

