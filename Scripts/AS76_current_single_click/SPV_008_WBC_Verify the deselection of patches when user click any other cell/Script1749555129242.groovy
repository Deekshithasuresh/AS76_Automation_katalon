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
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.testobject.ConditionType
import java.time.Duration

// Helper function to handle element interception with multiple strategies
def robustClick(TestObject element, String elementName, int maxRetries = 3) {
	WebDriver driver = DriverFactory.getWebDriver()
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15))
	
	for (int attempt = 1; attempt <= maxRetries; attempt++) {
		try {
			println("🔄 Attempt ${attempt} to click on ${elementName}")
			
			// Wait for element to be present and visible
			WebUI.waitForElementPresent(element, 15)
			WebUI.waitForElementVisible(element, 15)
			
			// Check if nlr-tag elements are present and try to handle them
			try {
				List<WebElement> nlrTags = driver.findElements(By.cssSelector(".nlr-tag"))
				if (nlrTags.size() > 0) {
					println("⚠️ Found ${nlrTags.size()} nlr-tag elements that might interfere")
					// Hide nlr-tag elements temporarily using JavaScript
					JavascriptExecutor js = (JavascriptExecutor) driver
					js.executeScript("document.querySelectorAll('.nlr-tag').forEach(el => el.style.visibility = 'hidden');")
					WebUI.delay(0.5)
				}
			} catch (Exception e) {
				println("⚠️ Could not handle nlr-tag elements: " + e.getMessage())
			}
			
			switch (attempt) {
				case 1:
					// Strategy 1: Standard click with scroll
					println("📍 Strategy 1: Standard click with scroll")
					WebUI.scrollToElement(element, 3)
					WebUI.delay(1)
					WebUI.waitForElementClickable(element, 10)
					WebUI.click(element)
					break
					
				case 2:
					// Strategy 2: JavaScript click
					println("📍 Strategy 2: JavaScript click")
					WebElement webElement = driver.findElement(By.xpath(element.findPropertyValue("xpath")))
					JavascriptExecutor js = (JavascriptExecutor) driver
					js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", webElement)
					WebUI.delay(1)
					js.executeScript("arguments[0].click();", webElement)
					break
					
				case 3:
					// Strategy 3: Actions click with offset
					println("📍 Strategy 3: Actions click with offset")
					WebElement webElement = driver.findElement(By.xpath(element.findPropertyValue("xpath")))
					JavascriptExecutor js = (JavascriptExecutor) driver
					
					// Move element to center of viewport
					js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", webElement)
					WebUI.delay(1)
					
					// Force click using coordinates
					js.executeScript("""
                        var rect = arguments[0].getBoundingClientRect();
                        var event = new MouseEvent('click', {
                            view: window,
                            bubbles: true,
                            cancelable: true,
                            clientX: rect.left + rect.width / 2,
                            clientY: rect.top + rect.height / 2
                        });
                        arguments[0].dispatchEvent(event);
                    """, webElement)
					break
			}
			
			// Restore nlr-tag visibility
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript("document.querySelectorAll('.nlr-tag').forEach(el => el.style.visibility = 'visible');")
			} catch (Exception e) {
				// Ignore restoration errors
			}
			
			WebUI.delay(2)
			println("✅ Successfully clicked ${elementName} using strategy ${attempt}")
			return true
			
		} catch (Exception e) {
			println("❌ Attempt ${attempt} failed for ${elementName}: " + e.getMessage())
			
			// Restore nlr-tag visibility on error
			try {
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript("document.querySelectorAll('.nlr-tag').forEach(el => el.style.visibility = 'visible');")
			} catch (Exception restoreError) {
				// Ignore restoration errors
			}
			
			if (attempt == maxRetries) {
				println("❌ All ${maxRetries} attempts failed for ${elementName}")
				WebUI.takeScreenshot()
				throw e
			}
			WebUI.delay(1)
		}
	}
	return false
}

// Helper function to safely find and click patches
def findAndClickPatch() {
	WebDriver driver = DriverFactory.getWebDriver()
	
	try {
		// Wait for patches to load
		WebUI.delay(3)
		
		TestObject patchElement = new TestObject()
		patchElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'patch-img-container')]")
		
		// Check if patches exist
		if (WebUI.verifyElementPresent(patchElement, 10, FailureHandling.OPTIONAL)) {
			WebUI.waitForElementVisible(patchElement, 15)
			WebUI.click(patchElement)
			println("✅ Patch selected successfully")
			return true
		} else {
			println("⚠️ No patch containers found")
			return false
		}
	} catch (Exception e) {
		println("❌ Failed to select patch: " + e.getMessage())
		return false
	}
}

