import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

// Wait for and verify presence of RBC button and click
WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)
WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)
WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)
WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))

// Wait and click Inclusions button
WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Inclusions'), 10)
WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Inclusions'))

// Verify References div present and click
WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 0)
WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/div_References'), 'References')
WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_References'))


boolean dropDownAbsent = WebUI.verifyElementNotPresent(findTestObject('Object Repository/CR_Drop_16/Page_PBS/div_dropdown'), 3, FailureHandling.CONTINUE_ON_FAILURE)
assert dropDownAbsent : "Dropdown is present but should NOT be on Inclusions tab."
WebUI.comment("Dropdown is NOT present as expected.")


WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/button_References_close-btn'))
