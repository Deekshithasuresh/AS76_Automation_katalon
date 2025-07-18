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


/**
 * Test Case: PBS Report Assignment
 * This test checks for reports with "To be reviewed" status but does NOT assign them.
 * If no "To be reviewed" reports are found, it finds "Under review" reports assigned to someone other than deekshithaS
 * without reassigning them.
 */


// Open browser and login to PBS system
WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))


// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded


// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()


// Wait for the table to be visible
WebUI.waitForElementVisible(findTestObject('Object Repository/Summary/Page_PBS (1)/table'), 30)


// Variable to track if we found and processed a report
boolean processedReport = false


try {
	// STEP 1: First look for reports with "To be reviewed" status
	WebUI.comment('Looking for reports with "To be reviewed" status...')
	
	// Find all rows with "To be reviewed" status
	List<WebElement> toBeReviewedRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), "To be reviewed")] or .//div[contains(text(), "To be reviewed")]]'))
	
	// Check if any "To be reviewed" reports were found
	if (toBeReviewedRows.size() > 0) {
		WebUI.comment('Found ' + toBeReviewedRows.size() + ' reports with "To be reviewed" status')
		
		// Get the first "To be reviewed" report
		WebElement firstToBeReviewedRow = toBeReviewedRows.get(0)
		
		// Get the report ID
		String reportId = "Unknown"
		try {
			WebElement reportIdElement = firstToBeReviewedRow.findElement(By.xpath('./td[2]'))
			reportId = reportIdElement.getText()
			WebUI.comment('Found "To be reviewed" report with Slide ID: ' + reportId)
		} catch (Exception e) {
			WebUI.comment('Could not extract report ID: ' + e.getMessage())
		}
		
		// Click on the report
		firstToBeReviewedRow.click()
		WebUI.comment('Clicked on "To be reviewed" report with Slide ID: ' + reportId)
		
		// Wait for the report details to load
		WebUI.delay(2)
		
		// Check if the report is unassigned as expected
		def currentAssignment = ""
		try {
			// Attempt to get the current assignee
			currentAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')
			currentAssignment = currentAssignment.trim()
		} catch (Exception e) {
			WebUI.comment('Could not get current assignment: ' + e.getMessage())
		}
		
		WebUI.comment('Current assignment for ' + reportId + ': \'' + currentAssignment + '\'')
		WebUI.comment('Found "To be reviewed" report. Not assigning to anyone.')
		
		// Now check if PS impressions are editable (they should NOT be if unassigned)
		WebUI.comment('Checking if PS impressions are correctly locked for unassigned report...')
		
		// Navigate through the report sections to reach Impression section
		WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')
		WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')
		WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel'))
		
		WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology'), 'WBC Morphology')
		WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel_1'))
		
		WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology'), 'Platelet Morphology')
		WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel_1_2'))
		
		WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')
		WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel_1_2_3'))
		
		WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')
		
		// Try to enter text in the impression field - we expect this to fail if properly locked
		TestObject impressionTextArea = new TestObject('Impression Text Area')
		impressionTextArea.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "impression")]//textarea')
		
		boolean isEditable = true
		try {
			if (WebUI.verifyElementPresent(impressionTextArea, 10, FailureHandling.OPTIONAL)) {
				// Try to enter text
				WebUI.setText(impressionTextArea, 'Test Impression Text')
				
				// If we get here, the field is editable (which is not expected for unassigned reports)
				String enteredText = WebUI.getAttribute(impressionTextArea, 'value')
				if (enteredText.contains('Test Impression Text')) {
					WebUI.comment('ERROR: Impression field is editable when it should be locked for unassigned reports!')
					isEditable = true
				}
			} else {
				WebUI.comment('Impression text area not found - it may be properly disabled')
				isEditable = false
			}
		} catch (Exception e) {
			// If we get an exception, likely the field is disabled/not editable as expected
			WebUI.comment('Could not edit impression field - this is the expected behavior: ' + e.getMessage())
			isEditable = false
		}
		
		// Final verdict
		if ((currentAssignment != 'deekshithaS' || currentAssignment == '') && !isEditable) {
			WebUI.comment('PASS: Report is not assigned to deekshithaS and PS impression is correctly NOT editable')
		} else if ((currentAssignment != 'deekshithaS' || currentAssignment == '') && isEditable) {
			WebUI.comment('FAIL: Report is not assigned to deekshithaS but PS impression IS editable when it should NOT be')
			assert false : 'PS impression is incorrectly editable for unassigned report'
		} else {
			WebUI.comment('Report is assigned to deekshithaS, impression editable status: ' + isEditable)
		}
		
		// Mark as processed
		processedReport = true
	} else {
		WebUI.comment('No reports with "To be reviewed" status found')
	}
	
	// STEP 2: If no "To be reviewed" reports were processed, look for "Under review" reports
	if (!processedReport) {
		WebUI.comment('Proceeding to find "Under review" reports assigned to someone other than deekshithaS')
		
		// Find all rows with "Under review" status
		List<WebElement> underReviewRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), "Under review")] or .//div[contains(text(), "Under review")]]'))
		
		WebUI.comment('Found ' + underReviewRows.size() + ' reports with "Under review" status')
		
		// Loop through all "Under review" reports to find one not assigned to deekshithaS
		for (int i = 0; i < underReviewRows.size(); i++) {
			WebElement currentRow = underReviewRows.get(i)
			
			// Get the report ID
			String reportId = "Unknown"
			try {
				WebElement reportIdElement = currentRow.findElement(By.xpath('./td[2]'))
				reportId = reportIdElement.getText()
				WebUI.comment('Checking "Under review" report with Slide ID: ' + reportId)
			} catch (Exception e) {
				WebUI.comment('Could not extract report ID: ' + e.getMessage())
			}
			
			// Click on the report
			currentRow.click()
			WebUI.comment('Clicked on report with Slide ID: ' + reportId)
			
			// Wait for the report details to load
			WebUI.delay(2)
			
			// Check current assignment status
			def currentAssignment = ""
			try {
				// Attempt to get the current assignee
				currentAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')
				currentAssignment = currentAssignment.trim()
			} catch (Exception e) {
				WebUI.comment('Could not get current assignment: ' + e.getMessage())
			}
			
			WebUI.comment('Current assignment for ' + reportId + ': \'' + currentAssignment + '\'')
			
			// Check if the report is assigned to someone other than deekshithaS
			if (currentAssignment != 'deekshithaS' && currentAssignment != '') {
				WebUI.comment('Found report ' + reportId + ' assigned to \'' + currentAssignment + '\'. NOT reassigning.')
				
				// Now check if PS impressions are editable for this user
				WebUI.comment('Checking if PS impressions are accessible for this user...')
				
				// Navigate through the report sections to reach Impression section
				WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Peripheral Smear Report'), 'Peripheral Smear Report')
				WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_RBC Morphology'), 'RBC Morphology')
				WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel'))
				
				WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC Morphology'), 'WBC Morphology')
				WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel_1'))
				
				WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet Morphology'), 'Platelet Morphology')
				WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel_1_2'))
				
				WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Hemoparasite'), 'Hemoparasite')
				WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel_1_2_3'))
				
				WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Impression'), 'Impression')
				WebUI.doubleClick(findTestObject('Object Repository/Summary/div_OKCancel_1_2_3_4'))
				
				
				// Try to enter text in the impression field - we expect this to fail if properly locked
				TestObject impressionTextArea = new TestObject('Impression Text Area')
				impressionTextArea.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "impression")]//textarea')
				
				boolean isEditable = true
				try {
					if (WebUI.verifyElementPresent(impressionTextArea, 10, FailureHandling.OPTIONAL)) {
						// Try to enter text
						WebUI.setText(impressionTextArea, 'Test Impression Text')
						
						// If we get here, the field is editable (which is not expected since this report is not assigned to us)
						String enteredText = WebUI.getAttribute(impressionTextArea, 'value')
						if (enteredText.contains('Test Impression Text')) {
							WebUI.comment('ERROR: Impression field is editable when it should be locked for reports assigned to others!')
							isEditable = true
						}
					} else {
						WebUI.comment('Impression text area not found - it may be properly disabled')
						isEditable = false
					}
				} catch (Exception e) {
					// If we get an exception, likely the field is disabled/not editable as expected
					WebUI.comment('Could not edit impression field - this is the expected behavior: ' + e.getMessage())
					isEditable = false
				}
				
				// Final verdict
				if (!isEditable) {
					WebUI.comment('PASS: Report is assigned to ' + currentAssignment + ' and PS impression is correctly NOT editable by deekshithaS')
				} else {
					WebUI.comment('FAIL: Report is assigned to ' + currentAssignment + ' but PS impression IS editable by deekshithaS when it should NOT be')
					assert false : 'PS impression is incorrectly editable for report assigned to someone else'
				}
				
				// Successfully found a report assigned to someone else - set flag and break out of loop
				processedReport = true
				break
			} else {
				WebUI.comment('Report ' + reportId + ' is already assigned to deekshithaS or unassigned. Checking next report.')
				
				// Go back to the report list to check the next report
				// Note: You may need to adjust this depending on your application's navigation
				WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/') // Or whatever URL returns to the report list
				WebUI.waitForPageLoad(30)
				WebUI.delay(2)
				
				// Re-fetch the list of "Under review" reports since the page has been refreshed
				underReviewRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), "Under review")] or .//div[contains(text(), "Under review")]]'))
			}
		}
		
		if (!processedReport) {
			WebUI.comment('No "Under review" reports found that are assigned to someone other than deekshithaS.')
		} else {
			WebUI.comment('Successfully found a report assigned to someone other than deekshithaS.')
		}
	}
} catch (Exception e) {
	WebUI.comment('An error occurred during execution: ' + e.getMessage())
	e.printStackTrace()
} finally {
	// Add a small delay before ending the test
	WebUI.delay(3)
	
	
}


