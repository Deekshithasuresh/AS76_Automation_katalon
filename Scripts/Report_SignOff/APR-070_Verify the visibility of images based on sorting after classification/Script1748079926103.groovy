import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver

DriverFactory

WebUI.openBrowser('')

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh')

WebDriver driver = DriverFactory.getWebDriver()

// Navigate to WBC tab and open Neutrophil cell details
WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))
WebUI.delay(2)
WebUI.click(findTestObject('Object Repository/Page_PBS/td_Neutrophils'))

// Get initial Neutrophils count
int NeutrophilsBefore = CustomKeywords.'generic.Reclacification.getCellCount'(driver, 'Neutrophils')
WebUI.comment("Initial Neutrophils Count: $NeutrophilsBefore")

if (NeutrophilsBefore > 0) {
	// Scroll to bottom and classify 1 Neutrophils cell
	WebUI.executeJavaScript('window.scrollTo(0, document.body.scrollHeight)', null)
	WebUI.delay(1)
	try {
		CustomKeywords.'generic.Reclacification.classifyFromCellToCell'('Neutrophils', 'Unclassified')
		WebUI.delay(2)  // Let UI update
	} catch (Exception e) {
		WebUI.comment("❌ Classification failed: ${e.message}")
	}
}

//WebDriver driver = DriverFactory.getWebDriver()

// === Step 1: Capture 2 WBC Tab Patch Rankings from class attribute ===
List<WebElement> wbcImgElements = driver.findElements(By.xpath("//img[contains(@class, 'qa_patch_rank-')]"))
List<String> wbcTop2Ranks = []

for (int i = 0; i < Math.min(2, wbcImgElements.size()); i++) {
	String classAttr = wbcImgElements[i].getAttribute("class")
	def matcher = classAttr =~ /(qa_patch_rank-\d+)/
	if (matcher.find()) {
		wbcTop2Ranks.add(matcher.group(1))
	}
}

println("WBC tab patch ranks: ${wbcTop2Ranks}")

// === Step 2: Open Modify popup ===
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Approve report'))
WebUI.click(findTestObject('Object Repository/Page_PBS/button_Confirm'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Add supporting images'))
WebUI.click(findTestObject('Object Repository/Page_PBS/span_Modify'))

WebUI.delay(2) // Wait for modify modal to appear

// === Step 3: Capture 2 Modify Modal Patch Rankings from class attribute ===
List<WebElement> modifyImgElements = driver.findElements(By.xpath("//img[contains(@class, 'qa_patch_rank-')]"))
List<String> modifyTop2Ranks = []

for (int i = 0; i < Math.min(2, modifyImgElements.size()); i++) {
	String classAttr = modifyImgElements[i].getAttribute("class")
	def matcher = classAttr =~ /(qa_patch_rank-\d+)/
	if (matcher.find()) {
		modifyTop2Ranks.add(matcher.group(1))
	}
}

println("Modify popup patch ranks: ${modifyTop2Ranks}")

// === Step 4: Compare Rankings ===
if (wbcTop2Ranks == modifyTop2Ranks) {
	println("✅ Patch rankings match.")
} else {
	println("❌ Patch rankings do NOT match.")
	println("WBC: ${wbcTop2Ranks}, Modify: ${modifyTop2Ranks}")
}

