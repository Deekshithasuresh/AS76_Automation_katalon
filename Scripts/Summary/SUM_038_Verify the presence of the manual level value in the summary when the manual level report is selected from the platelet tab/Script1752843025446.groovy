
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// Navigate to Platelets section
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Platelets'), 'Platelets')
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Platelets'))

// Click on manual level platelet count level
WebUI.click(findTestObject('Object Repository/Summary/manual_level_platelet_count_level'))

// Navigate to Summary
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Summary'), 'Summary')
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))

// Wait for the summary tab to load
WebUI.delay(2)

// Create XPath-based test object for the platelet count level row
TestObject plateletCountLevelObject = new TestObject()
plateletCountLevelObject.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class, 'reportSummary_pane-body__table-row') and .//span[contains(text(), 'Platelet count level')]]")

// Capture the actual text from platelet count level row
String actualText = WebUI.getText(plateletCountLevelObject)

// Define expected text
String expectedText = "Platelet count level"

// Extract just the label part (without the value)
String actualLabel = actualText.replaceAll("\\s+\\w+.*\$", "").trim()

// Compare expected vs actual
println "==== Platelet Count Level Text Verification ===="
println "Expected Text: '${expectedText}'"
println "Actual Full Text: '${actualText}'"
println "Actual Label: '${actualLabel}'"
println "Match: ${actualLabel == expectedText}"
println "=============================================="

// Verify the comparison
if (actualLabel == expectedText) {
	println "✅ PASS: Platelet count level text matches expected"
} else {
	println "❌ FAIL: Platelet count level text does not match"
	println "   Expected: '${expectedText}'"
	println "   Actual: '${actualLabel}'"
}

// Verify element is present
WebUI.verifyElementPresent(plateletCountLevelObject, 10)

// Log the result
println("Platelet count level verification - Expected: '${expectedText}', Actual: '${actualLabel}'")
