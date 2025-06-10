
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

// Variable to track if we found and processed a "To be reviewed" report
boolean processedToBeReviewed = false

try {
	// First try to find reports with "To be reviewed" status with scrolling
	WebUI.comment('Looking for reports with "To be reviewed" status...')
	
	// Variables for scrolling logic
	List<WebElement> toBeReviewedRows = []
	int scrollAttempts = 0
	int maxScrollAttempts = 15
	boolean foundToBeReviewed = false
	
	// Find the infinite scroll container based on the image structure
	WebElement scrollContainer = null
	try {
		// Try to find the infinite scroll component container
		scrollContainer = driver.findElement(By.xpath("//div[contains(@class, 'infinite-scroll-component')]"))
		WebUI.comment('Found infinite scroll container')
	} catch (Exception e) {
		// Fallback to table container or other possible containers
		try {
			scrollContainer = driver.findElement(By.xpath("//div[contains(@class, 'MuiTableContainer-root')]"))
			WebUI.comment('Found MUI table container')
		} catch (Exception e2) {
			try {
				scrollContainer = driver.findElement(By.xpath("//div[@id='reportListingTable']"))
				WebUI.comment('Found report listing table container')
			} catch (Exception e3) {
				WebUI.comment('Could not find specific scroll container, using page scrolling')
			}
		}
	}
	
	// Scroll through the table to find all "To be reviewed" reports
	while (scrollAttempts < maxScrollAttempts && !foundToBeReviewed) {
		// Find all rows with "To be reviewed" status in current view
		List<WebElement> currentToBeReviewedRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), "To be reviewed")] or .//div[contains(text(), "To be reviewed")]]'))
		
		if (currentToBeReviewedRows.size() > 0) {
			toBeReviewedRows.addAll(currentToBeReviewedRows)
			foundToBeReviewed = true
			WebUI.comment('Found "To be reviewed" reports on scroll attempt ' + (scrollAttempts + 1))
			break
		}
		
		// Scroll within the infinite scroll container if found
		if (scrollContainer != null) {
			// Get current scroll position
			Long currentScrollTop = (Long) js.executeScript("return arguments[0].scrollTop;", scrollContainer)
			
			// Scroll down within the container
			js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 500;", scrollContainer)
			WebUI.delay(2) // Wait for new content to load
			
			// Check if we've reached the bottom (no more scrolling possible)
			Long newScrollTop = (Long) js.executeScript("return arguments[0].scrollTop;", scrollContainer)
			if (newScrollTop.equals(currentScrollTop)) {
				WebUI.comment('Reached bottom of infinite scroll container')
				break
			}
		} else {
			// Fallback to page scrolling
			js.executeScript("window.scrollTo(0, document.body.scrollHeight);")
			WebUI.delay(2)
		}
		
		scrollAttempts++
		WebUI.comment('Scroll attempt ' + scrollAttempts + ' completed, checking for "To be reviewed" reports...')
	}
	
	// After scrolling, do a final comprehensive search
	toBeReviewedRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), "To be reviewed")] or .//div[contains(text(), "To be reviewed")]]'))
	
	// Check if any "To be reviewed" reports were found
	if (toBeReviewedRows.size() > 0) {
		WebUI.comment('Found ' + toBeReviewedRows.size() + ' reports with "To be reviewed" status - these reports are not assigned to any reviewer')
		
		// Get the first "To be reviewed" report
		WebElement firstToBeReviewedRow = toBeReviewedRows.get(0)
		
		// Scroll the specific report into view
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", firstToBeReviewedRow)
		WebUI.delay(1)
		
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
		WebUI.comment('Clicked on report with Slide ID: ' + reportId)
		
		// Wait for the report details to load
		WebUI.delay(2)
		
		// Directly assign to deekshithaS using more reliable methods
		WebUI.comment('Assigning report ' + reportId + ' to deekshithaS')
		
		// Click on the assigned_to field to open the dropdown
		WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
		WebUI.delay(1) // Give the dropdown time to appear
		
		// Create a dynamic XPath to find the deekshithaS option in the dropdown
		TestObject deekshithaOption = new TestObject('deekshithaS Option')
		deekshithaOption.addProperty("xpath", ConditionType.EQUALS, "//li[text()='deekshithaS' or contains(text(),'deekshithaS')]")
		
		// Make sure the dropdown option is visible before clicking
		WebUI.waitForElementVisible(deekshithaOption, 10)
		WebUI.click(deekshithaOption)
		
		// Alternative approach if the above doesn't work
		if (!WebUI.verifyElementAttributeValue(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'),
			'value', 'deekshithaS', 5, FailureHandling.OPTIONAL)) {
			
			WebUI.comment('First method failed, trying alternative approach')
			// Clear the input field and type the name directly
			WebUI.clearText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
			WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'deekshithaS')
			WebUI.delay(1)
			
			// Press Tab to select the option and move focus away
			WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), Keys.chord(Keys.TAB))
		}
		
		// Verify assignment was successful
		WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 10)
		def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')
		newAssignment = newAssignment.trim()
		WebUI.comment('New assignment for ' + reportId + ' (after trim): \'' + newAssignment + '\'')
		
		processedToBeReviewed = true
	} else {
		WebUI.comment('No reports with "To be reviewed" status found after scrolling through ' + scrollAttempts + ' attempts')
		WebUI.comment('Confirmed: No "To be reviewed" reports are present on the page')
	}
	
	// If no "To be reviewed" reports were processed, check the 3rd report
	if (!processedToBeReviewed) {
		WebUI.comment('No "To be reviewed" reports found. Proceeding to check the 3rd report')
		
		// Scroll back to top of the container to ensure we can see all reports
		if (scrollContainer != null) {
			js.executeScript("arguments[0].scrollTop = 0;", scrollContainer)
		} else {
			js.executeScript("window.scrollTo(0, 0);")
		}
		WebUI.delay(2)
		
		// Find all rows in the table
		List<WebElement> allRows = driver.findElements(By.xpath('//tr[./td]'))
		
		// Check if we have at least 3 rows
		if (allRows.size() >= 3) {
			// Get the 3rd row (index 2 since we start counting from 0)
			WebElement thirdRow = allRows.get(2)
			
			// Scroll the 3rd report into view
			js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", thirdRow)
			WebUI.delay(1)
			
			// Get the report ID from the 3rd row
			String reportId = "Unknown"
			try {
				WebElement reportIdElement = thirdRow.findElement(By.xpath('./td[2]'))
				reportId = reportIdElement.getText()
				WebUI.comment('Selected 3rd report with Slide ID: ' + reportId)
			} catch (Exception e) {
				WebUI.comment('Could not extract report ID: ' + e.getMessage())
			}
			
			// Click on the 3rd report
			thirdRow.click()
			WebUI.comment('Clicked on 3rd report with Slide ID: ' + reportId)
			
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
			
			// Handle assignment based on current status
			if (currentAssignment == 'deekshithaS') {
				// Case 1: Report is already assigned to deekshithaS
				WebUI.comment('Report ' + reportId + ' is already assigned to deekshithaS. No action needed.')
			} else {
				// Case 2: Report is unassigned or assigned to someone else
				WebUI.comment('Report ' + reportId + ' is assigned to \'' + currentAssignment + '\'. Reassigning to deekshithaS.')
				
				// Clear the current assignment and set to deekshithaS (following reference code)
				WebUI.doubleClick(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
				WebUI.clearText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
				WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'deekshithaS')
				
				// Create and use dynamic TestObject for deekshithaS in dropdown to match the reference code
				TestObject deekshithaOption = new TestObject('deekshithaS Option')
				deekshithaOption.addProperty("xpath", ConditionType.EQUALS, "//li[text()='deekshithaS' or contains(text(),'deekshithaS')]")
				WebUI.waitForElementVisible(deekshithaOption, 10)
				WebUI.click(deekshithaOption)
				
				// Handle reassignment confirmation if needed
				try {
					// Create a dynamic TestObject for the re-assign button based on reference code
					TestObject reassignButton = new TestObject('Reassign Button')
					reassignButton.addProperty('xpath', ConditionType.EQUALS, "//div[@id='reassign-dialog']//button[text()='Re-assign']")
					
					// Check if reassign button exists and click it (for when reports already assigned to someone else)
					if (WebUI.waitForElementPresent(reassignButton, 5, FailureHandling.OPTIONAL)) {
						WebUI.comment('Clicking Re-assign button to confirm reassignment')
						WebUI.click(reassignButton)
					}
				} catch (Exception e) {
					WebUI.comment('Reassignment confirmation dialog not found or not needed: ' + e.getMessage())
				}
				
				// Verify assignment was successful
				WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 10)
				def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')
				newAssignment = newAssignment.trim()
				WebUI.comment('New assignment for ' + reportId + ' (after trim): \'' + newAssignment + '\'')
			}
		} else {
			WebUI.comment('Not enough reports found in the table to select the 3rd one.')
		}
	}
} catch (Exception e) {
	WebUI.comment('An error occurred during execution: ' + e.getMessage())
	e.printStackTrace()
} finally {
	// Add a small delay before ending the test
	WebUI.delay(3)
	
}


