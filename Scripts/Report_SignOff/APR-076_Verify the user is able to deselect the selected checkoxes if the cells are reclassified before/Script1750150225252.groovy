import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.model.FailureHandling

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

CustomKeywords.'generic.Reclassification.dragAndDropFromCellToCell'("Neutrophils", "Immature Basophils")


TestObject approveButton = findTestObject('Object Repository/WBC_m/Page_PBS/span_Approve report')
WebElement apElement = WebUiCommonHelper.findWebElement(approveButton, 10)

JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
js.executeScript("arguments[0].click();", apElement)

WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_Confirm_after_aR'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Add supporting images'))


WebDriver driver = DriverFactory.getWebDriver()

// Locate all checkboxes
//List<WebElement> checkboxes = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'cell-selection-item')]//input[@type='checkbox' and not(@disabled)]")))


// Create TestObject from XPath
TestObject createTestObject(String xpath) {
	TestObject to = new TestObject()
	to.addProperty("xpath", ConditionType.EQUALS, xpath)
	return to
}

// Extract percentage value from a label string
float extractPercentage(String text) {
	def matcher = text =~ /\(([\d.]+)\)/
	return matcher ? Float.parseFloat(matcher[0][1]) : 0.0
}

// Cell thresholds
Map<String, Float> cellTypes = [
	'Neutrophils'             : 0.0,
	'Lymphocytes'             : 0.0,
	'Eosinophils'             : 0.0,
	'Monocytes'               : 0.0,
	'Basophils'               : 0.0,
	'Atypical Cells/Blasts'   : 0.0,
	'Immature Granulocytes'   : 5.0,
	'NRBC'                    : 5.0
]

cellTypes.each { cellName, threshold ->
	// XPath for the cell label
	String labelXpath = "//div[@class='celle-selection-list']//div[contains(text(), '${cellName}')]"
	TestObject labelObj = createTestObject(labelXpath)

	if (WebUI.verifyElementPresent(labelObj, 5, FailureHandling.CONTINUE_ON_FAILURE)) {
		String labelText = WebUI.getText(labelObj)
		float percent = extractPercentage(labelText)

		// Traverse up to common container and down to input
		String checkboxXpath = "${labelXpath}//parent::div//input"
		TestObject checkboxObj = createTestObject(checkboxXpath)

		if (percent > threshold) {
    WebUI.verifyElementChecked(checkboxObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
    println "${cellName} (${percent}%) > ${threshold}% → ✅ Checked – Verified"

    // Deselect the checkbox
    WebUI.click(checkboxObj)
    WebUI.delay(1)  // Allow UI update

    // XPath to the corresponding cell in the middle pane
    String cellInMiddleXpath = "(//div[contains(@class,'viewer-set-container')]//div[contains(text(),'${cellName}')])[1]"
    TestObject cellInMiddleObj = createTestObject(cellInMiddleXpath)

    boolean isPresent = WebUI.verifyElementNotPresent(cellInMiddleObj, 3, FailureHandling.CONTINUE_ON_FAILURE)
    if (isPresent) {
        println "✅ After deselecting ${cellName}, it is correctly removed from the middle pane"
    } else {
        println "❌ After deselecting ${cellName}, it is still visible in the middle pane"
    }

    // Re-select it again to restore original state (optional)
    //WebUI.click(checkboxObj)
    WebUI.delay(1)
} else {
			WebUI.verifyElementNotChecked(checkboxObj, 5, FailureHandling.CONTINUE_ON_FAILURE)
			println "${cellName} (${percent}%) ≤ ${threshold}% → ✅ Unchecked – Verified"
		}
	} else {
		println "⚠️ Label for '${cellName}' not found – Skipping"
	}
}



