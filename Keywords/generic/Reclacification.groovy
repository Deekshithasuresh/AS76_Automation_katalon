package generic
import java.time.Duration

import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.StaleElementReferenceException // Added this import

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

class Reclacification {
	@Keyword
	def classifyFromCellToCell(String fromCellName, String toCellName) {
		WebDriver driver = DriverFactory.getWebDriver()
		Actions actions = new Actions(driver)
		try {
			WebUI.comment("Starting classification from '${fromCellName}' to '${toCellName}'.")

			// Step 1: Get initial counts
			int fromInitialCount = getCellCount(driver, fromCellName)
			int toInitialCount = getCellCount(driver, toCellName)
			WebUI.comment("Initial count - From: ${fromCellName} = ${fromInitialCount}, To: ${toCellName} = ${toInitialCount}")

			// Step 2: Proceed only if fromCell count >= 1
			if (fromInitialCount >= 1) {
				// Click on fromCell row
				WebElement cellRow = driver.findElement(By.xpath("//table/tbody/tr/td[1][text()='" + fromCellName + "']"))
				cellRow.click()
				WebUI.comment("Clicked on cell row: ${fromCellName}")
				WebUI.delay(0.5) // Small delay after click

				// Right-click on the image
				WebElement imageElement = driver.findElement(By.xpath("//div[@class='patches-section ']//img"))
				actions.moveToElement(imageElement).contextClick().perform()
				WebUI.comment("Right-clicked on image.")
				WebUI.delay(0.5) // Small delay after right-click context menu appears

				// Click on "Classify" menu
				WebElement classifyButton = driver.findElement(By.xpath("//span[contains(text(),'Classify')]"))
				classifyButton.click()
				WebUI.comment("Clicked on 'Classify' option.")
				WebUI.delay(0.5) // Small delay for the classify sub-menu to appear

				// Select the toCellName from the dropdown
				WebElement toCellElement = driver.findElement(By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[text()='" + toCellName + "']"))
				toCellElement.click()
				WebUI.comment("Selected target cell type: ${toCellName}")
				WebUI.delay(1) // Delay after selection, allowing classification process to begin

				// Wait for classification confirmation snackbar
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25)) // Increased timeout to 25 seconds
				By snackbarLocator = By.cssSelector("div.classified-snackbar")

				WebElement snackbar = null
				try {
					WebUI.comment("Waiting for snackbar to be present in DOM.")
					wait.until(ExpectedConditions.presenceOfElementLocated(snackbarLocator)) // Wait for presence first
					WebUI.comment("Snackbar is present, now waiting for it to be visible.")
					snackbar = wait.until(ExpectedConditions.visibilityOfElementLocated(snackbarLocator)) // Then wait for visibility
					// Removed the WebUI.delay(1) here as it was likely causing StaleElementReferenceException

					String headerText = snackbar.findElement(By.cssSelector(".header-row .header")).getText().trim()
					String bodyText = snackbar.findElement(By.cssSelector(".body")).getText().trim()
					WebUI.comment("Snackbar message: Header='${headerText}' | Body='${bodyText}'")
					assert headerText.toLowerCase().contains("reclassified") : "Snackbar header not 'reclassified'."
					assert bodyText.toLowerCase().contains(fromCellName.toLowerCase()) : "Snackbar body missing 'fromCellName'."
					assert bodyText.toLowerCase().contains(toCellName.toLowerCase()) : "Snackbar body missing 'toCellName'."
					WebUI.comment("Snackbar reclassification message verified successfully.")
				} catch (TimeoutException e) {
					WebUI.comment("❌ Classification snackbar did not become visible within 25 seconds. This indicates classification might have failed or is very slow.")
					throw new TimeoutException("Classification confirmation snackbar not visible.", e) // Re-throw to propagate the failure
				} catch (StaleElementReferenceException e) {
					// Catch stale element specifically
					WebUI.comment("❌ StaleElementReferenceException when trying to read snackbar text. Snackbar might have vanished too quickly: ${e.message}")
					throw new StaleElementReferenceException("Snackbar element became stale before text could be read.", e)
				} catch (Exception e) {
					// Catching generic Exception for other potential issues
					WebUI.comment("❌ Error processing snackbar or its text: ${e.message}")
					throw e // Re-throw any other exceptions
				}


				// Step 3: Get new counts
				int fromFinalCount = getCellCount(driver, fromCellName)
				int toFinalCount = getCellCount(driver, toCellName)
				WebUI.comment("Final count - From: ${fromCellName} = ${fromFinalCount}, To: ${toCellName} = ${toFinalCount}")

				// Step 4: Validate that counts changed correctly
				assert fromFinalCount == fromInitialCount - 1 : "Expected ${fromCellName} count to decrease by 1. Initial: ${fromInitialCount}, Final: ${fromFinalCount}"
				assert toFinalCount == toInitialCount + 1 : "Expected ${toCellName} count to increase by 1. Initial: ${toInitialCount}, Final: ${toFinalCount}"
				WebUI.comment("Cell count validation after classification passed.")
			} else {
				WebUI.comment("Count for ${fromCellName} is ${fromInitialCount} (less than 1). Skipping classification for this cell type.")
			}
		} catch (Exception e) {
			// Catching generic Exception for any failure in the main flow
			WebUI.comment("Error during classification from '${fromCellName}' to '${toCellName}': " + e.message)
			// Re-throw the exception so the main test case can catch and handle it (e.g., mark as failed)
			throw e
		}
	}


	// Helper method to get count
	private int getCellCount(WebDriver driver, String cellName) {
		try {
			// Wait for the table row to be present before attempting to find elements within it
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)) // This timeout is for getting cell count rows
			WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table/tbody/tr[td[1][text()='" + cellName + "']]")))

			WebElement countCell = row.findElement(By.xpath("./td[2]"))
			String countText = countCell.getText().trim()
			return (countText == "-" || countText.isEmpty()) ? 0 : Integer.parseInt(countText)
		} catch (Exception e) {
			WebUI.comment("Count not found for ${cellName} or row not present. Assuming 0. Error: ${e.message}")
			return 0
		}
	}
}
