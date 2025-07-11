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

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

//WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0015'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology'), 'WBC Morphology')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology'), 'Platelet Morphology')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/img'), '')

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/img'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Ready for review'), 'Ready for review')

WebUI.click(findTestObject('Object Repository/Summary/span_Ready for review'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Reviewed'), 'Reviewed')

WebUI.click(findTestObject('Object Repository/Summary/span_Reviewed'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/img_yy_reportStatusComponent_image__NPeFc'), '')

//WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Approved'), 'Approved')

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Approved')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology'), 'WBC Morphology')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology'), 'Platelet Morphology')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')

