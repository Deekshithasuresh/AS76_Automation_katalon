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
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.interactions.Actions as Actions

// Login to the application
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/retain_patchs/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Navigate to the specific slide
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/td_SIG013'), 20)
WebUI.click(findTestObject('Object Repository/retain_patchs/td_SIG013'))

// Select WBC tab
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/span_WBC'), 20)
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/span_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/retain_patchs/span_WBC'))

// Start in patch view (default view)
WebUI.comment("=== STARTING IN PATCH VIEW ===")
WebUI.delay(3)

// Get the driver and initialize Actions
def driver = DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

// Data structure to store selected patch information for tracking
class SelectedPatchInfo {
	int index
	String imageSrc
	String imageFileName
	String patchId
	String position
	
	SelectedPatchInfo(WebElement element, int index) {
		this.index = index
		this.position = element.getLocation().toString()
		this.patchId = element.getAttribute('id') ?: ''
		
		try {
			WebElement imgElement = element.findElement(By.tagName('img'))
			this.imageSrc = imgElement.getAttribute('src') ?: ''
			
			// Extract just the filename for easier matching
			if (this.imageSrc && this.imageSrc.contains('/')) {
				String[] parts = this.imageSrc.split('/')
				this.imageFileName = parts[parts.length - 1]
			} else {
				this.imageFileName = this.imageSrc
			}
		} catch (Exception e) {
			this.imageSrc = ''
			this.imageFileName = ''
		}
	}
	
	boolean hasValidImage() {
		return !imageFileName.isEmpty() && imageFileName.contains('.jpg')
	}
	
	@Override
	String toString() {
		return "SelectedPatch[${index}] - Image: ${imageFileName}, Position: ${position}"
	}
}

List<SelectedPatchInfo> selectedPatchesInfo = []

WebUI.comment("=== SELECTING 3 PATCHES IN PATCH VIEW ===")
WebUI.delay(3)

// Find all patch containers in patch view
List<WebElement> allPatches = []
try {
	allPatches = driver.findElements(By.xpath("//div[contains(@class, 'patch-img-container')]"))
	WebUI.comment("Found " + allPatches.size() + " patch containers in patch view")
} catch (Exception e) {
	WebUI.comment("Error finding patches: " + e.getMessage())
	WebUI.closeBrowser()
	return
}

if (allPatches.size() < 3) {
	WebUI.comment("ERROR: Need at least 3 patches, but only found " + allPatches.size())
	WebUI.closeBrowser()
	return
}

// Select exactly 3 patches and store their information
String osName = System.getProperty('os.name').toLowerCase()
boolean isMac = osName.contains('mac')

for (int i = 0; i < 3; i++) {
	try {
		WebElement patchToSelect = allPatches.get(i)
		
		// Store patch info BEFORE selection to get original state
		SelectedPatchInfo patchInfo = new SelectedPatchInfo(patchToSelect, i)
		selectedPatchesInfo.add(patchInfo)
		
		WebUI.comment("Selecting patch " + (i + 1) + ": " + patchInfo.toString())
		
		// Scroll into view and select
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patchToSelect)
		WebUI.delay(1)
		
		if (i == 0) {
			patchToSelect.click()
			WebUI.comment("✓ Selected first patch (normal click)")
		} else {
			if (isMac) {
				actions.keyDown(Keys.COMMAND).click(patchToSelect).keyUp(Keys.COMMAND).perform()
				WebUI.comment("✓ Selected patch " + (i + 1) + " (Command+click)")
			} else {
				actions.keyDown(Keys.CONTROL).click(patchToSelect).keyUp(Keys.CONTROL).perform()
				WebUI.comment("✓ Selected patch " + (i + 1) + " (Ctrl+click)")
			}
		}
		
		WebUI.delay(1)
		
	} catch (Exception e) {
		WebUI.comment("❌ Error selecting patch " + (i + 1) + ": " + e.getMessage())
		WebUI.closeBrowser()
		return
	}
}

// Verify selections in patch view - count only selected patches
WebUI.comment("=== VERIFYING SELECTIONS IN PATCH VIEW ===")
int patchViewSelectedCount = 0
try {
	List<WebElement> selectedInPatchView = driver.findElements(By.xpath(
		"//div[contains(@class, 'selected-patch')]"))
	patchViewSelectedCount = selectedInPatchView.size()
	WebUI.comment("Patch view: Found " + patchViewSelectedCount + " selected patches")
} catch (Exception e) {
	WebUI.comment("Could not verify selections in patch view: " + e.getMessage())
}

