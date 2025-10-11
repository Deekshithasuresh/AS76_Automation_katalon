import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import java.time.Duration

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import generic.custumFunctionsvj

// Instantiate your custom function class
custumFunctionsvj cus = new custumFunctionsvj()


// Open browser and navigate to login page
WebUI.openBrowser('')
WebUI.navigateToUrl('https://pbsreview.as76.local/login')
WebDriver driver = DriverFactory.getWebDriver()

// Login step
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_username_loginId'), 'jyothi')
WebUI.setText(findTestObject('Object Repository/Platelets/Page_PBS/input_password_loginPassword'), 'jyothi@1995')
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Sign In'))

// Select report and assign
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('jyothi')

// Navigate to Platelets and Morphology tab
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Platelets'))
WebUI.click(findTestObject('Object Repository/Platelets/Page_PBS/button_Morphology'))
WebUI.delay(2)

// ------- Core batch classification function definition -------
def classifyMultipleSelectedPatches(String fromCellName, String toCellName, int numberOfPatches) {
    WebDriver driver = DriverFactory.getWebDriver()
    Actions actions = new Actions(driver)
	
    if (numberOfPatches == 0) {
        KeywordUtil.markFailed("⏩ Skipping classification: 'numberOfPatches' is 0 or less (${numberOfPatches}).")
        return
    }
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
		WebElement row = driver.findElement(By.xpath("//div[text()='"+fromCellName+"']/parent::div"))
		row.click()
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
        // Step 4: Hover on main cell (choose target class)
        WebElement mainCellElement = driver.findElement(
                By.xpath("//ul[contains(@class,'MuiMenu-list')]//li//div[contains(text(),'${toCellName}')]"))
        actions.moveToElement(mainCellElement).click().build().perform()
        WebUI.comment("Selected cell: ${toCellName}")

        // Step 5: Verify success message
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
        } catch (Exception e) {
            WebUI.comment("snackbar confirmation failed or snackbar not found")
        }

        WebUI.delay(4)

        WebUI.comment("Snackbar reclassification message verified.")

        // Step 6: Count verification
        int fromFinal = getCellCount(driver, fromCellName)
        int subFinal = getCellCount(driver, toCellName)
        WebUI.comment("Final Count - From: ${fromFinal}, To (Sub): ${subFinal}")
        // Assert reclassification math is correct
        assert fromFinal == fromInitial - numberOfPatches : "❌ From cell count didn't decrease by ${numberOfPatches}"
        assert subFinal == subInitial + numberOfPatches : "❌ Sub-cell count didn't increase by ${numberOfPatches}"
        WebUI.comment("✅ Reclassified ${numberOfPatches} patch(es) from ${fromCellName} → ${toCellName}.")
    } catch (Exception e) {
        WebUI.comment("❌ Classification error: ${e.message}")
        
    }
}

// ------- Helper for cell patch counts -------
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

// ---------------------- USAGE EXAMPLE ------------------------
classifyMultipleSelectedPatches("Large Platelets","Platelet Clumps",2)
