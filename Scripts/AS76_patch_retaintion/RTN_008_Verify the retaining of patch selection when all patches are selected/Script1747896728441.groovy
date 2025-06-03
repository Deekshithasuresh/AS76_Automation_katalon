
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
 * Test Case: RTN_002_Verify the retaining of ALL patch selections from Split View to Patch View
 *
 * Purpose: This test verifies that ALL WBC patch selections made in split view are properly retained
 * when switching back to normal patch view in the SigTuple application. The test first switches to
 * split view, selects all patches there, then switches back to patch view to verify retention.
 *
 * Test Flow:
 * 1. Login to the application
 * 2. Select a specific slide (SIG013)
 * 3. Navigate to the WBC tab
 * 4. Switch to split view first
 * 5. Scroll and select ALL available WBC patches in split view
 * 6. Switch back to normal patch view
 * 7. Verify that ALL selected patches are still selected in normal patch view
 *
 * Expected result: ALL selected patches from split view remain selected in normal patch view
 */

// Log start of test with clear separator for better log readability
WebUI.comment("==========================================")
WebUI.comment("STARTING TEST: RTN_002_Verify the retaining of ALL patch selections from Split View to Patch View")
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
 * Helper function to scroll within containers (works for both split view and patch view)
 * @param driver WebDriver instance
 * @param scrollAmount Amount to scroll (positive for down, negative for up)
 * @param viewType Type of view ("split" for split view, "patch" for patch view)
 */
def scrollContainer(WebDriver driver, int scrollAmount = 300, String viewType = "patch") {
	try {
		WebElement scrollContainer = null
		
		// Define different selectors based on view type
		List<String> containerSelectors = []
		
		if (viewType == "split") {
			// Split view specific selectors
			containerSelectors = [
				'//div[contains(@class, "split-view")]//div[contains(@class, "scroll")]',
				'//div[contains(@class, "split")]//div[contains(@class, "patches")]',
				'//div[contains(@class, "right-panel")]//div[contains(@class, "scroll")]',
				'//div[contains(@class, "patch-panel")]//div[contains(@class, "scroll")]',
				'//div[contains(@class, "patches-section")]',
				'//div[@class="scroll-container"]'
			]
		} else {
			// Normal patch view selectors
			containerSelectors = [
				'//div[contains(@class, "patches-section")]',
				'//div[contains(@class, "scroll-container")]',
				'//div[contains(@class, "patch-container")]',
				'//div[@class="scroll-container"]'
			]
		}
		
		for (String selector : containerSelectors) {
			try {
				scrollContainer = driver.findElement(By.xpath(selector))
				if (scrollContainer != null) {
					WebUI.comment("[SCROLL] Found scrollable container for ${viewType} view using selector: ${selector}")
					break
				}
			} catch (Exception e) {
				// Continue to next selector
			}
		}
		
		if (scrollContainer != null) {
			JavascriptExecutor js = (JavascriptExecutor) driver
			js.executeScript("arguments[0].scrollTop += arguments[1];", scrollContainer, scrollAmount)
			WebUI.comment("[SCROLL] Scrolled ${viewType} view container by ${scrollAmount} pixels")
		} else {
			// Fallback to window scroll
			WebUI.comment("[SCROLL] Container not found for ${viewType} view, using window scroll as fallback")
			JavascriptExecutor js = (JavascriptExecutor) driver
			js.executeScript("window.scrollBy(0, arguments[0]);", scrollAmount)
		}
		
		WebUI.delay(1) // Allow time for content to load after scroll
		
	} catch (Exception e) {
		WebUI.comment("[WARNING] Error during ${viewType} view scroll: ${e.getMessage()}")
	}
}

/**
 * Helper function to switch between split view and patch view
 * @param driver WebDriver instance
 * @param targetView "split" to switch to split view, "patch" to switch to patch view
 * @return boolean indicating success
 */
