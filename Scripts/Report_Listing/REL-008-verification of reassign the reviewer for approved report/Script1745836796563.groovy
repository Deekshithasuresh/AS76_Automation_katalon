import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Open browser and navigate to login
CustomKeywords.'generic.custumFunctions.login'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Ready for review'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed_1'), 'Reviewed')

// Create dynamic TestObject for the input field
TestObject assignedToInput = new TestObject('AssignedToInput')
assignedToInput.addProperty("xpath", ConditionType.EQUALS, "//span[text()='Approved']//parent::div/parent::td/following-sibling::td[1]//input")

// Get the value of 'disabled' attribute
String isDisabled = WebUI.getAttribute(assignedToInput, "disabled")

// Fail the test if not disabled
assert isDisabled != null : "❌ Assigned To input is editable. Expected it to be disabled."

println("✅ Assigned To input is not editable (disabled).")