// Take screenshot of patch view
String patchViewScreenshot = System.getProperty("user.dir") + "/Screenshots/patch_view_selection_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patchViewScreenshot)
WebUI.comment("Patch view screenshot saved: " + patchViewScreenshot)

WebUI.delay(3)

// Switch to split view
WebUI.comment("=== SWITCHING TO SPLIT VIEW ===")
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))

WebUI.delay(5)

// Verify patch retention in split view
WebUI.comment("=== VERIFYING PATCH RETENTION IN SPLIT VIEW ===")

int retainedPatchCount = 0
List<String> verificationResults = []

for (SelectedPatchInfo selectedPatch : selectedPatchesInfo) {
	boolean isRetained = false
	String verificationMsg = ""
	
	if (selectedPatch.hasValidImage()) {
		try {
			// Look for the same image in split view
			String imageXPath = "//img[contains(@src, '" + selectedPatch.imageFileName + "')]"
			List<WebElement> matchingImages = driver.findElements(By.xpath(imageXPath))
			
			if (matchingImages.size() > 0) {
				// Check if any matching image is in a selected/active state in split view
				for (WebElement img : matchingImages) {
					try {
						// Look for parent container that might indicate selection
						WebElement parentContainer = img.findElement(By.xpath("./ancestor::div[contains(@class, 'Card') or contains(@class, 'card') or contains(@class, 'patch-container')]"))
						String containerClasses = parentContainer.getAttribute('class') ?: ''
						
						// Check for selection indicators
						if (containerClasses.contains('selected') ||
							containerClasses.contains('active') ||
							containerClasses.contains('focus') ||
							containerClasses.contains('highlight')) {
							isRetained = true
							verificationMsg = "✓ RETAINED: Image '" + selectedPatch.imageFileName + "' found in selected state in split view"
							break
						}
					} catch (Exception e) {
						// Continue checking other matching images
					}
				}
				
				if (!isRetained) {
					verificationMsg = "⚠️ FOUND BUT NOT SELECTED: Image '" + selectedPatch.imageFileName + "' exists in split view but not in selected state"
				}
			} else {
				verificationMsg = "❌ NOT FOUND: Image '" + selectedPatch.imageFileName + "' not found in split view"
			}
		} catch (Exception e) {
			verificationMsg = "❌ ERROR: Could not verify patch - " + e.getMessage()
		}
	} else {
		verificationMsg = "⚠️ CANNOT VERIFY: No unique image identifier for this patch"
	}
	
	if (isRetained) {
		retainedPatchCount++
	}
	
	verificationResults.add("Patch " + (selectedPatch.index + 1) + ": " + verificationMsg)
	WebUI.comment(verificationResults[-1])
}

// Count selected patches in split view and compare with patch view
int splitViewSelectedCount = 0
try {
	// Use the same XPath that worked in patch view
	List<WebElement> selectedInSplitView = driver.findElements(By.xpath(
		"//div[contains(@class, 'selected-patch') and contains(@class, 'multiselect-patch')]"))
	splitViewSelectedCount = selectedInSplitView.size()
	
	WebUI.comment("Split view: Found " + splitViewSelectedCount + " selected patches using XPath: //div[contains(@class, 'selected-patch') and contains(@class, 'multiselect-patch')]")
	
} catch (Exception e) {
	WebUI.comment("Could not count selected patches in split view: " + e.getMessage())
}

// Take screenshot of split view
String splitViewScreenshot = System.getProperty("user.dir") + "/Screenshots/split_view_verification_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(splitViewScreenshot)
WebUI.comment("Split view screenshot saved: " + splitViewScreenshot)

// Final test result based on count comparison
WebUI.comment("=== FINAL TEST RESULT ===")
WebUI.comment("Patches selected in patch view: " + patchViewSelectedCount)
WebUI.comment("Patches found selected in split view: " + splitViewSelectedCount)

