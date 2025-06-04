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

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/div_1'), '1')

WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/div_2'), '2')

WebUI.click(findTestObject('Object Repository/Page_PBS/img_Neutrophils_MuiImageListItem-img qa_pat_2b6ddc'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/div_3'), '3')

WebUI.click(findTestObject('Object Repository/Page_PBS/img_Neutrophils_MuiImageListItem-img qa_pat_4f0e64'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/div_4'), '4')

WebUI.click(findTestObject('Object Repository/Page_PBS/img_Neutrophils_MuiImageListItem-img qa_pat_081a47'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/div_5'), '5')

