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

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'("manju")

//WebUI.click(findTestObject('Object Repository/Page_PBS/add_supporting_images'))

//WebUI.setText(findTestObject('Object Repository/Page_PBS/input_SiG_assigned_to'), 'santosh')

WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/span_Approve report'), 'Approve report')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))

//WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/span_Are you sure you want to approve'), 'Are you sure you want to approve?')

//WebUI.click(findTestObject('Object Repository/Page_PBS/div_All WBC patches, including unclassified_35d31c'))

//WebUI.verifyElementVisible(findTestObject('Object Repository/Page_PBS/div_Are you sure you want to approveAll WBC_fa951f'))

//WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/button_Cancel'), 'Cancel')

//WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'), 'Confirm')

WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_PBS/span_No changes can be made once report is _61297c'), 
    'No changes can be made once report is approved!')

