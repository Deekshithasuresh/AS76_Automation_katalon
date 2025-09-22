import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Log in and select report
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under review")

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// Approve flow
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))

// Open 'Add supporting images'
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))

// Short delay to allow UI to load
WebUI.delay(2)

// Helper function to create dynamic TestObject with XPath
TestObject createTestObject(String xpath) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

// Cell types to verify
def cellTypes = [
	'Neutrophils',
	'Lymphocytes',
	'Eosinophils',
	'Monocytes',
	'Basophils',
	'Atypical Cells/Blasts'
]

cellTypes.each { cellType ->
    // Locate checked checkbox using updated XPath
    String checkboxXPath = "//div[@class='cell-selection-item']//div[contains(text(),'${cellType}')]/ancestor::div[@class='cell-selection-item']//span[contains(@class, 'MuiCheckbox-root') and contains(@class, 'Mui-checked')]"
    TestObject checkboxTO = createTestObject(checkboxXPath)

    // Safely check if the checkbox is checked
    boolean isChecked = WebUI.waitForElementPresent(checkboxTO, 3, FailureHandling.OPTIONAL)

    if (isChecked) {
        println "✅ ${cellType} checkbox is checked"

        // Validate right panel visibility
        String sectionXPath = "//div[contains(@class,'supporting-images')]//div[contains(text(),'${cellType}')]"
        TestObject sectionTO = createTestObject(sectionXPath)

        if (WebUI.verifyElementVisible(sectionTO, 5, FailureHandling.CONTINUE_ON_FAILURE)) {
            println "✅ Section for ${cellType} is visible"

            // Check at least one image
            String imageXPath = "${sectionXPath}/following-sibling::div//img"
            TestObject imageTO = createTestObject(imageXPath)

            if (WebUI.waitForElementVisible(imageTO, 5, FailureHandling.CONTINUE_ON_FAILURE)) {
                println "✅ Images are present for ${cellType}"
            } else {
                println "❌ No images for ${cellType}"
            }
        } else {
            println "❌ Section not found for ${cellType}"
        }
    } else {
        println "ℹ️ ${cellType} is not checked — running diagnostic"

        // --- Diagnostic fallback: check if checkbox exists but is unchecked ---
        String uncheckedCheckboxXPath = "//div[@class='cell-selection-item']//div[contains(text(),'${cellType}')]/ancestor::div[@class='cell-selection-item']//span[contains(@class, 'MuiCheckbox-root')]"
        TestObject fallbackTO = createTestObject(uncheckedCheckboxXPath)

        boolean exists = WebUI.waitForElementPresent(fallbackTO, 3, FailureHandling.OPTIONAL)

        if (exists) {
            String checkboxClass = WebUI.getAttribute(fallbackTO, 'class')
            println "🔍 ${cellType} checkbox exists, class attribute: ${checkboxClass}"

            if (checkboxClass.contains('Mui-checked')) {
                println "⚠️ Unexpected: ${cellType} checkbox appears checked, but wasn't matched by main XPath"
            } else {
                println "✅ Confirmed: ${cellType} checkbox is NOT checked"
            }
        } else {
            println "❌ Checkbox element not found at all for ${cellType}"
        }
    }
}