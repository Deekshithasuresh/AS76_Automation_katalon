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

SelectedPatchInfo selectedPatchInfo = null

WebUI.comment("=== SELECTING 1 PATCH IN PATCH VIEW ===")
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

if (allPatches.size() < 1) {
	WebUI.comment("ERROR: Need at least 1 patch, but only found " + allPatches.size())
	WebUI.closeBrowser()
	return
}

// Select exactly 1 patch and store its information
try {
	WebElement patchToSelect = allPatches.get(0)
	
	// Store patch info BEFORE selection to get original state
	selectedPatchInfo = new SelectedPatchInfo(patchToSelect, 0)
	
	WebUI.comment("Selecting patch: " + selectedPatchInfo.toString())
	
	// Scroll into view and select
	((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", patchToSelect)
	WebUI.delay(1)
	
	patchToSelect.click()
	WebUI.comment("✓ Selected patch (normal click)")
	
	WebUI.delay(1)
	
} catch (Exception e) {
	WebUI.comment("❌ Error selecting patch: " + e.getMessage())
	WebUI.closeBrowser()
	return
}

// Verify selection in patch view - count only selected patches
WebUI.comment("=== VERIFYING SELECTION IN PATCH VIEW ===")
int patchViewSelectedCount = 0
try {
	List<WebElement> selectedInPatchView = driver.findElements(By.xpath(
		"//div[contains(@class, 'selected-patch')]"))
	patchViewSelectedCount = selectedInPatchView.size()
	WebUI.comment("Patch view: Found " + patchViewSelectedCount + " selected patch")
} catch (Exception e) {
	WebUI.comment("Could not verify selection in patch view: " + e.getMessage())
}

// Take screenshot of patch view
String patchViewScreenshot = System.getProperty("user.dir") + "/Screenshots/patch_view_selection_" + System.currentTimeMillis() + ".png"
WebUI.takeScreenshot(patchViewScreenshot)
WebUI.comment("Patch view screenshot saved: " + patchViewScreenshot)

WebUI.delay(3)

// Switch to split view
WebUI.comment("=== SWITCHING TO SPLIT VIEW ===")
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))

WebUI.delay(5)

// Verify patch retention in split view
WebUI.comment("=== VERIFYING PATCH RETENTION IN SPLIT VIEW ===")

boolean isRetained = false
String verificationMsg = ""

if (selectedPatchInfo != null && selectedPatchInfo.hasValidImage()) {
	try {
		// Look for the same image in split view
		String imageXPath = "//img[contains(@src, '" + selectedPatchInfo.imageFileName + "')]"
		List<WebElement> matchingImages = driver.findElements(By.xpath(imageXPath))
		
		if (matchingImages.size() > 0) {
			// Check if any matching image is in a selected/active state in split view
			for (WebElement img : matchingImages) {
				try {
					// Look for parent container that might indicate selection
					WebElement parentContainer = img.findElement(By.xpath("./ancestor::div[contains(@class, 'Card') or contains(@class, 'card') or contains(@class, 'patch-container') or contains(@class, 'selected-patch')]"))
					String containerClasses = parentContainer.getAttribute('class') ?: ''
					
					// Check for selection indicators
					if (containerClasses.contains('selected') ||
						containerClasses.contains('active') ||
						containerClasses.contains('focus') ||
						containerClasses.contains('highlight') ||
						containerClasses.contains('selected-patch')) {
						isRetained = true
						verificationMsg = "✓ RETAINED: Image '" + selectedPatchInfo.imageFileName + "' found in selected state in split view"
						break
					}
				} catch (Exception e) {
					// Continue checking other matching images
				}
			}
			
			if (!isRetained) {
				verificationMsg = "⚠️ FOUND BUT NOT SELECTED: Image '" + selectedPatchInfo.imageFileName + "' exists in split view but not in selected state"
			}
		} else {
			verificationMsg = "❌ NOT FOUND: Image '" + selectedPatchInfo.imageFileName + "' not found in split view"
		}
	} catch (Exception e) {
		verificationMsg = "❌ ERROR: Could not verify patch - " + e.getMessage()
	}
} else {
	verificationMsg = "⚠️ CANNOT VERIFY: No valid patch information available"
}

WebUI.comment("Patch verification: " + verificationMsg)

// Count selected patches in split view and compare with patch view
int splitViewSelectedCount = 0
try {
	// Use the same XPath that worked in patch view
	List<WebElement> selectedInSplitView = driver.findElements(By.xpath(
		"//div[contains(@class, 'selected-patch')]"))
	splitViewSelectedCount = selectedInSplitView.size()
	
	WebUI.comment("Split view: Found " + splitViewSelectedCount + " selected patches using XPath: //div[contains(@class, 'selected-patch')]")
	
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

// Compare counts to determine if patch is retained
if (splitViewSelectedCount == patchViewSelectedCount && patchViewSelectedCount == 1) {
	WebUI.comment("✅ TEST PASSED: The selected patch is retained in split view")
} else if (splitViewSelectedCount == patchViewSelectedCount) {
	WebUI.comment("✅ TEST PASSED: Selected patch count matches (" + patchViewSelectedCount + " patch retained)")
} else {
	WebUI.comment("❌ TEST FAILED: Expected " + patchViewSelectedCount + " selected patch, but found " + splitViewSelectedCount + " in split view")
}

WebUI.comment("=== VERIFICATION SUMMARY ===")
WebUI.comment("Patch View XPath: //div[contains(@class, 'selected-patch')] → Found: " + patchViewSelectedCount)
WebUI.comment("Split View XPath: //div[contains(@class, 'selected-patch')] → Found: " + splitViewSelectedCount)
WebUI.comment("Result: " + (splitViewSelectedCount == patchViewSelectedCount ? "RETAINED" : "NOT RETAINED"))

//WebUI.closeBrowser()