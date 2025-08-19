import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Sign In'))

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Summary'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/WBC_m/Page_PBS/other'), 'Others')