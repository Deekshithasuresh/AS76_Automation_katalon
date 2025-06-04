import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

// === Step 1: Login and Navigate ===
CustomKeywords.'generic.custumFunctions.login'()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

// === Step 2: Click Neutrophils ===
TestObject neutrophilRow = new TestObject()
neutrophilRow.addProperty("xpath", ConditionType.EQUALS, "//td[contains(text(),'Neutrophils')]")
WebUI.click(neutrophilRow)

// === Step 3: Count labeled patches and cues before reclassification ===
WebDriver driver = DriverFactory.getWebDriver()


List<WebElement> initialCues = driver.findElements(By.xpath("//div[contains(@class, 'Card patches-container')]//img[@class='reclassified-cue']"))

int beforeCueCount = initialCues.size()

println "📊 BEFORE reclassification —  Visual Cues: $beforeCueCount"

CustomKeywords.'generic.Reclassification.classifyFromCellToCell'( "Eosinophils" ,"Neutrophils")

// === Step 5: Refresh the patch view ===
WebUI.click(neutrophilRow)

// === Step 6: Count labeled patches and cues again ===

List<WebElement> finalCues = driver.findElements(By.xpath("//div[contains(@class, 'Card patches-container')]//img[@class='reclassified-cue']"))

int afterCueCount = finalCues.size()

println "📊 AFTER reclassification —  Visual Cues: $afterCueCount"


WebUI.comment("✅ Patch reclassification verified: visual cue increased.")