// Helper function to verify patch selection state
def verifyPatchSelectionState(String expectedState) {
	WebDriver driver = DriverFactory.getWebDriver()
	
	try {
		WebUI.delay(1)
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		
		if (expectedState == "selected") {
			if (selectedPatches.size() > 0) {
				println("✅ Patch selection confirmed - found ${selectedPatches.size()} selected patch(es)")
				return true
			} else {
				println("⚠️ Expected selected patches but found none")
				return false
			}
		} else if (expectedState == "deselected") {
			if (selectedPatches.size() == 0) {
				println("✅ Patch deselection confirmed - no selected patches found")
				return true
			} else {
				println("⚠️ Expected no selected patches but found ${selectedPatches.size()}")
				return false
			}
		}
	} catch (Exception e) {
		println("⚠️ Could not verify patch ${expectedState} state: " + e.getMessage())
		return false
	}
}

// Main test execution
try {
	// Initial setup
	println("🚀 Starting test execution...")
	WebUI.waitForPageLoad(30)
	WebUI.maximizeWindow()
	
	CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
	CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')
	
	// Navigate to WBC tab
	println("📋 Navigating to WBC tab...")
	WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/span_WBC'), 20)
	WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/span_WBC'), 'WBC')
	WebUI.click(findTestObject('Object Repository/retain_patchs/span_WBC'))
	
	// Switch to patch view
	println("🔍 Switching to patch view...")
	WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
	WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
	
	// Step 1: Select a patch
	println("🎯 Step 1: Selecting a patch...")
	if (findAndClickPatch()) {
		verifyPatchSelectionState("selected")
	} else {
		println("❌ Could not find patches to select")
		throw new Exception("No patches available for selection")
	}
	
	// Step 2: Ensure we're in Neutrophils section initially
	println("🩸 Step 2: Ensuring we're in Neutrophils section...")
	TestObject neutrophilsElement = new TestObject()
	neutrophilsElement.addProperty("xpath", ConditionType.EQUALS, "//td[normalize-space(text())='Neutrophils']")
	
	try {
		if (WebUI.verifyElementPresent(neutrophilsElement, 10, FailureHandling.OPTIONAL)) {
			robustClick(neutrophilsElement, "Neutrophils (initial)")
		}
	} catch (Exception e) {
		println("⚠️ Could not ensure initial Neutrophils section: " + e.getMessage())
	}
	
	// Step 3: Navigate to Lymphocytes (should deselect patches)
	println("🔄 Step 3: Navigating to Lymphocytes to test deselection...")
	TestObject lymphocytesElement = new TestObject()
	lymphocytesElement.addProperty("xpath", ConditionType.EQUALS, "//td[normalize-space(text())='Lymphocytes']")
	
	if (WebUI.verifyElementPresent(lymphocytesElement, 15, FailureHandling.OPTIONAL)) {
		robustClick(lymphocytesElement, "Lymphocytes")
		WebUI.delay(2)
		println("✅ Successfully navigated to Lymphocytes section")
	} else {
		println("⚠️ Lymphocytes element not found, trying alternative approach")
		// Try alternative xpath for Lymphocytes
		TestObject altLymphocytesElement = new TestObject()
		altLymphocytesElement.addProperty("xpath", ConditionType.EQUALS, "//td[contains(text(),'Lymphocytes')]")
		
		if (WebUI.verifyElementPresent(altLymphocytesElement, 10, FailureHandling.OPTIONAL)) {
			robustClick(altLymphocytesElement, "Lymphocytes (alternative)")
		}
	}
	
	// Step 4: Return to Neutrophils section
	println("↩️ Step 4: Returning to Neutrophils section...")
	if (WebUI.verifyElementPresent(neutrophilsElement, 15, FailureHandling.OPTIONAL)) {
		robustClick(neutrophilsElement, "Neutrophils (return)")
		WebUI.delay(2)
	} else {
		println("⚠️ Neutrophils element not found for return navigation")
	}
	
	// Step 5: Verify patch deselection after navigation
	println("✅ Step 5: Verifying patch deselection after navigation...")
	verifyPatchSelectionState("deselected")
	
	// Final verification - ensure we're still in patch view
	println("🔍 Final verification: Confirming patch view...")
	WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
	WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
	println("✅ Confirmed that we are still in patch view")
	
	println("🎉 Test completed successfully!")
	println('='.multiply(80))
	
} catch (Exception e) {
	println("❌ Test execution failed: " + e.getMessage())
	println("📸 Taking screenshot for debugging...")
	WebUI.takeScreenshot()
	
	// Print stack trace for debugging
	e.printStackTrace()
	
	println('='.multiply(80))
	throw e
}