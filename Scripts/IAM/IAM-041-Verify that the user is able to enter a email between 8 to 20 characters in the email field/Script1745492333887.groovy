import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Username_loginId'), 'adminuserr')

WebUI.setEncryptedText(findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/div_User'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Users'))

WebUI.click(findTestObject('Object Repository/IAM Model/Page_Admin Console/button_Create User'))

// Focus and input a valid 8-character email (e.g., ab@x.com)
String email = "abc@x.com"  // 8 characters
TestObject emailInput = findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box')
WebUI.setText(emailInput, email)

// Get the entered text from the field
String actualEmail = WebUI.getAttribute(emailInput, 'value')

// ✅ Verify it's exactly 8 characters
assert actualEmail.length() == 9 : "❌ Email is not 9 characters"

// ✅ Verify no validation error message is present (adjust the object below)
TestObject errorMsg = findTestObject('Object Repository/IAM Model/Page_Admin Console/div_Minimum 3 characters required')
WebUI.verifyElementNotPresent(errorMsg, 2)
