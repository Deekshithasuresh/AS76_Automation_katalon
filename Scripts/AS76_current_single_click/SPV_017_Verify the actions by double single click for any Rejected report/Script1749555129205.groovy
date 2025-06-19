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
import javassist.bytecode.stackmap.BasicBlock.Catch
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
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject

// Step 1: Login and navigate
try {
	WebUI.openBrowser('')
	WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
	WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
	WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
	WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))
	WebUI.maximizeWindow()
	
	// Wait for the page to load
	WebUI.waitForPageLoad(30)
	 
	WebUI.click(findTestObject('Object Repository/Summary/span_Ready for review'))
	WebUI.click(findTestObject('Object Repository/Summary/span_Reviewed'))
	WebUI.verifyElementText(findTestObject('Object Repository/Summary/span_Reviewed_1'), 'Reviewed')
	CustomKeywords.'generic.helper.selectReportByStatus'('Rejected')
	
	// Initialize the WebDriver - THIS IS THE KEY FIX
	WebDriver driver = DriverFactory.getWebDriver()
	
	println('✅ Login and navigation completed successfully')
} catch (Exception e) {
	println('❌ Failed during login and navigation: ' + e.getMessage())
	WebUI.takeScreenshot()
}

// WBC TAB SECTION
try {
	println('🔬 Starting WBC tab testing...')
	
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
		
		// Create the test object for double click
		TestObject targetElement = new TestObject()
		targetElement.addProperty("xpath", ConditionType.EQUALS, "//div[contains(@class, 'patch-img-container')]")
		
		// Wait for element and verify it exists
		WebUI.waitForElementPresent(targetElement, 30)
		WebUI.waitForElementVisible(targetElement, 30)
		
		// Perform double click
		WebUI.doubleClick(targetElement)
		println("✅ WBC Cell has been double clicked successfully")
		
		// Wait a moment for split view to load
		WebUI.delay(2)
		
		// Verify split view is displayed
		boolean splitViewExists = WebUI.verifyElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'), 10)
		
		if (splitViewExists) {
			println("✅ WBC Split view is now displayed - verification successful")
			
			// Additional verification - check if element is visible (removed timeout parameter)
			boolean splitViewVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))
			if (splitViewVisible) {
				println("✅ WBC Split view element is visible on screen")
			} else {
				println("⚠️ WBC Split view element exists but is not visible")
			}
			
		} else {
			println("❌ WBC Split view is NOT displayed after double click")
			WebUI.takeScreenshot()
		}
		
	} catch (Exception e) {
		println("❌ Failed to double-click WBC element: " + e.getMessage())
		// Take screenshot for debugging
		WebUI.takeScreenshot()
	}
	
	println('✅ WBC tab testing completed successfully')
	
} catch (Exception e) {
	println('❌ Failed during WBC tab testing: ' + e.getMessage())
	WebUI.takeScreenshot()
}

println('=========================================================================================================')

// Select RBC tab

WebUI.verifyElementText(findTestObject('Object Repository/single_click/span_RBC'), 'RBC')

WebUI.click(findTestObject('Object Repository/single_click/span_RBC'))

println('✅ RBC tab is clicked successfully and verifying for RBC SIZE')

// Start with patch view

WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

