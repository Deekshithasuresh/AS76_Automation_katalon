import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

//CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

TestObject nlrTooltipTrigger = new TestObject('obj_nlr_tooltip_icon')
nlrTooltipTrigger.addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(), 'NLR')]/parent::div")

WebUI.mouseOver(nlrTooltipTrigger)
WebUI.delay(1)

TestObject tooltip = new TestObject('obj_nlr_tooltip')
tooltip.addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(), 'NLR')]")

String tooltipText = WebUI.getText(tooltip)  // "NLR = 0.9"
String nlrValue = tooltipText.replaceAll("[^0-9.]", "") // Extracts "0.9"

boolean isRounded = nlrValue ==~ /\d+(\.\d{1})?/  // match up to 2 decimal places

assert isRounded : "❌ Tooltip value is not rounded properly: ${tooltipText}"
WebUI.comment("✅ Tooltip NLR value is properly rounded: ${nlrValue}")

