
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

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()
JavascriptExecutor js = (JavascriptExecutor) driver

// Wait for the table to be visible
WebUI.waitForElementVisible(findTestObject('Object Repository/Summary/Page_PBS (1)/table'), 30)

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

//CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// Simple WBC Details Verification

// Check if WBC differential count section is present
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/span_WBC differential count ()'), 5)) {
    println("WBC differential count section - Present")
    
    // Check Cell name header
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Cell name'), 5)) {
        println("Cell name header - Present")
    } else {
        println("Cell name header - Missing")
        KeywordUtil.markFailed("Cell name header not found")
    }
    
    // Check percentage header
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/th_'), 5)) {
        println("Percentage header - Present")
    } else {
        println("Percentage header - Missing")
        KeywordUtil.markFailed("Percentage header not found")
    }
    
    // Check WBC row
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/th_WBC'), 5)) {
        println("WBC row - Present")
    } else {
        println("WBC row - Missing")
        KeywordUtil.markFailed("WBC row not found")
    }
    
    // Check Non-WBC row
    if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Non-WBC'), 5)) {
        println("Non-WBC row - Present")
    } else {
        println("Non-WBC row - Missing")
        KeywordUtil.markFailed("Non-WBC row not found")
    }
    
    println("All WBC details verified successfully")
    
} else {
    println("WBC differential count section - Missing")
    KeywordUtil.markFailedAndStop("WBC section not found")
}


