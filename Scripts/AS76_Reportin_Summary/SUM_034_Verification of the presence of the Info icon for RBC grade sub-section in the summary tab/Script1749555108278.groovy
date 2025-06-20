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



WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/i_icon_summ_RBC'),0)
//WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/i_icon_summ_wbc'), 0)


// Verify if info button is present
boolean isInfoButtonPresent = WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/i_icon_summ_RBC'), 10, FailureHandling.OPTIONAL)

// Print message about info button presence
if (isInfoButtonPresent) {
	WebUI.comment("INFO: The info button is present on the page")
	println("INFO: The info button is present on the page")
} else {
	WebUI.comment("INFO: The info button is NOT present on the page")
	println("INFO: The info button is NOT present on the page")
}



//WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/i_icon_summ_RBC'))
