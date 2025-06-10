import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/input_Platelet count level_platelet-count-levels'))

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/Manual_radio_box_ selection'))
//Steps for the selecting of the manual level flow.
WebDriver driver = DriverFactory.getWebDriver()

//Actions actions = new Actions(driver)

WebElement dd = driver.findElement(By.xpath('//div[@class="plt-lvl-desc"]/following-sibling::div//input'))

//actions.moveToElement(dd).click().build().perform()

JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
js.executeScript("arguments[0].click();", dd)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/li_Normal'), 'Normal')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/li_Increased'), 'Increased')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/li_Decreased'), 'Decreased')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/li_Significantly decreased'), 'Significantly decreased')
//Verifying the selection of the  increased level flow here.
WebUI.click(findTestObject('Platelet/Page_PBS/li_Increased'))

