import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Login and assign
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

// --- Neutrophils Count ---
TestObject NeutrophilsCount = new TestObject('Neutrophils')
NeutrophilsCount.addProperty('xpath', ConditionType.EQUALS, "//td[text()='Neutrophils']/following-sibling::td[1]")

String neutrophilsText = WebUI.getText(NeutrophilsCount).trim()
int neutrophilsCountInt = 0

try {
    if (neutrophilsText == "-" ) {
        neutrophilsCountInt = 0
        WebUI.comment("ℹ️ Neutrophils text is '${neutrophilsText}', treating it as 0")
    } else {
        neutrophilsCountInt = Integer.parseInt(neutrophilsText)
        WebUI.comment("✅ Parsed Neutrophils count: ${neutrophilsCountInt}")
    }
} catch (NumberFormatException e) {
    WebUI.comment("❌ Failed to parse Neutrophils count to int: ${e.message}")
}


// Reclassify all Neutrophils to Basophils
CustomKeywords.'generic.Reclassification.classifyFromCellToCellMultiple'("Neutrophils", "Basophils", neutrophilsCountInt)
WebUI.refresh()
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))


// --- Tooltip Hover ---
TestObject nlrTooltipTrigger = new TestObject('obj_nlr_tooltip_icon')
nlrTooltipTrigger.addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(), 'NLR')]/parent::div")

WebUI.mouseOver(nlrTooltipTrigger)
WebUI.delay(1)

TestObject tooltip = new TestObject('obj_nlr_tooltip')
tooltip.addProperty('xpath', ConditionType.EQUALS, "//span[contains(text(), 'NLR')]")

String tooltipText = WebUI.getText(tooltip).trim()
String nlrValue

if (tooltipText.toUpperCase().contains("NA")) {
    nlrValue = "NA"
} else {
    nlrValue = tooltipText.replaceAll("[^0-9.]", "")
}
WebUI.comment("📌 Extracted NLR value: ${nlrValue}")


// --- Lymphocytes Count ---
TestObject LymphocytesCount = new TestObject('Lymphocytes')
LymphocytesCount.addProperty('xpath', ConditionType.EQUALS, "//td[text()='Lymphocytes']/following-sibling::td[1]")

String lymphocytesText = WebUI.getText(LymphocytesCount).trim()
double lymphocytesCountDouble = 0.0
try {
    lymphocytesCountDouble = Double.parseDouble(lymphocytesText)
    WebUI.comment("✅ Parsed Lymphocytes count: ${lymphocytesCountDouble}")
} catch (NumberFormatException e) {
    WebUI.comment("❌ Failed to parse Lymphocytes count to double: ${e.message}")
}

double neutrophilsCountDouble = 0.0
try {
    neutrophilsCountDouble = Double.parseDouble(neutrophilsText)
    WebUI.comment("✅ Parsed Neutrophils count as double: ${neutrophilsCountDouble}")
} catch (NumberFormatException e) {
    WebUI.comment("❌ Failed to parse Neutrophils count to double: ${e.message}")
}


// --- Validation ---
CustomKeywords.'generic.Wbc_helper.validateNLR'(neutrophilsCountDouble, lymphocytesCountDouble, nlrValue)
