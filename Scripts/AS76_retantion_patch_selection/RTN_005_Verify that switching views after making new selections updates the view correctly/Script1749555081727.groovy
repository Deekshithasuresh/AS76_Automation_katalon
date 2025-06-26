import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.JavascriptExecutor
import com.kms.katalon.core.webui.driver.DriverFactory
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
import org.openqa.selenium.Keys

// Login to the application
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/retain_patchs/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), Keys.chord(Keys.ENTER))
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

def driver = com.kms.katalon.core.webui.driver.DriverFactory.getWebDriver()
Actions actions = new Actions(driver)

// Enhanced data structure to store selected patch information
class SelectedPatchInfo {
	int index
	String imageSrc
	String imageFileName
	String patchId
	String position
	String viewSource // "patch" or "split"
	
	SelectedPatchInfo(WebElement element, int index, String viewSource = "patch") {
		this.index = index
		this.viewSource = viewSource
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
		return !imageFileName.isEmpty() && (imageFileName.contains('.jpg') || imageFileName.contains('.png'))
	}
	
	@Override
	String toString() {
		return "SelectedPatch[${index}] from ${viewSource} view - Image: ${imageFileName}, Position: ${position}"
	}
}

// Enhanced function to verify patch retention with multiple patch support
def verifyMultiplePatchRetention(List<SelectedPatchInfo> patchInfoList, String viewType) {
	Map<String, Object> results = [:]
	List<String> retainedPatches = []
	List<String> notFoundPatches = []
	List<String> foundButNotSelectedPatches = []
	
	for (SelectedPatchInfo patchInfo : patchInfoList) {
		if (patchInfo != null && patchInfo.hasValidImage()) {
			try {
				String imageXPath = "//img[contains(@src, '" + patchInfo.imageFileName + "')]"
				List<WebElement> matchingImages = driver.findElements(By.xpath(imageXPath))
				
				boolean isRetained = false
				if (matchingImages.size() > 0) {
					for (WebElement img : matchingImages) {
						try {
							WebElement parentContainer = img.findElement(By.xpath("./ancestor::div[contains(@class, 'Card') or contains(@class, 'card') or contains(@class, 'patch-container') or contains(@class, 'selected-patch') or contains(@class, 'selected') or @data-selected='true']"))
							String containerClasses = parentContainer.getAttribute('class') ?: ''
							String dataSelected = parentContainer.getAttribute('data-selected') ?: ''
							
							if (containerClasses.contains('selected') ||
								containerClasses.contains('active') ||
								containerClasses.contains('focus') ||
								containerClasses.contains('highlight') ||
								containerClasses.contains('selected-patch') ||
								dataSelected.equals('true')) {
								isRetained = true
								retainedPatches.add("Patch ${patchInfo.index + 1} (${patchInfo.viewSource})")
								break
							}
						} catch (Exception e) {
							// Continue checking other matching images
						}
					}
					
					if (!isRetained) {
						foundButNotSelectedPatches.add("Patch ${patchInfo.index + 1} (${patchInfo.viewSource})")
					}
				} else {
					notFoundPatches.add("Patch ${patchInfo.index + 1} (${patchInfo.viewSource})")
				}
			} catch (Exception e) {
				notFoundPatches.add("Patch ${patchInfo.index + 1} (${patchInfo.viewSource}) - Error: ${e.getMessage()}")
			}
		}
	}
	
	results.retainedPatches = retainedPatches
	results.notFoundPatches = notFoundPatches
	results.foundButNotSelectedPatches = foundButNotSelectedPatches
	results.totalRetained = retainedPatches.size()
	results.expectedCount = patchInfoList.size()
	results.allRetained = (results.totalRetained == results.expectedCount)
	
	return results
}

