import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


// ADD THIS MISSING IMPORT

// Login to the application
WebUI.openBrowser('')

WebUI.navigateToUrl('https://pbsreview.as76.local/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
WebUI.maximizeWindow()
//WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// Wait for the page to load
WebUI.waitForPageLoad(30)




println('Selecting RBC SIZE tab')

WebUI.waitForElementPresent(findTestObject('Object Repository/single_click/span_RBC'),20)
WebUI.verifyElementText(findTestObject('Object Repository/single_click/span_RBC'),'RBC')
WebUI.click(findTestObject('Object Repository/single_click/span_RBC'))

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
		println("FAILURE: No cells available in RBC SIZE")
		WebUI.takeScreenshot()
		KeywordUtil.markFailed("Test failed: No patches found in RBC SIZE tab")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("RBC size selected successfully")
		} else {
			println("WARNING: RBC size selection verification failed")
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Test failed: RBC size patch selection was not verified")
		}
		
		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("RBC size deselected successfully")
		} else {
			println("WARNING: RBC size deselection verification failed")
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Test failed: RBC size patch deselection was not verified")
		}
		
		println("RBC size selection/deselection cycle completed")
	}
	
} catch (Exception e) {
	println("Failed to perform RBC size operations: " + e.getMessage())
	WebUI.takeScreenshot()
	KeywordUtil.markFailed("Test failed with exception in RBC SIZE: " + e.getMessage())
}

// Confirm patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("Confirmed patch view is active")

println('=============================================================================================================')

println('Selecting RBC SHAPE tab')
WebUI.waitForElementPresent(findTestObject('Object Repository/single_click/button_Shape'),20)
WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Shape'),'Shape')
WebUI.click(findTestObject('Object Repository/single_click/button_Shape'))

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
		println("FAILURE: No cells available in RBC SHAPE")
		WebUI.takeScreenshot()
		KeywordUtil.markFailed("Test failed: No patches found in RBC SHAPE tab")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("RBC shape selected successfully")
		} else {
			println("WARNING: RBC shape selection verification failed")
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Test failed: RBC shape patch selection was not verified")
		}
		
		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("RBC shape deselected successfully")
		} else {
			println("WARNING: RBC shape deselection verification failed")
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Test failed: RBC shape patch deselection was not verified")
		}
		
		println("RBC shape selection/deselection cycle completed")
	}
	
} catch (Exception e) {
	println("Failed to perform RBC shape operations: " + e.getMessage())
	WebUI.takeScreenshot()
	KeywordUtil.markFailed("Test failed with exception in RBC SHAPE: " + e.getMessage())
}

// Confirm patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("Confirmed patch view is active")

println('=============================================================================================================')

println('Selecting RBC color tab')
WebUI.waitForElementPresent(findTestObject('Object Repository/single_click/button_Color'),20)
WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Color'),'Color')
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
		println("FAILURE: No cells available in RBC COLOR")
		WebUI.takeScreenshot()
		KeywordUtil.markFailed("Test failed: No patches found in RBC COLOR tab")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("RBC color selected successfully")
		} else {
			println("WARNING: RBC color selection verification failed")
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Test failed: RBC color patch selection was not verified")
		}
		
		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("RBC color deselected successfully")
		} else {
			println("WARNING: RBC color deselection verification failed")
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Test failed: RBC color patch deselection was not verified")
		}
		
		println("RBC color selection/deselection cycle completed")
	}
	
} catch (Exception e) {
	println("Failed to perform RBC color operations: " + e.getMessage())
	WebUI.takeScreenshot()
	KeywordUtil.markFailed("Test failed with exception in RBC COLOR: " + e.getMessage())
}

// Confirm patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("Confirmed patch view is active")

println('=============================================================================================================')

println('Selecting RBC inclusions tab')
WebUI.waitForElementPresent(findTestObject('Object Repository/single_click/button_inclusion'),20)
WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_inclusion'),'Inclusions')
WebUI.click(findTestObject('Object Repository/single_click/button_inclusion'))

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
		println("FAILURE: No cells available in RBC INCLUSIONS")
		WebUI.takeScreenshot()
		KeywordUtil.markFailed("Test failed: No patches found in RBC INCLUSIONS tab")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("RBC inclusion selected successfully")
		} else {
			println("WARNING: RBC inclusion selection verification failed")
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Test failed: RBC inclusion patch selection was not verified")
		}
		
		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("RBC inclusion deselected successfully")
		} else {
			println("WARNING: RBC inclusion deselection verification failed")
			WebUI.takeScreenshot()
			KeywordUtil.markFailed("Test failed: RBC inclusion patch deselection was not verified")
		}
		
		println("RBC inclusion selection/deselection cycle completed")
	}
	
} catch (Exception e) {
	println("Failed to perform RBC inclusion operations: " + e.getMessage())
	WebUI.takeScreenshot()
	KeywordUtil.markFailed("Test failed with exception in RBC INCLUSIONS: " + e.getMessage())
}

// Confirm patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("Confirmed patch view is active")

println('==============================END OF THE TESTCASE===============================================================================')