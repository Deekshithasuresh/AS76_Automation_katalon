package dst

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

import org.openqa.selenium.Keys

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import java.time.*
import java.time.format.*
import java.util.Locale






class DSTValidator {

	@Keyword
	def verifyDST(String timezoneId, LocalDate testDate, String expectedOffset) {


		selectTimeZone(timezoneId)


		TestObject saveButton = findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save')

		try {
			if (WebUI.verifyElementClickable(saveButton, 3, FailureHandling.OPTIONAL)) {
				WebUI.click(saveButton)
			} else {
				println "Save button is not clickable. Skipping click."
			}
		} catch (Exception e) {
			println "Save button click skipped due to exception: ${e.message}"
		}

		// 3. Format test date and send to system
		ZonedDateTime zdt = testDate.atStartOfDay(ZoneId.of(timezoneId))
		String actualOffset = zdt.getOffset().toString()  // Ex: -05:00 or -04:00

		println "Time on $testDate in $timezoneId = $zdt with offset = $actualOffset"

		assert actualOffset == expectedOffset : "Expected $expectedOffset but found $actualOffset"
	}

	@Keyword
	def selectTimeZone(String zone) {
		TestObject timeZoneInput = findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to')

		WebUI.click(timeZoneInput)

		WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
				Keys.chord(Keys.CONTROL, 'a'))

		WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
				Keys.chord(Keys.BACK_SPACE))

		WebUI.setText(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
				zone)

		WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
				Keys.chord(Keys.ARROW_DOWN))

		WebUI.sendKeys(findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/input_Select a time zone_assigned_to'),
				Keys.chord(Keys.RETURN))

		TestObject saveButton = findTestObject('Object Repository/Configure Time Zone Reporting/Page_PBS/button_Save')
		WebUI.click(saveButton)
	}


	@Keyword
	def verifyCurrentDSTOffset(String timezoneId, String uiOffsetObject, String expectedOffset = null) {


		// 1. Get today's date and DST offset
		LocalDate currentDate = LocalDate.now()
		ZonedDateTime zdtNow = currentDate.atStartOfDay(ZoneId.of(timezoneId))
		String actualOffset = zdtNow.getOffset().toString()  // e.g. -04:00, +01:00

		println "Current offset in $timezoneId on $currentDate = $actualOffset"

		// 2. If expectedOffset is passed, assert it
		if (expectedOffset != null) {
			assert actualOffset == expectedOffset : "❌ Expected offset $expectedOffset but got $actualOffset"
			println "✅ expected Offset [$expectedOffset] matches expected DST offset [$actualOffset]"
		}


		// 4. Get offset from UI
		String uiDisplayedOffset = uiOffsetObject

		println "UI displays offset: $uiDisplayedOffset"

		// 5. Compare UI offset with system-calculated offset
		assert uiDisplayedOffset.contains(actualOffset) : "❌ UI offset [$uiDisplayedOffset] does not match expected [$actualOffset]"

		println "✅ UI offset [$uiDisplayedOffset] matches expected DST offset [$actualOffset]"
	}
	
	
	
	
	
	
	static Map<String, String> tzMap = [
		'EDT': 'America/New_York', 'EST': 'America/New_York',
		'CDT': 'America/Chicago',  'CST': 'America/Chicago',
		'MDT': 'America/Denver',   'MST': 'America/Denver',
		'PDT': 'America/Los_Angeles', 'PST': 'America/Los_Angeles',
		'AKDT': 'America/Anchorage',  'AKST': 'America/Anchorage',
		'HADT': 'America/Adak',       'HAST': 'America/Adak',
		'BST': 'Europe/London',       'GMT': 'Europe/London',
		'CEST': 'Europe/Berlin',      'CET': 'Europe/Berlin',
		'EEST': 'Europe/Bucharest',   'EET': 'Europe/Bucharest',
		'MSK': 'Europe/Moscow',
		'IST': 'Asia/Kolkata', 'JST': 'Asia/Tokyo',
		'AEST': 'Australia/Sydney',   'AEDT': 'Australia/Sydney',
		'ACST': 'Australia/Adelaide', 'ACDT': 'Australia/Adelaide',
		'AWST': 'Australia/Perth'
	]

	/**
	 * Verifies if the given UI timestamp matches the expected timezone abbreviation (e.g., EDT, PDT)
	 * @param uiTimestamp String like "23-Jul-2025, 05:47 AM (EDT)"
	 */
	@Keyword
	static void verifyTimezoneAbbreviation(String uiTimestamp) {
		try {
			// 1. Extract date-time and abbreviation
			String datetimePart = uiTimestamp.split("\\(")[0].trim()  // "23-Jul-2025, 05:47 AM"
			String timezoneAbbr = uiTimestamp.replaceAll(".*\\((.*?)\\)", "\$1").trim() // "EDT"

			// 2. Parse local date-time
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm a", Locale.ENGLISH)
			LocalDateTime parsedLocalDateTime = LocalDateTime.parse(datetimePart, formatter)

			// 3. Map abbreviation to ZoneId
			if (!tzMap.containsKey(timezoneAbbr)) {
				throw new IllegalArgumentException("Unsupported timezone abbreviation: $timezoneAbbr")
			}

			String zoneIdStr = tzMap[timezoneAbbr]
			ZoneId zoneId = ZoneId.of(zoneIdStr)

			// 4. Get ZonedDateTime and calculate abbreviation
			ZonedDateTime zonedDateTime = parsedLocalDateTime.atZone(zoneId)
			String actualAbbr = zonedDateTime.format(DateTimeFormatter.ofPattern("zzz", Locale.ENGLISH))

			println "🔍 UI Timezone:       $timezoneAbbr"
			println "🕒 Zoned Time:        $zonedDateTime"
			println "✅ Calculated Abbr:   $actualAbbr"

			assert timezoneAbbr == actualAbbr : "❌ Mismatch! Expected $timezoneAbbr, but got $actualAbbr"

		} catch (Exception e) {
			throw new AssertionError("Timezone validation failed: ${e.message}", e)
		}
	}
}
