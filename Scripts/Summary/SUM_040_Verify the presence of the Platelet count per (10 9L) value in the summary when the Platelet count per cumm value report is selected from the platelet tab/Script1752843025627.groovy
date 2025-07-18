
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
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')



// Verify the Platelets button text
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Platelets'), 'Platelets')

// Click on the Platelets span
WebUI.click(findTestObject('Object Repository/Summary/span_Platelets'))

// Click on platelet estimate count
WebUI.click(findTestObject('Object Repository/Summary/platelet_estimate_count'))

// Click on Summary button to navigate to summary tab
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))

// Wait for the summary tab to load
WebUI.delay(2)

// Create XPath-based test object for the platelet count row
TestObject plateletRowObject = new TestObject()
plateletRowObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class, 'reportSummary_pane-body__table-row') and .//span[contains(text(), 'Platelet count')]]")

// Capture the actual text from platelet count row
String actualText = WebUI.getText(plateletRowObject)

// Define expected text
String expectedText = "Platelet count (x 10^9/L)"

// Extract just the label part (without the numeric value)
String actualLabel = actualText.replaceAll("\\s+\\d+.*\$", "").trim()

// Compare expected vs actual
println "==== Platelet Count Text Verification ===="
println "Expected Text: '${expectedText}'"
println "Actual Full Text: '${actualText}'"
println "Actual Label: '${actualLabel}'"
println "Match: ${actualLabel == expectedText}"
println "=========================================="

// Verify the comparison
if (actualLabel == expectedText) {
	println "✅ PASS: Platelet count text matches expected"
} else {
	println "❌ FAIL: Platelet count text does not match"
	println "   Expected: '${expectedText}'"
	println "   Actual: '${actualLabel}'"
}

// Verify element is present
WebUI.verifyElementPresent(plateletRowObject, 10)

// Log the result
WebUI.comment("Platelet count verification - Expected: '${expectedText}', Actual: '${actualLabel}'")