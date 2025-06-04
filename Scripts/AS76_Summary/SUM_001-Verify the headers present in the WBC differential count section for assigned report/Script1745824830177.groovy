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

/**
 * Test Case: PBS Report Assignment
 * This test verifies the slide assignment functionality of the PBS system
 */
// Login to the PBS system
WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for the dashboard to load
WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0015'), 20)

// Get the report ID before clicking
def reportId = WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0015'))

println('Selecting report with ID: ' + reportId)

// Click on a report (can be any report, not necessarily SIG0015)
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0015'))

// Check current assignment status
def currentAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')

currentAssignment = currentAssignment.trim( // Trim any whitespace
    )

println(((('Current assignment for ' + reportId) + ': \'') + currentAssignment) + '\'')

// Handle different assignment scenarios
if (currentAssignment == '') {
    // Case 1: Report is unassigned
    println(('Report ' + reportId) + ' is currently unassigned. Assigning to deekshithaS.')

    WebUI.doubleClick(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'))

    WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'deekshithaS')

    WebUI.click(findTestObject('Object Repository/Summary/li_deekshithaS (2)') // Case 2: Report is already assigned to deekshithaS
        ) // Case 3: Report is assigned to someone else
    // Handle the confirmation dialog
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
WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 10)

def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/Page_PBS (1)/input_SIG0015_assigned_to'), 'value')

newAssignment = newAssignment.trim( // Trim any whitespace
    )

println(((('New assignment for ' + reportId) + ' (after trim): \'') + newAssignment) + '\'')

// Compare trimmed values
WebUI.verifyEqual(newAssignment, 'deekshithaS')

println(('Successfully verified that report ' + reportId) + ' is now assigned to deekshithaS')

WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/span_WBC differential count ()'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Cell name'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/th_'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/th_WBC'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Non-WBC'), 0)

