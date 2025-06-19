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

// Wait for the popup to appear
WebUI.verifyElementClickable(findTestObject('Object Repository/Report-Signoff/button_Cancel'))

// Wait until cancel button is visible (popup open)
WebUI.verifyElementVisible(findTestObject('Object Repository/Report-Signoff/button_Cancel'))

// Use Robot to send the ESCAPE key (closes popup)
Robot robot = new Robot()
robot.keyPress(KeyEvent.VK_ESCAPE)
robot.keyRelease(KeyEvent.VK_ESCAPE)

// Optional: wait to observe the effect
WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Cancel'))