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

// Function to verify patch retention
def verifyPatchRetention(SelectedPatchInfo patchInfo, String viewType) {
	boolean isRetained = false
	String verificationMsg = ""
	
	if (patchInfo != null && patchInfo.hasValidImage()) {
		try {
			String imageXPath = "//img[contains(@src, '" + patchInfo.imageFileName + "')]"
			List<WebElement> matchingImages = driver.findElements(By.xpath(imageXPath))
			
			if (matchingImages.size() > 0) {
				for (WebElement img : matchingImages) {
					try {
						WebElement parentContainer = img.findElement(By.xpath("./ancestor::div[contains(@class, 'Card') or contains(@class, 'card') or contains(@class, 'patch-container') or contains(@class, 'selected-patch')]"))
						String containerClasses = parentContainer.getAttribute('class') ?: ''
						
						if (containerClasses.contains('selected') ||
							containerClasses.contains('active') ||
							containerClasses.contains('focus') ||
							containerClasses.contains('highlight') ||
							containerClasses.contains('selected-patch')) {
							isRetained = true
							verificationMsg = "✓ RETAINED: Patch ${patchInfo.index + 1} image '${patchInfo.imageFileName}' found in selected state in ${viewType}"
							break
						}
					} catch (Exception e) {
						// Continue checking other matching images
					}
				}
				
				if (!isRetained) {
					verificationMsg = "⚠️ FOUND BUT NOT SELECTED: Patch ${patchInfo.index + 1} image '${patchInfo.imageFileName}' exists in ${viewType} but not in selected state"
				}
			} else {
				verificationMsg = "❌ NOT FOUND: Patch ${patchInfo.index + 1} image '${patchInfo.imageFileName}' not found in ${viewType}"
			}
		} catch (Exception e) {
			verificationMsg = "❌ ERROR: Could not verify patch ${patchInfo.index + 1} in ${viewType} - ${e.getMessage()}"
		}
	} else {
		verificationMsg = "⚠️ CANNOT VERIFY: No valid patch ${patchInfo.index + 1} information available"
	}
	
	return [isRetained: isRetained, message: verificationMsg]
}

// Function to count selected patches
def countSelectedPatches(String context) {
	int selectedCount = 0
	try {
		List<WebElement> selectedPatches = driver.findElements(By.xpath("//div[contains(@class, 'selected-patch')]"))
		selectedCount = selectedPatches.size()
		WebUI.comment("${context}: Found ${selectedCount} selected patches")
	} catch (Exception e) {
		WebUI.comment("Could not count selected patches in ${context}: ${e.getMessage()}")
	}
	return selectedCount
}

// Test results storage
Map<String, Object> testResults = [:]

WebUI.comment("===========================================================================")
WebUI.comment("=== COMPREHENSIVE PATCH RETENTION TEST: PATCH 1 + PATCH 2 ===")
WebUI.comment("===========================================================================")

// ===== PHASE 1: TEST PATCH 1 RETENTION =====
WebUI.comment("\n🔵 PHASE 1: TESTING PATCH 1 RETENTION")
WebUI.comment("=== SELECTING PATCH 1 (INDEX 0) IN PATCH VIEW ===")
WebUI.delay(3)

SelectedPatchInfo patch1Info = null

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

if (allPatches.size() < 2) {
	WebUI.comment("ERROR: Need at least 2 patches for this test, but only found " + allPatches.size())
	WebUI.closeBrowser()
	return
}

// Select patch 1 (index 0)
try {
	WebElement patch1ToSelect = allPatches.get(0)
	patch1Info = new SelectedPatchInfo(patch1ToSelect, 0)
	
	WebUI.comment("Selecting patch 1: " + patch1Info.toString())
	
	((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patch1ToSelect)
	WebUI.delay(1)
	
	patch1ToSelect.click()
	WebUI.comment("✓ Selected patch 1 (normal click)")
	WebUI.delay(1)
} catch (Exception e) {
	WebUI.comment("❌ Error selecting patch 1: " + e.getMessage())
	WebUI.closeBrowser()
	return
}

// Verify patch 1 selection in patch view
int patch1InitialCount = countSelectedPatches("Patch 1 - Initial patch view")
String patch1InitialScreenshot = System.getProperty("user.dir") + "/Screenshots/patch1_initial_selection_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patch1InitialScreenshot)
WebUI.comment("Patch 1 initial screenshot saved: " + patch1InitialScreenshot)

// Switch to split view for patch 1
WebUI.comment("=== PATCH 1: SWITCHING TO SPLIT VIEW ===")
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))
WebUI.delay(5)

