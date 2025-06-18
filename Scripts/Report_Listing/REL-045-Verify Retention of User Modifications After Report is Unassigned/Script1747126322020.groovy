import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)



String SlideIDXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/span[1]"  

TestObject assignedSlideID = new TestObject('assignedSlideID')
assignedSlideID.addProperty('xpath', ConditionType.EQUALS, SlideIDXpath)

String slideID = WebUI.getText(assignedSlideID)
WebUI.comment("Slide ID is: " + slideID)

def rbcMorphologyInput = 'ABC123@#%^&*def456'

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_RBC Morphology'), 'RBC Morphology')

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'), 
    0)

def rbcText = 'ABC123@#%^&*def456'

WebUI.waitForElementVisible(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'), 
   10)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'))

WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'), rbcText)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'))


WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/unassign_img'))//need to change element

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_as76admin st'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/h2_Are you sure you want to re-assign this slide'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/p_Assigned to'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/Xicon'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Unassign'))


WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmledi_1a290a'), 'ABC123@#%^&*def456')
