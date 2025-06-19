
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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
/**
 * Test Case: PBS Report Assignment
 * This test checks for reports with "To be reviewed" status and assigns them to deekshithaS.
 * If no "To be reviewed" reports are found, it checks the 3rd report.
 * If the 3rd report is not assigned to deekshithaS, it reassigns it.
 */

// Open browser and login to PBS system
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver


// Define expected texts
def expectedTexts = [
    'Peripheral Smear Report': 'Peripheral Smear Report',
    'RBC Morphology': 'RBC Morphology',
    'WBC Morphology': 'WBC Morphology',
    'Platelet Morphology': 'Platelet Morphology',
    'Hemoparasite': 'Hemoparasite',
    'Impression': 'Impression'
]

// Define test objects
def testObjects = [
    'Peripheral Smear Report': findTestObject('Object Repository/Summary/span_Peripheral Smear Report'),
    'RBC Morphology': findTestObject('Object Repository/Summary/span_RBC Morphology'),
    'WBC Morphology': findTestObject('Object Repository/Summary/span_WBC Morphology'),
    'Platelet Morphology': findTestObject('Object Repository/Summary/span_Platelet Morphology'),
    'Hemoparasite': findTestObject('Object Repository/Summary/span_Hemoparasite'),
    'Impression': findTestObject('Object Repository/Summary/span_Impression')
]

println "==== Summary Elements Text Verification ===="

boolean allPassed = true

// Verify each element
expectedTexts.each { elementName, expectedText ->
    // Capture actual text
    String actualText = WebUI.getText(testObjects[elementName])
    
    // Compare expected vs actual
    boolean isMatch = (actualText == expectedText)
    
    // Verify element text
    WebUI.verifyElementText(testObjects[elementName], expectedText)
    
    // Print comparison results
    println "${elementName}:"
    println "  Expected: '${expectedText}'"
    println "  Actual: '${actualText}'"
    println "  Match: ${isMatch ? '✅ PASS' : '❌ FAIL'}"
    println "  ---"
    
    if (!isMatch) {
        allPassed = false
    }
}

println "============================================="

// Final summary
if (allPassed) {
    println "🎉 ALL ELEMENTS PASSED: All summary elements match expected text"
} else {
    println "⚠️ SOME ELEMENTS FAILED: Check individual results above"
}

// Log for reporting
WebUI.comment("Summary elements verification completed - Overall result: ${allPassed ? 'PASS' : 'FAIL'}")