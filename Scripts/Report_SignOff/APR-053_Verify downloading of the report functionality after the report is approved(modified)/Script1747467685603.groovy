import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

// Log in and approve the report as before
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh')
def driver = DriverFactory.getWebDriver()
// Navigate to WBC tab and open Neutrophil cell details
WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/Page_PBS/td_Neutrophils'))

// Get initial Neutrophils count
int NeutrophilsBefore = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Neutrophils')
WebUI.comment("Initial Neutrophils Count: $NeutrophilsBefore")

if (NeutrophilsBefore > 0) {
	// Scroll to bottom and classify 1 Neutrophils cell
	WebUI.executeJavaScript('window.scrollTo(0, document.body.scrollHeight)', null)
	WebUI.delay(1)
	try {
		CustomKeywords.'generic.Reclacification.classifyFromCellToCell'('Neutrophils', 'Unclassified')
		WebUI.delay(2)  // Let UI update
	} catch (Exception e) {
		WebUI.comment("❌ Classification failed: ${e.message}")
	}
}

// Approve the report
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report_1'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Download PDF report'))

