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
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import org.apache.poi.ss.usermodel.*
import java.io.File as File
import java.io.FileInputStream as FileInputStream
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver

WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Sign In'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/img_PBS_icon-img'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/li_Time zone'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Edit time zone settings'))

WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

// Load Excel file
String filePath = '../All time zone.xlsx' // Adjust if needed

FileInputStream fis = new FileInputStream(filePath)

Workbook workbook = new XSSFWorkbook(fis)

Sheet sheet = workbook.getSheetAt(0)

List<String> excelTimeZones = []

for (int i = 1; i <= sheet.getLastRowNum(); i++) {
    Row row = sheet.getRow(i)

    if ((row != null) && (row.getCell(0) != null)) {
        String tz = row.getCell(0).toString().trim()

        excelTimeZones.add(tz)
    }
}

workbook.close()

fis.close()

// Click to open dropdown
WebUI.click(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'))

// Wait for options to appear (adjust timeout as needed)
WebUI.delay(2)

// Get dropdown options
WebDriver driver = DriverFactory.getWebDriver()

//List<WebElement> dropdownOptions = driver.findElements(By.xpath("assigned_to-option-"))
String idd = 'assigned_to-option-'

List<String> uiTimeZones = []

for (int i = 0; i <= 595; i++) {
    WebElement element = driver.findElement(By.id(idd + i) // Change XPath
        )

    //println(element.getText().trim())

    uiTimeZones.add(element.getText().trim())
}

// Function to extract "Continent/City" part
String extractContinentCity(String timeZoneString) {
	// Match pattern like "(UTC+02:00) Europe/Busingen"
	if (timeZoneString.contains(") ")) {
		return timeZoneString.split("\\) ")[1].trim()
	} else {
		return timeZoneString.trim() // fallback
	}
}

// Extract only Continent/City from both Excel and UI lists
List<String> extractedExcelTimeZones = excelTimeZones.collect { extractContinentCity(it) }
List<String> extractedUiTimeZones = uiTimeZones.collect { extractContinentCity(it) }

// Compare the cleaned lists
extractedExcelTimeZones.each { def excelTz ->
	if (extractedUiTimeZones.contains(excelTz)) {
		println("✅ Found in dropdown: $excelTz")
	} else {
		println("❌ NOT found in dropdown: $excelTz")
	}
}

