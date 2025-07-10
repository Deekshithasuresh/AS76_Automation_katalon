import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import java.time.Duration as Duration
import java.util.concurrent.TimeoutException as TimeoutException
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.support.ui.ExpectedConditions as ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait as WebDriverWait
import com.kms.katalon.core.annotation.Keyword as Keyword
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.setText(findTestObject('Object Repository/Commontools/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/Commontools/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_Sign In'))

//WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/div_16-May-2025, 1145 AM (IST)'))
CustomKeywords.'chida.wbcFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('Chidu')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_WBC'))

//WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))
//WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Additional info'), 10)
//WebUI.click(findTestObject('Commontools/Page_PBS/li_Additional info'))
//WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Classification Rank'), 10)
//WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_1. Neutrophils'), 10)
//WebUI.verifyElementText(findTestObject('Commontools/Page_PBS/li_Neutrophils'), 'Neutrophils')
//WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_2. Lymphocytes'), 10)
//WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/td_Band Forms'))
//WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/td_Band Forms'))
CustomKeywords.'chida.wbcFunctions.classifyFromCellToCell'('Neutrophils', 'Promonocytes')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/td_Promonocytes'))

WebUI.rightClick(findTestObject('WBC/Page_PBS/Page_PBS/1stPatch'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Additional info'), 10)

WebUI.click(findTestObject('Commontools/Page_PBS/li_Additional info'))

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_Classification Rank'), 10)

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_1. Neutrophils'), 10)

WebUI.verifyElementText(findTestObject('Commontools/Page_PBS/li_Neutrophils'), '1. Neutrophils')

WebUI.verifyElementPresent(findTestObject('Commontools/Page_PBS/li_2. Lymphocytes'), 10)

