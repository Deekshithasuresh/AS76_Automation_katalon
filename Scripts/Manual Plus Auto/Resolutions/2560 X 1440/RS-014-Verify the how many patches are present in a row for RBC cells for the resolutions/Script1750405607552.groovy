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
driver.manage().window().setSize(new Dimension(2560, 1440))
WebUI.comment("✅ Browser resolution set to 2560x1440")

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
	if (firstRowCount >= 20 && firstRowCount <= 21) {
		WebUI.comment("✅ Patch count is within expected range (20–21). Found: ${firstRowCount}")
	} else {
		WebUI.comment("❌ Expected patch count between 20–21, but found ${firstRowCount}")
		KeywordUtil.markFailed("❌ Expected patch count between 20–21, but found ${firstRowCount}")
	}
}