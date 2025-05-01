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

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PBS/Xicon'), 0)

WebUI.closeBrowser()



//WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
//
//WebUI.setText(findTestObject('Object Repository/Page_PBS/input_username_loginId'), 'manju')
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RDxwVrYbiMqcg==')
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/button_Sign In'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/button_password_MuiButtonBase-root MuiIconB_027ea7'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/input_password_loginPassword'))
//
//WebUI.setEncryptedText(findTestObject('Object Repository/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/button_Sign In'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/div_22-Apr-2025, 0116 PM (UTC)'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/div_Slide Id_MuiAutocomplete-endAdornment c_5c7b3b'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/div_Slide Id_MuiAutocomplete-endAdornment c_5c7b3b'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/div_Slide Id_MuiAutocomplete-endAdornment c_5c7b3b'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/div_21-Apr-2025, 1213 PM (UTC)'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/div_Slide Id_MuiAutocomplete-endAdornment c_5c7b3b'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/td_25-Apr-2025, 0637 AM (UTC)'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/img'))
//
//WebUI.click(findTestObject('Object Repository/Page_PBS/li_pawankumar'))
//
//WebUI.verifyElementPresent(findTestObject('Object Repository/Page_PBS/assign__dd'), 
//    0)
//
//WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_PBS/assign__dd'), 
//    0)
//
//WebUI.waitForElementNotPresent(findTestObject('Object Repository/Page_PBS/assign__dd'), 
//    0)
//
//WebUI.closeBrowser(

