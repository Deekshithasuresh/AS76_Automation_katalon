import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import Libs.CustomKeywords as Keys

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

// Wait for the dashboard to load
WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0015'), 20)

// Get the report ID before clicking
def reportId = WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0015'))

println('Selecting report with ID: ' + reportId)

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

//CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// WBC Text Verification with Conditional Statements

// Verify WBC element text
if (WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_WBC'), 'WBC')) {
    println("WBC text present - Passed")
} else {
    println("WBC text not present - Failed")
    KeywordUtil.markFailed("WBC text does not match expected value")
}

// Verify Non-WBC element text
if (WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Non-WBC'), 'Non-WBC')) {
    println("Non-WBC text present - Passed")
} else {
    println("Non-WBC text not present - Failed")
    KeywordUtil.markFailed("Non-WBC text does not match expected value")
}

