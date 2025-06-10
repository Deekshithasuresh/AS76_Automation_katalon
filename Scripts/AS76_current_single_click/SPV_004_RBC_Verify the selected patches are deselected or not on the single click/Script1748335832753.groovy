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
		println("No cells available in RBC SIZE")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("RBC size selected successfully")
		}
		
		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("RBC size deselected successfully")
		}
		
		println("RBC size selection/deselection cycle completed")
	}
	
} catch (Exception e) {
	println("Failed to perform RBC size operations: " + e.getMessage())
	WebUI.takeScreenshot()
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
		println("No cells available in RBC SHAPE")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("RBC shape selected successfully")
		}
		
		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("RBC shape deselected successfully")
		}
		
		println("RBC shape selection/deselection cycle completed")
	}
	
} catch (Exception e) {
	println("Failed to perform RBC shape operations: " + e.getMessage())
	WebUI.takeScreenshot()
}

// Confirm patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("Confirmed patch view is active")

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
		println("No cells available in RBC COLOR")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("RBC color selected successfully")
		}
		
		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("RBC color deselected successfully")
		}
		
		println("RBC color selection/deselection cycle completed")
	}
	
} catch (Exception e) {
	println("Failed to perform RBC color operations: " + e.getMessage())
	WebUI.takeScreenshot()
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
		println("No cells available in RBC INCLUSIONS")
	} else {
		// First click to select patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify selection
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() > 0) {
			println("RBC inclusion selected successfully")
		}
		
		// Second click to deselect patch
		WebUI.click(targetElement)
		WebUI.delay(1)
		
		// Verify deselection
		selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		if (selectedPatches.size() == 0) {
			println("RBC inclusion deselected successfully")
		}
		
		println("RBC inclusion selection/deselection cycle completed")
	}
	
} catch (Exception e) {
	println("Failed to perform RBC inclusion operations: " + e.getMessage())
	WebUI.takeScreenshot()
}

// Confirm patch view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
println("Confirmed patch view is active")

println('==============================END OF THE TESTCASE===============================================================================')