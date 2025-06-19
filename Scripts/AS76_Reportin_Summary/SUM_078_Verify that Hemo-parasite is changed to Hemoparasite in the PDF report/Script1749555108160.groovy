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

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')


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