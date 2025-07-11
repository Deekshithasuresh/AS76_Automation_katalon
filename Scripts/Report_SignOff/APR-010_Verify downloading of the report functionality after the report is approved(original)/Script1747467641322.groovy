import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebDriver

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

DriverFactory

// Log in and approve the report as before
CustomKeywords.'generic.custumFunctions.login'()

WebDriver driver = DriverFactory.getWebDriver()

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Ready for review'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Reviewed'))

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Approved')

//WebUI.click(findTestObject('Object Repository/Page_PBS/approve_add_supporting_page'))
WebUI.click(findTestObject('Object Repository/Page_PBS/kebab_dots_approve'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Switch to original report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/kebab_dots_approve'))

WebUI.delay(3)

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Download PDF report (1)'))