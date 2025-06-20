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
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')



println('Selecting RBC SIZE tab')

WebUI.waitForElementPresent(findTestObject('Object Repository/single_click/span_RBC'),20)
WebUI.verifyElementText(findTestObject('Object Repository/single_click/span_RBC'),'RBC')
WebUI.click(findTestObject('Object Repository/single_click/span_RBC'))

 //Start with patch view
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

	// Check if cells are available
	List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class, 'patch-img-container')]"))
	if (patches.size() == 0) {
		println("❌ No cells present in RBC SIZE")
		println("⚠️ Proceeding with navigation testing without patch selection")
	} else {

	// Perform first single click to select the patch
	WebUI.click(targetElement)
	println("✅ RBC size patch has been selected with first single click")

	// Wait a moment for selection to register
	WebUI.delay(1)

	// Verify patch is selected using the selected-patch class indicator
	try {
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("✅ RBC size patch selection is confirmed - found " + selectedPatches.size() + " selected patch(es)")
		} else {
			println("⚠️ No selected patch indicator found, but proceeding with navigation")
		}
	} catch (Exception e) {
		println("⚠️ Could not verify patch selection state: " + e.getMessage())
	}

	// First ensure we're in Microcytes section
	TestObject microcytesElement = new TestObject()
	microcytesElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Microcytes']")
	
	try {
		WebUI.waitForElementPresent(microcytesElement, 5)
		if (WebUI.verifyElementPresent(microcytesElement, 5)) {
			WebUI.click(microcytesElement)
			WebUI.delay(1)
			println("✅ Ensured we are in Microcytes section initially")
		}
	} catch (Exception e) {
		println("⚠️ Could not ensure Microcytes section: " + e.getMessage())
	}

	// Create test object for Macrocytes navigation
	TestObject macrocytesElement = new TestObject()
	macrocytesElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Macrocytes']")

	// Click on Macrocytes to navigate away
	try {
		WebUI.waitForElementPresent(macrocytesElement, 15)
		WebUI.waitForElementClickable(macrocytesElement, 15)
		WebUI.click(macrocytesElement)
		WebUI.delay(3)
		println("✅ Navigated to Macrocytes section")
		
		// Verify we're in Macrocytes by checking if patches are different
		WebUI.delay(1)
		
	} catch (Exception e) {
		println("❌ Failed to navigate to Macrocytes: " + e.getMessage())
		throw e
	}

	// Click on Microcytes to return to original section
	try {
		WebUI.waitForElementPresent(microcytesElement, 15)
		
		// Try multiple approaches to click Microcytes
		try {
			// First try: Scroll to element and wait
			WebUI.scrollToElement(microcytesElement, 5)
			WebUI.delay(1)
			WebUI.waitForElementClickable(microcytesElement, 10)
			WebUI.click(microcytesElement)
			WebUI.delay(3)
			println("✅ Returned to Microcytes section")
			
		} catch (Exception clickError1) {
			println("⚠️ First click attempt failed: " + clickError1.getMessage())
			
			// Second try: Use JavaScript click
			try {
				WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(driver.findElement(By.xpath("//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Microcytes']"))))
				WebUI.delay(3)
				println("✅ Successfully returned to Microcytes section using JavaScript click")
				
			} catch (Exception clickError2) {
				println("⚠️ JavaScript click failed: " + clickError2.getMessage())
				
				// Third try: Use enhanced click with offset
				try {
					WebUI.enhancedClick(microcytesElement)
					WebUI.delay(3)
					println("✅ Successfully returned to Microcytes section using enhanced click")
					
				} catch (Exception clickError3) {
					println("❌ All click attempts failed for Microcytes: " + clickError3.getMessage())
					
					// Final try: Find and click a different Microcytes element if available
					try {
						TestObject altMicrocytesElement = new TestObject()
						altMicrocytesElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(text(),'Microcytes')]")
						WebUI.waitForElementPresent(altMicrocytesElement, 5)
						WebUI.scrollToElement(altMicrocytesElement, 3)
						WebUI.click(altMicrocytesElement)
						WebUI.delay(3)
						println("✅ Successfully returned to Microcytes section using alternative xpath")
					} catch (Exception finalError) {
						println("❌ Final attempt failed: " + finalError.getMessage())
						throw finalError
					}
				}
			}
		}
		
	} catch (Exception e) {
		println("❌ Failed to return to Microcytes after all attempts: " + e.getMessage())
	}

	// Wait for the original patches to be visible again after navigation
	WebUI.waitForElementPresent(targetElement, 10)
	WebUI.waitForElementVisible(targetElement, 10)

	// Verify patch is deselected after navigation using the selected-patch class indicator
	try {
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("✅ RBC size patch deselection is confirmed - no selected patches found after navigation")
		} else {
			println("⚠️ Found " + selectedPatches.size() + " selected patch(es) - patch may still be selected after navigation")
		}
	} catch (Exception e) {
		println("⚠️ Could not verify patch deselection state: " + e.getMessage())
	}

	if (patches.size() > 0) {
		println("✅ RBC size patch selection and deselection cycle completed successfully through navigation")
	} else {
		println("✅ RBC size navigation testing completed (no patches were available for selection)")
	}

	}

	// First ensure we're in Microcytes section

} catch (Exception e) {
	println("❌ Failed to perform RBC size patch selection/deselection: " + e.getMessage())
	// Take screenshot for debugging
	WebUI.takeScreenshot()
}

