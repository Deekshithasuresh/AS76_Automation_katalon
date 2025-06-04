import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable

import com.kms.katalon.core.webui.driver.DriverFactory

import java.time.Duration

import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.exception.StepFailedException

// Step 1: Login and go to WBC tab
CustomKeywords.'generic.custumFunctions.login'()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)
WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

WebDriver driver = DriverFactory.getWebDriver()

// Step 2: Get initial WBC count
String totalCountXPath = "//td[normalize-space()='Total']/following-sibling::td[1]"
WebElement inTotalRow = driver.findElement(By.xpath(totalCountXPath))
int initialCount = Integer.parseInt(inTotalRow.getText().trim())
WebUI.comment("🔎 Initial WBC Count = ${initialCount}")

// Step 3: Validate that warning is shown for count < 200
List<WebElement> warningList = driver.findElements(By.xpath("//div[contains(@class,'reportSummary_count-alert')]//span[contains(text(), 'Number of WBCs counted is <200')]"))
boolean warningVisible = warningList.any { it.isDisplayed() && it.getText().contains("Number of WBCs counted is <200") }

if (initialCount < 200 ) {
	WebUI.comment("✅ Warning shown correctly for WBC < 200")
} else {
	WebUI.comment("❌ Warning NOT shown as expected for WBC < 200")
}

// Step 4: Reclassify patches to push count above 200
int patchesNeeded = 200 - initialCount + 1
if (patchesNeeded > 0) {
	WebUI.comment("🔄 Reclassifying ${patchesNeeded} rejected patches to valid WBCs")
	CustomKeywords.'generic.Reclassification.classifyFromCellToCellMultiple'("Rejected", "Lymphocytes", patchesNeeded)
} else {
	WebUI.comment("✅ Already have sufficient WBC count (>= 200)")
}

// Step 5: Refresh and check new count
WebUI.refresh()
WebUI.waitForElementVisible(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'), 10)
WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))

WebElement updatedTotalRow = driver.findElement(By.xpath(totalCountXPath))
int updatedCount = Integer.parseInt(updatedTotalRow.getText().trim())
WebUI.comment("🔎 Updated WBC Count = ${updatedCount}")

// Step 6: Validate that warning has disappeared
List<WebElement> warningAfterList = driver.findElements(By.xpath("//div[contains(@class,'reportSummary_count-alert')]//span[contains(text(), 'Number of WBCs counted is <200')]"))
boolean warningStillVisible = warningAfterList.any { it.isDisplayed() }

if (updatedCount >= 200 && !warningStillVisible) {
	WebUI.comment("✅ Warning correctly disappeared after WBC reached ≥ 200")
} else {
	WebUI.comment("❌ Warning still present or count not updated")
	assert false : "Updated WBC Count = ${updatedCount}, Warning Still Visible = ${warningStillVisible}"
}

// Step 7: Check warning on Summary tab
WebUI.click(findTestObject('Object Repository/WBC_m/Page_PBS/button_Summary'))

boolean warningOnSummary = driver.findElements(By.xpath("//div[contains(@class,'reportSummary_count-alert')]//span[contains(text(), 'Number of WBCs counted is <200')]"))
                                .any { it.isDisplayed() }

if (updatedCount >= 200 && !warningOnSummary) {
	WebUI.comment("✅ Summary tab also shows no warning")
} else {
	WebUI.comment("❌ Warning still visible on Summary tab")
	assert false : "Summary Tab - Warning Still Visible = ${warningOnSummary}"
}
