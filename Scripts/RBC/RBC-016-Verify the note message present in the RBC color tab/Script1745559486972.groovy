import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.util.KeywordUtil.*

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login

Login lg = new Login()

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Color'))

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Total number of RBCs counted for color _9562ea'),
	0)

String Actual_note_msg = WebUI.getText(findTestObject('Object Repository/RBC_Objects/Page_PBS/div_Total number of RBCs counted for color _9562ea'))


WebUI.verifyMatch(
	Actual_note_msg.trim(),
	"Total number of RBCs counted for color based classification is \\d+",
	true   // enable regex
)