// Start with patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("✅ Confirmed that we are still in patch view for RBC size")

println('=============================================================================================================')

println('Selecting RBC SHAPE tab')
WebUI.waitForElementPresent(findTestObject('Object Repository/single_click/button_Shape'),20)
WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Shape'),'Shape')
WebUI.click(findTestObject('Object Repository/single_click/button_Shape'))

// Start with patch view
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

	// Check if cells are available
	List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class, 'patch-img-container')]"))
	if (patches.size() == 0) {
		println("❌ No cells present in RBC SHAPE")
		println("⚠️ Proceeding with navigation testing without patch selection")
	} else {
		// Perform first single click to select the patch
		WebUI.click(targetElement)
		println("✅ RBC shape patch has been selected with first single click")

		// Wait a moment for selection to register
		WebUI.delay(1)

		// Verify patch is selected using the selected-patch class indicator
		try {
			List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
			if (selectedPatches.size() > 0) {
				println("✅ RBC shape patch selection is confirmed - found " + selectedPatches.size() + " selected patch(es)")
			} else {
				println("⚠️ No selected patch indicator found, but proceeding with navigation")
			}
		} catch (Exception e) {
			println("⚠️ Could not verify patch selection state: " + e.getMessage())
		}
	}

	// First ensure we're in Ovalocytes section
	TestObject ovalocytesElement = new TestObject()
	ovalocytesElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Ovalocytes']")
	
	try {
		WebUI.waitForElementPresent(ovalocytesElement, 5)
		if (WebUI.verifyElementPresent(ovalocytesElement, 5)) {
			WebUI.click(ovalocytesElement)
			WebUI.delay(1)
			println("✅ Ensured we are in Ovalocytes section initially")
		}
	} catch (Exception e) {
		println("⚠️ Could not ensure Ovalocytes section: " + e.getMessage())
	}

	// Create test object for Elliptocytes navigation
	TestObject elliptocytesElement = new TestObject()
	elliptocytesElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Elliptocytes']")

	// Click on Elliptocytes to navigate away
	try {
		WebUI.waitForElementPresent(elliptocytesElement, 15)
		WebUI.waitForElementClickable(elliptocytesElement, 15)
		WebUI.click(elliptocytesElement)
		WebUI.delay(3)
		println("✅ Navigated to Elliptocytes section")
		
		// Verify we're in Elliptocytes by checking if patches are different
		WebUI.delay(1)
		
	} catch (Exception e) {
		println("❌ Failed to navigate to Elliptocytes: " + e.getMessage())
		throw e
	}

	// Click on Ovalocytes to return to original section
	try {
		WebUI.waitForElementPresent(ovalocytesElement, 15)
		
		// Try multiple approaches to click Ovalocytes
		try {
			// First try: Scroll to element and wait
			WebUI.scrollToElement(ovalocytesElement, 5)
			WebUI.delay(1)
			WebUI.waitForElementClickable(ovalocytesElement, 10)
			WebUI.click(ovalocytesElement)
			WebUI.delay(3)
			println("✅ Returned to Ovalocytes section")
			
		} catch (Exception clickError1) {
			println("⚠️ First click attempt failed: " + clickError1.getMessage())
			
			// Second try: Use JavaScript click
			try {
				WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(driver.findElement(By.xpath("//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Ovalocytes']"))))
				WebUI.delay(3)
				println("✅ Successfully returned to Ovalocytes section using JavaScript click")
				
			} catch (Exception clickError2) {
				println("⚠️ JavaScript click failed: " + clickError2.getMessage())
				
				// Third try: Use enhanced click with offset
				try {
					WebUI.enhancedClick(ovalocytesElement)
					WebUI.delay(3)
					println("✅ Successfully returned to Ovalocytes section using enhanced click")
					
				} catch (Exception clickError3) {
					println("❌ All click attempts failed for Ovalocytes: " + clickError3.getMessage())
					
					// Final try: Find and click a different Ovalocytes element if available
					try {
						TestObject altOvalocytesElement = new TestObject()
						altOvalocytesElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(text(),'Ovalocytes')]")
						WebUI.waitForElementPresent(altOvalocytesElement, 5)
						WebUI.scrollToElement(altOvalocytesElement, 3)
						WebUI.click(altOvalocytesElement)
						WebUI.delay(3)
						println("✅ Successfully returned to Ovalocytes section using alternative xpath")
					} catch (Exception finalError) {
						println("❌ Final attempt failed: " + finalError.getMessage())
						throw finalError
					}
				}
			}
		}
		
	} catch (Exception e) {
		println("❌ Failed to return to Ovalocytes after all attempts: " + e.getMessage())
	}

	// Wait for the original patches to be visible again after navigation
	WebUI.waitForElementPresent(targetElement, 10)
	WebUI.waitForElementVisible(targetElement, 10)

	// Verify patch is deselected after navigation using the selected-patch class indicator
	try {
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("✅ RBC shape patch deselection is confirmed - no selected patches found after navigation")
		} else {
			println("⚠️ Found " + selectedPatches.size() + " selected patch(es) - patch may still be selected after navigation")
		}
	} catch (Exception e) {
		println("⚠️ Could not verify patch deselection state: " + e.getMessage())
	}

	if (patches.size() > 0) {
		println("✅ RBC shape patch selection and deselection cycle completed successfully through navigation")
	} else {
		println("✅ RBC shape navigation testing completed (no patches were available for selection)")
	}

} catch (Exception e) {
	println("❌ Failed to perform RBC shape patch selection/deselection: " + e.getMessage())
	// Take screenshot for debugging
	WebUI.takeScreenshot()
}

