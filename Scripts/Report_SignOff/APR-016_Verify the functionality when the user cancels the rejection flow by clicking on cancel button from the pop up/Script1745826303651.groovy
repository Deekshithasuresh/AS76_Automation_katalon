import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.awt.Robot
import java.awt.event.KeyEvent

import org.openqa.selenium.By

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
DriverFactory

WebUI.openBrowser('')

CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')


WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Reject report'))

WebUI.verifyElementClickable(findTestObject('Object Repository/Report-Signoff/button_Cancel_modify_neutrophil'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Cancel_modify_neutrophil'))

