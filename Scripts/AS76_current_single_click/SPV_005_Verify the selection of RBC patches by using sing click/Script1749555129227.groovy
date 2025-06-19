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

// Open browser and login to PBS system
WebUI.openBrowser('')

WebUI.navigateToUrl('https://as76-pbs.sigtuple.com/login')

WebUI.setText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_username_loginId'), 'deekshithaS')

WebUI.setEncryptedText(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), 'ghLSEQG5l8dyyQdYVN+LYg==')

WebUI.sendKeys(findTestObject('Object Repository/Summary/Page_PBS (1)/input_password_loginPassword'), Keys.chord(Keys.ENTER))

// Wait for the page to load
WebUI.waitForPageLoad(30)

WebUI.delay(3 // Add a small delay to ensure all elements are loaded
    )

// Get the WebDriver instance
WebDriver driver = DriverFactory.getWebDriver()

WebUI.maximizeWindow()
CustomKeywords.'generic.custumFunctions.selectReportByStatus'('Under review')
CustomKeywords.'generic.custumFunctions.assignOrReassignOnTabs'('deekshithaS')

// Wait for the table to be visible

// Starting rbc size tab interactions
WebUI.comment('Starting rbc size tab interactions')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/span_RBC'), 'RBC')

WebUI.click(findTestObject('Object Repository/single_click/span_RBC'))

// Create a TestObject for RBC size patches with a more reliable XPath
def rbcsizePatchesObject = new TestObject('RBC Size Patches')

rbcsizePatchesObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "rbc")]')

// Find RBC size patch elements
List rbcsizePatches = []

try {
    // First try to find patches
    WebUI.comment('Searching for RBC size patches...')

    WebUI.delay(2 // Give some time for patches to load
        )

    // Try with WebUiCommonHelper
    try {
        rbcsizePatches = WebUiCommonHelper.findWebElements(rbcsizePatchesObject, 20)
    }
    catch (Exception e) {
        WebUI.comment('First attempt to find patches failed, trying alternate approach')

        rbcsizePatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "rbc")]'))
    } // Try direct driver approach as backup
    
    WebUI.comment('Total RBC size patches found: ' + rbcsizePatches.size())

    // If we still have no patches, try one more XPath variant
    if (rbcsizePatches.size() == 0) {
        WebUI.comment('No patches found with primary XPath, trying alternative XPath')

        rbcsizePatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@id, "rbc")]'))

        WebUI.comment('Total RBC size patches found with alternative XPath: ' + rbcsizePatches.size())
    }
}
catch (Exception e) {
    WebUI.comment('Failed to find RBC size patches: ' + e.getMessage())
} 

// Define the number of patches to select (exactly 3)
int rbcsizePatchesToSelect = 3

WebUI.comment(('Will select ' + rbcsizePatchesToSelect) + ' RBC size patches')

// Selected patches list
def selectedRBCsizePatches = []

// Make sure we have patches to work with
if (rbcsizePatches.size() > 0) {
    // Limit to available patches but don't exceed our target
    int maxToSelect = Math.min(rbcsizePatchesToSelect, rbcsizePatches.size())

    // Use CTRL+click (or Command+click on Mac) to select multiple patches
    Actions actions = new Actions(driver)

    for (int i = 0; i < maxToSelect; i++) {
        try {
            WebUI.comment('Selecting RBC size patch #' + (i + 1))

            // Scroll to the patch
            WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(rbcsizePatches.get(i)))

            WebUI.delay(1)

            // For the first patch, just click normally
            if (i == 0) {
                rbcsizePatches.get(i).click() // For subsequent patches, hold CTRL (or Command on Mac) while clicking
                // Determine if we're on Mac or Windows
                // For Mac OS
                // For Windows/Linux
            } else {
                String osName = System.getProperty('os.name').toLowerCase()

                if (osName.contains('mac')) {
                    actions.keyDown(Keys.COMMAND).click(rbcsizePatches.get(i)).keyUp(Keys.COMMAND).build().perform()
                } else {
                    actions.keyDown(Keys.CONTROL).click(rbcsizePatches.get(i)).keyUp(Keys.CONTROL).build().perform()
                }
            }
            
            WebUI.delay(1)

            selectedRBCsizePatches.add(rbcsizePatches.get(i))
        }
        catch (Exception e) {
            WebUI.comment((('Failed to select RBC size patch #' + (i + 1)) + ': ') + e.getMessage())
        } 
    }
    
    println(('Successfully selected ' + selectedRBCsizePatches.size()) + ' RBC size patches')
} else {
    println('No RBC size patches available to select')
}

