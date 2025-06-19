import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

// Step 1: Login and assign reviewer
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

// Step 2: Enter text in PS Impression fields by selecting the first template

// RBC Morphology
WebUI.waitForElementClickable(findTestObject('Object Repository/Page_PBS/span_RBC Morphology'), 10)
WebUI.click(findTestObject('Object Repository/Page_PBS/span_RBC Morphology'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[0].innerHTML = '';", null)
WebUI.click(findTestObject('Object Repository/Page_PBS/button_RBC_Morphology_Templates')) // Button to open dropdown
WebUI.click(findTestObject('Object Repository/Page_PBS/li_TemplateOption1')) // First option (e.g., "Impression 1")

// WBC Morphology
WebUI.waitForElementClickable(findTestObject('Object Repository/Page_PBS/span_WBC_Morphology'), 10)
WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC_Morphology'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[1].innerHTML = '';", null)
WebUI.click(findTestObject('Object Repository/Page_PBS/button_WBC_Morphology_Templates'))
WebUI.click(findTestObject('Object Repository/Page_PBS/li_TemplateOption2'))

// Platelet Morphology
WebUI.click(findTestObject('Object Repository/Page_PBS/div_Platelet MorphologyTemplatesOKCancel'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[2].innerHTML = '';", null)
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Platelet_Morphology_Templates'))
WebUI.click(findTestObject('Object Repository/Page_PBS/li_TemplateOption3'))

// Hemoparasite
WebUI.click(findTestObject('Object Repository/Page_PBS/div_HemoparasiteTemplatesOKCancel'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[3].innerHTML = '';", null)
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Hemoparasite_Templates'))
WebUI.click(findTestObject('Object Repository/Page_PBS/li_TemplateOption4'))

// Impression
WebUI.click(findTestObject('Object Repository/Page_PBS/div_Impression'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[4].innerHTML = '';", null)
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Impression_Templates'))
WebUI.click(findTestObject('Object Repository/Page_PBS/li_TemplateOption5'))

WebUI.delay(5)

// Step 3: Approve report
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))

// Step 4: Verify text in PS Impression section
WebUI.verifyTextPresent("First kind of impression for RBC.", false)
WebUI.verifyTextPresent("First kind of impression for WBC.", false)
WebUI.verifyTextPresent("First kind of impression for Platelet.", false)
WebUI.verifyTextPresent("First kind of impression for Hemo Parasite.", false)
WebUI.verifyTextPresent("First kind of impression for Impression.", false)
