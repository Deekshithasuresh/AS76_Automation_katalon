import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 0329 PM (IST)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology'), 'Morphology')

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyTextNotPresent('Additional info',true)

