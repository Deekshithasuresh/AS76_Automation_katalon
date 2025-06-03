import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.Keys

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI 
import java.text.SimpleDateFormat


CustomKeywords.'generic.dataManagement.loginAdmin'()

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/div_Storage management'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Configure retention policy'), 'Configure retention policy')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Configure retention policy'))

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Edit Policy'), 'Edit Policy')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/button_Edit Policy'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_Admin Console/input_Days_retention-days-input'), 0)

WebUI.verifyElementPresent(findTestObject('Object Repository/Report_Listing/Page_Admin Console/input_Time_deletion-time-input'), 0)

TestObject daysInput = findTestObject('Object Repository/Report_Listing/Page_Admin Console/input_Days_retention-days-input')


// 2. Clear and try to set a decimal number

// Clear the input
WebUI.sendKeys(daysInput, Keys.chord(Keys.COMMAND, 'a'))
WebUI.sendKeys(daysInput, Keys.chord(Keys.DELETE))
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


TestObject timeInput = findTestObject('Object Repository/Report_Listing/Page_Admin Console/input_Time_deletion-time-input')

// Clear the input
WebUI.sendKeys(timeInput, Keys.chord(Keys.COMMAND, 'a'))
WebUI.sendKeys(timeInput, Keys.chord(Keys.DELETE))

// Set desired time (e.g., 10:30 AM)
WebUI.setText(timeInput, '10:30 AM')

// Optional: click somewhere to trigger validation
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_Admin Console/section_Retention durationPlease enter the _8cfc85'))

String timeValue = WebUI.getAttribute(timeInput, 'value')  // e.g., "22:30"


// Expected time in 12-hour format
String expected12Hr = '10:30 PM'

// Convert expected 12-hour time to 24-hour format
SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm a")
SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm")
String expected24Hr = outFormat.format(inFormat.parse(expected12Hr))  // "22:30"

// Assert
assert timeValue == expected24Hr : "Expected '${expected24Hr}', got '${timeValue}'"
