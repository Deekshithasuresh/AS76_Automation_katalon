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

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'("Under Review")

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets (3)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_Platelets (3)'), 'Platelets')

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology (3)'), 'Morphology')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Morphology (3)'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.doubleClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebDriver driver = DriverFactory.getWebDriver()

Actions actions = new Actions(driver)

WebElement patchfirst = driver.findElement(By.xpath('(//div[@class=\'Card patches-container\'])[1]'))

actions.contextClick(patchfirst).perform()

WebUI.click(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