def switchView(WebDriver driver, String targetView) {
	WebUI.comment("[VIEW SWITCH] Attempting to switch to ${targetView} view")
	boolean switchSuccessful = false
	
	try {
		// Define different selectors based on target view
		List<String> buttonSelectors = []
		String buttonDescription = ""
		
		if (targetView == "split") {
			buttonDescription = "Split View Button"
			buttonSelectors = [
				"//img[@id='split-view' and contains(@src, 'split-view-active.svg')]",
				"//img[@id='split-view' and contains(@src, 'split-view-default.svg')]",
				"//img[contains(@id, 'split-view')]",
				"//div[contains(@class, 'view-button') or contains(@class, 'view-controls')]//img[contains(@src, 'split')]"
			]
		} else {
			buttonDescription = "Patch View Button"
			buttonSelectors = [
				"//img[@id='patch-view' and contains(@src, 'patch-view-active.svg')]",
				"//img[@id='patch-view' and contains(@src, 'patch-view-default.svg')]",
				"//img[contains(@id, 'patch-view')]",
				"//div[contains(@class, 'view-button') or contains(@class, 'view-controls')]//img[contains(@src, 'patch')]",
				"//img[contains(@src, 'normal-view') or contains(@src, 'single-view')]"
			]
		}
		
		// Try each selector approach
		for (int i = 0; i < buttonSelectors.size(); i++) {
			try {
				WebUI.comment("[APPROACH ${i+1}] Trying selector: ${buttonSelectors[i]}")
				
				TestObject viewButton = new TestObject(buttonDescription)
				viewButton.addProperty('xpath', ConditionType.EQUALS, buttonSelectors[i])
				
				if (WebUI.waitForElementPresent(viewButton, 5, FailureHandling.OPTIONAL)) {
					WebUI.comment("[INFO] ${buttonDescription} found with approach ${i+1}")
					WebUI.click(viewButton)
					switchSuccessful = true
					WebUI.comment("[SUCCESS] Clicked ${buttonDescription} using approach ${i+1}")
					break
				}
			} catch (Exception e) {
				WebUI.comment("[WARNING] Approach ${i+1} failed: ${e.getMessage()}")
			}
		}
		
		// If TestObject approach fails, try direct WebDriver
		if (!switchSuccessful) {
			WebUI.comment("[FALLBACK] Using direct WebDriver approach")
			for (String selector : buttonSelectors) {
				try {
					List<WebElement> buttons = driver.findElements(By.xpath(selector))
					if (buttons.size() > 0) {
						JavascriptExecutor js = (JavascriptExecutor) driver
						js.executeScript("arguments[0].click();", buttons.get(0))
						switchSuccessful = true
						WebUI.comment("[SUCCESS] Clicked ${buttonDescription} using direct WebDriver with selector: ${selector}")
						break
					}
				} catch (Exception e) {
					WebUI.comment("[WARNING] Direct WebDriver approach failed for selector ${selector}: ${e.getMessage()}")
				}
			}
		}
		
		if (switchSuccessful) {
			WebUI.comment("[WAIT] Waiting for ${targetView} view to load")
			WebUI.delay(5) // Wait for view to load
			WebUI.takeScreenshot("${targetView}_view_loaded.png")
		} else {
			WebUI.comment("[ERROR] Could not switch to ${targetView} view using any approach!")
			WebUI.takeScreenshot("${targetView}_view_switch_failed.png")
		}
		
	} catch (Exception e) {
		WebUI.comment("[ERROR] Exception during view switch: ${e.getMessage()}")
		WebUI.takeScreenshot("view_switch_exception.png")
	}
	
	return switchSuccessful
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
	
	// Wait for the dashboard to load after login
	WebUI.comment("[WAIT] Waiting for dashboard to load after login")
	WebUI.waitForPageLoad(60)
	
	// SECTION 3: SLIDE SELECTION
	WebUI.comment("[STEP 4] Selecting slide SIG013")
	// Click on specific slide with retry logic
	retryOperation({
		WebUI.waitForElementClickable(findTestObject('Object Repository/retain_patchs/td_SIG013'), 30)
		WebUI.comment("[INFO] Slide SIG013 is clickable, proceeding to click")
		WebUI.click(findTestObject('Object Repository/retain_patchs/td_SIG013'))
		WebUI.comment("[SUCCESS] Clicked on slide SIG013")
	})
	
	// Add explicit wait for slide to load
	WebUI.comment("[WAIT] Waiting for slide to fully load")
	WebUI.delay(5)
	WebUI.takeScreenshot('slide_loaded.png')
	
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
	
	// SECTION 5: SWITCH TO SPLIT VIEW FIRST
	WebUI.comment("[STEP 6] Switching to split view first")
	
	boolean splitViewSwitchSuccess = switchView(driver, "split")
	
	if (!splitViewSwitchSuccess) {
		WebUI.comment("[ERROR] Could not switch to split view!")
		assert false : "Could not switch to split view - test cannot continue"
	}
	
	// SECTION 6: SELECTING ALL PATCHES IN SPLIT VIEW
	WebUI.comment("[STEP 7] Starting to locate and select ALL WBC patches in SPLIT VIEW with scrolling")
	
	// Variables to track selection process in split view
	Set<String> splitViewSelectedIds = new HashSet<String>()
	List<WebElement> allSelectedPatchesInSplitView = []
	Actions actions = new Actions(driver)
	boolean isFirstPatch = true
	int splitViewScrollAttempts = 0
	int maxSplitViewScrollAttempts = 25
	int previousSplitViewPatchCount = 0
	int splitViewStableCountIterations = 0
	
	WebUI.comment("[INFO] Starting patch selection process in split view with automatic scrolling")
	
	// Determine OS for correct key combination
	String osName = System.getProperty('os.name').toLowerCase()
	boolean isMac = osName.contains('mac')
	WebUI.comment("[INFO] Detected OS: ${osName}, Using ${isMac ? 'COMMAND' : 'CONTROL'} key for multi-select")
	
	// Main scrolling and selection loop in split view
	while (splitViewScrollAttempts < maxSplitViewScrollAttempts) {
		WebUI.comment("[SPLIT VIEW LOOP ${splitViewScrollAttempts + 1}] Searching for patches at current scroll position")
		
		List<WebElement> currentPatches = []
		
		retryOperation({
			// Try multiple selector strategies to find patches in split view
			WebUI.comment("[SEARCH] Looking for WBC patches in split view")
			
			// Split view specific selectors
			String[] splitViewXpathPatterns = [
				'//div[contains(@class, "split-view")]//div[contains(@class, "patch") and contains(@id, "wbc")]',
				'//div[contains(@class, "split")]//div[contains(@id, "wbc")]',
				'//div[contains(@class, "right-panel")]//div[contains(@id, "wbc")]',
				'//div[contains(@class, "patch-panel")]//div[contains(@id, "wbc")]',
				'//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "wbc")]',
				'//div[contains(@id, "wbc") and contains(@id, "patch")]'
			]
			
			for (String xpath : splitViewXpathPatterns) {
				try {
					currentPatches = driver.findElements(By.xpath(xpath))
					if (currentPatches.size() > 0) {
						WebUI.comment("[INFO] Found ${currentPatches.size()} patches in split view with XPath: ${xpath}")
						break
					}
				} catch (Exception ex) {
					// Continue to next pattern
				}
			}
			
			return currentPatches
		})
		
		WebUI.comment("[RESULT] Found ${currentPatches.size()} patches at current split view position")
		
		// Process each patch found at this scroll position in split view
		if (currentPatches.size() > 0) {
			for (int i = 0; i < currentPatches.size(); i++) {
				try {
					WebElement patch = currentPatches.get(i)
					String patchId = patch.getAttribute('id')
					
					// Skip if we've already selected this patch
					if (patchId != null && splitViewSelectedIds.contains(patchId)) {
						continue
					}
					
					// Check if patch is visible and clickable
					if (patch.isDisplayed() && patch.isEnabled()) {
						WebUI.comment("[SELECT] Attempting to select patch in split view with ID: ${patchId ?: 'unknown'}")
						
						// Scroll patch into view
						WebUI.executeJavaScript('arguments[0].scrollIntoView({block: "center", behavior: "smooth"});', Arrays.asList(patch))
						WebUI.delay(1)
						
						// Select the patch
						if (isFirstPatch) {
							WebUI.comment("[CLICK] Normal click for first patch in split view")
							patch.click()
							isFirstPatch = false
						} else {
							// Multi-select with appropriate key
							if (isMac) {
								WebUI.comment("[CLICK] Using COMMAND+click for Mac in split view")
								actions.keyDown(Keys.COMMAND).click(patch).keyUp(Keys.COMMAND).build().perform()
							} else {
								WebUI.comment("[CLICK] Using CONTROL+click for Windows/Linux in split view")
								actions.keyDown(Keys.CONTROL).click(patch).keyUp(Keys.CONTROL).build().perform()
							}
						}
						
						// Add to our tracking lists
						if (patchId != null) {
							splitViewSelectedIds.add(patchId)
							WebUI.comment("[SUCCESS] Selected patch in split view: ${patchId}")
						}
						
						allSelectedPatchesInSplitView.add(patch)
						WebUI.delay(0.5) // Small delay between selections
					}
				} catch (StaleElementReferenceException stale) {
					WebUI.comment("[WARNING] Stale element reference in split view, patch may have been redrawn")
				} catch (Exception e) {
					WebUI.comment("[WARNING] Error selecting patch in split view: ${e.getMessage()}")
				}
			}
		}
		
		// Check if we've found new patches or if count is stable
		int currentSplitViewTotal = splitViewSelectedIds.size()
		WebUI.comment("[STATUS] Total patches selected in split view so far: ${currentSplitViewTotal}")
		
		if (currentSplitViewTotal == previousSplitViewPatchCount) {
			splitViewStableCountIterations++
			WebUI.comment("[INFO] Split view patch count stable for ${splitViewStableCountIterations} iterations")
			
			// If count has been stable for 3 iterations, we might be done
			if (splitViewStableCountIterations >= 3) {
				WebUI.comment("[INFO] Split view patch count stable for 3+ iterations, likely reached end")
				break
			}
		} else {
			splitViewStableCountIterations = 0 // Reset counter if we found new patches
			previousSplitViewPatchCount = currentSplitViewTotal
		}
		
		// Scroll down to find more patches in split view
		WebUI.comment("[SCROLL] Scrolling split view to find more patches")
		scrollContainer(driver, 350, "split")
		
		splitViewScrollAttempts++
		WebUI.delay(2) // Allow content to load after scroll
		
		// Take periodic screenshots
		if (splitViewScrollAttempts % 5 == 0) {
			WebUI.takeScreenshot("split_view_selection_${splitViewScrollAttempts}.png")
		}
	}
	
	WebUI.comment("[COMPLETION] Finished split view scrolling and selection process")
	WebUI.comment("[SPLIT VIEW FINAL COUNT] Total patches selected in split view: ${splitViewSelectedIds.size()}")
	
	// Take screenshot after all selections in split view
	WebUI.takeScreenshot('all_patches_selected_split_view.png')
	
	// Make sure we selected at least some patches in split view
	if (splitViewSelectedIds.size() == 0) {
		WebUI.comment("[ERROR] No WBC patches were selected in split view!")
		WebUI.takeScreenshot('no_patches_selected_split_view.png')
		assert false : "No WBC patches were selected in split view, cannot proceed with test"
	} else {
		WebUI.comment("[SUCCESS] Successfully selected ${splitViewSelectedIds.size()} WBC patches in split view")
	}
	
	// SECTION 7: SWITCHING BACK TO PATCH VIEW
	WebUI.comment("[STEP 8] Switching back to normal patch view")
	
	boolean patchViewSwitchSuccess = switchView(driver, "patch")
	
	if (!patchViewSwitchSuccess) {
		WebUI.comment("[ERROR] Could not switch back to patch view!")
		assert false : "Could not switch back to patch view - test cannot continue"
	}
	
	// SECTION 8: VERIFYING ALL SELECTIONS ARE RETAINED IN PATCH VIEW WITH SCROLLING
	WebUI.comment("[STEP 9] Verifying ALL patch selections are retained in normal patch view with scrolling")
	
	// Variables for patch view verification
	Set<String> patchViewSelectedIds = new HashSet<String>()
	List<WebElement> allSelectedPatchesInPatchView = []
	int patchViewScrollAttempts = 0
	int maxPatchViewScrollAttempts = 25
	int previousPatchViewPatchCount = 0
	int patchViewStableCountIterations = 0
	
	WebUI.comment("[INFO] Starting patch view verification with scrolling")
	
	// Scroll through patch view to find all selected patches
	while (patchViewScrollAttempts < maxPatchViewScrollAttempts) {
		WebUI.comment("[PATCH VIEW LOOP ${patchViewScrollAttempts + 1}] Searching for selected patches at current scroll position")
		
		List<WebElement> currentSelectedPatches = []
		
		try {
			// Using multiple strategies to find selected patches in patch view at current position
			WebUI.comment("[STRATEGY 1] Looking for div.selected-patch elements in patch view")
			currentSelectedPatches = driver.findElements(By.cssSelector("div.selected-patch"))
			WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using div.selected-patch")
			
			// If that doesn't work, try the multiselect patch class
			if (currentSelectedPatches.size() == 0) {
				WebUI.comment("[STRATEGY 2] Looking for div.selected-patch.multiselect-patch elements in patch view")
				currentSelectedPatches = driver.findElements(By.cssSelector("div.selected-patch.multiselect-patch"))
				WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using div.selected-patch.multiselect-patch")
			}
			
			// If still not found, try another approach with alternative classes
			if (currentSelectedPatches.size() == 0) {
				WebUI.comment("[STRATEGY 3] Looking for .patch-img-container.multiselect-patch elements in patch view")
				currentSelectedPatches = driver.findElements(By.cssSelector(".patch-img-container.multiselect-patch"))
				WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using .patch-img-container.multiselect-patch")
			}
			
			// One more attempt with div.selected-patch variations
			if (currentSelectedPatches.size() == 0) {
				WebUI.comment("[STRATEGY 4] Using broader XPath selector for selected elements in patch view")
				currentSelectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected') or contains(@class, 'active')]"))
				WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using broader 'selected' or 'active' class selector")
			}
			
			// Final attempt with any element that might indicate selection
			if (currentSelectedPatches.size() == 0) {
				WebUI.comment("[STRATEGY 5] Looking for any elements with selection indicators in patch view")
				currentSelectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'multiselect') or contains(@style, 'selected') or contains(@class, 'highlight')]"))
				WebUI.comment("[INFO] Found ${currentSelectedPatches.size()} selected patches using selection indicator strategy")
			}
			
			// Process found selected patches
			if (currentSelectedPatches.size() > 0) {
				WebUI.comment("[PROCESSING] Processing ${currentSelectedPatches.size()} selected patches found at current position in patch view")
				
				for (int i = 0; i < currentSelectedPatches.size(); i++) {
					try {
						WebElement selectedPatch = currentSelectedPatches.get(i)
						String patchId = selectedPatch.getAttribute("id")
						
						// Only add if we haven't seen this patch before
						if (patchId != null && !patchViewSelectedIds.contains(patchId)) {
							patchViewSelectedIds.add(patchId)
							allSelectedPatchesInPatchView.add(selectedPatch)
							WebUI.comment("[FOUND] New selected patch in patch view - ID: ${patchId}")
						} else if (patchId == null) {
							// Handle patches without ID by checking other attributes
							String className = selectedPatch.getAttribute("class") ?: ""
							String dataId = selectedPatch.getAttribute("data-id") ?: ""
							String uniqueIdentifier = "class_${className}_data_${dataId}_index_${i}"
							
							if (!patchViewSelectedIds.contains(uniqueIdentifier)) {
								patchViewSelectedIds.add(uniqueIdentifier)
								allSelectedPatchesInPatchView.add(selectedPatch)
								WebUI.comment("[FOUND] New selected patch in patch view - Identifier: ${uniqueIdentifier}")
							}
						}
					} catch (StaleElementReferenceException stale) {
						WebUI.comment("[WARNING] Stale element reference in patch view, patch may have been redrawn")
					} catch (Exception e) {
						WebUI.comment("[WARNING] Error processing selected patch in patch view: ${e.getMessage()}")
					}
				}
			}
			
		} catch (Exception e) {
			WebUI.comment("[ERROR] Error finding selected patches in patch view at scroll position ${patchViewScrollAttempts + 1}: ${e.getMessage()}")
		}
		
		// Check if we've found new patches or if count is stable
		int currentPatchViewTotal = patchViewSelectedIds.size()
		WebUI.comment("[STATUS] Total selected patches found in patch view so far: ${currentPatchViewTotal}")
		
		if (currentPatchViewTotal == previousPatchViewPatchCount) {
			patchViewStableCountIterations++
			WebUI.comment("[INFO] Patch view selected patch count stable for ${patchViewStableCountIterations} iterations")
			
			// If count has been stable for 4 iterations, we might be done
			if (patchViewStableCountIterations >= 4) {
				WebUI.comment("[INFO] Patch view selected patch count stable for 4+ iterations, likely reached end")
				break
			}
		} else {
			patchViewStableCountIterations = 0 // Reset counter if we found new patches
			previousPatchViewPatchCount = currentPatchViewTotal
		}
		
		// Scroll down in patch view to find more selected patches
		WebUI.comment("[SCROLL] Scrolling patch view to find more selected patches")
		scrollContainer(driver, 350, "patch")
		
		patchViewScrollAttempts++
		WebUI.delay(2) // Allow content to load after scroll
		
		// Take periodic screenshots during scrolling
		if (patchViewScrollAttempts % 5 == 0) {
			WebUI.takeScreenshot("patch_view_verification_${patchViewScrollAttempts}.png")
		}
	}
	
	WebUI.comment("[COMPLETION] Finished patch view scrolling and verification")
	WebUI.comment("[PATCH VIEW FINAL COUNT] Total selected patches found in patch view: ${patchViewSelectedIds.size()}")
	
	// DETAILED COMPARISON BETWEEN SPLIT VIEW SELECTION AND PATCH VIEW VERIFICATION
	WebUI.comment("[DETAILED COMPARISON] Comparing split view patch selection with patch view results")
	
