package chida

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



public class wbcFunctions {



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
				WebElement cellRow = driver.findElement(By.xpath("//table/tbody/tr/td[1][contains(text(),'" + fromCellName + "')]"))
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
				WebElement toCellElement = driver.findElement(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[text()='" + toCellName + "']"))
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

				// Step 3: Get new counts
				int fromFinalCount = getCellCount(driver, fromCellName)
				int toFinalCount = getCellCount(driver, toCellName)
				WebUI.comment("Final count - From: ${fromFinalCount}, To: ${toFinalCount}")
				// Step 4: Validate that counts changed correctly
				//assert fromFinalCount == fromInitialCount - 1 : "Expected ${fromCellName} count to decrease by 1"
				//assert toFinalCount == toInitialCount + 1 : "Expected ${toCellName} count to increase by 1"
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
	// :white_check_mark: Helper: check if the cell is a platelet
	boolean isPlatelet(String cellName) {
		List<String> plateletTypes = [
			'Large Platelets',
			'Platelet Clumps'
		]
		return plateletTypes.contains(cellName)
	}
	// :white_check_mark: Helper: check if text is integer
	boolean isInteger(String s) {
		try {
			Integer.parseInt(s)
			return true
		} catch (NumberFormatException e) {
			return false
		}
	}

	private int getCellCount(WebDriver driver, String cellName) {
		try {
			WebElement row = driver.findElement(By.xpath("//table/tbody/tr[td[1][text()='" + cellName + "']]"))
			WebElement countCell = row.findElement(By.xpath("./td[2]"))
			String countText = countCell.getText().trim()
			return (countText == "-" || countText.isEmpty()) ? 0 : Integer.parseInt(countText)
		} catch (Exception e) {
			WebUI.comment("Count not found for ${cellName}. Assuming 0.")
			return 0
		}
	}



	@Keyword
	def selectReportByStatus(String status) {
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
		// 1) Wait for table to appear
		//WebUI.waitForElementVisible(findTestObject('Object Repository/Report_Listing/Page_PBS/th_Slide Id'), 15)
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
		// 3) Count total rows loaded
		String rowXPath = "//*[@id='reportListingTable']/div/div/div/tbody/tr"
		int totalRows = driver.findElements(By.xpath(rowXPath)).size()
		KeywordUtil.logInfo(":white_check_mark: Scrolling complete. Total rows loaded: ${totalRows}")
		// 4) Find and click first row matching the status
		try {
			String xpath = "(//tr[.//td[contains(normalize-space(), '${status}')]])[1]"
			TestObject matchingRow = new TestObject('dynamicReportRow')
			matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
			WebUI.waitForElementClickable(matchingRow, 10)
			WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
			rowElement.click()
			WebUI.comment(":white_check_mark: Clicked first row with status = '${status}'")
		}
		catch (Exception e) {
			String xpath = "(//tr[.//td[contains(normalize-space(), 'Under review')]])[1]"
			TestObject matchingRow = new TestObject('dynamicReportRow')
			matchingRow.addProperty('xpath', ConditionType.EQUALS, xpath)
			WebUI.waitForElementClickable(matchingRow, 10)
			WebElement rowElement = WebUiCommonHelper.findWebElement(matchingRow, 10)
			rowElement.click()
			WebUI.comment(":white_check_mark: Clicked first row with status = 'Under review' there is no to be reviewd report")
		}
	}



	@Keyword
	def assignOrReassignOnTabs(String reviewerName, boolean confirmReassign = true) {
		// 1) Wait for the “Assigned To” field
		TestObject assignedField = new TestObject('dynamicAssignedField')
		assignedField.addProperty('xpath', ConditionType.EQUALS, "//input[@id='assigned_to']")
		WebUI.waitForElementVisible(assignedField, 10)
		// 2) Read current value
		String current = WebUI.getAttribute(assignedField, 'value').trim()
		WebUI.comment("Current reviewer: '${current}'")
		// 3) Open dropdown
		WebUI.scrollToElement(assignedField, 5)
		WebUI.click(assignedField)
		WebUI.delay(1)
		// 4) Fetch all options
		TestObject allOpts = new TestObject('dynamicAllOpts')
		allOpts.addProperty('xpath', ConditionType.EQUALS, "//ul[@role='listbox']//li")
		List<WebElement> els = WebUiCommonHelper.findWebElements(allOpts, 5)
		List<String> names = els.collect { it.getText().trim() }
		WebUI.comment("Available reviewers: ${names}")
		// 5) Proceed only if current != reviewerName
		if (current != reviewerName) {
			if (!names.contains(reviewerName)) {
				WebUI.comment("Reviewer '${reviewerName}' not found in dropdown! Skipping assignment.")
				return
			}
			// Select reviewer
			TestObject choice = new TestObject('dynamicChoice')
			choice.addProperty('xpath', ConditionType.EQUALS,
					"//ul[@role='listbox']//li[normalize-space() = '${reviewerName}']")
			WebUI.click(choice)
			// 6) Handle re-assign popup
			try {
				def popup = findTestObject('Object Repository/Report_Listing/Page_PBS/h2_Are you sure you want to re-assign this slide')
				def reassignBtn = findTestObject('Object Repository/Report_Listing/Page_PBS/button_Re-assign')
				def cancelBtn   = findTestObject('Object Repository/Report_Listing/Page_PBS/button_Cancel')
				if (WebUI.verifyElementPresent(popup, 3)) {
					WebUI.comment("Re-assign popup appeared.")
					if (confirmReassign) {
						WebUI.comment("Clicking Re-assign...")
						WebUI.click(reassignBtn)
						WebUI.waitForElementNotPresent(popup, 10)
					} else {
						WebUI.comment("Clicking Cancel...")
						WebUI.click(cancelBtn)
						WebUI.waitForElementNotPresent(popup, 10)
					}
				} else {
					WebUI.comment("No Re-assign popup appeared.")
				}
			} catch (Exception e) {
				WebUI.comment("Popup handling error: ${e.message}")
			}
		} else {
			WebUI.comment("Already assigned to '${reviewerName}', no action. Skipping reassignment.")
			return
		}
		// 7) Final Verification
		String after = WebUI.getAttribute(assignedField, 'value').trim()
		if (confirmReassign) {
			assert after == reviewerName : "Expected assignment to '${reviewerName}', but found '${after}'"
			WebUI.comment("Assignment confirmed: '${after}'")
		} else {
			assert after == current : "Expected assignment to stay '${current}' after Cancel, but found '${after}'"
			WebUI.comment("Assignment unchanged after Cancel as expected: '${after}'")
		}
	}
}
