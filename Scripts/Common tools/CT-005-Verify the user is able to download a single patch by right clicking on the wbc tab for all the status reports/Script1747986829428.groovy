import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId (20)'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword (20)'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In (20)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_02-May-2025, 0938 AM (IST) (4)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (9)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_WBC (9)'), 'WBC')
WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

WebUI.doubleClick(findTestObject('Object Repository/Commontools/Page_PBS/div_Image settings_default-patch  patch-foc_a6a738 (2)'))
WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

WebElement patchfirst=driver.findElement(By.xpath("(//div[@class='Card patches-container'])[1]"))
actions.contextClick(patchfirst).perform()



WebUI.click(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))
