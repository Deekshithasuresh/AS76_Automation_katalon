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

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/input_Username_loginId'), 'manju')

WebUI.setEncryptedText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/div_Storage management'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Configure retention policy'), 'Configure retention policy')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Configure retention policy'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/div_Retention duration'), 'Retention duration')

WebUI.verifyElementClickable(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Edit Policy'))


TestObject timeField = findTestObject('Object Repository/Report_Listing/Page_Admin Console/div_time_value')

// Verify the element is not an input
String tagName = WebUI.executeJavaScript(
	"return arguments[0].tagName.toLowerCase();", Arrays.asList(WebUI.findWebElement(timeField))
)

assert tagName == "div" : "❌ Expected a read-only div, but found: " + tagName
println("✅ Field is a non-editable div (read-only)")



TestObject dayField = findTestObject('Object Repository/Report_Listing/Page_Admin Console/div_days')

// Verify the element is not an input
String tagNameday = WebUI.executeJavaScript(
	"return arguments[0].tagName.toLowerCase();", Arrays.asList(WebUI.findWebElement(dayField))
)

assert tagName == "div" : "❌ Expected a read-only div, but found: " + tagNameday
println("✅ Field is a non-editable div (read-only)")

