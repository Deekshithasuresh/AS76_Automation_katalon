import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// assign the report to someone else
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// Define the TestObject for the "Reject report" button
TestObject rejectButton = new TestObject("RejectButton")
rejectButton.addProperty("xpath", ConditionType.EQUALS, "//button[contains(., 'Reject report')]")

// Wait briefly for page load
WebUI.delay(3)

// Check that the button is NOT present
boolean isRejectButtonPresent = WebUI.verifyElementNotPresent(rejectButton, 5, FailureHandling.OPTIONAL)

if (isRejectButtonPresent) {
	WebUI.comment("✅ 'Reject report' button is NOT present as expected for non-assigned report.")
} else {
	WebUI.comment("❌ 'Reject report' button IS visible for non-assigned report, which is unexpected.")
}