import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
import zoom.ZoomInOut
Login lg = new Login()

ZoomInOut zoom = new ZoomInOut()

lg.login()

WebUI.delay(2)

lg.selectReportByStatus('To be reviewed')


//lg.assignOrReassignOnTabs('prem')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)
WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.click(findTestObject('RBC_Objects/Page_PBS/div_Macrocytes'))

WebUI.verifyElementPresent(findTestObject('RBC_Objects/Page_PBS/split-view_button'), 10)

WebUI.click(findTestObject('RBC_Objects/Page_PBS/split-view_button'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Macrocytes (2)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/canvas'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Anisocytosis (2)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Please review individual cell types for_bd04fe (1)'), 
    10)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Please review individual cell types for_bd04fe_1'), 
    'Please review individual cell types for viewing patches')

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (1)'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_No preview (1)'), 'No preview')