println("Verify and click on RBC")

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_RBC'),'RBC')
WebUI.click(findTestObject('Object Repository/single_click/button_RBC'))
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Size'), 'Size')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Cell name'), 'Cell name')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Grade'), 'Grade')

println("===========moving to RBC Size==========")

WebUI.verifyElementText(findTestObject('Object Repository/Summary/microcyte_row'),'Microcytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/microcyte_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/microcyte_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/macrocyte_row'),'Macrocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/macrocyte_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/macrocyte_grade3'))


WebUI.verifyElementText(findTestObject('Object Repository/Summary/anisocytosis_row'),'Anisocytosis')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/anisocytosis_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/anisocytosis_grade3'))

println("===========moving to RBC Shape==========")
WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Shape'), 'Shape')
WebUI.click(findTestObject('Object Repository/Summary/button_Shape'))
	
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Ovalocytes_row'),'Ovalocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Ovalocytes_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Ovalocytes_grade3'))
	
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Elliptocytes_row'),'Elliptocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Elliptocytes_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Elliptocytes_grade3'))
//
//
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Teardrop Cells_row'),'Teardrop Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Teardrop Cells_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Teardrop Cells_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Fragmented_Cells_row'),'Fragmented Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Fragmented Cells_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Fragmented Cells_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Target_Cells_row'),'Target Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Target_Cells_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Target_cells_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Echinocytes_cells_row'),'Echinocytes')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Echinocytes_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Echinocytes_grade3'))

