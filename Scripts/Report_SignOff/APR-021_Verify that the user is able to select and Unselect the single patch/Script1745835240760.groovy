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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Report-Signoff/input_username_loginId'), 'manju')

WebUI.setEncryptedText(findTestObject('Object Repository/Report-Signoff/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/input_Clear filters_PrivateSwitchBase-input_068da8'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/td_22-Apr-2025, 0404 PM (EAT)'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/span_Approve report'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Confirm'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/span_Add supporting images'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/span_Modify'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/img_Neutrophils_MuiImageListItem-img qa_pat_2b6ddc'))

WebUI.verifyElementText(findTestObject('Object Repository/Report-Signoff/div_3'), '3')

WebUI.click(findTestObject('Object Repository/Report-Signoff/img_Neutrophils_MuiImageListItem-img qa_pat_2b6ddc'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report-Signoff/img_Neutrophils_MuiImageListItem-img qa_pat_2b6ddc'), 
    0)

WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Cancel'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/span_Modify_1'))

WebUI.click(findTestObject('Object Repository/Report-Signoff/div_Lymphocytes_MuiImageListItemBar-titleWr_4b456b'))

WebUI.verifyElementText(findTestObject('Object Repository/Report-Signoff/div_3'), '3')

WebUI.click(findTestObject('Object Repository/Report-Signoff/img_Lymphocytes_MuiImageListItem-img qa_pat_a69c43'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report-Signoff/img_Lymphocytes_MuiImageListItem-img qa_pat_a69c43'), 
    0)

WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Cancel'))

WebUI.closeBrowser()

