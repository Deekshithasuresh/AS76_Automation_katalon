import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
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

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('jyothi')


// Navigate to Platelets -> Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))

WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))

WebUI.delay(2)

classifyMultipleSelectedPatches("Large Platelets","Platelet Clumps",3)

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



def classifyMultipleSelectedPatches(String fromCellName, String toCellName, int numberOfPatches) {
	WebDriver driver = DriverFactory.getWebDriver()
	Actions actions = new Actions(driver)

	if (numberOfPatches == 0) {
		KeywordUtil.markFailed("⏩ Skipping classification: 'numberOfPatches' is 0 or less (${numberOfPatches}).")
		return
	}

	//String mainCellForSub = getParentCellForSubCell(toSubCellName)

	try {
		int fromInitial = getCellCount(driver, fromCellName)
		int subInitial = getCellCount(driver, toCellName)

		WebUI.comment("Initial Count - From: ${fromCellName} = ${fromInitial}, To (Sub): ${toCellName} = ${subInitial}")


		if (fromInitial < numberOfPatches) {
			KeywordUtil.markFailed("⚠️ Not enough patches to classify. Needed: ${numberOfPatches}, Available: ${fromInitial}")
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
				By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'${toCellName}')]"))
		actions.moveToElement(mainCellElement).click().build().perform()
		WebUI.comment("Selected cell: ${toCellName}")


		// Step 5: Verify success message
		//WebUI.verifyElementPresent(findTestObject('Object Repository/Platelets/Page_PBS/toast_msg_header'), 10)
		TestObject toastMsg = findTestObject('Object Repository/Platelets/Page_PBS/whole_toast_msg')
		WebUI.waitForElementVisible(toastMsg, 10)
		
		TestObject toast_msg_head = findTestObject('Object Repository/Platelets/Page_PBS/toast_msg_header')
		WebUI.waitForElementVisible(toast_msg_head, 10)
		String toast_msg_header = WebUI.getText(toast_msg_head).trim()
		println("Toast message header: " + toast_msg_header)
		assert toast_msg_header.equals('3 patches reclassified')
		
		TestObject toast_msg_descs = findTestObject('Object Repository/Platelets/Page_PBS/toast_msg_desc')
		WebUI.waitForElementVisible(toast_msg_descs, 10)
		String toast_msg_desc = WebUI.getText(toast_msg_descs).trim()
		println("Toast message desc: " + toast_msg_desc)
		assert toast_msg_desc.equals('Large Platelets to Platelet Clumps')
		
		
		
		WebUI.delay(2)

		// Step 6: Count verification
		int fromFinal = getCellCount(driver, fromCellName)
		int subFinal = getCellCount(driver, toCellName)

		WebUI.comment("Final Count - From: ${fromFinal}, To (Sub): ${subFinal}")

		// Reclassification from one cell to another
		assert fromFinal == fromInitial - numberOfPatches : "❌ From cell count didn't decrease by ${numberOfPatches}"
		assert subFinal == subInitial + numberOfPatches : "❌ Sub-cell count didn't increase by ${numberOfPatches}"
		WebUI.comment("✅ Reclassified ${numberOfPatches} patch(es) from ${fromCellName} → ${toCellName}.")
		
				
	} catch (Exception e) {
		WebUI.comment("❌ Classification error: ${e.message}")
		throw e
	}
}