WebUI.click(findTestObject('Object Repository/Summary/Acanthocytes_grade_03'))
WebUI.click(findTestObject('Object Repository/Summary/Sickle Cells_grade_03'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Poikilocytosis_cells_row'),'Poikilocytosis')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Poikilocytosis_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Poikilocytosis_grade3'))

println("===========moving to RBC Colour==========")


WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Color'),'Colour')
WebUI.click(findTestObject('Object Repository/Summary/button_Color'))



WebUI.verifyElementText(findTestObject('Object Repository/Summary/Hypochromic_row'),'Hypochromic Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Hypochromic Cells_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Hypochromic_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Polychromatic_row'),'Polychromatic Cells')
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Polychromatic_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Polychromatic_grade3'))

println("===========moving to RBC Inclusions==========")

WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Inclusions'),'Inclusions')
WebUI.click(findTestObject('Object Repository/Summary/button_Inclusions'))


WebUI.verifyElementText(findTestObject('Object Repository/Summary/howell_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Howell-Jolly Bodies_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/pappenheimer_grade_Row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Pappenheimer Bodies_grade3'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Basophilic_grade_row'),'')
WebUI.click(findTestObject('Object Repository/Summary/Basophilic Stippling_grade3'))


// Now navigate to Summary tab and verify presence of cells based on significance
println("\n==== Navigating to Summary Tab ====")
WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))
WebUI.delay(5) // Increase the delay to give more time for the summary tab to load

println("==========Checking for RBC Size===========")

// Check if Microcyte element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_microcyte'), 10, FailureHandling.OPTIONAL)) {
		println("Microcyte - Present in Summary")
	} else {
		println("Microcyte - Not Present in Summary")
	}
} catch (Exception e) {
	println("Microcyte - Error occurred: " + e.getMessage())
}

// Check if Macrocyte element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_macrocyte'), 10, FailureHandling.OPTIONAL)) {
		println("Macrocyte - Present in Summary")
	} else {
		println("Macrocyte - Not Present in Summary")
	}
} catch (Exception e) {
	println("Macrocyte - Error occurred: " + e.getMessage())
}

// Check if Anisocytosis element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/016_sum_anisocytosis'), 10, FailureHandling.OPTIONAL)) {
		println("Anisocytosis - Present in Summary")
	} else {
		println("Anisocytosis - Not Present in Summary")
	}
} catch (Exception e) {
	println("Anisocytosis - Error occurred: " + e.getMessage())
}

println("==========Checking for RBC Shape===========")

// Check if Ovalocytes element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Ovalocytes'), 10, FailureHandling.OPTIONAL)) {
		println("Ovalocytes - Present in Summary")
	} else {
		println("Ovalocytes - Not Present in Summary")
	}
} catch (Exception e) {
	println("Ovalocytes - Error occurred: " + e.getMessage())
}

