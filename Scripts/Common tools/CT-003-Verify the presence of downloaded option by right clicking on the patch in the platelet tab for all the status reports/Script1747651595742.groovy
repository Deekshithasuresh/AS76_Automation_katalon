import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.time.Duration as Duration
import java.util.concurrent.TimeoutException as TimeoutException
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_SELECTSign Inusernameusernamepasswordpa_8a33c2'))

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId (15)'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword (15)'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In (15)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 1005 AM (IST) (1)'))

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets (3)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets (3)'), 'Platelets')

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology (3)'), 'Morphology')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology (3)'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.doubleClick(findTestObject('Object Repository/Commontools/Page_PBS/div_Image settings_default-patch  patch-foc_a6a738 (1)'))

WebDriver driver = DriverFactory.getWebDriver()

Actions actions = new Actions(driver)

WebElement patchfirst = driver.findElement(By.xpath('(//div[@class=\'Card patches-container\'])[1]'))

actions.contextClick(patchfirst).perform()

WebUI.click(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

