import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Step 1: Login and assign reviewer
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
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
//WebUI.click(findTestObject('Object Repository/Page_PBS/summary_Approve report_button'))
//WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))

// Step 3: Approve report
//WebUI.click(findTestObject('Object Repository/Page_PBS/summary_Approve report_button'))
TestObject approveButton = findTestObject('Object Repository/WBC_m/Page_PBS/span_Approve report')
WebElement apElement = WebUiCommonHelper.findWebElement(approveButton, 10)
JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
js.executeScript("arguments[0].click();", apElement)
// Wait for the popup and click the Confirm button
TestObject confirmButton = new TestObject().addProperty(
	"xpath",
	ConditionType.EQUALS,
	"//div[@class='modal-actions']//button[contains(., 'Confirm')]"
)

WebUI.waitForElementVisible(confirmButton, 10)
WebUI.click(confirmButton)

// Step 4: Verify text in PS Impression section
WebUI.verifyTextPresent("First kind of impression for RBC.", false)
WebUI.verifyTextPresent("First kind of impression for WBC.", false)
WebUI.verifyTextPresent("First kind of impression for Platelet.", false)
WebUI.verifyTextPresent("First kind of impression for Hemo Parasite.", false)
WebUI.verifyTextPresent("First kind of impression for Impression.", false)
