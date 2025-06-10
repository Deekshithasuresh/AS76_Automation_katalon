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
// Open browser and login
WebUI.openBrowser('')
// Navigate to the PBS login page
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
// Enter the username 'deekshithaS' in the login field
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
// Enter the encrypted password in the password field
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
// Press Enter key to submit the login form
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
// Wait for the page to completely load with a timeout of 30 seconds
WebUI.waitForPageLoad(30)
// Add a delay of 3 seconds to ensure the dashboard is fully loaded and visible
WebUI.delay(3)
// Get the WebDriver instance to directly interact with the browser
WebDriver driver = DriverFactory.getWebDriver()
// Wait for the table element to become visible on the page with a timeout of 30 seconds
WebUI.waitForElementVisible(findTestObject('Object Repository/Summary/Page_PBS (1)/table'), 30)
// Start a try-catch block to handle any exceptions that might occur
try {
   // Initialize the variable to store the first row with "To be reviewed" status
   WebElement firstToBeReviewedRow = null
   // Find all table rows containing text "To be reviewed" in a span or div element
   List<WebElement> toBeReviewedRows = driver.findElements(By.xpath('//tr[.//span[contains(text(), \'To be reviewed\')] or .//div[contains(text(), \'To be reviewed\')]]'))
   // Check if any rows with "To be reviewed" text were found
   if (toBeReviewedRows.size() > 0) {
	   // Log the number of "To be reviewed" reports found
	   WebUI.comment(('Found ' + toBeReviewedRows.size()) + ' reports with \'To be reviewed\' status')
	   // Set the first row with "To be reviewed" status for further processing
	   firstToBeReviewedRow = toBeReviewedRows.get(0) // Log that no reports with "To be reviewed" text were found
	   // Try the second approach: find rows with elements having "review" in their class names
	   // Check if any rows with "review" in class names were found
	   // Log the number of reports found by review icon/class
	   // Set the first row found by icon/class for further processing
	   // Log that we're trying the third approach with the status column
	   // Try the third approach: look at status column (4th column) for "review" text
	   // Check if any rows with "review" in status column were found
	   // Log the number of reports found by status column
	   // Set the first row found by status column for further processing
   } else {
	   WebUI.comment('No reports found with \'To be reviewed\' text. Trying to find by icon/class...')
	   List<WebElement> reviewIconRows = driver.findElements(By.xpath('//tr[.//span[contains(@class, \'review\')] or .//i[contains(@class, \'review\')]]'))
	   if (reviewIconRows.size() > 0) {
		   WebUI.comment(('Found ' + reviewIconRows.size()) + ' reports by review icon/class')
		   firstToBeReviewedRow = reviewIconRows.get(0)
	   } else {
		   WebUI.comment('Looking at status column specifically...')
		   List<WebElement> statusReviewRows = driver.findElements(By.xpath('//tr[./td[4][contains(., \'review\') or .//span[contains(., \'review\')]]]'))
		   if (statusReviewRows.size() > 0) {
			   WebUI.comment(('Found ' + statusReviewRows.size()) + ' reports with status column containing \'review\'')
			   firstToBeReviewedRow = statusReviewRows.get(0)
		   }
	   }
   }
  
   // Check if any "To be reviewed" report was found using any of the three approaches
   if (firstToBeReviewedRow != null) {
	   // Initialize a variable to store the slide ID
	   String slideId = 'Unknown'
	   // Try to extract the slide ID from the second column
	   try {
		   // Find the second column cell in the row
		   WebElement slideIdElement = firstToBeReviewedRow.findElement(By.xpath('./td[2]'))
		   // Get the text content of the cell (the slide ID)
		   slideId = slideIdElement.getText()
	   }
	   catch (Exception e) {
	   } // If we can't get the slide ID, just continue without handling the exception
	  
	   // Log which report we're about to click
	   WebUI.comment('Clicking on the first \'To be reviewed\' report with ID: ' + slideId)
	   // Try to click the slide ID cell specifically
	   try {
		   // Find the slide ID cell (second column)
		   WebElement slideIdCell = firstToBeReviewedRow.findElement(By.xpath('./td[2]'))
		   // Click on the slide ID cell to open the report
		   slideIdCell.click()
	   }
	   catch (Exception e) {
		   firstToBeReviewedRow.click()
	   } // If clicking the cell fails, click the entire row instead
	  
	   // Wait 3 seconds for the report details to load
	   WebUI.delay(3)
	   // Create a dynamic test object to locate the platelet count element
	   TestObject plateletCountObject = new TestObject()
	   // Set the XPath property to find any span containing "Platelet count" or "platelet" text
	   plateletCountObject.addProperty('xpath', ConditionType.EQUALS, '//span[contains(text(), \'Platelet count\') or contains(text(), \'platelet\')]')
	   // Wait up to 30 seconds for the platelet count element to be present
	   WebUI.waitForElementPresent(plateletCountObject, 30)
	   // Verify that the platelet count element is present, confirming the report opened correctly
	   WebUI.verifyElementPresent(plateletCountObject, 30) // Log that no "To be reviewed" reports were found using any method
	   // Wait 5 seconds before ending the test
   } else {
	   WebUI.comment('No \'To be reviewed\' reports found using any method. Test cannot continue.')
	   WebUI.delay(5)
   }
}
catch (Exception e) {
   WebUI.comment('An error occurred: ' + e.getMessage())
   e.printStackTrace()
}
// Capture all the text elements from the UI
String actualPlateletLabel = WebUI.getText(findTestObject('Object Repository/Summary/span_Platelet'))
String actualPlateletCountLabel = WebUI.getText(findTestObject('Object Repository/Summary/span_Platelet count (x 109L)'))
String actualPlateletCountValue = WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/div_212'))

