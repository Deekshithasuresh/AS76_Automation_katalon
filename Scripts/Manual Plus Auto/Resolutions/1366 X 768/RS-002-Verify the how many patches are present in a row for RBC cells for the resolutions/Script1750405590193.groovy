import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import loginPackage.Login
import zoom.ZoomInOut as ZoomInOut

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_Summary'), 10)

WebUI.waitForElementVisible(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.verifyElementPresent(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'), 10)

WebUI.click(findTestObject('Object Repository/RBC_Objects/Page_PBS/button_RBC'))



WebDriver driver = DriverFactory.getWebDriver()
driver.manage().window().setSize(new Dimension(1366, 768))
WebUI.comment("✅ Browser resolution set to 1366x768")

// Locate all patch elements (adjust XPath if needed)
List<WebElement> allPatches = driver.findElements(By.xpath("//div[contains(@class, 'patches-viewer-section')]//div[@class='Card patches-container']"))

if (allPatches.isEmpty()) {
	WebUI.comment("❌ No patch elements found")
	KeywordUtil.markFailed('❌ No patch elements found')
	
} else {
	Map<Integer, List<WebElement>> rows = [:]

	// Group by Y-position to determine rows
	allPatches.each { patch ->
		int y = patch.getLocation().getY()
		int key = rows.keySet().find { Math.abs(it - y) <= 10 } ?: y
		rows[key] = rows.getOrDefault(key, []) + patch
	}

	// Print row-wise patch counts
	rows.eachWithIndex { entry, idx ->
		WebUI.comment("Row ${idx + 1}: ${entry.value.size()} patches")
	}

	int firstRowCount = rows.values()[0].size()
	if (firstRowCount == 11) {
		WebUI.comment("✅ Exactly 11 patches found in first row.")
	} else {
		WebUI.comment("❌ Expected 11 patches, but found ${firstRowCount} in first row.")
		KeywordUtil.markFailed('❌ Expected 11 patches, but found ${firstRowCount} in first row.')
		
	}
}