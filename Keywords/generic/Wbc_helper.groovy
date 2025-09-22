package generic

import java.time.Duration

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI





public class Wbc_helper {

	@Keyword
	def verifyMainWBCCountAndPercentage() {
		WebDriver driver = DriverFactory.getWebDriver()

		// Wait for WBC table to be visible
		TestObject table = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//table[contains(@class,'theame-table')]")
		WebUI.waitForElementVisible(table, 10)

		// Get total count and percent from Total row
		int totalCount = Integer.parseInt(driver.findElement(By.xpath("//td[text()='Total']/following-sibling::td[1]")).getText().trim())
		double totalPercent = Double.parseDouble(driver.findElement(By.xpath("//td[text()='Total']/following-sibling::td[2]")).getText().trim())

		WebUI.comment("📊 UI Total Count = ${totalCount}, UI Total % = ${totalPercent}")

		List<String> excludedKeywords = [
			"Total",
			"NRBC",
			"Smudge",
			"Degenerate",
			"Stain",
			"Unclassified",
			"Rejected"
		]

		List<WebElement> allRows = driver.findElements(By.xpath("//table[contains(@class,'theame-table')]/tbody/tr"))

		int sumCount = 0
		double sumPercent = 0.0

		for (WebElement row : allRows) {
			List<WebElement> cells = row.findElements(By.tagName("td"))
			if (cells.size() < 3) continue

				WebElement nameCell = cells[0]
			String name = nameCell.getText().trim()
			String countText = cells[1].getText().trim()
			String percentText = cells[2].getText().trim()

			// ✅ Skip if sub-cell (td class contains 'sub-row')
			if (nameCell.getAttribute("class")?.contains("sub-row")) {
				WebUI.comment("⏩ Skipping sub-cell: ${name}")
				continue
			}

			// ✅ Skip if it's in excluded list
			if (excludedKeywords.any { name.contains(it) } ||
					countText == '-' || countText == '' ||
					percentText == '-' || percentText == '') {
				WebUI.comment("⏩ Skipping empty/excluded row: ${name}")
				continue
			}

			try {
				int count = Integer.parseInt(countText)
				double percent = Double.parseDouble(percentText)

				sumCount += count
				sumPercent += percent

				WebUI.comment("✅ Counted: ${name} → Count=${count}, %=${percent}")
			} catch (Exception e) {
				WebUI.comment("⚠️ Failed to parse count or % for row: ${name} — ${e.message}")
			}
		}

		WebUI.comment("🧮 Summed Count = ${sumCount}, Summed % = ${sumPercent.round(3)}")

		assert sumCount == totalCount : "❌ Count mismatch! UI = ${totalCount}, Calculated = ${sumCount}"
		assert Math.abs(sumPercent - totalPercent) < 0.5 : "❌ Percentage mismatch! UI = ${totalPercent}, Calculated = ${sumPercent.round(3)}"

		WebUI.comment("🎉 Main WBC summary verified successfully (excluding sub-cells and unwanted rows).")
	}



	static boolean isInteger(String str) {
		try {
			Integer.parseInt(str)
			return true
		} catch (NumberFormatException e) {
			return false
		}
	}

	static boolean isDouble(String str) {
		try {
			Double.parseDouble(str)
			return true
		} catch (NumberFormatException e) {
			return false
		}
	}


	@Keyword
	def verifyScrollOptionForPatches(String patchesContainerXPath, String scrollableElementXPath, int thresholdPatchCount) {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

		try {
			// 1. Wait for the patches container to be visible
			WebElement patchesContainer = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(patchesContainerXPath)))

			// 2. Get all patch elements
			List<WebElement> patchElements = patchesContainer.findElements(By.xpath("./div[contains(@class,'Card patches-container')]")) // Adjust XPath if needed

			int patchCount = patchElements.size()
			WebUI.comment("Total Patches Found: ${patchCount}")

