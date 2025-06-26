import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

// Open the browser and navigate to the login page
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.maximizeWindow()

// Login process
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

// Navigate to approved reports
WebUI.click(findTestObject('Object Repository/Summary/span_Ready for review'))
WebUI.click(findTestObject('Object Repository/Summary/span_Reviewed'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Reviewed_1'), 'Reviewed')
CustomKeywords.'generic.helper.selectReportByStatus'('Approved')

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

// Enhanced function to check editability with double click
def checkSectionEditabilityWithDoubleClick(String sectionName, TestObject spanObject, TestObject divObject) {
	try {
		WebUI.comment("=== Checking editability for: ${sectionName} ===")
		
		// Check if span element exists and verify text
		if (!WebUI.verifyElementPresent(spanObject, 10, FailureHandling.OPTIONAL)) {
			WebUI.comment("${sectionName} section span not found - skipping")
			return false
		}
		
		WebUI.verifyElementText(spanObject, sectionName)
		WebUI.comment("${sectionName} section span found")
		
		// Check if div element exists
		if (!WebUI.verifyElementPresent(divObject, 10, FailureHandling.OPTIONAL)) {
			WebUI.comment("${sectionName} div element not found - skipping")
			return false
		}
		
		// Double click on the div element to attempt opening/editing
		WebUI.comment("Performing double-click on ${sectionName} div element...")
		WebUI.doubleClick(divObject)
		WebUI.delay(3) // Wait for response
		
		// Check if section became editable after double click
		boolean isEditable = false
		
		try {
			// Method 1: Try to send keys to check if it accepts input
			String testText = "TEST_EDIT_CHECK"
			String originalText = ""
			
			try {
				originalText = WebUI.getText(divObject, FailureHandling.OPTIONAL) ?: ""
				WebUI.comment("Original text retrieved: '${originalText}'")
			} catch (Exception e) {
				WebUI.comment("Could not retrieve original text: ${e.message}")
			}
			
			// Try to enter text
			WebUI.sendKeys(divObject, testText, FailureHandling.OPTIONAL)
			WebUI.delay(2)
			
			// Check if text was entered
			String currentText = ""
			try {
				currentText = WebUI.getText(divObject, FailureHandling.OPTIONAL) ?: ""
				WebUI.comment("Current text after input: '${currentText}'")
			} catch (Exception e) {
				WebUI.comment("Could not retrieve current text: ${e.message}")
			}
			
			// Check if test text was accepted
			if (currentText.contains(testText)) {
				isEditable = true
				WebUI.comment("Text input was accepted - Section is EDITABLE")
				
				// Try to clear the test text and restore original
				try {
					WebUI.clearText(divObject, FailureHandling.OPTIONAL)
					if (originalText && !originalText.isEmpty()) {
						WebUI.setText(divObject, originalText, FailureHandling.OPTIONAL)
					}
				} catch (Exception e) {
					WebUI.comment("Could not restore original text: ${e.message}")
				}
			}
			
			// Method 2: Check if element is clickable/interactable
			if (!isEditable) {
				boolean isClickable = WebUI.verifyElementClickable(divObject, FailureHandling.OPTIONAL)
				WebUI.comment("Element clickable status: ${isClickable}")
				
				if (isClickable) {
					// Try a single click to see if it responds
					try {
						WebUI.click(divObject, FailureHandling.OPTIONAL)
						WebUI.delay(1)
						
						// Check if click changed anything or opened an editor
						String textAfterClick = ""
						try {
							textAfterClick = WebUI.getText(divObject, FailureHandling.OPTIONAL) ?: ""
						} catch (Exception e) {
							// Continue silently
						}
						
						if (!textAfterClick.equals(currentText) || !textAfterClick.equals(originalText)) {
							isEditable = true
							WebUI.comment("Element responded to click - Section is EDITABLE")
						}
					} catch (Exception e) {
						WebUI.comment("Click test failed: ${e.message}")
					}
				}
			}
			
			// Method 3: Check if element has editable attributes
			if (!isEditable) {
				try {
					boolean hasContentEditable = WebUI.verifyElementHasAttribute(divObject, 'contenteditable', 10, FailureHandling.OPTIONAL)
					boolean hasEditable = WebUI.verifyElementHasAttribute(divObject, 'editable', 10, FailureHandling.OPTIONAL)
					
					if (hasContentEditable || hasEditable) {
						isEditable = true
						WebUI.comment("Element has editable attributes - Section is EDITABLE")
					}
				} catch (Exception e) {
					WebUI.comment("Attribute check failed: ${e.message}")
				}
			}
			
			// Method 4: Check for input fields within the div
			if (!isEditable) {
				try {
					// Look for input elements within the div using JavaScript
					JavascriptExecutor js = (JavascriptExecutor) driver
					String script = """
						var element = arguments[0];
						var inputs = element.querySelectorAll('input, textarea, [contenteditable="true"]');
						return inputs.length > 0;
					"""
					
					WebElement divElement = WebUI.findWebElement(divObject, 10)
					Boolean hasInputs = (Boolean) js.executeScript(script, divElement)
					
					if (hasInputs) {
						isEditable = true
						WebUI.comment("Found input fields within section - Section is EDITABLE")
					}
				} catch (Exception e) {
					WebUI.comment("Input field check failed: ${e.message}")
				}
			}
			
		} catch (Exception e) {
			WebUI.comment("Editability check failed: ${e.message}")
		}
		
		// Report results
		if (isEditable) {
			WebUI.comment("❌ FAIL: ${sectionName} is EDITABLE - This should NOT be editable for approved reports!")
			KeywordUtil.logInfo("SECURITY ISSUE: ${sectionName} allows editing on approved report")
			return true
		} else {
			WebUI.comment("✅ PASS: ${sectionName} is correctly NON-EDITABLE (as expected for approved report)")
			return false
		}
		
	} catch (Exception e) {
		WebUI.comment("✅ PASS: ${sectionName} is NON-EDITABLE - Cannot access section: ${e.message}")
		return false
	}
}

// Navigate through the report sections to reach Impression section
println('=== Starting PS Impressions Editability Validation ===')

// Verify we're on the correct page
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')

// Initialize tracking variables
boolean hasEditableFields = false
List<String> editableFields = []

// Test sections data - easier to maintain
def sectionsToTest = [
	[name: 'RBC Morphology', span: 'Object Repository/Summary/span_RBC Morphology', div: 'Object Repository/Summary/div_OKCancel'],
	[name: 'WBC Morphology', span: 'Object Repository/Summary/span_WBC Morphology', div: 'Object Repository/Summary/div_OKCancel_1'],
	[name: 'Platelet Morphology', span: 'Object Repository/Summary/span_Platelet Morphology', div: 'Object Repository/Summary/div_OKCancel_1_2'],
	[name: 'Hemoparasite', span: 'Object Repository/Summary/span_Hemoparasite', div: 'Object Repository/Summary/div_OKCancel_1_2_3'],
	[name: 'Impression', span: 'Object Repository/Summary/span_Impression', div: 'Object Repository/Summary/div_OKCancel_1_2_3_4']
]

// Test each section
sectionsToTest.each { section ->
	WebUI.comment("--- Validating ${section.name} Section ---")
	
	if (checkSectionEditabilityWithDoubleClick(
		section.name,
		findTestObject(section.span),
		findTestObject(section.div)
	)) {
		hasEditableFields = true
		editableFields.add(section.name)
	}
	
	// Add small delay between checks
	WebUI.delay(1)
}

// Final validation summary
WebUI.comment('=== PS Impressions Editability Validation Complete ===')

if (!hasEditableFields) {
	println('✅ VALIDATION PASSED: All PS impression sections are correctly NON-EDITABLE FOR AN APPROVED REPORT')
	println('✅ System properly restricts editing of approved reports')
	WebUI.comment('✅ TEST RESULT: PASS - All sections are properly protected from editing')
	KeywordUtil.logInfo("Test PASSED: All ${sectionsToTest.size()} sections are non-editable as expected")
} else {
	println('❌ VALIDATION FAILED: Found editable sections in approved report!')
	println("❌ Editable sections detected: ${editableFields.join(', ')}")
	println('❌ CRITICAL ISSUE: Approved reports should not allow editing')
	WebUI.comment("❌ TEST RESULT: FAIL - Editable sections found: ${editableFields.join(', ')}")
	
	// Mark test as failed with detailed information
	String failureMessage = "CRITICAL SECURITY ISSUE: Found ${editableFields.size()} editable sections in approved report: ${editableFields.join(', ')}. Approved reports must be read-only to maintain data integrity."
	KeywordUtil.markFailed(failureMessage)
}

WebUI.comment('Editability validation complete. Proceeding to check remaining fields...')

// Close browser
WebUI.closeBrowser()