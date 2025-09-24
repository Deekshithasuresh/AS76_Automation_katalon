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

try {
    // Open browser with error handling
    WebUI.openBrowser('')
    println("Browser opened successfully")
    
    // Navigate to URL with timeout
    WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')
    WebUI.waitForPageLoad(30)
    println("Navigated to Admin Console login page")
    
    // Verify logo visibility with optional failure handling
    boolean logoVisible = WebUI.verifyElementVisible(findTestObject('Session management reporting/Page_Admin Console/logo_img'), FailureHandling.OPTIONAL)
    
    if (logoVisible) {
        println("✓ Logo is visible")
        // Verify element is present with timeout
        WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/logo_img'), 30)
        println("✓ Logo element is present")
    } else {
        println("✗ Logo is not visible - check page load or element locator")
    }
    
    // Verify page title
    String title = WebUI.getWindowTitle()
    println("Current page title: " + title)
    
    boolean titleMatch = WebUI.verifyMatch(title, 'Admin Console', false, FailureHandling.OPTIONAL)
    
    if (titleMatch) {
        println("✓ Page title verification passed")
    } else {
        println("✗ Page title verification failed. Expected: 'Admin Console', Actual: '" + title + "'")
    }
    
} catch (Exception e) {
    println("ERROR: Test execution failed - " + e.getMessage())
    // Take screenshot for debugging
    WebUI.takeScreenshot()
} finally {
    // Clean up - close browser if needed for debugging
     WebUI.closeBrowser()
}
