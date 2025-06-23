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
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('Chidu')

WebUI.click(findTestObject('Object Repository/Commontools/Page_PBS/button_WBC'))

CustomKeywords.'chida.wbcFunctions.classifyFromCellToCell'('Neutrophils', 'Promonocytes')

// 6. Click back arrow or logo to trigger save
WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/img'))

// 7. Verify "Switch to original report" option is present
//WebUI.verifyElementPresent(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Switch to original report'), 5)
//WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/li_Switch to original report'), 'Switch to original report')


WebUI.click(findTestObject('Object Repository/Commontools/span_Switch to original report (2)'))

WebUI.click(findTestObject('Object Repository/Commontools/img (5)'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Commontools/span_Switch to modified report (1)'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Commontools/span_Switch to modified report (1)'), 'Switch to modified report')

