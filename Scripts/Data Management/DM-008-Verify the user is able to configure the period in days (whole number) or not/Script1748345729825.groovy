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

CustomKeywords.'generic.dataManagement.loginAdmin'()

WebUI.click(findTestObject('Object Repository/Page_Admin Console/div_Storage management'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/button_Configure retention policy'), 'Configure retention policy')

WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Configure retention policy'))

WebUI.verifyElementText(findTestObject('Object Repository/Page_Admin Console/button_Edit Policy'), 'Edit Policy')

WebUI.click(findTestObject('Object Repository/Page_Admin Console/button_Edit Policy'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Admin Console/input_Days_retention-days-input'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Page_Admin Console/input_Time_deletion-time-input'), 0)

TestObject daysInput = findTestObject('Object Repository/Page_Admin Console/input_Days_retention-days-input')


// 2. Clear and try to set a decimal number
WebUI.executeJavaScript("arguments[0].value=''", Arrays.asList(WebUI.findWebElement(daysInput)))
WebUI.click(daysInput)

WebUI.delay(10)
WebUI.setText(daysInput, '50.5')
daysValue = WebUI.getAttribute(daysInput, 'value')
assert !daysValue.contains('.') : "Decimal value accepted in 'Days' field: '${daysValue}'"
assert daysValue.matches('\\d+') : "Only whole numbers should be accepted, but got '${daysValue}'"


// 1. Clear and enter a valid whole number
WebUI.executeJavaScript("arguments[0].value=''", Arrays.asList(WebUI.findWebElement(daysInput)))

//WebUI.clearText(daysInput)
//WebUI.click(daysInput)
WebUI.setText(daysInput, '50')
String daysValue = WebUI.getAttribute(daysInput, 'value')
assert daysValue == '50' : "Expected '50', got '${daysValue}'"
