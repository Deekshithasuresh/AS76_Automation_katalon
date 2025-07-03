import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
// Log in and select report
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// Add a short wait to ensure the page loads
WebUI.delay(3)

// List of WBC cell types (update this list if new cell types are added)
List<String> wbcCellTypes = [
    "Neutrophils",
    "Lymphocytes",
    "Eosinophils",
    "Monocytes",
    "Basophils",
    "Immature Granulocytes",
    "Atypical Cells/Blasts",
    "Immature Eosinophils",
    "Immature Basophils",
    "Promonocytes",
    "Prolymphocytes",
    "Hairy Cells",
    "Sezary Cells",
    "Plasma Cells",
    "Others"
]
// Approve flow
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))

// Open 'Add supporting images'
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))
WebUI.delay(2)

// Loop through each WBC cell type
for (String cellType : wbcCellTypes) {
    // Find the span element that wraps the input and gets class changes on selection
    TestObject checkboxWrapper = new TestObject("${cellType}_checkboxWrapper")
    checkboxWrapper.addProperty("xpath", ConditionType.EQUALS,
        "//div[@class='checkbox-label' and contains(text(), '${cellType}')]/preceding::span[contains(@class,'MuiSwitch-root')][1]")

    // Verify if the checkbox wrapper is present
    if (WebUI.verifyElementPresent(checkboxWrapper, 2, FailureHandling.OPTIONAL)) {
        String classAttribute = WebUI.getAttribute(checkboxWrapper, "class")

        // MUI uses 'Mui-checked' (or similar) in the wrapper class when checked
        boolean isChecked = classAttribute.contains("Mui-checked")

        if (isChecked) {
            TestObject imageSection = new TestObject("${cellType}_imageSection")
            imageSection.addProperty("xpath", ConditionType.EQUALS,
                "//h6[contains(text(),'${cellType}')]")

            boolean isVisible = WebUI.verifyElementVisible(imageSection, FailureHandling.OPTIONAL)

            if (isVisible) {
                WebUI.comment("✅ '${cellType}' is selected and image section is visible.")
            } else {
                WebUI.comment("❌ '${cellType}' is selected but image section is NOT visible.")
            }
        } else {
            WebUI.comment("⚠️ Checkbox for '${cellType}' is NOT selected. Skipping image visibility check.")
        }
    } else {
        WebUI.comment("⚠️ Checkbox for '${cellType}' NOT found on page. Skipping.")
    }
}