// Compare counts to determine if patches are retained
if (splitViewSelectedCount == patchViewSelectedCount && patchViewSelectedCount == 3) {
	WebUI.comment("✅ TEST PASSED: All " + patchViewSelectedCount + " selected patches are retained in split view")
} else if (splitViewSelectedCount == patchViewSelectedCount) {
	WebUI.comment("✅ TEST PASSED: Selected patch count matches (" + patchViewSelectedCount + " patches retained)")
} else {
	WebUI.comment("❌ TEST FAILED: Expected " + patchViewSelectedCount + " selected patches, but found " + splitViewSelectedCount + " in split view")
}

WebUI.comment("=== VERIFICATION SUMMARY ===")
WebUI.comment("Patch View XPath: //div[contains(@class, 'selected-patch')] → Found: " + patchViewSelectedCount)
WebUI.comment("Split View XPath: //div[contains(@class, 'selected-patch') and contains(@class, 'multiselect-patch')] → Found: " + splitViewSelectedCount)
WebUI.comment("Result: " + (splitViewSelectedCount == patchViewSelectedCount ? "RETAINED" : "NOT RETAINED"))

WebUI.delay(3)

// DESELECT ALL 3 PATCHES IN SPLIT VIEW
WebUI.comment("=== DESELECTING ALL 3 PATCHES IN SPLIT VIEW ===")