// Check if Elliptocytes element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Elliptocytes'), 10, FailureHandling.OPTIONAL)) {
		println("Elliptocytes - Present in Summary")
	} else {
		println("Elliptocytes - Not Present in Summary")
	}
} catch (Exception e) {
	println("Elliptocytes - Error occurred: " + e.getMessage())
}

// Check if Teardrop Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Teardrop_Cells'), 10, FailureHandling.OPTIONAL)) {
		println("Teardrop Cells - Present in Summary")
	} else {
		println("Teardrop Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Teardrop Cells - Error occurred: " + e.getMessage())
}

// Check if Fragmented Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Fragmented Cells'), 10, FailureHandling.OPTIONAL)) {
		println("Fragmented Cells - Present in Summary")
	} else {
		println("Fragmented Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Fragmented Cells - Error occurred: " + e.getMessage())
}

// Check if Target Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Target Cells'), 10, FailureHandling.OPTIONAL)) {
		println("Target Cells - Present in Summary")
	} else {
		println("Target Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Target Cells - Error occurred: " + e.getMessage())
}

// Check if Echinocytes element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Echinocytes'), 10, FailureHandling.OPTIONAL)) {
		println("Echinocytes - Present in Summary")
	} else {
		println("Echinocytes - Not Present in Summary")
	}
} catch (Exception e) {
	println("Echinocytes - Error occurred: " + e.getMessage())
}

// Check if Acanthocytes element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_acanthocytes'), 10, FailureHandling.OPTIONAL)) {
		println("Acanthocytes - Present in Summary")
	} else {
		println("Acanthocytes - Not Present in Summary")
	}
} catch (Exception e) {
	println("Acanthocytes - Error occurred: " + e.getMessage())
}

// Check if Sickle Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Sickle_cells'), 10, FailureHandling.OPTIONAL)) {
		println("Sickle Cells - Present in Summary")
	} else {
		println("Sickle Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Sickle Cells - Error occurred: " + e.getMessage())
}

// Check if Poikilocytosis element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/018_sum_Poikilocytosis'), 10, FailureHandling.OPTIONAL)) {
		println("Poikilocytosis - Present in Summary")
	} else {
		println("Poikilocytosis - Not Present in Summary")
	}
} catch (Exception e) {
	println("Poikilocytosis - Error occurred: " + e.getMessage())
}

println("==========Checking for RBC Colour===========")

// Check if Hypochromic Cells element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/20_sum_Hypochromic Cells'), 10, FailureHandling.OPTIONAL)) {
		println("Hypochromic Cells - Present in Summary")
	} else {
		println("Hypochromic Cells - Not Present in Summary")
	}
} catch (Exception e) {
	println("Hypochromic Cells - Error occurred: " + e.getMessage())
}

// Check if Polychromatic element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/20_sum_Polychromatic'), 10, FailureHandling.OPTIONAL)) {
		println("Polychromatic - Present in Summary")
	} else {
		println("Polychromatic - Not Present in Summary")
	}
} catch (Exception e) {
	println("Polychromatic - Error occurred: " + e.getMessage())
}

println("==========Checking for RBC Inclusions===========")

// Check if Howell-Jolly Bodies element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/33_sum_howell_jolly_bodies'), 10, FailureHandling.OPTIONAL)) {
		println("Howell-Jolly Bodies - Present in Summary")
	} else {
		println("Howell-Jolly Bodies - Not Present in Summary")
	}
} catch (Exception e) {
	println("Howell-Jolly Bodies - Error occurred: " + e.getMessage())
}

// Check if Pappenheimer Bodies element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/33_sum_Pappenheimer Bodies'), 10, FailureHandling.OPTIONAL)) {
		println("Pappenheimer Bodies - Present in Summary")
	} else {
		println("Pappenheimer Bodies - Not Present in Summary")
	}
} catch (Exception e) {
	println("Pappenheimer Bodies - Error occurred: " + e.getMessage())
}

// Check if Basophilic Stippling element is present
try {
	if (WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/33_sum_Basophilic Stippling'), 10, FailureHandling.OPTIONAL)) {
		println("Basophilic Stippling - Present in Summary")
	} else {
		println("Basophilic Stippling - Not Present in Summary")
	}
} catch (Exception e) {
	println("Basophilic Stippling - Error occurred: " + e.getMessage())
}

println("======================End of the Test case======================")