import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import generic.custumFunctionsvj

custumFunctionsvj cus = new custumFunctionsvj();


WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

// Login
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')

WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

// Select a sample
//WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/td_SIG0146'))

cus.selectReportByStatus('To be reviewed')

cus.assignOrReassignOnTabs("jyothi")


// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)

classifyFromCellToCellMultiple("Platelet Clumps","Large Platelets",1)


private int getCellCount(WebDriver driver, String cellName) {
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
				int fromFinalCount = getCellCount(driver, fromCellName)
				int toFinalCount = getCellCount(driver, toCellName)

				WebUI.comment("Final count - From: ${fromFinalCount}, To: ${toFinalCount}")

				// Step 4: Validate count changes
				assert fromFinalCount == fromInitialCount - 1 : "Expected ${fromCellName} count to decrease by 1"
				assert toFinalCount == toInitialCount + 1 : "Expected ${toCellName} count to increase by 1"

				WebUI.comment("✅ Classification #${i} complete and verified.")
			} else {
				WebUI.comment("⚠️ Count for ${fromCellName} is ${fromInitialCount} (<=1). Skipping classification at attempt ${i}.")
				break  // No more patches to classify
			}
		}
	} catch (Exception e) {
		WebUI.comment("❌ Error during classification: ${e.message}")
		throw e
	}
}

