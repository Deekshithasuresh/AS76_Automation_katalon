import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
DriverFactory

WebUI.openBrowser('')

CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

def driver = DriverFactory.getWebDriver()

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

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Reject report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Reject report_1'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/div_Report Rejected'), 'Report Rejected')
