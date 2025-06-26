import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory

// Log in and approve the report as before
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')
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
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report_1'))

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report_1'))


//WebUI.click(findTestObject('Object Repository/Page_PBS/summary_Approve report_button'))
//WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))
//WebUI.click(findTestObject('Object Repository/Page_PBS/review_Approve report_button'))
//WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report_1'))

WebUI.click(findTestObject('Object Repository/Page_PBS/approve_add_supporting_page'))
WebUI.click(findTestObject('Object Repository/Page_PBS/kebab_dots_approve'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Download PDF report (1)'))
//WebUI.click(findTestObject('Object Repository/Page_PBS/span_Download PDF report'))

