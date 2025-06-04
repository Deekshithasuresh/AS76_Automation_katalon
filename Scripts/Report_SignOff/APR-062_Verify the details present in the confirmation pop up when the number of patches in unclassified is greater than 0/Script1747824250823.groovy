import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
DriverFactory

WebUI.openBrowser('')

CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

def driver = DriverFactory.getWebDriver()

// Get initial Unclassified count
int unclassifiedBefore = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Unclassified')
WebUI.comment("Initial Unclassified Count: $unclassifiedBefore")

if (unclassifiedBefore > 0) {
    // Scroll to bottom and classify 1 unclassified cell
    WebUI.executeJavaScript('window.scrollTo(0, document.body.scrollHeight)', null)
    WebUI.delay(1)
    try {
        CustomKeywords.'generic.Reclacification.classifyFromCellToCell'('Unclassified', 'Neutrophils')
        WebUI.delay(2)  // Let UI update
    } catch (Exception e) {
        WebUI.comment("❌ Classification failed: ${e.message}")
    }
}

// Get new Unclassified count
int unclassifiedAfter = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Unclassified')
WebUI.comment("Remaining Unclassified Count: $unclassifiedAfter")

// Click Approve to open the popup
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.waitForElementVisible(findTestObject('Object Repository/Page_PBS/div_Are you sure you want to approveAll WBC_fa951f'), 5)

// Get popup text via XPath and extract number
String popupText = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div[5]/b[2]")).getText()
WebUI.comment("Popup text: ${popupText}")

// Extract number using regex
def matcher = popupText =~ /There are (\d+) patches/
int popupUnclassifiedCount = matcher ? matcher[0][1].toInteger() : -1

WebUI.comment("Popup Unclassified Count: $popupUnclassifiedCount")

// Compare values
if (unclassifiedAfter == popupUnclassifiedCount) {
    WebUI.comment("✅ Count matched: ${unclassifiedAfter}")
} else {
    WebUI.comment("❌ Mismatch! Table count: ${unclassifiedAfter}, Popup count: ${popupUnclassifiedCount}")
}