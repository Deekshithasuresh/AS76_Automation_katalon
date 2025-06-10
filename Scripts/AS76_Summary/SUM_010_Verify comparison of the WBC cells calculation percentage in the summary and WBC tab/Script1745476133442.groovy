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

// Helper method to normalize cell values for comparison
String normalizeValue(String value) {
	if (value == null || value.trim().isEmpty() || value.equals("Not Found")) {
		return "-"
	}
	return value.trim().replaceAll("\\s+", " ")
}

// Helper method to find cell value with multiple xpath strategies
String findCellValue(String cellName, int maxRetries = 3) {
	List<String> xpathPatterns = [
		"//tr[td[normalize-space(text())='${cellName}']]/td[last()]",
		"//tr[td[contains(normalize-space(text()),'${cellName}')]]/td[last()]",
		"//tr[td[text()='${cellName}']]/td[last()]",
		"//tr[contains(@class,'cell-data-row') and td[normalize-space(text())='${cellName}']]/td[last()]",
		"//tr[contains(@class,'row') and td[normalize-space(text())='${cellName}']]/td[last()]",
		"//*[contains(@class,'table')]//tr[td[normalize-space(text())='${cellName}']]/td[last()]"
	]
	
	for (int retry = 0; retry < maxRetries; retry++) {
		for (String xpath : xpathPatterns) {
			try {
				TestObject testObj = new TestObject()
				testObj.addProperty('xpath', ConditionType.EQUALS, xpath)
				
				if (WebUI.verifyElementPresent(testObj, 3, FailureHandling.OPTIONAL)) {
					// Scroll to element to ensure it's visible
					WebUI.scrollToElement(testObj, 2)
					WebUI.delay(1)
					
					String cellValue = WebUI.getText(testObj)
					if (cellValue != null && !cellValue.trim().isEmpty()) {
						return normalizeValue(cellValue)
					}
				}
			} catch (Exception e) {
				WebUI.comment("Xpath attempt failed for ${cellName}: ${e.getMessage()}")
			}
		}
		
		if (retry < maxRetries - 1) {
			WebUI.comment("Retry ${retry + 1} for ${cellName}")
			WebUI.delay(2)
		}
	}
	
	return "Not Found"
}

// Login to the application
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()

// Wait for the page to load
WebUI.waitForPageLoad(30)

// Navigate to the specific slide
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/td_SIG013'), 20)
WebUI.click(findTestObject('Object Repository/retain_patchs/td_SIG013'))

// Define WBC cells to verify
List<String> cellsToVerify = [
	'Neutrophils',
	'Lymphocytes',
	'Eosinophils',
	'Monocytes',
	'Basophils',
	'Immature Granulocytes',
	'Atypical Cells/Blasts',
	'Immature Eosinophils',
	'Immature Basophils',
	'Promonocytes',
	'Prolymphocytes',
	'Hairy Cells',
	'Sezary Cells',
	'Plasma Cells',
	'Others'
]

// Maps to store values from both tabs
Map<String, String> wbcValues = [:]
Map<String, String> summaryValues = [:]

WebUI.comment("=== STARTING WBC AND SUMMARY TAB COMPARISON ===")

// STEP 1: Collect values from WBC tab
WebUI.comment("=== COLLECTING VALUES FROM WBC TAB ===")
WebUI.click(findTestObject('Object Repository/Summary/button_WBC'))
WebUI.waitForPageLoad(5)
WebUI.delay(3)

// Ensure page is fully loaded and scroll to make content visible
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight/3)", null)
WebUI.delay(2)

for (String cellName : cellsToVerify) {
	WebUI.comment("Searching for ${cellName} in WBC tab...")
	String cellValue = findCellValue(cellName)
	wbcValues.put(cellName, cellValue)
	WebUI.comment("WBC - ${cellName}: ${cellValue}")
}

WebUI.comment("WBC tab data collection completed. Found ${wbcValues.size()} cells.")

// STEP 2: Collect values from Summary tab
WebUI.comment("=== COLLECTING VALUES FROM SUMMARY TAB ===")
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.waitForPageLoad(5)
WebUI.delay(3)

// Ensure page is fully loaded and scroll to make content visible
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight/3)", null)
WebUI.delay(2)

for (String cellName : cellsToVerify) {
	WebUI.comment("Searching for ${cellName} in Summary tab...")
	String cellValue = findCellValue(cellName)
	summaryValues.put(cellName, cellValue)
	WebUI.comment("Summary - ${cellName}: ${cellValue}")
}

WebUI.comment("Summary tab data collection completed. Found ${summaryValues.size()} cells.")

// STEP 3: Compare values between tabs
WebUI.comment("=== COMPARING VALUES BETWEEN WBC AND SUMMARY TABS ===")

int totalCells = cellsToVerify.size()
int passedCount = 0
int failedCount = 0
int notFoundInWBC = 0
int notFoundInSummary = 0
int notFoundInBoth = 0

List<String> passedCells = []
List<String> failedCells = []
List<String> wbcMissingCells = []
List<String> summaryMissingCells = []
List<String> bothMissingCells = []

