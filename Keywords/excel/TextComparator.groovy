package excel

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class TextComparator {

	/**
	 * Compare content of a TestObject text against a file, line by line.
	 * Ignores cosmetic lines (empty lines, separators like -----).
	 *
	 * @param obj   TestObject of the UI element
	 * @param filePath   Path to expected text file
	 */
	@Keyword
	def verifyContentAgainstFile(TestObject obj, String filePath) {
		String appContent = WebUI.getText(obj)
		String expectedContent = new File(filePath).text

		// Normalize + filter
		List<String> actualLines = appContent.split("\\r?\\n")
				.collect { it.trim() }
				.findAll { it && !it.matches("-{5,}") }  // ignore separators + blanks

		List<String> expectedLines = expectedContent.split("\\r?\\n")
				.collect { it.trim() }
				.findAll { it && !it.matches("-{5,}") }

		int maxLines = Math.max(actualLines.size(), expectedLines.size())
		boolean mismatchFound = false

		for (int i = 0; i < maxLines; i++) {
			String actualLine = (i < actualLines.size()) ? actualLines[i] : "<MISSING>"
			String expectedLine = (i < expectedLines.size()) ? expectedLines[i] : "<MISSING>"

			if (!actualLine.equalsIgnoreCase(expectedLine)) {
				println "❌ Difference at line ${i+1}:"
				println "   Actual   : ${actualLine}"
				println "   Expected : ${expectedLine}"
				mismatchFound = true
			}
		}

		if (!mismatchFound) {
			println "✅ All lines match"
			KeywordUtil.markPassed("UI content matches expected file.")
		} else {
			KeywordUtil.markFailed("UI content mismatched. Check console log for details.")
		}
	}




	@Keyword
	def verifyPopupTextRegex(List<String> expectedPatterns) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebElement popup = driver.findElement(By.cssSelector("div[role='presentation']"))
		String fullText = popup.getText().trim()
	
		List<String> actualLines = fullText.readLines().collect { it.trim() }.findAll { it }
	
		assert actualLines.size() == expectedPatterns.size() :
			"Expected ${expectedPatterns.size()} lines but found ${actualLines.size()}"
	
		for (int i = 0; i < expectedPatterns.size(); i++) {
			String regex = expectedPatterns[i]
			assert actualLines[i] ==~ regex :
				"Line ${i+1} mismatch. Expected pattern: '${regex}' but found: '${actualLines[i]}'"
		}
	
		WebUI.comment("✅ Popup text verification (with regex) passed.")
	}
	


}
