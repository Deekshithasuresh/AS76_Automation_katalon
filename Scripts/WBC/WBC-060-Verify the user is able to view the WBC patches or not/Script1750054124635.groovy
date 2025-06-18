import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.*

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

	 // Wait for WBC table to be visible
    TestObject table = new TestObject().addProperty("xpath", ConditionType.EQUALS, "//table[contains(@class,'theame-table')]")
    WebUI.waitForElementVisible(table, 10)

    List<String> excludedKeywords = ["Total", "NRBC", "Smudge", "Degenerate", "Stain", "Unclassified", "Rejected", "Others"]

    List<WebElement> allRows = driver.findElements(By.xpath("//table[contains(@class,'theame-table')]/tbody/tr"))

 

    for (WebElement row : allRows) {
        List<WebElement> cells = row.findElements(By.tagName("td"))
        if (cells.size() < 3) continue

        WebElement nameCell = cells[0]
        String name = nameCell.getText().trim()
        String countText = cells[1].getText().trim()
        String percentText = cells[2].getText().trim()

        // ✅ Skip if it's in excluded list or data is missing
        if (excludedKeywords.any { name.contains(it) } ||
            countText == '-' || countText == '' ||
            percentText == '-' || percentText == '') {
            WebUI.comment("⏩ Skipping empty/excluded row: ${name}")
            continue
        }

        try {
            int count = Integer.parseInt(countText)
            double percent = Double.parseDouble(percentText)



            WebUI.comment("✅ Counted: ${name} → Count=${count}, %=${percent}")

            // **NEW LOGIC:**

            // 1. Click on the row
            row.click()
            WebUI.delay(2) // Wait for patches to load (adjust as needed)
            WebUI.comment("✅ Clicked on row: ${name}")

            // 2. Check if count is greater than 0
            if (count > 0) {
                WebUI.comment("✅ Count for ${name} (${count}) is greater than 0.")
            } else {
                WebUI.comment("❌ Count for ${name} (${count}) is NOT greater than 0.")
            }

            // 3. Check for patches
            List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class,'patches-section ')]//div[contains(@class,'Card patches-container')]")) // Adjust XPath
            if (patches.size() > 0) {
                WebUI.comment("✅ Patches are available for ${name}.")
            } else {
                WebUI.comment("❌ No patches found for ${name}.")
            }

            // **END NEW LOGIC**

        } catch (Exception e) {
            WebUI.comment("⚠️ Error processing row: ${name} — ${e.message}")
        }

    }