println('===================================================================')

// Starting rbc shape tab interactions
WebUI.comment('Starting rbc shape tab interactions')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Shape'), 'Shape')

WebUI.click(findTestObject('Object Repository/single_click/button_Shape'))

// Create a TestObject for RBC shape patches with a more reliable XPath
def rbcshapePatchesObject = new TestObject('RBC Shape Patches')

rbcshapePatchesObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "rbc")]')

// Find RBC shape patch elements
List rbcshapePatches = []

try {
    // First try to find patches
    WebUI.comment('Searching for RBC shape patches...')

    WebUI.delay(2 // Give some time for patches to load
        )

    // Try with WebUiCommonHelper
    try {
        rbcshapePatches = WebUiCommonHelper.findWebElements(rbcshapePatchesObject, 20)
    }
    catch (Exception e) {
        WebUI.comment('First attempt to find patches failed, trying alternate approach')

        rbcshapePatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "rbc")]'))
    } // Try direct driver approach as backup
    
    WebUI.comment('Total RBC shape patches found: ' + rbcshapePatches.size())

    // If we still have no patches, try one more XPath variant
    if (rbcshapePatches.size() == 0) {
        WebUI.comment('No patches found with primary XPath, trying alternative XPath')

        rbcshapePatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@id, "rbc")]'))

        WebUI.comment('Total RBC shape patches found with alternative XPath: ' + rbcshapePatches.size())
    }
}
catch (Exception e) {
    WebUI.comment('Failed to find RBC shape patches: ' + e.getMessage())
} 

// Define the number of patches to select (exactly 3)
int rbcshapePatchesToSelect = 3

WebUI.comment(('Will select ' + rbcshapePatchesToSelect) + ' RBC shape patches')

// Selected patches list
def selectedRBCshapePatches = []

// Make sure we have patches to work with
if (rbcshapePatches.size() > 0) {
    // Limit to available patches but don't exceed our target
    int maxToSelect = Math.min(rbcshapePatchesToSelect, rbcshapePatches.size())

    // Use CTRL+click (or Command+click on Mac) to select multiple patches
    Actions actions = new Actions(driver)

    for (int i = 0; i < maxToSelect; i++) {
        try {
            WebUI.comment('Selecting RBC shape patch #' + (i + 1))

            // Scroll to the patch
            WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(rbcshapePatches.get(i)))

            WebUI.delay(1)

            // For the first patch, just click normally
            if (i == 0) {
                rbcshapePatches.get(i).click() // For subsequent patches, hold CTRL (or Command on Mac) while clicking
                // Determine if we're on Mac or Windows
                // For Mac OS
                // For Windows/Linux
            } else {
                String osName = System.getProperty('os.name').toLowerCase()

                if (osName.contains('mac')) {
                    actions.keyDown(Keys.COMMAND).click(rbcshapePatches.get(i)).keyUp(Keys.COMMAND).build().perform()
                } else {
                    actions.keyDown(Keys.CONTROL).click(rbcshapePatches.get(i)).keyUp(Keys.CONTROL).build().perform()
                }
            }
            
            WebUI.delay(1)

            selectedRBCshapePatches.add(rbcshapePatches.get(i))
        }
        catch (Exception e) {
            WebUI.comment((('Failed to select RBC shape patch #' + (i + 1)) + ': ') + e.getMessage())
        } 
    }
    
    println(('Successfully selected ' + selectedRBCshapePatches.size()) + ' RBC shape patches')
} else {
    println('No RBC shape patches available to select')
}

