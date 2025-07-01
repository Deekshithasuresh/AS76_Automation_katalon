
package generic
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI











class FilterFunctions {

	@Keyword
	def boolean applyAndVerifyDateFilter(String startDateStr, String endDateStr) {
		boolean result = true

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
		Calendar startCal = Calendar.getInstance()
		Calendar endCal = Calendar.getInstance()
		startCal.setTime(sdf.parse(startDateStr))
		endCal.setTime(sdf.parse(endDateStr))

		// Open Date Range Picker
		TestObject dateRangeBtn = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"//div[contains(@class,'scanDatePage_container-date')]")
		WebUI.waitForElementClickable(dateRangeBtn, 10)
		WebUI.click(dateRangeBtn)

		// Select start date
		selectDateFromCalendar(startCal)

		// Select end date if different
		if (!startDateStr.equals(endDateStr)) {
			selectDateFromCalendar(endCal)
		}

		// Click "Apply" button (use JS if normal click fails)
		TestObject applyBtn = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"//div[contains(@class,'bottombar-buttons')]/span[2]")
		WebUI.executeJavaScript("arguments[0].click()", Arrays.asList(WebUiCommonHelper.findWebElement(applyBtn, 5)))
		WebUI.delay(2)

		// Read result dates from the table
		List<WebElement> dateElements = WebUiCommonHelper.findWebElements(
				new TestObject().addProperty("xpath", ConditionType.EQUALS, "//div[@class='time']"), 10)

		if (dateElements.isEmpty()) {
			WebUI.comment("✅ No reports found in selected date range — valid case.")
			return true
		}

		LocalDate startDate = parseDate(startDateStr)
		LocalDate endDate = parseDate(endDateStr)

		dateElements.eachWithIndex { el, i ->
			String rawText = el.getText().split(",")[0].trim()
			LocalDate reportDate = parseDate(rawText)

			if (reportDate == null) {
				WebUI.comment("⚠️ Unable to parse date in row ${i + 1}: ${rawText}")
				result = false
			} else if (reportDate.isBefore(startDate) || reportDate.isAfter(endDate)) {
				WebUI.comment("❌ Report in row ${i + 1} has date ${reportDate} outside selected range (${startDate} - ${endDate})")
				result = false
			} else {
				WebUI.comment("✅ Row ${i + 1} date ${reportDate} is within range.")
			}
		}

		return result
	}


	private void selectDateFromCalendar(Calendar cal) {
		String targetMonthYear = new SimpleDateFormat("MMMM yyyy").format(cal.getTime())
		String targetDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH))

		TestObject monthYearLabel = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"(//div[contains(@class,'MuiPickersCalendarHeader')])[3]")

		// Navigate to correct month/year
		int safetyCounter = 12
		while (safetyCounter-- > 0) {
			String currentMonthYear = WebUI.getText(monthYearLabel).trim()
			if (currentMonthYear.equalsIgnoreCase(targetMonthYear)) {
				break
			}
			if (cal.after(Calendar.getInstance())) {
				WebUI.click(new TestObject().addProperty("xpath", ConditionType.EQUALS, "//button[@aria-label='Next month']"))
			} else {
				WebUI.click(new TestObject().addProperty("xpath", ConditionType.EQUALS, "//button[@aria-label='Previous month']"))
			}
			WebUI.delay(1)
		}

		// Select the day
		TestObject dayButton = new TestObject().addProperty("xpath", ConditionType.EQUALS,
				"//button[not(@disabled) and text()='${targetDay}']")
		WebUI.click(dayButton)
		WebUI.delay(1)
	}

	private LocalDate parseDate(String input) {
		List<String> formats = [
			'd-M-yyyy',
			'dd-MM-yyyy',
			'd-MM-yyyy',
			'dd-M-yyyy',
			'dd-MMM-yyyy'
		]
		for (String fmt : formats) {
			try {
				return LocalDate.parse(input, DateTimeFormatter.ofPattern(fmt))
			} catch (DateTimeParseException e) {
				// Try next format
			}
		}
		return null
	}

	@Keyword
	def boolean assignedToFilter(List<String> reviewers) {
		TestObject assignedDropdown = new TestObject('assignedDropdown')
				.addProperty('xpath', ConditionType.EQUALS, "//ul[contains(@class,'filterComponent')]/li/span[text()='Assigned to']")
		WebUI.waitForElementClickable(assignedDropdown, 5)
		WebUI.click(assignedDropdown)
		WebUI.delay(1)

		// 3) Tick only the reviewers in your list
		TestObject optionsTO = new TestObject('assignedOptions')
				.addProperty('xpath', ConditionType.EQUALS,
				"//div[contains(@class,'assignedToPage_container-body')]//li")
		List<WebElement> options = WebUiCommonHelper.findWebElements(optionsTO, 10)
		options.each { WebElement li ->
			String name = li.getText().trim()
			if (reviewers.contains(name)) {
				TestObject checkbox = new TestObject('cb_' + name)
						.addProperty('xpath', ConditionType.EQUALS, ".//input")

				WebElement cbElt = li.findElement(By.xpath(".//input"))
				if (!cbElt.isSelected()) {
					cbElt.click()
					WebUI.comment("Selected reviewer: ${name}")
				}
			}
		}

		// 4) Click Apply via JS
		TestObject applyBtn = new TestObject('applyBtn')
				.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'bottombar-buttons')]/span[2]")
		WebUI.waitForElementVisible(applyBtn, 5)
		WebUI.executeJavaScript("arguments[0].click();",
				Arrays.asList(WebUiCommonHelper.findWebElement(applyBtn, 5)))
		WebUI.delay(5)

		// 5) Verify each visible report’s assigned‑to valuegit 
		TestObject reportInputsTO = new TestObject('reportInputs')
				.addProperty('xpath', ConditionType.EQUALS,
				"//tbody//tr/td//input[@id='assigned_to']")
		List<WebElement> reportInputs = WebUiCommonHelper.findWebElements(reportInputsTO, 10)
		boolean allMatched = true
		if (reportInputs.isEmpty()) {
			WebUI.comment("⚠ No reports found after filter.")
			//allMatched = false
		} else {
			reportInputs.eachWithIndex { WebElement inp, int idx ->
				String val = inp.getAttribute('value').trim()
				if (reviewers.any { val.contains(it) }) {
					WebUI.comment("✅ Row ${idx+1}: assigned to '${val}' is in filter list")
				} else {
					WebUI.comment("❌ Row ${idx+1}: assigned to '${val}' NOT in ${reviewers}")
					allMatched = false
				}
			}
		}

		return allMatched
	}

	@Keyword
	def statusFilter(String RStatus) {
		boolean flag = true

		// Get initial report count
		List<WebElement> initialReports = WebUiCommonHelper.findWebElements(
				new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody/tr"),
				10)
		int totalBeforeFilter = initialReports.size()

		// Open Filter UI
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Status'))
		WebUI.delay(2)

		// Select Status
		String initialXpath = "//div[contains(@class,'statusPage_container-body')]//ul/li//span[text()='${RStatus}']/parent::li/span[1]"
		TestObject statusOptionObj = new TestObject().addProperty("xpath", ConditionType.EQUALS, initialXpath)
		WebElement statusOption = WebUiCommonHelper.findWebElement(statusOptionObj, 10)
		statusOption.click()

		// Click Apply
		TestObject applyBtn = new TestObject('applyBtn')
				.addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'bottombar-buttons')]/span[2]")
		WebUI.waitForElementVisible(applyBtn, 5)
		WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUiCommonHelper.findWebElement(applyBtn, 5)))
		WebUI.delay(10)

		// Validate filtered results
		List<WebElement> filteredItems = WebUiCommonHelper.findWebElements(
				new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody//tr"),
				10)

		if (filteredItems.isEmpty()) {
			WebUI.comment("⚠ No reports available for the selected status: '${RStatus}'")
		} else {
			for (int i = 1; i <= filteredItems.size(); i++) {
				// Status check
				TestObject statusObj = new TestObject().addProperty("xpath", ConditionType.EQUALS,
						"//tbody//tr[" + i + "]//span[contains(@class,'reportStatusComponent_text')]")
				String actualStatus = WebUI.getText(statusObj).trim()

				if (!actualStatus.equalsIgnoreCase(RStatus)) {
					WebUI.comment("❌ Row ${i}: Expected status '${RStatus}', but found '${actualStatus}'")
					flag = false
				}

				// Assigned-To check
				TestObject assignedToObj = new TestObject().addProperty("xpath", ConditionType.EQUALS,
						"//tbody//tr[" + i + "]//input[@id='assigned_to']")
				String assignedValue = WebUI.getAttribute(assignedToObj, 'value', FailureHandling.OPTIONAL)?.trim()

				if (RStatus.equalsIgnoreCase("Under Review")) {
					if (!assignedValue || assignedValue.isEmpty()) {
						WebUI.comment("❌ Row ${i}: Assigned-To is empty for status 'Under Review'")
						flag = false
					}
				}
				else if (RStatus.equalsIgnoreCase("To Be Reviewed")) {
					// For "To Be Reviewed", assigned_to can be empty or selected
					WebUI.comment("ℹ Row ${i}: Assigned-To = '${assignedValue}' for 'To Be Reviewed'")
				}
			}

			if (flag) {
				WebUI.comment("✅ All reports match the expected status and Assigned-To conditions.")
			}
		}

		// Clear Filter
		TestObject clearBtn = new TestObject('clearBtn').addProperty('xpath', ConditionType.EQUALS, "//div[@class='clear-filter-btn']/button")
		boolean exists = WebUI.waitForElementPresent(clearBtn, 5, FailureHandling.OPTIONAL)
		if (exists) {
			WebUI.click(clearBtn)
			WebUI.comment("✅ Clear Filter clicked")
		} else {
			WebUI.comment("⚠ Clear Filter not enabled")
			flag = false
		}

		WebUI.delay(2)

		// Check row count after reset
		List<WebElement> afterResetReports = WebUiCommonHelper.findWebElements(
				new TestObject().addProperty("xpath", ConditionType.EQUALS, "//div[@id='reportListingTable']//tr"),
				10)
		int totalAfterReset = afterResetReports.size()

		if (totalAfterReset >= totalBeforeFilter) {
			WebUI.comment("✅ Filter reset verified. Rows after reset: ${totalAfterReset}")
		} else {
			WebUI.comment("❌ Filter reset failed. Expected ≥ ${totalBeforeFilter}, got ${totalAfterReset}")
			flag = false
		}

		return flag
	}
	@Keyword
	def verifyCombineFilters(String status, String assignedReviewer) {
		boolean flag = true

		// Open Filter Panel
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))
		WebUI.delay(1)

		// Apply Status Filter
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Status'))
		WebUI.delay(1)
		String statusXpath = "//div[contains(@class,'statusPage_container-body')]//ul/li//span[text()='${status}']/parent::li/span[1]"
		WebUiCommonHelper.findWebElement(new TestObject().addProperty('xpath', ConditionType.EQUALS, statusXpath), 10).click()

		// Apply Assigned To Filter
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Assigned to'))
		WebUI.delay(1)
		String reviewerXpath = "//div[contains(@class,'assignedToPage_container-body')]//li/span[contains(text(),'${assignedReviewer}')]/parent::li/span[1]"

		WebUiCommonHelper.findWebElement(new TestObject().addProperty('xpath', ConditionType.EQUALS, reviewerXpath), 10).click()

		// Apply Date Range Filter
		//WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_DateRange'))
		//WebUI.setText(findTestObject('Object Repository/Page_PBS/input_FromDate'), startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
		//	WebUI.setText(findTestObject('Object Repository/Page_PBS/input_ToDate'), endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))

		// Click Apply
		TestObject applyBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//div[contains(@class,'bottombar-buttons')]/span[2]")
		WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(WebUiCommonHelper.findWebElement(applyBtn, 5)))
		WebUI.delay(4)

		// Validate report list
		List<WebElement> reports = WebUiCommonHelper.findWebElements(new TestObject().addProperty('xpath', ConditionType.EQUALS, "//tbody//tr"), 10)

		if (reports.isEmpty()) {
			WebUI.comment("✅ No reports found for given filters. (Status: ${status}, Reviewer: ${assignedReviewer}, Date Range: ${startDate} to ${endDate})")
			return true
		}

		for (WebElement row : reports) {
			String rowStatus = row.findElement(By.xpath(".//span[contains(@class,'reportStatusComponent_text')]")).getText().trim()
			String rowReviewer = row.findElement(By.xpath(".//input[@id='assigned_to']")).getAttribute('value').trim()

			if (!rowStatus.equalsIgnoreCase(status)) {
				WebUI.comment("❌ Status mismatch. Expected: '${status}', Found: '${rowStatus}'")
				flag = false
			}

			if (!rowReviewer.equalsIgnoreCase(assignedReviewer)) {
				WebUI.comment("❌ Reviewer mismatch. Expected: '${assignedReviewer}', Found: '${rowReviewer}'")
				flag = false
			}
		}

		if (flag) {
			WebUI.comment("✅ All filters verified successfully.")
		}

		return flag
	}

	@Keyword
	def boolean verifyAllFiltersApplied(String startDate, String endDate, List<String> assignedUsers, List<String> statuses) {
		boolean flag = true

		// Open the Filters
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/filter_button'))

		/** SCAN DATE FILTER **/
		//WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_DateRange'))

		selectScanDateRange(startDate, endDate)

		/** ASSIGNED TO **/

		WebUI.delay(5)
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Assigned to'))
		for (String user : assignedUsers) {
			TestObject userCheckbox = new TestObject().addProperty("xpath", ConditionType.EQUALS,
					"//span[contains(text(),'${user}')]/preceding-sibling::span/input[@type='checkbox']")
			WebUI.click(userCheckbox)
		}

		/** STATUS **/
		WebUI.delay(5)
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_Status'))
		for (String status : statuses) {
			TestObject statusCheckbox = new TestObject().addProperty("xpath", ConditionType.EQUALS,
					"//span[contains(text(),'${status}')]/preceding-sibling::span/input[@type='checkbox']")
			WebUI.click(statusCheckbox)
		}

		/** APPLY **/
		TestObject applyBtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, "//span[text()='Apply']")
		WebUI.click(applyBtn)
		WebUI.delay(5)

		/** VERIFICATION **/
		List<WebElement> rows = WebUiCommonHelper.findWebElements(
				new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody/tr"),10)



		if (rows.isEmpty()) {
			WebUI.comment("⚠ No data after applying filters.")
			return true
		}

		for (int i = 1; i <= rows.size(); i++) {
			// Assigned To
			TestObject assignedCell = new TestObject().addProperty("xpath", ConditionType.EQUALS,
					"//tbody/tr[${i}]/td[5]")
			String actualUser = WebUI.getText(assignedCell).trim()
			if (!assignedUsers.contains(actualUser)) {
				WebUI.comment("❌ Row ${i} has unexpected Assigned To: ${actualUser}")
				flag = false
			}

			// Status
			TestObject statusCell = new TestObject().addProperty("xpath", ConditionType.EQUALS,
					"//tbody/tr[${i}]//span[contains(@class,'reportStatusComponent_text')]")
			String actualStatus = WebUI.getText(statusCell).trim()
			if (!statuses.contains(actualStatus)) {
				WebUI.comment("❌ Row ${i} has unexpected Status: ${actualStatus}")
				flag = false
			}

			// Optional: You can also validate scan date from the 2nd or 3rd column if visible
		}

		return flag
	}
	private def selectScanDateRange(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy")
		Calendar startCal = Calendar.getInstance()
		Calendar endCal = Calendar.getInstance()
		startCal.setTime(sdf.parse(startDate))
		endCal.setTime(sdf.parse(endDate))

		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_DateRange'))
		WebUI.delay(1)
		selectDateFromCalendar(startCal)
		if (!startDate.equals(endDate)) {
			selectDateFromCalendar(endCal)
		}
	}






	@Keyword
	def verifySlideIdSearch() {
		boolean flag = true

		String fullSlideId = getFirstAvailableSlideId()
		if (fullSlideId == null || fullSlideId.isEmpty()) {
			WebUI.comment("❌ No Slide ID found to test.")
			return false
		}

		// Prepare different test cases
		Map<String, String> testCases = [
			"First 2 characters"   : fullSlideId.take(2),
			"Last 1 character"     : fullSlideId[-1],
			"Last 2 characters"    : fullSlideId.takeRight(2),
			"First 1 character"    : fullSlideId[0],
			"Special character"    : "@#&S",
			"Invalid character"    : "ZZZZZZ"
		]

		TestObject searchBox = findTestObject("Object Repository/Report_Listing/Page_PBS/search_input_text")

		testCases.each { description, input ->
			try {
				WebUI.comment("🔍 Testing: ${description} with input '${input}'")
				WebUI.setText(searchBox, input)
				WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
				WebUI.delay(5)

				TestObject tableRows = new TestObject().addProperty("xpath", ConditionType.EQUALS,
						"//tbody[contains(@class,'MuiTableBody-root')]/tr")
				List<WebElement> results = WebUiCommonHelper.findWebElements(tableRows, 5)

				if (description.contains("Invalid") || description.contains("Special")) {
					if (results.size() == 0) {
						WebUI.comment("✅ [${description}] — No results as expected.")
					} else {
						WebUI.comment("❌ [${description}] — Expected no results, found ${results.size()}")
						flag = false
					}
				} else {
					boolean matched = results.any {
						it.findElement(By.xpath(".//td[3]")).getText().contains(input)
					}
					if (matched) {
						WebUI.comment("✅ [${description}] — Slide ID contains '${input}'")
					} else {
						WebUI.comment("❌ [${description}] — Slide ID does not contain '${input}'")
						flag = false
					}
				}
			} catch (Exception e) {
				WebUI.comment("⚠️ Error during '${description}': ${e.message}")
				WebUI.takeScreenshot("Screenshots/${description.replaceAll(' ', '_')}.png")
				flag = false
			} finally {
				// Always clear the field after each test
				WebUI.clearText(searchBox)
				WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
				WebUI.delay(2)
			}
		}

		// Final Clear Verification
		try {
			WebUI.setText(searchBox, fullSlideId.take(2))
			WebUI.delay(2)
			WebUI.clearText(searchBox)
			WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
			WebUI.delay(2)

			TestObject resetRows = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody//tr")
			List<WebElement> resetList = WebUiCommonHelper.findWebElements(resetRows, 10)

			if (resetList.size() > 0) {
				WebUI.comment("✅ Search cleared — Report list reset with ${resetList.size()} entries.")
			} else {
				WebUI.comment("❌ Search clear failed — No entries after clearing.")
				flag = false
			}
		} catch (Exception e) {
			WebUI.comment("⚠️ Final clear verification failed: ${e.message}")
			flag = false
		}

		WebUI.clearText(searchBox)
		WebUI.refresh();
		return flag
	}



	@Keyword
	def verifySearchClearFunctionality() {
		boolean result = true

		String fullSlideId = getFirstAvailableSlideId()
		if (fullSlideId == null || fullSlideId.isEmpty()) {
			WebUI.comment("❌ No Slide ID found to test.")
			return false
		}

		TestObject searchBox = findTestObject("Object Repository/Report_Listing/Page_PBS/search_input_text")

		// Step 1: Enter partial text and perform search
		WebUI.setText(searchBox, fullSlideId.take(2))
		WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
		WebUI.delay(3)

		TestObject filteredRows = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody//tr")
		List<WebElement> filteredResults = WebUiCommonHelper.findWebElements(filteredRows, 5)

		if (filteredResults.size() == 0) {
			WebUI.comment("❌ Search did not return any results, cannot verify clear.")
			return false
		}
		WebUI.comment("✅ Search returned ${filteredResults.size()} filtered results.")

		// Step 2: Clear the search
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/button_PBS_X_search'))
		//WebUI.clearText(searchBox)
		WebUI.sendKeys(searchBox, Keys.chord(Keys.ENTER))
		WebUI.delay(3)

		List<WebElement> resetResults = WebUiCommonHelper.findWebElements(filteredRows, 5)

		if (resetResults.size() > filteredResults.size()) {
			WebUI.comment("✅ Search clear successful — Report list expanded to ${resetResults.size()} entries.")
		} else {
			WebUI.comment("❌ Search clear failed — Still showing ${resetResults.size()} entries.")
			result = false
		}

		return result
	}
	private String getFirstAvailableSlideId() {
		TestObject firstSlideCell = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//tbody//tr[1]/td[3]")
		if (WebUI.waitForElementVisible(firstSlideCell, 5)) {
			return WebUI.getText(firstSlideCell).trim()
		}
		return null
	}
}