// Verify patch 1 retention in split view
def patch1SplitResult = verifyPatchRetention(patch1Info, "split view")
WebUI.comment("Patch 1 split view verification: " + patch1SplitResult.message)
int patch1SplitCount = countSelectedPatches("Patch 1 - Split view")

String patch1SplitScreenshot = System.getProperty("user.dir") + "/Screenshots/patch1_split_view_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patch1SplitScreenshot)
WebUI.comment("Patch 1 split view screenshot saved: " + patch1SplitScreenshot)

// Switch back to patch view for patch 1
WebUI.comment("=== PATCH 1: SWITCHING BACK TO PATCH VIEW ===")
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
WebUI.delay(5)

// Verify patch 1 retention after return
def patch1ReturnResult = verifyPatchRetention(patch1Info, "patch view after return")
WebUI.comment("Patch 1 return verification: " + patch1ReturnResult.message)
int patch1ReturnCount = countSelectedPatches("Patch 1 - Return to patch view")

String patch1ReturnScreenshot = System.getProperty("user.dir") + "/Screenshots/patch1_return_verification_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patch1ReturnScreenshot)
WebUI.comment("Patch 1 return screenshot saved: " + patch1ReturnScreenshot)

// Store patch 1 test results
testResults.patch1 = [
	initialCount: patch1InitialCount,
	splitCount: patch1SplitCount,
	returnCount: patch1ReturnCount,
	splitRetained: patch1SplitResult.isRetained,
	returnRetained: patch1ReturnResult.isRetained,
	overallPassed: (patch1InitialCount == 1 && patch1SplitCount == 1 && patch1ReturnCount == 1)
]

WebUI.comment("🔵 PATCH 1 TEST COMPLETED")

// ===== PHASE 2: TEST PATCH 2 RETENTION =====
WebUI.comment("\n🟠 PHASE 2: TESTING PATCH 2 RETENTION")
WebUI.comment("=== DESELECTING PATCH 1 AND SELECTING PATCH 2 (INDEX 1) ===")
WebUI.delay(3)

SelectedPatchInfo patch2Info = null

// First, deselect patch 1 if it's still selected
try {
	WebElement patch1Element = allPatches.get(0)
	patch1Element.click() // Click to deselect
	WebUI.comment("✓ Deselected patch 1")
	WebUI.delay(1)
} catch (Exception e) {
	WebUI.comment("Note: Could not explicitly deselect patch 1: " + e.getMessage())
}

// Select patch 2 (index 1)
try {
	WebElement patch2ToSelect = allPatches.get(1)
	patch2Info = new SelectedPatchInfo(patch2ToSelect, 1)
	
	WebUI.comment("Selecting patch 2: " + patch2Info.toString())
	
	((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patch2ToSelect)
	WebUI.delay(1)
	
	patch2ToSelect.click()
	WebUI.comment("✓ Selected patch 2 (normal click)")
	WebUI.delay(1)
} catch (Exception e) {
	WebUI.comment("❌ Error selecting patch 2: " + e.getMessage())
	WebUI.closeBrowser()
	return
}

// Verify patch 2 selection in patch view
int patch2InitialCount = countSelectedPatches("Patch 2 - Initial patch view")
String patch2InitialScreenshot = System.getProperty("user.dir") + "/Screenshots/patch2_initial_selection_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patch2InitialScreenshot)
WebUI.comment("Patch 2 initial screenshot saved: " + patch2InitialScreenshot)

// Switch to split view for patch 2
WebUI.comment("=== PATCH 2: SWITCHING TO SPLIT VIEW ===")
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))
WebUI.delay(5)

// Verify patch 2 retention in split view
def patch2SplitResult = verifyPatchRetention(patch2Info, "split view")
WebUI.comment("Patch 2 split view verification: " + patch2SplitResult.message)
int patch2SplitCount = countSelectedPatches("Patch 2 - Split view")

String patch2SplitScreenshot = System.getProperty("user.dir") + "/Screenshots/patch2_split_view_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patch2SplitScreenshot)
WebUI.comment("Patch 2 split view screenshot saved: " + patch2SplitScreenshot)

