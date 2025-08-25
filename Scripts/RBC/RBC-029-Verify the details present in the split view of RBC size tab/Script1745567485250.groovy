import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login as Login

Login lg = new Login()

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Size'))

// Build TestObject dynamically with your XPath
TestObject rbcCell = new TestObject('dynamicRbcCell')
rbcCell.addProperty("xpath", ConditionType.EQUALS, "//div[@class='rbc-cell-body']/div[not(contains(@class,'cell-row not-clickable'))][1]")

// Wait until it is clickable
WebUI.waitForElementClickable(rbcCell, 10)

// Click on it
WebUI.click(rbcCell)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/img_Platelets_split-view_1_2 (1)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/Home_zoom_CTA'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/zoom_in_button'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/zoom_out_button'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/Overview_CTA'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/Overview_CTA'), 10)




