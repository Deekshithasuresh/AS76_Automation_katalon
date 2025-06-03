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

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'), 0)

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'))


WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), 0)

// Set the value to 5000 after clearing
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '50000')

// Click the save button
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/button_Save'))

// Wait for the changes to be saved
WebUI.delay(2)

// Dynamically create the TestObject for the multiplication factor display
TestObject multiplicationFactorDisplay = new TestObject()
multiplicationFactorDisplay.addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, "//div[@class='multiplication-factor']/span")

// Check if the object is present before getting the text
if (WebUI.verifyElementPresent(multiplicationFactorDisplay, 5)) {
	// Get the saved value from the multiplication factor field
	String savedValue = WebUI.getText(multiplicationFactorDisplay)
	
	// Print the saved value
	WebUI.comment('Saved value in multiplication factor: ' + savedValue)
	
	// Verify if the saved value is correct
	WebUI.verifyMatch(savedValue, '50000', false)
} else {
	WebUI.comment('Multiplication factor display not found!')
}