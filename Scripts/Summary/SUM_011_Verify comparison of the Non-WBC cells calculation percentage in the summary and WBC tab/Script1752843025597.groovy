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
import org.openqa.selenium.support.Color as Color
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.testobject.ConditionType as ConditionType

// Login to the application
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()

// Wait for the page to load
WebUI.waitForPageLoad(30)

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')


// Define the non-WBC cells to verify
List<String> cellsToVerify = [
	'NRBC',
	'Smudge Cells'
]

// Maps to store values from both tabs
Map<String, String> wbcValues = [:]
Map<String, String> summaryValues = [:]

// First click on WBC tab and collect all cell values
WebUI.click(findTestObject('Object Repository/Summary/button_WBC'))
WebUI.delay(3)

// Scroll to top of the page first
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)

// Scroll to ensure all cells are visible
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight/2)", null)
WebUI.delay(2)

WebUI.comment("=== Collecting values from WBC tab ===")

for (String cellName : cellsToVerify) {
	try {
		TestObject cellObject = new TestObject()
		cellObject.addProperty('xpath', ConditionType.EQUALS, "//tr[td[text()='${cellName}']]/td[last()]")
		
		WebUI.waitForElementPresent(cellObject, 10)
		WebUI.scrollToElement(cellObject, 5)
		String cellValue = WebUI.getText(cellObject).trim()
		wbcValues.put(cellName, cellValue)
		WebUI.comment("WBC - ${cellName}: ${cellValue}")
	} catch (Exception e) {
		wbcValues.put(cellName, "Not Found")
		WebUI.comment("WBC - ${cellName}: Not Found")
	}
}

// Now click on Summary tab and collect all cell values
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(3)

// Scroll to ensure all cells are visible
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight/2)", null)
WebUI.delay(2)

WebUI.comment("=== Collecting values from Summary tab ===")

for (String cellName : cellsToVerify) {
	try {
		TestObject cellObject = new TestObject()
		cellObject.addProperty('xpath', ConditionType.EQUALS, "//tr[td[text()='${cellName}']]/td[last()]")
		
		WebUI.waitForElementPresent(cellObject, 10)
		WebUI.scrollToElement(cellObject, 5)
		String cellValue = WebUI.getText(cellObject).trim()
		summaryValues.put(cellName, cellValue)
		WebUI.comment("Summary - ${cellName}: ${cellValue}")
	} catch (Exception e) {
		summaryValues.put(cellName, "Not Found")
		WebUI.comment("Summary - ${cellName}: Not Found")
	}
}

WebUI.waitForPageLoad(5)

// Scroll up to the top
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)

// Scroll down to the end to show complete summary
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)", null)
WebUI.delay(3)

// Compare all cell values and generate report
WebUI.comment("=== VERIFICATION RESULTS ===")
int passedCount = 0
int failedCount = 0

for (String cellName : cellsToVerify) {
	String wbcValue = wbcValues.get(cellName)
	String summaryValue = summaryValues.get(cellName)
	
	if (wbcValue.equals(summaryValue)) {
		WebUI.comment("✓ ${cellName} verification PASSED - Values match: ${wbcValue}")
		passedCount++
	} else {
		WebUI.comment("✗ ${cellName} verification FAILED - WBC: ${wbcValue} | Summary: ${summaryValue}")
		failedCount++
	}
}

// Final summary report
WebUI.comment("=== FINAL SUMMARY ===")
WebUI.comment("Total cells verified: ${cellsToVerify.size()}")
WebUI.comment("Passed: ${passedCount}")
WebUI.comment("Failed: ${failedCount}")

if (failedCount == 0) {
	WebUI.comment("🎉 ALL VERIFICATIONS PASSED! WBC and Summary tabs match perfectly.")
} else {
	WebUI.comment("⚠️ ${failedCount} verification(s) failed. Please review the mismatched values.")
}

// Final scroll to top
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)