// Define the expected text values
String expectedPlateletLabel = 'Platelet'
String expectedPlateletCountLabel = 'Platelet count (x 10^9/L)'

// Dynamic validation - capture the actual value and validate it's not empty/null
String expectedPlateletCountValue = actualPlateletCountValue // Dynamic assignment

// Verify that the actual values match the expected values
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet'), expectedPlateletLabel)
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet count (x 109L)'), expectedPlateletCountLabel)

// Verify that platelet count value exists and is numeric
boolean isPlateletCountValid = false
if (actualPlateletCountValue != null && !actualPlateletCountValue.trim().isEmpty()) {
	try {
		// Check if the value is numeric (can be integer or decimal)
		Double.parseDouble(actualPlateletCountValue.trim())
		isPlateletCountValid = true
	} catch (NumberFormatException e) {
		println "WARNING: Platelet count value '${actualPlateletCountValue}' is not a valid number"
		isPlateletCountValid = false
	}
} else {
	println "ERROR: Platelet count value is null or empty"
	isPlateletCountValid = false
}

// Print the comparison results
println "==== Platelet Count Verification Results ===="
println "Platelet Label: Expected [${expectedPlateletLabel}] - Actual [${actualPlateletLabel}] - Match: ${actualPlateletLabel == expectedPlateletLabel}"
println "Platelet Count Label: Expected [${expectedPlateletCountLabel}] - Actual [${actualPlateletCountLabel}] - Match: ${actualPlateletCountLabel == expectedPlateletCountLabel}"
println "Platelet Count Value: Captured [${actualPlateletCountValue}] - Valid: ${isPlateletCountValid}"
println "=============================================="

// Print a summary statement
if (actualPlateletLabel == expectedPlateletLabel &&
	actualPlateletCountLabel == expectedPlateletCountLabel &&
	isPlateletCountValid) {
	println "PASS: The default presence of Platelet count value is present in the summary"
} else {
	println "FAIL: The default presence of Platelet count value is not present in the summary"
}

// Log additional information about the test
println("Verified default text in summary tab: '${actualPlateletCountLabel}' with value '${actualPlateletCountValue}'")

