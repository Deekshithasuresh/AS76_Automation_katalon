import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import imageUtils.blurChecker
import loginPackage.Login
import zoom.ZoomInOut

Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

//blurChecker blurCheck = new blurChecker()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('Under review')

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'),30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/split-view_button'),30)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/split-view_button'))

WebUI.delay(10)

WebUI.waitForPageLoad(4)

TestObject zoomout= findTestObject('Object Repository/RBC_Objects/Page_PBS/zoom_out_button')

TestObject ZoominButton = findTestObject('Object Repository/RBC_Objects/Page_PBS/zoom_in_button')

zoom.zoominout(6,zoomout)

zoom.zoominout(8,ZoominButton)
