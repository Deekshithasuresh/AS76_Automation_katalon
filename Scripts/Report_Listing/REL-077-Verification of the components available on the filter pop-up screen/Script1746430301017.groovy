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

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'), 0)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Scan date'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Assigned to'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Status'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Device ID'), 0)

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Product Version'), 'Product Version')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Scan date'), 'Scan date')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Show applied filters'), 'Show applied filters')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Reset'), 'Reset')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Apply'), 'Apply')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Filters'), 'Filters')

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Filters_MuiButtonBase-root MuiIconBu_2f02cd'),  0)

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Assigned to'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Assigned to'), 'Assigned to')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Status'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Status'), 'Status')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_To be reviewed'), 'To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Under review'), 'Under review')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Device ID'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Device ID'), 'Device ID')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Product Version'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/div_Product Version'), 'Product Version')

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_PBS/img_1'), 5)


