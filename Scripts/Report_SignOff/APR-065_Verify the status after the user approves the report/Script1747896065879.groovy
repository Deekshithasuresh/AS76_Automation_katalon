import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Log in and approve the report as before
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// Step 2: Capture the Slide ID dynamically (assuming it's visible before approval)
TestObject slideIdObject = new TestObject().addProperty("xpath", ConditionType.EQUALS, "/html/body/div/div/div[1]/div[1]/div[2]/div[2]/span")
String slideId = WebUI.getText(slideIdObject).trim()
WebUI.comment("Captured Slide ID: " + slideId)

// Approve the report
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report_1'))

// Go to Reviewed tab
WebUI.click(findTestObject('Object Repository/Page_PBS/img')) // menu or logo
WebUI.click(findTestObject('Object Repository/Page_PBS/div_Ready for review'))
WebUI.click(findTestObject('Object Repository/Page_PBS/div_Reviewed'))

// Search for the slide ID in the Reviewed reports
TestObject searchInput = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//input[@placeholder='Search']")
WebUI.waitForElementVisible(searchInput, 10)
WebUI.setText(searchInput, slideId)
WebUI.delay(2) // Wait for filtering

// Verify the Slide ID appears in the list
TestObject slideIdCell = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//div[contains(text(),'" + slideId + "')]")

if (WebUI.verifyElementPresent(slideIdCell, 5, FailureHandling.OPTIONAL)) {
	WebUI.comment("✅ Slide ID '" + slideId + "' is found in the Reviewed tab.")
} else {
	WebUI.comment("❌ Slide ID '" + slideId + "' not found in the Reviewed tab.")
}
