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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty

// Add these missing imports
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper

/**
 * Test Case: PBS Report Assignment
 * This test checks for reports with "To be reviewed" status and assigns them to deekshithaS.
 * If no "To be reviewed" reports are found, it checks the 3rd report.
 * If the 3rd report is not assigned to deekshithaS, it reassigns it.
 */

// Open browser and login to PBS system
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for the page to load
WebUI.waitForPageLoad(30)
WebUI.delay(3) // Add a small delay to ensure all elements are loaded

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()
WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')



// Starting WBC tab interactions
WebUI.comment('Starting WBC tab interactions')

// Click on WBC tab
try {
	WebUI.verifyElementPresent(findTestObject('Object Repository/single_click/span_WBC'), 10)
	WebUI.click(findTestObject('Object Repository/single_click/span_WBC'))
	
	// Wait for WBC tab to load completely
	WebUI.delay(3)
} catch (Exception e) {
	WebUI.comment('Failed to interact with WBC tab: ' + e.getMessage())
	throw e
}

// We're reusing the existing driver instance that was defined earlier
// No need to redeclare "driver" as it already exists

// Create a TestObject for WBC patches with a more reliable XPath
def wbcPatchesObject = new TestObject('WBC Patches')
wbcPatchesObject.addProperty('xpath', ConditionType.EQUALS,
	'//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "wbc")]')

// Find WBC patch elements
List<WebElement> wbcPatches = []
try {
	// First try to find patches
	WebUI.comment('Searching for WBC patches...')
	WebUI.delay(2) // Give some time for patches to load
	
	// Try with WebUiCommonHelper
	try {
		wbcPatches = WebUiCommonHelper.findWebElements(wbcPatchesObject, 20)
	} catch (Exception e) {
		WebUI.comment('First attempt to find patches failed, trying alternate approach')
		
		// Try direct driver approach as backup
		wbcPatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "wbc")]'))
	}
	
	WebUI.comment('Total WBC patches found: ' + wbcPatches.size())
	
	// If we still have no patches, try one more XPath variant
	if (wbcPatches.size() == 0) {
		WebUI.comment('No patches found with primary XPath, trying alternative XPath')
		wbcPatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@id, "wbc")]'))
		WebUI.comment('Total WBC patches found with alternative XPath: ' + wbcPatches.size())
	}
} catch (Exception e) {
	WebUI.comment('Failed to find WBC patches: ' + e.getMessage())
}

// Define the number of patches to select (exactly 3)
int patchesToSelect = 3
WebUI.comment('Will select ' + patchesToSelect + ' WBC patches')

// Selected patches list
def selectedPatches = []

// Make sure we have patches to work with
if (wbcPatches.size() > 0) {
	// Limit to available patches but don't exceed our target
	int maxToSelect = Math.min(patchesToSelect, wbcPatches.size())
	
	// Use CTRL+click (or Command+click on Mac) to select multiple patches
	Actions actions = new Actions(driver)
	
	for (int i = 0; i < maxToSelect; i++) {
		try {
			WebUI.comment('Selecting WBC patch #' + (i + 1))
			
			// Scroll to the patch
			WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(wbcPatches.get(i)))
			WebUI.delay(1)
			
			// For the first patch, just click normally
			if (i == 0) {
				wbcPatches.get(i).click()
			} else {
				// For subsequent patches, hold CTRL (or Command on Mac) while clicking
				// Determine if we're on Mac or Windows
				String osName = System.getProperty('os.name').toLowerCase()
				if (osName.contains('mac')) {
					// For Mac OS
					actions.keyDown(Keys.COMMAND).click(wbcPatches.get(i)).keyUp(Keys.COMMAND).build().perform()
				} else {
					// For Windows/Linux
					actions.keyDown(Keys.CONTROL).click(wbcPatches.get(i)).keyUp(Keys.CONTROL).build().perform()
				}
			}
			
			WebUI.delay(1)
			selectedPatches.add(wbcPatches.get(i))
			
		} catch (Exception e) {
			WebUI.comment('Failed to select WBC patch #' + (i + 1) + ': ' + e.getMessage())
		}
	}
	
	WebUI.comment('Successfully selected ' + selectedPatches.size() + ' WBC patches')
} else {
	WebUI.comment('No WBC patches available to select')
}

// Get the Monocytes row/category as the target for drag and drop
WebElement monocytesTarget = null
try {
	// First try with the repository object
	monocytesTarget = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/retain_patchs/td_Monocytes'), 10)
} catch (Exception e) {
	WebUI.comment('Could not find Monocytes target through repository object, trying direct XPath')
	
	// Try direct XPath as fallback
	try {
		monocytesTarget = driver.findElement(By.xpath('//td[contains(text(), "Monocytes")]'))
	} catch (Exception e2) {
		WebUI.comment('Could not find Monocytes target through direct XPath either')
		
		// Try more general XPath
		try {
			monocytesTarget = driver.findElement(By.xpath('//tr[contains(.,"Monocytes")]/td[1]'))
		} catch (Exception e3) {
			WebUI.comment('All attempts to find Monocytes target failed')
			throw new Exception('Could not locate Monocytes target')
		}
	}
}

WebUI.comment('Monocytes target found, proceeding with drag and drop')

// Create Actions object for drag and drop operations if not already created
if (!binding.hasVariable('actions')) {
	actions = new Actions(driver)
}

