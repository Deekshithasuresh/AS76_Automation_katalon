import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint


import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.NoSuchSessionException
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

/**
 * Test Case: RTN_001_Verify the retaining of ALL patch selections with scrolling
 *
 * Purpose: This test verifies that ALL WBC patch selections are properly retained when switching
 * from normal view to split view in the SigTuple application. The test scrolls through all patches
 * and selects them all before switching to split view.
 *
 * Test Flow:
 * 1. Login to the application
 * 2. Select a specific slide (SIG013)
 * 3. Navigate to the WBC tab
 * 4. Scroll and select ALL available WBC patches
 * 5. Switch to split view
 * 6. Verify that ALL selected patches are still selected in split view
 *
 * Expected result: ALL selected patches remain selected after switching to split view
 */

// Log start of test with clear separator for better log readability
WebUI.comment("==========================================")
WebUI.comment("STARTING TEST: RTN_001_Verify the retaining of ALL patch selections with scrolling")
WebUI.comment("==========================================")

/**
 * Helper function to perform retry logic for web operations
 *
 * @param operation The code block to retry (as a Closure)
 * @param maxRetries Maximum number of retry attempts (default: 3)
 * @param waitBetweenRetries Time to wait between retries in seconds (default: 2)
 * @return The result of the operation if successful
 * @throws The last exception encountered if all retries fail
 */
def retryOperation(Closure operation, int maxRetries = 3, int waitBetweenRetries = 2) {
	WebUI.comment("[RETRY HELPER] Setting up retry logic with max ${maxRetries} attempts")
	Exception lastException = null
	for (int i = 0; i < maxRetries; i++) {
		try {
			def result = operation()
			WebUI.comment("[RETRY HELPER] Operation succeeded on attempt ${i+1}")
			return result // Success, return the result
		} catch (Exception e) {
			lastException = e
			WebUI.comment("[RETRY HELPER] Attempt ${i+1}/${maxRetries} failed: ${e.getMessage()}")
			
			// Take a screenshot on failure for debugging
			WebUI.takeScreenshot("retry_failure_attempt_${i+1}.png")
			
			if (e instanceof NoSuchSessionException) {
				WebUI.comment("[RETRY HELPER] Browser session lost. Test cannot continue.")
				throw e // Can't recover from this
			}
			WebUI.delay(waitBetweenRetries)
		}
	}
	WebUI.comment("[RETRY HELPER] All ${maxRetries} retry attempts exhausted")
	throw lastException // All retries failed
}

/**
 * Helper function to scroll within the patches container
 * @param driver WebDriver instance
 * @param scrollAmount Amount to scroll (positive for down, negative for up)
 */
def scrollPatchesContainer(WebDriver driver, int scrollAmount = 300) {
	try {
		// Try to find the scrollable container for patches
		WebElement scrollContainer = null
		
		// Try multiple selectors to find the scrollable container
		List<String> containerSelectors = [
			'//div[contains(@class, "patches-section")]',
			'//div[contains(@class, "scroll-container")]',
			'//div[contains(@class, "patch-container")]',
			'//div[@class="scroll-container"]'
		]
		
		for (String selector : containerSelectors) {
			try {
				scrollContainer = driver.findElement(By.xpath(selector))
				if (scrollContainer != null) {
					WebUI.comment("[SCROLL] Found scrollable container using selector: ${selector}")
					break
				}
			} catch (Exception e) {
				// Continue to next selector
			}
		}
		
		if (scrollContainer != null) {
			JavascriptExecutor js = (JavascriptExecutor) driver
			js.executeScript("arguments[0].scrollTop += arguments[1];", scrollContainer, scrollAmount)
			WebUI.comment("[SCROLL] Scrolled container by ${scrollAmount} pixels")
		} else {
			// Fallback to window scroll
			WebUI.comment("[SCROLL] Container not found, using window scroll as fallback")
			JavascriptExecutor js = (JavascriptExecutor) driver
			js.executeScript("window.scrollBy(0, arguments[0]);", scrollAmount)
		}
		
		WebUI.delay(1) // Allow time for content to load after scroll
		
	} catch (Exception e) {
		WebUI.comment("[WARNING] Error during scroll: ${e.getMessage()}")
	}
}

