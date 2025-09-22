import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

DriverFactory

CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under review")
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

def driver = DriverFactory.getWebDriver()

int unclassifiedCount = CustomKeywords.'generic.Reclassification.getCellCountInCurrentTab'(driver, 'Unclassified')
WebUI.comment("Starting reclassification. Initial Unclassified count: $unclassifiedCount")

int remainingUnclassified = unclassifiedCount

while (remainingUnclassified > 0) {   // ✅ Correct condition
    try {
        int beforeCount = remainingUnclassified

        // Scroll to bottom so last cells are visible
        WebUI.executeJavaScript('window.scrollTo(0, document.body.scrollHeight)', null)
        WebUI.delay(1)

        // Attempt classification
        CustomKeywords.'generic.Reclassification.classifyFromCellToCell'('Unclassified', 'Neutrophils')
        WebUI.delay(2)

        // Recheck count
        remainingUnclassified = CustomKeywords.'generic.Reclassification.getCellCountInCurrentTab'(driver, 'Unclassified')

        if (remainingUnclassified == beforeCount) {
            WebUI.comment("⚠️ Count did not decrease after reclassification attempt.")
            break
        }

    } catch (Exception e) {
        WebUI.comment("❌ Exception during reclassification: ${e.message}")
        break
    }
}

// 🔹 Final attempt: if only 1 unclassified cell remains, force classify it
if (remainingUnclassified == 1) {
    WebUI.comment("🔄 Final attempt: Classifying the last unclassified cell...")
    CustomKeywords.'generic.Reclassification.classifyFromCellToCell'('Unclassified', 'Neutrophils')
    WebUI.delay(2)
    remainingUnclassified = CustomKeywords.'generic.Reclassification.getCellCountInCurrentTab'(driver, 'Unclassified')
}

// 🔹 Validation
if (remainingUnclassified == 0) {
    WebUI.comment("✅ All Unclassified cells successfully reclassified to Neutrophils.")
} else {
    WebUI.comment("⚠️ Reclassification incomplete. ${remainingUnclassified} unclassified cell(s) remain.")
}

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PBS/div_Are you sure you want to approveAll WBC_fa951f'), 0)
