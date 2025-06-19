import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
DriverFactory

WebUI.openBrowser('')

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

def driver = DriverFactory.getWebDriver()

int unclassifiedCount = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Unclassified')

WebUI.comment("Starting reclassification. Initial Unclassified count: $unclassifiedCount")

int remainingUnclassified = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Unclassified')
WebUI.comment("Starting reclassification. Initial Unclassified count: ${remainingUnclassified}")

while (remainingUnclassified > 0) {
    try {
        int beforeCount = remainingUnclassified

        // Scroll to bottom to bring last cells into view (important for UI interaction)
        WebUI.executeJavaScript('window.scrollTo(0, document.body.scrollHeight)', null)
        WebUI.delay(1)

        // Attempt to classify
        CustomKeywords.'generic.Reclacification.classifyFromCellToCell'('Unclassified', 'Neutrophils')

        // Wait for count to reduce (you can reuse your existing method or simple delay)
        WebUI.delay(2)

        // Recheck count
        remainingUnclassified = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Unclassified')

        if (remainingUnclassified == beforeCount) {
            WebUI.comment("⚠️ Count did not decrease after reclassification attempt. Breaking loop.")
            break
        }

    } catch (Exception e) {
        WebUI.comment("❌ Exception during reclassification: ${e.message}")
        break
    }
}

if (remainingUnclassified == 0) {
    WebUI.comment("✅ All Unclassified cells successfully reclassified.")
} else {
    WebUI.comment("⚠️ Reclassification incomplete. ${remainingUnclassified} unclassified cell(s) remain.")
}

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PBS/div_Are you sure you want to approveAll WBC_fa951f'), 
    0)