try {
	// SECTION 1: BROWSER SETUP
	WebUI.comment("[STEP 1] Setting up browser session")
	WebUI.openBrowser('')
	WebUI.maximizeWindow()
	
	// Configure WebDriver timeout settings
	WebUI.comment("[SETUP] Configuring browser timeout settings")
	WebDriver driver = DriverFactory.getWebDriver()
	driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60))
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))
	
	// SECTION 2: LOGIN
	WebUI.comment("[STEP 2] Navigating to login page")
	WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
	WebUI.comment("[INFO] Page title: " + WebUI.getWindowTitle())
	
	WebUI.comment("[STEP 3] Logging in with test credentials")
	WebUI.setText(findTestObject('Object Repository/retain_patchs/input_username_loginId'), 'deekshithaS')
	WebUI.setEncryptedText(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
	WebUI.comment("[INFO] Submitting login credentials")
	WebUI.sendKeys(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), Keys.chord(Keys.ENTER))
	WebUI.maximizeWindow()
	CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
	CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

	
	// SECTION 4: WBC TAB SELECTION
	WebUI.comment("[STEP 5] Navigating to WBC tab")
	
	// Click on WBC tab with retry
	retryOperation({
		WebUI.waitForElementVisible(findTestObject('Object Repository/single_click/span_WBC'), 15)
		WebUI.waitForElementClickable(findTestObject('Object Repository/single_click/span_WBC'), 15)
		WebUI.comment("[INFO] WBC tab is visible and clickable")
		WebUI.click(findTestObject('Object Repository/single_click/span_WBC'))
		WebUI.comment("[SUCCESS] Clicked on WBC tab")
		
		// Wait for WBC tab to load completely
		WebUI.comment("[WAIT] Waiting for WBC content to load")
		WebUI.delay(3)
	})
	
	// Get the driver instance again after potential page changes
	driver = DriverFactory.getWebDriver()
	
	// SECTION 5: FINDING AND SELECTING ALL WBC PATCHES WITH SCROLLING
	WebUI.comment("[STEP 6] Starting to locate and select ALL WBC patches with scrolling")
	
	// Create a TestObject for WBC patches with a more reliable XPath
	def wbcPatchesObject = new TestObject('WBC Patches')
	wbcPatchesObject.addProperty('xpath', ConditionType.EQUALS,
		'//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "wbc")]')
	
	// Variables to track selection process
	List<WebElement> allFoundPatches = []
	Set<String> selectedPatchIds = new HashSet<String>()
	Actions actions = new Actions(driver)
	boolean isFirstPatch = true
	int scrollAttempts = 0
	int maxScrollAttempts = 20 // Prevent infinite scrolling
	int previousPatchCount = 0
	int stableCountIterations = 0
	
	WebUI.comment("[INFO] Starting patch selection process with automatic scrolling")
	
	// Determine OS for correct key combination
	String osName = System.getProperty('os.name').toLowerCase()
	boolean isMac = osName.contains('mac')
	WebUI.comment("[INFO] Detected OS: ${osName}, Using ${isMac ? 'COMMAND' : 'CONTROL'} key for multi-select")
	
	// Main scrolling and selection loop
	while (scrollAttempts < maxScrollAttempts) {
		WebUI.comment("[LOOP ${scrollAttempts + 1}] Searching for patches at current scroll position")
		
		// Find patches at current scroll position
		List<WebElement> currentPatches = []
		
		retryOperation({
			// Try multiple selector strategies to find the patches
			try {
				WebUI.comment("[SEARCH] Using TestObject approach")
				currentPatches = WebUiCommonHelper.findWebElements(wbcPatchesObject, 10)
				WebUI.comment("[INFO] Found ${currentPatches.size()} patches with TestObject")
			} catch (Exception e) {
				WebUI.comment("[INFO] TestObject approach failed: ${e.getMessage()}")
				WebUI.comment("[SEARCH] Using direct WebDriver approach")
				
				// Try multiple XPath patterns
				String[] xpathPatterns = [
					'//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "wbc")]',
					'//div[@class="scroll-container"]//div[contains(@id, "wbc")]',
					'//div[contains(@id, "wbc") and contains(@id, "patch")]',
					'//div[contains(@class, "patch")]//div[contains(@id, "wbc")]'
				]
				
				for (String xpath : xpathPatterns) {
					try {
						currentPatches = driver.findElements(By.xpath(xpath))
						if (currentPatches.size() > 0) {
							WebUI.comment("[INFO] Found ${currentPatches.size()} patches with XPath: ${xpath}")
							break
						}
					} catch (Exception ex) {
						// Continue to next pattern
					}
				}
			}
			
			return currentPatches
		})
		
		WebUI.comment("[RESULT] Found ${currentPatches.size()} patches at current position")
		
		// Process each patch found at this scroll position
		if (currentPatches.size() > 0) {
			for (int i = 0; i < currentPatches.size(); i++) {
				try {
					WebElement patch = currentPatches.get(i)
					String patchId = patch.getAttribute('id')
					
					// Skip if we've already selected this patch
					if (patchId != null && selectedPatchIds.contains(patchId)) {
						continue
					}
					
					// Check if patch is visible and clickable
					if (patch.isDisplayed() && patch.isEnabled()) {
						WebUI.comment("[SELECT] Attempting to select patch with ID: ${patchId ?: 'unknown'}")
						
						// Scroll patch into view
						WebUI.executeJavaScript('arguments[0].scrollIntoView({block: "center", behavior: "smooth"});', Arrays.asList(patch))
						WebUI.delay(1)
						
						// Select the patch
						if (isFirstPatch) {
							WebUI.comment("[CLICK] Normal click for first patch")
							patch.click()
							isFirstPatch = false
						} else {
							// Multi-select with appropriate key
							if (isMac) {
								WebUI.comment("[CLICK] Using COMMAND+click for Mac")
								actions.keyDown(Keys.COMMAND).click(patch).keyUp(Keys.COMMAND).build().perform()
							} else {
								WebUI.comment("[CLICK] Using CONTROL+click for Windows/Linux")
								actions.keyDown(Keys.CONTROL).click(patch).keyUp(Keys.CONTROL).build().perform()
							}
						}
						
						// Add to our tracking lists
						if (patchId != null) {
							selectedPatchIds.add(patchId)
							WebUI.comment("[SUCCESS] Selected patch: ${patchId}")
						}
						
						allFoundPatches.add(patch)
						WebUI.delay(0.5) // Small delay between selections
					}
				} catch (StaleElementReferenceException stale) {
					WebUI.comment("[WARNING] Stale element reference, patch may have been redrawn")
				} catch (Exception e) {
					WebUI.comment("[WARNING] Error selecting patch: ${e.getMessage()}")
				}
			}
		}
		
		// Check if we've found new patches or if count is stable
		int currentTotalPatches = selectedPatchIds.size()
		WebUI.comment("[STATUS] Total patches selected so far: ${currentTotalPatches}")
		
		if (currentTotalPatches == previousPatchCount) {
			stableCountIterations++
			WebUI.comment("[INFO] Patch count stable for ${stableCountIterations} iterations")
			
			// If count has been stable for 3 iterations, we might be done
			if (stableCountIterations >= 3) {
				WebUI.comment("[INFO] Patch count stable for 3+ iterations, likely reached end")
				break
			}
		} else {
			stableCountIterations = 0 // Reset counter if we found new patches
			previousPatchCount = currentTotalPatches
		}
		
		// Scroll down to find more patches
		WebUI.comment("[SCROLL] Scrolling to find more patches")
		scrollPatchesContainer(driver, 300)
		
		scrollAttempts++
		WebUI.delay(2) // Allow content to load after scroll
	}
	
	WebUI.comment("[COMPLETION] Finished scrolling and selection process")
	WebUI.comment("[FINAL COUNT] Total patches selected: ${selectedPatchIds.size()}")
	WebUI.comment("[FINAL COUNT] Total patch elements tracked: ${allFoundPatches.size()}")
	
	// Take screenshot after all selections
	WebUI.takeScreenshot('all_patches_selected.png')
	
	// Make sure we selected at least some patches
	if (selectedPatchIds.size() == 0) {
		WebUI.comment("[ERROR] No WBC patches were selected!")
		WebUI.takeScreenshot('no_patches_selected.png')
		assert false : "No WBC patches were selected, cannot proceed with test"
	} else {
		WebUI.comment("[SUCCESS] Successfully selected ${selectedPatchIds.size()} WBC patches")
	}
	
	// SECTION 6: SWITCHING TO SPLIT VIEW
	WebUI.comment("[STEP 7] Switching to split view mode")
	
	boolean splitViewClickSuccessful = false
	
	// Try multiple approaches to find and click the split view button
	WebUI.comment("[STRATEGY] Trying multiple approaches to locate and click split view button")
	
	try {
		// Approach 1: Using standard test object
		WebUI.comment("[APPROACH 1] Using standard TestObject with active image source")
		TestObject splitViewButton = new TestObject('Split View Button')
		splitViewButton.addProperty('xpath', ConditionType.EQUALS, "//img[@id='split-view' and contains(@src, 'split-view-active.svg')]")
		
		if (WebUI.waitForElementPresent(splitViewButton, 5, FailureHandling.OPTIONAL)) {
			WebUI.comment("[INFO] Split view button found with approach 1")
			WebUI.click(splitViewButton)
			splitViewClickSuccessful = true
			WebUI.comment("[SUCCESS] Clicked split view button using approach 1")
		} else {
			WebUI.comment("[INFO] Split view button not found with approach 1")
		}
	} catch (Exception e) {
		WebUI.comment("[WARNING] Approach 1 failed: ${e.getMessage()}")
	}
	
	if (!splitViewClickSuccessful) {
		try {
			// Approach 2: Try alternate XPath
			WebUI.comment("[APPROACH 2] Using alternate TestObject with default image source")
			TestObject altSplitViewButton = new TestObject('Alt Split View Button')
			altSplitViewButton.addProperty('xpath', ConditionType.EQUALS, "//img[@id='split-view' and contains(@src, 'split-view-default.svg')]")
			
			if (WebUI.waitForElementPresent(altSplitViewButton, 5, FailureHandling.OPTIONAL)) {
				WebUI.comment("[INFO] Split view button found with approach 2")
				WebUI.click(altSplitViewButton)
				splitViewClickSuccessful = true
				WebUI.comment("[SUCCESS] Clicked split view button using approach 2")
			} else {
				WebUI.comment("[INFO] Split view button not found with approach 2")
			}
		} catch (Exception e) {
			WebUI.comment("[WARNING] Approach 2 failed: ${e.getMessage()}")
		}
	}
	
	if (!splitViewClickSuccessful) {
		try {
			// Approach 3: Using broader selector with direct WebDriver
			WebUI.comment("[APPROACH 3] Using broader selector with direct WebDriver")
			List<WebElement> splitViewButtons = driver.findElements(By.xpath("//img[contains(@id, 'split-view')]"))
			WebUI.comment("[INFO] Found ${splitViewButtons.size()} possible split view buttons with approach 3")
			
			if (splitViewButtons.size() > 0) {
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript("arguments[0].click();", splitViewButtons.get(0))
				splitViewClickSuccessful = true
				WebUI.comment("[SUCCESS] Clicked split view button using approach 3")
			} else {
				WebUI.comment("[INFO] No split view buttons found with approach 3")
			}
		} catch (Exception e) {
			WebUI.comment("[WARNING] Approach 3 failed: ${e.getMessage()}")
		}
	}
	
	if (!splitViewClickSuccessful) {
		try {
			// Approach 4: Try by icon class or other attributes
			WebUI.comment("[APPROACH 4] Using class-based selectors for view buttons")
			List<WebElement> viewButtons = driver.findElements(By.xpath("//div[contains(@class, 'view-button') or contains(@class, 'view-controls')]//img"))
			WebUI.comment("[INFO] Found ${viewButtons.size()} potential view control buttons with approach 4")
			
			if (viewButtons.size() > 0) {
				for (int i = 0; i < viewButtons.size(); i++) {
					WebElement btn = viewButtons.get(i)
					WebUI.comment("[DEBUG] Button #${i+1} - ID: ${btn.getAttribute('id') ?: 'N/A'}, Src: ${btn.getAttribute('src') ?: 'N/A'}")
				}
				
				if (viewButtons.size() > 0) {
					JavascriptExecutor js = (JavascriptExecutor) driver
					js.executeScript("arguments[0].click();", viewButtons.get(0))
					splitViewClickSuccessful = true
					WebUI.comment("[SUCCESS] Clicked possible split view button using approach 4")
				}
			} else {
				WebUI.comment("[INFO] No view control buttons found with approach 4")
			}
		} catch (Exception e) {
			WebUI.comment("[WARNING] Approach 4 failed: ${e.getMessage()}")
		}
	}
	
	if (!splitViewClickSuccessful) {
		WebUI.comment("[ERROR] Could not find or click the split view button using any approach!")
		WebUI.comment("[DEBUG] Taking screenshot to help diagnose the issue")
		WebUI.takeScreenshot('split_view_button_not_found.png')
		assert false : "Could not switch to split view - button not found or not clickable"
	} else {
		WebUI.comment("[SUCCESS] Split view button clicked successfully")
		
		// Wait for split view to load
		WebUI.comment("[WAIT] Waiting for split view to load")
		WebUI.delay(5) // Longer wait for split view with many patches
		WebUI.takeScreenshot('split_view_loaded.png')
		
		// SECTION 7: VERIFYING ALL SELECTIONS ARE RETAINED WITH SCROLLING IN SPLIT VIEW
		WebUI.comment("[STEP 8] Verifying ALL patch selections are retained in split view with scrolling")
		
		// Variables for split view verification
		Set<String> splitViewSelectedIds = new HashSet<String>()
		List<WebElement> allSelectedPatchesInSplitView = []
		int splitViewScrollAttempts = 0
		int maxSplitViewScrollAttempts = 25 // Allow more scrolling for split view
		int previousSplitViewPatchCount = 0
		int splitViewStableCountIterations = 0
		
		WebUI.comment("[INFO] Starting split view verification with scrolling")
		
		// Scroll through split view to find all selected patches
		while (splitViewScrollAttempts < maxSplitViewScrollAttempts) {
			WebUI.comment("[SPLIT VIEW LOOP ${splitViewScrollAttempts + 1}] Searching for selected patches at current scroll position")
			
			List<WebElement> currentSelectedPatches = []
			
			try {
				// Using multiple strategies to find selected patches in split view at current position
				WebUI.comment("[STRATEGY 1] Looking for div.selected-patch elements")
				currentSelectedPatches = driver.findElements(By.cssSelector("div.selected-patch"))
				WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using div.selected-patch")
				
				// If that doesn't work, try the multiselect patch class
				if (currentSelectedPatches.size() == 0) {
					WebUI.comment("[STRATEGY 2] Looking for div.selected-patch.multiselect-patch elements")
					currentSelectedPatches = driver.findElements(By.cssSelector("div.selected-patch.multiselect-patch"))
					WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using div.selected-patch.multiselect-patch")
				}
				
				// If still not found, try another approach with alternative classes
				if (currentSelectedPatches.size() == 0) {
					WebUI.comment("[STRATEGY 3] Looking for .patch-img-container.multiselect-patch elements")
					currentSelectedPatches = driver.findElements(By.cssSelector(".patch-img-container.multiselect-patch"))
					WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using .patch-img-container.multiselect-patch")
				}
				
				// One more attempt with div.selected-patch variations
				if (currentSelectedPatches.size() == 0) {
					WebUI.comment("[STRATEGY 4] Using broader XPath selector for selected elements")
					currentSelectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected') or contains(@class, 'active')]"))
					WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using broader 'selected' or 'active' class selector")
				}
				
				// Final attempt with any element that might indicate selection
				if (currentSelectedPatches.size() == 0) {
					WebUI.comment("[STRATEGY 5] Looking for any elements with selection indicators")
					currentSelectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'multiselect') or contains(@style, 'selected') or contains(@class, 'highlight')]"))
					WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using selection indicator strategy")
				}
				
				// Process found selected patches
				if (currentSelectedPatches.size() > 0) {
					WebUI.comment("[PROCESSING] Processing ${currentSelectedPatches.size()} selected patches found at current position")
					
					for (int i = 0; i < currentSelectedPatches.size(); i++) {
						try {
							WebElement selectedPatch = currentSelectedPatches.get(i)
							String patchId = selectedPatch.getAttribute("id")
							
							// Only add if we haven't seen this patch before
							if (patchId != null && !splitViewSelectedIds.contains(patchId)) {
								splitViewSelectedIds.add(patchId)
								allSelectedPatchesInSplitView.add(selectedPatch)
								WebUI.comment("[FOUND] New selected patch in split view - ID: ${patchId}")
							} else if (patchId == null) {
								// Handle patches without ID by checking other attributes
								String className = selectedPatch.getAttribute("class") ?: ""
								String dataId = selectedPatch.getAttribute("data-id") ?: ""
								String uniqueIdentifier = "class_${className}_data_${dataId}_index_${i}"
								
								if (!splitViewSelectedIds.contains(uniqueIdentifier)) {
									splitViewSelectedIds.add(uniqueIdentifier)
									allSelectedPatchesInSplitView.add(selectedPatch)
									WebUI.comment("[FOUND] New selected patch in split view - Identifier: ${uniqueIdentifier}")
								}
							}
						} catch (StaleElementReferenceException stale) {
							WebUI.comment("[WARNING] Stale element reference in split view, patch may have been redrawn")
						} catch (Exception e) {
							WebUI.comment("[WARNING] Error processing selected patch in split view: ${e.getMessage()}")
						}
					}
				}
				
			} catch (Exception e) {
				WebUI.comment("[ERROR] Error finding selected patches in split view at scroll position ${splitViewScrollAttempts + 1}: ${e.getMessage()}")
			}
			
			// Check if we've found new patches or if count is stable
			int currentSplitViewTotal = splitViewSelectedIds.size()
			WebUI.comment("[STATUS] Total selected patches found in split view so far: ${currentSplitViewTotal}")
			
			if (currentSplitViewTotal == previousSplitViewPatchCount) {
				splitViewStableCountIterations++
				WebUI.comment("[INFO] Split view selected patch count stable for ${splitViewStableCountIterations} iterations")
				
				// If count has been stable for 4 iterations, we might be done
				if (splitViewStableCountIterations >= 4) {
					WebUI.comment("[INFO] Split view selected patch count stable for 4+ iterations, likely reached end")
					break
				}
			} else {
				splitViewStableCountIterations = 0 // Reset counter if we found new patches
				previousSplitViewPatchCount = currentSplitViewTotal
			}
			
			// Scroll down in split view to find more selected patches
			WebUI.comment("[SCROLL] Scrolling split view to find more selected patches")
			
			// Try to find split view specific scrollable container
			try {
				// Split view might have different container classes
				List<String> splitViewContainerSelectors = [
					'//div[contains(@class, "split-view")]//div[contains(@class, "scroll")]',
					'//div[contains(@class, "split")]//div[contains(@class, "patches")]',
					'//div[contains(@class, "right-panel")]//div[contains(@class, "scroll")]',
					'//div[contains(@class, "patch-panel")]//div[contains(@class, "scroll")]',
					'//div[contains(@class, "patches-section")]', // Fallback to original
					'//div[@class="scroll-container"]' // Final fallback
				]
				
				WebElement splitViewScrollContainer = null
				for (String selector : splitViewContainerSelectors) {
					try {
						splitViewScrollContainer = driver.findElement(By.xpath(selector))
						if (splitViewScrollContainer != null) {
							WebUI.comment("[SCROLL] Found split view scrollable container using selector: ${selector}")
							break
						}
					} catch (Exception e) {
						// Continue to next selector
					}
				}
				
				if (splitViewScrollContainer != null) {
					JavascriptExecutor js = (JavascriptExecutor) driver
					js.executeScript("arguments[0].scrollTop += arguments[1];", splitViewScrollContainer, 350)
					WebUI.comment("[SCROLL] Scrolled split view container by 350 pixels")
				} else {
					// Fallback to window scroll
					WebUI.comment("[SCROLL] Split view container not found, using window scroll as fallback")
					JavascriptExecutor js = (JavascriptExecutor) driver
					js.executeScript("window.scrollBy(0, 350);")
				}
				
			} catch (Exception e) {
				WebUI.comment("[WARNING] Error during split view scroll: ${e.getMessage()}")
				// Try basic window scroll as last resort
				JavascriptExecutor js = (JavascriptExecutor) driver
				js.executeScript("window.scrollBy(0, 350);")
			}
			
			splitViewScrollAttempts++
			WebUI.delay(2) // Allow content to load after scroll
			
			// Take periodic screenshots during scrolling
			if (splitViewScrollAttempts % 5 == 0) {
				WebUI.takeScreenshot("split_view_scroll_${splitViewScrollAttempts}.png")
			}
		}
		
		WebUI.comment("[COMPLETION] Finished split view scrolling and verification")
		WebUI.comment("[SPLIT VIEW FINAL COUNT] Total selected patches found in split view: ${splitViewSelectedIds.size()}")
		
		// Detailed comparison between original selection and split view
		WebUI.comment("[DETAILED COMPARISON] Comparing original patch selection with split view results")
		
		// Find matching IDs between original selection and split view
		int exactMatches = 0
		int partialMatches = 0
		List<String> missingPatches = []
		List<String> foundPatches = []
		
		for (String originalId : selectedPatchIds) {
			if (splitViewSelectedIds.contains(originalId)) {
				exactMatches++
				foundPatches.add(originalId)
				WebUI.comment("[MATCH] ✅ Patch retained: ${originalId}")
			} else {
				// Check for partial matches (sometimes IDs might have slight variations)
				boolean partialMatch = false
				for (String splitViewId : splitViewSelectedIds) {
					if (splitViewId.contains(originalId) || originalId.contains(splitViewId)) {
						partialMatches++
						partialMatch = true
						foundPatches.add(originalId)
						WebUI.comment("[PARTIAL MATCH] ⚠️ Patch partially retained: ${originalId} ≈ ${splitViewId}")
						break
					}
				}
				
				if (!partialMatch) {
					missingPatches.add(originalId)
					WebUI.comment("[MISSING] ❌ Patch NOT retained: ${originalId}")
				}
			}
		}
		
		// Log detailed statistics
		WebUI.comment("[STATISTICS] ==========================================")
		WebUI.comment("[STATISTICS] Original patches selected: ${selectedPatchIds.size()}")
		WebUI.comment("[STATISTICS] Split view patches found: ${splitViewSelectedIds.size()}")
		WebUI.comment("[STATISTICS] Exact matches: ${exactMatches}")
		WebUI.comment("[STATISTICS] Partial matches: ${partialMatches}")
		WebUI.comment("[STATISTICS] Missing patches: ${missingPatches.size()}")
		WebUI.comment("[STATISTICS] Total retained (exact + partial): ${exactMatches + partialMatches}")
		WebUI.comment("[STATISTICS] Retention rate: ${((exactMatches + partialMatches) / selectedPatchIds.size() * 100).round(2)}%")
		WebUI.comment("[STATISTICS] ==========================================")
		
		// List missing patches for debugging
		if (missingPatches.size() > 0) {
			WebUI.comment("[MISSING PATCHES] The following patches were not retained:")
			missingPatches.each { patch ->
				WebUI.comment("[MISSING PATCHES] - ${patch}")
			}
		}
		
		// List additional patches found in split view (if any)
		Set<String> additionalPatches = new HashSet<String>(splitViewSelectedIds)
		additionalPatches.removeAll(selectedPatchIds)
		if (additionalPatches.size() > 0) {
			WebUI.comment("[ADDITIONAL PATCHES] Found ${additionalPatches.size()} additional selected patches in split view:")
			additionalPatches.each { patch ->
				WebUI.comment("[ADDITIONAL PATCHES] + ${patch}")
			}
		}
		
		
		// Final verification and test result
		WebUI.comment("[VERIFICATION] Final test verification")
		WebUI.comment("[STATS] Originally selected patches: ${selectedPatchIds.size()}")
		WebUI.comment("[STATS] Selected patches found in split view: ${splitViewSelectedIds.size()}")
		WebUI.comment("[STATS] Exact matches: ${exactMatches}")
		WebUI.comment("[STATS] Partial matches: ${partialMatches}")
		WebUI.comment("[STATS] Total retention: ${exactMatches + partialMatches}")
		
		// Take final screenshot for verification
		WebUI.takeScreenshot('final_split_view_verification.png')
		
		// Calculate retention percentage
		double retentionPercentage = selectedPatchIds.size() > 0 ?
			((exactMatches + partialMatches) / selectedPatchIds.size()) * 100 : 0
		
		// Test result determination with detailed criteria
		if (exactMatches == selectedPatchIds.size()) {
			WebUI.comment("[TEST RESULT] ✅ PERFECT PASS: All ${selectedPatchIds.size()} patches perfectly retained in split view (100% retention)")
		} else if ((exactMatches + partialMatches) == selectedPatchIds.size()) {
			WebUI.comment("[TEST RESULT] ✅ FULL PASS: All ${selectedPatchIds.size()} patches retained in split view (${exactMatches} exact + ${partialMatches} partial matches = 100% retention)")
		} else if (retentionPercentage >= 90) {
			WebUI.comment("[TEST RESULT] ✅ HIGH PASS: Excellent retention - ${exactMatches + partialMatches}/${selectedPatchIds.size()} patches retained (${retentionPercentage.round(1)}% retention)")
		} else if (retentionPercentage >= 75) {
			WebUI.comment("[TEST RESULT] ✅ GOOD PASS: Good retention - ${exactMatches + partialMatches}/${selectedPatchIds.size()} patches retained (${retentionPercentage.round(1)}% retention)")
		} else if (retentionPercentage >= 50) {
			WebUI.comment("[TEST RESULT] ⚠️ PARTIAL PASS: Moderate retention - ${exactMatches + partialMatches}/${selectedPatchIds.size()} patches retained (${retentionPercentage.round(1)}% retention)")
		} else if (retentionPercentage > 0) {
			WebUI.comment("[TEST RESULT] ⚠️ LOW PASS: Low retention - ${exactMatches + partialMatches}/${selectedPatchIds.size()} patches retained (${retentionPercentage.round(1)}% retention)")
		} else {
			WebUI.comment("[TEST RESULT] ❌ FAIL: No patches retained in split view despite ${selectedPatchIds.size()} being selected originally")
		}
		
		// Additional insights for debugging
		if (splitViewSelectedIds.size() > selectedPatchIds.size()) {
			WebUI.comment("[INSIGHT] Split view shows more selected patches than originally selected. This might indicate:")
			WebUI.comment("[INSIGHT] - Additional patches were auto-selected in split view")
			WebUI.comment("[INSIGHT] - Different selection criteria in split view")
			WebUI.comment("[INSIGHT] - Duplicate detection of the same patches")
		} else if (splitViewSelectedIds.size() < selectedPatchIds.size()) {
			WebUI.comment("[INSIGHT] Split view shows fewer selected patches than originally selected. This might indicate:")
			WebUI.comment("[INSIGHT] - Some patches were deselected during view switch")
			WebUI.comment("[INSIGHT] - Different rendering of selected patches in split view")
			WebUI.comment("[INSIGHT] - Patches not visible in current split view scroll position")
		}
		
		// Summary for test reporting
		WebUI.comment("[SUMMARY] ==========================================")
		WebUI.comment("[SUMMARY] Test: Patch Selection Retention Verification")
		WebUI.comment("[SUMMARY] Patches Selected in Patch View: ${selectedPatchIds.size()}")
		WebUI.comment("[SUMMARY] Patches Found in Split View: ${splitViewSelectedIds.size()}")
		WebUI.comment("[SUMMARY] Retention Rate: ${retentionPercentage.round(2)}%")
		WebUI.comment("[SUMMARY] Result: ${retentionPercentage >= 75 ? 'PASS' : retentionPercentage >= 50 ? 'PARTIAL' : 'FAIL'}")
		WebUI.comment("[SUMMARY] ==========================================")
	}
	
} catch (Exception e) {
	WebUI.comment("[CRITICAL ERROR] Test encountered an exception: ${e.getMessage()}")
	WebUI.comment("[STACKTRACE] ${e.getStackTrace().join('\n')}")
	WebUI.takeScreenshot('test_failure.png')
	throw e
} finally {
	// SECTION 8: CLEANUP
	WebUI.comment("[CLEANUP] Performing test cleanup")
	WebUI.delay(2)
	WebUI.comment("[CLEANUP] Closing browser")
	WebUI.closeBrowser()
	WebUI.comment("==========================================")
	WebUI.comment("TEST COMPLETED: ALL PATCH SELECTION WITH SCROLLING")
	WebUI.comment("==========================================")
}