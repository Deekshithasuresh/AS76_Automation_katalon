import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))

// Helper to create TestObject from XPath
TestObject createTestObject(String xpath) {
	TestObject to = new TestObject(xpath)
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

// Extract % value from label text
def extractPercentage(String text) {
	def matcher = text =~ /\(([\d.]+)\)/
	return matcher ? Float.parseFloat(matcher[0][1]) : 0.0
}

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

cellTypes.each { cellName, threshold ->

	String labelXpath = "//div[@class='celle-selection-list']//div[contains(text(), '${cellName}')]"
	TestObject labelObj = createTestObject(labelXpath)

	if (WebUI.verifyElementPresent(labelObj, 5, FailureHandling.CONTINUE_ON_FAILURE)) {
		String labelText = WebUI.getText(labelObj)
		float percent = extractPercentage(labelText)

		String checkboxXpath = "${labelXpath}/parent::div//input[@type='checkbox']"
		TestObject checkboxObj = createTestObject(checkboxXpath)

		println "Checking cell: ${cellName} → ${percent}% (threshold: ${threshold}%)"
		println "Checkbox XPath: ${checkboxXpath}"

		if (percent > threshold) {
			// It should be checked → deselect if already checked
			if (WebUI.verifyElementChecked(checkboxObj, 5, FailureHandling.CONTINUE_ON_FAILURE)) {
				WebUI.click(checkboxObj)
				println "${cellName} was checked and got DESELECTED"
			} else {
				println "${cellName} was already not selected"
			}
		} else {
			// It should not be checked
			WebUI.verifyElementNotChecked(checkboxObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
			println "${cellName} is correctly NOT checked"
		}
	} else {
		println "Label for ${cellName} NOT FOUND – Skipping"
	}
}


