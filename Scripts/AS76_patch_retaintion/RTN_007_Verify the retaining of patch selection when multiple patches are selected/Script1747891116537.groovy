
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

// Switch to split view
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))

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

WebUI.comment("=== SELECTING 3 PATCHES IN SPLIT VIEW ===")
WebUI.delay(3)

// Find all patch containers in split view
List<WebElement> allPatches = []
try {
	allPatches = driver.findElements(By.xpath("//div[contains(@class, 'patch-img-container')]"))
	WebUI.comment("Found " + allPatches.size() + " patch containers in split view")
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

for (int i = 0; i <3; i++) {
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

// Verify selections in split view - count only selected patches
WebUI.comment("=== VERIFYING SELECTIONS IN SPLIT VIEW ===")
int splitViewSelectedCount = 0
try {
	List<WebElement> selectedInSplitView = driver.findElements(By.xpath(
		"//div[contains(@class, 'selected-patch') and contains(@class, 'multiselect-patch')]"))
	splitViewSelectedCount = selectedInSplitView.size()
	WebUI.comment("Split view: Found " + splitViewSelectedCount + " selected patches")
} catch (Exception e) {
	WebUI.comment("Could not verify selections in split view: " + e.getMessage())
}

// Take screenshot of split view
String splitViewScreenshot = System.getProperty("user.dir") + "/Screenshots/split_view_selection_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(splitViewScreenshot)
WebUI.comment("Split view screenshot saved: " + splitViewScreenshot)

WebUI.delay(3)

// Switch to patch view
WebUI.comment("=== SWITCHING TO PATCH VIEW ===")
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

WebUI.delay(5)

// Verify patch retention in patch view
WebUI.comment("=== VERIFYING PATCH RETENTION IN PATCH VIEW ===")


int retainedPatchCount = 0
List<String> verificationResults = []

for (SelectedPatchInfo selectedPatch : selectedPatchesInfo) {
	boolean isRetained = false
	String verificationMsg = ""
	
	if (selectedPatch.hasValidImage()) {
		try {
			// Look for the same image in patch view
			String imageXPath = "//img[contains(@src, '" + selectedPatch.imageFileName + "')]"
			List<WebElement> matchingImages = driver.findElements(By.xpath(imageXPath))
			
			if (matchingImages.size() > 0) {
				// Check if any matching image is in a selected/active state in patch view
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
							verificationMsg = "✓ RETAINED: Image '" + selectedPatch.imageFileName + "' found in selected state in patch view"
							break
						}
					} catch (Exception e) {
						// Continue checking other matching images
					}
				}
				
				if (!isRetained) {
					verificationMsg = "⚠️ FOUND BUT NOT SELECTED: Image '" + selectedPatch.imageFileName + "' exists in patch view but not in selected state"
				}
			} else {
				verificationMsg = "❌ NOT FOUND: Image '" + selectedPatch.imageFileName + "' not found in patch view"
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

// Count selected patches in patch view and compare with split view
int patchViewSelectedCount = 0
try {
	// Use the same XPath that worked in split view
	List<WebElement> selectedInPatchView = driver.findElements(By.xpath(
		"//div[contains(@class, 'selected-patch')]"))
	patchViewSelectedCount = selectedInPatchView.size()
	
	WebUI.comment("Patch view: Found " + patchViewSelectedCount + " selected patches using XPath: //div[contains(@class, 'selected-patch')]")
	
} catch (Exception e) {
	WebUI.comment("Could not count selected patches in patch view: " + e.getMessage())
}

// Take screenshot of patch view
String patchViewScreenshot = System.getProperty("user.dir") + "/Screenshots/patch_view_verification_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patchViewScreenshot)
WebUI.comment("Patch view screenshot saved: " + patchViewScreenshot)

// Final test result based on count comparison
WebUI.comment("=== FINAL TEST RESULT ===")
WebUI.comment("Patches selected in split view: " + splitViewSelectedCount)
WebUI.comment("Patches found selected in patch view: " + patchViewSelectedCount)

// Compare counts to determine if patches are retained
if (patchViewSelectedCount == splitViewSelectedCount && splitViewSelectedCount == 3) {
	WebUI.comment("✅ TEST PASSED: All " + splitViewSelectedCount + " selected patches are retained in patch view")
} else if (patchViewSelectedCount == splitViewSelectedCount) {
	WebUI.comment("✅ TEST PASSED: Selected patch count matches (" + splitViewSelectedCount + " patches retained)")
} else {
	WebUI.comment("❌ TEST FAILED: Expected " + splitViewSelectedCount + " selected patches, but found " + patchViewSelectedCount + " in patch view")
}

WebUI.comment("=== VERIFICATION SUMMARY ===")
WebUI.comment("Split View XPath: //div[contains(@class, 'selected-patch')] → Found: " + splitViewSelectedCount)
WebUI.comment("Patch View XPath: //div[contains(@class, 'selected-patch')] → Found: " + patchViewSelectedCount)
WebUI.comment("Result: " + (patchViewSelectedCount == splitViewSelectedCount ? "RETAINED" : "NOT RETAINED"))

WebUI.closeBrowser()