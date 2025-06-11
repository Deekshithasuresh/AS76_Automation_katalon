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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.text.SimpleDateFormat as SimpleDateFormat
import java.util.TimeZone as TimeZone
import java.util.Date as Date

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC0900) Japan'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

//WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'),
//    'Time zone changed successfully')
WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Success'), 'Success')

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_(UTC0000) AfricaAbidjan_icon-tick-mark-filled'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
    'Time zone changed successfully')

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'), 
    30)

WebUI.verifyElementClickable(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/th_Scan date'), 30)

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_07-May-2025, 0441 PM (JST)'), 
    30)

// Step 1: Capture scan times in the current time zone (e.g., JST)
WebDriver driver = DriverFactory.getWebDriver()

List<WebElement> initialScanDates = driver.findElements(By.xpath('//tbody//tr//td[4]'))

List<String> initialTimes = initialScanDates.collect({ 
        it.getText()
    })

println('Initial scan times: ' + initialTimes)

// Step 2: Convert each initial scan time by subtracting 3 hours 30 minutes
List<String> adjustedTimes = []

SimpleDateFormat sdf = new SimpleDateFormat('dd-MMM-yyyy, hh:mm a (z)' // Adjust this format to match the format of your scan time text
)

initialTimes.each({ def scanTime ->
        try {
            Date date = sdf.parse(scanTime)

            Calendar calendar = Calendar.getInstance()

            calendar.setTime(date)

            calendar.add(Calendar.HOUR, -3 // Subtract 3 hours
                )

            calendar.add(Calendar.MINUTE, -30 // Subtract 30 minutes
                )

            Date adjustedDate = calendar.getTime()

            // Format the adjusted time back into a string
            String adjustedTime = sdf.format(adjustedDate)

            adjustedTimes.add(adjustedTime)

            println('Adjusted scan time: ' + adjustedTime)
        }
        catch (Exception e) {
            println('Error parsing scan time: ' + scanTime)
        } 
    })

// Step 2: Change the time zone (example: click dropdown and select UTC)
WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    Keys.chord(Keys.COMMAND, 'a'))

WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    Keys.chord(Keys.BACK_SPACE))

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'), 
    'kolkata')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_(UTC0530) AsiaKolkata'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save'))

//WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'),
//    'Time zone changed successfully')
WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Success'), 'Success')

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_(UTC0000) AfricaAbidjan_icon-tick-mark-filled'), 
    30)

WebUI.verifyElementText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully'), 
    'Time zone changed successfully')

WebUI.verifyElementPresent(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'), 
    30)

WebUI.verifyElementClickable(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/div_Time zone changed successfully_icon-close-thin'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_1'))

// Step 4: Capture updated scan times in the new time zone
WebUI.delay(3)

List<WebElement> updatedScanDates = driver.findElements(By.xpath('//tbody//tr//td[4]'))

List<String> updatedTimes = updatedScanDates.collect({ 
        it.getText()
    })

println('Updated scan times: ' + updatedTimes)

// Step 5: Verify the adjusted scan times with the updated times after changing the time zone
boolean scanTimesChanged = false

for (int i = 0; i < adjustedTimes.size(); i++) {
    if (!((adjustedTimes[i]).equals(updatedTimes[i]))) {
        scanTimesChanged = true

       println("Scan time changed at row ${i + 1}: ${adjustedTimes[i]} to ${updatedTimes[i]}")
    } else {
        scanTimesChanged = false

        println("Scan time did NOT change at row ${i + 1}: ${adjustedTimes[i]}")
    }
    
    assert scanTimesChanged == true : '❌ Scan times did not update after changing the time zone!'
}

