import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


// -----------------------------
// Custom Setup Steps
// -----------------------------
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))