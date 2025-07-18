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
import org.openqa.selenium.interactions.Actions as Actions

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

// Starting platlet tab tab interactions
WebUI.comment('Starting  platletes tab interactions')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_plateletes'),'Platelets')

WebUI.click(findTestObject('Object Repository/single_click/button_plateletes'))

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Morphology'),'Morphology')

WebUI.click(findTestObject('Object Repository/single_click/button_Morphology'))


// Create a TestObject for platelets morphology patches with a more reliable XPath
def plateletsmorphologyPatchesObject = new TestObject('Platelets Morphology Patches')
plateletsmorphologyPatchesObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@class,"Card patches-container")]')

// Find platelets morphology patch elements
List plateletsmorphologyPatches = []
try {
	// First try to find patches
	WebUI.comment('Searching for platelets morphology patches...')
	WebUI.delay(2) // Give some time for patches to load

	// Try with WebUiCommonHelper
	try {
		plateletsmorphologyPatches = WebUiCommonHelper.findWebElements(plateletsmorphologyPatchesObject, 20)
	} catch (Exception e) {
		WebUI.comment('First attempt to find patches failed, trying alternate approach')

		// Try direct driver approach as backup
		plateletsmorphologyPatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@class,"Card patches-container")]'))
	}

	WebUI.comment('Total platelets morphology patches found: ' + plateletsmorphologyPatches.size())

	// If we still have no patches, try one more XPath variant
	if (plateletsmorphologyPatches.size() == 0) {
		WebUI.comment('No patches found with primary XPath, trying alternative XPath')
		plateletsmorphologyPatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@class,"Card patches-container")]'))
		WebUI.comment('Total platelets morphology patches found with alternative XPath: ' + plateletsmorphologyPatches.size())
	}
} catch (Exception e) {
	WebUI.comment('Failed to find platelets morphology patches: ' + e.getMessage())
}

// Define the number of patches to select (exactly 3)
int plateletsmorphologyPatchesToSelect = 3
WebUI.comment('Will select ' + plateletsmorphologyPatchesToSelect + ' platelets morphology patches')

// Selected patches list
def selectedPlateletsmorphologyPatches = []

// Make sure we have patches to work with
if (plateletsmorphologyPatches.size() > 0) {
	// Limit to available patches but don't exceed our target
	int maxToSelect = Math.min(plateletsmorphologyPatchesToSelect, plateletsmorphologyPatches.size())

	// Use CTRL+click (or Command+click on Mac) to select multiple patches
	Actions actions = new Actions(driver)

	for (int i = 0; i < maxToSelect; i++) {
		try {
			WebUI.comment('Selecting platelets morphology patch #' + (i + 1))

			// Scroll to the patch
			WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(plateletsmorphologyPatches.get(i)))
			WebUI.delay(1)

			// For the first patch, just click normally
			if (i == 0) {
				plateletsmorphologyPatches.get(i).click()
			} else {
				// For subsequent patches, hold CTRL (or Command on Mac) while clicking
				// Determine if we're on Mac or Windows
				String osName = System.getProperty('os.name').toLowerCase()
				if (osName.contains('mac')) {
					// For Mac OS
					actions.keyDown(Keys.COMMAND).click(plateletsmorphologyPatches.get(i)).keyUp(Keys.COMMAND).build().perform()
				} else {
					// For Windows/Linux
					actions.keyDown(Keys.CONTROL).click(plateletsmorphologyPatches.get(i)).keyUp(Keys.CONTROL).build().perform()
				}
			}

			WebUI.delay(1)
			selectedPlateletsmorphologyPatches.add(plateletsmorphologyPatches.get(i))

		} catch (Exception e) {
			WebUI.comment('Failed to select platelets morphology patch #' + (i + 1) + ': ' + e.getMessage())
		}
	}

	println('Successfully selected ' + selectedPlateletsmorphologyPatches.size() + ' platelets morphology patches')
} else {
	println('No platelets morphology patches available to select')
}


println"==================================================================="

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_platelet_clumps'),'Platelet Clumps')

WebUI.click(findTestObject('Object Repository/single_click/button_platelet_clumps'))



// Create a TestObject for platelet clumps patches with a more reliable XPath
def plateletclumpsPatchesObject = new TestObject('Platelet Clumps Patches')
plateletclumpsPatchesObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@class,"Card patches-container")]')

// Find platelet clumps patch elements
List plateletclumpsPatches = []
try {
	// First try to find patches
	WebUI.comment('Searching for platelet clumps patches...')
	WebUI.delay(2) // Give some time for patches to load

	// Try with WebUiCommonHelper
	try {
		plateletclumpsPatches = WebUiCommonHelper.findWebElements(plateletclumpsPatchesObject, 20)
	} catch (Exception e) {
		WebUI.comment('First attempt to find patches failed, trying alternate approach')

		// Try direct driver approach as backup
		plateletclumpsPatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@class,"Card patches-container")]'))
	}

	WebUI.comment('Total platelet clumps patches found: ' + plateletclumpsPatches.size())

	// If we still have no patches, try one more XPath variant
	if (plateletclumpsPatches.size() == 0) {
		WebUI.comment('No patches found with primary XPath, trying alternative XPath')
		plateletclumpsPatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@class,"Card patches-container")]'))
		WebUI.comment('Total platelet clumps patches found with alternative XPath: ' + plateletclumpsPatches.size())
	}
} catch (Exception e) {
	WebUI.comment('Failed to find platelet clumps patches: ' + e.getMessage())
}

// Define the number of patches to select (exactly 3)
int plateletclumpsPatchesToSelect = 3
WebUI.comment('Will select ' + plateletclumpsPatchesToSelect + ' platelet clumps patches')

// Selected patches list
def selectedPlateletclumpsPatches = []

// Make sure we have patches to work with
if (plateletclumpsPatches.size() > 0) {
	// Limit to available patches but don't exceed our target
	int maxToSelect = Math.min(plateletclumpsPatchesToSelect, plateletclumpsPatches.size())

	// Use CTRL+click (or Command+click on Mac) to select multiple patches
	Actions actions = new Actions(driver)

	for (int i = 0; i < maxToSelect; i++) {
		try {
			WebUI.comment('Selecting platelet clumps patch #' + (i + 1))

			// Scroll to the patch
			WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(plateletclumpsPatches.get(i)))
			WebUI.delay(1)

			// For the first patch, just click normally
			if (i == 0) {
				plateletclumpsPatches.get(i).click()
			} else {
				// For subsequent patches, hold CTRL (or Command on Mac) while clicking
				// Determine if we're on Mac or Windows
				String osName = System.getProperty('os.name').toLowerCase()
				if (osName.contains('mac')) {
					// For Mac OS
					actions.keyDown(Keys.COMMAND).click(plateletclumpsPatches.get(i)).keyUp(Keys.COMMAND).build().perform()
				} else {
					// For Windows/Linux
					actions.keyDown(Keys.CONTROL).click(plateletclumpsPatches.get(i)).keyUp(Keys.CONTROL).build().perform()
				}
			}

			WebUI.delay(1)
			selectedPlateletclumpsPatches.add(plateletclumpsPatches.get(i))

		} catch (Exception e) {
			WebUI.comment('Failed to select platelet clumps patch #' + (i + 1) + ': ' + e.getMessage())
		}
	}

	println('Successfully selected ' + selectedPlateletclumpsPatches.size() + ' platelet clumps patches')
} else {
	println('No platelet clumps patches available to select')
}

println"==================================================================="


