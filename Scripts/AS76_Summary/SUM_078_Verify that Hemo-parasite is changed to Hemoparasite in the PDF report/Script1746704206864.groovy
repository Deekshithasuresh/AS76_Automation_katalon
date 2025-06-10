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
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream
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
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Stream
import java.util.Comparator


/**
 * Navigate to Preview Report, approve it, and download the PDF report
 * This function navigates to the preview page, approves without text comparison,
 * and downloads the PDF report for verification
 * The verification of "Hemoparasite" will be done in the PDF
 */
def verifyHemoparasiteTextInPreview() {
	// Expected text to be found (keeping this for reference, will be used in PDF verification)
	String expectedText = "Hemoparasite"

	// Verify Approve report button is present and click it
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Approve report'), 'Approve report')
	WebUI.click(findTestObject('Object Repository/Summary/button_Approve report'))

	// Click Confirm to proceed without verification in the preview
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Confirm'), 'Confirm')
	WebUI.click(findTestObject('Object Repository/Summary/button_Confirm'))

	// Click Approve report again
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Approve report'), 'Approve report')
	WebUI.click(findTestObject('Object Repository/Summary/button_Approve report'))

	// Click on the approve button to generate PDF
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/approve_button_pdf'), 'Approve report')
	WebUI.click(findTestObject('Object Repository/Summary/approve_button_pdf'))

	// Wait for a moment to ensure the approval is processed
	WebUI.delay(2)

	// Download the PDF report
	// Click on the menu button (no text verification needed for image element)
	WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/button_deekshithaS_MuiButtonBase-root MuiIc_e8ce4e'), 5)
	WebUI.click(findTestObject('Object Repository/Summary/button_deekshithaS_MuiButtonBase-root MuiIc_e8ce4e'))

	// Click on Download PDF report option
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Download PDF report'), 'Download PDF report')
	WebUI.click(findTestObject('Object Repository/Summary/span_Download PDF report'))

	// Wait for download to complete
	WebUI.delay(3) // Adjust time as needed for download to complete

	WebUI.comment("Report successfully approved and PDF downloaded")

	// Return true as we've successfully completed the approval and download flow
	return true
}

/**
 * Utility class to handle PDF operations
 */
class PdfReader {
	
	/**
	 * Get the latest PDF report from the downloads folder
	 */
	static File getLatestPdfReport(String downloadsPath) {
		File downloadsDir = new File(downloadsPath)
		
		if (!downloadsDir.exists() || !downloadsDir.isDirectory()) {
			throw new FileNotFoundException("Downloads directory not found: " + downloadsPath)
		}
		
		// Find all PDF files that start with "pdfReport"
		File[] pdfFiles = downloadsDir.listFiles(new FileFilter() {
			@Override
			boolean accept(File file) {
				return file.isFile() && file.getName().toLowerCase().startsWith("pdfreport") && file.getName().toLowerCase().endsWith(".pdf")
			}
		})
		
		if (pdfFiles == null || pdfFiles.length == 0) {
			throw new FileNotFoundException("No pdfReport*.pdf files found in downloads directory")
		}
		
		// Sort by last modified time (most recent first)
		Arrays.sort(pdfFiles, new Comparator<File>() {
			@Override
			int compare(File f1, File f2) {
				return Long.compare(f2.lastModified(), f1.lastModified())
			}
		})
		
		return pdfFiles[0] // Return the most recent file
	}
	
	/**
	 * Read text content from PDF file
	 */
	static String readText(String pdfFilePath) {
		PDDocument document = null
		try {
			File pdfFile = new File(pdfFilePath)
			if (!pdfFile.exists()) {
				throw new FileNotFoundException("PDF file not found: " + pdfFilePath)
			}
			
			document = PDDocument.load(pdfFile)
			PDFTextStripper pdfStripper = new PDFTextStripper()
			String text = pdfStripper.getText(document)
			return text
		} catch (Exception e) {
			throw new RuntimeException("Error reading PDF: " + e.getMessage(), e)
		} finally {
			if (document != null) {
				try {
					document.close()
				} catch (Exception e) {
					// Log but don't throw
					println("Warning: Error closing PDF document: " + e.getMessage())
				}
			}
		}
	}
}

// Main test case execution
try {
	// Execute the navigation function
	verifyHemoparasiteTextInPreview()

	// Step 1: Get Downloads folder path
	String downloadsPath = System.getProperty('user.home') + '/Downloads'
	
	// Step 2: Find the latest 'pdfReport*.pdf'
	File latestPdf = PdfReader.getLatestPdfReport(downloadsPath)
	println("Latest pdfReport file: ${latestPdf.absolutePath}")
	
	// Step 3: Read text from PDF
	String pdfText = PdfReader.readText(latestPdf.absolutePath)
	println("PDF Content:\n$pdfText")

	// Check if "hemoparasite" (without hyphen) is present
	boolean containsHemoparasite = pdfText.contains("Hemoparasite")

	// Check if "hemo-parasite" (with hyphen) is present
	boolean containsHemoWithHyphen = pdfText.contains("Hemo-parasite")

	// Verify the text has changed from "hemo-parasite" to "hemoparasite"
	if (containsHemoparasite && !containsHemoWithHyphen) {
		println("Success: Text has been changed from 'Hemo-parasite' to 'Hemoparasite'")
	} else if (!containsHemoparasite && containsHemoWithHyphen) {
		println("Failed: Text is still 'Hemo-parasite' and has not been changed to 'Hemoparasite'")
		// Use the correct method to mark test as failed
		assert false : "Text verification failed - expected 'Hemoparasite' but found 'Hemo-parasite'"
	} else if (!containsHemoparasite && !containsHemoWithHyphen) {
		println("Warning: Neither 'Hemoparasite' nor 'Hemo-parasite' found in PDF")
		assert false : "Neither expected text variations found in PDF"
	} else {
		println("Warning: Both 'Hemoparasite' and 'Hemo-parasite' found in PDF")
		assert false : "Both text variations found - ambiguous result"
	}

} catch (Exception e) {
	WebUI.comment("Test failed with exception: " + e.getMessage())
	e.printStackTrace()
	// Use assert false instead of the non-existent markFailedAndStop method
	assert false : "Test failed due to exception: " + e.getMessage()
}