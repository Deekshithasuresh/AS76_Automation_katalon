import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import adimin_pbs_Settings.PBS_Settings
import loginPackage.Login

Login lg = new Login()
PBS_Settings pbs_set = new PBS_Settings()

// Step 1: Login to Admin Portal and Set Platelet Ranges
lg.AdminLogin('prem', 'prem@2807')
WebUI.verifyElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 10)
WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Platelet level limits'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/button_Edit settings'))

WebElement save_btn = WebUiCommonHelper.findWebElement(findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_CTA'), 10)

WebElement sigDecreasedInput = WebUiCommonHelper.findWebElement(findTestObject('PBS_Settings_Objects/Page_Admin Console/Signifiant_decresed_input'), 10)
WebElement decreasedInput = WebUiCommonHelper.findWebElement(findTestObject('PBS_Settings_Objects/Page_Admin Console/Decresed_input'), 10)
WebElement normalInput = WebUiCommonHelper.findWebElement(findTestObject('PBS_Settings_Objects/Page_Admin Console/Platelet_normal_input'), 10)

ArrayList<String> values = pbs_set.getValuesPresentInPlateletLevelFields()
println(values)

pbs_set.enterValueIntoPlateLetLevelField(normalInput, '750')
pbs_set.enterValueIntoPlateLetLevelField(decreasedInput, '500')
pbs_set.enterValueIntoPlateLetLevelField(sigDecreasedInput, '250')
save_btn.click()
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_Admin Console/button_Confirm_platelet'))
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_Admin Console/X_img_platelet'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/profile_img'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/li_Logout (1)'))

// Step 2: Login to PBS Portal as Reviewer
WebUI.executeJavaScript("window.open('https://pbsreview.as76.local/login','_blank');", null)
WebUI.switchToWindowIndex(1)
WebUI.setText(findTestObject('Object Repository/Report_Listing/Login_page/input_username_loginId'), "manju")
WebUI.setText(findTestObject('Object Repository/Report_Listing/Login_page/input_password_loginPassword'), "Sigtuple@123")
WebUI.click(findTestObject('Object Repository/Report_Listing/Login_page/button_Sign In'))
WebUI.waitForPageLoad(10)

WebUI.delay(1200)

// Step 3: Open Report Slide from Excel ID
String filePath = 'Time_zone_data/Manual_data.xlsx'
String Slideid = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'pbs setting slide id ')
println "✅ Slide id from Excel: ${Slideid}"

String xpath = "(//tr[.//td[contains(normalize-space(), '${Slideid}')]])[1]"
TestObject matchingRow = new TestObject('dynamicReportRow')
matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
WebUI.waitForElementClickable(matchingRow, 10)
WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
rowElement.click()

// Assign reviewer
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.delay(1200)

// Step 4: Navigate to Platelet Tab
WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

// Step 5: Verify Calculated Level Displayed
String spanXPathEstimation = "//tr[@class='level-container']//div[@class='description selected']//span"
TestObject estimation = new TestObject("targetSpan")
estimation.addProperty("xpath", ConditionType.EQUALS, spanXPathEstimation)
WebUI.waitForElementVisible(estimation, 10)
String actualCountStr = WebUI.getText(estimation).trim()
int plateletCount = Integer.parseInt(actualCountStr.replaceAll("[^\\d]", ""))



// Step 5: Verify Calculated Level Displayed
String spanXPath = "//div[text()='Platelet count level']/following-sibling::div/span"
TestObject spanObject = new TestObject("targetSpan")
spanObject.addProperty("xpath", ConditionType.EQUALS, spanXPath)
WebUI.waitForElementVisible(spanObject, 10)
String actualLevel = WebUI.getText(spanObject).trim()

// Step 6: Validate Level Logic (based on previously set values)
String expectedLevel = ""
if (plateletCount < 250) {
    expectedLevel = "Significantly decreased"
} else if (plateletCount >= 250 && plateletCount <= 500) {
    expectedLevel = "Decreased"
} else if (plateletCount > 500 && plateletCount <= 750) {
    expectedLevel = "Normal"
} else {
    expectedLevel = "Increased"
}

println "🧾 Expected Level: $expectedLevel"
println "🧾 Actual UI Level: $actualLevel"
assert actualLevel.equalsIgnoreCase(expectedLevel) : "❌ Mismatch! Expected $expectedLevel but found $actualLevel"

println "✅ Platelet level verification passed."
