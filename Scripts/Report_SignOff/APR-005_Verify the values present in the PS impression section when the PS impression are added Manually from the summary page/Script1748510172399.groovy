import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Step 1: Login and assign reviewer
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

// Step 2: Enter text in PS Impression fields

// RBC Morphology
WebUI.waitForElementClickable(findTestObject('Object Repository/Page_PBS/span_RBC Morphology'), 10)
WebUI.click(findTestObject('Object Repository/Page_PBS/span_RBC Morphology'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[0].innerHTML = '';", null)
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[0].innerHTML = 'RBC Morphology';", null)

// WBC Morphology
WebUI.waitForElementClickable(findTestObject('Object Repository/Page_PBS/span_WBC_Morphology'), 10)
WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC_Morphology'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[1].innerHTML = '';", null)
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[1].innerHTML = 'WBC Morphology';", null)

// Platelet Morphology
WebUI.click(findTestObject('Object Repository/Page_PBS/div_Platelet MorphologyTemplatesOKCancel'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[2].innerHTML = '';", null)
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[2].innerHTML = 'Platelet Morphology';", null)

// Hemoparasite
WebUI.click(findTestObject('Object Repository/Page_PBS/div_HemoparasiteTemplatesOKCancel'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[3].innerHTML = '';", null)
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[3].innerHTML = 'Hemoparasite';", null)

// Impression
WebUI.click(findTestObject('Object Repository/Page_PBS/div_Impression'))
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[4].innerHTML = '';", null)
WebUI.executeJavaScript("document.querySelectorAll(\"div.ql-editor[contenteditable='true']\")[4].innerHTML = 'Impression';", null)


// Step 3: Approve report
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))

// Step 4: Verify text in PS Impression section
WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/p_RBC_Morphology'), 'RBC Morphology')
WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/p_WBC_Morphology'), 'WBC Morphology')
WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/p_Platelet_Morphology'), 'Platelet Morphology')
WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/p_Hemoparasite'), 'Hemoparasite')
WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/p_Impression'), 'Impression')