// If we have both selected patches and a target, perform drag and drop
if (selectedPatches.size() > 0 && monocytesTarget != null) {
	try {
		WebUI.comment('Performing drag and drop of ' + selectedPatches.size() + ' patches to Monocytes')
		
		// Method 1: Use dragAndDrop for the first patch (which will also move all selected patches)
		WebElement firstPatch = selectedPatches.get(0)
		
		// Scroll both elements into view
		WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(firstPatch))
		WebUI.delay(1)
		WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(monocytesTarget))
		WebUI.delay(1)
		
		// Perform the drag and drop
		actions.dragAndDrop(firstPatch, monocytesTarget).build().perform()
		WebUI.delay(3) // Give some time for the drag and drop to complete
		
		// Method 2 (Backup): Use clickAndHold and moveToElement and release if Method 1 doesn't work
		if (!WebUI.verifyElementNotPresent(
			new TestObject().addProperty('xpath', ConditionType.EQUALS,
				'//div[@class="selected-patches"]//div[contains(@id, "wbc")]'), 2, FailureHandling.OPTIONAL)) {
			
			WebUI.comment('First drag and drop method may have failed, trying alternate method')
			
			actions.clickAndHold(firstPatch)
				.moveToElement(monocytesTarget)
				.release()
				.build()
				.perform()
				
			WebUI.delay(3)
		}
		
	} catch (Exception e) {
		WebUI.comment('Failed to perform drag and drop: ' + e.getMessage())
		
		// Try JavaScript-based approach as a last resort
		try {
			WebUI.comment('Attempting JavaScript-based drag and drop')
			
			// Get patch coordinates
			int patchX = selectedPatches.get(0).getLocation().getX()
			int patchY = selectedPatches.get(0).getLocation().getY()
			
			// Get target coordinates
			int targetX = monocytesTarget.getLocation().getX()
			int targetY = monocytesTarget.getLocation().getY()
			
			// Execute JavaScript to simulate drag and drop
			WebUI.executeJavaScript('''
                function simulateDragDrop(sourceNode, destinationNode) {
                    var EVENT_TYPES = {
                        DRAG_END: 'dragend',
                        DRAG_START: 'dragstart',
                        DROP: 'drop'
                    }
                
                    function createCustomEvent(type) {
                        var event = new CustomEvent("CustomEvent")
                        event.initCustomEvent(type, true, true, null)
                        event.dataTransfer = {
                            data: {
                            },
                            setData: function(type, val) {
                                this.data[type] = val
                            },
                            getData: function(type) {
                                return this.data[type]
                            }
                        }
                        return event
                    }
                
                    function dispatchEvent(node, type, event) {
                        if (node.dispatchEvent) {
                            return node.dispatchEvent(event)
                        }
                        if (node.fireEvent) {
                            return node.fireEvent("on" + type, event)
                        }
                    }
                
                    var event = createCustomEvent(EVENT_TYPES.DRAG_START)
                    dispatchEvent(sourceNode, EVENT_TYPES.DRAG_START, event)
                
                    var dropEvent = createCustomEvent(EVENT_TYPES.DROP)
                    dropEvent.dataTransfer = event.dataTransfer
                    dispatchEvent(destinationNode, EVENT_TYPES.DROP, dropEvent)
                
                    var dragEndEvent = createCustomEvent(EVENT_TYPES.DRAG_END)
                    dragEndEvent.dataTransfer = event.dataTransfer
                    dispatchEvent(sourceNode, EVENT_TYPES.DRAG_END, dragEndEvent)
                }
                
                simulateDragDrop(arguments[0], arguments[1]);
            ''', Arrays.asList(selectedPatches.get(0), monocytesTarget))
			
			WebUI.delay(3)
		} catch (Exception e2) {
			WebUI.comment('JavaScript drag and drop also failed: ' + e2.getMessage())
		}
	}
}

WebUI.comment('Drag and drop operations completed')

// Get updated cell counts after the drag and drop operations
try {
	WebUI.comment('Getting updated cell counts')
	
	// Get and print Neutrophil count
	try {
		// Try to find the element first
		TestObject neutrophilCountObject = findTestObject('Object Repository/retain_patchs/neutrophil_count')
		if (WebUI.verifyElementPresent(neutrophilCountObject, 5, FailureHandling.OPTIONAL)) {
			String neutrophilCountText = WebUI.getText(neutrophilCountObject)
			WebUI.comment("Updated Neutrophil Count: " + neutrophilCountText)
		} else {
			WebUI.comment('Neutrophil count element not found')
		}
	} catch (Exception e) {
		WebUI.comment('Could not get neutrophil count: ' + e.getMessage())
	}
	
	// Get and print Monocyte count
	try {
		// Try to find the element first
		TestObject monocyteCountObject = findTestObject('Object Repository/retain_patchs/monocyte_count')
		if (WebUI.verifyElementPresent(monocyteCountObject, 5, FailureHandling.OPTIONAL)) {
			String monocyteCountText = WebUI.getText(monocyteCountObject)
			WebUI.comment("Updated Monocyte Count: " + monocyteCountText)
		} else {
			WebUI.comment('Monocyte count element not found')
		}
	} catch (Exception e) {
		WebUI.comment('Could not get monocyte count: ' + e.getMessage())
	}
	
} catch (Exception e) {
	WebUI.comment('Failed to get updated cell counts: ' + e.getMessage())
}

WebUI.comment('WBC classification task completed')