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

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFuctions.assignOrReassignOnTabs'('pawan', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'), 
    0)

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), 0)

// Clear the input box
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '')

// Define the new expected value dynamically
String expectedValue = '6666'

// Set the new value
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), expectedValue)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/div_Default for all your future reports'), 'Default for all your future reports')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/div_Default for all your future reports'))

// Click the save button
WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/button_Save'))

// Wait for the changes to reflect
WebUI.delay(2)

// Dynamically create the TestObject for the multiplication factor display
TestObject multiplicationFactorDisplay = new TestObject()

multiplicationFactorDisplay.addProperty('xpath', com.kms.katalon.core.testobject.ConditionType.EQUALS, '//div[@class=\'multiplication-factor\']/span')

// Check if the object is present before fetching the text
if (WebUI.verifyElementPresent(multiplicationFactorDisplay, 5)) {
    String actualValue = WebUI.getText(multiplicationFactorDisplay)

    WebUI.comment('Expected Value: ' + expectedValue)

    WebUI.comment('Actual Value Displayed: ' + actualValue)

    // Verify if the saved value matches the expected one
    WebUI.verifyMatch(actualValue, expectedValue, false)
} else {
    WebUI.comment('Multiplication factor display not found!')
}