// Function to count selected patches with enhanced detection
def countSelectedPatches(String context) {
	int selectedCount = 0
	try {
		// Try multiple XPath patterns to find selected patches
		List<String> xpathPatterns = [
			"//div[contains(@class, 'selected-patch')]",
			"//div[contains(@class, 'selected')]",
			"//div[@data-selected='true']",
			"//div[contains(@class, 'active')]",
			"//div[contains(@class, 'highlight')]"
		]
		
		Set<WebElement> allSelectedElements = new HashSet<>()
		
		for (String xpath : xpathPatterns) {
			try {
				List<WebElement> elements = driver.findElements(By.xpath(xpath))
				allSelectedElements.addAll(elements)
			} catch (Exception e) {
				// Continue with next pattern
			}
		}
		
		selectedCount = allSelectedElements.size()
		WebUI.comment("${context}: Found ${selectedCount} selected patches using enhanced detection")
	} catch (Exception e) {
		WebUI.comment("Could not count selected patches in ${context}: ${e.getMessage()}")
	}
	return selectedCount
}

// Function to find patches in split view
def findPatchesInSplitView() {
	List<WebElement> splitViewPatches = []
	try {
		// Try different XPath patterns for split view patches
		List<String> splitViewXPaths = [
			"//div[contains(@class, 'split-view')]//div[contains(@class, 'patch')]",
			"//div[contains(@class, 'patch-container')]",
			"//div[contains(@class, 'patch-img-container')]",
			"//img[contains(@src, '.jpg') or contains(@src, '.png')]/parent::div"
		]
		
		for (String xpath : splitViewXPaths) {
			try {
				List<WebElement> elements = driver.findElements(By.xpath(xpath))
				if (elements.size() > 0) {
					splitViewPatches = elements
					WebUI.comment("Found ${elements.size()} patches in split view using xpath: ${xpath}")
					break
				}
			} catch (Exception e) {
				continue
			}
		}
	} catch (Exception e) {
		WebUI.comment("Error finding patches in split view: ${e.getMessage()}")
	}
	return splitViewPatches
}

// Test results storage
Map<String, Object> testResults = [:]
List<SelectedPatchInfo> selectedPatches = []

WebUI.comment("===========================================================================")
WebUI.comment("=== ENHANCED PATCH RETENTION TEST: PATCH VIEW + SPLIT VIEW SELECTION ===")
WebUI.comment("===========================================================================")

// ===== PHASE 1: SELECT PATCH IN PATCH VIEW =====
WebUI.comment("\n🔵 PHASE 1: SELECTING PATCH IN PATCH VIEW")
WebUI.delay(3)

// Find all patch containers in patch view
List<WebElement> patchViewPatches = []
try {
	patchViewPatches = driver.findElements(By.xpath("//div[contains(@class, 'patch-img-container')]"))
	WebUI.comment("Found " + patchViewPatches.size() + " patch containers in patch view")
} catch (Exception e) {
	WebUI.comment("Error finding patches in patch view: " + e.getMessage())
}

// Try alternative XPath if first one doesn't work
if (patchViewPatches.size() == 0) {
	try {
		patchViewPatches = driver.findElements(By.xpath("//div[contains(@class, 'patch-container')]"))
		WebUI.comment("Found " + patchViewPatches.size() + " patches using alternative XPath")
	} catch (Exception e) {
		WebUI.comment("Error with alternative XPath: " + e.getMessage())
	}
}

if (patchViewPatches.size() == 0) {
	WebUI.comment("ERROR: No patches found in patch view")
	WebUI.closeBrowser()
	return
}

// Select first patch in patch view
SelectedPatchInfo patchViewSelection = null
try {
	WebElement patchToSelect = patchViewPatches.get(0)
	patchViewSelection = new SelectedPatchInfo(patchToSelect, 0, "patch")
	
	WebUI.comment("Selecting patch in patch view: " + patchViewSelection.toString())
	
	((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patchToSelect)
	WebUI.delay(1)
	
	patchToSelect.click()
	WebUI.comment("✓ Selected patch in patch view")
	selectedPatches.add(patchViewSelection)
	WebUI.delay(2)
} catch (Exception e) {
	WebUI.comment("❌ Error selecting patch in patch view: " + e.getMessage())
	WebUI.closeBrowser()
	return
}

// Verify initial selection in patch view
int initialPatchViewCount = countSelectedPatches("Initial patch view selection")
String initialScreenshot = System.getProperty("user.dir") + "/Screenshots/initial_patch_selection_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(initialScreenshot)
WebUI.comment("Initial selection screenshot saved: " + initialScreenshot)

// ===== PHASE 2: SWITCH TO SPLIT VIEW =====
WebUI.comment("\n🟠 PHASE 2: SWITCHING TO SPLIT VIEW")
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))
WebUI.delay(5)