if (selectedPatchesInfo.size() >= 3) {
	boolean deselectionSuccessful = false
	
	try {
		// Find all currently selected patches in split view
		List<WebElement> selectedPatches = driver.findElements(By.xpath(
			"//div[contains(@class, 'selected-patch') and contains(@class, 'multiselect-patch')]"))
		
		if (selectedPatches.size() >= 3) {
			WebUI.comment("Found " + selectedPatches.size() + " selected patches to deselect")
			
			// Deselect all patches by clicking on each one
			for (int i = 0; i < selectedPatches.size(); i++) {
				WebElement patchToDeselect = selectedPatches.get(i)
				
				WebUI.comment("Attempting to deselect patch " + (i + 1) + " by clicking on it again")
				
				// Scroll into view
				((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patchToDeselect)
				WebUI.delay(1)
				
				// Simply click on the already selected patch to deselect it
				patchToDeselect.click()
				WebUI.comment("✓ Deselected patch " + (i + 1) + " by clicking on it")
				
				WebUI.delay(1)
			}
			
			deselectionSuccessful = true
			WebUI.delay(2)
			
		} else {
			WebUI.comment("❌ Could not find enough selected patches for deselection. Found: " + selectedPatches.size())
		}
		
	} catch (Exception e) {
		WebUI.comment("❌ Error during deselection: " + e.getMessage())
	}
	
	if (deselectionSuccessful) {
		// Verify deselection in split view
		int splitViewCountAfterDeselection = 0
		try {
			List<WebElement> selectedAfterDeselection = driver.findElements(By.xpath(
				"//div[contains(@class, 'selected-patch') and contains(@class, 'multiselect-patch')]"))
			splitViewCountAfterDeselection = selectedAfterDeselection.size()
			WebUI.comment("After deselection - Split view: Found " + splitViewCountAfterDeselection + " selected patches")
		} catch (Exception e) {
			WebUI.comment("Could not verify deselection count: " + e.getMessage())
		}
		
		// Take screenshot after deselection
		String afterDeselectionScreenshot = System.getProperty("user.dir") + "/Screenshots/split_view_after_deselection_" + System.currentTimeMillis() + ".png"
		WebUI.takeScreenshot(afterDeselectionScreenshot)
		WebUI.comment("Split view after deselection screenshot saved: " + afterDeselectionScreenshot)
		
		WebUI.delay(3)
		
		// Switch back to patch view
		WebUI.comment("=== SWITCHING BACK TO PATCH VIEW AFTER DESELECTION ===")
		WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
		
		WebUI.delay(5)
		
		// Verify only 2 patches are retained in patch view
		WebUI.comment("=== VERIFYING 2 PATCHES RETAINED IN PATCH VIEW ===")
		
		int finalPatchViewCount = 0
		try {
			List<WebElement> finalSelectedInPatchView = driver.findElements(By.xpath(
				"//div[contains(@class, 'selected-patch')]"))
			finalPatchViewCount = finalSelectedInPatchView.size()
			WebUI.comment("Final patch view: Found " + finalPatchViewCount + " selected patches")
		} catch (Exception e) {
			WebUI.comment("Could not count final selections in patch view: " + e.getMessage())
		}
		
		// Verify which patches are retained (should be 1st and 3rd)
		List<String> finalVerificationResults = []
		int finalRetainedCount = 0
		
		for (int i = 0; i < selectedPatchesInfo.size(); i++) {
			SelectedPatchInfo patch = selectedPatchesInfo.get(i)
			boolean isStillRetained = false
			String finalVerificationMsg = ""
			
			if (patch.hasValidImage()) {
				try {
					String imageXPath = "//img[contains(@src, '" + patch.imageFileName + "')]"
					List<WebElement> matchingImages = driver.findElements(By.xpath(imageXPath))
					
					if (matchingImages.size() > 0) {
						for (WebElement img : matchingImages) {
							try {
								WebElement parentContainer = img.findElement(By.xpath("./ancestor::div[contains(@class, 'Card') or contains(@class, 'card') or contains(@class, 'patch-container')]"))
								String containerClasses = parentContainer.getAttribute('class') ?: ''
								
								if (containerClasses.contains('selected') ||
									containerClasses.contains('active') ||
									containerClasses.contains('focus') ||
									containerClasses.contains('highlight')) {
									isStillRetained = true
									break
								}
							} catch (Exception e) {
								// Continue checking
							}
						}
					}
					
					if (i == 1) { // Second patch (should be deselected)
						if (isStillRetained) {
							finalVerificationMsg = "❌ UNEXPECTED: Second patch '" + patch.imageFileName + "' should be deselected but is still retained"
						} else {
							finalVerificationMsg = "✓ CORRECTLY DESELECTED: Second patch '" + patch.imageFileName + "' is no longer selected"
						}
					} else { // First and third patches (should be retained)
						if (isStillRetained) {
							finalVerificationMsg = "✓ CORRECTLY RETAINED: Patch " + (i + 1) + " '" + patch.imageFileName + "' is still selected"
							finalRetainedCount++
						} else {
							finalVerificationMsg = "❌ UNEXPECTED: Patch " + (i + 1) + " '" + patch.imageFileName + "' should be retained but is deselected"
						}
					}
					
				} catch (Exception e) {
					finalVerificationMsg = "❌ ERROR: Could not verify patch " + (i + 1) + " - " + e.getMessage()
				}
			} else {
				finalVerificationMsg = "⚠️ CANNOT VERIFY: No unique image identifier for patch " + (i + 1)
			}
			
			finalVerificationResults.add("Patch " + (i + 1) + ": " + finalVerificationMsg)
			WebUI.comment(finalVerificationResults[-1])
		}
		
		// Take final screenshot
		String finalPatchViewScreenshot = System.getProperty("user.dir") + "/Screenshots/final_patch_view_2_retained_" + System.currentTimeMillis() + ".png"
		WebUI.takeScreenshot(finalPatchViewScreenshot)
		WebUI.comment("Final patch view screenshot saved: " + finalPatchViewScreenshot)
		
		// Final deselection test result
		WebUI.comment("=== FINAL DESELECTION TEST RESULT ===")
		WebUI.comment("Expected patches in final patch view: 2")
		WebUI.comment("Actual patches found in final patch view: " + finalPatchViewCount)
		
		if (finalPatchViewCount == 2) {
			WebUI.comment("✅ DESELECTION TEST PASSED: Exactly 2 patches retained after deselecting second patch")
		} else {
			WebUI.comment("❌ DESELECTION TEST FAILED: Expected 2 patches, but found " + finalPatchViewCount + " patches")
		}
		
		WebUI.comment("=== COMPLETE TEST SUMMARY ===")
		WebUI.comment("1. Started with 3 patches selected in patch view")
		WebUI.comment("2. Switched to split view - " + (splitViewSelectedCount == 3 ? "✅ 3 patches retained" : "❌ Retention failed"))
		WebUI.comment("3. Deselected second patch in split view - " + (deselectionSuccessful ? "✅ Successful" : "❌ Failed"))
		WebUI.comment("4. Switched back to patch view - " + (finalPatchViewCount == 0? "✅ 0 patches retained" : "❌ Incorrect count: " + finalPatchViewCount))
		
	} else {
		WebUI.comment("❌ DESELECTION FAILED: Could not deselect second patch, skipping final verification")
	}
	
} else {
	WebUI.comment("❌ ERROR: Not enough patches selected to perform deselection test")
}
