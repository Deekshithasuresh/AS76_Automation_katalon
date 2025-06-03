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


WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_as76admin st'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/h2_Are you sure you want to re-assign this slide'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/p_Assigned to'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/Xicon'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Unassign'))

//WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/RBC_text_verify'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/RBC_text_verify'), 'ABC123@#%^&*def456')

WebUI.back()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Logout'))

WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/input_username_loginId'), 'santosh')

WebUI.setEncryptedText(findTestObject('Object Repository/Report_Listing/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Sign In'))

TestObject searchBox = findTestObject("Object Repository/Report_Listing/Page_PBS/search_input_text")

		// Step 1: Enter partial text and perform search
		WebUI.setText(searchBox, slideID)
		WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
		WebUI.delay(3)
		
		
		TestObject filteredRows = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody//tr")
		List<WebElement> filteredResults = WebUiCommonHelper.findWebElements(filteredRows, 5)
		
		if (filteredResults.size() > 0) {
			filteredResults[0].click()
			WebUI.comment("Clicked on the first report row")
		} else {
			WebUI.comment("No report rows found to click")
		}

		def wbcText = 'ABC123@#%^&*def456'
		
		CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh', true)
		
		WebUI.waitForElementVisible(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmleditor-content dx-state-focused'),
		   10)
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmleditor-content dx-state-focused'))
		
		WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Templates_ql-editor ql-blank dx-htmleditor-content dx-state-focused'), wbcText)
		
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img'))
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_as76admin st'))
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/h2_Are you sure you want to re-assign this slide'))
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/p_Assigned to'))
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/Xicon'))
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Unassign'))
		
		//WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/RBC_text_verify'))
		
		WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_ABC123def456OKCancel'), 'ABC123@#%^&*def456')
		
		
		WebUI.back()
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/img_PBS_icon-img'))
		
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/li_Logout'))
		
		CustomKeywords.'generic.custumFunctions.login'()
		
	