// Verify patch retention in split view
def splitViewRetentionCheck = verifyMultiplePatchRetention(selectedPatches, "split view")
WebUI.comment("Split view retention check:")
WebUI.comment("  ✓ Retained: ${splitViewRetentionCheck.retainedPatches}")
WebUI.comment("  ❌ Not found: ${splitViewRetentionCheck.notFoundPatches}")
WebUI.comment("  ⚠️ Found but not selected: ${splitViewRetentionCheck.foundButNotSelectedPatches}")

int splitViewInitialCount = countSelectedPatches("Split view after transition")
String splitViewInitialScreenshot = System.getProperty("user.dir") + "/Screenshots/split_view_initial_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(splitViewInitialScreenshot)
WebUI.comment("Split view initial screenshot saved: " + splitViewInitialScreenshot)

// ===== PHASE 3: SELECT ADDITIONAL PATCH IN SPLIT VIEW =====
WebUI.comment("\n🟢 PHASE 3: SELECTING ADDITIONAL PATCH IN SPLIT VIEW")
WebUI.delay(2)

// Find patches in split view
List<WebElement> splitViewPatches = findPatchesInSplitView()

if (splitViewPatches.size() < 2) {
	WebUI.comment("WARNING: Only ${splitViewPatches.size()} patches found in split view. Looking for any available patch to select.")
	
	// Try to find any clickable patch elements
	try {
		splitViewPatches = driver.findElements(By.xpath("//img[contains(@src, '.jpg') or contains(@src, '.png')]/parent::div"))
		WebUI.comment("Found ${splitViewPatches.size()} image containers in split view")
	} catch (Exception e) {
		WebUI.comment("Could not find image containers: ${e.getMessage()}")
	}
}

