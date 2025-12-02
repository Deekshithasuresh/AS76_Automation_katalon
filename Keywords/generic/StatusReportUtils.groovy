package generic

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.text.SimpleDateFormat

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class StatusReportUtils {

	@Keyword
	Map<String, Integer> getStatusWiseReportCount(List<String> statusList, Date thresholdDate) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver

		// Format: 06-Aug-2025, 09:46 AM
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy, hh:mm a", Locale.ENGLISH)

		Map<String, Integer> statusReportCounts = [:]

		for (String status : statusList) {
			// 1) Select dropdown option
			WebUI.click(findTestObject('Object Repository/Data management/StatusDropdown'))
			TestObject dropdownOption = new TestObject("dynamicDropdownOption")
			dropdownOption.addProperty("xpath", ConditionType.EQUALS,
					"//div[contains(@class,'reportTypeComponent_report') ]//span[text()='" + status + "']")
			WebUI.click(dropdownOption)

			WebUI.delay(2)

			// 2) Scroll container to load all rows
			String scrollSelector = "#reportListingTable > div > div"
			int scrollStep = 300
			int delaySeconds = 1
			int maxTries = 100
			int tries = 0

			while (tries < maxTries) {
				def result = js.executeScript("""
                    const el = document.querySelector(arguments[0]);
                    const before = el.scrollTop;
                    el.scrollBy(0, arguments[1]);
                    return {
                        scrollTop: el.scrollTop,
                        scrollHeight: el.scrollHeight,
                        clientHeight: el.clientHeight,
                        before: before
                    };
                """, scrollSelector, scrollStep)

				long scrollTop = result.scrollTop
				long scrollHeight = result.scrollHeight
				long clientHeight = result.clientHeight
				long before = result.before

				KeywordUtil.logInfo("Scroll position: ${scrollTop} / ${scrollHeight - clientHeight}")
				WebUI.delay(delaySeconds)

				if (scrollTop >= (scrollHeight - clientHeight - 10)) {
					KeywordUtil.logInfo("Reached bottom of scroll container.")
					break
				}
				if (scrollTop == before) {
					KeywordUtil.logInfo("ScrollTop unchanged. Possibly stuck. Breaking.")
					break
				}
				tries++
			}

			// 3) Find all report dates
			List<WebElement> dateElements = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Data management/reportDateColumn'), 10)
			int count = 0

			for (WebElement el : dateElements) {
				String originalDateText = el.getText().trim()

				// ✅ Clean timezone like "(WAT)" or any suffix in parentheses
				String cleanedDateText = originalDateText.replaceAll("\\s*\\([^)]*\\)", "").trim()

				println "📅 Cleaned UI Report Date: '${cleanedDateText}'"

				// Find the row containing this date
				WebElement row = el.findElement(By.xpath("./parent::td/parent::tr"))

				// Optional: Handle bookmark (if needed)
				List<WebElement> filledBookmarks = row.findElements(By.xpath("./td/img[contains(@src, 'bookmark-filled')]"))

				if (!filledBookmarks.isEmpty()) {
					try {
						filledBookmarks.get(0).click()
						WebUI.delay(1)
						WebUI.waitForElementVisible(findPopupHeader(), 5)
						WebUI.click(findPopupButton('Confirm'))
					} catch (Exception e) {
						println "❌ Failed to click bookmark in row with date: ${originalDateText}"
					}
				} else {
					println "ℹ️ No bookmark found"
				}

				// Parse date and compare with threshold
				try {
					Date reportDate = sdf.parse(cleanedDateText)
					println "🕒 Parsed: ${sdf.format(reportDate)} | Threshold: ${sdf.format(thresholdDate)}"

					if (reportDate.before(thresholdDate)) {
						count++
						WebUI.delay(1)
					}
				} catch (Exception e) {
					println "⚠️ Error parsing scan date: ${cleanedDateText}"
				}
			}

			statusReportCounts[status] = count
			println "✅ Status: ${status}, Reports Older Than Retention: ${count}"
		}

		return statusReportCounts
	}

	private TestObject findPopupHeader() {
		String xp = "//span[contains(normalize-space(),'remove bookmark')]"
		return new TestObject('popupHeader').addProperty('xpath', ConditionType.EQUALS, xp)
	}

	private TestObject findPopupButton(String label) {
		String xp = "//div[contains(@class,'modal-actions')]//button[normalize-space() = '${label}']"
		return new TestObject('popupButton').addProperty('xpath', ConditionType.EQUALS, xp)
	}
}
