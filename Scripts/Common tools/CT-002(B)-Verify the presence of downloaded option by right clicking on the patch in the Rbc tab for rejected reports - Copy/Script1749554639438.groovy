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

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In'))

//WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 1145 AM (IST)'))
CustomKeywords.'generic.custumFunctions.login'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Ready for review'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reviewed_1'), 'Reviewed')

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Rejected')

WebUI.waitForElementNotPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/assign__dd'), 0)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'))

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/Page_PBS/span_RBC (4)'), 'RBC')
WebUI.click(findTestObject('Commontools/Macrocytes_cell_name'), FailureHandling.STOP_ON_FAILURE)

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

WebUI.doubleClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebDriver driver = DriverFactory.getWebDriver()

Actions actions = new Actions(driver)

WebElement patchfirst = driver.findElement(By.xpath('(//div[@class=\'Card patches-container\'])[1]'))

actions.contextClick(patchfirst).perform()

WebUI.click(findTestObject('Commontools/2ndpatch'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Download'), 10)

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/li_Download'))

