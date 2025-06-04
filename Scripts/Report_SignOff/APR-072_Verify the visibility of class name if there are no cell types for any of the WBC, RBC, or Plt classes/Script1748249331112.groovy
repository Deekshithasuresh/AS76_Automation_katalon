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

WebUI.click(findTestObject('Object Repository/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

WebUI.click(findTestObject('Object Repository/Page_PBS/td_Monocytes'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

WebUI.click(findTestObject('Object Repository/Page_PBS/td_Monocytes'))

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.click(findTestObject('Object Repository/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Switch to original report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Download PDF report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

WebUI.click(findTestObject('Object Repository/Page_PBS/td_Monocytes'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

WebUI.click(findTestObject('Object Repository/Page_PBS/td_Monocytes'))

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.click(findTestObject('Object Repository/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Switch to original report'))

WebUI.click(findTestObject('Object Repository/Page_PBS/img_1'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Download PDF report'))

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Page_PBS/input_username_loginId'), 'santosh')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Page_PBS/td_SIG0030'))

WebUI.setText(findTestObject('Object Repository/Page_PBS/input_SIG0030_assigned_to'), 'santos')

WebUI.click(findTestObject('Object Repository/Page_PBS/li_santosh'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_RBC Morphology'))

WebUI.click(findTestObject('Object Repository/Page_PBS/div_Hemoparasite'))

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Page_PBS/input_username_loginId'), 'santosh')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Page_PBS/td_27-May-2025, 0902 AM (EAT)'))

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

WebUI.acceptAlert()

WebUI.click(findTestObject('Object Repository/Page_PBS/button_Summary'))

