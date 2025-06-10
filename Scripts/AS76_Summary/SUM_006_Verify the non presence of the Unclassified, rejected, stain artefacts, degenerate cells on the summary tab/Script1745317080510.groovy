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

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_44'))

//WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_44'))

// Scroll to ensure all content is loaded
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)", null)
WebUI.delay(2)

// Check if Degenerate Cells is not present
if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Degenerate Cells'), 0, FailureHandling.OPTIONAL)) {
    WebUI.comment("✓ SUCCESS: 'Degenerate Cells' is not present in summary")
} else {
    WebUI.comment("❌ ISSUE: 'Degenerate Cells' found in summary - should not be present")
}

// Check if Stain Artefacts is not present
if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Stain Artefacts'), 0, FailureHandling.OPTIONAL)) {
    WebUI.comment("✓ SUCCESS: 'Stain Artefacts' is not present in summary")
} else {
    WebUI.comment("❌ ISSUE: 'Stain Artefacts' found in summary - should not be present")
}

// Check if Unclassified is not present
if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Unclassified'), 0, FailureHandling.OPTIONAL)) {
    WebUI.comment("✓ SUCCESS: 'Unclassified' is not present in summary")
} else {
    WebUI.comment("❌ ISSUE: 'Unclassified' found in summary - should not be present")
}

// Check if Rejected is not present
if (WebUI.verifyElementNotPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Rejected'), 0, FailureHandling.OPTIONAL)) {
    WebUI.comment("✓ SUCCESS: 'Rejected' is not present in summary")
} else {
    WebUI.comment("❌ ISSUE: 'Rejected' found in summary - should not be present")
}

// Scroll back to top
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(1)

WebUI.comment("=== Secondary Cells Verification Complete ===")