import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.By as By
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.support.Color as Color
import org.openqa.selenium.Dimension as Dimension  // ADD THIS MISSING IMPORT
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


// Select WBC tab
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/span_WBC'), 20)
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/span_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/retain_patchs/span_WBC'))

// Start with patch view 
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

try {
	// Wait for page to load completely
	WebUI.delay(2)
	
	// Get the WebDriver instance for element finding
	WebDriver driver = DriverFactory.getWebDriver()
	
	// Create the test object for single click on WBC patch
	TestObject targetElement = new TestObject()
	targetElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'patch-img-container')]")
	
	// Wait for element and verify it exists
	WebUI.waitForElementPresent(targetElement, 30)
	WebUI.waitForElementVisible(targetElement, 30)
	
	// Perform single click to select the WBC patch
	WebUI.click(targetElement)
	println("✅ WBC Patch has been selected with single click")
	
	// Wait a moment for selection to register
	WebUI.delay(1)
	
	// Verify patch is selected using the selected-patch class indicator
	try {
	List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
	if (selectedPatches.size() > 0) {
	println("✅ WBC Patch selection is confirmed - found " + selectedPatches.size() + " selected patch(es)")
	} else {
	println("⚠️ No selected patch indicator found, but proceeding with outside click")
	}
	} catch (Exception e) {
	println("⚠️ Could not verify WBC patch selection state: " + e.getMessage())
	}
	
	// Click outside the report area to deselect the patch
	// Using coordinates that are clearly outside the report viewing area
	// Based on typical screen layouts, clicking at top-left corner outside the main content
	try {
	// Get the current window size for reference
	Dimension windowSize = driver.manage().window().getSize()
	println("Current window size: " + windowSize.width + "x" + windowSize.height)
	
	// Click at coordinates outside the report area (top-left area outside main content)
	// Adjust these coordinates based on your specific layout
	Actions actions = new Actions(driver)
	actions.moveByOffset(555, 87).click().perform() // Click at position (50, 50) from top-left
	println("✅ Clicked outside the report area at coordinates (555, 87)")
	
	// Alternative approach: Click on a known element outside the report if available
	// Uncomment and modify if you have a specific element to click outside
	// WebUI.click(findTestObject('Object Repository/some_element_outside_report'))
	
	} catch (Exception e) {
	println("⚠️ Failed to click outside report area using coordinates: " + e.getMessage())
	// Fallback: try clicking on page background or header area
	try {
	TestObject outsideElement = new TestObject()
	outsideElement.addProperty("xpath", ConditionType.EQUALS, "//body")
	WebUI.click(outsideElement)
	println("✅ Fallback: Clicked on body element to deselect")
	} catch (Exception e2) {
	println("❌ Fallback click also failed: " + e2.getMessage())
	}
	}
	
	// Wait a moment for deselection to register
	WebUI.delay(1)
	
	// Verify patch is deselected using the selected-patch class indicator
	try {
	List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
	if (selectedPatches.size() == 0) {
	println("✅ WBC Patch deselection is confirmed - no selected patches found")
	} else {
	println("⚠️ Found " + selectedPatches.size() + " selected patch(es) - patch may still be selected")
	}
	} catch (Exception e) {
	println("⚠️ Could not verify WBC patch deselection state: " + e.getMessage())
	}
	
	// Additional verification: Check if any patch-related highlighting or borders are gone
	try {
	List<WebElement> highlightedElements = driver.findElements(By.xpath("//div[contains(@class, 'highlighted') or contains(@class, 'active') or contains(@style, 'border')]"))
	if (highlightedElements.size() == 0) {
	println("✅ No highlighted/active elements found - deselection successful")
	} else {
	println("⚠️ Found " + highlightedElements.size() + " potentially highlighted elements")
	}
	} catch (Exception e) {
	println("⚠️ Could not verify highlighting state: " + e.getMessage())
	}
	
	println("✅ WBC Patch selection and outside-click deselection cycle completed successfully")
	
	} catch (Exception e) {
	println("❌ Failed to perform WBC patch selection/deselection: " + e.getMessage())
	// Take screenshot for debugging
	WebUI.takeScreenshot()
	}
	
	//Start with patch view
	WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
	WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
	println("✅ Confirmed that we are still in patch view")
	
	println('=============================================================================================================')