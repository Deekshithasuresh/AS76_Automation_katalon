import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
DriverFactory

WebUI.openBrowser('')

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju')

WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))

def driver = DriverFactory.getWebDriver()

int Monocytecount = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Monocytes')
WebUI.comment("Starting reclassification. Initial Monocytes count: $Monocytecount")
int remainingMonocytes = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Monocytes')
WebUI.comment("Starting reclassification. Initial Monocytes count: ${remainingMonocytes}")
while (remainingMonocytes > 1) {
	try {
		int beforeCount = remainingMonocytes
		// Scroll to bottom to bring last cells into view (important for UI interaction)
		WebUI.executeJavaScript('window.scrollTo(0, document.body.scrollHeight)', null)
		WebUI.delay(1)
		// Attempt to classify
		CustomKeywords.'generic.Reclacification.classifyFromCellToCell'('Monocytes', 'Unclassified')
		// Wait for count to reduce (you can reuse your existing method or simple delay)
		WebUI.delay(2)
		// Recheck count
		remainingMonocytes = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Monocytes')
		if (remainingMonocytes == beforeCount) {
			WebUI.comment("⚠️ Count did not decrease after reclassification attempt. Breaking loop.")
			break
		}
	} catch (Exception e) {
		WebUI.comment("❌ Exception during reclassification: ${e.message}")
		break
	}
}

// === Step 1: Capture Monocyte Tab Patch Rankings from class attribute ===
List<WebElement> wbcImgElements = driver.findElements(By.xpath("//img[contains(@class, 'qa_patch_rank-')]"))
List<String> wbcTopRanks = []

for (int i = 0; i < Math.min(2, wbcImgElements.size()); i++) {
	String classAttr = wbcImgElements[i].getAttribute("class")
	def matcher = classAttr =~ /(qa_patch_rank-\d+)/
	if (matcher.find()) {
		wbcTopRanks.add(matcher.group(1))
	}
}
println("Monocyte tab patch ranks: ${wbcTopRanks}")

// === Step 2: Open Modify popup ===
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.delay(2) // Wait for modify modal to appear

// === Step 3: Capture Modify Modal Patch Rankings from class attribute ===
List<WebElement> modifyImgElements = driver.findElements(By.xpath("//img[contains(@class, 'qa_patch_rank-')]"))
List<String> modifyTopRanks = []

for (int i = 0; i < Math.min(2, modifyImgElements.size()); i++) {
	String classAttr = modifyImgElements[i].getAttribute("class")
	def matcher = classAttr =~ /(qa_patch_rank-\d+)/
	if (matcher.find()) {
		modifyTopRanks.add(matcher.group(1))
	}
}
println("Modify popup patch ranks: ${modifyTopRanks}")

// === Step 4: Dynamically Compare Available Rankings ===
int compareCount = Math.min(wbcTopRanks.size(), modifyTopRanks.size())
boolean allMatch = true

for (int i = 0; i < compareCount; i++) {
	if (wbcTopRanks[i] != modifyTopRanks[i]) {
		allMatch = false
		break
	}
}

if (allMatch) {
	println("✅ Patch rankings match.")
} else {
	println("❌ Patch rankings do NOT match.")
	println("WBC: ${wbcTopRanks}, Modify: ${modifyTopRanks}")
}