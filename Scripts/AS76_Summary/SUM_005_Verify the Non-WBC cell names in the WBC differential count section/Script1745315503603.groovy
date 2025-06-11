import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_44'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_NRBC'), 'NRBC')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/td_Smudge Cells'), 'Smudge Cells')

//Get all cell rows
def cellRows = WebUI.findWebElements(findTestObject('Object Repository/Page_PBS/table_cells'), 10)

// Initialize variables to track positions
int nrbcPosition = -1
int smudgeCellsPosition = -1

// Find positions of the cells
for (int i = 0; i < cellRows.size(); i++) {
	String cellText = cellRows[i].getText()
	if (cellText.contains('NRBC')) {
		nrbcPosition = i
	} else if (cellText.contains('Smudge Cells')) {
		smudgeCellsPosition = i
	}
}

// Verify NRBC appears before Smudge Cells
if (nrbcPosition != -1 && smudgeCellsPosition != -1) {
	assert nrbcPosition < smudgeCellsPosition, "Error: NRBC (position ${nrbcPosition}) does not appear before Smudge Cells (position ${smudgeCellsPosition})"
	println "Verification successful: NRBC (position ${nrbcPosition}) appears before Smudge Cells (position ${smudgeCellsPosition})"
} else {
	if (nrbcPosition == -1) {
		WebUI.comment("NRBC cell not found")
	}
	if (smudgeCellsPosition == -1) {
		WebUI.comment("Smudge Cells not found")
	}
	assert false, "Required cells not found"
}