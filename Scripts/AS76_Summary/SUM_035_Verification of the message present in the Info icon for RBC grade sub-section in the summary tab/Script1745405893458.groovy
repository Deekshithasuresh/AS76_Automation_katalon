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

// Login steps
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'))
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_44'))

// Wait for page to load
WebUI.delay(3)

// Define expected tooltip text
String expectedText = "Please check the RBC tab for complete evaluation"

println("Expected tooltip text: " + expectedText)

// Hover over the WBC info icon
WebUI.mouseOver(findTestObject('Object Repository/Summary/i_icon_summ_RBC'))
println("Hovering over RBC info icon...")

// Wait for tooltip to appear
WebUI.delay(2)

// Check if tooltip text appears on the page
String jsScript = '''
    var expectedText = arguments[0];
    var allElements = document.querySelectorAll('*');
    
    for (var i = 0; i < allElements.length; i++) {
        var element = allElements[i];
        var text = element.textContent || element.innerText || '';
        
        if (text.trim() === expectedText) {
            return true;
        }
    }
    return false;
'''

boolean tooltipFound = WebUI.executeJavaScript(jsScript, [expectedText])

if (tooltipFound) {
	println("✓ PASS: Expected tooltip text is appearing on hover")
	println("Tooltip text found: " + expectedText)
} else {
	println("✗ FAIL: Expected tooltip text is NOT appearing on hover")
	println("Expected: " + expectedText)
}

WebUI.closeBrowser()


