import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static org.testng.Assert.assertTrue

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI




CustomKeywords.'generic.custumFunctions.login'()

Boolean result= CustomKeywords.'generic.FilterFunctions.statusFilter'("To be reviewed")
assertTrue(result)

// Step 2: Capture a unique value (like Slide ID) from the first row
String slideIdBefore = WebUI.getText(findTestObject('Object Repository/Report_Listing/Page_PBS/first_row_slide_id'))
WebUI.comment("Captured Slide ID: " + slideIdBefore)

// Step 3: Click the first report to view details
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/first_row'))

// Step 4: Wait and navigate back
WebUI.delay(5)
WebUI.back()
WebUI.delay(3)


String slideIdAfter = WebUI.getText(findTestObject('Object Repository/Report_Listing/Page_PBS/first_row_slide_id'))

if (slideIdBefore != slideIdAfter) {
	WebUI.comment("❌ Filter state was not retained.")
	result = false
} else {
	WebUI.comment("✅ Filter state was retained after navigating back.")
}
