import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.awt.Robot
import java.awt.event.KeyEvent

WebUI.openBrowser('')

// Login and select a report
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

// Click the "Reject report" button to open popup


WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Reject report'))

WebUI.verifyElementClickable(findTestObject('Object Repository/Page_PBS/cancel_button_reject'))

WebUI.click(findTestObject('Object Repository/Page_PBS/cancel_button_reject'))