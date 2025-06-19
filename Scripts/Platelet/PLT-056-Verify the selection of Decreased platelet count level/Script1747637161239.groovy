import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import groovy.console.ui.BytecodeCollector as BytecodeCollector
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/input_Platelet count level_platelet-count-levels'))

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/Manual_radio_box_ selection'))

//Steps for the selecting of the manual level flow.
WebDriver driver = DriverFactory.getWebDriver()

Actions actions = new Actions(driver)

WebElement dd = driver.findElement(By.xpath('//div[@class=\'plt-lvl-desc\']/following-sibling::div//input'))

actions.moveToElement(dd).click().build().perform()

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/li_Normal'), 'Normal')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/li_Increased'), 'Increased')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/li_Decreased'), 'Decreased')

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/li_Significantly decreased'), 'Significantly decreased')

//Verifying the selection of the  increased level flow here.
WebUI.click(findTestObject('Platelet/Page_PBS/li_Decreased'))