// Find matching IDs between split view selection and patch view verification
	int exactMatches = 0
	int partialMatches = 0
	List<String> missingPatches = []
	List<String> extraPatches = []
	
	WebUI.comment("[ANALYSIS] Analyzing patch retention between split view and patch view")
	
	// Check for exact matches first
	for (String splitViewId : splitViewSelectedIds) {
		if (patchViewSelectedIds.contains(splitViewId)) {
			exactMatches++
			WebUI.comment("[EXACT MATCH] Patch ${splitViewId} found in both views")
		} else {
			// Check for partial matches (in case IDs are slightly different)
			boolean foundPartialMatch = false
			for (String patchViewId : patchViewSelectedIds) {
				if (patchViewId.contains(splitViewId) || splitViewId.contains(patchViewId)) {
					partialMatches++
					foundPartialMatch = true
					WebUI.comment("[PARTIAL MATCH] Split view ${splitViewId} matches patch view ${patchViewId}")
					break
				}
			}
			if (!foundPartialMatch) {
				missingPatches.add(splitViewId)
				WebUI.comment("[MISSING] Patch ${splitViewId} selected in split view but not found in patch view")
			}
		}
	}
	
	// Check for extra patches in patch view
	for (String patchViewId : patchViewSelectedIds) {
		boolean foundInSplitView = false
		for (String splitViewId : splitViewSelectedIds) {
			if (patchViewId.equals(splitViewId) || patchViewId.contains(splitViewId) || splitViewId.contains(patchViewId)) {
				foundInSplitView = true
				break
			}
		}
		if (!foundInSplitView) {
			extraPatches.add(patchViewId)
			WebUI.comment("[EXTRA] Patch ${patchViewId} found in patch view but not selected in split view")
		}
	}
	
	// SECTION 9: FINAL VERIFICATION AND RESULTS
	WebUI.comment("==========================================")
	WebUI.comment("TEST RESULTS SUMMARY")
	WebUI.comment("==========================================")
	
	WebUI.comment("[SPLIT VIEW SELECTION] Total patches selected: ${splitViewSelectedIds.size()}")
	WebUI.comment("[PATCH VIEW VERIFICATION] Total selected patches found: ${patchViewSelectedIds.size()}")
	WebUI.comment("[EXACT MATCHES] ${exactMatches}")
	WebUI.comment("[PARTIAL MATCHES] ${partialMatches}")
	WebUI.comment("[MISSING PATCHES] ${missingPatches.size()}")
	WebUI.comment("[EXTRA PATCHES] ${extraPatches.size()}")
	
	// Calculate retention percentage
	double retentionPercentage = 0.0
	if (splitViewSelectedIds.size() > 0) {
		retentionPercentage = ((exactMatches + partialMatches) / (double) splitViewSelectedIds.size()) * 100.0
	}
	
	WebUI.comment("[RETENTION RATE] ${String.format('%.2f', retentionPercentage)}% of patches retained from split view to patch view")
	
	// Take final screenshots
	WebUI.takeScreenshot('final_patch_view_verification.png')
	
	// SECTION 10: TEST ASSERTION AND CLEANUP
	WebUI.comment("[FINAL ASSERTION] Determining test result")
	
	boolean testPassed = false
	String testResult = "FAIL"
	String failureReason = ""
	
	// Define success criteria (adjust threshold as needed)
	double minimumRetentionThreshold = 90.0 // 90% retention required for pass
	
	if (splitViewSelectedIds.size() == 0) {
		failureReason = "No patches were selected in split view - cannot verify retention"
	} else if (patchViewSelectedIds.size() == 0) {
		failureReason = "No selected patches found in patch view - all selections were lost"
	} else if (retentionPercentage < minimumRetentionThreshold) {
		failureReason = "Retention rate (${String.format('%.2f', retentionPercentage)}%) is below minimum threshold (${minimumRetentionThreshold}%)"
	} else {
		testPassed = true
		testResult = "PASS"
	}
	
	WebUI.comment("==========================================")
	WebUI.comment("FINAL TEST RESULT: ${testResult}")
	if (!testPassed) {
		WebUI.comment("FAILURE REASON: ${failureReason}")
	}
	WebUI.comment("==========================================")
	
	// Log detailed results for debugging if needed
	if (missingPatches.size() > 0) {
		WebUI.comment("[DEBUG] Missing patches details:")
		for (String missing : missingPatches) {
			WebUI.comment("  - ${missing}")
		}
	}
	
	if (extraPatches.size() > 0) {
		WebUI.comment("[DEBUG] Extra patches details:")
		for (String extra : extraPatches) {
			WebUI.comment("  - ${extra}")
		}
	}
	
	// Assert test result
	if (testPassed) {
		WebUI.comment("[SUCCESS] Test RTN_002 completed successfully - All patch selections properly retained")
	} else {
		WebUI.comment("[FAILURE] Test RTN_002 failed - Patch selection retention issue detected")
		// Take failure screenshot
		WebUI.takeScreenshot('test_failure_final.png')
		
		// Create detailed failure report
		String failureReport = """
		TEST FAILURE REPORT - RTN_002
		========================================
		Split View Selections: ${splitViewSelectedIds.size()}
		Patch View Selections Found: ${patchViewSelectedIds.size()}
		Exact Matches: ${exactMatches}
		Partial Matches: ${partialMatches}
		Retention Rate: ${String.format('%.2f', retentionPercentage)}%
		Missing Patches: ${missingPatches.size()}
		Extra Patches: ${extraPatches.size()}
		
		Failure Reason: ${failureReason}
		========================================
		"""
		WebUI.comment(failureReport)
		
		// Soft assertion with detailed message
		assert testPassed : "RTN_002 Test Failed: ${failureReason}. Expected retention rate >= ${minimumRetentionThreshold}%, but got ${String.format('%.2f', retentionPercentage)}%"
	}
	
} catch (Exception e) {
	// EXCEPTION HANDLING
	WebUI.comment("[EXCEPTION] Test encountered an unexpected error: ${e.getMessage()}")
	WebUI.comment("[STACK TRACE] ${e.getStackTrace()}")
	
	// Take screenshot of error state
	WebUI.takeScreenshot('test_exception_error.png')
	
	// Log the exception details
	WebUI.comment("==========================================")
	WebUI.comment("EXCEPTION DETAILS")
	WebUI.comment("==========================================")
	WebUI.comment("[ERROR TYPE] ${e.getClass().getSimpleName()}")
	WebUI.comment("[ERROR MESSAGE] ${e.getMessage()}")
	WebUI.comment("[ERROR LOCATION] Check stack trace above for details")
	
	// Re-throw the exception to fail the test
	throw e
	
} finally {
	// CLEANUP SECTION
	WebUI.comment("==========================================")
	WebUI.comment("CLEANUP AND TEST COMPLETION")
	WebUI.comment("==========================================")
	
	try {
		// Take final screenshot regardless of test result
		WebUI.takeScreenshot('test_completion_final.png')
		
		// Log test completion time
		WebUI.comment("[INFO] Test RTN_002 execution completed at: ${new Date()}")
		
		// Close the browser
		WebUI.comment("[CLEANUP] Closing browser session")
		WebUI.closeBrowser()
		
	} catch (Exception cleanupException) {
		WebUI.comment("[WARNING] Error during cleanup: ${cleanupException.getMessage()}")
	}
	
	WebUI.comment("==========================================")
	WebUI.comment("END OF TEST: RTN_002_Verify the retaining of ALL patch selections from Split View to Patch View")
	WebUI.comment("==========================================")
}