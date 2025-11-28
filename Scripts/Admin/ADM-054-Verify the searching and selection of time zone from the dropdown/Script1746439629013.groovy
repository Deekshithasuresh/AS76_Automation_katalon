import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


WebUI.openBrowser('')

WebUI.navigateToUrl('https://admin.as76.local/login')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Username_loginId'), 
    'jyothi')

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Password_loginPassword'), 
    'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Sign in'))

WebUI.click(findTestObject('Session management reporting/Page_Admin Console/profile_img'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Edit time zone settings'))

TestObject input_select_tz = findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to')

WebUI.click(input_select_tz)

WebUI.sendKeys(input_select_tz, Keys.chord(Keys.CONTROL, 'a'))

WebUI.sendKeys(input_select_tz, Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to'), 
    'abc')

WebUI.delay(1)

// Define a dynamic TestObject for the "No options" text
TestObject noOptionsText = new TestObject('dynamicNoOptions')

noOptionsText.addProperty('xpath', ConditionType.EQUALS, '//*[contains(text(), \'No options\')]')

// Verify if "No options" is present
boolean isNoOptionsVisible = WebUI.verifyElementPresent(noOptionsText, 2, FailureHandling.OPTIONAL)

if (isNoOptionsVisible) {
    WebUI.comment('✅ \'No options\' message is visible as expected.')
} else {
    WebUI.comment('❌ \'No options\' message was not found.')
}

String TimeZone1 = 'Africa/Cairo'

setAndSaveTimeZone(TimeZone1)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/alter_popup_img'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Success'), 
    'Success')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Time zone changed successfuly'), 
    'Time zone changed successfuly')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'), 
    30)

String TimeZone2 = '(UTC+01:00) Africa/Casablanca'

setAndSaveTimeZone(TimeZone2)

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/alter_popup_img'), 30)

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Success'), 
    'Success')

WebUI.verifyElementText(findTestObject('Object Repository/Session management reporting/Page_Admin Console/div_Time zone changed successfuly'), 
    'Time zone changed successfuly')

WebUI.verifyElementPresent(findTestObject('Session management reporting/Page_Admin Console/close_edit_timezone_settings'), 
    30)

void setAndSaveTimeZone(String timeZone) {
    TestObject timezoneDropdown = findTestObject('Object Repository/Session management reporting/Page_Admin Console/input_Select a time zone_assigned_to')

    WebUI.click(timezoneDropdown)

    WebUI.sendKeys(timezoneDropdown, Keys.chord(Keys.CONTROL, 'a'))

    WebUI.sendKeys(timezoneDropdown, Keys.chord(Keys.BACK_SPACE))

    WebUI.sendKeys(timezoneDropdown, timeZone)
	
    WebDriver driver = DriverFactory.getWebDriver()
    //WebElement firstOption = driver.findElement(By.id('assigned_to-option-0'))

	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))
	
	WebElement firstOption = wait.until(
		ExpectedConditions.elementToBeClickable(
			By.id('assigned_to-option-0')
		)
	)
	
    firstOption.click()

    TestObject saveButton = findTestObject('Object Repository/Session management reporting/Page_Admin Console/button_Save')

    WebUI.click(saveButton)
}

