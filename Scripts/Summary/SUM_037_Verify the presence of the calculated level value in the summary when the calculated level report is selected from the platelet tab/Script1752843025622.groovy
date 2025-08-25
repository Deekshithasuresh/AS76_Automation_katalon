import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelets'), 'Platelets')

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Platelets'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_Platelet count (x 109L)_platelet-coun_cc3a22'), 
    '')

WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/input_Platelet count (x 109L)_platelet-coun_cc3a22'))



WebUI.click(findTestObject('Object Repository/Summary/Page_PBS (1)/button_Summary'))

WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Platelet count level'), 'Platelet count level')

String actualText = WebUI.getText(findTestObject('Object Repository/Summary/span_Significantly decreased'))
println("Text from element: " + actualText)

// Verify the text of the "Platelet count level" and "Significantly decreased"
String plateletCountLevel = WebUI.getText(findTestObject('Object Repository/Summary/span_Platelet count level'))
String plateletCountStatus = WebUI.getText(findTestObject('Object Repository/Summary/span_Significantly decreased'))

// Expected values
TestObject expectedPlateletCount = new TestObject().addProperty(
	'xpath', ConditionType.EQUALS,
	"//span[contains(text(),'Platelet count level')]/parent::div/following-sibling::div"
)


String expectedPlateletCountStatus = WebUI.getText(expectedPlateletCount)



String expectedPlateletCountLevel = 'Platelet count level'

// Check if both values are equal to the expected ones
if (plateletCountLevel == expectedPlateletCountLevel && plateletCountStatus == expectedPlateletCountStatus) {
	println("Both values are as expected.")
} else {
	println("Values do not match the expected text.")
}
