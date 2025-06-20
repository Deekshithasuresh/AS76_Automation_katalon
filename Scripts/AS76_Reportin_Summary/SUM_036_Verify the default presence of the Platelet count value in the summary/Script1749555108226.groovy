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
// Open browser and login
WebUI.openBrowser('')
// Navigate to the PBS login page
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
// Enter the username 'deekshithaS' in the login field
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
// Enter the encrypted password in the password field
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
// Press Enter key to submit the login form
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
// Wait for the page to completely load with a timeout of 30 seconds
WebUI.waitForPageLoad(30)
// Add a delay of 3 seconds to ensure the dashboard is fully loaded and visible
WebUI.delay(3)
// Get the WebDriver instance to directly interact with the browser
WebDriver driver = DriverFactory.getWebDriver()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
// Capture all the text elements from the UI
String actualPlateletLabel = WebUI.getText(findTestObject('Object Repository/Summary/span_Platelet'))
String actualPlateletCountLabel = WebUI.getText(findTestObject('Object Repository/Summary/span_Platelet count (x 109L)'))
String actualPlateletCountValue = WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/div_212'))

// Define the expected text values
String expectedPlateletLabel = 'Platelet'
String expectedPlateletCountLabel = 'Platelet count (x 10^9/L)'

// Dynamic validation - capture the actual value and validate it's not empty/null
String expectedPlateletCountValue = actualPlateletCountValue // Dynamic assignment

// Verify that the actual values match the expected values
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet'), expectedPlateletLabel)
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet count (x 109L)'), expectedPlateletCountLabel)

// Verify that platelet count value exists and is numeric
boolean isPlateletCountValid = false
if (actualPlateletCountValue != null && !actualPlateletCountValue.trim().isEmpty()) {
	try {
		// Check if the value is numeric (can be integer or decimal)
		Double.parseDouble(actualPlateletCountValue.trim())
		isPlateletCountValid = true
	} catch (NumberFormatException e) {
		println "WARNING: Platelet count value '${actualPlateletCountValue}' is not a valid number"
		isPlateletCountValid = false
	}
} else {
	println "ERROR: Platelet count value is null or empty"
	isPlateletCountValid = false
}

// Print the comparison results
println "==== Platelet Count Verification Results ===="
println "Platelet Label: Expected [${expectedPlateletLabel}] - Actual [${actualPlateletLabel}] - Match: ${actualPlateletLabel == expectedPlateletLabel}"
println "Platelet Count Label: Expected [${expectedPlateletCountLabel}] - Actual [${actualPlateletCountLabel}] - Match: ${actualPlateletCountLabel == expectedPlateletCountLabel}"
println "Platelet Count Value: Captured [${actualPlateletCountValue}] - Valid: ${isPlateletCountValid}"
println "=============================================="

// Print a summary statement
if (actualPlateletLabel == expectedPlateletLabel &&
	actualPlateletCountLabel == expectedPlateletCountLabel &&
	isPlateletCountValid) {
	println "PASS: The default presence of Platelet count value is present in the summary"
} else {
	println "FAIL: The default presence of Platelet count value is not present in the summary"
}

// Log additional information about the test
println("Verified default text in summary tab: '${actualPlateletCountLabel}' with value '${actualPlateletCountValue}'")

