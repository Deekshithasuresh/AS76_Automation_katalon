import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// Open browser and login
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-admin.sigtuple.com/login')

WebUI.setText(findTestObject('IAM Model/Page_Admin Console/input_Username_loginId'), 'adminuserr')
WebUI.setEncryptedText(findTestObject('IAM Model/Page_Admin Console/input_Password_loginPassword'), 'JBaPNhID5RC7zcsLVwaWIA==')
WebUI.click(findTestObject('IAM Model/Page_Admin Console/button_Sign in'))

// Navigate to Create User
WebUI.click(findTestObject('IAM Model/Page_Admin Console/div_User'))
WebUI.click(findTestObject('IAM Model/Page_Admin Console/button_Users'))
WebUI.click(findTestObject('IAM Model/Page_Admin Console/button_Create User'))

// Enter invalid username (less than 3 characters)
TestObject usernameInput = findTestObject('IAM Model/Page_Admin Console/input_Username_rbc-input-box')
//WebUI.click(usernameInput)
WebUI.setText(usernameInput, 'Pa') // Only 2 characters

// Validate the error message text
TestObject errorMsg = findTestObject('IAM Model/Page_Admin Console/div_Minimum 3 characters required')
WebUI.verifyElementText(errorMsg, 'Minimum 3 characters required')

// ✅ Get and verify the error text color (usually red in UI)
String colorValue = WebUI.getCSSValue(usernameInput, 'color')
WebUI.comment("🔍 Error message color: " + colorValue)

// ✅ Expected color
assert colorValue == 'rgba(224, 66, 77, 1)' : "❌ Expected red color, but got: $colorValue"

WebUI.comment("✅ Validation message and color are verified successfully.")