println('===================================================================')

// Starting rbc color tab interactions
WebUI.comment('Starting rbc color tab interactions')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Color'), 'Colour')

WebUI.click(findTestObject('Object Repository/single_click/button_Color'))

// Create a TestObject for RBC color patches with a more reliable XPath
def rbccolorPatchesObject = new TestObject('RBC Color Patches')

rbccolorPatchesObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "rbc")]')

// Find RBC color patch elements
List rbccolorPatches = []

try {
    // First try to find patches
    WebUI.comment('Searching for RBC color patches...')

    WebUI.delay(2 // Give some time for patches to load
        )

    // Try with WebUiCommonHelper
    try {
        rbccolorPatches = WebUiCommonHelper.findWebElements(rbccolorPatchesObject, 20)
    }
    catch (Exception e) {
        WebUI.comment('First attempt to find patches failed, trying alternate approach')

        rbccolorPatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "rbc")]'))
    } // Try direct driver approach as backup
    
    WebUI.comment('Total RBC color patches found: ' + rbccolorPatches.size())

    // If we still have no patches, try one more XPath variant
    if (rbccolorPatches.size() == 0) {
        WebUI.comment('No patches found with primary XPath, trying alternative XPath')

        rbccolorPatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@id, "rbc")]'))

        WebUI.comment('Total RBC color patches found with alternative XPath: ' + rbccolorPatches.size())
    }
}
catch (Exception e) {
    WebUI.comment('Failed to find RBC color patches: ' + e.getMessage())
} 

// Define the number of patches to select (exactly 3)
int rbccolorPatchesToSelect = 3

WebUI.comment(('Will select ' + rbccolorPatchesToSelect) + ' RBC color patches')

// Selected patches list
def selectedRBCcolorPatches = []

// Make sure we have patches to work with
if (rbccolorPatches.size() > 0) {
    // Limit to available patches but don't exceed our target
    int maxToSelect = Math.min(rbccolorPatchesToSelect, rbccolorPatches.size())

    // Use CTRL+click (or Command+click on Mac) to select multiple patches
    Actions actions = new Actions(driver)

    for (int i = 0; i < maxToSelect; i++) {
        try {
            WebUI.comment('Selecting RBC color patch #' + (i + 1))

            // Scroll to the patch
            WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(rbccolorPatches.get(i)))

            WebUI.delay(1)

            // For the first patch, just click normally
            if (i == 0) {
                rbccolorPatches.get(i).click() // For subsequent patches, hold CTRL (or Command on Mac) while clicking
                // Determine if we're on Mac or Windows
                // For Mac OS
                // For Windows/Linux
            } else {
                String osName = System.getProperty('os.name').toLowerCase()

                if (osName.contains('mac')) {
                    actions.keyDown(Keys.COMMAND).click(rbccolorPatches.get(i)).keyUp(Keys.COMMAND).build().perform()
                } else {
                    actions.keyDown(Keys.CONTROL).click(rbccolorPatches.get(i)).keyUp(Keys.CONTROL).build().perform()
                }
            }
            
            WebUI.delay(1)

            selectedRBCcolorPatches.add(rbccolorPatches.get(i))
        }
        catch (Exception e) {
            WebUI.comment((('Failed to select RBC color patch #' + (i + 1)) + ': ') + e.getMessage())
        } 
    }
    
    println(('Successfully selected ' + selectedRBCcolorPatches.size()) + ' RBC color patches')
} else {
    println('No RBC color patches available to select')
}

println('===================================================================')

// Starting rbc Inclusions tab interactions
WebUI.comment('Starting rbc Inclusions tab interactions')

WebUI.verifyElementText(findTestObject('Object Repository/single_click/button_Inclusions'), 'Inclusions')

