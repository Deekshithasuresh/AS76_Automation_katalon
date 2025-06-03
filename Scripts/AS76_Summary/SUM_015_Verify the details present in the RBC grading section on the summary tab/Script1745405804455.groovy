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

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/input_username_loginId (2)'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/input_password_loginPassword (2)'), 
    'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/input_password_loginPassword (2)'), Keys.chord(
        Keys.ENTER))

// Wait for the dashboard to load
WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/td_SIG0015 (2)'), 20)

// Get the report ID before clicking
def reportId = WebUI.getText(findTestObject('Object Repository/Summary/td_SIG0015 (2)'))

println('Selecting report with ID: ' + reportId)

// Click on a report (can be any report, not necessarily SIG0015)
WebUI.click(findTestObject('Object Repository/Summary/td_SIG0015 (2)'))

// Check current assignment status
def currentAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to (1)'), 
    'value')

currentAssignment = currentAssignment.trim( // Trim any whitespace
    )

println(((('Current assignment for ' + reportId) + ': \'') + currentAssignment) + '\'')

// Handle different assignment scenarios
if (currentAssignment == '') {
    // Case 1: Report is unassigned
    println(('Report ' + reportId) + ' is currently unassigned. Assigning to deekshithaS.')

    WebUI.doubleClick(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to (1)'))

    WebUI.setText(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to (1)'), 'deekshithaS')

    WebUI.click(findTestObject('Object Repository/Summary/li_deekshithaS (1)') // Handle the confirmation dialog
        )
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
WebUI.waitForElementPresent(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to (1)'), 
    10)

def newAssignment = WebUI.getAttribute(findTestObject('Object Repository/Summary/input_SIG0015_assigned_to (1)'), 
    'value')

newAssignment = newAssignment.trim( // Trim any whitespace
    )

println(((('New assignment for ' + reportId) + ' (after trim): \'') + newAssignment) + '\'')

// Compare trimmed values
WebUI.verifyEqual(newAssignment, 'deekshithaS')

println(('Successfully verified that report ' + reportId) + ' is now assigned to deekshithaS')

WebUI.click(findTestObject('Object Repository/Summary/span_RBC (1)'))

WebUI.click(findTestObject('Object Repository/Summary/input_Microcytes_Microcytes'))

WebUI.click(findTestObject('Object Repository/Summary/input_Macrocytes_Macrocytes'))

WebUI.click(findTestObject('Object Repository/Summary/input_Anisocytosis_Anisocytosis'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Shape'), 'Shape')

WebUI.click(findTestObject('Object Repository/Summary/button_Shape'))

WebUI.click(findTestObject('Object Repository/Summary/input_Ovalocytes_Ovalocytes'))

WebUI.click(findTestObject('Object Repository/Summary/input_Elliptocytes_Elliptocytes'))

WebUI.click(findTestObject('Object Repository/Summary/input_Teardrop Cells_Teardrop Cells'))

WebUI.click(findTestObject('Object Repository/Summary/input_Fragmented Cells_Fragmented Cells'))

WebUI.click(findTestObject('Object Repository/Summary/input_Target Cells_Target Cells'))

WebUI.click(findTestObject('Object Repository/Summary/input_Echinocytes_Echinocytes'))

WebUI.click(findTestObject('Object Repository/Summary/input_Acanthocytes_Acanthocytes'))

WebUI.click(findTestObject('Object Repository/Summary/input_Sickle Cells_Sickle Cells'))

WebUI.click(findTestObject('Object Repository/Summary/input_Stomatocytes_Stomatocytes'))

WebUI.click(findTestObject('Object Repository/Summary/input_Poikilocytosis_Poikilocytosis'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Color'), 'Color')

WebUI.click(findTestObject('Object Repository/Summary/button_Color'))

WebUI.click(findTestObject('Object Repository/Summary/input_Hypochromic Cells_Hypochromic Cells'))

WebUI.click(findTestObject('Object Repository/Summary/input_Polychromatic Cells_Polychromatic Cells'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Inclusions'), 'Inclusions')

WebUI.click(findTestObject('Object Repository/Summary/button_Inclusions'))

WebUI.click(findTestObject('Object Repository/Summary/input_Howell-Jolly Bodies_Howell-Jolly Bodies'))

WebUI.click(findTestObject('Object Repository/Summary/input_Pappenheimer Bodies_Pappenheimer Bodies'))

WebUI.click(findTestObject('Object Repository/Summary/input_Basophilic Stippling_Basophilic Stippling'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/button_Summary'), 'Summary')

WebUI.click(findTestObject('Object Repository/Summary/button_Summary'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_RBC grading'), 'RBC grading')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Cell name'), 'Cell name')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Grade'), 'Grade')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Size'), 'Size')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_0'), '0')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_1'), '1')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_2'), '2')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_3'), '3')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Macrocytes'), 'Macrocytes')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Anisocytosis'), 'Anisocytosis')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Microcytes_1'), 'Microcytes')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Shape'), 'Shape')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Ovalocytes'), 'Ovalocytes')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Elliptocytes'), 'Elliptocytes')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Teardrop Cells'), 'Teardrop Cells')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Fragmented Cells'), 'Fragmented Cells')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Target Cells'), 'Target Cells')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Echinocytes'), 'Echinocytes')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Acanthocytes'), 'Acanthocytes')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Sickle Cells'), 'Sickle Cells')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Stomatocytes'), 'Stomatocytes')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Poikilocytosis'), 'Poikilocytosis')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Color'), 'Color')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Hypochromic Cells'), 'Hypochromic Cells')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Polychromatic Cells'), 
    'Polychromatic Cells')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Inclusions'), 'Inclusions')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Howell-Jolly Bodies'), 
    'Howell-Jolly Bodies')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Pappenheimer Bodies'), 
    'Pappenheimer Bodies')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/div_Basophilic Stippling'), 
    'Basophilic Stippling')



