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
import com.kms.katalon.core.testobject.ConditionType

WebUI.openBrowser('')
WebUI.maximizeWindow()
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/input_username_loginId (1)'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/input_password_loginPassword (1)'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/input_password_loginPassword (1)'), Keys.chord(Keys.ENTER))
WebUI.waitForPageLoad(30)

// Variables to store results
String reportId = ''
boolean largePlateletsDetected = false
boolean plateletClumpsDetected = false
int largePlateletsCount = 0
int plateletClumpsCount = 0
String largePlateletsStatus = ''
String plateletClumpsStatus = ''
boolean testPassed = true

// Select the first report in the list
TestObject firstRowObject = new TestObject('First Row')
firstRowObject.addProperty('xpath', ConditionType.EQUALS, '//tbody/tr[3]')

// Wait for the reports table to load and verify first row exists
if (WebUI.waitForElementPresent(firstRowObject, 10, FailureHandling.OPTIONAL)) {
	// Get report ID from the first column before clicking on the row
	TestObject reportIdObject = new TestObject('Report ID')
	reportIdObject.addProperty('xpath', ConditionType.EQUALS, '//tbody/tr[1]/td[3]')

	if (WebUI.waitForElementPresent(reportIdObject, 5, FailureHandling.OPTIONAL)) {
		reportId = WebUI.getText(reportIdObject)
		println('Checking Report ID: ' + reportId)
	} else {
		reportId = 'Unknown'
		println('Could not retrieve Report ID for the first row')
	}
	
	// Click on the first row to open the slide details
	WebUI.waitForElementVisible(firstRowObject, 20)
	WebUI.click(firstRowObject)
	WebUI.delay(3)

	// Check current assignment status
	def currentAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to'), 'value')
	currentAssignment = currentAssignment.trim() // Trim any whitespace
	println(((('Current assignment for ' + reportId) + ': \'') + currentAssignment) + '\'')

	// Handle different assignment scenarios
	if (currentAssignment == '') {
		// Case 1: Report is unassigned
		println(('Report ' + reportId) + ' is currently unassigned. Assigning to deekshithaS.')
		WebUI.doubleClick(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to'))
		WebUI.setText(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to'), 'deekshithaS')
		WebUI.click(findTestObject('Object Repository/Summary/li_deekshithaS'))
	} else if (currentAssignment == 'deekshithaS') {
		println(('Report ' + reportId) + ' is already assigned to deekshithaS.')
	} else {
		println(((('Report ' + reportId) + ' is assigned to \'') + currentAssignment) + '\'. Reassigning to deekshithaS.')
		WebUI.doubleClick(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))
		WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'deekshithaS')
		WebUI.click(findTestObject('Object Repository/Summary/li_deekshithaS (2)'))
		WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Re-assign_1'), 10)
		WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Re-assign_1'))
	}
	
	// Verify assignment was successful
	WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to'), 10)
	def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to'), 'value')
	newAssignment = newAssignment.trim() // Trim any whitespace
	println(((('New assignment for ' + reportId) + ' (after trim): \'') + newAssignment) + '\'')

	// Compare trimmed values
	WebUI.verifyEqual(newAssignment, 'deekshithaS')
	println(('Successfully verified that report ' + reportId) + ' is now assigned to deekshithaS')

	// Navigate to Platelets tab
	TestObject plateletsTabObject = new TestObject('Platelets Tab')
	plateletsTabObject.addProperty('xpath', ConditionType.EQUALS, '//span[text()="Platelets"]')
	WebUI.waitForElementVisible(plateletsTabObject, 20)
	WebUI.click(plateletsTabObject)

	// Navigate to Morphology tab within Platelets section
	TestObject morphologyTabObject = new TestObject('Morphology Tab')
	morphologyTabObject.addProperty('xpath', ConditionType.EQUALS, '//button[text()="Morphology"]')
	WebUI.waitForElementVisible(morphologyTabObject, 20)
	WebUI.click(morphologyTabObject)
	WebUI.delay(2)

	/* Define test objects for Large Platelets elements */
	// Object for the Large Platelets label
	TestObject largePlateletsNameObject = new TestObject('Large Platelets Name')
	largePlateletsNameObject.addProperty('xpath', ConditionType.EQUALS, '//*/text()[normalize-space(.)="Large Platelets"]/parent::*')

	// Object for the Large Platelets count value
	TestObject largePlateletsCountObject = new TestObject('Large Platelets Count')
	largePlateletsCountObject.addProperty('xpath', ConditionType.EQUALS, '//*[@id="root"]/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div[2]')

	// Object for the Large Platelets detection status
	TestObject largePlateletsStatusObject = new TestObject('Large Platelets Status')
	largePlateletsStatusObject.addProperty('xpath', ConditionType.EQUALS, '//div[text()="Large Platelets"]/parent::div/div[3]')

	/* Define test objects for Platelet Clumps elements */
	// Object for the Platelet Clumps label
	TestObject plateletClumpsNameObject = new TestObject('Platelet Clumps Name')
	plateletClumpsNameObject.addProperty('xpath', ConditionType.EQUALS, '//*[@id="root"]/div/div[2]/div[2]/div/div/div/div[2]/div[3]/div[1]')

	// Object for the Platelet Clumps count value
	TestObject plateletClumpsCountObject = new TestObject('Platelet Clumps Count')
	plateletClumpsCountObject.addProperty('xpath', ConditionType.EQUALS, '//*[@id="root"]/div/div[2]/div[2]/div/div/div/div[2]/div[3]/div[2]')

	// Object for the Platelet Clumps detection status
	TestObject plateletClumpsStatusObject = new TestObject('Platelet Clumps Status')
	plateletClumpsStatusObject.addProperty('xpath', ConditionType.EQUALS, '//*[@id="root"]/div/div[2]/div[2]/div/div/div/div[2]/div[3]/div[3]')

	/* Define alternative XPath selectors in case primary selectors don't work */
	// Alternative object for Large Platelets count
	TestObject altLargePlateletsCountObject = new TestObject('Alt Large Platelets Count')
	altLargePlateletsCountObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(text(),"Large Platelets")]/following-sibling::div[1]')

	// Alternative object for Platelet Clumps count
	TestObject altPlateletClumpsCountObject = new TestObject('Alt Platelet Clumps Count')
	altPlateletClumpsCountObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(text(),"Platelet Clumps")]/following-sibling::div[1]')

	// Try to find elements with reasonable timeout
	boolean foundLargePlatelets = WebUI.waitForElementVisible(largePlateletsNameObject, 5, FailureHandling.OPTIONAL)
	boolean foundPlateletClumps = WebUI.waitForElementVisible(plateletClumpsNameObject, 5, FailureHandling.OPTIONAL)

	/* Check Large Platelets */
	if (foundLargePlatelets) {
		// Determine which count object to use (primary or alternative)
		TestObject currentLargePlateletsCountObject = WebUI.waitForElementVisible(largePlateletsCountObject, 5, FailureHandling.OPTIONAL) ?
			largePlateletsCountObject : altLargePlateletsCountObject

		// Process Large Platelets count if the element is found
		if (WebUI.waitForElementVisible(currentLargePlateletsCountObject, 5, FailureHandling.OPTIONAL)) {
			String largePlateletsCountText = WebUI.getText(currentLargePlateletsCountObject)

			try {
				largePlateletsCount = Integer.parseInt(largePlateletsCountText.trim())

				// Check if count is greater than 0
				if (largePlateletsCount > 0) {
					largePlateletsStatus = WebUI.getText(largePlateletsStatusObject).trim()
					largePlateletsDetected = true
					println(((('Large Platelets count is ' + largePlateletsCount) + ' and status is \'') + largePlateletsStatus) + '\'')
					WebUI.verifyMatch(largePlateletsStatus, 'Detected', true)
				} else {
					largePlateletsStatus = WebUI.getText(largePlateletsStatusObject).trim()
					println(('Large Platelets count is 0 and status is \'' + largePlateletsStatus) + '\'')
				}
			}
			catch (NumberFormatException e) {
				println('Could not parse Large Platelets count from: ' + largePlateletsCountText)
			}
		}
	} else {
		println('Large Platelets section not found in this report')
	}
	
	/* Check Platelet Clumps */
	if (foundPlateletClumps) {
		// Determine which count object to use (primary or alternative)
		TestObject currentPlateletClumpsCountObject = WebUI.waitForElementVisible(plateletClumpsCountObject, 5, FailureHandling.OPTIONAL) ?
			plateletClumpsCountObject : altPlateletClumpsCountObject

		// Process Platelet Clumps count if the element is found
		if (WebUI.waitForElementVisible(currentPlateletClumpsCountObject, 5, FailureHandling.OPTIONAL)) {
			String plateletClumpsCountText = WebUI.getText(currentPlateletClumpsCountObject)

			try {
				plateletClumpsCount = Integer.parseInt(plateletClumpsCountText.trim())

				// Check if count is greater than 0
				if (plateletClumpsCount > 0) {
					plateletClumpsStatus = WebUI.getText(plateletClumpsStatusObject).trim()
					plateletClumpsDetected = true
					println(((('Platelet Clumps count is ' + plateletClumpsCount) + ' and status is \'') + plateletClumpsStatus) + '\'')
					WebUI.verifyMatch(plateletClumpsStatus, 'Detected', true)
				} else {
					plateletClumpsStatus = WebUI.getText(plateletClumpsStatusObject).trim()
					println(('Platelet Clumps count is 0 and status is \'' + plateletClumpsStatus) + '\'')
				}
			}
			catch (NumberFormatException e) {
				println('Could not parse Platelet Clumps count from: ' + plateletClumpsCountText)
			}
		}
	} else {
		println('Platelet Clumps section not found in this report')
	}
} else {
	println('No reports available in the table')
}

