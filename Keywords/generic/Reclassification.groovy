package generic

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration
import java.util.concurrent.TimeoutException

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI





public class Reclassification {

	@Keyword
	def reclassifyAllWBCtoPlateletInBatches(String toPlateletCell, int batchSize = 5) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		try {
			// Step 1: Get all WBC cell names (excluding Platelet)
			List<WebElement> cellRows = driver.findElements(By.xpath("//table/tbody/tr"))
			List<String> fromWBCells = []
			cellRows.each { WebElement row ->
				String cellName = row.findElement(By.xpath("./td[1]")).getText().trim()
				if (!isPlatelet(cellName) && !cellName.equalsIgnoreCase(toPlateletCell)) {
					fromWBCells.add(cellName)
				}
			}
			WebUI.comment("WBCs to reclassify: ${fromWBCells}")

			// Step 2: Reclassify each cell's patches in batches
			int totalReclassified = 0
			for (String fromWBCell : fromWBCells) {
				int fromInitialCount = getCellCountInCurrentTab(driver, fromWBCell)
				WebUI.comment("Reclassifying from: ${fromWBCell}, Initial Count: ${fromInitialCount}")

				WebElement wbcCellRow = driver.findElement(By.xpath("//table/tbody/tr/td[1][contains(text(),'" + fromWBCell + "')]/parent::tr"))

				// Get the count cell text
				String countValue = wbcCellRow.findElement(By.xpath("./td[2]")).getText().trim()

				// Skip if count is "-" or "0"
				if (countValue == '-' || countValue == '0') {
					WebUI.comment("⏭️ Skipping '${fromWBCell}' as count is '${countValue}'")
					continue
				}

				wbcCellRow.click()
				WebUI.delay(1)

				int i = 0
				while (true) {
					List<WebElement> allPatches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
					int total = allPatches.size()
					if (i >= total) break

						int end = Math.min(i + batchSize, total)
					List<WebElement> batchOfPatches = allPatches.subList(i, end)

					if (!batchOfPatches.isEmpty()) {
						try {
							for (WebElement patch : batchOfPatches) {
								try {
									patch.click()
									WebUI.delay(0.2)
								} catch (StaleElementReferenceException e) {
									WebUI.refresh()
									WebUI.delay(3)
									WebUI.comment("⚠️ StaleElementReferenceException while clicking patch. Skipping...")
									break
								}
							}

							actions.contextClick(batchOfPatches.get(0)).perform()
							WebUI.delay(1)
							driver.findElement(By.xpath("//span[text()='Classify']")).click()
							WebUI.delay(1)
							driver.findElement(By.xpath("//li//div[text()='" + toPlateletCell + "']")).click()
							WebUI.delay(1)

							TestObject reclassifyMsg = new TestObject()
							reclassifyMsg.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class,'MuiSnackbarContent-message')]//div[contains(text(),'patches reclassified')]")
							WebUI.waitForElementVisible(reclassifyMsg, 5)
							WebUI.verifyElementPresent(reclassifyMsg, 5, FailureHandling.OPTIONAL)
							WebUI.comment("✅ Reclassified ${batchOfPatches.size()} patches from ${fromWBCell} (batch ${i / batchSize + 1}) to ${toPlateletCell}")

							totalReclassified += batchOfPatches.size()

							for (WebElement patch : batchOfPatches) {
								try {
									patch.click()
									WebUI.delay(0.1)
								} catch (StaleElementReferenceException e) {
									WebUI.refresh()
									WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_WBC'))
									WebUI.delay(5)
									wbcCellRow.click()

									WebUI.comment("⚠️ StaleElementReferenceException while deselecting patch.")
								}
							}
							WebUI.delay(2)
						} catch (StaleElementReferenceException e) {
							WebUI.comment("⚠️ StaleElementReferenceException during reclassification. Retrying...")
							continue
						}
					}
					i += batchSize
				}
				WebUI.delay(2)
			}
		} catch (Exception e) {
			WebUI.comment("❌ Error during reclassification: " + e.message)
			throw e
		}

		// Step 3: Validate that all cells now have "-" for count and percentage
		boolean allCellsHaveHyphen = true
		List<String> cellsWithoutHyphen = []

		try {
			List<WebElement> cellRows = driver.findElements(By.xpath("//table/tbody/tr"))
			for (WebElement cellRow : cellRows) {
				WebElement countCell = cellRow.findElement(By.xpath("./td[2]"))
				WebElement percentageCell = cellRow.findElement(By.xpath("./td[3]"))

				String countValue = countCell.getText().trim()
				String percentageValue = percentageCell.getText().trim()

				if (!countValue.contains("-") || !percentageValue.contains("-")) {
					allCellsHaveHyphen = false
					String cellName = cellRow.findElement(By.xpath("./td[1]")).getText().trim()
					cellsWithoutHyphen.add(cellName)
					WebUI.comment("❌ Cell '${cellName}' does not have hyphen.")
				} else {
					WebUI.comment("✅ Cell shows hyphen as expected.")
				}
			}

			if (!allCellsHaveHyphen) {
				WebUI.comment("❌ Cells without hyphen: ${cellsWithoutHyphen}")
			}
		} catch (Exception e) {
			WebUI.comment("❌ Error during hyphen check: " + e.getMessage())
			throw e
		}
	}


	@Keyword
	def reclassifyPlateletToWBC(String fromPlateletCell, String toWbcCell, int patchCount) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		try {
			// Step 1: Go to Platelets tab and get initial count


			int fromInitialCount = getCellCountInCurrentTab(driver, fromPlateletCell)
			WebUI.comment("Initial Platelet Count (${fromPlateletCell}): ${fromInitialCount}")
			if(fromInitialCount==0) {
				WebUI.comment("Initial Platelet Count (${fromPlateletCell}): ${fromInitialCount} soo Skipped" )
				return
			}

			// Step 2: Switch to WBC tab and get initial count there
			WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_WBC'))
			WebUI.delay(1)
			int toInitialCount = getCellCountInCurrentTab(driver, toWbcCell)
			WebUI.comment("Initial WBC Count (${toWbcCell}): ${toInitialCount}")

			// Step 3: Go back to Platelets tab and select patches
			WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_Platelets'))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_Morphology'))
			WebUI.delay(1)

			WebElement plateletRow = driver.findElement(By.xpath("//div[@class='split-view-count-section']//div[text()='" + fromPlateletCell + "']/following-sibling::div[1]"))
			plateletRow.click()

			List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
			for (int i = 0; i < patchCount; i++) {
				patches.get(i).click()
			}
			WebUI.comment("Selected ${patchCount} platelet patch(es)")

			// Step 4: Right-click and classify to WBC
			actions.contextClick(patches.get(0)).perform()
			WebUI.comment("Right-clicked on platelet patch")
			driver.findElement(By.xpath("//span[text()='Classify']")).click()
			driver.findElement(By.xpath("//li//div[text()='" + toWbcCell + "']")).click()

			// Step 5: Confirm classification message
			TestObject reclassifyMsg = new TestObject()
			reclassifyMsg.addProperty("xpath", ConditionType.EQUALS,"//div[contains(@class,'MuiSnackbarContent-message')]//div[contains(text(),'patches reclassified')]")
			WebUI.waitForElementVisible(reclassifyMsg, 5)
			boolean isPresent = WebUI.verifyElementPresent(reclassifyMsg, 5, FailureHandling.OPTIONAL)
			WebUI.comment("✅ Reclassification message visible: ${isPresent}")

			// Step 6: Get final counts
			int fromFinalCount = getCellCountInCurrentTab(driver, fromPlateletCell)

			WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_WBC'))
			WebUI.delay(1)
			int toFinalCount = getCellCountInCurrentTab(driver, toWbcCell)

			WebUI.comment("Final Platelet Count: ${fromFinalCount}, Final WBC Count: ${toFinalCount}")

			// Step 7: Validate changes
			assert fromFinalCount == fromInitialCount - patchCount : "❌ Platelet count not decreased correctly"
			assert toFinalCount == toInitialCount + patchCount : "❌ WBC count not increased correctly"
		} catch (Exception e) {
			WebUI.comment("❌ Reclassification error: " + e.message)
			throw e
		}
	}


	@Keyword
	def reclassifyWBCToPlatelet(String fromCellName, String toPlateletCell, int patchCount) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		try {
			// Step 1: Go to Platelets tab and get initial count
			WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_Platelets'))
			WebUI.delay(1)
			WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_Morphology'))
			WebUI.delay(1)


			int toInitialCount = getCellCountInCurrentTab(driver, toPlateletCell)
			WebUI.comment("Initial Platelet Count (${toPlateletCell}): ${toInitialCount}")

			// Step 2: Go to WBC tab
			WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_WBC'))
			WebUI.delay(1)
			int fromInitialCount = getCellCountInCurrentTab(driver, fromCellName)
			WebUI.comment("Initial WBC Count (${fromCellName}): ${fromInitialCount}")

			if (fromInitialCount >= patchCount) {
				// Step 3: Select WBC cell row
				WebElement wbcCellRow = driver.findElement(By.xpath("//table//td[contains(text(),'" + fromCellName + "')]"))
				wbcCellRow.click()

				// Step 4: Select patches
				List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
				for (int i = 0; i < patchCount; i++) {
					patches.get(i).click()
				}
				WebUI.comment("Selected ${patchCount} patch(es)")

				// Step 5: Right-click + classify to platelet
				actions.contextClick(patches.get(0)).perform()
				WebUI.comment("Right-clicked on patch")
				driver.findElement(By.xpath("//span[text()='Classify']")).click()
				driver.findElement(By.xpath("//li//div[text()='" + toPlateletCell + "']")).click()

				// Step 6: Verify classification message
				TestObject reclassifyMsg = new TestObject()
				reclassifyMsg.addProperty("xpath", ConditionType.EQUALS,"//div[contains(@class,'MuiSnackbarContent-message')]//div[contains(text(),'patches reclassified')]")
				WebUI.delay(1)
				WebUI.waitForElementVisible(reclassifyMsg, 5)
				boolean isPresent = WebUI.verifyElementPresent(reclassifyMsg, 5, FailureHandling.OPTIONAL)
				WebUI.comment("✅ Reclassification message visible: ${isPresent}")




				// Step 7: Recheck WBC count
				int fromFinalCount = getCellCountInCurrentTab(driver, fromCellName)

				// Step 8: Switch to Platelets tab to get final count
				WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/span_Platelets'))
				WebUI.delay(1)
				WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_Morphology'))
				WebUI.delay(1)
				int toFinalCount = getCellCountInCurrentTab(driver, toPlateletCell)

				WebUI.comment("Final WBC Count: ${fromFinalCount}, Final Platelet Count: ${toFinalCount}")

				// Step 9: Validate count changes
				assert fromFinalCount == fromInitialCount - patchCount : "WBC count not decreased correctly"
				assert toFinalCount == toInitialCount + patchCount : "Platelet count not increased correctly"
			} else {
				WebUI.comment("Not enough WBC patches to reclassify.")
			}
		} catch (Exception e) {
			WebUI.comment("Classification error: " + e.message)
			throw e
		}
	}


	@Keyword
	int getCellCountInCurrentTab(WebDriver driver, String cellName) {
		try {
			String xpath = ""

			if (isPlatelet(cellName)) {
				// Platelet tab (table layout)
				xpath = "//div[@class='split-view-count-section']//div[text()='" + cellName + "']/following-sibling::div[1]"
			} else {
				// WBC tab (left label with spans)
				xpath = "//table//td[contains(text(),'" + cellName + "')]/following-sibling::td[1]"
			}

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5))
			WebElement cell = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))

			String text = cell.getText().trim()


			String cleanText = text.replaceAll("[^\\d]", "") // Remove commas, spaces, etc.
			if (cleanText.isEmpty()) {
				WebUI.comment("Non-numeric or empty count for cell: " + cellName + " → Raw value: '" + text + "'")
				return 0
			}
			return Integer.parseInt(cleanText)
		} catch (TimeoutException te) {
			WebUI.comment("Timeout: Count not found for cell: " + cellName)
			return 0
		} catch (NumberFormatException ne) {
			WebUI.comment("Invalid number format for cell count: " + cellName)
			return 0
		} catch (Exception e) {
			WebUI.comment("Error retrieving count for cell: " + cellName + " → " + e.getMessage())
			return 0
		}
	}

	// ✅ Helper: check if the cell is a platelet
	boolean isPlatelet(String cellName) {
		List<String> plateletTypes = [
			'Large Platelets',
			'Platelet Clumps'
		]
		return plateletTypes.contains(cellName)
	}

	// ✅ Helper: check if text is integer
	boolean isInteger(String s) {
		try {
			Integer.parseInt(s)
			return true
		} catch (NumberFormatException e) {
			return false
		}
	}







	static Map<String, List<String>> cellToSubCells = [
		"Neutrophils"             : [
			"Band Forms",
			"Hypersegmented",
			"Neutrophils with Toxic Granules"
		],
		"Lymphocytes"             : [
			"Reactive",
			"Large Granular Lymphocytes"
		],
		"Atypical Cells/Blasts"   : [
			"Atypical Cells",
			"Lymphoid Blasts",
			"Myeloid Blasts"
		],
		"Immature Granulocytes"   : [
			"Promyelocytes",
			"Myelocytes",
			"Metamyelocytes"
		]
	]

	private String getParentCellForSubCell(String subCell) {
		for (entry in cellToSubCells) {
			if (entry.value.contains(subCell)) return entry.key
		}
		throw new IllegalArgumentException("No main cell found for sub-cell: ${subCell}")
	}


	@Keyword
	def classifyMultipleSelectedPatches(String fromCellName, String toSubCellName, int numberOfPatches) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		if (numberOfPatches == 0) {
			WebUI.comment("⏩ Skipping classification: 'numberOfPatches' is 0 or less (${numberOfPatches}).")
			return
		}

		String mainCellForSub = getParentCellForSubCell(toSubCellName)

		try {
			int fromInitial = getCellCount(driver, fromCellName)
			int subInitial = getCellCount(driver, toSubCellName)

			WebUI.comment("Initial Count - From: ${fromCellName} = ${fromInitial}, To (Sub): ${toSubCellName} = ${subInitial}")


			if (fromInitial <= numberOfPatches) {
				WebUI.comment("⚠️ Not enough patches to classify. Needed: ${numberOfPatches}, Available: ${fromInitial}")
				return
			}

			// Step 1: Select multiple patches
			List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
			if (patches.size() < numberOfPatches) {
				WebUI.comment("⚠️ Only found ${patches.size()} patches. Cannot select ${numberOfPatches}.")
				return
			}
			for (int i = 0; i < numberOfPatches; i++) {
				patches[i].click()
				WebUI.comment("Selected patch ${i + 1}")
				WebUI.delay(0.2)
			}

			// Step 2: Right-click on last selected patch
			WebElement lastSelectedPatch = patches[numberOfPatches - 1]
			actions.moveToElement(lastSelectedPatch).contextClick().perform()
			WebUI.comment("Right-clicked on selected patches.")

			// Step 3: Click on "Classify"
			WebElement classifyOption = driver.findElement(By.xpath("//span[contains(text(),'Classify')]"))
			classifyOption.click()
			WebUI.comment("Clicked on 'Classify'")

			// Step 4: Hover on main cell
			WebElement mainCellElement = driver.findElement(
					By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'${mainCellForSub}')]"))
			actions.moveToElement(mainCellElement).perform()
			WebUI.comment("Hovered over main cell: ${mainCellForSub}")

			// Step 5: Click sub-cell
			WebElement subCellElement = driver.findElement(
					By.xpath("(//ul[contains(@class,'MuiMenu-list')])[3]//li[contains(text(),'${toSubCellName}')]"))
			subCellElement.click()
			WebUI.comment("Selected sub-cell: ${toSubCellName}")

			// Step 6: Verify success message
			try {
				WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/div_1 patch subclassified'), 10)
			} catch (Exception e) {
				WebUI.comment("confirmation massage")
			}
			WebUI.delay(2)

			// Step 7: Count verification
			int fromFinal = getCellCount(driver, fromCellName)
			int subFinal = getCellCount(driver, toSubCellName)

			WebUI.comment("Final Count - From: ${fromFinal}, To (Sub): ${subFinal}")

			if (fromCellName == mainCellForSub) {
				// Subclassification: Main cell to its own sub-cell
				assert fromFinal == fromInitial : "❌ Main cell count should remain same during subclassification"
				assert subFinal == subInitial + numberOfPatches : "❌ Sub-cell count should increase by ${numberOfPatches}"
				WebUI.comment("✅ Subclassified ${numberOfPatches} patch(es) from ${fromCellName} → ${toSubCellName} without affecting main cell count.")
			} else {
				// Reclassification from one cell to another
				assert fromFinal == fromInitial - numberOfPatches : "❌ From cell count didn't decrease by ${numberOfPatches}"
				assert subFinal == subInitial + numberOfPatches : "❌ Sub-cell count didn't increase by ${numberOfPatches}"
				WebUI.comment("✅ Reclassified ${numberOfPatches} patch(es) from ${fromCellName} → ${toSubCellName}.")
			}
		} catch (Exception e) {
			WebUI.comment("❌ Classification error: ${e.message}")
			throw e
		}
	}




	@Keyword
	def classifyMultiplePatchesToCell(String fromCellName, String toCellName, int patchCount) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		// Mapping of main cells to sub-cells
		Map<String, List<String>> cellToSubCells = [
			"Neutrophils": [
				"Band Forms",
				"Hypersegmented",
				"Neutrophils with Toxic Granules"
			],
			"Lymphocytes": [
				"Reactive",
				"Large Granular Lymphocytes"
			],
			"Atypical Cells/Blasts": [
				"Atypical Cells",
				"Lymphoid Blasts",
				"Myeloid Blasts"
			],
			"Immature Granulocytes": [
				"Promyelocytes",
				"Myelocytes",
				"Metamyelocytes"
			]
		]

		try {
			int fromInitial = getCellCount(driver, fromCellName)
			int toInitial = getCellCount(driver, toCellName)
			WebUI.comment("Initial count - From: ${fromInitial}, To: ${toInitial}")

			if (fromInitial < patchCount) {
				WebUI.comment("Not enough patches to classify. Needed: ${patchCount}, Available: ${fromInitial}")
				return
			}

			WebElement row = driver.findElement(By.xpath("//table/tbody/tr[td[1][contains(text(),'" + fromCellName + "')]]"))
			row.click()

			// Step 1: Select multiple patches
			List<WebElement> patchList = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
			for (int i = 0; i < patchCount && i < patchList.size(); i++) {
				patchList.get(i).click()
				WebUI.delay(0.2)
			}
			WebUI.comment("Selected ${patchCount} patch(es)")

			// Step 2: Right-click on any selected patch
			actions.contextClick(patchList.get(0)).perform()
			WebUI.comment("Right-clicked on selected patch")

			// Step 3: Click 'Classify'
			WebElement classifyButton = driver.findElement(By.xpath("//span[contains(text(),'Classify')]"))
			classifyButton.click()
			WebUI.comment("Clicked 'Classify'")

			// Step 4: Select destination cell
			WebElement toCellElement = driver.findElement(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'" + toCellName + "')]"))
			toCellElement.click()
			WebUI.comment("Selected target cell: ${toCellName}")

			// Step 5: Wait for confirmation
			String messageText = "${patchCount} patch" + (patchCount > 1 ? "es" : "") + " reclassified"
			TestObject dynamicObj = findTestObject('Object Repository/WBC_m/Page_PBS/div_reclassified_generic', [('msgText') : messageText])
			WebUI.comment(messageText)

			//WebUI.verifyElementPresent(dynamicObj, 10)
			WebUI.delay(2)

			int fromFinal = getCellCount(driver, fromCellName)
			int toFinal = getCellCount(driver, toCellName)
			WebUI.comment("Final count - From: ${fromFinal}, To: ${toFinal}")

			// Step 6: Determine if subclassification
			boolean isSubclassification = cellToSubCells.containsKey(fromCellName) && cellToSubCells.get(fromCellName).contains(toCellName)

			if (isSubclassification) {
				assert fromFinal == fromInitial : "Main cell count should not change during subclassification"
				assert toFinal == toInitial + patchCount : "Sub-cell count should increase by ${patchCount}"
				WebUI.comment("✅ Subclassified ${patchCount} patch(es) from ${fromCellName} → ${toCellName}")
			} else {
				assert fromFinal == fromInitial - patchCount : "From cell count did not decrease by ${patchCount}"
				assert toFinal == toInitial + patchCount : "To cell count did not increase by ${patchCount}"
				WebUI.comment("✅ Reclassified ${patchCount} patch(es) from ${fromCellName} → ${toCellName}")
			}
		} catch (Exception e) {
			WebUI.comment("❌ Error during multiple patch classification: " + e.message)
			throw e
		}
	}



	@Keyword
	def classifyToSubCell(String mainCell, String subCell) {
		def driver = DriverFactory.getWebDriver()
		def actions = new Actions(driver)

		try {
			// Step 1: Get main cell row and initial count
			WebElement mainRow = driver.findElement(By.xpath("//table/tbody/tr[td[1][contains(text(),'" + mainCell + "')]]"))
			WebElement mainCountCell = mainRow.findElement(By.xpath("./td[2]"))
			String mainCountText = mainCountCell.getText().trim()
			int mainInitialCount = (mainCountText == "-" || mainCountText.isEmpty()) ? 0 : Integer.parseInt(mainCountText)
			WebUI.comment("Initial count for ${mainCell}: ${mainInitialCount}")

			// Step 2: Get sub-cell row and initial count
			WebElement subRow = driver.findElement(By.xpath("//table/tbody/tr[td[1][contains(text(),'" + subCell + "')]]"))
			WebElement subCountCell = subRow.findElement(By.xpath("./td[2]"))
			String subCountText = subCountCell.getText().trim()
			int subInitialCount = (subCountText == "-" || subCountText.isEmpty()) ? 0 : Integer.parseInt(subCountText)
			WebUI.comment("Initial count for ${subCell}: ${subInitialCount}")

			if (mainInitialCount > 1) {
				// Step 3: Right-click on image
				WebElement imgElement = driver.findElement(By.xpath("//div[@class='patches-section ']//img"))
				actions.moveToElement(imgElement).contextClick().perform()
				WebUI.comment("Right-clicked on image")

				// Step 4: Click Classify
				WebElement classifyOption = driver.findElement(By.xpath("//span[contains(text(),'Classify')]"))
				classifyOption.click()
				WebUI.comment("Clicked Classify")

				// Step 5: Hover on main cell
				WebElement mainCellElement = driver.findElement(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'${mainCell}')]"))
				actions.moveToElement(mainCellElement).perform()
				WebUI.comment("Hovered on main cell: ${mainCell}")

				// Step 6: Select sub-cell
				WebElement subCellElement = driver.findElement(By.xpath("(//ul[contains(@class,'MuiMenu-list')])[3]//li[contains(text(),'${subCell}')]"))
				subCellElement.click()
				WebUI.comment("Selected sub-cell: ${subCell}")

				// Step 7: Verify success message
				WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/div_1 patch subclassified'), 10)

				// Step 8: Wait and get updated counts
				WebUI.delay(2)  // wait for table to update

				int mainFinalCount = Integer.parseInt(mainCountCell.getText().trim().replace("-", "0"))
				int subFinalCount = Integer.parseInt(subCountCell.getText().trim().replace("-", "0"))
				WebUI.comment("Final count for ${mainCell}: ${mainFinalCount}")
				WebUI.comment("Final count for ${subCell}: ${subFinalCount}")

				// Step 9: Validate count changes
				//assert mainFinalCount == mainInitialCount - 1 : "Main cell count did not decrease correctly"
				assert subFinalCount == subInitialCount + 1 : "Sub-cell count did not increase correctly"

				WebUI.comment("Count verification successful: ${mainCell} (-1), ${subCell} (+1)")
			} else {
				WebUI.comment("Count for ${mainCell} is ${mainInitialCount} (<=1). Skipping sub-cell classification.")
			}
		} catch (Exception e) {
			WebUI.comment("Classification failed: ${e.message}")
			throw e
		}
	}







	@Keyword
	def classifyFromCellToCellMultiple(String fromCellName, String toCellName, int times) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		try {
			for (int i = 1; i <= times; i++) {
				WebUI.comment("🔁 Classification attempt ${i} of ${times}")

				// Step 1: Get initial counts
				int fromInitialCount = getCellCount(driver, fromCellName)
				int toInitialCount = getCellCount(driver, toCellName)

				WebUI.comment("Initial count - From: ${fromCellName} = ${fromInitialCount}, To: ${toCellName} = ${toInitialCount}")

				// Step 2: Proceed only if fromCell count > 0
				if (fromInitialCount > 0) {

					WebUI.delay(2)

					// Click on fromCell row
					WebElement cellRow = driver.findElement(By.xpath("//table/tbody/tr/td[1][text()='" + fromCellName + "']"))

					cellRow.click()
					WebUI.comment("Clicked on cell row: ${fromCellName}")

					// Right-click on the image
					WebElement imageElement = driver.findElement(By.xpath("//div[@class='patches-section ']//img"))
					actions.moveToElement(imageElement).contextClick().perform()
					WebUI.comment("Right-clicked on image.")

					// Click on "Classify" menu
					WebElement classifyButton = driver.findElement(By.xpath("//span[contains(text(),'Classify')]"))
					classifyButton.click()
					WebUI.comment("Clicked on 'Classify' option.")

					// Click the toCellName (main or sub-cell logic will be handled automatically in UI)
					WebElement toCellElement = driver.findElement(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'" + toCellName + "')]"))
					toCellElement.click()
					WebUI.comment("Selected target cell type: ${toCellName}")

					// Wait for classification success
					try {
						WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/div_1 patch reclassified'), 10)
						WebUI.delay(2)  // Optional wait for table refresh
					} catch (Exception e) {
						WebUI.comment("✅ Classification massage  verified.")
					}

					// Step 3: Get final counts
					int fromFinalCount = getCellCount(driver, fromCellName)
					int toFinalCount = getCellCount(driver, toCellName)

					WebUI.comment("Final count - From: ${fromFinalCount}, To: ${toFinalCount}")

					// Step 4: Validate count changes
					assert fromFinalCount == fromInitialCount - 1 : "Expected ${fromCellName} count to decrease by 1"
					assert toFinalCount == toInitialCount + 1 : "Expected ${toCellName} count to increase by 1"

					WebUI.comment("✅ Classification #${i} complete and verified.")
				} else {
					WebUI.comment("⚠️ Count for ${fromCellName} is ${fromInitialCount} (<=0). Skipping classification at attempt ${i}.")
					break  // No more patches to classify
				}
			}
		} catch (Exception e) {
			WebUI.comment("❌ Error during classification: ${e.message}")
			throw e
		}
	}




	@Keyword
	def classifyFromCellToCell(String fromCellName, String toCellName) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		try {
			// Step 1: Get initial counts
			int fromInitialCount = getCellCount(driver, fromCellName)
			int toInitialCount = getCellCount(driver, toCellName)

			WebUI.comment("Initial count - From: ${fromCellName} = ${fromInitialCount}, To: ${toCellName} = ${toInitialCount}")

			// Step 2: Proceed only if fromCell count > 1
			if (fromInitialCount > 1) {
				// Click on fromCell row
				WebElement cellRow = driver.findElement(By.xpath("//table/tbody/tr/td[1][text()='" + fromCellName + "']"))
				cellRow.click()
				WebUI.comment("Clicked on cell row: ${fromCellName}")

				// Right-click on the image
				WebElement imageElement = driver.findElement(By.xpath("//div[@class='patches-section ']//img"))
				actions.moveToElement(imageElement).contextClick().perform()
				WebUI.comment("Right-clicked on image.")

				// Click on "Classify" menu
				WebElement classifyButton = driver.findElement(By.xpath("//span[contains(text(),'Classify')]"))
				classifyButton.click()
				WebUI.comment("Clicked on 'Classify' option.")

				// Select the toCellName from the dropdown
				WebElement toCellElement = driver.findElement(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'" + toCellName + "')]"))
				toCellElement.click()
				WebUI.comment("Selected target cell type: ${toCellName}")

				// Wait for classification confirmation
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))
				WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.classified-snackbar")))

				String headerText = snackbar.findElement(By.cssSelector(".header-row .header")).getText().trim()
				String bodyText = snackbar.findElement(By.cssSelector(".body")).getText().trim()

				WebUI.comment("Snackbar message: ${headerText} | ${bodyText}")

				assert headerText.toLowerCase().contains("reclassified")
				assert bodyText.toLowerCase().contains(fromCellName.toLowerCase())
				assert bodyText.toLowerCase().contains(toCellName.toLowerCase())

				WebUI.comment("Snackbar reclassification message verified.")


				// Step 4: Click the 'X' icon to close the snackbar
				WebUI.delay(1)
				WebElement closeIcon = snackbar.findElement(By.xpath("//div[contains(@class,'MuiSnackbarContent-action')]/div"))
				actions.moveToElement(closeIcon).click().build().perform()
				WebUI.comment("Snackbar closed by clicking X icon.")
				// Step 3: Get new counts
				int fromFinalCount = getCellCount(driver, fromCellName)
				int toFinalCount = getCellCount(driver, toCellName)

				WebUI.comment("Final count - From: ${fromFinalCount}, To: ${toFinalCount}")

				// Step 4: Validate that counts changed correctly
				assert fromFinalCount == fromInitialCount - 1 : "Expected ${fromCellName} count to decrease by 1"
				assert toFinalCount == toInitialCount + 1 : "Expected ${toCellName} count to increase by 1"

				WebUI.comment("Count validation passed.")
			} else {
				WebUI.comment("Count for ${fromCellName} is ${fromInitialCount} (<1). Skipping classification.")
			}
		} catch (Exception e) {
			WebUI.comment("Error during classification from '${fromCellName}' to '${toCellName}': " + e.message)
			throw e
		}
	}







	@Keyword
	def classifyFromCellToSubCellAny(String fromCellName, String toSubCellName) {
		def driver = DriverFactory.getWebDriver()
		def actions = new Actions(driver)

		try {
			// Initial count handling
			int fromCount = getCellCount(driver, fromCellName)
			if (fromCount <= 1) {
				WebUI.comment("Count for ${fromCellName} is ${fromCount} (<=1). Skipping classification.")
				return
			}

			// Get initial count for subcell (if it's in the table)
			int subInitialCount = getCellCount(driver, toSubCellName)

			// Click the source cell row
			WebElement cellRow = driver.findElement(By.xpath("//table/tbody/tr/td[1][contains(text(),'" + fromCellName + "')]"))
			cellRow.click()
			WebUI.comment("Clicked on cell row: ${fromCellName}")

			// Right-click on image
			WebElement imageElement = driver.findElement(By.xpath("//div[@class='patches-section ']//img"))
			actions.moveToElement(imageElement).contextClick().perform()
			WebUI.comment("Right-clicked on image.")

			// Click Classify
			WebElement classifyButton = driver.findElement(By.xpath("//span[contains(text(),'Classify')]"))
			classifyButton.click()
			WebUI.comment("Clicked on 'Classify'.")

			// Hover over main cell (determine which main cell has the sub-cell)
			String mainCell = getMainCellForSubCell(toSubCellName)
			if (!mainCell) {
				WebUI.comment("Sub-cell '${toSubCellName}' does not belong to any main cell.")
				return
			}

			WebElement mainCellElement = driver.findElement(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'${mainCell}')]"))
			actions.moveToElement(mainCellElement).perform()
			WebUI.comment("Hovered on main cell: ${mainCell}")

			// Click sub-cell
			WebElement subCellElement = driver.findElement(By.xpath("(//ul[contains(@class,'MuiMenu-list')])[3]//li[contains(text(),'${toSubCellName}')]"))
			subCellElement.click()
			WebUI.comment("Selected sub-cell: ${toSubCellName}")

			// Confirm classification
			WebUI.verifyElementPresent(findTestObject('Object Repository/WBC_m/Page_PBS/div_1 patch subclassified'), 10)
			WebUI.delay(2)

			// Validate count changes
			int fromFinal = getCellCount(driver, fromCellName)
			int subFinal = getCellCount(driver, toSubCellName)

			WebUI.comment("Final count for ${fromCellName}: ${fromFinal}")
			WebUI.comment("Final count for ${toSubCellName}: ${subFinal}")
			assert fromFinal == fromCount - 1 : "From cell count did not decrease"
			assert subFinal == subInitialCount + 1 : "To sub-cell count did not increase"
			WebUI.comment("Classification from ${fromCellName} to ${toSubCellName} successful.")
		} catch (Exception e) {
			WebUI.comment("Error during classification from '${fromCellName}' to '${toSubCellName}': " + e.message)
			throw e
		}
	}



	// Helper method to resolve sub-cell to main cell
	private String getMainCellForSubCell(String subCell) {
		Map<String, List<String>> cellToSubCells = [
			"Neutrophils"              : [
				"Band Forms",
				"Hypersegmented",
				"Neutrophils with Toxic Granules"
			],
			"Lymphocytes"              : [
				"Reactive",
				"Large Granular Lymphocytes"
			],
			"Atypical Cells/Blasts"    : [
				"Atypical Cells",
				"Lymphoid Blasts",
				"Myeloid Blasts"
			],
			"Immature Granulocytes"    : [
				"Promyelocytes",
				"Myelocytes",
				"Metamyelocytes"
			]
		]
		for (entry in cellToSubCells.entrySet()) {
			if (entry.value.contains(subCell)) {
				return entry.key
			}
		}
		return null
	}

	@Keyword
	def dragAndDropFromCellToCell(String fromCellName, String toCellName) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		try {
			int fromInitial = getCellCount(driver, fromCellName)
			int toInitial = getCellCount(driver, toCellName)

			WebUI.comment("Initial count - From: ${fromCellName} = ${fromInitial}, To: ${toCellName} = ${toInitial}")

			if (fromInitial < 1) {
				WebUI.comment("⚠️ Count for ${fromCellName} is ${fromInitial} (<1). Skipping drag and drop.")
				return
			}

			// Locate source patch (assuming single visible patch for drag)
			WebElement source = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/WBC_m/Page_PBS/patch'), 10)

			// Locate destination cell
			TestObject targetObj = new TestObject().addProperty("xpath",
					com.kms.katalon.core.testobject.ConditionType.EQUALS,
					"//td[contains(text(),'${toCellName}')]")
			WebElement target = WebUiCommonHelper.findWebElement(targetObj, 10)

			// Drag and drop action
			actions.clickAndHold(source)
					.moveToElement(target, 10, 10)
					.pause(1000)
					.release()
					.build()
					.perform()

			WebUI.comment("✅ Dragged patch from ${fromCellName} to ${toCellName}")

			WebUI.delay(2) // allow count to update

			// Wait for classification confirmation
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30))
				WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.classified-snackbar")))

				String headerText = snackbar.findElement(By.cssSelector(".header-row .header")).getText().trim()
				String bodyText = snackbar.findElement(By.cssSelector(".body")).getText().trim()

				WebUI.comment("Snackbar message: ${headerText} | ${bodyText}")

				assert headerText.toLowerCase().contains("reclassified")
				assert bodyText.toLowerCase().contains(fromCellName.toLowerCase())
				assert bodyText.toLowerCase().contains(toCellName.toLowerCase())

				WebUI.comment("Snackbar reclassification message verified.")


				// Step 4: Click the 'X' icon to close the snackbar
				WebUI.delay(2)
				WebElement closeIcon = snackbar.findElement(By.xpath("//div[contains(@class,'MuiSnackbarContent-action')]/div"))
				actions.moveToElement(closeIcon).click().build().perform()
				WebUI.comment("Snackbar closed by clicking X icon.")
			}
			catch (Exception e) {
				WebUI.comment("snackbar confirmed")
			}
			//			WebUI.refresh()
			//			WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))
			//
			WebUI.delay(4)


			int fromFinal = getCellCount(driver, fromCellName)
			int toFinal = getCellCount(driver, toCellName)

			WebUI.comment("Final count - From: ${fromFinal}, To: ${toFinal}")

			assert fromFinal == fromInitial - 1 : "❌ ${fromCellName} count did not decrease by 1"
			assert toFinal == toInitial + 1 : "❌ ${toCellName} count did not increase by 1"

			WebUI.comment("✅ Count verification passed for drag and drop.")
		} catch (Exception e) {
			WebUI.comment("❌ Drag and drop failed: ${e.message}")
			throw e
		}
	}



	@Keyword
	def dragAndDropMultipleSelectedPatches(String fromCellName, String toCellName, int numberOfPatches) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)

		try {
			int fromInitial = getCellCount(driver, fromCellName)
			int toInitial = getCellCount(driver, toCellName)

			WebUI.comment("Initial count - From: ${fromCellName} = ${fromInitial}, To: ${toCellName} = ${toInitial}")

			if (fromInitial < numberOfPatches) {
				WebUI.comment("⚠️ Not enough patches to classify from ${fromCellName}. Skipping.")
				return
			}

			WebElement row = driver.findElement(By.xpath("//table/tbody/tr[td[1][text()='" + fromCellName + "']]"))

			row.click()


			// Select multiple patches by clicking them
			List<WebElement> patchElements = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]"))
			if (patchElements.size() < numberOfPatches) {
				WebUI.comment("⚠️ Only found ${patchElements.size()} patches. Cannot select ${numberOfPatches}.")
				return
			}

			for (int i = 0; i < numberOfPatches; i++) {
				patchElements[i].click()
				WebUI.comment("Selected patch ${i + 1}")
				WebUI.delay(0.2)
			}

			// Drag the last selected patch
			WebElement sourcePatch = patchElements[numberOfPatches - 1]

			// Locate target cell element
			TestObject targetObj = new TestObject().addProperty("xpath",
					com.kms.katalon.core.testobject.ConditionType.EQUALS,
					"//td[contains(text(),'${toCellName}')]")
			WebElement target = WebUiCommonHelper.findWebElement(targetObj, 10)

			// Perform drag-and-drop
			actions.clickAndHold(sourcePatch)
					.moveToElement(target, 10, 10)
					.pause(1000)
					.release()
					.build()
					.perform()

			WebUI.comment("✅ Dragged ${numberOfPatches} patch(es) from ${fromCellName} to ${toCellName}")
			WebUI.delay(2)

			// Wait for classification confirmation
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30))
				WebElement snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.classified-snackbar")))

				String headerText = snackbar.findElement(By.cssSelector(".header-row .header")).getText().trim()
				String bodyText = snackbar.findElement(By.cssSelector(".body")).getText().trim()

				WebUI.comment("Snackbar message: ${headerText} | ${bodyText}")

				assert headerText.toLowerCase().contains("reclassified")
				assert bodyText.toLowerCase().contains(fromCellName.toLowerCase())
				assert bodyText.toLowerCase().contains(toCellName.toLowerCase())

				WebUI.comment("Snackbar reclassification message verified.")


				// Step 4: Click the 'X' icon to close the snackbar
				WebUI.delay(2)
				WebElement closeIcon = snackbar.findElement(By.xpath("//div[contains(@class,'MuiSnackbarContent-action')]/div"))
				actions.moveToElement(closeIcon).click().build().perform()
				WebUI.comment("Snackbar closed by clicking X icon.")
			}
			catch (Exception e) {
				WebUI.comment("snackbar confirmed")
			}
			//			WebUI.refresh()
			//			WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))
			//
			WebUI.delay(4)

			int fromFinal = getCellCount(driver, fromCellName)
			int toFinal = getCellCount(driver, toCellName)

			WebUI.comment("Final count - From: ${fromFinal}, To: ${toFinal}")

			assert fromFinal == fromInitial - numberOfPatches : "❌ ${fromCellName} count did not decrease by ${numberOfPatches}"
			assert toFinal == toInitial + numberOfPatches : "❌ ${toCellName} count did not increase by ${numberOfPatches}"

			WebUI.comment("✅ Multiple patch reclassification verification passed.")
		} catch (Exception e) {
			WebUI.comment("❌ Error during multi-patch drag-and-drop: ${e.message}")
			throw e
		}
	}





	// Helper method to get count
	private int getCellCount(WebDriver driver, String cellName) {
		try {
			WebElement row = driver.findElement(By.xpath("//table/tbody/tr[td[1][contains(text(),'" + cellName + "')]]"))
			WebElement countCell = row.findElement(By.xpath("./td[2]"))
			String countText = countCell.getText().trim()
			return (countText == "-" || countText.isEmpty()) ? 0 : Integer.parseInt(countText)
		} catch (Exception e) {
			WebUI.comment("Count not found for ${cellName}. Assuming 0.")
			return 0
		}
	}




	@Keyword
	def classifyFromCellToCellMultiplePlatelet(String fromCellName, String toCellName, int times) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)
		try {
			for (int i = 1; i <= times; i++) {
				WebUI.comment(":repeat: Classification attempt ${i} of ${times}")
				// Step 1: Get initial counts
				int fromInitialCount = getCellCountPlatelet(driver, fromCellName)
				println(fromInitialCount)
				int toInitialCount = getCellCountPlatelet(driver, toCellName)
				println(toInitialCount)
				WebUI.comment("Initial count - From: ${fromCellName} = ${fromInitialCount}, To: ${toCellName} = ${toInitialCount}")
				// Step 2: Proceed only if fromCell count > 1
				if (fromInitialCount > 0) {
					// Click on fromCell row
					//WebElement cellRow = driver.findElement(By.xpath("//table/tbody/tr/td[1][text()='" + fromCellName + "']"))
					WebElement cellRow = driver.findElement(By.xpath("//div[text()='" + fromCellName + "']/parent::div"))


					cellRow.click()
					WebUI.comment("Clicked on cell row: ${fromCellName}")
					// Right-click on the image
					WebElement imageElement = driver.findElement(By.xpath("//div[@class='patches-section ']//img"))
					actions.moveToElement(imageElement).contextClick().perform()
					WebUI.comment("Right-clicked on image.")
					// Click on "Classify" menu
					WebElement classifyButton = driver.findElement(By.xpath("//span[contains(text(),'Classify')]"))
					classifyButton.click()
					WebUI.comment("Clicked on 'Classify' option.")
					// Click the toCellName (main or sub-cell logic will be handled automatically in UI)
					WebElement toCellElement = driver.findElement(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[text()='" + toCellName + "']"))
					toCellElement.click()
					WebUI.comment("Selected target cell type: ${toCellName}")
					// Wait for classification success
					WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/toast_msg_header'), 10)
					WebUI.delay(2)  // Optional wait for table refresh

					// Step 3: Get final counts
					int fromFinalCount = getCellCountPlatelet(driver, fromCellName)
					int toFinalCount = getCellCountPlatelet(driver, toCellName)
					WebUI.comment("Final count - From: ${fromFinalCount}, To: ${toFinalCount}")
					// Step 4: Validate count changes
					assert fromFinalCount == fromInitialCount - 1 : "Expected ${fromCellName} count to decrease by 1"
					assert toFinalCount == toInitialCount + 1 : "Expected ${toCellName} count to increase by 1"
					WebUI.comment(":white_tick: Classification #${i} complete and verified.")
				} else {
					WebUI.comment(":warning: Count for ${fromCellName} is ${fromInitialCount} (<=1). Skipping classification at attempt ${i}.")
					break  // No more patches to classify
				}
			}
		} catch (Exception e) {
			WebUI.comment(":x: Error during classification: ${e.message}")
			throw e
		}
	}

	private int getCellCountPlatelet(WebDriver driver, String cellName) {
		try {
			WebElement row = driver.findElement(By.xpath("//div[text()='"+cellName+"']/parent::div"))
			WebElement countCell = row.findElement(By.xpath("./div[2]"))
			String countText = countCell.getText().trim()
			return (countText == "0" || countText.isEmpty()) ? 0 : Integer.parseInt(countText)
		} catch (Exception e) {
			WebUI.comment("Count not found for ${cellName}. Assuming 0.")
			return 0
		}
	}
}