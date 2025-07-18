import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import pdfutils.PdfReader

/**
 * Navigate to Preview Report, approve it, and download the PDF report
 * This function navigates to the preview page, approves without text comparison,
 * and downloads the PDF report for verification
 * The verification of "Hemoparasite" will be done in the PDF
 * 
 */

WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

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