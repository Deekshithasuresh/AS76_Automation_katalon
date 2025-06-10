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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword as WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFuctions.assignOrReassignOnTabs'('pawan', true)


WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

//verifying the presence of i icon when the platlet is not detected.

WebUI.verifyElementNotPresent(findTestObject('Object Repository/Platelet/Page_PBS/i-icon beside the platlet clum detected'),
	0)

// Check if the element is present before attempting to get the text
if (WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/div_Platelet clumps are detected. Platelet count might be underestimated'),
	5, FailureHandling.OPTIONAL)) {
	
	// Get the actual text from the element
	String actualText = WebUI.getText(findTestObject('Object Repository/Platelet/Page_PBS/div_Platelet clumps are detected. Platelet count might be underestimated'))
	String expectedText = 'Platelet clumps are detected. Platelet count might be underestimated.'
	
	// Compare the actual and expected texts
	if (actualText == expectedText) {
		WebUI.comment("Verification successful: " + actualText)
	} else {
		WebUI.comment("Text does not match. Actual: " + actualText + " | Expected: " + expectedText)
	}
} else {
	// Log a message if the element is not present
	WebUI.comment("Platelet clumps are not detected. The expected message is not displayed.")
}
