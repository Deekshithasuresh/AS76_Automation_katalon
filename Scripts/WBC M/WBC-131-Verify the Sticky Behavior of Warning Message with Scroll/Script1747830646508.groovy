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
import com.kms.katalon.core.testobject.ConditionType
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
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.exception.StepFailedException
		
		// Step 1: Login and navigate
		CustomKeywords.'generic.custumFunctions.login'()
		WebUI.maximizeWindow()
		CustomKeywords.'generic.custumFunctions.selectReportByStatus'('To be reviewed')
		WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_Summary'), 'Summary')
		
		// Step 2: Assign or Reassign
		CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('manju', true)
		WebUI.verifyElementText(findTestObject('Object Repository/Report_Listing/Page_PBS/button_WBC'), 'WBC')
		
		// Step 3: Click WBC tab
		WebUI.click(findTestObject('Object Repository/Report_Listing/Page_PBS/span_WBC'))
		
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor js = (JavascriptExecutor) driver
	
		// Step 1: Check WBC Total
		WebElement totalRow = driver.findElement(By.xpath("//td[normalize-space()='Total']/following-sibling::td[1]"))
		int wbcCount = Integer.parseInt(totalRow.getText().trim())
		WebUI.comment("🔎 WBC Total Count = ${wbcCount}")
	
		if (wbcCount >= 200) {
			WebUI.comment("ℹ️ WBC count is >= 200 — warning is not expected.")
			return
		}
	
		// Step 2: Locate warning element
		WebElement warningElement = driver.findElement(By.xpath("//div[contains(@class,'reportSummary_count-alert')]"))
	
	
		WebUI.comment("✅ Warning message is initially visible.")
	
		// Step 3: Scroll down to bottom

				WebUI.delay(2)  // Let scroll settle
				
				WebElement container = driver.findElement(By.cssSelector(".cell-table-tab"))
				js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", container)
				
	
		
				// 2. Try to locate the warning message
	boolean isWarningVisible = driver.findElements(By.xpath("//div[contains(@class,'reportSummary_count-alert')]//span[contains(text(), 'Number of WBCs counted is <200')]")).size() > 0
	try {
	WebElement textMsg=driver.findElement(By.xpath("//div[contains(@class,'reportSummary_count-alert')]//span[contains(text(), 'Number of WBCs counted is <200')]"))
	// 3. Validate the condition
	if (wbcCount < 200 && isWarningVisible && textMsg.getText().equals("Number of WBCs counted is <200. Differential count might not be accurate")) {
		WebUI.comment("✅ Warning is shown correctly for WBC < 200")
		
	}
	}catch (Exception e) {
		WebUI.comment("✅ No warning shown for WBC ≥ 200 (as expected)")
		
	}

	// 3. Validate the condition
	if (wbcCount < 200 && isWarningVisible) {
		WebUI.comment("✅ Warning is shown correctly for WBC < 200")
		
	} else if (wbcCount >= 200 && !isWarningVisible) {
		WebUI.comment("✅ No warning shown for WBC ≥ 200 (as expected)")
	} else {
		WebUI.comment("❌ Mismatch between WBC count and warning message display")
		assert false : "WBC Count = ${wbcCount}, Warning Visible = ${isWarningVisible}"
	}


	
		