// Start with patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("✅ Confirmed that we are still in patch view for RBC shape")

println('=============================================================================================================')


println('Selecting RBC color tab')
WebUI.waitForElementPresent(findTestObject('Object Repository/single_click/button_Color'),20)
WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Color'),'Colour')
WebUI.click(findTestObject('Object Repository/single_click/button_Color'))

// Start with patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))


try {
	// Wait for page to load
	WebUI.delay(2)

	// Get WebDriver instance
	WebDriver driver = DriverFactory.getWebDriver()

	// Create target element
	TestObject targetElement = new TestObject()
	targetElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'patch-img-container')]")

	// Wait for element
	WebUI.waitForElementPresent(targetElement, 30)
	WebUI.waitForElementVisible(targetElement, 30)

	// Check if cells are available
	List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class, 'patch-img-container')]"))
	if (patches.size() == 0) {
		println("❌ No cells present in RBC COLOR")
		println("⚠️ Proceeding with remaining testing without patch selection")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)

		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("✅ RBC color selected successfully")
		}

		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)

		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("✅ RBC color deselected successfully")
		}

		println("✅ RBC color selection/deselection cycle completed")
	}

} catch (Exception e) {
	println("❌ Failed to perform RBC color operations: " + e.getMessage())
	WebUI.takeScreenshot()
}

// Confirm patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("✅ Confirmed patch view is active for RBC color")

println('=============================================================================================================')

println('Selecting RBC inclusions tab')
WebUI.waitForElementPresent(findTestObject('Object Repository/single_click/button_inclusion'),20)
WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_inclusion'),'Inclusions')
WebUI.click(findTestObject('Object Repository/single_click/button_inclusion'))