// Switch back to patch view for patch 2
WebUI.comment("=== PATCH 2: SWITCHING BACK TO PATCH VIEW ===")
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))
WebUI.delay(5)

// Verify patch 2 retention after return
def patch2ReturnResult = verifyPatchRetention(patch2Info, "patch view after return")
WebUI.comment("Patch 2 return verification: " + patch2ReturnResult.message)
int patch2ReturnCount = countSelectedPatches("Patch 2 - Return to patch view")

String patch2ReturnScreenshot = System.getProperty("user.dir") + "/Screenshots/patch2_return_verification_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patch2ReturnScreenshot)
WebUI.comment("Patch 2 return screenshot saved: " + patch2ReturnScreenshot)

// Store patch 2 test results
testResults.patch2 = [
	initialCount: patch2InitialCount,
	splitCount: patch2SplitCount,
	returnCount: patch2ReturnCount,
	splitRetained: patch2SplitResult.isRetained,
	returnRetained: patch2ReturnResult.isRetained,
	overallPassed: (patch2InitialCount == 1 && patch2SplitCount == 1 && patch2ReturnCount == 1)
]

WebUI.comment("🟠 PATCH 2 TEST COMPLETED")

// ===== COMPREHENSIVE TEST RESULTS =====
WebUI.comment("\n===========================================================================")
WebUI.comment("=== COMPREHENSIVE TEST RESULTS SUMMARY ===")
WebUI.comment("===========================================================================")

WebUI.comment("\n📊 PATCH 1 RESULTS:")
WebUI.comment("   ├─ Initial Selection: " + (testResults.patch1.initialCount == 1 ? "✓ PASSED" : "❌ FAILED") + " (${testResults.patch1.initialCount} selected)")
WebUI.comment("   ├─ Split View Retention: " + (testResults.patch1.splitCount == 1 ? "✓ PASSED" : "❌ FAILED") + " (${testResults.patch1.splitCount} selected)")
WebUI.comment("   ├─ Return Retention: " + (testResults.patch1.returnCount == 1 ? "✓ PASSED" : "❌ FAILED") + " (${testResults.patch1.returnCount} selected)")
WebUI.comment("   └─ Overall Result: " + (testResults.patch1.overallPassed ? "🎉 PASSED" : "❌ FAILED"))

WebUI.comment("\n📊 PATCH 2 RESULTS:")
WebUI.comment("   ├─ Initial Selection: " + (testResults.patch2.initialCount == 1 ? "✓ PASSED" : "❌ FAILED") + " (${testResults.patch2.initialCount} selected)")
WebUI.comment("   ├─ Split View Retention: " + (testResults.patch2.splitCount == 1 ? "✓ PASSED" : "❌ FAILED") + " (${testResults.patch2.splitCount} selected)")
WebUI.comment("   ├─ Return Retention: " + (testResults.patch2.returnCount == 1 ? "✓ PASSED" : "❌ FAILED") + " (${testResults.patch2.returnCount} selected)")
WebUI.comment("   └─ Overall Result: " + (testResults.patch2.overallPassed ? "🎉 PASSED" : "❌ FAILED"))

// Final comprehensive result
boolean overallTestPassed = testResults.patch1.overallPassed && testResults.patch2.overallPassed

WebUI.comment("\n🏆 FINAL COMPREHENSIVE RESULT:")
if (overallTestPassed) {
	WebUI.comment("🎉🎉 COMPREHENSIVE TEST SUITE PASSED! 🎉🎉")
	WebUI.comment("Both Patch 1 and Patch 2 retention functionality is working correctly.")
	WebUI.comment("✓ Patch selections are properly retained across view transitions.")
} else {
	WebUI.comment("❌❌ COMPREHENSIVE TEST SUITE FAILED! ❌❌")
	WebUI.comment("Issues detected with patch retention functionality:")
	if (!testResults.patch1.overallPassed) {
		WebUI.comment("   • Patch 1 retention has issues")
	}
	if (!testResults.patch2.overallPassed) {
		WebUI.comment("   • Patch 2 retention has issues")
	}
}

WebUI.comment("\n📋 TEST EXECUTION SUMMARY:")
WebUI.comment("XPath used for detection: //div[contains(@class, 'selected-patch')]")
WebUI.comment("Total screenshots captured: 6 (3 for each patch)")
WebUI.comment("Test completion: " + (overallTestPassed ? "SUCCESS" : "FAILURE"))

WebUI.closeBrowser()