			// 3. Check if the patch count is less than the threshold
			if (patchCount < thresholdPatchCount) {
				// 4. Verify the scroll option is NOT present
				boolean scrollableElementPresent = WebUI.verifyElementNotPresent(new TestObject().addProperty("xpath", ConditionType.EQUALS, scrollableElementXPath), 2) // Adjust XPath and timeout
				WebUI.verifyTrue(scrollableElementPresent, "✅ Scroll option is NOT present as expected (patch count < ${thresholdPatchCount})")
			} else {
				// 4. Verify the scroll option IS present
				boolean scrollableElementPresent = WebUI.verifyElementPresent(new TestObject().addProperty("xpath", ConditionType.EQUALS, scrollableElementXPath), 2) // Adjust XPath and timeout
				WebUI.verifyTrue(scrollableElementPresent, "✅ Scroll option IS present (patch count >= ${thresholdPatchCount})")
			}
		} catch (Exception e) {
			WebUI.comment("❌ Error during scroll option verification: " + e.getMessage())
			throw e
		}
	}



	@Keyword
	def validateNLR(double neutrophilCount, double lymphocyteCount, String expectedRoundedNLR) {
		String calculatedNLRString

		// If expectedRoundedNLR is "NA", and either count is 0, then we expect "NA"
		if (expectedRoundedNLR == "NA" && (lymphocyteCount == 0 || neutrophilCount == 0)) {
			calculatedNLRString = "NA"
			WebUI.comment("⚠️ Expected NLR is 'NA', and a count is zero. NLR is considered 'NA'.")
		} else if (lymphocyteCount == 0) {
			WebUI.comment("❌ Invalid scenario: Lymphocyte count is 0, but expected NLR is not 'NA'. This might cause division by zero or unexpected results.")
			calculatedNLRString = "NA_ERROR_OR_UNDEFINED" // Indicate an unexpected state
		}
		else {
			// Perform calculation if neither count is zero
			double actualNLR = neutrophilCount / lymphocyteCount
			calculatedNLRString = String.format("%.1f", actualNLR)
			WebUI.comment("Calculated NLR: ${calculatedNLRString} for Neutrophils: ${neutrophilCount}, Lymphocytes: ${lymphocyteCount}")
		}

		// Assert the calculated/assigned NLR against the expected value
		assert calculatedNLRString == expectedRoundedNLR : "❌ Expected NLR: ${expectedRoundedNLR}, but got: ${calculatedNLRString}"
		WebUI.comment("✅ Correct NLR: ${calculatedNLRString} matches expected: ${expectedRoundedNLR}")
	}





	@Keyword
	def verifySplitViewOnDoubleClick() {
		WebDriver driver = DriverFactory.getWebDriver()

		// Locate a patch element (example locator — update as needed)
		WebElement patch = driver.findElement(By.xpath("//div[contains(@class,'patch-thumbnail')]"))

		// Perform double-click using Actions
		Actions action = new Actions(driver)
		action.doubleClick(patch).perform()

		WebUI.delay(2)

		// Verify split view container is displayed
		boolean isSplitViewVisible = WebUI.verifyElementPresent(
				FindTestObject('Object Repository/SplitViewContainer'),
				5, FailureHandling.OPTIONAL
				)

		if (isSplitViewVisible) {
			WebUI.comment("✅ Split view opened successfully on double-click.")
		} else {
			WebUI.comment("❌ Split view did not appear.")
		}
	}


	@Keyword
	def verifyRenderedAndOriginalPatchSize() {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver

		// Select the first WBC cell with valid count
		List<WebElement> wbcRows = driver.findElements(By.xpath(
				"//div[contains(@class,'cell-table-tab')]//tr[td[1][not(contains(., 'Total'))] and td[2][text() != '-' and normalize-space(text()) != '']]"
				))

		if (wbcRows.size() == 0) {
			WebUI.comment("❌ No WBC rows with valid count found.")
			return
		}

		wbcRows[0].click()
		WebUI.delay(2)

		// JS: Fetch rendered & original size for each patch image
		String jsGetSizes = """
            const imgs = document.querySelectorAll('img[src*="/rendered"]');
            return Array.from(imgs).map(img => ({
                renderedWidth: img.offsetWidth,
                renderedHeight: img.offsetHeight,
                originalWidth: img.naturalWidth,
                originalHeight: img.naturalHeight,
                src: img.src
            }));
        """

		def result = js.executeScript(jsGetSizes)

		if (result instanceof List && result.size() > 0) {
			for (int i = 0; i < result.size(); i++) {
				def img = result[i]
				int rWidth = img.renderedWidth as int
				int rHeight = img.renderedHeight as int
				int oWidth = img.originalWidth as int
				int oHeight = img.originalHeight as int
				String src = img.src

				boolean renderedCorrect = (rWidth >= 160 && rWidth <= 162) && (rHeight >= 160 && rHeight <= 162)
				boolean originalValid = (oWidth > 0 && oHeight > 0)

				WebUI.comment("🧪 Patch #${i + 1}")
				WebUI.comment("   ✅ Rendered: ${rWidth}x${rHeight} px ${renderedCorrect ? '' : '(❌ Unexpected size!)'}")
				WebUI.comment("   ✅ Original: ${oWidth}x${oHeight} px ${originalValid ? '' : '(❌ Not loaded properly!)'}")
				WebUI.comment("   🔗 src: ${src}")

				if (!(rWidth >= 160 && rWidth <= 162) || !(rHeight >= 160 && rHeight <= 162)) {
					throw new StepFailedException("❌ Patch #${i + 1} has incorrect rendered size: ${rWidth}x${rHeight}px (expected 160x160)")
				}

				if (oWidth == 0 || oHeight == 0) {
					throw new StepFailedException("❌ Patch #${i + 1} image not loaded correctly. Intrinsic size is 0.")
				}
			}
		} else {
			WebUI.comment("❌ No rendered patch images found.")
		}
	}



	@Keyword
	Map<String, String> getWbcDifferentialFromUI() {
		WebDriver driver = DriverFactory.getWebDriver()
		Map<String, String> wbcData = [:]

		List<WebElement> rows = driver.findElements(By.xpath("//table[contains(@class, 'theame-table')]//tbody//tr[not(contains(@class,'report-highlights'))]"))

		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"))
			if (cells.size() >= 2) {
				String cellType = cells[0].getText().trim()
				String value = cells[2].getText().trim()
				if (cellType && value && value != '-') {
					wbcData[cellType] = value
				}
			}
		}
		return wbcData
	}

	@Keyword
	Map<String, String> getRbcGradesFromUI() {
		WebDriver driver = DriverFactory.getWebDriver()
		Map<String, String> rbcData = [:]
	
		// All RBC rows
		List<WebElement> rows = driver.findElements(By.xpath("//div[contains(@class, 'cell-row')]"))
	
		for (WebElement row : rows) {
			try {
				// Cell type (fallback to plain text if 'significant' not present)
				String cellType = ""
				try {
					cellType = row.findElement(By.xpath(".//div[contains(@class,'significant') or contains(@class,'cell-name')]")).getText().trim()
				} catch (Exception ignored) {
					cellType = row.getText().split("\n")[0].trim()  // fallback
				}
	
				// Grade radio buttons
				List<WebElement> gradeOptions = row.findElements(
					By.xpath(".//div[contains(@class,'grade-div')]//span[contains(@class,'MuiButtonBase-root')]")
				)
	
				String selectedGrade = ""
				for (int i = 0; i < gradeOptions.size(); i++) {
					if (gradeOptions[i].getAttribute("class").contains("Mui-checked")) {
						// Map index → grade label
						switch (i) {
							case 0: selectedGrade = "0"; break
							case 1: selectedGrade = "1"; break
							case 2: selectedGrade = "2"; break
							case 3: selectedGrade = "3"; break
							default: selectedGrade = i.toString()
						}
						break
					}
				}
	
				if (cellType && selectedGrade) {
					rbcData[cellType] = selectedGrade
				} else {
					WebUI.comment("⚠️ No grade found for RBC cell: ${cellType}")
				}
	
			} catch (Exception e) {
				WebUI.comment("⚠️ Skipping row due to: ${e.message}")
			}
		}
	
		return rbcData
	}
	
	
	
	
}