SelectedPatchInfo splitViewSelection = null
if (splitViewPatches.size() > 0) {
	try {
		// Try to select a different patch (not the first one if possible)
		int indexToSelect = splitViewPatches.size() > 1 ? 1 : 0
		WebElement splitPatchToSelect = splitViewPatches.get(indexToSelect)
		splitViewSelection = new SelectedPatchInfo(splitPatchToSelect, indexToSelect, "split")
		
		WebUI.comment("Selecting additional patch in split view: " + splitViewSelection.toString())
		
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", splitPatchToSelect)
		WebUI.delay(1)
		
		// Try clicking with JavaScript if normal click doesn't work
		try {
			splitPatchToSelect.click()
			WebUI.comment("✓ Selected additional patch in split view (normal click)")
		} catch (Exception e) {
			((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", splitPatchToSelect)
			WebUI.comment("✓ Selected additional patch in split view (JavaScript click)")
		}
		
		selectedPatches.add(splitViewSelection)
		WebUI.delay(2)
	} catch (Exception e) {
		WebUI.comment("❌ Error selecting additional patch in split view: " + e.getMessage())
	}
} else {
	WebUI.comment("⚠️ No additional patches found in split view to select")
}

// Verify selections after split view selection
int splitViewAfterSelectionCount = countSelectedPatches("Split view after additional selection")
def splitViewAfterSelectionCheck = verifyMultiplePatchRetention(selectedPatches, "split view after selection")
WebUI.comment("Split view after selection check:")
WebUI.comment("  ✓ Retained: ${splitViewAfterSelectionCheck.retainedPatches}")
WebUI.comment("  Total retained: ${splitViewAfterSelectionCheck.totalRetained}/${splitViewAfterSelectionCheck.expectedCount}")

String splitViewAfterSelectionScreenshot = System.getProperty("user.dir") + "/Screenshots/split_view_after_selection_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(splitViewAfterSelectionScreenshot)
WebUI.comment("Split view after selection screenshot saved: " + splitViewAfterSelectionScreenshot)

// ===== PHASE 4: RETURN TO PATCH VIEW AND VERIFY RETENTION =====
WebUI.comment("\n🔴 PHASE 4: RETURNING TO PATCH VIEW AND VERIFYING RETENTION")
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
WebUI.delay(5)

// Final verification of all selected patches
def finalRetentionCheck = verifyMultiplePatchRetention(selectedPatches, "patch view final")
int finalPatchViewCount = countSelectedPatches("Final patch view")

WebUI.comment("Final patch view retention check:")
WebUI.comment("  ✓ Retained patches: ${finalRetentionCheck.retainedPatches}")
WebUI.comment("  ❌ Not found patches: ${finalRetentionCheck.notFoundPatches}")
WebUI.comment("  ⚠️ Found but not selected: ${finalRetentionCheck.foundButNotSelectedPatches}")
WebUI.comment("  📊 Total retained: ${finalRetentionCheck.totalRetained}/${finalRetentionCheck.expectedCount}")

String finalScreenshot = System.getProperty("user.dir") + "/Screenshots/final_patch_view_verification_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(finalScreenshot)
WebUI.comment("Final verification screenshot saved: " + finalScreenshot)

// ===== COMPREHENSIVE TEST RESULTS =====
WebUI.comment("\n===========================================================================")
WebUI.comment("=== COMPREHENSIVE TEST RESULTS SUMMARY ===")
WebUI.comment("===========================================================================")

// Calculate test results
boolean patchViewSelectionSuccess = (initialPatchViewCount >= 1)
boolean splitViewRetentionSuccess = (splitViewInitialCount >= 1)
boolean splitViewSelectionSuccess = (splitViewAfterSelectionCount >= splitViewInitialCount)
boolean finalRetentionSuccess = (finalRetentionCheck.totalRetained >= 1)
boolean overallSuccess = finalRetentionCheck.allRetained

WebUI.comment("\n📊 DETAILED TEST RESULTS:")
WebUI.comment("   ├─ Patch View Selection: " + (patchViewSelectionSuccess ? "✓ PASSED" : "❌ FAILED") + " (${initialPatchViewCount} selected)")
WebUI.comment("   ├─ Split View Retention: " + (splitViewRetentionSuccess ? "✓ PASSED" : "❌ FAILED") + " (${splitViewInitialCount} retained)")
WebUI.comment("   ├─ Split View Selection: " + (splitViewSelectionSuccess ? "✓ PASSED" : "❌ FAILED") + " (${splitViewAfterSelectionCount} total)")
WebUI.comment("   ├─ Final Retention: " + (finalRetentionSuccess ? "✓ PASSED" : "❌ FAILED") + " (${finalRetentionCheck.totalRetained}/${finalRetentionCheck.expectedCount} retained)")
WebUI.comment("   └─ Overall Result: " + (overallSuccess ? "🎉 PASSED" : "❌ FAILED"))

WebUI.comment("\n📋 SELECTED PATCHES SUMMARY:")
for (int i = 0; i < selectedPatches.size(); i++) {
	SelectedPatchInfo patch = selectedPatches.get(i)
	WebUI.comment("   ${i + 1}. ${patch.toString()}")
}

WebUI.comment("\n🏆 FINAL TEST RESULT:")
if (overallSuccess) {
	WebUI.comment("🎉🎉 ENHANCED PATCH RETENTION TEST PASSED! 🎉🎉")
	WebUI.comment("✓ Patch selected in patch view was retained")
	WebUI.comment("✓ Additional patch selected in split view was retained")
	WebUI.comment("✓ All selections were properly retained when returning to patch view")
} else {
	WebUI.comment("❌❌ ENHANCED PATCH RETENTION TEST FAILED! ❌❌")
	WebUI.comment("Issues detected with patch retention functionality:")
	WebUI.comment("Expected ${selectedPatches.size()} patches to be retained, but only ${finalRetentionCheck.totalRetained} were found")
}

WebUI.comment("\n📋 TEST EXECUTION SUMMARY:")
WebUI.comment("Total patches selected: ${selectedPatches.size()}")
WebUI.comment("Patches retained: ${finalRetentionCheck.totalRetained}")
WebUI.comment("Screenshots captured: 4")
WebUI.comment("Test completion: " + (overallSuccess ? "SUCCESS" : "FAILURE"))

WebUI.closeBrowser()