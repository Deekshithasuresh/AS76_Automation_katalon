import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



CustomKeywords.'generic.custumFunctions.login'()

WebUI.maximizeWindow()

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
	Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
	Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 'UTC')

WebDriver driver = DriverFactory.getWebDriver()

// Get all option elements containing "UTC" after search
List<WebElement> timeZoneOptions = driver.findElements(By.xpath("//li[contains(@id, 'assigned_to-option') and contains(.,'UTC')]"))

// Verify at least one result and that all shown options have "UTC" prefix
assert timeZoneOptions.size() > 0 : "No timezone options containing 'UTC' found."
for (WebElement option : timeZoneOptions) {
    String optText = option.getText()
    assert optText.startsWith("UTC") || optText.contains("(UTC") : "Timezone option lacks 'UTC' prefix: $optText"
}

if (timeZoneOptions.size() > 0) {
    ((JavascriptExecutor) driver).executeScript('arguments[0].scrollIntoView(false);', timeZoneOptions.get(0))
    WebUI.delay(1)
    ((JavascriptExecutor) driver).executeScript('arguments[0].scrollIntoView(false);', timeZoneOptions.get(timeZoneOptions.size()-1))
    WebUI.delay(1)
}

WebUI.delay(5)


WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
	Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
	Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), "Kolkata")
List<WebElement> searchOptions = driver.findElements(By.xpath("//li[starts-with(.,'(UTC') and contains(.,'Kolkata')]"))
assert searchOptions.size() > 0 : "Search for 'Kolkata' returned no options."

WebUI.delay(5)


WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
	Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
	Keys.chord(Keys.BACK_SPACE))
WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), "+05:30")
List<WebElement> numSearchOptions = driver.findElements(By.xpath("//li[starts-with(.,'(UTC') and contains(.,'+05:30')]"))
assert numSearchOptions.size() > 0 : "Search for numeric '+05:30' returned no options."

WebUI.delay(5)


// 4. Check for any misformatted (non-UTC prefixed) timezones in dropdown
List<WebElement> nonUtcOptions = driver.findElements(
	By.xpath("//li[not(starts-with(.,'(UTC'))]")
)
assert nonUtcOptions.size() == 0 : "Drop down includes timezones without UTC prefix!"

WebUI.comment("Timezone dropdown validations passed as per UI and requirements.")