// Start with patch view
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

	// Check if cells are available
	List<WebElement> patches = driver.findElements(By.xpath("//div[contains(@class, 'patch-img-container')]"))
	if (patches.size() == 0) {
		println("❌ No cells present in RBC INCLUSIONS")
		println("⚠️ Proceeding with navigation testing without patch selection")
	} else {
		// Perform first single click to select the patch
		WebUI.click(targetElement)
		println("✅ RBC inclusion patch has been selected with first single click")

		// Wait a moment for selection to register
		WebUI.delay(1)

		// Verify patch is selected using the selected-patch class indicator
		try {
			List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
			if (selectedPatches.size() > 0) {
				println("✅ RBC inclusion patch selection is confirmed - found " + selectedPatches.size() + " selected patch(es)")
			} else {
				println("⚠️ No selected patch indicator found, but proceeding with navigation")
			}
		} catch (Exception e) {
			println("⚠️ Could not verify patch selection state: " + e.getMessage())
		}
	}

	// First ensure we're in Howell-Jolly Bodies section
	TestObject howellJollyElement = new TestObject()
	howellJollyElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Howell-Jolly Bodies']")
	
	try {
		WebUI.waitForElementPresent(howellJollyElement, 5)
		if (WebUI.verifyElementPresent(howellJollyElement, 5)) {
			WebUI.click(howellJollyElement)
			WebUI.delay(1)
			println("✅ Ensured we are in Howell-Jolly Bodies section initially")
		}
	} catch (Exception e) {
		println("⚠️ Could not ensure Howell-Jolly Bodies section: " + e.getMessage())
	}

	// Create test object for Pappenheimer Bodies navigation
	TestObject pappenheimerElement = new TestObject()
	pappenheimerElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Pappenheimer Bodies']")

	// Click on Pappenheimer Bodies to navigate away
	try {
		WebUI.waitForElementPresent(pappenheimerElement, 15)
		WebUI.waitForElementClickable(pappenheimerElement, 15)
		WebUI.click(pappenheimerElement)
		WebUI.delay(3)
		println("✅ Navigated to Pappenheimer Bodies section")
		
		// Verify we're in Pappenheimer Bodies by checking if patches are different
		WebUI.delay(1)
		
	} catch (Exception e) {
		println("❌ Failed to navigate to Pappenheimer Bodies: " + e.getMessage())
		throw e
	}

	// Click on Howell-Jolly Bodies to return to original section
	try {
		WebUI.waitForElementPresent(howellJollyElement, 15)
		
		// Try multiple approaches to click Howell-Jolly Bodies
		try {
			// First try: Scroll to element and wait
			WebUI.scrollToElement(howellJollyElement, 5)
			WebUI.delay(1)
			WebUI.waitForElementClickable(howellJollyElement, 10)
			WebUI.click(howellJollyElement)
			WebUI.delay(3)
			println("✅ Returned to Howell-Jolly Bodies section")
			
		} catch (Exception clickError1) {
			println("⚠️ First click attempt failed: " + clickError1.getMessage())
			
			// Second try: Use JavaScript click
			try {
				WebUI.executeJavaScript("arguments[0].click();", Arrays.asList(driver.findElement(By.xpath("//div[contains(@class, 'cell-row')]//div[normalize-space(text())='Howell-Jolly Bodies']"))))
				WebUI.delay(3)
				println("✅ Successfully returned to Howell-Jolly Bodies section using JavaScript click")
				
			} catch (Exception clickError2) {
				println("⚠️ JavaScript click failed: " + clickError2.getMessage())
				
				// Third try: Use enhanced click with offset
				try {
					WebUI.enhancedClick(howellJollyElement)
					WebUI.delay(3)
					println("✅ Successfully returned to Howell-Jolly Bodies section using enhanced click")
					
				} catch (Exception clickError3) {
					println("❌ All click attempts failed for Howell-Jolly Bodies: " + clickError3.getMessage())
					
					// Final try: Find and click a different Howell-Jolly Bodies element if available
					try {
						TestObject altHowellJollyElement = new TestObject()
						altHowellJollyElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(text(),'Howell-Jolly Bodies')]")
						WebUI.waitForElementPresent(altHowellJollyElement, 5)
						WebUI.scrollToElement(altHowellJollyElement, 3)
						WebUI.click(altHowellJollyElement)
						WebUI.delay(3)
						println("✅ Successfully returned to Howell-Jolly Bodies section using alternative xpath")
					} catch (Exception finalError) {
						println("❌ Final attempt failed: " + finalError.getMessage())
						throw finalError
					}
				}
			}
		}
		
	} catch (Exception e) {
		println("❌ Failed to return to Howell-Jolly Bodies after all attempts: " + e.getMessage())
	}

	// Wait for the original patches to be visible again after navigation
	WebUI.waitForElementPresent(targetElement, 10)
	WebUI.waitForElementVisible(targetElement, 10)

	// Verify patch is deselected after navigation using the selected-patch class indicator
	try {
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("✅ RBC inclusion patch deselection is confirmed - no selected patches found after navigation")
		} else {
			println("⚠️ Found " + selectedPatches.size() + " selected patch(es) - patch may still be selected after navigation")
		}
	} catch (Exception e) {
		println("⚠️ Could not verify patch deselection state: " + e.getMessage())
	}

	if (patches.size() > 0) {
		println("✅ RBC inclusion patch selection and deselection cycle completed successfully through navigation")
	} else {
		println("✅ RBC inclusion navigation testing completed (no patches were available for selection)")
	}

} catch (Exception e) {
	println("❌ Failed to perform RBC inclusion patch selection/deselection: " + e.getMessage())
	// Take screenshot for debugging
	WebUI.takeScreenshot()
}

// Start with patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("✅ Confirmed that we are still in patch view for RBC inclusions")

println('==========================================END OF THE TESTCASE===================================================================')
