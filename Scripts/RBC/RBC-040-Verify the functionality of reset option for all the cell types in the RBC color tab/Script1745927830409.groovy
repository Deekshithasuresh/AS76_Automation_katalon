import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
import zoom.ZoomInOut
Login lg = new Login()
ZoomInOut zoom = new ZoomInOut()

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'),10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))

TestObject homeZoomButton = findTestObject('Object Repository/RBC_Objects/Page_PBS/Home_zoom_CTA')

zoom.zoominout(1,homeZoomButton)

