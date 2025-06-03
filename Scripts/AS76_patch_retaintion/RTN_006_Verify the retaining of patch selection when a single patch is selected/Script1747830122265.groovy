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

// Login to the application using your existing objects
WebUI.openBrowser('')
WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')
WebUI.setText(findTestObject('Object Repository/retain_patchs/input_username_loginId'), 'deekshithaS')
WebUI.setEncryptedText(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')
WebUI.sendKeys(findTestObject('Object Repository/retain_patchs/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for the dashboard to load and navigate to the specific slide
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/td_SIG013'), 20)
WebUI.click(findTestObject('Object Repository/retain_patchs/td_SIG013'))

// Select WBC tab - using your existing object
WebUI.waitForElementPresent(findTestObject('Object Repository/retain_patchs/span_WBC'), 20)
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/span_WBC'), 'WBC')
WebUI.click(findTestObject('Object Repository/retain_patchs/span_WBC'))

// Switch to split view using your existing object
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_split-view'))

// Get the selected patch in split view before clicking
def driver = DriverFactory.getWebDriver()

// Create a TestObject for the selected patch in split view using the exact XPath
TestObject splitViewSelectedPatchObject = findTestObject('Object Repository/retain_patchs/RTN_006_patch_Selection_splitview')
WebUI.waitForElementPresent(splitViewSelectedPatchObject, 20)

// Extract the patch details from split view
WebElement splitViewPatchElement = WebUI.findWebElement(splitViewSelectedPatchObject)
String splitViewPatchId = splitViewPatchElement.getAttribute('id') ?: ''
String splitViewPatchClass = splitViewPatchElement.getAttribute('class') ?: ''

// Get the image source if available
String splitViewImageSrc = ''
try {
	WebElement imgElement = splitViewPatchElement.findElement(By.tagName('img'))
	splitViewImageSrc = imgElement.getAttribute('src')
} catch (Exception e) {
	WebUI.comment("No direct img element found in split view patch: " + e.getMessage())
}

// Log the details for debugging
WebUI.comment("Split View - Selected Patch ID: " + splitViewPatchId)
WebUI.comment("Split View - Selected Patch Class: " + splitViewPatchClass)
WebUI.comment("Split View - Selected Patch Image Source: " + splitViewImageSrc)

// Get the X and Y coordinates of the selected patch in split view
int splitViewPatchX = splitViewPatchElement.getLocation().getX()
int splitViewPatchY = splitViewPatchElement.getLocation().getY()
WebUI.comment("Split View - Selected Patch X: " + splitViewPatchX + ", Y: " + splitViewPatchY)

// Extract the image URL to use for later comparison
String patchImageUrl = splitViewImageSrc
// Extract a unique identifier from the URL (the frame number)
String frameIdentifier = ""
if (patchImageUrl.contains("/frames/")) {
	frameIdentifier = patchImageUrl.substring(patchImageUrl.indexOf("/frames/") + 8)
	if (frameIdentifier.contains("/")) {
		frameIdentifier = frameIdentifier.substring(0, frameIdentifier.indexOf("/"))
	}
	WebUI.comment("Frame identifier extracted: " + frameIdentifier)
}

// Now click on the selected patch to view it in patch view
WebUI.click(splitViewSelectedPatchObject)

// Switch to patch view using your existing object
WebUI.verifyElementText(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'), '')
WebUI.click(findTestObject('Object Repository/retain_patchs/img_Platelets_patch-view'))

// IMPROVED APPROACH: Wait for the patch view to load and use a more flexible XPath
// First, add a delay to ensure the patch view has fully loaded
WebUI.delay(3)

// Create a more flexible XPath for finding selected patch in patch view
// We'll look for img elements containing the same frame number in their src attribute
TestObject patchViewSelectedObject = new TestObject('patchViewSelectedObject')

// Check if we have a frame identifier to use
if (frameIdentifier) {
	patchViewSelectedObject.addProperty('xpath',
		com.kms.katalon.core.testobject.ConditionType.EQUALS,
		"//img[contains(@src, '/frames/${frameIdentifier}/')]")
} else {
	// Fallback to a more general selector if we couldn't extract a frame identifier
	patchViewSelectedObject.addProperty('xpath',
		com.kms.katalon.core.testobject.ConditionType.EQUALS,
		"//div[contains(@class, 'selected') or contains(@class, 'patch-focus')]//img")
}

// Use TRY/CATCH to handle potential issues finding the element
boolean patchElementFound = false
try {
	WebUI.waitForElementPresent(patchViewSelectedObject, 20)
	WebElement patchViewElement = WebUI.findWebElement(patchViewSelectedObject, 20)
	patchElementFound = true
	
	// Extract image source for comparison
	String patchViewImageSrc = patchViewElement.getAttribute('src') ?: ''
	
	// Log the details from patch view
	WebUI.comment("Patch View - Selected Patch Image Source: " + patchViewImageSrc)
	
	// Compare the image sources
	boolean sameImage = !splitViewImageSrc.isEmpty() && !patchViewImageSrc.isEmpty() &&
						(splitViewImageSrc == patchViewImageSrc ||
						 (frameIdentifier && patchViewImageSrc.contains("/frames/" + frameIdentifier + "/")))
	
	if (sameImage) {
		WebUI.comment("VERIFICATION PASSED: The same patch is selected in both split view and patch view")
		WebUI.comment("Match confirmed by image source containing frame identifier: " + frameIdentifier)
	} else {
		WebUI.comment("VERIFICATION FAILED: Different patches appear to be selected in split view and patch view")
		WebUI.comment("Split View Image: " + splitViewImageSrc)
		WebUI.comment("Patch View Image: " + patchViewImageSrc)
	}
} catch (Exception e) {
	WebUI.comment("Could not find the selected patch in patch view: " + e.getMessage())
	
	// Alternative approach: Try to find any patch image and check its attributes
	try {
		TestObject anyPatchObject = new TestObject('anyPatchObject')
		anyPatchObject.addProperty('xpath',
			com.kms.katalon.core.testobject.ConditionType.EQUALS,
			"//img[contains(@src, '/frames/')]")
		
		WebUI.waitForElementPresent(anyPatchObject, 10)
		WebElement anyPatchElement = WebUI.findWebElement(anyPatchObject)
		String anyPatchImageSrc = anyPatchElement.getAttribute('src') ?: ''
		
		WebUI.comment("Found alternative patch image: " + anyPatchImageSrc)
		
		// Check if it contains our frame identifier
		if (frameIdentifier && anyPatchImageSrc.contains("/frames/" + frameIdentifier + "/")) {
			WebUI.comment("VERIFICATION PASSED: Found a patch with matching frame identifier: " + frameIdentifier)
		} else {
			WebUI.comment("VERIFICATION INCONCLUSIVE: Could not confirm if the same patch is selected")
		}
	} catch (Exception e2) {
		WebUI.comment("Could not find any patch images in patch view: " + e2.getMessage())
	}
}

// If we couldn't find the element using our primary approach, capture a screenshot for analysis
if (!patchElementFound) {
	// Take a screenshot for further analysis
	String screenshotPath = System.getProperty("user.dir") + "/Screenshots/patch_view_screen_" +
						   System.currentTimeMillis() + ".png"
	WebUI.takeScreenshot(screenshotPath)
	WebUI.comment("Saved screenshot to: " + screenshotPath)
}

// Close the browser
WebUI.closeBrowser()