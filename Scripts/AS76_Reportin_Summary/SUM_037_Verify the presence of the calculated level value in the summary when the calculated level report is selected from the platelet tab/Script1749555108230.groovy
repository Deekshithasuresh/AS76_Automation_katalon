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

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Platelets'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_Platelet count (x 109L)_platelet-coun_cc3a22'), 
    '')

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/input_Platelet count (x 109L)_platelet-coun_cc3a22'))



WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Summary'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet count level'), 'Platelet count level')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Significantly decreased'), 'Significantly decreased')


// Verify the text of the "Platelet count level" and "Significantly decreased"
String plateletCountLevel = WebUI.getText(findTestObject('Object Repository/Summary/span_Platelet count level'))
String plateletCountStatus = WebUI.getText(findTestObject('Object Repository/Summary/span_Significantly decreased'))

// Expected values
String expectedPlateletCountLevel = 'Platelet count level'
String expectedPlateletCountStatus = 'Significantly decreased'

// Check if both values are equal to the expected ones
if (plateletCountLevel == expectedPlateletCountLevel && plateletCountStatus == expectedPlateletCountStatus) {
	println("Both values are as expected.")
} else {
	println("Values do not match the expected text.")
}
