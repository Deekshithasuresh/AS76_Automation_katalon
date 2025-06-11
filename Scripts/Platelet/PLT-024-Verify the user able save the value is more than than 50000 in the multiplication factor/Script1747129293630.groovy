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

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/span_Platelets'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'), 0)

WebUI.click(findTestObject('Object Repository/Platelet/Page_PBS/i edit icon for multiplication factor'))


WebUI.verifyElementPresent(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), 0)

//set the input box to empty by this line.
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '')

// Set the value to 3000 after clearing
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '50001')

//set the input box to empty by this line.
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '')


// Set the value to 1 after clearing
WebUI.setText(findTestObject('Object Repository/Platelet/Page_PBS/Multiplication-input-box'), '999999')

//Verifying the error message when user enter the value 3000 or 0 in the mmultiplicator factor text box.
WebUI.verifyElementText(findTestObject('Object Repository/Platelet/Page_PBS/p_Enter a value between 5000 and 50000'), 'Enter a value between 5000 and 50000')

//Verifying the default state of the save button
WebUI.verifyElementNotClickable(findTestObject('Object Repository/Platelet/Page_PBS/button_Save'))