WebUI.click(findTestObject('Object Repository/single_click/button_Inclusions'))

// Create a TestObject for RBC inclusions patches with a more reliable XPath
def rbcinclusionsPatchesObject = new TestObject('RBC Inclusions Patches')

rbcinclusionsPatchesObject.addProperty('xpath', ConditionType.EQUALS, '//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "rbc")]')

// Find RBC inclusions patch elements
List rbcinclusionsPatches = []

try {
    // First try to find patches
    WebUI.comment('Searching for RBC inclusions patches...')

    WebUI.delay(2 // Give some time for patches to load
        )

    // Try with WebUiCommonHelper
    try {
        rbcinclusionsPatches = WebUiCommonHelper.findWebElements(rbcinclusionsPatchesObject, 20)
    }
    catch (Exception e) {
        WebUI.comment('First attempt to find patches failed, trying alternate approach')

        rbcinclusionsPatches = driver.findElements(By.xpath('//div[contains(@class, "patches-section")]//div[contains(@class, "patch") and contains(@id, "rbc")]'))
    } // Try direct driver approach as backup
    
    WebUI.comment('Total RBC inclusions patches found: ' + rbcinclusionsPatches.size())

    // If we still have no patches, try one more XPath variant
    if (rbcinclusionsPatches.size() == 0) {
        WebUI.comment('No patches found with primary XPath, trying alternative XPath')

        rbcinclusionsPatches = driver.findElements(By.xpath('//div[@class="scroll-container"]//div[contains(@id, "rbc")]'))

        WebUI.comment('Total RBC inclusions patches found with alternative XPath: ' + rbcinclusionsPatches.size())
    }
}
catch (Exception e) {
    WebUI.comment('Failed to find RBC inclusions patches: ' + e.getMessage())
} 

// Define the number of patches to select (exactly 3)
int rbcinclusionsPatchesToSelect = 3

WebUI.comment(('Will select ' + rbcinclusionsPatchesToSelect) + ' RBC inclusions patches')

// Selected patches list
def selectedRBCinclusionsPatches = []

// Make sure we have patches to work with
if (rbcinclusionsPatches.size() > 0) {
    // Limit to available patches but don't exceed our target
    int maxToSelect = Math.min(rbcinclusionsPatchesToSelect, rbcinclusionsPatches.size())

    // Use CTRL+click (or Command+click on Mac) to select multiple patches
    Actions actions = new Actions(driver)

    for (int i = 0; i < maxToSelect; i++) {
        try {
            WebUI.comment('Selecting RBC inclusions patch #' + (i + 1))

            // Scroll to the patch
            WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', Arrays.asList(rbcinclusionsPatches.get(i)))

            WebUI.delay(1)

            // For the first patch, just click normally
            if (i == 0) {
                rbcinclusionsPatches.get(i).click() // For subsequent patches, hold CTRL (or Command on Mac) while clicking
                // Determine if we're on Mac or Windows
                // For Mac OS
                // For Windows/Linux
            } else {
                String osName = System.getProperty('os.name').toLowerCase()

                if (osName.contains('mac')) {
                    actions.keyDown(Keys.COMMAND).click(rbcinclusionsPatches.get(i)).keyUp(Keys.COMMAND).build().perform()
                } else {
                    actions.keyDown(Keys.CONTROL).click(rbcinclusionsPatches.get(i)).keyUp(Keys.CONTROL).build().perform()
                }
            }
            
            WebUI.delay(1)

            selectedRBCinclusionsPatches.add(rbcinclusionsPatches.get(i))
        }
        catch (Exception e) {
            WebUI.comment((('Failed to select RBC inclusions patch #' + (i + 1)) + ': ') + e.getMessage())
        } 
    }
    
    println(('Successfully selected ' + selectedRBCinclusionsPatches.size()) + ' RBC inclusions patches')
} else {
    println('No RBC inclusions patches available to select')
}

println('===================================================================')

