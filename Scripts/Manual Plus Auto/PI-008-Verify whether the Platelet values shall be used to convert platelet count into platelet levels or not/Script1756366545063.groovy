import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import javax.sound.sampled.*

import org.openqa.selenium.*

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import adimin_pbs_Settings.PBS_Settings
import loginPackage.Login
import zoom.ZoomInOut


Login lg = new Login()
PBS_Settings pbs_set = new PBS_Settings()
ZoomInOut zoom = new ZoomInOut()

// Admin login and update RBC/Shape/Color limits
lg.AdminLogin('manju', 'Sigtuple@123')
WebUI.verifyElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'), 10)
WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/div_PBS settings'))

// RBC Limits
WebUI.verifyElementPresent(findTestObject('PBS_Settings_Objects/Page_Admin Console/button_RBC grade limits'), 10)
WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/button_RBC grade limits'))
WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/Edit_button'))
pbs_set.EneterValueInUpperLimitFields('10', '20','enter correct values')
pbs_set.checkFunctionalityOfCTAS(findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_CTA'),
								 findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_Confirm_CTA'),'save_confirm')

// Shape
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Close_CTA'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Shape_button'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Edit_button'))
pbs_set.EneterValueInUpperLimitFields('10', '20','enter correct values')
pbs_set.checkFunctionalityOfCTAS(findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_CTA'),
								 findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_Confirm_CTA'),'save_confirm')

// Color
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Close_CTA'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Color_button'))
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Edit_button'))
pbs_set.EneterValueInUpperLimitFields('10', '20','enter correct values')
pbs_set.checkFunctionalityOfCTAS(findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_CTA'),
								 findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_Confirm_CTA'),'save_confirm')

// Platelet level cutoffs
WebUI.click(findTestObject('Object Repository/PBS_Settings_Objects/Page_Admin Console/Close_CTA'))
WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/button_Platelet level limits'))
WebUI.click(findTestObject('PBS_Settings_Objects/Page_Admin Console/button_Edit settings'))
WebElement saveBtn = WebUiCommonHelper.findWebElement(findTestObject('PBS_Settings_Objects/Page_Admin Console/Save_CTA'), 10)
WebElement sigDecInput = WebUiCommonHelper.findWebElement(findTestObject('PBS_Settings_Objects/Page_Admin Console/Signifiant_decresed_input'), 10)
WebElement decInput = WebUiCommonHelper.findWebElement(findTestObject('PBS_Settings_Objects/Page_Admin Console/Decresed_input'), 10)
WebElement normalInput = WebUiCommonHelper.findWebElement(findTestObject('PBS_Settings_Objects/Page_Admin Console/Platelet_normal_input'), 10)
pbs_set.enterValueIntoPlateLetLevelField(normalInput, '750')
pbs_set.enterValueIntoPlateLetLevelField(decInput, '500')
pbs_set.enterValueIntoPlateLetLevelField(sigDecInput, '250')
saveBtn.click()
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/Page_Admin Console/button_Confirm_platelet'))

// Admin logout
WebUI.refresh()
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/profile_img'))
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/li_Logout (1)'))


File soundFile = new File('Include/resources/Please start scan.wav')  // Place alert.wav in Include/resources
AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)
Clip clip = AudioSystem.getClip()
clip.open(audioIn)
clip.start()

// Wait 30 minutes to allow report/scans generation
WebUI.delay(1800) // 1800 seconds = 30 minutes

// Reviewer login (in a new window)
WebUI.executeJavaScript("window.open('https://pbsreview.as76.local/login','_blank');", null)
WebUI.switchToWindowIndex(1)
WebUI.setText(findTestObject('Report_Listing/Login_page/input_username_loginId'), "manju")
WebUI.setText(findTestObject('Report_Listing/Login_page/input_password_loginPassword'), "Sigtuple@123")
WebUI.click(findTestObject('Report_Listing/Login_page/button_Sign In'))
WebUI.waitForPageLoad(10)
//CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

// Open slide from Excel
String filePath = 'Time_zone_data/Manual_data.xlsx'
String slideId = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'pbs setting slide id')
String xpath = "(//tr[.//td[contains(normalize-space(), '${slideId}')]])[1]"
TestObject matchingRow = new TestObject('dynamicReportRow')
matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
WebUI.waitForElementClickable(matchingRow, 10)
WebUiCommonHelper.findWebElement(matchingRow, 10).click()


CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

// RBC/Shape/Color verifications
WebUI.waitForElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'),20)
WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))
CustomKeywords.'manual.helper.verifyCorrectnessOfGradeAccordingToPercentageValue'()
WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Shape'))
CustomKeywords.'manual.helper.verifyCorrectnessOfGradeAccordingToPercentageValue'()
WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_color'))
CustomKeywords.'manual.helper.verifyCorrectnessOfGradeAccordingToPercentageValue'()

// Platelet verification
WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))
// Fetch values from UI
	
	
String spanXPathEstimation = "//tr[@class='level-container']//div[@class='description selected']//span"
TestObject estimation = new TestObject("targetSpan")
estimation.addProperty("xpath", ConditionType.EQUALS, spanXPathEstimation)
WebUI.waitForElementVisible(estimation, 10)
String actualCountStr = WebUI.getText(estimation).trim()
println "🧾 actual count: $actualCountStr"

int plateletCount = Integer.parseInt(actualCountStr.replaceAll("[^\\d]", ""))


String xpathCalucalate = "(//input[@name='platelet-count-levels'])[2]"
TestObject levelRadio = new TestObject("targetSpan")
levelRadio.addProperty("xpath", ConditionType.EQUALS, xpathCalucalate)
WebUI.waitForElementVisible(levelRadio, 10)

WebUI.click(levelRadio)


String spanXPath = "//div[text()='Platelet count level']/following-sibling::div/span"
TestObject spanObject = new TestObject("targetSpanLevel")
spanObject.addProperty("xpath", ConditionType.EQUALS, spanXPath)
WebUI.waitForElementVisible(spanObject, 10)
String actualLevel = WebUI.getText(spanObject).trim()

// Platelet level logic check
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