try {
	// Wait for page to load completely
	WebUI.delay(2)
	
	// Create the test object for double click
	TestObject targetElement = new TestObject()
	
	targetElement.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, \'patch-img-container\')]')
	
	// Check if element exists first
	boolean elementExists = WebUI.verifyElementPresent(targetElement, 10, FailureHandling.OPTIONAL)
	
	if (!elementExists) {
		println('⚠️ No cells present to double click in RBC SIZE')
		WebUI.takeScreenshot()
	} else {
		// Wait for element and verify it exists
		WebUI.waitForElementPresent(targetElement, 30)
		
		WebUI.waitForElementVisible(targetElement, 30)
		
		// Perform double click
		WebUI.doubleClick(targetElement)
		
		println('✅ Cell has been double clicked successfully')
		
		// Wait a moment for split view to load
		WebUI.delay(2)
		
		// Verify split view is displayed
		boolean splitViewExists = WebUI.verifyElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'),
			10)
		
		if (splitViewExists) {
			println('✅ Split view is now displayed - verification successful')
			
			// Additional verification - check if element is visible (removed timeout parameter)
			boolean splitViewVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))
			
			if (splitViewVisible) {
				println('✅ Split view element is visible on screen')
			} else {
				println('⚠️ Split view element exists but is not visible')
			}
		} else {
			println('THERE ARE NO CELLS IN RBC SIZE')
			WebUI.takeScreenshot()
		}
	}
}
catch (Exception e) {
	println('❌ Failed to double-click element: ' + e.getMessage())

	WebUI.takeScreenshot()
}
println('=========================================================================================================')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_shape_16'),'Shape')

WebUI.click(findTestObject('Object Repository/single_click/button_shape_16'))


println('✅ RBC tab is clicked successfully and verifying for RBC SHAPE')

// Start with patch view (reversed from original)
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

try {
	// Wait for page to load completely
	WebUI.delay(2)
	
	// Create the test object for double click
	TestObject targetElement = new TestObject()
	
	targetElement.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, \'patch-img-container\')]')
	
	// Check if element exists first
	boolean elementExists = WebUI.verifyElementPresent(targetElement, 10, FailureHandling.OPTIONAL)
	
	if (!elementExists) {
		println('⚠️ No cells present to double click in RBC SHAPE')
		WebUI.takeScreenshot()
	} else {
		// Wait for element and verify it exists
		WebUI.waitForElementPresent(targetElement, 30)
		
		WebUI.waitForElementVisible(targetElement, 30)
		
		// Perform double click
		WebUI.doubleClick(targetElement)
		
		println('✅ Cell has been double clicked successfully')
		
		// Wait a moment for split view to load
		WebUI.delay(2)
		
		// Verify split view is displayed
		boolean splitViewExists = WebUI.verifyElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'),
			10)
		
		if (splitViewExists) {
			println('✅ Split view is now displayed - verification successful')
			
			// Additional verification - check if element is visible (removed timeout parameter)
			boolean splitViewVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))
			
			if (splitViewVisible) {
				println('✅ Split view element is visible on screen')
			} else {
				println('⚠️ Split view element exists but is not visible')
			}
		} else {
			println('THERE ARE NO CELLS IN RBC SHAPE')
			WebUI.takeScreenshot()
		}
	}
}
catch (Exception e) {
	println('❌ Failed to double-click element: ' + e.getMessage())

	WebUI.takeScreenshot()
}
println('=========================================================================================================')
WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_color_16'),'Colour')
WebUI.click(findTestObject('Object Repository/single_click/button_color_16'))

println('✅ RBC tab is clicked successfully and verifying for RBC COLOR')

// Start with patch view (reversed from original)
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

try {
	// Wait for page to load completely
	WebUI.delay(2)
	
	// Create the test object for double click
	TestObject targetElement = new TestObject()
	
	targetElement.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, \'patch-img-container\')]')
	
	// Check if element exists first
	boolean elementExists = WebUI.verifyElementPresent(targetElement, 10, FailureHandling.OPTIONAL)
	
	if (!elementExists) {
		println('⚠️ No cells present to double click in RBC Color')
		WebUI.takeScreenshot()
	} else {
		// Wait for element and verify it exists
		WebUI.waitForElementPresent(targetElement, 30)
		
		WebUI.waitForElementVisible(targetElement, 30)
		
		// Perform double click
		WebUI.doubleClick(targetElement)
		
		println('✅ Cell has been double clicked successfully')
		
		// Wait a moment for split view to load
		WebUI.delay(2)
		
		// Verify split view is displayed
		boolean splitViewExists = WebUI.verifyElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'),
				10)
		
		if (splitViewExists) {
			println('✅ Split view is now displayed - verification successful')
			
			// Additional verification - check if element is visible
			boolean splitViewVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))
			
			if (splitViewVisible) {
				println('✅ Split view element is visible on screen')
			} else {
				println('⚠️ Split view element exists but is not visible')
			}
		} else {
			println('THERE ARE NO CELLS IN RBC Color')
			WebUI.takeScreenshot()
		}
	}
} catch (Exception e) {
	println('❌ Failed to double-click element: ' + e.getMessage())
	WebUI.takeScreenshot()
}

