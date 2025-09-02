import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import pdfutils.PdfReader

// ========== STEP 1: Login ==========
CustomKeywords.'generic.custumFunctions.login'()

// ========== STEP 2: Read Slide ID and Timezone from Excel ==========
String filePath = 'Time_zone_data/Manual_data.xlsx'
String Slideid = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'Time zone slide')
String setTimeZone = CustomKeywords.'excel.ExcelUtils.getValueForKey'(filePath, 'set time zone')

println "✅ Slide ID: ${Slideid}"
println "🕒 Set Timezone (raw from Excel): ${setTimeZone}"

if (setTimeZone == null || setTimeZone.trim().isEmpty()) {
	WebUI.comment("❌ 'set time zone' key missing or blank in Excel.")
	assert false : "Missing 'set time zone' value from Excel!"
}

// ========== STEP 3: Click Row Based on Slide ID ==========
String xpath = "(//tr[.//td[contains(normalize-space(), '${Slideid}')]])[1]"
TestObject matchingRow = new TestObject('dynamicReportRow')
matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
WebUI.waitForElementClickable(matchingRow, 10)
WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
rowElement.click()

// ========== STEP 4: Assign Reviewer and Approve ==========
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')
def driver = DriverFactory.getWebDriver()

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/Page_PBS/td_Neutrophils'))

int NeutrophilsBefore = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Neutrophils')
WebUI.comment("🧪 Neutrophils Count Before: $NeutrophilsBefore")

if (NeutrophilsBefore > 0) {
	WebUI.executeJavaScript('window.scrollTo(0, document.body.scrollHeight)', null)
	WebUI.delay(1)
	try {
		CustomKeywords.'generic.Reclacification.classifyFromCellToCell'('Neutrophils', 'Unclassified')
		WebUI.delay(2)
	} catch (Exception e) {
		WebUI.comment("❌ Classification failed: ${e.message}")
	}
}

WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/buttonclick_Confirm_approve'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report_1'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Approve report_1'))

WebUI.delay(5)
WebUI.click(findTestObject('Object Repository/Page_PBS/kebab_dots_approve'))
WebUI.delay(3)
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Download PDF report (1)'))
// STEP 5: Read PDF
String downloadsPath = System.getProperty('user.home') + '/Downloads'
File latestPdf = PdfReader.getLatestPdfReport(downloadsPath)
println("📄 Latest PDF path: ${latestPdf.absolutePath}")

String pdfText = PdfReader.readText(latestPdf.absolutePath)
println("📃 PDF Preview:\n" + pdfText.take(5000))

// STEP 6: Extract (Excel) ZoneId
String selectedZoneId = String.valueOf(setTimeZone).replaceAll(".*\\)\\s*", "").trim() // e.g., "(UTC+00:00) Etc/GMT" → "Etc/GMT"
ZoneId excelZoneId = ZoneId.of(selectedZoneId)
println "📌 ZoneId from Excel: $excelZoneId"

// STEP 7: Extract full timestamp(s) from PDF (e.g., "23-Jul-2025, 05:47 AM (GMT)")
def timestampMatches = (pdfText =~ /(\d{2}-\w{3}-\d{4}, \d{2}:\d{2} (AM|PM)) \((\w+)\)/)

if (!timestampMatches) {
	WebUI.comment("❌ No timestamp patterns found in the PDF.")
	assert false : "No timestamp patterns found in the PDF"
}

// Timezone abbreviation map (PDF → ZoneId)
Map<String, String> tzMap = [
	'GMT': 'Etc/GMT', 'UTC': 'Etc/UTC',
	'EDT': 'America/New_York', 'EST': 'America/New_York',
	'CDT': 'America/Chicago', 'CST': 'America/Chicago',
	'PDT': 'America/Los_Angeles', 'PST': 'America/Los_Angeles',
	'BST': 'Europe/London', 'CET': 'Europe/Paris', 'CEST': 'Europe/Paris',
	'EET': 'Europe/Bucharest', 'EEST': 'Europe/Bucharest',
	'IST': 'Asia/Kolkata', 'JST': 'Asia/Tokyo', 'MSK': 'Europe/Moscow',
	'AEST': 'Australia/Sydney', 'AEDT': 'Australia/Sydney',
	'ACST': 'Australia/Adelaide', 'ACDT': 'Australia/Adelaide',
	'AKDT': 'America/Anchorage', 'AKST': 'America/Anchorage'
]

// STEP 8: Compare each timestamp
timestampMatches.eachWithIndex { fullMatch, i ->
	String dateTimeStr = fullMatch[1]          // e.g., "23-Jul-2025, 05:47 AM"
	String abbr = fullMatch[3]                 // e.g., "GMT"

	println "🧾 PDF Timestamp #${i+1} = $dateTimeStr ($abbr)"

	String zoneIdStr = tzMap[abbr]
	if (zoneIdStr == null) {
		WebUI.comment("⚠️ Unknown timezone abbreviation: $abbr. Add it to tzMap.")
		assert false : "Missing mapping for: $abbr"
	}

	ZoneId pdfZone = ZoneId.of(zoneIdStr)

	// Parse PDF's date-time string
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm a", Locale.ENGLISH)
	def localDT = LocalDateTime.parse(dateTimeStr, dtf)
	ZonedDateTime pdfZoned = localDT.atZone(pdfZone)

	// Get equivalent time in Excel's zone
	ZonedDateTime converted = pdfZoned.withZoneSameInstant(excelZoneId)

	println "🕒 From PDF: ${pdfZoned}"
	println "🕒 Converted to Excel zone ($excelZoneId): ${converted}"

	// Final validation (check just zone match or also hour if needed)
	assert converted.getZone() == excelZoneId : "❌ Zone mismatch: expected $excelZoneId but got ${converted.getZone()}"
}
