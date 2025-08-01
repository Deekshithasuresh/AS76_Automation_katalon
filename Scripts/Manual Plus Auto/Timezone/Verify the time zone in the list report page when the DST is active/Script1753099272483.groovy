import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.*
import java.time.format.*

import org.openqa.selenium.*

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI 

import dst.DSTValidator

CustomKeywords.'generic.custumFunctions.login'()

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))


String filePath = 'Time_zone_data/Manual_data.xlsx'
String Timezonefromxl = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'Time zone')
String TimezoneOffset = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'Offset')




DSTValidator validator = new DSTValidator()
 validator.selectTimeZone(Timezonefromxl)
 
 WebDriver driver = DriverFactory.getWebDriver()
 String timezonefull = driver.findElement(By.xpath("//input[@id='assigned_to']")).getAttribute("value")
 
 def matcher = timezonefull =~ /\(?(UTC[+-]\d{2}:\d{2})\)?/
 String extractedOffset = matcher ? matcher[0][1] : ""
 
 println extractedOffset
 
 validator.verifyCurrentDSTOffset(Timezonefromxl, extractedOffset , TimezoneOffset)

 WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))
 
 WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))
 
 // Wait and get full time string for UTC+12
 TestObject timeDisplay = findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_TimeZoneDisplay')
 
 WebUI.waitForElementVisible(timeDisplay, 10)
 
 String dateTimeTextUtcPlus12 = WebUI.getText(timeDisplay)
 
 println('Full text in UTC+12: ' + dateTimeTextUtcPlus12)
 
 
 validator.verifyTimezoneAbbreviation(dateTimeTextUtcPlus12)
 
 
 
 CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
 
 CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)
 
 WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')
 
 WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))
 
 CustomKeywords.'generic.Reclassification.classifyToSubCell'("Neutrophils", "Band Forms")
 
 TestObject btnKebab = new TestObject('btnKebab')
 btnKebab.addProperty('xpath', ConditionType.EQUALS,
	 "//button[.//img[contains(@src,'kebab_menu.svg')]]")
 TestObject optHistory = new TestObject('optHistory')
 optHistory.addProperty('xpath', ConditionType.EQUALS,
	 "//li[.//span[normalize-space()='History']]")
 
 WebUI.waitForElementClickable(btnKebab, 10)
 WebUI.click(btnKebab)
 WebUI.waitForElementClickable(optHistory, 5)
 WebUI.click(optHistory)
 
 String Historytimezone = driver.findElement(By.xpath("(//div[@class='time'])[1]")).getText()
 
 validator.verifyTimezoneAbbreviation(Historytimezone)
 
 
 
 
 
// America/New_York
//validator.verifyDST("America/New_York", LocalDate.of(2025, 3, 1), "-05:00")
//validator.verifyDST("America/New_York", LocalDate.of(2025, 3, 20), "-04:00")

// America/Los_Angeles
//validator.verifyDST("America/Los_Angeles", LocalDate.of(2025, 3, 1), "-08:00")
//validator.verifyDST("America/Los_Angeles", LocalDate.of(2025, 3, 20), "-07:00")

// Europe/London
//validator.verifyDST("Europe/London", LocalDate.of(2025, 3, 20), "+00:00")
//validator.verifyDST("Europe/London", LocalDate.of(2025, 4, 1), "+01:00")

// Europe/Berlin
//validator.verifyDST("Europe/Berlin", LocalDate.of(2025, 3, 20), "+01:00")
//validator.verifyDST("Europe/Berlin", LocalDate.of(2025, 4, 1), "+02:00")

// Australia/Sydney (DST ends April 6, starts Oct 5)
//validator.verifyDST("Australia/Sydney", LocalDate.of(2025, 4, 5), "+11:00")
//validator.verifyDST("Australia/Sydney", LocalDate.of(2025, 4, 7), "+10:00")

// Asia/Amman (DST starts Feb 28, 2025)
//validator.verifyDST("Asia/Amman", LocalDate.of(2025, 2, 27), "+02:00")
//validator.verifyDST("Asia/Amman", LocalDate.of(2025, 2, 28), "+03:00")

// Pacific/Auckland
//validator.verifyDST("Pacific/Auckland", LocalDate.of(2025, 4, 5), "+13:00")
//validator.verifyDST("Pacific/Auckland", LocalDate.of(2025, 4, 7), "+12:00")

