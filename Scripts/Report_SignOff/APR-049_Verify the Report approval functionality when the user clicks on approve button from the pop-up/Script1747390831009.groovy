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

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report_1'))

//WebUI.delay(5)

//WebUI.click(findTestObject('Object Repository/Page_PBS/summary_Approve report_button'))

//WebUI.waitForElementClickable(findTestObject('Object Repository/Page_PBS/summary_Approve report_button'), 10)
WebUI.click(findTestObject('Object Repository/Page_PBS/review_Approve report_button'))


WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/div_Report Approved'), 'Report Approved')

