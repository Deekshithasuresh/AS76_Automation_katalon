import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.openBrowser('')

CustomKeywords.'generic.custumFunctions.login'()

CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')

CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('santosh')

// === Step 1: Go to WBC Tab and Neutrophils ===
WebUI.click(findTestObject('Object Repository/Page_PBS/span_WBC'))
WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Page_PBS/td_Neutrophils'))
WebUI.delay(3)

// === Step 2: Collect Top 5 Patch Rankings from WBC Tab ===
List<WebElement> wbcImages = WebUI.findWebElements(
	new TestObject().addProperty("xpath", ConditionType.EQUALS, "//img[contains(@class, 'qa_patch_rank')]"),
	10
)

List<String> wbcTop5Ranks = []
int count = 0
for (WebElement image : wbcImages) {
	String classAttr = image.getAttribute("class")
	def matcher = (classAttr =~ /qa_patch_rank-(\d+)/)
	if (matcher.find()) {
		wbcTop5Ranks.add("qa_patch_rank-${matcher[0][1]}")
		count++
		if (count == 5) break
	}
}
println "WBC Neutrophils Top 5 Patch Ranks: ${wbcTop5Ranks}"

// === Step 3: Open Modify Popup ===
WebUI.click(findTestObject('Object Repository/Report-Signoff/button_Sign In'))
WebUI.click(findTestObject('Object Repository/Report-Signoff/input_Clear filters_PrivateSwitchBase-input_068da8'))
WebUI.click(findTestObject('Object Repository/Report-Signoff/td_22-Apr-2025, 0404 PM (EAT)'))
WebUI.click(findTestObject('Object Repository/Report-Signoff/span_Add supporting images'))

// === Step 3.1: Wait for Backdrop to Disappear ===
TestObject backdrop = new TestObject()
backdrop.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'MuiBackdrop-root') and @aria-hidden='true']")
WebUI.waitForElementNotVisible(backdrop, 10)

// === Step 4: Collect Top 5 Patch Rankings from Modify Popup ===
List<WebElement> modifyRankedImages = WebUI.findWebElements(
	new TestObject().addProperty("xpath", ConditionType.EQUALS, "//ul[contains(@class,'patches-image-list')]//img[contains(@class,'qa_patch_rank')]"),
	10
)

List<String> modifyTop5Ranks = []
count = 0
for (WebElement image : modifyRankedImages) {
	String classAttr = image.getAttribute("class")
	def matcher = (classAttr =~ /qa_patch_rank-(\d+)/)
	if (matcher.find()) {
		modifyTop5Ranks.add("qa_patch_rank-${matcher[0][1]}")
		count++
		if (count == 5) break
	}
}
println "Modify Popup Top 5 Patch Ranks: ${modifyTop5Ranks}"

// === Step 5: Compare ===
if (wbcTop5Ranks == modifyTop5Ranks) {
	println("✅ Patch rankings match.")
} else {
	println("❌ Patch rankings do NOT match.")
	println("WBC: ${wbcTop5Ranks}")
	println("Modify: ${modifyTop5Ranks}")
}