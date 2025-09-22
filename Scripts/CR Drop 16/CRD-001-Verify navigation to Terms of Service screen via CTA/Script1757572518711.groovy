import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

WebUI.maximizeWindow()

WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/button_kebab'))

WebUI.verifyElementText(findTestObject('Object Repository/CR_Drop_16/Page_PBS/li_Terms of service'), 'Terms of service')

WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/li_Terms of service'))

WebUI.verifyElementText(findTestObject('Object Repository/CR_Drop_16/Page_PBS/div_Terms of service _header'), 'Terms of service')

WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/terms_policy_content'))

WebDriver driver = DriverFactory.getWebDriver()

Actions actions = new Actions(driver)

// Press PAGE_DOWN multiple times until you reach bottom
for (int i = 0; i < 12; i++) {
    actions.sendKeys(Keys.PAGE_DOWN).perform()

    WebUI.delay(1)
}

CustomKeywords.'excel.TextComparator.verifyContentAgainstFile'(findTestObject('Object Repository/CR_Drop_16/Page_PBS/terms_policy_content'), 
    'Time_zone_data/AS 76 __ Terms of Service.txt')

WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/terms_policy_content'))

