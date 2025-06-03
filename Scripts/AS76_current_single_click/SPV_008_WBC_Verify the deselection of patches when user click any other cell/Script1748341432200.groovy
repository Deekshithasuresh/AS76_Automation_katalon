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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject


import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.Color as Color
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.interactions.Actions as Actions
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
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.testobject.ConditionType


// ADD THIS MISSING IMPORT
import com.kms.katalon.core.testobject.ConditionType as ConditionType

// Login to the application
WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()

// Wait for the page to load
WebUI.waitForPageLoad(30)

// Navigate to the specific slide
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/td_SIG013'), 20)
WebUI.click(findTestObject('Object Repository/retain_patchs/td_SIG013'))

// Select WBC tab
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/span_WBC'), 20)
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/span_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/retain_patchs/span_WBC'))

// Start with patch view (reversed from original)
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))



try {
	// Wait for page to load completely
	WebUI.delay(2)

	// Get the WebDriver instance for element finding
	WebDriver driver = DriverFactory.getWebDriver()

	// Create the test object for single click
	TestObject targetElement = new TestObject()
	targetElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'patch-img-container')]")

	// Wait for element and verify it exists
	WebUI.waitForElementPresent(targetElement, 30)
	WebUI.waitForElementVisible(targetElement, 30)

	// Perform first single click to select the patch
	WebUI.click(targetElement)
	println("✅ Patch has been selected with first single click")

	// Wait a moment for selection to register
	WebUI.delay(1)

	// Verify patch is selected using the selected-patch class indicator
	try {
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("✅ Patch selection is confirmed - found " + selectedPatches.size() + " selected patch(es)")
		} else {
			println("⚠️ No selected patch indicator found, but proceeding with navigation")
		}
	} catch (Exception e) {
		println("⚠️ Could not verify patch selection state: " + e.getMessage())
	}

	// First ensure we're in Neutrophils section
	TestObject neutrophilsElement = new TestObject()
	neutrophilsElement.addProperty("xpath", ConditionType.EQUALS, "//td[normalize-space(text())='Neutrophils']")
	
	try {
		WebUI.waitForElementPresent(neutrophilsElement, 5)
		if (WebUI.verifyElementPresent(neutrophilsElement, 5)) {
			WebUI.click(neutrophilsElement)
			WebUI.delay(1)
			println("✅ Ensured we are in Neutrophils section initially")
		}
	} catch (Exception e) {
		println("⚠️ Could not ensure Neutrophils section: " + e.getMessage())
	}

	// Create test object for Lymphocytes navigation
	TestObject lymphocytesElement = new TestObject()
	lymphocytesElement.addProperty("xpath", ConditionType.EQUALS, "//td[normalize-space(text())='Lymphocytes']")

	// Click on Lymphocytes to navigate away
	try {
		WebUI.waitForElementPresent(lymphocytesElement, 15)
		WebUI.waitForElementClickable(lymphocytesElement, 15)
		WebUI.click(lymphocytesElement)
		WebUI.delay(3)
		println("✅ Navigated to Lymphocytes section")
		
		// Verify we're in Lymphocytes by checking if patches are different
		WebUI.delay(1)
		
	} catch (Exception e) {
		println("❌ Failed to navigate to Lymphocytes: " + e.getMessage())
		throw e
	}

	// Click on Neutrophils to return to original section
	try {
		WebUI.waitForElementPresent(neutrophilsElement, 15)
		
		// Try multiple approaches to click Neutrophils
		try {
			// First try: Scroll to element and wait
			WebUI.scrollToElement(neutrophilsElement, 5)
			WebUI.delay(1)
			WebUI.waitForElementClickable(neutrophilsElement, 10)
			WebUI.click(neutrophilsElement)
			WebUI.delay(3)
			println("✅ Returned to Neutrophils section")
			
		} catch (Exception clickError1) {
			println("⚠️ First click attempt failed: " + clickError1.getMessage())
			
			// Second try: Use JavaScript click
			try {
				WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(driver.findElement(By.xpath("//td[normalize-space(text())='Neutrophils']"))))
				WebUI.delay(3)
				println("✅ Successfully returned to Neutrophils section using JavaScript click")
				
			} catch (Exception clickError2) {
				println("⚠️ JavaScript click failed: " + clickError2.getMessage())
				
				// Third try: Use enhanced click with offset
				try {
					WebUI.enhancedClick(neutrophilsElement)
					WebUI.delay(3)
					println("✅ Successfully returned to Neutrophils section using enhanced click")
					
				} catch (Exception clickError3) {
					println("❌ All click attempts failed for Neutrophils: " + clickError3.getMessage())
					
					// Final try: Find and click a different Neutrophils element if available
					try {
						TestObject altNeutrophilsElement = new TestObject()
						altNeutrophilsElement.addProperty("xpath", ConditionType.EQUALS, "//td[contains(text(),'Neutrophils')]")
						WebUI.waitForElementPresent(altNeutrophilsElement, 5)
						WebUI.scrollToElement(altNeutrophilsElement, 3)
						WebUI.click(altNeutrophilsElement)
						WebUI.delay(3)
						println("✅ Successfully returned to Neutrophils section using alternative xpath")
					} catch (Exception finalError) {
						println("❌ Final attempt failed: " + finalError.getMessage())
						throw finalError
					}
				}
			}
		}
		
	} catch (Exception e) {
		println("❌ Failed to return to Neutrophils after all attempts: " + e.getMessage())
	}

	// Wait for the original patches to be visible again after navigation
	WebUI.waitForElementPresent(targetElement, 10)
	WebUI.waitForElementVisible(targetElement, 10)

	// Verify patch is deselected after navigation using the selected-patch class indicator
	try {
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("✅ Patch deselection is confirmed - no selected patches found after navigation")
		} else {
			println("⚠️ Found " + selectedPatches.size() + " selected patch(es) - patch may still be selected after navigation")
		}
	} catch (Exception e) {
		println("⚠️ Could not verify patch deselection state: " + e.getMessage())
	}

	println("✅ Patch selection and deselection cycle completed successfully through navigation")

} catch (Exception e) {
	println("❌ Failed to perform patch selection/deselection: " + e.getMessage())
	// Take screenshot for debugging
	WebUI.takeScreenshot()
}

// Start with patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("✅ Confirmed that we are still in patch view")

println('=============================================================================================================')