import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)

WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')

WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))
WebDriver driver = DriverFactory.getWebDriver()

Actions actions = new Actions(driver)
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10))

// Wait for WBC table to be visible
TestObject table = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//table[contains(@class,'theame-table')]")
WebUI.waitForElementVisible(table, 10)

List<String> excludedKeywords = [ "NRBC", "Smudge", "Degenerate", "Stain", "Unclassified", "Rejected", "Others"]

List<WebElement> allRows = driver.findElements(By.xpath("//table[contains(@class,'theame-table')]/tbody/tr"))

for (WebElement row : allRows) {
	List<WebElement> cells = row.findElements(By.tagName("td"))
	if (cells.size() < 3) continue

	WebElement nameCell = cells[0]
	String name = nameCell.getText().trim()
	String countText = cells[1].getText().trim()
	String percentText = cells[2].getText().trim()

	// ✅ Check if the row is excluded
	if (excludedKeywords.any { name.contains(it) } ||
		countText == '-' || countText == '' ||
		percentText == '-' || percentText == '') {

		WebUI.comment("Processing excluded row: ${name}")

		try {
			// 1. Click on the row
			row.click()
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]")))
			WebUI.delay(2) // Wait for patches to load (adjust as needed)
			WebUI.comment("✅ Clicked on excluded row: ${name}")

			// No need to check count > 0 for excluded rows (as per original logic)

			// 2. Check for patches
			List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]")) // Adjust XPath
			if (patches.size() > 0) {
				WebUI.comment("✅ Patches are available for excluded row: ${name}.")
			} else {
				WebUI.comment("❌ No patches found for excluded row: ${name}.")
			}

		} catch (Exception e) {
			WebUI.comment("⚠️ Error processing excluded row: ${name} — ${e.message}")
		}

	} else {
		WebUI.comment("⏩ Skipping non-excluded row: ${name}")
	}
}