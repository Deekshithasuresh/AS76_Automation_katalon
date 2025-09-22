import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


CustomKeywords.'generic.custumFunctions.login'()

WebUI.maximizeWindow()

WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/button_kebab'))

WebUI.verifyElementText(findTestObject('Object Repository/CR_Drop_16/Page_PBS/li_Privacy Policy'), "Privacy Policy")

WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/li_Privacy Policy'))

WebUI.verifyElementText(findTestObject('Object Repository/CR_Drop_16/Page_PBS/div_Privacy Policy_header'), "Privacy Policy")

WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/Privacy_policy_content'))

WebDriver driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

// Press PAGE_DOWN multiple times until you reach bottom
for(int i=0; i<5; i++) {
	actions.sendKeys(Keys.PAGE_DOWN).perform()
	WebUI.delay(1)
}



CustomKeywords.
'excel.TextComparator.verifyContentAgainstFile'(
	findTestObject('Object Repository/CR_Drop_16/Page_PBS/Privacy_policy_content'),'Time_zone_data/AS76   __  Privacy Policy .txt')

WebUI.click(findTestObject('Object Repository/CR_Drop_16/Page_PBS/Back_button'))
