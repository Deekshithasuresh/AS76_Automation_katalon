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

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'),30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'))

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/split-view_button'),30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/split-view_button'))

WebUI.delay(10)

//zoom.checkPanningFunctionality()
TestObject zoomout= findTestObject('Object Repository/RBC_Objects/Page_PBS/zoom_out_button')

zoom.checkPanningFunctionality2(zoomout,0)

