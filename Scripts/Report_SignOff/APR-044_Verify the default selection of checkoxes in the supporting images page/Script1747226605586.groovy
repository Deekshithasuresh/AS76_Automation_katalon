import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))

// Helper function to create TestObject from XPath
TestObject createTestObject(String xpath) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

// Function to extract the percentage from label text
def extractPercentage(String text) {
	def matcher = text =~ /\(([\d.]+)\)/
	return matcher ? Float.parseFloat(matcher[0][1]) : 0.0
}

// Map of cell names and thresholds
def cellTypes = [
	'Neutrophils':              0.0,
	'Lymphocytes':              0.0,
	'Eosinophils':              0.0,
	'Monocytes':                0.0,
	'Basophils':                0.0,
	'Atypical Cells/Blasts':    0.0,
	'Immature Granulocytes':    5.0,
	'NRBC':                     5.0
]

// Iterate and validate each checkbox
cellTypes.each { cellName, threshold ->

	// XPath to label containing cell name
	String labelXpath = "//div[@class='celle-selection-list']//div[contains(text(), '${cellName}')]"
	
	TestObject labelObj = createTestObject(labelXpath)

	if (WebUI.verifyElementPresent(labelObj, 5, FailureHandling.CONTINUE_ON_FAILURE)) {
		String labelText = WebUI.getText(labelObj)
		float percent = extractPercentage(labelText)

		// XPath to checkbox inside that label
		String checkboxXpath = "${labelXpath}//parent::div//input"
		TestObject checkboxObj = createTestObject(checkboxXpath)

		if (percent > threshold) {
			WebUI.verifyElementChecked(checkboxObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
			println "${cellName} (${percent}%) > ${threshold}% – should be CHECKED – Verified"
		} else {
			WebUI.verifyElementNotChecked(checkboxObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
			println "${cellName} (${percent}%) <= ${threshold}% – should NOT be checked – Verified"
		}
	} else {
		println "Label for ${cellName} NOT FOUND – Skipped"
	}
}

