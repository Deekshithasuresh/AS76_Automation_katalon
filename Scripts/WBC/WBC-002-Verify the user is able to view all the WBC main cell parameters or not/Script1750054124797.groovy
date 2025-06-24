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

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/pbs/reportlist')

WebUI.setText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_username_loginId'), 'Chidu')

WebUI.setEncryptedText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/input_password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

//WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_Sign In'))

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.click(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/button_WBC'))

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Neutrophils'), 'Neutrophils')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Lymphocytes'), 'Lymphocytes')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Eosinophils'), 'Eosinophils')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Monocytes'), 'Monocytes')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Basophils'), 'Basophils')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Granulocytes'), 'Immature Granulocytes')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Atypical CellsBlasts'), 'Atypical Cells/Blasts')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Eosinophils'), 'Immature Eosinophils*')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Immature Basophils'), 'Immature Basophils*')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Promonocytes'), 'Promonocytes*')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Prolymphocytes'), 'Prolymphocytes*')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Hairy Cells'), 'Hairy Cells*')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Sezary Cells'), 'Sezary Cells*')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Plasma Cells'), 'Plasma Cells*')

WebUI.verifyElementText(findTestObject('Object Repository/WBC/Page_PBS/Page_PBS/td_Others'), 'Others*')

