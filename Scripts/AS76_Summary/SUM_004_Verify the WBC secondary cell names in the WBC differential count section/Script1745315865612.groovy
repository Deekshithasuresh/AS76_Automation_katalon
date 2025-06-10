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

WebUI.maximizeWindow()
// Click on the report with ID SIG0004 to open it
WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/td_SIG0004'))

// Define secondary cells to check
def secondaryCells = [
	'Band Forms',
	'Hypersegmented*',
	'Neutrophils with Toxic Granules*',
	'Reactive',
	'Large Granular Lymphocytes*',
	'Promyelocytes',
	'Myelocytes',
	'Metamyelocytes',
	'Atypical Cells*',
	'Lymphoid Blasts*',
	'Myeloid Blasts*'
]

WebUI.comment("=== CHECKING SECONDARY CELLS IN SUMMARY ===")

// Scroll to ensure all content is loaded
WebUI.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)", null)
WebUI.delay(2)

def foundCells = []
def notFoundCells = []

// Check each secondary cell
for (String cellName : secondaryCells) {
	def cellExists = WebUI.executeJavaScript(
		"return Array.from(document.querySelectorAll('td')).some(td => td.textContent.trim() === '${cellName}');",
		null
	)
	
	if (cellExists) {
		foundCells.add(cellName)
		WebUI.comment("❌ FOUND: ${cellName}")
	} else {
		notFoundCells.add(cellName)
		WebUI.comment("✓ NOT FOUND: ${cellName}")
	}
}

// Summary
WebUI.comment("=== RESULTS ===")
WebUI.comment("Secondary cells found in summary: ${foundCells.size()}")
WebUI.comment("Secondary cells not found in summary: ${notFoundCells.size()}")

if (foundCells.isEmpty()) {
	WebUI.comment("🎉 SUCCESS: No secondary cells found in summary")
} else {
	WebUI.comment("❌ ISSUE: Secondary cells should not appear in summary")
	WebUI.comment("Found cells: ${foundCells}")
}

// Scroll back to top
WebUI.executeJavaScript("window.scrollTo(0, 0)", null)
WebUI.delay(1)