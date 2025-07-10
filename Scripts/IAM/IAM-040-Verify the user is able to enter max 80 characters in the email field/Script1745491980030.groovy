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
String email = "asfghjkldrtfgyuhifdghjkdfghjkdfghjkfghjfghjfghjhgjgfhjhgfdffgdgsdrftgyl@gmail.com"  // above 51 characters
TestObject emailInput = findTestObject('Object Repository/IAM Model/Page_Admin Console/input_Email_rbc-input-box')
WebUI.setText(emailInput, email)

// Get the entered text from the field
String actualEmail = WebUI.getAttribute(emailInput, 'value')

// ✅ Verify it's exactly 8 characters
assert actualEmail.length() == 81 : "❌ Email is not 81 characters"

// ✅ Verify no validation error message is present (adjust the object below)
TestObject errorMsg = findTestObject('Object Repository/IAM Model/Page_Admin Console/Page_Admin Console/Page_Admin Console/Max_50 characters')
WebUI.verifyElementPresent(errorMsg, 5)

// Expected text you want to verify
String expectedText = "Maximum 80 characters allowed"

// Verify the element contains the expected text
WebUI.verifyElementText(errorMsg, expectedText)

String actualColor = WebUI.getCSSValue(emailInput, "color")

// Expected color value 
String expectedColor = "rgba(224, 66, 77, 1)" 

assert actualColor == expectedColor : "❌ Color mismatch. Expected: $expectedColor, but got: $actualColor"
WebUI.comment("✅ Color of error message is as expected: $actualColor")

