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

/**
 * Test Case: PBS Report Assignment
 * This test verifies the slide assignment functionality of the PBS system
 */
// Login to the PBS system
WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()



// Get the report ID before clicking
def reportId = WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0015'))

println('Selecting report with ID: ' + reportId)

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

//CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// WBC Text Verification with Conditional Statements

// Verify WBC element text
//if (WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_WBC'), 'WBC')) {
//    println("WBC text present - Passed")
//} else {
//    println("WBC text not present - Failed")
//    KeywordUtil.markFailed("WBC text does not match expected value")
//}
//
//// Verify Non-WBC element text
//if (WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Non-WBC'), 'Non-WBC')) {
//    println("Non-WBC text present - Passed")
//} else {
//    println("Non-WBC text not present - Failed")
//    KeywordUtil.markFailed("Non-WBC text does not match expected value")
//}


if (WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_WBC'), 'WBC')) {
	println("WBC text present - Passed")
} else {
	println("WBC text not present - Failed")
	KeywordUtil.markFailed("WBC text does not match expected value")
}

// Scroll to Non-WBC element before verification
WebUI.scrollToElement(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Non-WBC'), 5)

// Verify Non-WBC element text
if (WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Non-WBC'), 'Non-WBC')) {
	println("Non-WBC text present - Passed")
} else {
	println("Non-WBC text not present - Failed")
	KeywordUtil.markFailed("Non-WBC text does not match expected value")
}