// Fixed summary section - This is where the error was occurring
println('\n----- SUMMARY FOR REPORT ' + reportId + ' -----')

// Large Platelets summary
println('Large Platelets:')
println('  Count: ' + largePlateletsCount)
println('  Status: ' + largePlateletsStatus)

// Fix the type comparison issue - calculate boolean expression first, then convert to string
boolean largePlateletsMatchesExpected = (largePlateletsCount > 0) ?
	(largePlateletsStatus == 'Detected') : (largePlateletsStatus != 'Detected')
println('  Expected Status Match: ' + largePlateletsMatchesExpected)

// Platelet Clumps summary
println('\nPlatelet Clumps:')
println('  Count: ' + plateletClumpsCount)
println('  Status: ' + plateletClumpsStatus)

// Fix the type comparison issue - calculate boolean expression first, then convert to string
boolean plateletClumpsMatchesExpected = (plateletClumpsCount > 0) ?
	(plateletClumpsStatus == 'Detected') : (plateletClumpsStatus != 'Detected')
println('  Expected Status Match: ' + plateletClumpsMatchesExpected)

// Fix the overall test result logic
boolean overallTestResult = largePlateletsMatchesExpected && plateletClumpsMatchesExpected
println('\nTest Result: ' + (overallTestResult ? 'PASS' : 'FAIL'))

// Keep browser open for manual inspection
println('\nTest complete. Browser left open for further inspection.')