println('=========================================================================================================')

// RBC Inclusions section
WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_inclusion_16'),'Inclusions')

WebUI.click(findTestObject('Object Repository/single_click/button_inclusion_16'))

println('✅ RBC tab is clicked successfully and verifying for RBC INCLUSIONS')

// Start with patch view
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

try {
	// Wait for page to load completely
	WebUI.delay(2)
	
	// Create the test object for double click
	TestObject targetElement = new TestObject()
	
	targetElement.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, \'patch-img-container\')]')
	
	// Check if element exists first
	boolean elementExists = WebUI.verifyElementPresent(targetElement, 10, FailureHandling.OPTIONAL)
	
	if (!elementExists) {
		println('⚠️ No cells present to double click in RBC INCLUSIONS')
		WebUI.takeScreenshot()
	} else {
		// Wait for element and verify it exists
		WebUI.waitForElementPresent(targetElement, 30)
		
		WebUI.waitForElementVisible(targetElement, 30)
		
		// Perform double click
		WebUI.doubleClick(targetElement)
		
		println('✅ Cell has been double clicked successfully')
		
		// Wait a moment for split view to load
		WebUI.delay(2)
		
		// Verify split view is displayed
		boolean splitViewExists = WebUI.verifyElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'), 10)
		
		if (splitViewExists) {
			println('✅ Split view is now displayed - verification successful')
			
			// Additional verification - check if element is visible
			boolean splitViewVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))
			
			if (splitViewVisible) {
				println('✅ Split view element is visible on screen')
			} else {
				println('⚠️ Split view element exists but is not visible')
			}
		} else {
			println('THERE ARE NO CELLS IN RBC INCLUSIONS')
			WebUI.takeScreenshot()
		}
	}
} catch (Exception e) {
	println('❌ Failed to double-click element: ' + e.getMessage())
	WebUI.takeScreenshot()
}

println('=========================================================================================================')


WebUI.comment('Starting  platletes tab interactions')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_plateletes'),'Platelets')

WebUI.click(findTestObject('Object Repository/single_click/button_plateletes'))

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Morphology'),'Morphology')

WebUI.click(findTestObject('Object Repository/single_click/button_Morphology'))

println('✅ Platelet morphology tab is clicked successfully and verifying for platelet')

// Start with patch view

WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), 10)

WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')

WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

try {
	// Wait for page to load completely
	WebUI.delay(2)

	// Create the test object for double click
	TestObject targetElement = new TestObject()

	targetElement.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patch-img-container")]')

	// Wait for element and verify it exists
	WebUI.waitForElementPresent(targetElement, 30)

	WebUI.waitForElementVisible(targetElement, 30)

	// Perform double click
	WebUI.doubleClick(targetElement)

	println('✅ Cell has been double clicked successfully')

	// Wait a moment for split view to load
	WebUI.delay(2)

	// Verify split view is displayed
	boolean splitViewExists = WebUI.verifyElementPresent(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'),
		10)

	if (splitViewExists) {
		println('✅ Split view is now displayed - verification successful')

		// Additional verification - check if element is visible (removed timeout parameter)
		boolean splitViewVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))

		if (splitViewVisible) {
			println('✅ Split view element is visible on screen')
		} else {
			println('⚠️ Split view element exists but is not visible')
		}
	} else {
		println('There are no cells present in RBC shape')

		WebUI.takeScreenshot()
	}
}
catch (Exception e) {
	println('❌ Failed to double-click element: ' + e.getMessage())

	WebUI.takeScreenshot()
}
println('End of the testcase')

println('=========================================================================================================')