for (String cellName : cellsToVerify) {
	String wbcValue = wbcValues.get(cellName) ?: "Not Found"
	String summaryValue = summaryValues.get(cellName) ?: "Not Found"
	
	boolean wbcFound = !wbcValue.equals("Not Found")
	boolean summaryFound = !summaryValue.equals("Not Found")
	
	if (!wbcFound && !summaryFound) {
		WebUI.comment("⚠️ ${cellName} - NOT FOUND in both tabs")
		bothMissingCells.add(cellName)
		notFoundInBoth++
	} else if (!wbcFound) {
		WebUI.comment("⚠️ ${cellName} - NOT FOUND in WBC tab (Summary: ${summaryValue})")
		wbcMissingCells.add(cellName)
		notFoundInWBC++
	} else if (!summaryFound) {
		WebUI.comment("⚠️ ${cellName} - NOT FOUND in Summary tab (WBC: ${wbcValue})")
		summaryMissingCells.add(cellName)
		notFoundInSummary++
	} else {
		// Both values found, compare them
		if (wbcValue.equals(summaryValue)) {
			WebUI.comment("✅ ${cellName} - VALUES MATCH: ${wbcValue}")
			passedCells.add(cellName)
			passedCount++
		} else {
			WebUI.comment("❌ ${cellName} - VALUES MISMATCH: WBC='${wbcValue}' vs Summary='${summaryValue}'")
			failedCells.add(cellName)
			failedCount++
		}
	}
}

// STEP 4: Generate comprehensive report
WebUI.comment("=== COMPREHENSIVE COMPARISON REPORT ===")
WebUI.comment("📊 STATISTICS:")
WebUI.comment("   Total cells to verify: ${totalCells}")
WebUI.comment("   Values match: ${passedCount}")
WebUI.comment("   Values mismatch: ${failedCount}")
WebUI.comment("   Not found in WBC only: ${notFoundInWBC}")
WebUI.comment("   Not found in Summary only: ${notFoundInSummary}")
WebUI.comment("   Not found in both tabs: ${notFoundInBoth}")

if (passedCells.size() > 0) {
	WebUI.comment("✅ MATCHING CELLS (${passedCount}): ${passedCells.join(', ')}")
}

if (failedCells.size() > 0) {
	WebUI.comment("❌ MISMATCHED CELLS (${failedCount}): ${failedCells.join(', ')}")
}

if (wbcMissingCells.size() > 0) {
	WebUI.comment("⚠️ MISSING FROM WBC TAB (${notFoundInWBC}): ${wbcMissingCells.join(', ')}")
}

if (summaryMissingCells.size() > 0) {
	WebUI.comment("⚠️ MISSING FROM SUMMARY TAB (${notFoundInSummary}): ${summaryMissingCells.join(', ')}")
}

if (bothMissingCells.size() > 0) {
	WebUI.comment("⚠️ MISSING FROM BOTH TABS (${notFoundInBoth}): ${bothMissingCells.join(', ')}")
}

// STEP 5: Calculate and display success metrics
int cellsFoundInBothTabs = passedCount + failedCount
int totalIssues = failedCount + notFoundInWBC + notFoundInSummary + notFoundInBoth

WebUI.comment("=== FINAL RESULTS ===")

if (totalIssues == 0) {
	WebUI.comment("🎉 PERFECT MATCH! All ${totalCells} cells found and values match between WBC and Summary tabs.")
} else {
	WebUI.comment("📋 SUMMARY:")
	
	if (cellsFoundInBothTabs > 0) {
		double matchRate = (passedCount * 100.0) / cellsFoundInBothTabs
		WebUI.comment("   Match Rate: ${String.format('%.1f', matchRate)}% (${passedCount}/${cellsFoundInBothTabs} found cells)")
	}
	
	if (failedCount == 0) {
		WebUI.comment("   ✅ No value mismatches found!")
	} else {
		WebUI.comment("   ❌ ${failedCount} value mismatch(es) detected")
	}
	
	if (notFoundInWBC + notFoundInSummary + notFoundInBoth > 0) {
		WebUI.comment("   ⚠️ ${notFoundInWBC + notFoundInSummary + notFoundInBoth} cell(s) had visibility issues")
	}
}

// STEP 6: Detailed value comparison table
WebUI.comment("=== DETAILED VALUE COMPARISON TABLE ===")
WebUI.comment(String.format("%-25s | %-15s | %-15s | %s", "Cell Name", "WBC Value", "Summary Value", "Status"))
WebUI.comment("${'-' * 80}")

for (String cellName : cellsToVerify) {
	String wbcVal = wbcValues.get(cellName) ?: "Not Found"
	String summaryVal = summaryValues.get(cellName) ?: "Not Found"
	String status = ""
	
	if (wbcVal.equals("Not Found") && summaryVal.equals("Not Found")) {
		status = "Both Missing"
	} else if (wbcVal.equals("Not Found")) {
		status = "WBC Missing"
	} else if (summaryVal.equals("Not Found")) {
		status = "Summary Missing"
	} else if (wbcVal.equals(summaryVal)) {
		status = "✅ Match"
	} else {
		status = "❌ Mismatch"
	}
	
	WebUI.comment(String.format("%-25s | %-15s | %-15s | %s",
		cellName.length() > 25 ? cellName.substring(0, 22) + "..." : cellName,
		wbcVal.length() > 15 ? wbcVal.substring(0, 12) + "..." : wbcVal,
		summaryVal.length() > 15 ? summaryVal.substring(0, 12) + "..." : summaryVal,
		status))
}

// Final cleanup - scroll to top
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(2)

WebUI.comment("=== WBC vs SUMMARY COMPARISON COMPLETED ===")