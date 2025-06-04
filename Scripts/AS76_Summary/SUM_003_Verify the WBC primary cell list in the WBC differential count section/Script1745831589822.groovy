 // Import static methods from Katalon libraries for finding test objects, data, and checkpoints
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
// Import Katalon classes and assign aliases for easier use
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

// Initialize a new Chrome browser instance
WebUI.openBrowser('')

// Navigate to the PBS login page
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Enter the username 'deekshithaS' in the login ID field
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

// Enter the encrypted password in the password field
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

// Press Enter to submit the login form
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Click on the report with ID SIG0004 to open it
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0004'))

// Verify the WBC differential count table header is present
WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_WBC differential count ()'), 'WBC differential count (%)')

// Verify the 'Cell name' column header is present
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_Cell name'), 'Cell name')

// Verify the percentage symbol column header is present
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_'), '%')

// Verify the WBC header is present
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/th_WBC'), 'WBC')

// Verify Neutrophils cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Neutrophils'), 'Neutrophils')

// Verify Lymphocytes cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Lymphocytes'), 'Lymphocytes')

// Verify Eosinophils cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Eosinophils'), 'Eosinophils')

// Verify Monocytes cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Monocytes'), 'Monocytes')

// Verify Basophils cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Basophils'), 'Basophils')

// Verify Immature Granulocytes cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Immature Granulocytes'), 'Immature Granulocytes')

// Verify Atypical Cells/Blasts cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Atypical CellsBlasts'), 'Atypical Cells/Blasts')

// Verify Immature Eosinophils cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Immature Eosinophils'), 'Immature Eosinophils')

// Verify Immature Basophils cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Immature Basophils'), 'Immature Basophils')

// Verify Promonocytes cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Promonocytes'), 'Promonocytes')

// Verify Prolymphocytes cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Prolymphocytes'), 'Prolymphocytes')

// Verify Hairy Cells cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Hairy Cells'), 'Hairy Cells')

// Verify Sezary Cells cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Sezary Cells'), 'Sezary Cells')

// Verify Plasma Cells cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Plasma Cells'), 'Plasma Cells')

// Verify Others cell is present in the WBC table
WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Others'), 'Others')

summary = WebUI.getText(findTestObject('Summary/Page_PBS (1)/div_Cell nameWBCNeutrophils34.8Lymphocytes4.3Eosinophils10.9Monocytes19.6Basophils-Immature Granulocytes-Atypical CellsBlasts30.4Immature Eosinophils-Immature Basophils-Promonocytes-Prolymphocytes-Hairy Cells-Sezary Cell'))

println ('hi')
println(summary)
// Define the expected order of WBC cells from the reference screenshot
def referenceWbcOrder = ['Neutrophils', 'Lymphocytes', 'Eosinophils', 'Monocytes', 'Basophils', 'Immature Granulocytes', 'Atypical Cells/Blasts'
    , 'Immature Eosinophils', 'Immature Basophils', 'Promonocytes', 'Prolymphocytes', 'Hairy Cells', 'Sezary Cells', 'Plasma Cells'
    , 'Others']

println(referenceWbcOrder)

// Create an array to store the actual cell names from the UI in order
def uiCells = [WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Neutrophils')), WebUI.getText(findTestObject(
            'Object Repository/Page_PBS/td_Lymphocytes')), WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Eosinophils'))
    , WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Monocytes')), WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Basophils'))
    , WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Immature Granulocytes')), WebUI.getText(findTestObject(
            'Object Repository/Page_PBS/td_Atypical CellsBlasts')), WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Immature Eosinophils'))
    , WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Immature Basophils')), WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Promonocytes'))
    , WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Prolymphocytes')), WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Hairy Cells'))
    , WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Sezary Cells')), WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Plasma Cells'))
    , WebUI.getText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Others'))]

println(uiCells)

// Print a statement confirming all WBC cells are present in the UI
println('All the WBC differential count cells are present in the UI.')

// Add the same confirmation message to the Katalon test report
WebUI.comment('All the WBC differential count cells are present in the UI.')

// Initialize a boolean flag to track if the order matches
boolean orderMatch = true

// Compare each cell in the reference order with the corresponding cell in the UI
for (int i = 0; i < referenceWbcOrder.size(); i++) {
    // Check if the current position exists in UI cells and if the names match
    if ((i < uiCells.size()) && ((referenceWbcOrder[i]) != (uiCells[i]))) {
        // If any cell is out of order, set flag to false and exit loop
        orderMatch = false

        break
    }
}

// If order matches, print confirmation message
if (orderMatch) {
    // Print message to console
    println('The cells in the UI are displayed in the same order as in the reference screenshot.')

    // Add message to Katalon test report
    WebUI.comment('The cells in the UI are displayed in the same order as in the reference screenshot.') // If order doesn't match, print error message
    // Add error message to Katalon test report
    // Print both reference and actual orders for comparison to console
    // Add both reference and actual orders to Katalon test report
} else {
    println('The cells in the UI are NOT displayed in the same order as in the reference screenshot.')

    WebUI.comment('The cells in the UI are NOT displayed in the same order as in the reference screenshot.')

    println('Reference order: ' + referenceWbcOrder.join(', '))

    println('UI order: ' + uiCells.join(', '))

    WebUI.comment('Reference order: ' + referenceWbcOrder.join(', '))

    WebUI.comment('UI order: ' + uiCells.join(', '))
}

